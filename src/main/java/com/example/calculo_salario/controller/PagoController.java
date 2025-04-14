package com.example.calculo_salario.controller;

import com.example.calculo_salario.dto.PagoRequest;
import com.example.calculo_salario.dto.PagoResponse;
import com.example.calculo_salario.service.PagoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pago")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/calcular")
    public PagoResponse calcular(@Valid @RequestBody PagoRequest request) {
        return pagoService.calcularPago(request);
    }
}
