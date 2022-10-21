package com.experiment.foodproductapp.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.*

@Preview(showBackground = true)
@Composable
fun ForgotPassword() {

    ChangeBarColors(navigationBarColor = Color.White)

    val inputList = remember { mutableStateListOf("","","","") }

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
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){
                item {
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
                                },
                                textStyle = TextStyle().copy(textAlign = TextAlign.Center),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,

                                    focusedIndicatorColor = DarkYellow,
                                    unfocusedIndicatorColor = Orange,
                                    cursorColor = DarkYellow,
                                    errorCursorColor = Color.Red,
                                )
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                item {
                    Button(
                        onClick = {
                            var str = ""
                            inputList.forEach{ str += it }
                            Log.d("testOTP", "ForgotPassword: $str")
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
        }

    }

}
