package com.experiment.foodproductapp.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.domain.event.SignInFormEvent
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.ui.theme.DarkGray1
import com.experiment.foodproductapp.ui.theme.DarkYellow
import com.experiment.foodproductapp.ui.theme.LightGray1
import com.experiment.foodproductapp.ui.theme.Orange
import com.experiment.foodproductapp.viewmodels.UserDetailsViewModel

@Composable
@Preview
fun show()
{
    val string ="Sahil"
    UserDetails(email = string)
}
@Composable
fun UserDetails(
    email : String?,
    userDetailsViewModel: UserDetailsViewModel = viewModel(),
) {
    if (email != null) {

        val context = LocalContext.current

        LaunchedEffect(key1 = Unit) {
            userDetailsViewModel.execute(context, email)
        }
        Box(modifier = Modifier.fillMaxSize()
        ) {
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

                Image(
                    modifier = Modifier
                        .fillMaxHeight(0.25F),
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile"
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                        .background(Color.White)
                        .padding(10.dp),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Profile",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            letterSpacing = 2.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Spacer(modifier = Modifier.padding(10.dp))
                        TextField(
                            readOnly = userDetailsViewModel.visibilityBt.value,
                            modifier = Modifier.fillMaxWidth(),
                            value = userDetailsViewModel.user.value.firstName,
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
                                            userDetailsViewModel.state.firstName
                            },
                            shape= RoundedCornerShape(30.dp),
                            label = { Text(text = "First Name", color = DarkGray1) }
                        )

                        Spacer(modifier = Modifier.padding(10.dp))
                        TextField(
                            readOnly = userDetailsViewModel.visibilityBt.value,
                            modifier = Modifier.fillMaxWidth(),
                            value = userDetailsViewModel.user.value.lastName,
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
                                            userDetailsViewModel.state.lastName
                            },
                            shape= RoundedCornerShape(30.dp),
                            label = { Text(text = "Last Name", color = DarkGray1) }
                        )

                        Spacer(modifier = Modifier.padding(10.dp))
                        TextField(
                            readOnly = userDetailsViewModel.visibilityBt.value,
                            modifier = Modifier.fillMaxWidth(),
                            value = userDetailsViewModel.user.value.dob,
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
                                userDetailsViewModel.state.dob
                            },
                            shape= RoundedCornerShape(30.dp),
                            label = { Text(text = "Date Of Birth", color = DarkGray1) }
                        )

                        Spacer(modifier = Modifier.padding(10.dp))
                        TextField(
                            readOnly = userDetailsViewModel.visibilityBt.value,
                            modifier = Modifier.fillMaxWidth(),
                            value = userDetailsViewModel.user.value.email,
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
                                userDetailsViewModel.state.email
                            },
                            shape= RoundedCornerShape(30.dp),
                            label = { Text(text = "Email", color = DarkGray1) }
                        )

                        Spacer(modifier = Modifier.padding(10.dp))
                        TextField(
                            readOnly = userDetailsViewModel.visibilityBt.value,
                            modifier = Modifier.fillMaxWidth(),
                            value = userDetailsViewModel.user.value.phoneNumber,
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
                                userDetailsViewModel.state.phoneNumber
                            },
                            shape= RoundedCornerShape(30.dp),
                            label = { Text(text = "Phone Number", color = DarkGray1) }
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
//
                        OutlinedButton(
                            onClick = {
                                      userDetailsViewModel.edit()
                                Log.d("userdetails", userDetailsViewModel.visibilityBt.toString())
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .height(40.dp),

                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DarkYellow,
                                contentColor = Color.White
                            ),

                            ) {
                            Text(text = "Edit Profile", fontSize = 20.sp, color = Color.Black)
                        }
                    }
//                    {
//                        Spacer(modifier = Modifier.padding(5.dp))
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(modifier = Modifier.fillMaxWidth(0.31F),text = "First Name", color = Color.Black,style = TextStyle(
//                                fontSize = 16.sp,
//                                textAlign = TextAlign.Center,
//                                letterSpacing = 2.sp
//                            ))
//                            Spacer(modifier = Modifier.padding(5.dp))
//                            OutlinedTextField(
//                                enabled = false,
//                                readOnly = true,
//                                modifier = Modifier.fillMaxWidth(),
//                                value = userDetailsViewModel.user.value.firstName,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    disabledTextColor = Color.Black,
//                                    disabledIndicatorColor= Orange,
//                                    backgroundColor= Color.Transparent
//                                ),
//                                onValueChange = {},)
//                        }
//
//                        Spacer(modifier = Modifier.padding(5.dp))
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(modifier = Modifier.fillMaxWidth(0.34F),text = "Last Name", color = Color.Black,style = TextStyle(
//                                fontSize = 16.sp,
//                                textAlign = TextAlign.Center,
//                                letterSpacing = 2.sp
//                            ),)
//                            OutlinedTextField(
//                                readOnly = true,
//                                enabled = false,
//                                modifier = Modifier.fillMaxWidth(),
//                                value = userDetailsViewModel.user.value.lastName,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    disabledTextColor = Color.Black,
//                                    disabledIndicatorColor= Orange,
//                                    backgroundColor= Color.Transparent
//                                ),
//                                onValueChange = {},)
//                        }
//
//                        Spacer(modifier = Modifier.padding(5.dp))
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(modifier = Modifier.fillMaxWidth(0.34F),text = "Date Of Birth", color = Color.Black,style = TextStyle(
//                                fontSize = 16.sp,
//                                textAlign = TextAlign.Center,
//                                letterSpacing = 2.sp
//                            ),)
//                            OutlinedTextField(
//                                readOnly = true,
//                                enabled = false,
//                                modifier = Modifier.fillMaxWidth(),
//                                value = userDetailsViewModel.user.value.dob,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    disabledTextColor = Color.Black,
//                                    disabledIndicatorColor= Orange,
//                                    backgroundColor= Color.Transparent
//                                ),
//                                onValueChange = {},)
//                        }
//
//                        Spacer(modifier = Modifier.padding(5.dp))
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(modifier = Modifier.fillMaxWidth(0.34F), text = "Email", color = Color.Black,style = TextStyle(
//                                fontSize = 16.sp,
//                                textAlign = TextAlign.Center,
//                                letterSpacing = 2.sp
//                            ),)
//                            OutlinedTextField(
//                                readOnly = true,
//                                enabled = false,
//                                modifier = Modifier.fillMaxWidth(),
//                                value = userDetailsViewModel.user.value.email,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    disabledTextColor = Color.Black,
//                                    disabledIndicatorColor= Orange,
//                                    backgroundColor= Color.Transparent
//                                ),
//                                onValueChange = {},)
//                        }
//                        Spacer(modifier = Modifier.padding(10.dp))
//
//                        OutlinedButton(
//                            onClick = {
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth(0.6f)
//                                .height(40.dp),
//
//                            shape = RoundedCornerShape(50),
//                            colors = ButtonDefaults.buttonColors(
//                                backgroundColor = DarkYellow,
//                                contentColor = Color.White
//                            ),
//
//                            ) {
//                            Text(text = "Edit Profile", fontSize = 20.sp, color = Color.Black)
//                        }
//                    }
                }
            }
        }
    }
}