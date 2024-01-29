package com.cajeroautomatico.cuenta.service;

import com.cajeroautomatico.cuenta.dto.*;

import com.cajeroautomatico.cuenta.respository.TransaccionEntity;
import com.cajeroautomatico.cuenta.respository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class TransaccionService {
    int respOK = 200;

    String urlGestionCuenta = "http://cuenta:8085/v1/cuenta";
    String urlGestionTipoDeCambio = "http://tasa:8084/v1/tipo-cambio";
    String urlGestionCliente = "http://cliente:8083/v1/cliente";
    String urlGestionTransaccion = "http://transaccion:8082/v1/transaccion";


    @Autowired
    TransaccionRepository transaccionRepository;

    public TransaccionATMResponse transaccionATM(TransaccionATMRequest request) {

        var restTemplate = new RestTemplate();
        ResponseEntity<CuentaResponse> responseCuenta = restTemplate.getForEntity(urlGestionCuenta + "/{idcuenta}",
                CuentaResponse.class, request.getIdCuenta());

        if (responseCuenta.getStatusCode().value() == respOK) {
            CuentaResponse cuentaResponse = responseCuenta.getBody();
            ResponseEntity<TipoDeCambioResponse> responseTasa = restTemplate.getForEntity(urlGestionTipoDeCambio + "/{idmoneda}",
                    TipoDeCambioResponse.class, request.getIdMonedaTransaccion());

            if (responseTasa.getStatusCode().value() == respOK) {
                float montoFinal = 0.0f;
                TipoDeCambioResponse TipoDeCambioResponse = responseTasa.getBody();
                float tipoCambio = (request.getTipoTransaccion().equals("debito")) ? TipoDeCambioResponse.getTipoCambioCompra() : TipoDeCambioResponse.getTipoCambioVenta();

                if (request.getIdMonedaTransaccion() == cuentaResponse.getIdMoneda()) {
                    montoFinal = request.getMonto();
                } else {
                    montoFinal = (request.getIdMonedaTransaccion() == 1 && cuentaResponse.getIdMoneda() == 0) ? request.getMonto() * tipoCambio : request.getMonto() / tipoCambio;
                }

                if (request.getTipoTransaccion().equals("credito")) {
                    float diferencia = cuentaResponse.getSaldo() - montoFinal;
                    if (diferencia < 0) {
                        return TransaccionATMResponse.builder().montoFinal(diferencia).build();
                    }
                }

                var transaccionEntity = TransaccionEntity.builder()
                        .idCuenta(request.getIdCuenta())
                        .tipoTransaccion(request.getTipoTransaccion())
                        .idMonedaTransaccion(request.getIdMonedaTransaccion())
                        .monto(request.getMonto())
                        .tipoCambio(tipoCambio)
                        .idMonedaCuenta(cuentaResponse.getIdMoneda())
                        .montoFinal(montoFinal)
                        .fecha(new Timestamp((new java.util.Date()).getTime()))
                        .build();
                actualizarSaldoCuenta(cuentaResponse, montoFinal, request.getTipoTransaccion());
                return convertirEntityAResponse(transaccionRepository.save(transaccionEntity));
            }
        }
        return TransaccionATMResponse.builder().build();
    }

    public TransaccionATMResponse convertirEntityAResponse(TransaccionEntity entity) {
        return TransaccionATMResponse.builder()
                .idTransaccion(entity.getIdTransaccion())
                .idCuenta(entity.getIdCuenta())
                .tipoTransaccion(entity.getTipoTransaccion())
                .idMonedaTransaccion(entity.getIdMonedaTransaccion())
                .monto(entity.getMonto())
                .tipoCambio(entity.getTipoCambio())
                .idMonedaCuenta(entity.getIdMonedaCuenta())
                .montoFinal(entity.getMontoFinal())
                .fecha(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entity.getFecha()))
                .build();

    }

    public void actualizarSaldoCuenta(CuentaResponse cuentaResponse, float montoFinal, String tipoOperacion) {
        float saldoCuenta =
                (tipoOperacion.equals("debito")) ? cuentaResponse.getSaldo() - montoFinal : cuentaResponse.getSaldo() + montoFinal;
        // Todo: Llamar a Gestion de cuentas para actualizar el saldo
    }
}
