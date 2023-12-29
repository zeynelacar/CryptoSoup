package com.example.cryptostuff.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class ClearPinBlockDecodeDTO(
    @field:NotBlank
    @JsonProperty("cardNo")
    val cardNo: String,

    @field:NotBlank
    @JsonProperty("pinBlock")
    val pinBlock: String,

    @field:NotBlank
    @JsonProperty("pinBlockFormat")
    val pinBlockFormat: String

)