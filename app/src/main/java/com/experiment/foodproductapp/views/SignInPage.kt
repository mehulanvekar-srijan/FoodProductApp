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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.viewmodels.SignInViewModel


@Composable
fun SignInPage(navHostControllerLambda: () -> NavHostController,signInViewModel: SignInViewModel= viewModel()) {

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }


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
                modifier = Modifier.fillMaxHeight(0.4F).padding(20.dp),
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
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = { Text("Email Address") },
                        placeholder = { Text(text = "Email Address") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    )


                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        trailingIcon = {
                            val image = if (passwordVisibility.value) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            }

                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = "",
                                    tint = Color.Red
                                )
                            }
                        },
                        label = { Text("Password") },
                        //placeholder = { Text(text = "Password") },
                        singleLine = true,
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            // backgroundColor = Color.Blue,
                            //contentColor = Color),
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp),

                        shape = RoundedCornerShape(50)
                    ) {
                        Text(text = "Sign In", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.padding(20.dp))
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
