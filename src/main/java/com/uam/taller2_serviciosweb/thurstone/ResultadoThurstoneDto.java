package com.uam.taller2_serviciosweb.thurstone;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ResultadoThurstoneDto(
        Long id,

        @NotBlank(message = "El nombre del participante no puede estar vacío")
        String nombreParticipante,

        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "Debe proporcionar un correo electrónico válido")
        String correo,

        @NotNull(message = "La edad es requerida")
        @Min(value = 1, message = "La edad debe ser mayor a 0")
        Integer edad,

        @NotNull(message = "La fecha de aplicación es requerida")
        @PastOrPresent(message = "La fecha no puede ser futura")
        LocalDate fechaAplicacion,

        @NotNull(message = "La puntuación es requerida")
        @Min(value = 0, message = "La puntuación no puede ser negativa")
        Integer puntuacion,

        NivelThurstone nivel,

        Boolean completado
) {}