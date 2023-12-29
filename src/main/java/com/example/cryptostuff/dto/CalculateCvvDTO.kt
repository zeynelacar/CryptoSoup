package com.example.cryptostuff.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CalculateCvvDTO(
    @JsonProperty("cvvKey")
    val cvvKey : String,

    @JsonProperty("cardNumber")
    val cardNumber : String,

    @JsonProperty
    val expiryDate : String,

    @JsonProperty
    val serviceCode : String
)
