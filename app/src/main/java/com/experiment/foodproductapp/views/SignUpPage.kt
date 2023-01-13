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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.actor.route.NavigateObj
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.domain.event.SignInFormEvent
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.stream.AppStream
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.MainViewModel
import com.experiment.foodproductapp.viewmodels.NavigationUIMessages
import com.experiment.foodproductapp.viewmodels.SignUpViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Preview
@Composable
fun preview1() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {

        navHostController
    }
    SignupPage(navHostControllerLambda = navHostControllerLambda)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SignupPage(
    navHostControllerLambda: () -> NavHostController,
    signUpViewModel: SignUpViewModel = koinViewModel(),
    mainViewModel: MainViewModel = koinViewModel()
) {
    ChangeBarColors(navigationBarColor = Color.White)
    val focusManager = LocalFocusManager.current

    val viewRequesterForConfirmPassword = remember { BringIntoViewRequester() }
    val viewRequesterForConfirmDatePicker = remember { BringIntoViewRequester() }

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    val mYear: Int = mCalendar.get(Calendar.YEAR)
    val mMonth: Int = mCalendar.get(Calendar.MONTH)
    val mDay: Int = mCalendar.get(Calendar.DAY_OF_MONTH)

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


    LaunchedEffect(key1 = Unit) {
        signUpViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navHostControllerLambda().navigate(Screen.HomeScreen.routeWithData(signUpViewModel.state.value.email)
                    ){
                        popUpTo(Screen.SignInScreen.route) { inclusive = true }
                    }
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                }
                is ValidationEvent.Failure -> {
                    Toast.makeText(context, "Email already registered", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        mainViewModel.uiMessages.collect {
            when(it) {
                is NavigationUIMessages.NavigateTo -> {
                    signUpViewModel.onEvent(SignupFormEvent.Submit)
                }
                NavigationUIMessages.SkipNavigation -> {}
            }
        }
    }

    Box(
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_yellow_wave),
            contentDescription = "ic_background_image",
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
                contentDescription = "ic_brand_logo"
            )

            Column(
                modifier = Modifier
                    .shadow(30.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(top = 10.dp, start = 28.dp, end = 28.dp, bottom = 10.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.sign_up_string),
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
                            value = signUpViewModel.state.value.firstName,
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
                            shape = RoundedCornerShape(30.dp),
                            isError = signUpViewModel.state.value.firstNameError != null,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.first_name_string),
                                    color = Color.Black
                                )
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (signUpViewModel.state.value.firstNameError != null) {
                            Text(
                                text = signUpViewModel.state.value.firstNameError!!,
                                color = MaterialTheme.colors.error,
                                fontSize = 14.sp, modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = signUpViewModel.state.value.lastName,
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
                            shape = RoundedCornerShape(30.dp),
                            isError = signUpViewModel.state.value.lastNameError != null,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.last_name_string),
                                    color = Color.Black
                                )
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (signUpViewModel.state.value.lastNameError != null) {
                            Text(
                                text = signUpViewModel.state.value.lastNameError!!,
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
                            value = signUpViewModel.state.value.phoneNumber,
                            shape = RoundedCornerShape(30.dp),
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
                            isError = signUpViewModel.state.value.phoneNumberError != null,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            onValueChange = {
                                signUpViewModel.onEvent(SignupFormEvent.PhoneNumberChanged(it))
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.enter_phone_number_string),
                                    color = Color.Black
                                )
                            },
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (signUpViewModel.state.value.phoneNumberError != null) {
                            Text(
                                text = signUpViewModel.state.value.phoneNumberError!!,
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
                            value = signUpViewModel.state.value.email,
                            shape = RoundedCornerShape(30.dp),
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
                            isError = signUpViewModel.state.value.emailError != null,
                            keyboardOptions = KeyboardOptions(
                                //keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next,
                            ),
                            onValueChange = {
                                signUpViewModel.onEvent(SignupFormEvent.EmailChanged(it))
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.enter_email_string),
                                    color = Color.Black
                                )
                            },
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (signUpViewModel.state.value.emailError != null) {
                            Text(
                                text = signUpViewModel.state.value.emailError!!,
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
                            value = signUpViewModel.state.value.password,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange = {
                                signUpViewModel.onEvent(SignupFormEvent.PasswordChanged(it))
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.enter_password_string),
                                    color = Color.Black
                                )
                            },
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
                                    if (signUpViewModel.passwordVisible.value) stringResource(id = R.string.hide_password_string) else stringResource(
                                        id = R.string.show_password_string
                                    )

                                IconButton(onClick = { signUpViewModel.passwordChange() }) {
                                    Icon(imageVector = image, description)
                                }
                            },
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            )
                        )
                        if (signUpViewModel.state.value.passwordError != null) {
                            Text(
                                text = signUpViewModel.state.value.passwordError!!,
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
                            value = signUpViewModel.state.value.repeatedPassword,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange = {
                                signUpViewModel.onEvent(
                                    SignupFormEvent.ConfirmPasswordChanged(
                                        it
                                    )
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.confirm_password_string),
                                    color = Color.Black
                                )
                            },
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
                                    if (signUpViewModel.confirmPasswordVisible.value) stringResource(
                                        id = R.string.hide_password_string
                                    ) else stringResource(id = R.string.show_password_string)

                                IconButton(onClick = {
                                    signUpViewModel.confirmPasswordChange()
                                }) {
                                    Icon(imageVector = image, description)
                                }
                            },
                            keyboardActions = KeyboardActions(
                                onNext = { mDatePickerDialog.show() },
                            )
                        )
                        if (signUpViewModel.state.value.repeatedPasswordError != null) {
                            Text(
                                text = signUpViewModel.state.value.repeatedPasswordError!!,
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
                            value = signUpViewModel.state.value.date,
                            shape = RoundedCornerShape(30.dp),
                            isError = signUpViewModel.state.value.dateError != null,
                            onValueChange = {
                                signUpViewModel.onEvent(
                                    SignupFormEvent.CalenderChanged(it)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.date_of_birth_string),
                                    color = Color.Black
                                )
                            },
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
                                        contentDescription = "ic_edit_dob_bt",
                                    )
                                }
                            },
                        )
                        if (signUpViewModel.state.value.dateError != null) {
                            Text(
                                text = signUpViewModel.state.value.dateError!!,
                                color = MaterialTheme.colors.error,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = {
                        coroutineScope.launch {
                            AppStream.send(NavigateObj(route = Screen.HomeScreen.route))
                            mainViewModel.getNavigationState()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
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
                        text = stringResource(id = R.string.create_account_string),
                        color = Color.Black
                    )
                }

            }
        }
    }
}


