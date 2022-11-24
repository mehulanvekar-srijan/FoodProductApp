package com.experiment.foodproductapp.views

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.EditCalendar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.domain.event.UserDetailsFormEvent
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.utility.ComposeFileProvider
import com.experiment.foodproductapp.viewmodels.ProductCartViewModel
import com.experiment.foodproductapp.viewmodels.UserDetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun UserDetails(
    navHostControllerLambda: () -> NavHostController,
    email: String?,
    userDetailsViewModel: UserDetailsViewModel = koinViewModel(),
) {
    if (email != null) {
        val focusManager = LocalFocusManager.current
        val viewRequesterForDatePicker = remember { BringIntoViewRequester() }
        val coroutineScope = rememberCoroutineScope()

        val context = LocalContext.current

        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Declaring a string value to
        // store date in string format

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                userDetailsViewModel.onEvent(
                    context,
                    UserDetailsFormEvent.CalenderChanged("$mDayOfMonth/${mMonth + 1}/$mYear")
                )
            }, mYear, mMonth, mDay
        )

        LaunchedEffect(key1 = Unit) {
            userDetailsViewModel.execute(email)
            userDetailsViewModel.initProfilePicture(email) //Load image from db
        }

        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->

                if (uri == null) return@rememberLauncherForActivityResult

                //uri from argument requires permission to access it after relaunching the app
                //Hence create a local file in Apps internal storage and copy the selected image
                //into a local file, and save that uri in database

                val localUri = ComposeFileProvider.getImageUri(context)
                val inputStream = context.contentResolver.openInputStream(uri)
                val outputStream = context.contentResolver.openOutputStream(localUri)

                inputStream.use {
                    if (outputStream != null) {
                        it?.copyTo(outputStream)
                    }
                }

                userDetailsViewModel.hasImage.value = localUri != null           //Set has image
                userDetailsViewModel.imageUri.value = localUri                   //Set URI
                userDetailsViewModel.updateUserProfilePictureInDatabase(        //Update database
                    email, localUri
                )
            })

        var intermediateUri: Uri? = null
        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { status ->

                if (!status) return@rememberLauncherForActivityResult

                userDetailsViewModel.hasImage.value = status                //Set has image
                userDetailsViewModel.imageUri.value = intermediateUri       //Set URI
                userDetailsViewModel.updateUserProfilePictureInDatabase(    //Update database
                    email, intermediateUri
                )
            }
        )

        //Alert Dialog
        if(userDetailsViewModel.dialogBox.value) {
            ShowDialogBox(
                confirmButtonLogic = {
                    userDetailsViewModel.logOutUser(
                        email,
                        navHostControllerLambda()
                    )
                    userDetailsViewModel.changeDialogBoxStatus(false)
                },
                dismissButtonLogic = {
                    userDetailsViewModel.changeDialogBoxStatus(false)
                },
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_user_view),
                contentDescription = "ic_background_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            //Main Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                horizontalAlignment = CenterHorizontally,
            ) {

                val showOptions = remember { mutableStateOf(false) }
                val profileImageModifier = Modifier
                    .fillMaxHeight(0.25F)
                    .padding(top = 30.dp, start = 25.dp, end = 25.dp, bottom = 15.dp)
                    .aspectRatio(1F)
                    .clip(CircleShape)
                    .clickable { showOptions.value = !showOptions.value }

                //Profile Image
                if (userDetailsViewModel.hasImage.value && userDetailsViewModel.imageUri.value != null) {
                    Image(
                        painter = rememberImagePainter(userDetailsViewModel.imageUri.value),
                        contentDescription = "ic_profile_pic",
                        contentScale = ContentScale.Crop,
                        modifier = profileImageModifier
                    )
                }
                else {
                    Image(
                        painter = rememberImagePainter(R.drawable.ic_user3),
                        contentDescription = "ic_profile_pic",
                        contentScale = ContentScale.Crop,
                        modifier = profileImageModifier
                    )
                }

                AnimatedVisibility(
                    visible = showOptions.value
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally,
                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_triangle),
//                            contentDescription = "",
//                            modifier = Modifier.height(15.dp),
//                            tint = Color.Unspecified
//                        )
                        Row(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 0.dp)
                                .clip(RoundedCornerShape(20))
                                .background(Color.White),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            IconButton(onClick = {
                                val uri = ComposeFileProvider.getImageUri(context)
                                cameraLauncher.launch(uri)
                                intermediateUri = uri
                            }) {
                                Icon(imageVector = Icons.Default.Camera, contentDescription = "ic_click_image")
                            }
                            IconButton(onClick = { imagePicker.launch("image/*")}) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "ic_pick_image")
                            }
                        }
                    }

                }

                //Text Fields
                Column(
                    modifier = Modifier
                        .shadow(30.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                        .background(Color.White)
                        .padding(top = 10.dp, start = 28.dp, end = 28.dp),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.profile_string),
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            letterSpacing = 2.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.80F)
                    ) {
                        item {
                            Spacer(modifier = Modifier.padding(10.dp))
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = userDetailsViewModel.state.value.firstName,
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
                                    userDetailsViewModel.onEvent(
                                        context,
                                        UserDetailsFormEvent.FirstNameChanged(it)
                                    )
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
                            if (userDetailsViewModel.state.value.firstNameError != null) {
                                Text(
                                    text = userDetailsViewModel.state.value.firstNameError!!,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End
                                )
                            }

                            Spacer(modifier = Modifier.padding(10.dp))
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = userDetailsViewModel.state.value.lastName,
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
                                    userDetailsViewModel.onEvent(
                                        context,
                                        UserDetailsFormEvent.LastNameChanged(it)
                                    )
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
                            if (userDetailsViewModel.state.value.lastNameError != null) {
                                Text(
                                    text = userDetailsViewModel.state.value.lastNameError!!,
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
                                                viewRequesterForDatePicker.bringIntoView()
                                            }
                                        }
                                    },
                                value = userDetailsViewModel.state.value.phoneNumber,
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
                                    userDetailsViewModel.onEvent(
                                        context,
                                        UserDetailsFormEvent.PhoneNumberChanged(it)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone,
                                    imeAction = ImeAction.Next
                                ),
                                shape = RoundedCornerShape(30.dp),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.phone_number_string),
                                        color = DarkGray1
                                    )
                                },
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                                )
                            )
                            if (userDetailsViewModel.state.value.phoneNumberError != null) {
                                Text(
                                    text = userDetailsViewModel.state.value.phoneNumberError!!,
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
                                                viewRequesterForDatePicker.bringIntoView()
                                            }
                                        }
                                    },
                                value = userDetailsViewModel.state.value.password,
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
                                    userDetailsViewModel.onEvent(
                                        context,
                                        UserDetailsFormEvent.PasswordChanged(it)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                shape = RoundedCornerShape(30.dp),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.password_string),
                                        color = DarkGray1
                                    )
                                },
                                keyboardActions = KeyboardActions(
                                    onNext = { mDatePickerDialog.show() },
                                )
                            )
                            if (userDetailsViewModel.state.value.passwordError != null) {
                                Text(
                                    text = userDetailsViewModel.state.value.passwordError!!,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End
                                )
                            }


                            Spacer(modifier = Modifier.padding(10.dp))
                            TextField(
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .bringIntoViewRequester(viewRequesterForDatePicker),
                                value = userDetailsViewModel.state.value.date,
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
                                    userDetailsViewModel.onEvent(
                                        context,
                                        UserDetailsFormEvent.CalenderChanged(it)
                                    )
                                },
                                leadingIcon = {
                                    IconButton(onClick = {
                                        mDatePickerDialog.show()
                                    }) {
                                        Icon(
                                            imageVector = Icons.TwoTone.EditCalendar,
                                            contentDescription = "ic_edit_dob_bt",
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(30.dp),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.date_of_birth_string),
                                        color = DarkGray1
                                    )
                                }
                            )
                            if (userDetailsViewModel.state.value.dateError != null) {
                                Text(
                                    text = userDetailsViewModel.state.value.dateError!!,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    OutlinedButton(
                        onClick = {
                            userDetailsViewModel.onEvent(context, UserDetailsFormEvent.Submit)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(start = 20.dp, end = 20.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = DarkYellow,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 3.dp
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.save_changes_string),
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name), color = Color.White) },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            navigationIcon = {
                IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "ic_arrow_back_bt",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    navHostControllerLambda().navigate(Screen.Rewards.routeWithData(email))
                }) {
                    Icon(
                        imageVector = Icons.Default.Stars,
                        contentDescription = "ic_rewards_bt",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    userDetailsViewModel.changeDialogBoxStatus(true)
                }) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "ic_logout_bt",
                        tint = Color.White
                    )
                }
            }
        )
    }
}

@Composable
fun ShowDialogBox(
    confirmButtonLogic: () -> Unit = {},
    dismissButtonLogic: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Logout?", fontFamily = titleFontFamily,) },
        text = { Text(text = "Are you sure you want to log out?") },
        confirmButton = { Button(onClick = confirmButtonLogic) { Text("Yes") } },
        dismissButton = { Button(onClick = dismissButtonLogic) { Text("No") } }
    )

}

@Composable
@Preview(showBackground = true)
fun Show1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = CenterHorizontally,
    ){
        BoxWithConstraints(
            modifier = Modifier.background(Color.Green),
            contentAlignment = Center
        ){

            val height: Dp = maxHeight
            val width: Dp  = maxWidth

            val offset = 55
            BadgedBox(
                badge = {
                    Badge(
                        modifier = Modifier.offset(x = -offset.dp, y = offset.dp),
                    ) { Icon(imageVector = Icons.Default.Edit, contentDescription = "") }
                }
            ){
                Image(
                    painter = painterResource(R.drawable.ic_user3),
                    contentDescription = "ic_profile_pic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight(0.25F)
                        .padding(25.dp)
                        .clickable {

                        }
                        .aspectRatio(1F)
                        .clip(CircleShape)
                )
            }


        }
    }

}@Composable

@Preview(showBackground = true)
fun Show2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = CenterHorizontally,
    ){

        Box(
            modifier = Modifier.background(Color.Transparent),
            contentAlignment = Center
        ){
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.25F)
                    .padding(20.dp)
                    .clickable {

                    }
                    .aspectRatio(1F)
                    .clip(CircleShape)
                    .background(Color.Blue)
            ){

                Image(
                    painter = painterResource(R.drawable.ic_user3),
                    contentDescription = "ic_profile_pic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable {

                        }
                        .aspectRatio(1F)
                        .clip(CircleShape)
                        .background(Color.Red)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    contentAlignment = BottomEnd,
                ){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier
                                .background(Color.Yellow)
                        )
                    }
                }

            }
        }

    }

}

@Composable
@Preview(showBackground = true)
fun Show3() {



}
