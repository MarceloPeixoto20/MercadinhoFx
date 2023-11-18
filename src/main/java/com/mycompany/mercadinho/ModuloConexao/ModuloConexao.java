/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mercadinho.ModuloConexao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author MICRO
 */
public class ModuloConexao {
    
    public static Connection conector() {
        String url = "jdbc:mysql://localhost:3306/mercadinho";
        String user = "root";
        String password = "";

        try {
            Connection conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
