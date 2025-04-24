package com.example.calculo_salario.service;

import com.example.calculo_salario.dto.PagoRequest;
import com.example.calculo_salario.dto.PagoResponse;
import com.example.calculo_salario.enums.ContratoTipo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PagoServiceTest {

    private final PagoService pagoService = new PagoService();

    @Test
    void calcularPago_contratoMedioTiempo_conHorasOrdinarias() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.MEDIO_TIEMPO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        PagoResponse response = pagoService.calcularPago(request);

        double salarioBrutoEsperado = 40 * 12500;
        double descuentoEsperado = Math.round(salarioBrutoEsperado * 0.08 * 100.0) / 100.0;
        double salarioNetoEsperado = Math.round((salarioBrutoEsperado - descuentoEsperado) * 100.0) / 100.0;

        assertEquals(salarioBrutoEsperado, response.getSalarioBruto(), 0.01);
        assertEquals(descuentoEsperado, response.getDescuentoSeguridadSocial(), 0.01);
        assertEquals(salarioNetoEsperado, response.getSalarioNeto(), 0.01);
    }

    @Test
    void calcularPago_contratoTiempoCompleto_conHorasOrdinarias() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        PagoResponse response = pagoService.calcularPago(request);

        double salarioBrutoEsperado = 40 * 25000;
        double descuentoEsperado = Math.round(salarioBrutoEsperado * 0.08 * 100.0) / 100.0;
        double salarioNetoEsperado = Math.round((salarioBrutoEsperado - descuentoEsperado) * 100.0) / 100.0;

        assertEquals(salarioBrutoEsperado, response.getSalarioBruto(), 0.01);
        assertEquals(descuentoEsperado, response.getDescuentoSeguridadSocial(), 0.01);
        assertEquals(salarioNetoEsperado, response.getSalarioNeto(), 0.01);
    }

    @Test
    void calcularPago_contratoMedioTiempo_conHorasOrdinariasYNocturnas() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.MEDIO_TIEMPO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(10);
        request.setHorasDominicalesYFestivos(0);

        PagoResponse response = pagoService.calcularPago(request);

        double salarioBrutoEsperado = 40 * 12500 + 10 * 12500 * 1.35;
        double descuentoEsperado = Math.round(salarioBrutoEsperado * 0.08 * 100.0) / 100.0;
        double salarioNetoEsperado = Math.round((salarioBrutoEsperado - descuentoEsperado) * 100.0) / 100.0;

        assertEquals(salarioBrutoEsperado, response.getSalarioBruto(), 0.01);
        assertEquals(descuentoEsperado, response.getDescuentoSeguridadSocial(), 0.01);
        assertEquals(salarioNetoEsperado, response.getSalarioNeto(), 0.01);
    }

    @Test
    void calcularPago_contratoTiempoCompleto_conHorasOrdinariasYDominicales() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(5);

        PagoResponse response = pagoService.calcularPago(request);

        double salarioBrutoEsperado = 40 * 25000 + 5 * 25000 * 1.75;
        double descuentoEsperado = Math.round(salarioBrutoEsperado * 0.08 * 100.0) / 100.0;
        double salarioNetoEsperado = Math.round((salarioBrutoEsperado - descuentoEsperado) * 100.0) / 100.0;

        assertEquals(salarioBrutoEsperado, response.getSalarioBruto(), 0.01);
        assertEquals(descuentoEsperado, response.getDescuentoSeguridadSocial(), 0.01);
        assertEquals(salarioNetoEsperado, response.getSalarioNeto(), 0.01);
    }

    @Test
    void calcularPago_contratoMedioTiempo_conHorasNocturnasYDominicales() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.MEDIO_TIEMPO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(5);
        request.setHorasDominicalesYFestivos(3);

        PagoResponse response = pagoService.calcularPago(request);

        double salarioBrutoEsperado = 40 * 12500 + 5 * 12500 * 1.35 + 3 * 12500 * 1.75;
        double descuentoEsperado = Math.round(salarioBrutoEsperado * 0.08 * 100.0) / 100.0;
        double salarioNetoEsperado = Math.round((salarioBrutoEsperado - descuentoEsperado) * 100.0) / 100.0;

        assertEquals(salarioBrutoEsperado, response.getSalarioBruto(), 0.01);
        assertEquals(descuentoEsperado, response.getDescuentoSeguridadSocial(), 0.01);
        assertEquals(salarioNetoEsperado, response.getSalarioNeto(), 0.01);
    }


    @Test
    void calcularPago_horasTrabajadasNegativas_lanzaExcepcion() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(-5);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoService.calcularPago(request);
        });
        assertEquals("Las horas trabajadas no pueden ser negativas.", exception.getMessage());
    }

    @Test
    void calcularPago_tipoContratoNulo_lanzaExcepcion() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(null);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoService.calcularPago(request);
        });
        assertEquals("El tipo de contrato es obligatorio.", exception.getMessage());
    }

    @Test
    void calcularPago_redondeoSalario() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        PagoResponse response = pagoService.calcularPago(request);

        assertEquals(1000000.00, response.getSalarioBruto(), 0.01);
    }


    @Test
    void calcularPago_contratoTiempoCompleto_500HorasOrdinarias() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(500);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        PagoResponse response = pagoService.calcularPago(request);

        assertTrue(response.getSalarioBruto() > 0);
    }

    @Test
    void calcularPago_jsonValido() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.MEDIO_TIEMPO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        PagoResponse response = pagoService.calcularPago(request);

        assertNotNull(response.getSalarioBruto());
        assertNotNull(response.getDescuentoSeguridadSocial());
        assertNotNull(response.getSalarioNeto());
    }

    @Test
    void calcularPago_tiempoDeRespuesta() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.MEDIO_TIEMPO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        long startTime = System.currentTimeMillis();
        pagoService.calcularPago(request);
        long endTime = System.currentTimeMillis();

        assertTrue(endTime - startTime < 1000);
    }

    @Test
    void calcularPago_todosLosValoresCorrectos() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.MEDIO_TIEMPO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(5);
        request.setHorasDominicalesYFestivos(3);

        PagoResponse response = pagoService.calcularPago(request);

        assertEquals(40 * 12500 + 5 * 12500 * 1.35 + 3 * 12500 * 1.75, response.getSalarioBruto(), 0.01);
    }

    @Test
    void calcularPago_horasNocturnasNegativas_lanzaExcepcion() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(-1);
        request.setHorasDominicalesYFestivos(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoService.calcularPago(request);
        });
        assertEquals("Las horas nocturnas no pueden ser negativas.", exception.getMessage());
    }

    @Test
    void calcularPago_horasDominicalesYFestivasNegativas_lanzaExcepcion() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(40);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(-1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoService.calcularPago(request);
        });
        assertEquals("Las horas dominicales y festivos no pueden ser negativas.", exception.getMessage());
    }

    @Test
    void calcularPago_todasLasHorasCero_lanzaExcepcion() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(0);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoService.calcularPago(request);
        });
        assertEquals("No se ha calculado nada. Todas las horas son cero.", exception.getMessage());
    }

    @Test
    void calcularPago_horasNocturnasYDominicalesCero_lanzaExcepcion() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(0);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoService.calcularPago(request);
        });
        assertEquals("No se ha calculado nada. Todas las horas son cero.", exception.getMessage());
    }

    @Test
    void calcularPago_contratoTiempoCompleto_soloDominicales() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.TIEMPO_COMPLETO);
        request.setHorasTrabajadas(0);
        request.setHorasNocturnas(0);
        request.setHorasDominicalesYFestivos(4);

        PagoResponse response = pagoService.calcularPago(request);

        double valorHora = 25000;
        double esperado = 4 * valorHora * 1.75;
        double descuento = Math.round(esperado * 0.08 * 100.0) / 100.0;
        double neto = Math.round((esperado - descuento) * 100.0) / 100.0;

        assertEquals(esperado, response.getSalarioBruto(), 0.01);
        assertEquals(descuento, response.getDescuentoSeguridadSocial(), 0.01);
        assertEquals(neto, response.getSalarioNeto(), 0.01);
    }

    @Test
    void calcularPago_contratoMedioTiempo_valoresGrandes() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.MEDIO_TIEMPO);
        request.setHorasTrabajadas(500);
        request.setHorasNocturnas(50);
        request.setHorasDominicalesYFestivos(20);

        PagoResponse response = pagoService.calcularPago(request);

        assertTrue(response.getSalarioBruto() > 0);
        assertTrue(response.getDescuentoSeguridadSocial() > 0);
        assertTrue(response.getSalarioNeto() > 0);
    }

    @Test
    void calcularPago_contratoMedioTiempo_todasLasHoras() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(ContratoTipo.MEDIO_TIEMPO);
        request.setHorasTrabajadas(20);
        request.setHorasNocturnas(5);
        request.setHorasDominicalesYFestivos(2);

        PagoResponse response = pagoService.calcularPago(request);

        double valorHora = 12500;
        double esperado = 20 * valorHora + 5 * valorHora * 1.35 + 2 * valorHora * 1.75;
        double descuento = Math.round(esperado * 0.08 * 100.0) / 100.0;
        double neto = Math.round((esperado - descuento) * 100.0) / 100.0;

        assertEquals(esperado, response.getSalarioBruto(), 0.01);
        assertEquals(descuento, response.getDescuentoSeguridadSocial(), 0.01);
        assertEquals(neto, response.getSalarioNeto(), 0.01);
    }

    @Test
    void obtenerValorHora_paraMedioTiempo_yTiempoCompleto() throws Exception {
        var method = PagoService.class.getDeclaredMethod("obtenerValorHora", ContratoTipo.class);
        method.setAccessible(true);

        double valorMedio = (double) method.invoke(pagoService, ContratoTipo.MEDIO_TIEMPO);
        double valorCompleto = (double) method.invoke(pagoService, ContratoTipo.TIEMPO_COMPLETO);

        assertEquals(12500.0, valorMedio);
        assertEquals(25000.0, valorCompleto);
    }

}
