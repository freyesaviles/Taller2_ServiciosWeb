package com.uam.taller2_serviciosweb.allport.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ResultadoAllportDto(
        Long id,

        @NotBlank(message = "El nombre del participante es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombreParticipante,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato válido")
        String correo,

        @NotNull(message = "La edad es obligatoria")
        @Min(value = 6, message = "La edad debe ser mayor o igual a 6")
        @Max(value = 120, message = "La edad debe ser menor o igual a 120")
        Integer edad,

        @NotNull(message = "La fecha de aplicación es obligatoria")
        @PastOrPresent(message = "La fecha de aplicación no puede estar en el futuro")
        LocalDate fechaAplicacion,

        @NotNull(message = "La puntuación del valor teórico es obligatoria")
        @Min(value = 0, message = "La puntuación no puede ser negativa")
        @Max(value = 60, message = "La puntuación no puede ser mayor que 60")
        Integer puntuacionTeorico,

        @NotNull(message = "La puntuación del valor económico es obligatoria")
        @Min(value = 0, message = "La puntuación no puede ser negativa")
        @Max(value = 60, message = "La puntuación no puede ser mayor que 60")
        Integer puntuacionEconomico,

        @NotNull(message = "La puntuación del valor estético es obligatoria")
        @Min(value = 0, message = "La puntuación no puede ser negativa")
        @Max(value = 60, message = "La puntuación no puede ser mayor que 60")
        Integer puntuacionEstetico,

        @NotNull(message = "La puntuación del valor social es obligatoria")
        @Min(value = 0, message = "La puntuación no puede ser negativa")
        @Max(value = 60, message = "La puntuación no puede ser mayor que 60")
        Integer puntuacionSocial,

        @NotNull(message = "La puntuación del valor político es obligatoria")
        @Min(value = 0, message = "La puntuación no puede ser negativa")
        @Max(value = 60, message = "La puntuación no puede ser mayor que 60")
        Integer puntuacionPolitico,

        @NotNull(message = "La puntuación del valor religioso es obligatoria")
        @Min(value = 0, message = "La puntuación no puede ser negativa")
        @Max(value = 60, message = "La puntuación no puede ser mayor que 60")
        Integer puntuacionReligioso,

        @NotNull(message = "El valor dominante es obligatorio")
        ValorAllport valorDominante,

        @NotNull(message = "Debe indicar si la evaluación fue finalizada")
        Boolean finalizado
) {
    public ResultadoAllportDto conId(Long nuevoId) {
        return new ResultadoAllportDto(nuevoId, nombreParticipante, correo, edad, fechaAplicacion,
                puntuacionTeorico, puntuacionEconomico, puntuacionEstetico, puntuacionSocial,
                puntuacionPolitico, puntuacionReligioso, valorDominante, finalizado);
    }
}
