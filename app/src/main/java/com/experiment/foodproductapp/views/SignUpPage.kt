package com.experiment.foodproductapp.views

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.viewmodels.SignUpViewModel
import kotlinx.coroutines.flow.collect

@Preview(showBackground = true)
@Composable
fun SignupPage(signUpViewModel: SignUpViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val state = signUpViewModel.state
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            signUpViewModel.validationEvents.collect { event ->
                when (event) {
                    is SignUpViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.fillMaxSize(.20f),
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "REGISTER",
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Left,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.firstName,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                focusedLabelColor = Color.Blue
            ),
            onValueChange = {
                signUpViewModel.onEvent(SignupFormEvent.FirstNameChanged(it))
            },
            isError = state.firstNameError != null,
            label = { Text(text = "First Name") })
        if (state.firstNameError != null) {
            Text(
                text = state.firstNameError,
                color = MaterialTheme.colors.error,
                fontSize = 14.sp
                ,modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.lastName,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                focusedLabelColor = Color.Blue
            ),
            onValueChange = {
                signUpViewModel.onEvent(SignupFormEvent.LastNameChanged(it))
            },
            isError = state.lastNameError != null,
            label = { Text(text = "Last Name") })
        if (state.lastNameError != null) {
            Text(
                text = state.lastNameError,
                color = MaterialTheme.colors.error,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                focusedLabelColor = Color.Blue
            ),
            isError = state.emailError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                signUpViewModel.onEvent(SignupFormEvent.EmailChanged(it))
            },
            label = { Text(text = "Email") })
        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = MaterialTheme.colors.error,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            shape = RoundedCornerShape(20.dp),
            onValueChange = {
                signUpViewModel.onEvent(SignupFormEvent.PasswordChanged(it))
            },
            label = { Text(text = "Enter password") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                focusedLabelColor = Color.Blue
            ),
            visualTransformation = if (signUpViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (signUpViewModel.passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description =
                    if (signUpViewModel.passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = { signUpViewModel.passwordchange() }) {
                    Icon(imageVector = image, description)
                }
            })
        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = MaterialTheme.colors.error,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.repeatedPassword,
            shape = RoundedCornerShape(20.dp),
            onValueChange = {
                signUpViewModel.onEvent(SignupFormEvent.ConfirmPasswordChanged(it))
            },
            label = { Text(text = "Confirm password") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                focusedLabelColor = Color.Blue
            ),
            visualTransformation = if (signUpViewModel.confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (signUpViewModel.confirmPasswordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description =
                    if (signUpViewModel.confirmPasswordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = {
                    signUpViewModel.confirmpasswordchange()
                }) {
                    Icon(imageVector = image, description)
                }
            })
        if (state.repeatedPasswordError != null) {
            Text(
                text = state.repeatedPasswordError,
                color = MaterialTheme.colors.error,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                shape = RoundedCornerShape(40.dp),
                onClick = {
                    signUpViewModel.onEvent(SignupFormEvent.Submit)
                }) {
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