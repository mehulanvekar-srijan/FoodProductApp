package com.experiment.foodproductapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun SignupPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.fillMaxSize(.20f),
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "REGISTER",
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Left,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            onValueChange = { },
            label = { Text(text = "First Name") })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            onValueChange = { },
            label = { Text(text = "Last Name") })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            onValueChange = { },
            label = { Text(text = "Email") })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            shape = RoundedCornerShape(20.dp),
            onValueChange = { },
            label = { Text(text = "Enter password") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            visualTransformation = if (true) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (true)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description =
                    if (true) "Hide password" else "Show password"

                IconButton(onClick = { }) {
                    Icon(imageVector = image, description)
                }
            })
        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedButton(
                shape = RoundedCornerShape(40.dp),
                onClick = {}) {
                Text(text = "CREATE ACCOUNT")

            }
        }

    }

}

//@Composable
//fun ShowRadioGroup(
//    selected: String,
//    updateRadioGroupSelection: (String) -> Unit,
//    radioGroupError: Boolean,
//) {
//    Column(
//        modifier = Modifier.padding(top = 10.dp)
//    ) {
//        Row {
//            RadioButton(
//                selected = selected == "Male",
//                onClick = { updateRadioGroupSelection("Male") })
//            Text(
//                text = "Male",
//                modifier = Modifier
//                    .clickable {
//                        updateRadioGroupSelection("Male")
//                    }
//                    .padding(start = 4.dp)
//            )
//            Spacer(modifier = Modifier.size(4.dp))
//            RadioButton(
//                selected = selected == "Female",
//                onClick = { updateRadioGroupSelection("Female") })
//            Text(
//                text = "Female",
//                modifier = Modifier
//                    .clickable(onClick = { updateRadioGroupSelection("Female") })
//                    .padding(start = 4.dp)
//            )
//            Spacer(modifier = Modifier.size(4.dp))
//            RadioButton(
//                selected = selected == "Other",
//                onClick = { updateRadioGroupSelection("Other") })
//            Text(
//                text = "Other",
//                modifier = Modifier
//                    .clickable(onClick = { updateRadioGroupSelection("Other") })
//                    .padding(start = 4.dp)
//            )
//        }
//        if (radioGroupError) {
//            Text(
//                text = "Select Valid Option",
//                color = MaterialTheme.colors.error,
//                style = MaterialTheme.typography.caption,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun TextFieldWithErrorView(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    readOnly: Boolean = false,
//    textStyle: TextStyle = LocalTextStyle.current,
//    label: @Composable (() -> Unit)? = null,
//    placeholder: @Composable (() -> Unit)? = null,
//    leadingIcon: @Composable (() -> Unit)? = null,
//    trailingIcon: @Composable (() -> Unit)? = null,
//    isError: Boolean = false,
//    visualTransformation: VisualTransformation = VisualTransformation.None,
//    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//    keyboardActions: KeyboardActions = KeyboardActions.Default,
//    singleLine: Boolean = false,
//    maxLines: Int = Int.MAX_VALUE,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    shape: Shape = MaterialTheme.shapes.small,
//    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
//    errorMsg: String = ""
//) {
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(
//                bottom = if (isError) {
//                    0.dp
//                } else {
//                    10.dp
//                }
//            )
//    ) {
//        TextField(
//            enabled = enabled,
//            readOnly = readOnly,
//            value = value,
//            onValueChange = onValueChange,
//            modifier = Modifier.fillMaxWidth(),
//            singleLine = singleLine,
//            textStyle = textStyle,
//            label = label,
//            placeholder = placeholder,
//            leadingIcon = leadingIcon,
//            trailingIcon = trailingIcon,
//            isError = isError,
//            visualTransformation = visualTransformation,
//            keyboardOptions = keyboardOptions,
//            keyboardActions = keyboardActions,
//            maxLines = maxLines,
//            interactionSource = interactionSource,
//            shape = shape,
//            colors = colors
//        )
//
//        if (isError) {
//            Text(
//                text = errorMsg,
//                color = MaterialTheme.colors.error,
//                style = MaterialTheme.typography.caption,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//        }
//    }
//
//}