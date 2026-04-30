package br.edu.vaultroom.dto.requests

import br.edu.vaultroom.validation.OnCreate
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size


data class ProductPostRequest(
    @field:NotBlank(message = "Name is required", groups = [OnCreate::class])
    @field:Size(
        message = "Name should have between 4 and 100 characters",
        min = 4,
        max = 100,
        groups = [OnCreate::class]
    )
    val name: String,

    @field:Min(
        message = "Quantity must not be lower than 1",
        value = 1,
        groups = [OnCreate::class]
    )
    @field:Max(
        message = "Quantity must not be higher than 9999",
        value = 9999,
        groups = [OnCreate::class]
    )
    val quantity: Int,

    @field:DecimalMin(
        message = "Price by unit must not be lower than 0.01",
        value = "0.01",
        groups = [OnCreate::class]
    )
    @field:DecimalMax(
        message = "Price by unit must not be higher than 9999.99",
        value = "9999.99",
        groups = [OnCreate::class]
    )
    val price_by_unit: Double,

    @field:Positive(message = "Vault id must be higher than 0", groups = [OnCreate::class])
    val vault_id: Long,

    @field:Positive(message = "Type id must be higher than 0", groups = [OnCreate::class])
    val type_id: Long,
)
