package com.example.shipatonapp

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class MessageType {
    SUCCESS,
    ERROR,
    INFO
}

data class UiMessage(
    val text: String,
    val type: MessageType
)

fun friendlyAuthError(
    error: Throwable,
    isLogin: Boolean
): String {

    val raw = (
            error.message
                ?: error.toString()
            ).lowercase()

    return when {

        raw.contains("email_not_confirmed") ||
                raw.contains("email not confirmed") -> {
            "Please verify your email before signing in."
        }

        raw.contains("invalid login credentials") ||
                raw.contains("invalid credentials") ||
                raw.contains("invalid email or password") -> {
            "Incorrect email or password."
        }

        raw.contains("user already registered") ||
                raw.contains("already registered") ||
                raw.contains("already exists") -> {
            "An account with this email already exists. Please sign in."
        }

        raw.contains("weak password") ||
                (
                        raw.contains("password") &&
                                raw.contains("too short")
                        ) -> {
            "Choose a stronger password."
        }

        raw.contains("invalid email") ||
                raw.contains("email address is invalid") -> {
            "Please enter a valid email address."
        }

        raw.contains("rate limit") ||
                raw.contains("too many requests") -> {
            "Too many attempts. Please wait a moment and try again."
        }

        raw.contains("timeout") ||
                raw.contains("timed out") ||
                raw.contains("network") ||
                raw.contains("connection") ||
                raw.contains("socket") ||
                raw.contains("unable to resolve host") -> {
            "Network connection problem. Please check your internet and try again."
        }

        raw.contains("401") ||
                raw.contains("unauthorized") -> {
            "Your session has expired. Please sign in again."
        }

        raw.contains("403") ||
                raw.contains("forbidden") -> {
            "You are not allowed to perform this action."
        }

        raw.contains("500") ||
                raw.contains("502") ||
                raw.contains("503") ||
                raw.contains("server error") -> {
            "The service is temporarily unavailable. Please try again."
        }

        else -> {
            if (isLogin) {
                "Unable to sign in. Please check your details and try again."
            } else {
                "Unable to create your account. Please try again."
            }
        }
    }
}

fun friendlyAiError(
    error: Throwable
): String {

    val raw = (
            error.message
                ?: error.toString()
            ).lowercase()

    return when {

        raw.contains("timeout") ||
                raw.contains("timed out") -> {
            "The analysis took too long. Please try again."
        }

        raw.contains("network") ||
                raw.contains("connection") ||
                raw.contains("socket") ||
                raw.contains("unable to resolve host") -> {
            "Unable to connect to the AI service. Check your internet and try again."
        }

        raw.contains("429") ||
                raw.contains("quota") ||
                raw.contains("rate limit") -> {
            "The AI service is temporarily busy. Please try again shortly."
        }

        raw.contains("401") ||
                raw.contains("403") ||
                raw.contains("unauthorized") -> {
            "The AI service is currently unavailable. Please try again later."
        }

        raw.contains("400") ||
                raw.contains("bad request") -> {
            "The AI service could not process this request. Please try again."
        }

        raw.contains("502") ||
                raw.contains("503") ||
                raw.contains("server") -> {
            "The AI service is temporarily unavailable. Please try again."
        }

        else -> {
            "We couldn't complete the analysis. Please try again."
        }
    }
}

fun logAppError(
    tag: String,
    error: Throwable
) {

    Log.e(
        "RoleFitAI.$tag",
        error.message ?: "Unknown error",
        error
    )
}

@Composable
fun UserMessageCard(
    message: UiMessage,
    modifier: Modifier = Modifier
) {

    val colors =
        when (message.type) {

            MessageType.SUCCESS ->
                CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.secondaryContainer
                )

            MessageType.ERROR ->
                CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.errorContainer
                )

            MessageType.INFO ->
                CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
        }

    val textColor =
        when (message.type) {

            MessageType.SUCCESS ->
                MaterialTheme.colorScheme.onSecondaryContainer

            MessageType.ERROR ->
                MaterialTheme.colorScheme.onErrorContainer

            MessageType.INFO ->
                MaterialTheme.colorScheme.onPrimaryContainer
        }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = colors
    ) {

        Text(
            text = message.text,
            color = textColor,
            modifier = Modifier.padding(14.dp)
        )
    }
}