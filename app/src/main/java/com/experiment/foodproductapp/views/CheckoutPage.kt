package com.experiment.foodproductapp.views

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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.domain.event.CheckoutFormEvent
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.CheckoutPageViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CheckoutPage(
    email: String?,
    sum: Int?,
    points: Int?,
    navHostControllerLambda: () -> NavHostController,
    checkoutPageViewModel: CheckoutPageViewModel = koinViewModel(),
) {
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }


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
            checkoutPageViewModel.fetchUserDetails(email)
        }
        checkoutPageViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    if (points != null) {
                        navHostControllerLambda().navigate(
                            Screen.PaymentScreen.routeWithData(
                                email = checkoutPageViewModel.state.value.email,
                                phoneNumber = checkoutPageViewModel.state.value.phoneNumber,
                                sum = checkoutPageViewModel.sum.value * 100,
                                points = points
                            )
                        )
                    }
                }
                else -> {}
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
                        text = stringResource(id = R.string.shipping_details_string) + " ",
                        fontFamily = titleFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = DarkYellow,
                    )

                    Icon(
                        painterResource(id = R.drawable.ic_shopping_cart_checkout),
                        contentDescription = "ic_shopping_cart_checkout",
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
                        contentDescription = "ic_arrow_back_bt",
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
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {

            //Textfields
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                ) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    TextField(
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = checkoutPageViewModel.state.value.firstName,
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
                        label = {
                            Text(
                                text = stringResource(id = R.string.first_name_string),
                                color = DarkGray1
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                        )
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    TextField(
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = checkoutPageViewModel.state.value.lastName,
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
                        label = {
                            Text(
                                text = stringResource(id = R.string.last_name_string),
                                color = DarkGray1
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                        )
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    TextField(
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = checkoutPageViewModel.state.value.phoneNumber,
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
                        label = {
                            Text(
                                text = stringResource(id = R.string.phone_number_string),
                                color = DarkGray1
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                        )
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = checkoutPageViewModel.state.value.pincode,
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
                        label = {
                            Text(
                                text = stringResource(id = R.string.enter_pincode_string),
                                color = DarkGray1
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                        )
                    )
                    if (checkoutPageViewModel.state.value.pincodeError != null) {
                        Text(
                            text = checkoutPageViewModel.state.value.pincodeError!!,
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
                        value = checkoutPageViewModel.state.value.addressLine1,
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
                        label = {
                            Text(
                                text = stringResource(id = R.string.flat_house_string),
                                color = DarkGray1
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                        )
                    )
                    if (checkoutPageViewModel.state.value.addressLine1Error != null) {
                        Text(
                            text = checkoutPageViewModel.state.value.addressLine1Error!!,
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
                        value = checkoutPageViewModel.state.value.addressLine2,
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
                        label = {
                            Text(
                                text = stringResource(id = R.string.area_street_string),
                                color = DarkGray1
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                        )
                    )
                    if (checkoutPageViewModel.state.value.addressLine2Error != null) {
                        Text(
                            text = checkoutPageViewModel.state.value.addressLine2Error!!,
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
                        value = checkoutPageViewModel.state.value.city,
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
                        label = {
                            Text(
                                text = stringResource(id = R.string.city_string),
                                color = DarkGray1
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { mExpanded = !mExpanded },
                        )
                    )
                    if (checkoutPageViewModel.state.value.cityError != null) {
                        Text(
                            text = checkoutPageViewModel.state.value.cityError!!,
                            color = MaterialTheme.colors.error,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    }

                    Spacer(modifier = Modifier.padding(10.dp))
//                OutlinedButton(
//                    modifier = Modifier.fillMaxWidth(),
//                    contentPadding = PaddingValues(0.dp,0.dp,0.dp,0.dp),
//                    onClick = { mExpanded = !mExpanded },
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
//                    border = BorderStroke(width = 0.dp,color = Color.Transparent)
//                )
                    ExposedDropdownMenuBox(
                        expanded = mExpanded,
                        onExpandedChange = {
                            mExpanded = !mExpanded
                        }
                    )
                    {

                        TextField(
                            readOnly = true,
                            value = checkoutPageViewModel.state.value.state,
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
                            label = {
                                Text(
                                    text = stringResource(id = R.string.state_string),
                                    color = DarkGray1
                                )
                            },
                            trailingIcon = {
                                Icon(icon, "ic_arrow_up/down",
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
                            stringArrayResource(id = R.array.state).forEach { label ->
                                DropdownMenuItem(onClick = {
                                    checkoutPageViewModel.onEvent(
                                        CheckoutFormEvent.StateChanged(
                                            label
                                        )
                                    )
                                    mExpanded = false
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }
                    if (checkoutPageViewModel.state.value.stateError != null) {
                        Text(
                            text = checkoutPageViewModel.state.value.stateError!!,
                            color = MaterialTheme.colors.error,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .shadow(70.dp)
                        .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        .fillMaxWidth()
                        .background(DarkYellow),
                ) {
                    CheckoutArea(
                        checkoutPageViewModel = checkoutPageViewModel,
                    )
                }
            }

        }
    }
}

@Composable
fun CheckoutArea(
    checkoutPageViewModel: CheckoutPageViewModel,
) {

    Column {
        //Price row
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.total_string) + " ",
                color = Color.White,
                modifier = Modifier
                    .padding(start = 25.dp)
                    .weight(1F),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )

            Text(
                text = stringResource(id = R.string.rs_string) + " " + checkoutPageViewModel.sum.value,
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
                .fillMaxSize(),
        ) {
            Button(
                modifier = Modifier
                    .padding(
                        start = 30.dp,
                        end = 30.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                    .fillMaxSize(),
                onClick = { checkoutPageViewModel.onEvent(CheckoutFormEvent.Submit) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                shape = RoundedCornerShape(50),
            ) {
                Text(
                    text = stringResource(id = R.string.proceed_to_payment_string),
                    color = Color.Black,
                    maxLines = 1,
                    fontSize = 20.sp
//                    style = LocalTextStyle.current.copy(
//                        fontSize = LocalTextStyle.current.fontSize * multiplier
//                    ),
//                    onTextLayout = {
//                        if (it.hasVisualOverflow) {
//                            multiplier *= 0.99f // you can tune this constant
//                        }
//                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun preview() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {
        navHostController
    }
    CheckoutPage("sahil@test.com", 500, 0, navHostControllerLambda)
}