package com.cajeroautomatico.cuenta.respository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "transaccion_atm")
public class TransaccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;
    private Long idCuenta;
    private String tipoTransaccion;
    private int idMonedaTransaccion;
    private float monto;
    private float tipoCambio;
    private int idMonedaCuenta;
    private float montoFinal;
    private String estado;
    private String usuario;
    private Timestamp fecha;
}
