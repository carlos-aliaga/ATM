package com.cajeroautomatico.cuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuentaRequest {
    private int id;
    private  String numeroCuenta;
    private int idMoneda;
    private int idCliente;
    private  float saldo;
}
