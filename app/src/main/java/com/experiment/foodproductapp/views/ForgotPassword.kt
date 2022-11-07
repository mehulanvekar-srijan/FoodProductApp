package com.experiment.foodproductapp.views

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.ForgotPasswordViewModel

@Preview(showBackground = true)
@Composable
fun ForgotPassword(
    forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
) {

    ChangeBarColors(navigationBarColor = Color.White)
    val focusManager = LocalFocusManager.current

    val inputList = remember { mutableStateListOf("","","","") }

    val showEnterEmail = remember { mutableStateOf(true) }
    val showEnterOTP = remember { mutableStateOf(false) }
    val showEnterPasswordTextField = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()){

        //Background Image
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
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){
                item {
                    AnimatedVisibility(visible = showEnterEmail.value){
                        Text(
                            text = "Please enter your registered email",
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = titleFontFamily
                        )
                        Spacer(modifier = Modifier.padding(30.dp))
                    }
                }
                item {
                    AnimatedVisibility(visible = showEnterEmail.value){
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = "",
                            shape= RoundedCornerShape(30.dp),
                            onValueChange = {},
                            label = { Text(text = "Email", color = Color.Black) },
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
                item {
                    AnimatedVisibility(visible = showEnterEmail.value){
                        Button(
                            onClick = {
                                showEnterOTP.value = true
                                showEnterEmail.value = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DarkYellow,
                                contentColor = Color.White
                            ),
                        ) {
                            Text(text = "Next")
                        }
                    }
                }

                item {
                    AnimatedVisibility(visible = showEnterOTP.value){
                        Text(
                            text = "Enter 4 digit OTP sent via email",
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = titleFontFamily
                        )
                        Spacer(modifier = Modifier.padding(30.dp))
                    }
                }
                item {
                    AnimatedVisibility(visible = showEnterOTP.value){
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            for(i in 0..3){
                                TextField(
                                    value = inputList[i],
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .size(55.dp),
                                    onValueChange = {
                                        inputList[i] = it
                                        if(inputList[i].isNotEmpty()) focusManager.moveFocus(FocusDirection.Right)
                                        if(i == 3 && inputList[i].isNotEmpty()) focusManager.clearFocus()
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
                item {
                    AnimatedVisibility(visible = showEnterOTP.value){
                        Button(
                            onClick = {
                                var str = ""
                                inputList.forEach{ str += it }
                                showEnterOTP.value = false
                                showEnterPasswordTextField.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DarkYellow,
                                contentColor = Color.White
                            ),
                        ) {
                            Text(text = "submit")
                        }
                    }
                }
                item {
                    AnimatedVisibility(visible = showEnterPasswordTextField.value){
                        Text(
                            text = "Please enter your new password",
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = titleFontFamily
                        )
                        Spacer(modifier = Modifier.padding(30.dp))
                    }
                }
                item {
                    AnimatedVisibility(visible = showEnterPasswordTextField.value){
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = "",
                            shape= RoundedCornerShape(30.dp),
                            onValueChange = {},
                            label = { Text(text = "Enter new password", color = Color.Black) },
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
                item {
                    AnimatedVisibility(visible = showEnterPasswordTextField.value){
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = "",
                            shape= RoundedCornerShape(30.dp),
                            onValueChange = {},
                            label = { Text(text = "Confirm password", color = Color.Black) },
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
            }
        }
    }

}
