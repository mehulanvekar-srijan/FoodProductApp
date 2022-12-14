package com.experiment.foodproductapp.views


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection


import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.domain.event.SignInFormEvent
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.SignInViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInPage(
    navHostControllerLambda: () -> NavHostController,
    signInViewModel: SignInViewModel = koinViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        signInViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navHostControllerLambda().navigate(
                        Screen.HomeScreen.routeWithData(
                            signInViewModel.state.value.email
                        )
                    ) {
                        popUpTo(Screen.SignInScreen.route) { inclusive = true }
                    }
                }
                is ValidationEvent.Failure -> {
                    if (signInViewModel.error.value) {
                        Toast.makeText(context, "Email not Registered", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Incorrect Email or Password", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    ChangeBarColors(navigationBarColor = Color.White)
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
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

            Column(
                modifier = Modifier
                    .shadow(30.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in_string),
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        letterSpacing = 2.sp,
                        fontFamily = titleFontFamily
                    ),
                )
                Spacer(modifier = Modifier.padding(10.dp))
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        TextField(

                            value = signInViewModel.state.value.email,
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
                                signInViewModel.onEvent(SignInFormEvent.EmailChanged(it))
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            ),
                            label = {
                                Text(
                                    text = stringResource(id = R.string.email_address_string),
                                    color = Color.Black
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        )
                        if (signInViewModel.state.value.emailError != null) {
                            Text(
                                text = signInViewModel.state.value.emailError!!,
                                color = Color.Red,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                textAlign = TextAlign.End
                            )
                        }
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        TextField(
                            value = signInViewModel.state.value.password,
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
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            ),
                            onValueChange = {
                                signInViewModel.onEvent(SignInFormEvent.PasswordChanged(it))
                            },
                            trailingIcon = {
                                val image = if (signInViewModel.passwordVisibility.value) {
                                    Icons.Filled.Visibility
                                } else {
                                    Icons.Filled.VisibilityOff
                                }

                                IconButton(onClick = {
                                    signInViewModel.passwordVisibility.value =
                                        !signInViewModel.passwordVisibility.value
                                }) {
                                    Icon(
                                        imageVector = image,
                                        contentDescription = "ic_password_visibility_bt",
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.password_string),
                                    color = Color.Black
                                )
                            },
                            singleLine = true,
                            visualTransformation = if (signInViewModel.passwordVisibility.value) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        )
                        if (signInViewModel.state.value.passwordError != null) {
                            Text(
                                text = signInViewModel.state.value.passwordError!!,
                                color = Color.Red,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                textAlign = TextAlign.End
                            )
                        }

                        Spacer(modifier = Modifier.padding(10.dp))
                        OutlinedButton(
                            onClick = {
                                signInViewModel.onEvent(SignInFormEvent.Login)
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(50.dp),
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
                                text = stringResource(id = R.string.sign_in_string),
                                fontSize = 22.sp,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.padding(15.dp))
                        Text(
                            text = stringResource(id = R.string.create_an_account_string),
                            color = DarkYellow,
                            modifier = Modifier.clickable(onClick = {
                                navHostControllerLambda().navigate(Screen.SignUpScreen.route)
                            }),
                            style = TextStyle(fontSize = 20.sp),
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = stringResource(id = R.string.forgot_password_string),
                            color = DarkYellow,
                            modifier = Modifier.clickable(onClick = {
                                navHostControllerLambda().navigate(Screen.ForgotPassword.route)
                            }),
                            style = TextStyle(fontSize = 20.sp),
                        )
                    }
                }
            }
        }
    }
}


