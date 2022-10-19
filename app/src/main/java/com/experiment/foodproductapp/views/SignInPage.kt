package com.experiment.foodproductapp.views


import android.graphics.Paint
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

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
import com.experiment.foodproductapp.domain.event.SignInFormEvent
import com.experiment.foodproductapp.viewmodels.SignInViewModel


@Composable
fun SignInPage(
    navHostControllerLambda: () -> NavHostController,
    signInViewModel: SignInViewModel = viewModel()
) {

    val context = LocalContext.current
    val state = signInViewModel.state

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {

        item {
            Image(
                painter = painterResource(id = R.drawable.ic_beer_cheers),
                contentDescription = "ic_burger_logo",
                modifier = Modifier
                    .fillMaxHeight(0.4F)
                    .padding(20.dp),
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Fit,
            )
        }

        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Sign In",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        letterSpacing = 2.sp
                    ),
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(
                        value = state.email,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            unfocusedLabelColor = Color.Blue
                        ),
                        onValueChange = { signInViewModel.OnEvent(context,SignInFormEvent.EmailChanged(it),navHostControllerLambda())},
                        label = { Text("Email Address") },
                        isError = state.emailError != null,
                        placeholder = { Text(text = "Email Address") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)

                    )
                    if (state.emailError != null) {
                        Text(
                            text = state.emailError,
                            color = MaterialTheme.colors.error,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    TextField(
                        value = state.password,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            unfocusedLabelColor = Color.Blue
                        ),
                        onValueChange = { signInViewModel.OnEvent(context,SignInFormEvent.PasswordChanged(it),navHostControllerLambda()) },
                        trailingIcon = {
                            val image = if (signInViewModel.passwordVisibility.value) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            }

                            IconButton(onClick = {
                                signInViewModel.passwordVisibility.value = !signInViewModel.passwordVisibility.value
                            }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = "",
                                    tint = Color.Red
                                )
                            }
                        },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = if (signInViewModel.passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    )
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError,
                            color = MaterialTheme.colors.error,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                                  signInViewModel.OnEvent(context,SignInFormEvent.Login,navHostControllerLambda())
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp),

                        shape = RoundedCornerShape(50)
                    ) {
                        Text(text = "Sign In", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.padding(15.dp))
                    Text(
                        text = "Create An Account",
                        color = Color.Blue,
                        modifier = Modifier.clickable(onClick = {
                            signInViewModel.navigate(navHostControllerLambda())
                        }),
                        style = TextStyle(
                            fontSize = 15.sp,
                        ),
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                }

            }
        }
    }
}
