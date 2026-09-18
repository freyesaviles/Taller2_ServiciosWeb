package com.uam.taller2_serviciosweb.allport.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AllportDto(

        Long id,

        @NotBlank(message = "El nombre del participante es obligatorio")
        @Size(
                min = 3,
                max = 100,
                message = "El nombre debe tener entre 3 y 100 caracteres"
        )
        String nombreParticipante,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato válido")
        String correo,

        @NotNull(message = "La edad es obligatoria")
        @Min(value = 6, message = "La edad debe ser mayor o igual a 6")
        @Max(value = 120, message = "La edad debe ser menor o igual a 120")
        Integer edad,

        @NotNull(message = "La fecha de aplicación es obligatoria")
        @PastOrPresent(
                message = "La fecha de aplicación no puede ser futura"
        )
        LocalDate fechaAplicacion,

        @NotNull(message = "El valor dominante es obligatorio")
        ValorAllport valorDominante,

        @NotNull(message = "La puntuación es obligatoria")
        @Min(value = 0, message = "La puntuación no puede ser menor que 0")
        @Max(value = 100, message = "La puntuación no puede ser mayor que 100")
        Integer puntuacion,

        @NotNull(message = "Debe indicar si la evaluación fue finalizada")
        Boolean finalizado

) {

    public AllportDto conId(Long nuevoId) {

        return new AllportDto(
                nuevoId,
                nombreParticipante,
                correo,
                edad,
                fechaAplicacion,
                valorDominante,
                puntuacion,
                finalizado
        );
    }
}