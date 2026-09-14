package com.uam.taller2_serviciosweb.shared.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorRespuesta(
        int estado,
        String mensaje,
        Map<String, String> errores
) {
    public ErrorRespuesta(int estado, String mensaje) {
        this(estado, mensaje, null);
    }
}
