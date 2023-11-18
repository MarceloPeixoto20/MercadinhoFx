/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mercadinho.model;

import java.sql.Date;

/**
 *
 * @author MICRO
 */
public class Pedidos {
    
    private String id_pedido;
    private Date data_pedido;
    private Double total_pedido;    

    public Pedidos() {
    }

    public Pedidos(String id_pedido, Date data_pedido, Double total_pedido) {
        this.id_pedido = id_pedido;        
        this.data_pedido = data_pedido;
        this.total_pedido = total_pedido;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }    

    public Date getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(Date data_pedido) {
        this.data_pedido = data_pedido;
    }

    public Double getTotal_pedido() {
        return total_pedido;
    }

    public void setTotal_pedido(Double total_pedido) {
        this.total_pedido = total_pedido;
    }
       
}
