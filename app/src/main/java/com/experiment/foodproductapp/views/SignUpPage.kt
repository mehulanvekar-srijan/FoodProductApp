package com.experiment.foodproductapp.views

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.twotone.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.ui.theme.ChangeBarColors
import com.experiment.foodproductapp.ui.theme.DarkGray
import com.experiment.foodproductapp.ui.theme.DarkYellow
import com.experiment.foodproductapp.ui.theme.LightGray
import com.experiment.foodproductapp.viewmodels.SignUpViewModel
import java.util.*

@Composable
fun sSignupPage(
    navHostControllerLambda: () -> NavHostController,
    signUpViewModel: SignUpViewModel = viewModel()
) {
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
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background_yellow_wave),
            contentDescription = "background_image",
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.fillMaxSize(.30f),
                    painter = painterResource(id = R.drawable.ic_beer_cheers),
                    contentDescription = ""
                )
            }

            Text(
                text = "REGISTER",
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Left,
                fontSize = 30.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {

                item {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.firstName,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            errorCursorColor = Color.White,
                            errorBorderColor = Color.White,
                            errorLabelColor = Color.Cyan,
                        ),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.FirstNameChanged(it))
                        },
                        isError = state.firstNameError != null,
                        label = { Text(text = "First Name", color = Color.White) })
                    if (state.firstNameError != null) {
                        Text(
                            text = state.firstNameError,
                            color = Color.Cyan,
                            fontSize = 14.sp, modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.lastName,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            errorCursorColor = Color.Cyan,
                            errorBorderColor = Color.White,
                            errorLabelColor = Color.White,
                            focusedLabelColor = Color.White
                        ),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.LastNameChanged(it))
                        },
                        isError = state.lastNameError != null,
                        label = { Text(text = "Last Name", color = Color.White) })
                    if (state.lastNameError != null) {
                        Text(
                            text = state.lastNameError,
                            color = Color.Cyan,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }


                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = state.date,
                        isError = state.dateError != null,
                        onValueChange = { signUpViewModel.onEvent(SignupFormEvent.CalenderChanged(it)) },
                        label = { Text(text = "Date of Birth", color = Color.White) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            errorLeadingIconColor = Color.Cyan,
                            focusedLabelColor = Color.White,
                            errorCursorColor = Color.White,
                            errorBorderColor = Color.White,
                            errorLabelColor = Color.Cyan,
                        ),
                        leadingIcon = {
                            IconButton(onClick = { mDatePickerDialog.show() }) {
                                Icon(
                                    imageVector = Icons.TwoTone.EditCalendar,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        },
                    )
                    if (state.dateError != null) {
                        Text(
                            text = state.dateError,
                            color = Color.Cyan,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.phoneNumber,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            errorCursorColor = Color.White,
                            errorBorderColor = Color.White,
                            errorLabelColor = Color.Cyan,
                        ),
                        isError = state.phoneNumberError != null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.PhoneNumberChanged(it))
                        },
                        label = { Text(text = "Enter Phone Number", color = Color.White) })
                    if (state.phoneNumberError != null) {
                        Text(
                            text = state.phoneNumberError,
                            color = Color.Cyan,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            errorCursorColor = Color.White,
                            errorBorderColor = Color.White,
                            errorLabelColor = Color.Cyan,
                        ),
                        isError = state.emailError != null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.EmailChanged(it))
                        },
                        label = { Text(text = "Enter Email", color = Color.White) })
                    if (state.emailError != null) {
                        Text(
                            text = state.emailError,
                            color = Color.Cyan,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        shape = RoundedCornerShape(20.dp),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.PasswordChanged(it))
                        },
                        label = { Text(text = "Enter password", color = Color.White) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            cursorColor = Color.White,
                            errorTrailingIconColor = Color.Cyan,
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            errorCursorColor = Color.White,
                            errorBorderColor = Color.White,
                            errorLabelColor = Color.Cyan,
                        ),
                        visualTransformation = if (signUpViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (signUpViewModel.passwordVisible.value)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff
                            val description =
                                if (signUpViewModel.passwordVisible.value) "Hide password" else "Show password"

                            IconButton(onClick = { signUpViewModel.passwordchange() }) {
                                Icon(imageVector = image, description, tint = Color.White)
                            }
                        })
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError,
                            color = Color.Cyan,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.repeatedPassword,
                        shape = RoundedCornerShape(20.dp),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.ConfirmPasswordChanged(it))
                        },
                        label = { Text(text = "Confirm password", color = Color.White) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            errorTrailingIconColor = Color.Cyan,
                            cursorColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            errorCursorColor = Color.White,
                            errorBorderColor = Color.White,
                            errorLabelColor = Color.Cyan,
                        ),
                        visualTransformation = if (signUpViewModel.confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (signUpViewModel.confirmPasswordVisible.value)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff
                            val description =
                                if (signUpViewModel.confirmPasswordVisible.value) "Hide password" else "Show password"

                            IconButton(onClick = {
                                signUpViewModel.confirmpasswordchange()
                            }) {
                                Icon(imageVector = image, description, tint = Color.White)
                            }
                        })
                    if (state.repeatedPasswordError != null) {
                        Text(
                            text = state.repeatedPasswordError,
                            color = Color.Cyan,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedButton(
                            shape = RoundedCornerShape(40.dp),
                            border = BorderStroke(1.dp, Color.White),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent
                            ), onClick = {
                                signUpViewModel.onEvent(SignupFormEvent.Submit)
                            }) {
                            Text(text = "CREATE ACCOUNT", color = Color.White)

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SignupPage(
    navHostControllerLambda: () -> NavHostController,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    ChangeBarColors(navigationBarColor = Color.White)

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
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        Image(
            painter = painterResource(id = R.drawable.background_yellow_wave),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        //Main Column
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            //Brand Logo
            Image(
                modifier = Modifier
                    .fillMaxHeight(0.40F),
                painter = painterResource(id = R.drawable.ic_beer_cheers),
                contentDescription = "brand logo"
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                item {
                    Text(
                        text = "REGISTER",
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Left,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }

                item {

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.firstName,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            errorCursorColor = Color.Black,
                            errorLabelColor = Color.Cyan,

                            focusedIndicatorColor = DarkGray,
                            unfocusedIndicatorColor = LightGray,

                            focusedLabelColor = DarkGray,
                            unfocusedLabelColor = LightGray,
                        ),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.FirstNameChanged(it))
                        },
                        isError = state.firstNameError != null,
                        label = { Text(text = "First Name") }
                    )
                    if (state.firstNameError != null) {
                        Text(
                            text = state.firstNameError,
                            color = Color.Cyan,
                            fontSize = 14.sp, modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.lastName,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            errorCursorColor = Color.Black,
                            errorLabelColor = Color.Cyan,

                            focusedIndicatorColor = DarkGray,
                            unfocusedIndicatorColor = LightGray,

                            focusedLabelColor = DarkGray,
                            unfocusedLabelColor = LightGray,
                        ),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.LastNameChanged(it))
                        },
                        isError = state.lastNameError != null,
                        label = { Text(text = "Last Name") })
                    if (state.lastNameError != null) {
                        Text(
                            text = state.lastNameError,
                            color = Color.Cyan,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }


                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = state.date,
                        isError = state.dateError != null,
                        onValueChange = { signUpViewModel.onEvent(SignupFormEvent.CalenderChanged(it)) },
                        label = { Text(text = "Date of Birth")},
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            errorCursorColor = Color.Black,
                            errorLabelColor = Color.Cyan,

                            focusedIndicatorColor = DarkGray,
                            unfocusedIndicatorColor = LightGray,

                            focusedLabelColor = DarkGray,
                            unfocusedLabelColor = LightGray,
                        ),
                        leadingIcon = {
                            IconButton(onClick = { mDatePickerDialog.show() }) {
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
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.phoneNumber,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            errorCursorColor = Color.Black,
                            errorLabelColor = Color.Cyan,

                            focusedIndicatorColor = DarkGray,
                            unfocusedIndicatorColor = LightGray,

                            focusedLabelColor = DarkGray,
                            unfocusedLabelColor = LightGray,
                        ),
                        isError = state.phoneNumberError != null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.PhoneNumberChanged(it))
                        },
                        label = { Text(text = "Enter Phone Number") })
                    if (state.phoneNumberError != null) {
                        Text(
                            text = state.phoneNumberError,
                            color = Color.Cyan,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            errorCursorColor = Color.Black,
                            errorLabelColor = Color.Cyan,

                            focusedIndicatorColor = DarkGray,
                            unfocusedIndicatorColor = LightGray,

                            focusedLabelColor = DarkGray,
                            unfocusedLabelColor = LightGray,
                        ),
                        isError = state.emailError != null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.EmailChanged(it))
                        },
                        label = { Text(text = "Enter Email") })
                    if (state.emailError != null) {
                        Text(
                            text = state.emailError,
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        shape = RoundedCornerShape(20.dp),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.PasswordChanged(it))
                        },
                        label = { Text(text = "Enter password") },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            errorCursorColor = Color.Black,
                            errorLabelColor = Color.Cyan,

                            focusedIndicatorColor = DarkGray,
                            unfocusedIndicatorColor = LightGray,

                            focusedLabelColor = DarkGray,
                            unfocusedLabelColor = LightGray,
                        ),
                        visualTransformation = if (signUpViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (signUpViewModel.passwordVisible.value)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff
                            val description =
                                if (signUpViewModel.passwordVisible.value) "Hide password" else "Show password"

                            IconButton(onClick = { signUpViewModel.passwordchange() }) {
                                Icon(imageVector = image, description)
                            }
                        })
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError,
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.repeatedPassword,
                        shape = RoundedCornerShape(20.dp),
                        onValueChange = {
                            signUpViewModel.onEvent(SignupFormEvent.ConfirmPasswordChanged(it))
                        },
                        label = { Text(text = "Confirm password") },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            errorCursorColor = Color.Black,
                            errorLabelColor = Color.Cyan,

                            focusedIndicatorColor = DarkGray,
                            unfocusedIndicatorColor = LightGray,

                            focusedLabelColor = DarkGray,
                            unfocusedLabelColor = LightGray,
                        ),
                        visualTransformation = if (signUpViewModel.confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                        })
                    if (state.repeatedPasswordError != null) {
                        Text(
                            text = state.repeatedPasswordError,
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedButton(
                            border = BorderStroke(1.dp, DarkYellow),
                            onClick = {
                                signUpViewModel.onEvent(SignupFormEvent.Submit)
                            }) {
                            Text(text = "CREATE ACCOUNT", color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

