package com.experiment.foodproductapp.views



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip


import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.domain.event.SignInFormEvent
import com.experiment.foodproductapp.ui.theme.DarkYellow
import com.experiment.foodproductapp.ui.theme.Orange
import com.experiment.foodproductapp.viewmodels.SignInViewModel

@Preview
@Composable
fun preview() {
    val navHostController = rememberNavController()
    val navHostControllerLambda : () -> NavHostController = {

        navHostController }
    SignInPage(navHostControllerLambda = navHostControllerLambda)
}

@Composable
fun SignInPage(
    navHostControllerLambda: () -> NavHostController,
    signInViewModel: SignInViewModel = viewModel()
) {

    val context = LocalContext.current
    val state = signInViewModel.state

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
                    text = "Sign In",
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        letterSpacing = 2.sp
                     ),
                )
            }

            item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Spacer(modifier = Modifier.padding(5.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            TextField(

                                value = state.email,
                                shape = RoundedCornerShape(50),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    backgroundColor = Color.Transparent,
                                    placeholderColor = Color.White,
                                    errorTrailingIconColor = Orange,
                                    cursorColor = Orange,
                                    focusedLabelColor = Orange,
                                    errorCursorColor = Orange,
                                    errorLabelColor = Orange,
                                    focusedIndicatorColor = Orange,
                                    unfocusedIndicatorColor = Orange,
                                    unfocusedLabelColor = Orange,
                                    ),
                                onValueChange = {
                                    signInViewModel.onEvent(
                                        context,
                                        SignInFormEvent.EmailChanged(it),
                                        navHostControllerLambda()
                                    )
                                },
                                label = { Text("Email Address", color = Color.Black) },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                            )
                            if (state.emailError != null) {
                                Text(
                                    text = state.emailError,
                                    color = Color.Red,
                                    fontSize = 16.sp,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                            Spacer(modifier = Modifier.padding(top = 8.dp))
                            TextField(
                                value = state.password,
                                shape = RoundedCornerShape(50),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    backgroundColor = Color.Transparent,
                                    placeholderColor = Color.White,
                                    errorTrailingIconColor = Orange,
                                    cursorColor = Orange,
                                    focusedLabelColor = Orange,
                                    errorCursorColor = Orange,
                                    errorLabelColor = Orange,
                                    focusedIndicatorColor = Orange,
                                    unfocusedIndicatorColor = Orange,
                                    unfocusedLabelColor = Orange,
                                ),
                                onValueChange = {
                                    signInViewModel.onEvent(
                                        context,
                                        SignInFormEvent.PasswordChanged(it),
                                        navHostControllerLambda()
                                    )
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
                                            contentDescription = "",
                                            tint = Orange
                                        )
                                    }
                                },
                                label = { Text("Password", color = Color.Black) },
                                singleLine = true,
                                visualTransformation = if (signInViewModel.passwordVisibility.value) VisualTransformation.None
                                else PasswordVisualTransformation(),
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                            )
                            if (state.passwordError != null) {
                                Text(
                                    text = state.passwordError,
                                    color = Color.Red,
                                    fontSize = 16.sp,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }

                            Spacer(modifier = Modifier.padding(10.dp))
                            OutlinedButton(
                                onClick = {
                                    signInViewModel.onEvent(
                                        context,
                                        SignInFormEvent.Login,
                                        navHostControllerLambda()
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(50.dp),

                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = DarkYellow,
                                    contentColor = Color.White
                                ),

                            ) {
                                Text(text = "Sign In", fontSize = 22.sp, color = Color.Black)
                            }

                            Spacer(modifier = Modifier.padding(15.dp))
                            Text(
                                text = "Create An Account",
                                color = DarkYellow,
                                modifier = Modifier.clickable(onClick = {
                                    signInViewModel.navigate(
                                        navHostController = navHostControllerLambda(),
                                        route = Screen.SignUpScreen.route
                                    )
                                }),
                                style = TextStyle(fontSize = 20.sp,),
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text(
                                text = "Forgot Password?",
                                color = DarkYellow,
                                modifier = Modifier.clickable(onClick = {
                                    signInViewModel.navigate(
                                        navHostController = navHostControllerLambda(),
                                        route = Screen.ForgotPassword.route
                                    )
                                }),
                                style = TextStyle(fontSize = 20.sp),
                            )
                        }

                    }
                }
            }
        }
    }
}


