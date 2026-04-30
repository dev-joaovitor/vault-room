package br.edu.vaultroom.dto.requests

import br.edu.vaultroom.validation.OnCreate
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class VaultPostRequest(
    @field:NotBlank(message = "Name is required", groups = [OnCreate::class])
    @field:Size(
        message = "Name should have between 4 and 100 characters",
        min = 4,
        max = 100, groups = [OnCreate::class]
    )
    val name: String
)
