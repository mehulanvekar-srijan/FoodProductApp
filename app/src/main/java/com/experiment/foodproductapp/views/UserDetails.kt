package com.experiment.foodproductapp.views

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.twotone.EditCalendar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.domain.event.UserDetailsFormEvent
import com.experiment.foodproductapp.ui.theme.DarkGray1
import com.experiment.foodproductapp.ui.theme.DarkYellow
import com.experiment.foodproductapp.ui.theme.LightGray1
import com.experiment.foodproductapp.ui.theme.Orange
import com.experiment.foodproductapp.utility.ComposeFileProvider
import com.experiment.foodproductapp.viewmodels.UserDetailsViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
@Preview
fun show() {
    val string = "Sahil"
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {
        navHostController
    }
    UserDetails(navHostControllerLambda, email = string)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun UserDetails(
    navHostControllerLambda: () -> NavHostController,
    email: String?,
    userDetailsViewModel: UserDetailsViewModel = viewModel(),
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
            userDetailsViewModel.execute(context, email)
            userDetailsViewModel.initProfilePicture(context,email) //Load image from db
        }

        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->

                //uri from argument requires permission to access it after relaunching the app
                //Hence create a local file in Apps internal storage and copy the selected image
                //into a local file, and save that uri in database

                val localUri = ComposeFileProvider.getImageUri(context)
                if (uri != null) {

                    val inputStream = context.contentResolver.openInputStream(uri)
                    val outputStream = context.contentResolver.openOutputStream(localUri)

                    inputStream.use{
                        if (outputStream != null) {
                            it?.copyTo(outputStream)
                        }
                    }
                }

                userDetailsViewModel.hasImage.value = localUri != null           //Set has image
                userDetailsViewModel.imageUri.value = localUri                   //Set URI
                userDetailsViewModel.updateUserProfilePictureInDatabase(        //Update database
                    context,email,localUri
                )

            })

        var intermediateUri: Uri? = null
        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { status ->
                userDetailsViewModel.hasImage.value = status                //Set has image
                userDetailsViewModel.imageUri.value = intermediateUri       //Set URI
                userDetailsViewModel.updateUserProfilePictureInDatabase(    //Update database
                    context,email,intermediateUri
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_yellow_wave),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            //Main Column
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = CenterHorizontally,
            ) {

                if (userDetailsViewModel.hasImage.value && userDetailsViewModel.imageUri.value != null){
                    Image(painter = rememberImagePainter(userDetailsViewModel.imageUri.value),
                        contentDescription = "Profile Pic",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight(0.25F)
                            .padding(25.dp)
                            .aspectRatio(1F)
                            .clip(CircleShape)
                    )
                }
                else{
                    Image(
                        painter = rememberImagePainter(R.drawable.ic_user),
                        contentDescription = "Profile Pic",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight(0.25F)
                            .padding(25.dp)
                            .aspectRatio(1F)
                            .clip(CircleShape)
                    )
                }

                //Pick Click
                Column {
                    Button(onClick = { imagePicker.launch("image/*") }
                    ) {
                        Text(text = "pick")
                    }
                    Button(onClick = {
                        val uri = ComposeFileProvider.getImageUri(context)
                        cameraLauncher.launch(uri)
                        intermediateUri = uri
                    }
                    ) {
                        Text(text = "click")
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                        .background(Color.White)
                        .padding(top = 10.dp, start = 28.dp, end = 28.dp, bottom = 10.dp),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Profile",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            letterSpacing = 2.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.80F)
                    )
                    {
                        item {
                            Spacer(modifier = Modifier.padding(10.dp))
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = userDetailsViewModel.state.firstName,
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
                                label = { Text(text = "First Name", color = DarkGray1) },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                                )
                            )
                            if (userDetailsViewModel.state.firstNameError != null) {
                                Text(
                                    text = userDetailsViewModel.state.firstNameError!!,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End
                                )
                            }

                            Spacer(modifier = Modifier.padding(10.dp))
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = userDetailsViewModel.state.lastName,
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
                                label = { Text(text = "Last Name", color = DarkGray1) },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                                )
                            )
                            if (userDetailsViewModel.state.lastNameError != null) {
                                Text(
                                    text = userDetailsViewModel.state.lastNameError!!,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End
                                )
                            }

//                            Spacer(modifier = Modifier.padding(10.dp))
//                            TextField(
//                                readOnly=true,
//                                modifier = Modifier.fillMaxWidth(),
//                                value = userDetailsViewModel.user.value.email,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    textColor = Color.Black,
//                                    backgroundColor = LightGray1,
//                                    placeholderColor = Color.White,
//                                    cursorColor = Color.Black,
//                                    focusedLabelColor = Color.Black,
//                                    errorCursorColor = Color.Black,
//                                    errorLabelColor = Color.Red,
//                                    focusedIndicatorColor = Color.Transparent,
//                                    unfocusedIndicatorColor = Color.Transparent,
//                                    unfocusedLabelColor = Orange,
//                                ),
//                                onValueChange = {
//                                    userDetailsViewModel.onEvent(
//                                        context,
//                                        ProfileFormEvent.EmailChanged(it)
//                                    )
//                                },
//                                shape = RoundedCornerShape(30.dp),
//                                label = { Text(text = "Email", color = DarkGray1) }
//                            )


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
                                value = userDetailsViewModel.state.phoneNumber,
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
                                label = { Text(text = "Phone Number", color = DarkGray1) },
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                                )
                            )
                            if (userDetailsViewModel.state.phoneNumberError != null) {
                                Text(
                                    text = userDetailsViewModel.state.phoneNumberError!!,
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
                                value = userDetailsViewModel.state.password,
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
                                label = { Text(text = "Password", color = DarkGray1) },
                                keyboardActions = KeyboardActions(
                                    onNext = { mDatePickerDialog.show() },
                                )
                            )
                            if (userDetailsViewModel.state.passwordError != null) {
                                Text(
                                    text = userDetailsViewModel.state.passwordError!!,
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
                                value = userDetailsViewModel.state.date,
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
                                            contentDescription = "",
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(30.dp),
                                label = { Text(text = "Date Of Birth", color = DarkGray1) }
                            )
                            if (userDetailsViewModel.state.dateError != null) {
                                Text(
                                    text = userDetailsViewModel.state.dateError!!,
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

                        ) {
                        Text(text = "Save Changes", fontSize = 20.sp, color = Color.Black)
                    }
//                    {
//                        Spacer(modifier = Modifier.padding(5.dp))
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(modifier = Modifier.fillMaxWidth(0.31F),text = "First Name", color = Color.Black,style = TextStyle(
//                                fontSize = 16.sp,
//                                textAlign = TextAlign.Center,
//                                letterSpacing = 2.sp
//                            ))
//                            Spacer(modifier = Modifier.padding(5.dp))
//                            OutlinedTextField(
//                                enabled = false,
//                                readOnly = true,
//                                modifier = Modifier.fillMaxWidth(),
//                                value = userDetailsViewModel.user.value.firstName,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    disabledTextColor = Color.Black,
//                                    disabledIndicatorColor= Orange,
//                                    backgroundColor= Color.Transparent
//                                ),
//                                onValueChange = {},)
//                        }
//
//                        Spacer(modifier = Modifier.padding(5.dp))
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(modifier = Modifier.fillMaxWidth(0.34F),text = "Last Name", color = Color.Black,style = TextStyle(
//                                fontSize = 16.sp,
//                                textAlign = TextAlign.Center,
//                                letterSpacing = 2.sp
//                            ),)
//                            OutlinedTextField(
//                                readOnly = true,
//                                enabled = false,
//                                modifier = Modifier.fillMaxWidth(),
//                                value = userDetailsViewModel.user.value.lastName,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    disabledTextColor = Color.Black,
//                                    disabledIndicatorColor= Orange,
//                                    backgroundColor= Color.Transparent
//                                ),
//                                onValueChange = {},)
//                        }
//
//                        Spacer(modifier = Modifier.padding(5.dp))
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(modifier = Modifier.fillMaxWidth(0.34F),text = "Date Of Birth", color = Color.Black,style = TextStyle(
//                                fontSize = 16.sp,
//                                textAlign = TextAlign.Center,
//                                letterSpacing = 2.sp
//                            ),)
//                            OutlinedTextField(
//                                readOnly = true,
//                                enabled = false,
//                                modifier = Modifier.fillMaxWidth(),
//                                value = userDetailsViewModel.user.value.dob,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    disabledTextColor = Color.Black,
//                                    disabledIndicatorColor= Orange,
//                                    backgroundColor= Color.Transparent
//                                ),
//                                onValueChange = {},)
//                        }
//
//                        Spacer(modifier = Modifier.padding(5.dp))
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(modifier = Modifier.fillMaxWidth(0.34F), text = "Email", color = Color.Black,style = TextStyle(
//                                fontSize = 16.sp,
//                                textAlign = TextAlign.Center,
//                                letterSpacing = 2.sp
//                            ),)
//                            OutlinedTextField(
//                                readOnly = true,
//                                enabled = false,
//                                modifier = Modifier.fillMaxWidth(),
//                                value = userDetailsViewModel.user.value.email,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    disabledTextColor = Color.Black,
//                                    disabledIndicatorColor= Orange,
//                                    backgroundColor= Color.Transparent
//                                ),
//                                onValueChange = {},)
//                        }
//                        Spacer(modifier = Modifier.padding(10.dp))
//
//                        OutlinedButton(
//                            onClick = {
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth(0.6f)
//                                .height(40.dp),
//
//                            shape = RoundedCornerShape(50),
//                            colors = ButtonDefaults.buttonColors(
//                                backgroundColor = DarkYellow,
//                                contentColor = Color.White
//                            ),
//
//                            ) {
//                            Text(text = "Edit Profile", fontSize = 20.sp, color = Color.Black)
//                        }
//                    }
                }
            }
        }

        TopAppBar(
            title = { Text(text = "Beer App", color = Color.Black) },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            navigationIcon = {
                IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }

            },
        )
    }
}
