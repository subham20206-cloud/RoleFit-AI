package com.example.shipatonapp

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(

    result: AIAnalysisResponse,

    onBackHome: () -> Unit,

    onLogout: () -> Unit
) {

    LazyColumn(

        modifier =
            Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .imePadding()
                .navigationBarsPadding()
                .padding(
                    horizontal = 20.dp
                ),

        verticalArrangement =
            Arrangement.spacedBy(14.dp)
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
                        Modifier.weight(1f)
                ) {

                    Text(
                        text = "HOME"
                    )
                }

                Button(

                    onClick =
                        onLogout,

                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        text = "LOGOUT"
                    )
                }
            }
        }

        // =========================================
        // TITLE
        // =========================================

        item {

            Text(

                text =
                    "AI Analysis Result",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                fontWeight =
                    FontWeight.Bold
            )
        }

        // =========================================
        // SCORE
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

                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(22.dp)
                ) {

                    Text(

                        text =
                            "YOUR MATCH SCORE",

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(

                        text =
                            "${result.matchScore}%",

                        style =
                            MaterialTheme
                                .typography
                                .displaySmall,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }

        // =========================================
        // SUMMARY
        // =========================================

        item {

            ResultSectionCard(
                title = "SUMMARY"
            ) {

                Text(
                    text =
                        result.summary
                )
            }
        }

        // =========================================
        // MATCHED SKILLS
        // =========================================

        item {

            Text(

                text =
                    "MATCHED SKILLS",

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold
            )
        }

        if (
            result.matchedSkills.isEmpty()
        ) {

            item {

                Text(
                    text =
                        "No strong matches detected."
                )
            }

        } else {

            items(
                result.matchedSkills
            ) { skill ->

                SkillCard(
                    text =
                        "✓  $skill"
                )
            }
        }

        // =========================================
        // USER SKILLS NOT MATCHING
        // =========================================

        item {

            Text(

                text =
                    "YOUR SKILLS NOT MATCHING",

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold
            )
        }

        if (
            result.missingUserSkills.isEmpty()
        ) {

            item {

                Text(
                    text =
                        "All supplied skills have relevant matches."
                )
            }

        } else {

            items(
                result.missingUserSkills
            ) { skill ->

                SkillCard(
                    text =
                        "•  $skill"
                )
            }
        }

        // =========================================
        // JOB REQUIREMENTS
        // =========================================

        item {

            Text(

                text =
                    "JOB SKILLS / REQUIREMENTS",

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold
            )
        }

        if (
            result.jobSkills.isEmpty()
        ) {

            item {

                Text(
                    text =
                        "No job skills detected."
                )
            }

        } else {

            items(
                result.jobSkills
            ) { skill ->

                SkillCard(
                    text =
                        "•  $skill"
                )
            }
        }

        // =========================================
        // RECOMMENDATIONS
        // =========================================

        item {

            Text(

                text =
                    "RECOMMENDATIONS",

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold
            )
        }

        if (
            result.recommendations.isEmpty()
        ) {

            item {

                Text(
                    text =
                        "No additional recommendations."
                )
            }

        } else {

            items(
                result.recommendations
            ) { recommendation ->

                SkillCard(
                    text =
                        "→  $recommendation"
                )
            }
        }

        // =========================================
        // ANALYZE ANOTHER
        // =========================================

        item {

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Button(

                onClick =
                    onBackHome,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    text =
                        "ANALYZE ANOTHER JOB"
                )
            }

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )
        }
    }
}


// =====================================================
// RESULT SECTION CARD
// =====================================================

@Composable
private fun ResultSectionCard(

    title: String,

    content: @Composable () -> Unit
) {

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
                    title,

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            content()
        }
    }
}


// =====================================================
// SKILL CARD
// =====================================================

@Composable
private fun SkillCard(
    text: String
) {

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

        Text(

            text =
                text,

            modifier =
                Modifier.padding(14.dp)
        )
    }
}