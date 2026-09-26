package com.example.shipatonapp

import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AIService {

    suspend fun analyzeJob(
        skills: String,
        jobDescription: String
    ): AIAnalysisResponse {

        val requestBody: JsonObject =
            buildJsonObject {

                put(
                    "skills",
                    skills
                )

                put(
                    "jobDescription",
                    jobDescription
                )
            }

        val response =
            supabase.functions(

                "analyze-job",

                body = requestBody,

                headers = Headers.build {

                    append(
                        HttpHeaders.ContentType,

                        ContentType.Application.Json
                            .toString()
                    )
                }
            )

        val wrapper =
            response.body<AIWrapperResponse>()

        if (!wrapper.success) {

            throw Exception(
                "AI analysis failed."
            )
        }

        return wrapper.result
    }
}