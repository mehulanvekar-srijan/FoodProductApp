package com.experiment.foodproductapp.views

import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.twotone.EditCalendar
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.domain.event.ForgotPasswordFormEvent
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.ForgotPasswordViewModel
import com.razorpay.OTP
import kotlinx.coroutines.launch

var count = 1

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun ForgotPassword(
    forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
) {

    ChangeBarColors(navigationBarColor = Color.White)

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val viewRequesterForSetButton = remember { BringIntoViewRequester() }


    val inputList = remember { mutableStateListOf("", "", "", "") }

    Box(modifier = Modifier.fillMaxSize()) {

        //Background Image
        Image(
            painter = painterResource(id = R.drawable.background_yellow_wave),
            contentDescription = "ic_background_image",
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
                contentDescription = "ic_brand_logo"
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                item { //Enter email Text
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterEmail.value) {
                        Text(
                            text = stringResource(id = R.string.please_enter_your_registered_email_string),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = titleFontFamily
                        )
                        Spacer(modifier = Modifier.padding(30.dp))
                    }
                }
                item { //Enter email TextField
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterEmail.value) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = forgotPasswordViewModel.state.email,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange = {
                                forgotPasswordViewModel.onEvent(ForgotPasswordFormEvent.EmailChanged(it),context)
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.email_string),
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
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    Spacer(modifier = Modifier.padding(15.dp))
                }
                item { //Next Button
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterEmail.value) {
                        Button(
                            onClick = {
                                forgotPasswordViewModel.onEvent(ForgotPasswordFormEvent.Next,context)
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DarkYellow,
                                contentColor = Color.White
                            ),
                        ) {
                            Text(text = stringResource(id = R.string.next_string))
                        }
                    }
                }



                item { //Enter OTP Text
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterOTP.value) {
                        Text(
                            text = stringResource(id = R.string.enter_4_digit_otp_sent_via_email_string),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = titleFontFamily
                        )
                        Spacer(modifier = Modifier.padding(30.dp))
                    }
                    Spacer(modifier = Modifier.padding(15.dp))
                }
                item { //Enter OTP TextField
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterOTP.value) {
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            for (i in 0..3) {
                                TextField(
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone,
                                    ),
                                    value = inputList[i],
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .size(55.dp),
                                    onValueChange = {
                                        inputList[i] = it
                                        if (inputList[i].isNotEmpty()) focusManager.moveFocus(
                                            FocusDirection.Right
                                        )
                                        if (i == 3 && inputList[i].isNotEmpty()) focusManager.clearFocus()
                                    },
                                    textStyle = TextStyle().copy(
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 20.sp
                                    ),
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.Transparent,
                                        textColor = LightPink,
                                        focusedIndicatorColor = DarkYellow,
                                        unfocusedIndicatorColor = Orange,
                                        cursorColor = DarkYellow,
                                        errorCursorColor = Color.Red,
                                    ),
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                item { //submit button
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterOTP.value) {
                        Button(
                            onClick = {
                                var otp = ""
                                inputList.forEach { otp += it }

                                forgotPasswordViewModel.setOtp(otp)
                                if (forgotPasswordViewModel.verifyOtp()) { //success
                                    forgotPasswordViewModel.showEnterOTP.value = false
                                    forgotPasswordViewModel.showEnterPasswordTextField.value = true
                                } else { //failure
                                    Toast.makeText(
                                        context,
                                        R.string.incorrect_otp_string,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DarkYellow,
                                contentColor = Color.White
                            ),
                        ) {
                            Text(text = stringResource(id = R.string.submit_string))
                        }
                    }
                }



                item { //new password Text
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterPasswordTextField.value) {
                        Text(
                            text = stringResource(id = R.string.please_enter_your_new_password_string),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = titleFontFamily,
                        )
                        Spacer(modifier = Modifier.padding(30.dp))
                    }
                }
                item { //new password TextField
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterPasswordTextField.value) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusEvent {
                                    if (it.isFocused) {
                                        coroutineScope.launch {
                                            viewRequesterForSetButton.bringIntoView()
                                        }
                                    }
                                },
                            value = forgotPasswordViewModel.state.password,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange = {
                                forgotPasswordViewModel.onEvent(ForgotPasswordFormEvent.PasswordChanged(it),context)
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.enter_new_password_string),
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
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
                            visualTransformation = if (forgotPasswordViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (forgotPasswordViewModel.passwordVisible.value)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff
                                val description =
                                    if (forgotPasswordViewModel.passwordVisible.value) stringResource(id = R.string.hide_password_string) else stringResource(
                                        id = R.string.show_password_string
                                    )

                                IconButton(onClick = { forgotPasswordViewModel.passwordVisibilityChange() }) {
                                    Icon(imageVector = image, description)
                                }
                            },
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                item { //confirm password TextField
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterPasswordTextField.value) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = forgotPasswordViewModel.state.confirmPassword,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange = {
                                forgotPasswordViewModel.onEvent(ForgotPasswordFormEvent.ConfirmPasswordChanged(it),context)
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
                                errorLabelColor = Color.Red,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Orange,
                            ),
                            visualTransformation = if (forgotPasswordViewModel.confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (forgotPasswordViewModel.confirmPasswordVisible.value)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff
                                val description =
                                    if (forgotPasswordViewModel.confirmPasswordVisible.value) stringResource(id = R.string.hide_password_string) else stringResource(
                                        id = R.string.show_password_string
                                    )

                                IconButton(onClick = { forgotPasswordViewModel.confirmPasswordVisibilityChange() }) {
                                    Icon(imageVector = image, description)
                                }
                            },
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                item { //Set Button
                    AnimatedVisibility(visible = forgotPasswordViewModel.showEnterPasswordTextField.value) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    forgotPasswordViewModel.onEvent(ForgotPasswordFormEvent.Set, context)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DarkYellow,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.bringIntoViewRequester(viewRequesterForSetButton)
                        ) {
                            Text(text = stringResource(id = R.string.set_string))
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OtpTextField(
    inputList: SnapshotStateList<String>,
    i: Int,
    focusManager: FocusManager
) {
    TextField(
        value = inputList[i],
        modifier = Modifier
            .padding(2.dp)
            .size(55.dp)
            .onKeyEvent {
                if (it.key == Key.Backspace) {
                    if (inputList[i].isEmpty() && i > 0) inputList[i - 1] =
                        ""
                    focusManager.moveFocus(FocusDirection.Left)
                }
                false
            },
        onValueChange = {
            inputList[i] = it
            if (inputList[i].isNotEmpty()) focusManager.moveFocus(
                FocusDirection.Right
            )
            if (i == 3 && inputList[i].isNotEmpty()) focusManager.clearFocus()
        },
        textStyle = TextStyle().copy(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            textColor = LightPink,
            focusedIndicatorColor = DarkYellow,
            unfocusedIndicatorColor = Orange,
            cursorColor = DarkYellow,
            errorCursorColor = Color.Red,
        ),
    )
}