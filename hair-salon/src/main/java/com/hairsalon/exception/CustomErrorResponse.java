package com.hairsalon.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//Based on RFC 7807 standar
public class CustomErrorResponse { //Tambien podria ser un Record
 /**
     * Identificador único del tipo de problema. Debe ser una URI que sirva como un
     * identificador global.
     */
    private String type;

    /**
     * Título descriptivo del problema.
     */
    private String title;

    /**
     * Código de estado HTTP asociado con el problema.
     *
     * @see <a href="https://developer.mozilla.org/es/docs/Web/HTTP/Status">Códigos de estado HTTP</a>
     */
    private int status;

    /**
     * Detalles adicionales que proporcionan más información sobre el problema.
     */
    private String detail;

    /**
     * URI única que identifica la instancia (URL) específica del problema.
     */
    private String instance;

    /**
     * Timestamp que indica cuándo ocurrió el problema.
     */
    private LocalDateTime timestamp;

}
