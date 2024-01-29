package com.cajeroautomatico.cuenta.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionATMRequest {
    private Long idCuenta;
    private String tipoTransaccion;
    private int idMonedaTransaccion;
    private float monto;
}
