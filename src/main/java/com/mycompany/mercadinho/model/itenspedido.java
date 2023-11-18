/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mercadinho.model;



/**
 *
 * @author MICRO
 */
public class itenspedido {
    
    private int id_itens;
    private int id_produto;
    private int id_pedido;
    private int quantidade_itens;

    public itenspedido() {
    }

    public itenspedido(int id_itens, int id_produto, int id_pedido, int quantidade_itens) {
        this.id_itens = id_itens;
        this.id_produto = id_produto;
        this.id_pedido = id_pedido;
        this.quantidade_itens = quantidade_itens;
    }

    public int getId_itens() {
        return id_itens;
    }

    public void setId_itens(int id_itens) {
        this.id_itens = id_itens;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getQuantidade_itens() {
        return quantidade_itens;
    }

    public void setQuantidade_itens(int quantidade_itens) {
        this.quantidade_itens = quantidade_itens;
    }
    
    
}
