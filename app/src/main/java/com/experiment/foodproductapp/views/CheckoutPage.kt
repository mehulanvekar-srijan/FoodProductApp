package com.experiment.foodproductapp.views

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.domain.event.CheckoutFormEvent
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.CheckoutPageViewModel
import kotlinx.coroutines.launch


@Preview
@Composable
fun preview() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {

        navHostController
    }
    CheckoutPage("sahil@test.com", 500, navHostControllerLambda = navHostControllerLambda)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckoutPage(
    email: String?,
    sum: Int?,
    navHostControllerLambda: () -> NavHostController,
    checkoutPageViewModel: CheckoutPageViewModel = viewModel(),
) {
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // Create a list of cities
    val mCities = listOf(
        "Goa",
        "Maharashtra",
        "Karnataka",
        "Rajasthan",
        "Kerala",
        "Tamil Nadu",
        "Andhra Pradesh"
    )


    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    val viewRequesterForCity = remember { BringIntoViewRequester() }
    val viewRequesterForState = remember { BringIntoViewRequester() }

    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()

    if (sum != null) {
        checkoutPageViewModel.sum.value = sum
    }

    val context = LocalContext.current

    ChangeBarColors(statusColor = Color.White, navigationBarColor = DarkYellow)

    LaunchedEffect(key1 = Unit) {
        if (email != null && sum != null) {
            checkoutPageViewModel.sum.value = sum.toInt()
            checkoutPageViewModel.fetchUserDetails(context, email)
        }
        checkoutPageViewModel.validationEvents.collect { event ->
            when (event) {
                is CheckoutPageViewModel.ValidationEvent.Success -> {
                    checkoutPageViewModel.navigateOnSuccess(context, navHostControllerLambda())
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
//        top icon and back button
        TopAppBar(
            title = {
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "Checkout ",
                        fontFamily = titleFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = DarkYellow,
                    )
                    Icon(
                        painterResource(id = R.drawable.ic_shopping_cart_checkout),
                        contentDescription = "",
                        tint = DarkYellow,
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        navHostControllerLambda().navigateUp()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = DarkYellow,
                    )
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(6F)
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {

            //Textfields
            item {
                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = checkoutPageViewModel.state.firstName,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = LightGray1,
                        placeholderColor = Color.White,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        errorCursorColor = Color.Black,
                        errorLabelColor = Color.Red,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Orange,
                    ),
                    onValueChange = {
                    },
                    shape = RoundedCornerShape(30.dp),
                    label = { Text(text = "First Name", color = DarkGray1) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    )
                )

                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = checkoutPageViewModel.state.lastName,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = LightGray1,
                        placeholderColor = Color.White,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        errorCursorColor = Color.Black,
                        errorLabelColor = Color.Red,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Orange,
                    ),
                    onValueChange = {
                    },
                    shape = RoundedCornerShape(30.dp),
                    label = { Text(text = "Last Name", color = DarkGray1) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    )
                )

                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = checkoutPageViewModel.state.phoneNumber,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = LightGray1,
                        placeholderColor = Color.White,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        errorCursorColor = Color.Black,
                        errorLabelColor = Color.Red,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Orange,
                    ),
                    onValueChange = {
                    },
                    shape = RoundedCornerShape(30.dp),
                    label = { Text(text = "Phone Number", color = DarkGray1) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    )
                )

                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = checkoutPageViewModel.state.pincode,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = LightGray1,
                        placeholderColor = Color.White,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        errorCursorColor = Color.Black,
                        errorLabelColor = Color.Red,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Orange,
                    ),
                    onValueChange = {
                        checkoutPageViewModel.onEvent(
                            CheckoutFormEvent.PinCodeChanged(it)
                        )
                    },
                    shape = RoundedCornerShape(30.dp),
                    label = { Text(text = "Enter Pincode", color = DarkGray1) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    )
                )
                if (checkoutPageViewModel.state.pincodeError != null) {
                    Text(
                        text = checkoutPageViewModel.state.pincodeError!!,
                        color = MaterialTheme.colors.error,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch {
                                    viewRequesterForCity.bringIntoView()
                                }
                            }
                        },
                    value = checkoutPageViewModel.state.addressLine1,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = LightGray1,
                        placeholderColor = Color.White,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        errorCursorColor = Color.Black,
                        errorLabelColor = Color.Red,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Orange,
                    ),
                    onValueChange = {
                        checkoutPageViewModel.onEvent(
                            CheckoutFormEvent.AddressLine1Changed(it)
                        )
                    },
                    shape = RoundedCornerShape(30.dp),
                    label = { Text(text = "Flat, House no., Building", color = DarkGray1) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    )
                )
                if (checkoutPageViewModel.state.addressLine1Error != null) {
                    Text(
                        text = checkoutPageViewModel.state.addressLine1Error!!,
                        color = MaterialTheme.colors.error,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch {
                                    viewRequesterForState.bringIntoView()
                                }
                            }
                        },
                    value = checkoutPageViewModel.state.addressLine2,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = LightGray1,
                        placeholderColor = Color.White,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        errorCursorColor = Color.Black,
                        errorLabelColor = Color.Red,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Orange,
                    ),
                    onValueChange = {
                        checkoutPageViewModel.onEvent(
                            CheckoutFormEvent.AddressLine2Changed(it)
                        )
                    },
                    shape = RoundedCornerShape(30.dp),
                    label = { Text(text = "Area, Street", color = DarkGray1) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    )
                )
                if (checkoutPageViewModel.state.addressLine2Error != null) {
                    Text(
                        text = checkoutPageViewModel.state.addressLine2Error!!,
                        color = MaterialTheme.colors.error,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .bringIntoViewRequester(viewRequesterForCity),
                    value = checkoutPageViewModel.state.city,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = LightGray1,
                        placeholderColor = Color.White,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        errorCursorColor = Color.Black,
                        errorLabelColor = Color.Red,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Orange,
                    ),
                    onValueChange = {
                        checkoutPageViewModel.onEvent(
                            CheckoutFormEvent.CityChanged(it)
                        )
                    },
                    shape = RoundedCornerShape(30.dp),
                    label = { Text(text = "City", color = DarkGray1) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { mExpanded = !mExpanded },
                    )
                )
                if (checkoutPageViewModel.state.cityError != null) {
                    Text(
                        text = checkoutPageViewModel.state.cityError!!,
                        color = MaterialTheme.colors.error,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))
                Column() {
                    TextField(
                        readOnly = true,
                        value = checkoutPageViewModel.state.state,
                        shape = RoundedCornerShape(30.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = LightGray1,
                            placeholderColor = Color.White,
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            errorCursorColor = Color.Black,
                            errorLabelColor = Color.Red,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedLabelColor = Orange,
                        ),
                        onValueChange = {
                            checkoutPageViewModel.onEvent(CheckoutFormEvent.StateChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .bringIntoViewRequester(viewRequesterForState),
                        label = { Text(text = "State", color = DarkGray1) },
                        trailingIcon = {
                            Icon(icon, "contentDescription",
                                Modifier.clickable { mExpanded = !mExpanded })
                        }
                    )

                    // Create a drop-down menu with list of cities,
                    // when clicked, set the Text Field text as the city selected
                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        mCities.forEach { label ->
                            DropdownMenuItem(onClick = {
                                checkoutPageViewModel.onEvent(CheckoutFormEvent.StateChanged(label))
                                mExpanded = false
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                }
                if (checkoutPageViewModel.state.stateError != null) {
                    Text(
                        text = checkoutPageViewModel.state.stateError!!,
                        color = MaterialTheme.colors.error,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Box(
            modifier = Modifier
                .shadow(70.dp)
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .weight(1F)
                .fillMaxSize()
                .background(DarkYellow)
        ) {
            CheckoutArea(
                checkoutPageViewModel = checkoutPageViewModel
            )
        }
    }
}


@Composable
fun CheckoutArea(
    checkoutPageViewModel: CheckoutPageViewModel
) {
    Column {
        //Price row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35F),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Total ",
                color = Color.White,
                modifier = Modifier
                    .padding(start = 25.dp)
                    .weight(1F),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )

            Text(
                text = "Rs : ${checkoutPageViewModel.sum.value}",
                color = Color.White,
                modifier = Modifier
                    .padding(end = 25.dp),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        //Checkout button
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, top = 6.dp, bottom = 15.dp)
                    .fillMaxSize(),
                onClick = { checkoutPageViewModel.onEvent(CheckoutFormEvent.Submit) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                shape = RoundedCornerShape(50),
            ) {
                Text(
                    text = "Proceed to Payment",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))
    }
}

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun States(
//    viewRequesterForState: BringIntoViewRequester,
//    checkoutPageViewModel: CheckoutPageViewModel
//) {
//
//    // Declaring a boolean value to store
//    // the expanded state of the Text Field
//    var mExpanded by remember { mutableStateOf(false) }
//
//    // Create a list of cities
//    val mCities = listOf(
//        "Goa",
//        "Maharashtra",
//        "Karnataka",
//        "Rajasthan",
//        "Kerala",
//        "Tamil Nadu",
//        "Andhra Pradesh"
//    )
//
//
//    // Up Icon when expanded and down icon when collapsed
//    val icon = if (mExpanded)
//        Icons.Filled.KeyboardArrowUp
//    else
//        Icons.Filled.KeyboardArrowDown
//
//    Column() {
//
//        // Create an Outlined Text Field
//        // with icon and not expanded
//        TextField(
//            readOnly = true,
//            value = checkoutPageViewModel.state.state,
//            shape = RoundedCornerShape(30.dp),
//            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.Black,
//                backgroundColor = LightGray1,
//                placeholderColor = Color.White,
//                cursorColor = Color.Black,
//                focusedLabelColor = Color.Black,
//                errorCursorColor = Color.Black,
//                errorLabelColor = Color.Red,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                unfocusedLabelColor = Orange,
//            ),
//            onValueChange = {
//                checkoutPageViewModel.onEvent(CheckoutFormEvent.StateChanged(it))
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .bringIntoViewRequester(viewRequesterForState),
//            label = { Text(text = "State", color = DarkGray1) },
//            trailingIcon = {
//                Icon(icon, "contentDescription",
//                    Modifier.clickable { mExpanded = !mExpanded })
//            }
//        )
//
//        // Create a drop-down menu with list of cities,
//        // when clicked, set the Text Field text as the city selected
//        DropdownMenu(
//            expanded = mExpanded,
//            onDismissRequest = { mExpanded = false },
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            mCities.forEach { label ->
//                DropdownMenuItem(onClick = {
//                    checkoutPageViewModel.onEvent(CheckoutFormEvent.StateChanged(label))
//                    mExpanded = false
//                }) {
//                    Text(text = label)
//                }
//            }
//        }
//    }
//}