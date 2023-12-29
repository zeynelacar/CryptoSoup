package com.example.cryptostuff.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class GenericResponse(
    @field:NotBlank
    @JsonProperty("statusCode")
    var statusCode: String,

    @JsonProperty
    var responseData: String
)
