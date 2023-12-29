package com.example.cryptostuff.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.context.properties.bind.DefaultValue

data class Generate3DESKeyDTO(
    //0 for double length 1 for triple length
    @JsonProperty("lengthType")
    @DefaultValue("0")
    val lengthType: Short
)