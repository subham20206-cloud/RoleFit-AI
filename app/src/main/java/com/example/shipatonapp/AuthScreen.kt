package com.example.shipatonapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    onAuthenticated: () -> Unit,
    initialMessage: UiMessage? = null
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var isLogin by remember {
        mutableStateOf(true)
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var message by remember {
        mutableStateOf<UiMessage?>(initialMessage)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(initialMessage) {

        if (initialMessage != null) {
            message = initialMessage
        }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),

        verticalArrangement =
            Arrangement.Center,

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        // =========================================
        // LOGO
        // =========================================

        Image(

            painter =
                painterResource(
                    id = R.drawable.app_logo
                ),

            contentDescription =
                "RoleFit AI",

            modifier =
                Modifier.size(100.dp)
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(

            text = "RoleFit AI",

            fontWeight =
                FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text =
                "AI-powered job matching"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // =========================================
        // TITLE
        // =========================================

        Text(

            text =
                if (isLogin) {
                    "Welcome back"
                } else {
                    "Create your account"
                },

            style =
                androidx.compose.material3.MaterialTheme
                    .typography
                    .headlineSmall,

            fontWeight =
                FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        // =========================================
        // EMAIL
        // =========================================

        OutlinedTextField(

            value = email,

            onValueChange = {

                email = it
                message = null
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {
                Text("Email")
            },

            placeholder = {
                Text("you@example.com")
            },

            singleLine = true,

            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Email
                ),

            enabled = !isLoading
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // =========================================
        // PASSWORD
        // =========================================

        OutlinedTextField(

            value = password,

            onValueChange = {

                password = it
                message = null
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {
                Text("Password")
            },

            placeholder = {
                Text("Enter your password")
            },

            singleLine = true,

            visualTransformation =
                if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },

            trailingIcon = {

                IconButton(
                    onClick = {
                        passwordVisible =
                            !passwordVisible
                    },

                    enabled =
                        !isLoading
                ) {

                    Text(
                        text =
                            if (passwordVisible) {
                                "Hide"
                            } else {
                                "Show"
                            }
                    )
                }
            },

            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Password
                ),

            enabled = !isLoading
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // =========================================
        // MESSAGE
        // =========================================

        message?.let {

            UserMessageCard(
                message = it
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )
        }

        // =========================================
        // MAIN BUTTON
        // =========================================

        Button(

            onClick = {

                // -------------------------------------
                // VALIDATION
                // -------------------------------------

                if (email.isBlank()) {

                    message =
                        UiMessage(
                            "Please enter your email address.",
                            MessageType.ERROR
                        )

                    return@Button
                }

                if (
                    !android.util.Patterns
                        .EMAIL_ADDRESS
                        .matcher(email.trim())
                        .matches()
                ) {

                    message =
                        UiMessage(
                            "Please enter a valid email address.",
                            MessageType.ERROR
                        )

                    return@Button
                }

                if (password.isBlank()) {

                    message =
                        UiMessage(
                            "Please enter your password.",
                            MessageType.ERROR
                        )

                    return@Button
                }

                if (password.length < 6) {

                    message =
                        UiMessage(
                            "Password must be at least 6 characters.",
                            MessageType.ERROR
                        )

                    return@Button
                }

                scope.launch {

                    isLoading = true
                    message = null

                    try {

                        if (isLogin) {

                            // =============================
                            // LOGIN
                            // =============================

                            supabase.auth
                                .signInWith(
                                    Email
                                ) {

                                    this.email =
                                        email.trim()

                                    this.password =
                                        password
                                }

                            onAuthenticated()

                        } else {

                            // =============================
                            // SIGN UP
                            // =============================

                            supabase.auth
                                .signUpWith(
                                    Email
                                ) {

                                    this.email =
                                        email.trim()

                                    this.password =
                                        password
                                }

                            /*
                             * Force registration and
                             * login to remain separate.
                             */

                            if (
                                supabase.auth
                                    .currentUserOrNull()
                                != null
                            ) {

                                supabase.auth.signOut()
                            }

                            password = ""
                            passwordVisible = false
                            isLogin = true

                            message =
                                UiMessage(
                                    "Account created successfully. Please sign in.",
                                    MessageType.SUCCESS
                                )
                        }

                    } catch (e: Exception) {

                        logAppError(
                            "Auth",
                            e
                        )

                        message =
                            UiMessage(
                                friendlyAuthError(
                                    e,
                                    isLogin
                                ),
                                MessageType.ERROR
                            )

                    } finally {

                        isLoading = false
                    }
                }
            },

            modifier =
                Modifier.fillMaxWidth(),

            enabled =
                !isLoading
        ) {

            Text(

                text =
                    when {

                        isLoading ->
                            "PLEASE WAIT..."

                        isLogin ->
                            "LOGIN"

                        else ->
                            "CREATE ACCOUNT"
                    }
            )
        }

        Spacer(
            modifier =
                Modifier.height(10.dp)
        )

        // =========================================
        // SWITCH MODE
        // =========================================

        Button(

            onClick = {

                isLogin =
                    !isLogin

                password = ""
                passwordVisible = false
                message = null
            },

            modifier =
                Modifier.fillMaxWidth(),

            enabled =
                !isLoading
        ) {

            Text(

                text =
                    if (isLogin) {
                        "CREATE NEW ACCOUNT"
                    } else {
                        "I ALREADY HAVE AN ACCOUNT"
                    }
            )
        }

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        Text(
            text =
                "Your information stays private to your account.",
            style =
                androidx.compose.material3.MaterialTheme
                    .typography
                    .bodySmall
        )
    }
}