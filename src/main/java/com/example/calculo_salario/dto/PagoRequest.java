package com.example.calculo_salario.dto;

import com.example.calculo_salario.enums.ContratoTipo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequest {

    @NotNull(message = "El tipo de contrato es obligatorio.")
    private ContratoTipo tipoContrato;

    @Min(value = 0, message = "Las horas trabajadas no pueden ser negativas.")
    private int horasTrabajadas;

    @Min(value = 0, message = "Las horas nocturnas no pueden ser negativas.")
    private int horasNocturnas;

    @Min(value = 0, message = "Las horas dominicales y festivos no pueden ser negativas.")
    private int horasDominicalesYFestivos;
}
