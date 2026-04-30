package br.edu.vaultroom.dto.requests

import br.edu.vaultroom.validation.OnCreate
import br.edu.vaultroom.validation.OnUpdate
import jakarta.annotation.Nullable
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Null
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size


data class ProductPatchQuantityRequest(
    @field:Min(
        message = "Quantity must not be lower than 1",
        value = 1,
        groups = [OnUpdate::class]
    )
    @field:Max(
        message = "Quantity must not be higher than 9999",
        value = 9999,
        groups = [OnUpdate::class]
    )
    val quantity: Int,

    @field:Positive(message = "Type id must be higher than 0", groups = [OnUpdate::class])
    val type_id: Long,
)


