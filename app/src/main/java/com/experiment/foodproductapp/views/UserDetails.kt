package com.experiment.foodproductapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.experiment.foodproductapp.ui.theme.DarkYellow
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
                        Spacer(modifier = Modifier.padding(5.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(modifier = Modifier.fillMaxWidth(0.31F),text = "First Name", color = Color.Black,style = TextStyle(
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                letterSpacing = 2.sp
                            ))
                            Spacer(modifier = Modifier.padding(5.dp))
                            OutlinedTextField(
                                enabled = false,
                                readOnly = true,
                                modifier = Modifier.fillMaxWidth(),
                                value = userDetailsViewModel.user.value.firstName,
                                colors = TextFieldDefaults.textFieldColors(
                                    disabledTextColor = Color.Black,
                                    disabledIndicatorColor= Orange,
                                    backgroundColor= Color.Transparent
                                ),
                                onValueChange = {},)
                        }

                        Spacer(modifier = Modifier.padding(5.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(modifier = Modifier.fillMaxWidth(0.34F),text = "Last Name", color = Color.Black,style = TextStyle(
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                letterSpacing = 2.sp
                            ),)
                            OutlinedTextField(
                                readOnly = true,
                                enabled = false,
                                modifier = Modifier.fillMaxWidth(),
                                value = userDetailsViewModel.user.value.lastName,
                                colors = TextFieldDefaults.textFieldColors(
                                    disabledTextColor = Color.Black,
                                    disabledIndicatorColor= Orange,
                                    backgroundColor= Color.Transparent
                                ),
                                onValueChange = {},)
                        }

                        Spacer(modifier = Modifier.padding(5.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(modifier = Modifier.fillMaxWidth(0.34F),text = "Date Of Birth", color = Color.Black,style = TextStyle(
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                letterSpacing = 2.sp
                            ),)
                            OutlinedTextField(
                                readOnly = true,
                                enabled = false,
                                modifier = Modifier.fillMaxWidth(),
                                value = userDetailsViewModel.user.value.dob,
                                colors = TextFieldDefaults.textFieldColors(
                                    disabledTextColor = Color.Black,
                                    disabledIndicatorColor= Orange,
                                    backgroundColor= Color.Transparent
                                ),
                                onValueChange = {},)
                        }

                        Spacer(modifier = Modifier.padding(5.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(modifier = Modifier.fillMaxWidth(0.34F), text = "Email", color = Color.Black,style = TextStyle(
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                letterSpacing = 2.sp
                            ),)
                            OutlinedTextField(
                                readOnly = true,
                                enabled = false,
                                modifier = Modifier.fillMaxWidth(),
                                value = userDetailsViewModel.user.value.email,
                                colors = TextFieldDefaults.textFieldColors(
                                    disabledTextColor = Color.Black,
                                    disabledIndicatorColor= Orange,
                                    backgroundColor= Color.Transparent
                                ),
                                onValueChange = {},)
                        }
                        Spacer(modifier = Modifier.padding(10.dp))

                        OutlinedButton(
                            onClick = {
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
                }
            }
        }
    }
}