package com.example.cryptostuff.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class DecryptDesDto (
    @field:NotBlank
    @JsonProperty("plainTextData")
    val plainTextData : String,

    @JsonProperty("initialVector")
    @field:Max(16)
    val initialVector : String,

    @field:NotBlank
    @field:Min(32)
    @field:Max(32)
    @JsonProperty("secretKey")
    val secretKey : String,

    @field:NotBlank
    @JsonProperty("paddingMode")
    val paddingMode: String
)