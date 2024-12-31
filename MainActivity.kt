package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "register") {
                composable("register") { RegisterScreen(navController) }
                composable("profile/{userData}") { backStackEntry ->
                    ProfileScreen(backStackEntry.arguments?.getString("userData"))
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C28))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create an account", fontSize = 28.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Already have an account? Log in", fontSize = 14.sp, color = Color(0xFF9A9AA6))

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = {
                if (it.length <= 30) firstName = it
            },
            label = { Text("First Name", color = Color(0xFF9A9AA6)) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = {
                if (it.length <= 30) lastName = it
            },
            label = { Text("Last Name", color = Color(0xFF9A9AA6)) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color(0xFF9A9AA6)) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter your password", color = Color(0xFF9A9AA6)) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(icon, contentDescription = "Toggle password visibility", tint = Color.White)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF9A9AA6))
            )
            Text("I agree to the Terms & Conditions", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (!validateForm(firstName, email, password)) {
                    Toast.makeText(context, "Invalid input, check again!", Toast.LENGTH_SHORT).show()
                } else if (!isChecked) {
                    Toast.makeText(context, "Please agree to the terms & conditions", Toast.LENGTH_SHORT).show()
                } else {
                    val userData = "$firstName $lastName, $email"
                    navController.navigate("profile/$userData")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF))
        ) {
            Text("Create Account", color = Color.White)
        }
    }
}

// Validation Logic
fun validateForm(firstName: String, email: String, password: String): Boolean {
    return firstName.isNotEmpty() && email.contains("@") && password.length >= 6
}


@Composable
fun ProfileScreen(userData: String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Text(
            text = "Welcome, $userData",
            fontSize = 28.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
