package com.example.calculo_salario.dto;

import lombok.Data;

@Data
public class PagoResponse {

    private double salarioBruto;
    private double descuentoSeguridadSocial;
    private double salarioNeto;

    public PagoResponse(double salarioBruto, double descuentoSeguridadSocial, double salarioNeto) {
        this.salarioBruto = salarioBruto;
        this.descuentoSeguridadSocial = descuentoSeguridadSocial;
        this.salarioNeto = salarioNeto;
    }
}
