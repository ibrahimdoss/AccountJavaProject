package com.SpringBoot.account.dto

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class CreateAccountRequest (

    @field:NotBlank
    val customerId: String,
    @field: Min(value = 0)
    val initialCredit: BigDecimal

    )