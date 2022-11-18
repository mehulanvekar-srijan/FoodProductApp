package com.experiment.foodproductapp.views

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.EditCalendar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.ForgotPasswordViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPassword(
    navHostControllerLambda: () -> NavHostController,
    forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
) {

    ChangeBarColors(navigationBarColor = Color.White)

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val inputList = remember { mutableStateListOf("", "", "", "") }

    val showEnterEmail = remember { mutableStateOf(true) }
    val showEnterOTP = remember { mutableStateOf(false) }
    val showEnterPasswordTextField = remember { mutableStateOf(false) }

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
                    AnimatedVisibility(visible = showEnterEmail.value) {
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
                    AnimatedVisibility(visible = showEnterEmail.value) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = forgotPasswordViewModel.inputEmail.value,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange = {
                                forgotPasswordViewModel.setEmail(it)
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
                }
                item { //Next Button
                    AnimatedVisibility(visible = showEnterEmail.value) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    val status = forgotPasswordViewModel.isUserRegistered(context)
                                    if (status) {
                                        showEnterOTP.value = true
                                        showEnterEmail.value = false
                                        forgotPasswordViewModel.sendOtp()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            R.string.email_not_registered_string,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
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
                    AnimatedVisibility(visible = showEnterOTP.value) {
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
                }
                item { //Enter OTP TextField
                    AnimatedVisibility(visible = showEnterOTP.value) {
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            for (i in 0..3) {
                                TextField(
                                    value = inputList[i],
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .size(55.dp)
                                        .onFocusEvent {
                                            if (it.hasFocus) Log.d("testKeyB", "onFocusEvent: i=$i")
                                        }
                                        .onKeyEvent {
                                            Log.d(
                                                "testKeyB",
                                                "onKeyEvent: i=$i | key=${it.key} | type=${it.type} | nativeKeyEvent=${it.nativeKeyEvent}"
                                            )

                                            if (it.key == Key.Backspace) {
                                                if (inputList[i].isEmpty() && i > 0) inputList[i - 1] =
                                                    ""
                                                focusManager.moveFocus(FocusDirection.Left)
                                            }
                                            if (it.key == Key.Back) {
                                                navHostControllerLambda().navigateUp()
                                            }

                                            true
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
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
                item { //submit button
                    AnimatedVisibility(visible = showEnterOTP.value) {
                        Button(
                            onClick = {
                                var otp = ""
                                inputList.forEach { otp += it }

                                forgotPasswordViewModel.setOtp(otp)
                                if (forgotPasswordViewModel.verifyOtp()) { //success
                                    showEnterOTP.value = false
                                    showEnterPasswordTextField.value = true
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
                    AnimatedVisibility(visible = showEnterPasswordTextField.value) {
                        Text(
                            text = stringResource(id = R.string.please_enter_your_new_password_string),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = titleFontFamily
                        )
                        Spacer(modifier = Modifier.padding(30.dp))
                    }
                }
                item { //new password TextField
                    AnimatedVisibility(visible = showEnterPasswordTextField.value) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = forgotPasswordViewModel.inputPassword.value,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange = {
                                forgotPasswordViewModel.setPassword(it)
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
                        )
                        Spacer(modifier = Modifier.padding(30.dp))
                    }
                }
                item { //new password TextField
                    AnimatedVisibility(visible = showEnterPasswordTextField.value) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = forgotPasswordViewModel.confirmPassword.value,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange = {
                                forgotPasswordViewModel.setConfirmPassword(it)
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
                        )
                    }
                }
                item { //Set Button
                    AnimatedVisibility(visible = showEnterPasswordTextField.value) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    forgotPasswordViewModel.changePassword(context)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DarkYellow,
                                contentColor = Color.White
                            ),
                        ) {
                            Text(text = stringResource(id = R.string.set_string))
                        }
                    }
                }
            }
        }
    }
}
