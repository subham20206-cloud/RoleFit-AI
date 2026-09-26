package com.example.shipatonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        setContent {

            MaterialTheme {

                RoleFitApp()
            }
        }
    }
}

@Composable
fun RoleFitApp() {

    // =========================================
    // AUTH STATE
    // =========================================

    var authenticated by remember {

        mutableStateOf(
            supabase.auth
                .currentUserOrNull() != null
        )
    }

    // =========================================
    // SCREEN
    // =========================================

    var screen by remember {

        mutableStateOf("home")
    }

    // =========================================
    // AI RESULT
    // =========================================

    var analysisResult by remember {

        mutableStateOf<AIAnalysisResponse?>(null)
    }

    // =========================================
    // AI LOADING
    // =========================================

    var isAnalyzing by remember {

        mutableStateOf(false)
    }

    // =========================================
    // ERROR
    // =========================================

    var errorMessage by remember {

        mutableStateOf("")
    }

    // =========================================
    // AUTH MESSAGE
    // =========================================

    var authMessage by remember {

        mutableStateOf<UiMessage?>(null)
    }

    // =========================================
    // COROUTINE
    // =========================================

    val scope =
        rememberCoroutineScope()

    // =========================================
    // LOGOUT
    // =========================================

    fun logout() {

        // Return to login immediately.
        authenticated = false

        screen = "home"

        analysisResult = null

        errorMessage = ""

        isAnalyzing = false

        authMessage =
            UiMessage(
                "You have been signed out.",
                MessageType.SUCCESS
            )

        scope.launch {

            try {

                supabase.auth.signOut()

            } catch (e: Exception) {

                logAppError(
                    "Logout",
                    e
                )
            }
        }
    }

    // =========================================
    // AUTHENTICATION
    // =========================================

    if (!authenticated) {

        AuthScreen(

            onAuthenticated = {

                authenticated = true
                screen = "home"
                errorMessage = ""
                authMessage = null
            },

            initialMessage =
                authMessage
        )

        return
    }

    // =========================================
    // APPLICATION
    // =========================================

    when (screen) {

        // =====================================
        // HOME
        // =====================================

        "home" -> {

            HomeScreen(

                onStart = {

                    screen = "input"
                    errorMessage = ""
                },

                onLogout = {

                    logout()
                }
            )
        }

        // =====================================
        // INPUT
        // =====================================

        "input" -> {

            InputScreen(

                onGenerate = {
                        skills,
                        jobDescription ->

                    scope.launch {

                        isAnalyzing = true
                        errorMessage = ""

                        try {

                            val result =
                                AIService()
                                    .analyzeJob(
                                        skills =
                                            skills,
                                        jobDescription =
                                            jobDescription
                                    )

                            analysisResult =
                                result

                            screen =
                                "result"

                        } catch (e: Exception) {

                            logAppError(
                                "AIAnalysis",
                                e
                            )

                            errorMessage =
                                friendlyAiError(
                                    e
                                )

                        } finally {

                            isAnalyzing = false
                        }
                    }
                },

                onBackHome = {

                    if (!isAnalyzing) {

                        screen = "home"
                        errorMessage = ""
                    }
                },

                onLogout = {

                    if (!isAnalyzing) {

                        logout()
                    }
                },

                isAnalyzing =
                    isAnalyzing,

                errorMessage =
                    errorMessage
            )
        }

        // =====================================
        // RESULT
        // =====================================

        "result" -> {

            val result =
                analysisResult

            if (result != null) {

                ResultScreen(

                    result = result,

                    onBackHome = {

                        screen = "home"
                        errorMessage = ""
                    },

                    onLogout = {

                        logout()
                    }
                )

            } else {

                screen = "home"
            }
        }

        // =====================================
        // FALLBACK
        // =====================================

        else -> {

            screen = "home"
        }
    }
}


// =====================================================
// HOME SCREEN
// =====================================================

@Composable
fun HomeScreen(
    onStart: () -> Unit,
    onLogout: () -> Unit
) {

    Column(

        modifier =
            Modifier
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
                    id =
                        R.drawable.app_logo
                ),

            contentDescription =
                "RoleFit AI",

            modifier =
                Modifier.size(150.dp)
        )

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        // =========================================
        // BRAND
        // =========================================

        Text(

            text =
                "RoleFit AI",

            style =
                MaterialTheme
                    .typography
                    .headlineLarge,

            fontWeight =
                FontWeight.Bold
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(

            text =
                "Find your fit. Build your future.",

            style =
                MaterialTheme
                    .typography
                    .titleMedium,

            textAlign =
                TextAlign.Center
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(

            text =
                "Compare your skills with any job description using AI.",

            style =
                MaterialTheme
                    .typography
                    .bodyLarge,

            textAlign =
                TextAlign.Center
        )

        Spacer(
            modifier =
                Modifier.height(28.dp)
        )

        // =========================================
        // FEATURE CARD
        // =========================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme
                            .colorScheme
                            .primaryContainer
                )
        ) {

            Column(

                modifier =
                    Modifier.padding(18.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(

                    text =
                        "AI JOB MATCHING",

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )

                Text(

                    text =
                        "Match skills • Find gaps • Get recommendations",

                    textAlign =
                        TextAlign.Center,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        // =========================================
        // START
        // =========================================

        Button(

            onClick =
                onStart,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),

            shape =
                MaterialTheme
                    .shapes
                    .large
        ) {

            Text(

                text =
                    "✦  START JOB ANALYSIS",

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold
            )
        }

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        // =========================================
        // LOGOUT
        // =========================================

        Button(

            onClick =
                onLogout,

            modifier =
                Modifier.fillMaxWidth(),

            colors =
                ButtonDefaults.textButtonColors()
        ) {

            Text(
                text =
                    "LOGOUT"
            )
        }
    }
}