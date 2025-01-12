package org.elteq.logic.dob.models

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import java.time.Month
import java.time.MonthDay
import java.time.Year

@Serializable
data class DoBDTO(
    @Required
    @NotBlank
    @Min(1)
    @Max(31)
//    var day: MonthDay? = null,
    var day : Int = 1,

    @Required
    @NotNull
    var month: Month? = Month.JANUARY,

    @Required
    @NotNull
    @Min(1600)
    var year: Int = 2000,
)