package com.example.calculo_salario.service;

import com.example.calculo_salario.dto.PagoRequest;
import com.example.calculo_salario.dto.PagoResponse;
import com.example.calculo_salario.enums.ContratoTipo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PagoService {


    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);


    public PagoResponse calcularPago(PagoRequest request) {
        logger.info("Iniciando cálculo de pago para el request: {}", request);

        validarRequest(request);
        logger.debug("Validación del request completada correctamente");

        double valorHora = obtenerValorHora(request.getTipoContrato());
        logger.debug("Valor hora determinado según tipo de contrato ({}): {}", request.getTipoContrato(), valorHora);

        double pagoOrdinario = request.getHorasTrabajadas() * valorHora;
        double pagoNocturno = request.getHorasNocturnas() * valorHora * 1.35;
        double pagoDominical = request.getHorasDominicalesYFestivos() * valorHora * 1.75;

        double salarioBruto = pagoOrdinario + pagoNocturno + pagoDominical;
        double descuento = Math.round(salarioBruto * 0.08 * 100.0) / 100.0;
        double salarioNeto = Math.round((salarioBruto - descuento) * 100.0) / 100.0;

        logger.info("Cálculo completado: Salario Bruto = {}, Descuento = {}, Salario Neto = {}",
                salarioBruto, descuento, salarioNeto);

        return new PagoResponse(
                Math.round(salarioBruto * 100.0) / 100.0,
                descuento,
                salarioNeto
        );
    }

    private double obtenerValorHora(ContratoTipo tipo) {
        return tipo == ContratoTipo.MEDIO_TIEMPO ? 12500 : 25000;
    }

    private void validarRequest(PagoRequest request) {
        if (request.getTipoContrato() == null) {
            logger.error("Validación fallida: tipo de contrato es nulo");
            throw new IllegalArgumentException("El tipo de contrato es obligatorio.");
        }

        if (request.getHorasTrabajadas() < 0) {
            logger.error("Validación fallida: horas trabajadas negativas ({})", request.getHorasTrabajadas());
            throw new IllegalArgumentException("Las horas trabajadas no pueden ser negativas.");
        }

        if (request.getHorasNocturnas() < 0) {
            logger.error("Validación fallida: horas nocturnas negativas ({})", request.getHorasNocturnas());
            throw new IllegalArgumentException("Las horas nocturnas no pueden ser negativas.");
        }

        if (request.getHorasDominicalesYFestivos() < 0) {
            logger.error("Validación fallida: horas dominicales y festivos negativas ({})", request.getHorasDominicalesYFestivos());
            throw new IllegalArgumentException("Las horas dominicales y festivos no pueden ser negativas.");
        }

        if (request.getHorasTrabajadas() == 0 &&
                request.getHorasNocturnas() == 0 &&
                request.getHorasDominicalesYFestivos() == 0) {
            logger.warn("Validación fallida: todas las horas son cero");
            throw new IllegalArgumentException("No se ha calculado nada. Todas las horas son cero.");
        }
    }
}
