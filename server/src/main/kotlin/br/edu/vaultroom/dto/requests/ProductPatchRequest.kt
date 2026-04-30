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
import java.math.BigDecimal


data class ProductPatchRequest(
    @field:NotBlank(message = "Name is required", groups = [OnCreate::class])
    @field:Size(
        message = "Name should have between 4 and 100 characters",
        min = 4,
        max = 100,
        groups = [OnUpdate::class]
    )
    val name: String,

    @field:DecimalMin(
        message = "Price by unit must not be lower than 0.01",
        value = "0.01",
        groups = [OnUpdate::class]
    )
    @field:DecimalMax(
        message = "Price by unit must not be higher than 9999.99",
        value = "9999.99",
        groups = [OnUpdate::class]
    )
    val price_by_unit: BigDecimal,

    @field:Positive(message = "Type id must be higher than 0", groups = [OnUpdate::class])
    val type_id: Long?,
)

