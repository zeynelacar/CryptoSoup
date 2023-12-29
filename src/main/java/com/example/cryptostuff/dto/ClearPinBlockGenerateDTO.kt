package com.example.cryptostuff.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class ClearPinBlockGenerateDTO(
    @field:NotBlank
    @JsonProperty("cardNo")
    val cardNo: String,

    @field:NotBlank
    @JsonProperty("clearPin")
    val clearPin: String,

    @field:NotBlank
    @JsonProperty("pinBlockFormat")
    val pinBlockFormat: String

)