package com.example.cryptostuff.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank

data class EncryptDecryptAesDTO (
    @field:NotBlank
    @JsonProperty("plainHexData")
    val plainHexData : String,

    @JsonProperty("initialVector")
    @field:Max(32)
    val initialVector : String,

    @field:NotBlank
    @JsonProperty("secretKey")
    val secretKey : String,

    @field:NotBlank
    @JsonProperty("encryptionMode")
    val encryptionMode: String
)