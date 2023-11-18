/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mercadinho.model;

/**
 *
 * @author MICRO
 */
public class Produtos {
    
    private int id_produto;
    private String Nome_produto;
    private String Descricao_produto;
    private Double Preco_produto;
    private String Estoque_produto;
    private int quantidade_itenspedido;
    private int id_itens;

    public Produtos() {
    }

    public Produtos(int id_produto, String Nome_produto, Double Preco_produto, int quantidade_itenspedido,int id_itens) {
        this.id_produto = id_produto;
        this.Nome_produto = Nome_produto;
        this.Preco_produto = Preco_produto;
        this.quantidade_itenspedido = quantidade_itenspedido;
        this.id_itens = id_itens;
    }

    public Produtos(int id_produto, String Nome_produto, Double Preco_produto) {
        this.id_produto = id_produto;
        this.Nome_produto = Nome_produto;
        this.Preco_produto = Preco_produto;
    }
    

    public Produtos(int id_produto, String Nome_produto, String Descricao_produto, Double Preco_produto, String Estoque_produto) {
        this.id_produto = id_produto;
        this.Nome_produto = Nome_produto;
        this.Descricao_produto = Descricao_produto;
        this.Preco_produto = Preco_produto;
        this.Estoque_produto = Estoque_produto;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return Nome_produto;
    }

    public void setNome_produto(String Nome_produto) {
        this.Nome_produto = Nome_produto;
    }

    public String getDescricao_produto() {
        return Descricao_produto;
    }

    public void setDescricao_produto(String Descricao_produto) {
        this.Descricao_produto = Descricao_produto;
    }

    public Double getPreco_produto() {
        return Preco_produto;
    }

    public void setPreco_produto(Double Preco_produto) {
        this.Preco_produto = Preco_produto;
    }

    public String getEstoque_produto() {
        return Estoque_produto;
    }

    public void setEstoque_produto(String Estoque_produto) {
        this.Estoque_produto = Estoque_produto;
    }

    public int getQuantidade() {
        return quantidade_itenspedido;
    }

    public void setQuantidade(int quantidade_itenspedido) {
        this.quantidade_itenspedido = quantidade_itenspedido;
    }

    public int getQuantidade_itenspedido() {
        return quantidade_itenspedido;
    }

    public void setQuantidade_itenspedido(int quantidade_itenspedido) {
        this.quantidade_itenspedido = quantidade_itenspedido;
    }

    public int getId_itens() {
        return id_itens;
    }

    public void setId_itens(int id_itens) {
        this.id_itens = id_itens;
    }
    
}
