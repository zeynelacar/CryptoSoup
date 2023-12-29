package com.example.cryptostuff.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class EncPinBlockDecodeDTO(

    @field:NotBlank
    @JsonProperty("cardNo")
    val cardNo: String,

    @field:NotBlank
    @JsonProperty("pinBlock")
    val pinBlock: String,

    @field:NotBlank
    @JsonProperty("pinBlockFormat")
    val pinBlockFormat: String,

    @field:NotBlank
    @JsonProperty("pinKey")
    val pinKey: String

)
