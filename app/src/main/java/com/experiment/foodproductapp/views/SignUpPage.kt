package com.experiment.foodproductapp.views

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.twotone.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.SignUpViewModel
import kotlinx.coroutines.launch
import java.util.*

@Preview
@Composable
fun preview1(){
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {

        navHostController
    }
    SignupPage(navHostControllerLambda =navHostControllerLambda)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SignupPage(
    navHostControllerLambda: () -> NavHostController,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    ChangeBarColors(navigationBarColor = Color.White)
    val focusManager = LocalFocusManager.current
    val softwareKeyboard = LocalSoftwareKeyboardController.current

    val viewRequesterForConfirmPassword = remember { BringIntoViewRequester() }
    val viewRequesterForConfirmDatePicker = remember { BringIntoViewRequester() }

    val coroutineScope = rememberCoroutineScope()

    val state = signUpViewModel.state

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
            signUpViewModel.onEvent(SignupFormEvent.CalenderChanged("$mDayOfMonth/${mMonth + 1}/$mYear"))
        }, mYear, mMonth, mDay
    )


    LaunchedEffect(key1 = context) {
        signUpViewModel.validationEvents.collect { event ->
            when (event) {
                is SignUpViewModel.ValidationEvent.Success -> {
                    signUpViewModel.navigateOnSucces(context, navHostControllerLambda())
                }
            }
        }
    }

    Box(
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_yellow_wave),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        //Main Column
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            //Brand Logo
            Image(
                modifier = Modifier
                    .fillMaxHeight(0.30F),
                painter = painterResource(id = R.drawable.ic_beer_cheers),
                contentDescription = "brand logo"
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(top = 10.dp, start = 28.dp, end = 28.dp, bottom = 10.dp)
                ,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Sign Up",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        letterSpacing = 2.sp,
                        fontFamily = titleFontFamily
                    ),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.80F)
                ) {
                    item {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = state.firstName,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = LightGray1,
                                placeholderColor = Color.White,
                                cursorColor = Color.Black,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.Black,
                                errorCursorColor = Color.Black,
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
                            onValueChange = {
                                signUpViewModel.onEvent(SignupFormEvent.FirstNameChanged(it))
                            },
                            shape= RoundedCornerShape(30.dp),
                            isError = state.firstNameError != null,
                            label = { Text(text = "First Name", color = Color.Black) },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (state.firstNameError != null) {
                            Text(
                                text = state.firstNameError,
                                color = MaterialTheme.colors.error,
                                fontSize = 14.sp, modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = state.lastName,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = LightGray1,
                                placeholderColor = Color.White,
                                cursorColor = Color.Black,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.Black,
                                errorCursorColor = Color.Black,
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
                            onValueChange = {
                                signUpViewModel.onEvent(SignupFormEvent.LastNameChanged(it))
                            },
                            shape= RoundedCornerShape(30.dp),
                            isError = state.lastNameError != null,
                            label = { Text(text = "Last Name", color = Color.Black) },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (state.lastNameError != null) {
                            Text(
                                text = state.lastNameError,
                                color = MaterialTheme.colors.error,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = state.phoneNumber,
                            shape= RoundedCornerShape(30.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = LightGray1,
                                placeholderColor = Color.White,
                                cursorColor = Color.Black,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.Black,
                                errorCursorColor = Color.Black,
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
                            isError = state.phoneNumberError != null,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            onValueChange = {
                                signUpViewModel.onEvent(SignupFormEvent.PhoneNumberChanged(it))
                            },
                            label = { Text(text = "Enter Phone Number", color = Color.Black) },
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (state.phoneNumberError != null) {
                            Text(
                                text = state.phoneNumberError,
                                color = MaterialTheme.colors.error,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusEvent {
                                    if (it.isFocused) {
                                        coroutineScope.launch {
                                            viewRequesterForConfirmPassword.bringIntoView()
                                        }
                                    }
                                },
                            value = state.email,
                            shape= RoundedCornerShape(30.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = LightGray1,
                                placeholderColor = Color.White,
                                cursorColor = Color.Black,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.Black,
                                errorCursorColor = Color.Black,
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
                            isError = state.emailError != null,
                            keyboardOptions = KeyboardOptions(
                                //keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next,
                            )
                            ,
                            onValueChange = {
                                signUpViewModel.onEvent(SignupFormEvent.EmailChanged(it))
                            },
                            label = { Text(text = "Enter Email", color = Color.Black) },
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (state.emailError != null) {
                            Text(
                                text = state.emailError,
                                color = MaterialTheme.colors.error,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = state.password,
                            shape= RoundedCornerShape(30.dp),
                            onValueChange = {
                                signUpViewModel.onEvent(SignupFormEvent.PasswordChanged(it))
                            },
                            label = { Text(text = "Enter password", color = Color.Black) },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = LightGray1,
                                placeholderColor = Color.White,
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                errorIndicatorColor = Color.Transparent,
                                errorCursorColor = Color.Black,
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
                            visualTransformation = if (signUpViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            trailingIcon = {
                                val image = if (signUpViewModel.passwordVisible.value)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff
                                val description =
                                    if (signUpViewModel.passwordVisible.value) "Hide password" else "Show password"

                                IconButton(onClick = { signUpViewModel.passwordchange() }) {
                                    Icon(imageVector = image, description)
                                }
                            },
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (state.passwordError != null) {
                            Text(
                                text = state.passwordError,
                                color = MaterialTheme.colors.error,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .bringIntoViewRequester(viewRequesterForConfirmPassword)
                                .onFocusEvent {
                                    if (it.isFocused) {
                                        coroutineScope.launch {
                                            viewRequesterForConfirmDatePicker.bringIntoView()
                                        }
                                    }
                                },
                            value = state.repeatedPassword,
                            shape= RoundedCornerShape(30.dp),
                            onValueChange = {
                                signUpViewModel.onEvent(
                                    SignupFormEvent.ConfirmPasswordChanged(
                                        it
                                    )
                                )
                            },
                            label = { Text(text = "Confirm password", color = Color.Black) },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = LightGray1,
                                placeholderColor = Color.White,
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                errorCursorColor = Color.Black,
                                errorIndicatorColor = Color.Transparent,
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
                            visualTransformation = if (signUpViewModel.confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            trailingIcon = {
                                val image = if (signUpViewModel.confirmPasswordVisible.value)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff
                                val description =
                                    if (signUpViewModel.confirmPasswordVisible.value) "Hide password" else "Show password"

                                IconButton(onClick = {
                                    signUpViewModel.confirmpasswordchange()
                                }) {
                                    Icon(imageVector = image, description)
                                }
                            },
                            keyboardActions = KeyboardActions(
                                onNext = { mDatePickerDialog.show() },
                            )
                        )
                        if (state.repeatedPasswordError != null) {
                            Text(
                                text = state.repeatedPasswordError,
                                color = MaterialTheme.colors.error,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .bringIntoViewRequester(viewRequesterForConfirmDatePicker),
                            value = state.date,
                            shape= RoundedCornerShape(30.dp),
                            isError = state.dateError != null,
                            onValueChange = {
                                signUpViewModel.onEvent(
                                    SignupFormEvent.CalenderChanged(it)
                                )
                            },
                            label = { Text(text = "Date of Birth", color = Color.Black) },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = LightGray1,
                                placeholderColor = Color.White,
                                cursorColor = Color.Black,
                                errorIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.Black,
                                errorCursorColor = Color.Black,
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
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
                        )
                        if (state.dateError != null) {
                            Text(
                                text = state.dateError,
                                color = MaterialTheme.colors.error,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = {
                        signUpViewModel.onEvent(SignupFormEvent.Submit)
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
                        defaultElevation = 5.dp
                    )
                ) {
                    Text(text = "CREATE ACCOUNT", color = Color.Black)
                }

            }
        }
    }
}


