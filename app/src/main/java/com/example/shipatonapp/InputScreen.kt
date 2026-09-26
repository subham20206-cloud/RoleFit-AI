package com.example.shipatonapp
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InputScreen(
    onGenerate: (
        skills: String,
        jobDescription: String
    ) -> Unit,

    onBackHome: () -> Unit,

    onLogout: () -> Unit,

    isAnalyzing: Boolean,

    errorMessage: String
) {

    var skills by remember {
        mutableStateOf("")
    }

    var jobDescription by remember {
        mutableStateOf("")
    }

    LazyColumn(

        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .imePadding()
            .navigationBarsPadding()
            .padding(
                horizontal = 20.dp
            ),

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        // =========================================
        // TOP NAVIGATION
        // =========================================

        item {

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                Button(

                    onClick =
                        onBackHome,

                    modifier =
                        Modifier.weight(1f),

                    enabled =
                        !isAnalyzing
                ) {

                    Text(
                        text = "HOME"
                    )
                }

                Button(

                    onClick =
                        onLogout,

                    modifier =
                        Modifier.weight(1f),

                    enabled =
                        !isAnalyzing
                ) {

                    Text(
                        text = "LOGOUT"
                    )
                }
            }
        }

        // =========================================
        // HEADER
        // =========================================

        item {

            Row(

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Image(

                    painter =
                        painterResource(
                            id =
                                R.drawable.app_logo
                        ),

                    contentDescription =
                        "RoleFit AI",

                    modifier =
                        Modifier.size(48.dp)
                )

                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )

                Column {

                    Text(

                        text =
                            "Job Match Analyzer",

                        style =
                            MaterialTheme
                                .typography
                                .headlineSmall,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(

                        text =
                            "Understand how your skills fit the role."
                    )
                }
            }
        }

        // =========================================
        // YOUR SKILLS
        // =========================================

        item {

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme
                                .colorScheme
                                .surfaceVariant
                    )
            ) {

                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                ) {

                    Text(

                        text =
                            "YOUR SKILLS",

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    Text(
                        text =
                            "Paste the skills you currently have."
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    OutlinedTextField(

                        value =
                            skills,

                        onValueChange = {
                            skills = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text("Skills")
                        },

                        placeholder = {
                            Text(
                                "Kotlin, Python, SQL, Docker, AWS"
                            )
                        },

                        minLines = 5,

                        enabled =
                            !isAnalyzing
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(

                        text =
                            "Use commas, semicolons, or separate lines.",

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall
                    )
                }
            }
        }

        // =========================================
        // JOB DESCRIPTION
        // =========================================

        item {

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme
                                .colorScheme
                                .surfaceVariant
                    )
            ) {

                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                ) {

                    Text(

                        text =
                            "JOB DESCRIPTION",

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    Text(
                        text =
                            "Paste the complete job posting."
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    OutlinedTextField(

                        value =
                            jobDescription,

                        onValueChange = {
                            jobDescription = it
                        },

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(280.dp),

                        label = {
                            Text(
                                "Job description"
                            )
                        },

                        placeholder = {
                            Text(
                                "Paste the complete job description here..."
                            )
                        },

                        minLines = 12,

                        enabled =
                            !isAnalyzing
                    )
                }
            }
        }

        // =========================================
        // ERROR
        // =========================================

        if (
            errorMessage.isNotBlank()
        ) {

            item {

                UserMessageCard(

                    message =
                        UiMessage(
                            text =
                                errorMessage,

                            type =
                                MessageType.ERROR
                        )
                )
            }
        }

        // =========================================
        // ANALYZE BUTTON
        // =========================================

        item {

            Button(

                onClick = {

                    onGenerate(
                        skills,
                        jobDescription
                    )
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                enabled =
                    !isAnalyzing &&
                            skills.isNotBlank() &&
                            jobDescription.isNotBlank(),

                shape =
                    MaterialTheme
                        .shapes
                        .large
            ) {

                Text(

                    text =
                        if (isAnalyzing) {
                            "ANALYZING..."
                        } else {
                            "✦  ANALYZE JOB"
                        },

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }

        // =========================================
        // AI INFORMATION
        // =========================================

        item {

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

                Text(

                    text =
                        "AI evaluates your skills against the job requirements and identifies matches, gaps, and learning recommendations.",

                    modifier =
                        Modifier.padding(16.dp),

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onPrimaryContainer
                )
            }

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )
        }
    }
}