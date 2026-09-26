package com.example.shipatonapp

import kotlinx.serialization.Serializable

@Serializable
data class AIAnalysisResponse(

    val matchScore: Int,

    val matchedSkills: List<String>,

    val missingUserSkills: List<String>,

    val jobSkills: List<String>,

    val recommendations: List<String>,

    val summary: String
)

@Serializable
data class AIWrapperResponse(

    val success: Boolean,

    val userId: String? = null,

    val result: AIAnalysisResponse
)