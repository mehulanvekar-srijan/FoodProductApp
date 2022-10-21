package com.experiment.foodproductapp.views


import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
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

    Box {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(3.dp),
            painter = painterResource(R.drawable.background_image_beer),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(Color.Gray, blendMode = BlendMode.Darken)
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Image(
                painter = painterResource(id = R.drawable.ic_beer_cheers),
                contentDescription = "ic_burger_logo",
                modifier = Modifier
                    .fillMaxSize(.3f)
                    .fillMaxWidth(),

                alignment = Alignment.BottomCenter,
                contentScale = ContentScale.Fit,

                )

            Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(.2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign In",
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        letterSpacing = 2.sp
                    ),

                )
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                //verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
            ) {

                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Spacer(modifier = Modifier.padding(5.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            TextField(
                                value = state.email,
                                shape = RoundedCornerShape(50),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.White,
                                    backgroundColor = Color.Transparent,
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
                                onValueChange = {
                                    signInViewModel.OnEvent(
                                        context,
                                        SignInFormEvent.EmailChanged(it),
                                        navHostControllerLambda()
                                    )
                                },
                                label = { Text("Email Address", color = Color.White) },
                                isError = state.emailError != null,
                                //placeholder = { Text(text = "Email Address") },
                                singleLine = true,
                                modifier = Modifier

                                    .fillMaxWidth(0.8f)

                            )
                            if (state.emailError != null) {
                                Text(
                                    text = state.emailError,
                                    color = Color.Cyan,
                                    fontSize = 16.sp,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                            Spacer(modifier = Modifier.padding(top = 8.dp))
                            TextField(
                                value = state.password,
                                shape = RoundedCornerShape(50),
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
                                onValueChange = {
                                    signInViewModel.OnEvent(
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
                                            tint = Color.White
                                        )
                                    }
                                },
                                label = { Text("Password", color = Color.White) },
                                singleLine = true,
                                visualTransformation = if (signInViewModel.passwordVisibility.value) VisualTransformation.None
                                else PasswordVisualTransformation(),
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                            )
                            if (state.passwordError != null) {
                                Text(
                                    text = state.passwordError,
                                    color = Color.Cyan,
                                    fontSize = 16.sp,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }

                            Spacer(modifier = Modifier.padding(10.dp))
                            OutlinedButton(
                                onClick = {
                                    signInViewModel.OnEvent(
                                        context,
                                        SignInFormEvent.Login,
                                        navHostControllerLambda()
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(50.dp),

                                shape = RoundedCornerShape(50),
                                border = BorderStroke(1.dp, Color.White),

                                colors = ButtonDefaults.buttonColors(Color.Transparent)

                            ) {
                                Text(text = "Sign In", fontSize = 22.sp, color = Color.White)
                            }

                            Spacer(modifier = Modifier.padding(15.dp))
                            Text(
                                text = "Create An Account",
                                color = Color.White,
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
                                color = Color.White,
                                modifier = Modifier.clickable(onClick = {
                                    signInViewModel.navigate(
                                        navHostController = navHostControllerLambda(),
                                        route = Screen.ForgotPassword.route
                                    )
                                }),
                                style = TextStyle(fontSize = 20.sp,),
                            )
                        }

                    }
                }
            }
        }
    }
}

