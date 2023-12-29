package com.example.cryptostuff.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DecryptRSAUnsafeDTO(
    @JsonProperty("exponent")
    val exponent: Long,
    @JsonProperty("cipher")
    val cipher: String,
    @JsonProperty("number")
    val number: String,
    @JsonProperty("componentP")
    val componentP: String,
    @JsonProperty("componentQ")
    val componentQ: String
)