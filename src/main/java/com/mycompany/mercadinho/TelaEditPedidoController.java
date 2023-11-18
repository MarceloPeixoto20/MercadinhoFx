/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mercadinho;

import com.mycompany.mercadinho.ModuloConexao.ModuloConexao;
import com.mycompany.mercadinho.model.itenspedido;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MICRO
 */
public class TelaEditPedidoController implements Initializable {

    @FXML
    private Button btnConcluir;

    @FXML
    private Button btnExcluirProduto;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnVoltar;
    
    @FXML
    private Label lblId_pedido;

    @FXML
    private TableView<com.mycompany.mercadinho.model.Produtos> tblTabelaProdutos;    
    
    @FXML
    private TableView<com.mycompany.mercadinho.model.Produtos> tblTabelaPedido;
    
    @FXML
    private TableColumn<com.mycompany.mercadinho.model.Produtos, String> tbl_col_idproduto;
    
    @FXML
    private TableColumn<com.mycompany.mercadinho.model.Produtos, String> tbl_col_idproduto1;
    
    @FXML
    private TableColumn<com.mycompany.mercadinho.model.Produtos, String> tbl_col_nomeproduto1;

    @FXML
    private TableColumn<com.mycompany.mercadinho.model.Produtos, String> tbl_col_precoproduto1;

    @FXML
    private TableColumn<com.mycompany.mercadinho.model.Produtos, String> tbl_col_nomeproduto;

    @FXML
    private TableColumn<com.mycompany.mercadinho.model.Produtos, String> tbl_col_precoproduto;
    
    @FXML
    private TableColumn<com.mycompany.mercadinho.model.Produtos, String> tbl_col_quantidade1;
    
    @FXML
    private TableColumn<com.mycompany.mercadinho.model.Produtos, String> tbl_col_id_itens;
    
    private Stage stage;
    private Stage stage1;
    private Scene scene;
    private Parent root;
    private Connection conexao;
    private PreparedStatement pst;
    private ResultSet rs;  
    private String[] sugestao;
    private com.mycompany.mercadinho.model.Produtos produtoss = new com.mycompany.mercadinho.model.Produtos();
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        stage = new Stage();
        stage1 = new Stage();
        btnInserir.setDisable(true);
        addlistar();        
        tblTabelaProdutos.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {            
            btnInserir.setDisable(false);        
    });
        
    }    
    private int idpedido;
    private ObservableList<com.mycompany.mercadinho.model.Produtos> addListaeditar;
    public void editar(int id){
        String idp = Integer.toString(id);
        lblId_pedido.setText(idp);
        ObservableList<com.mycompany.mercadinho.model.Produtos> data = FXCollections.observableArrayList();
        try {
            ArrayList<com.mycompany.mercadinho.model.Produtos> produtos = listarprodutoseditar(id);             
            data.addAll(produtos);            
        } catch (Exception e){
            e.printStackTrace();
        }
        addListaeditar = data;           
        //System.out.println(addLista.get(0).getCPF());
        tbl_col_idproduto1.setCellValueFactory(new PropertyValueFactory<>("id_produto"));
        tbl_col_nomeproduto1.setCellValueFactory(new PropertyValueFactory<>("nome_produto"));
        tbl_col_precoproduto1.setCellValueFactory(new PropertyValueFactory<>("preco_produto"));
        tbl_col_quantidade1.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tbl_col_id_itens.setCellValueFactory(new PropertyValueFactory<>("id_itens"));
        tblTabelaPedido.setItems(addListaeditar);
        data1 = data;
    }
    
    public ArrayList<com.mycompany.mercadinho.model.Produtos> listarprodutoseditar(int idpe) {
        ArrayList<com.mycompany.mercadinho.model.Produtos> produtos = new ArrayList<>();        
        String read = "SELECT itenspedido.id_produto, produtos.Nome_produto, produtos.Preco_produto, itenspedido.quantidade_itens, itenspedido.id_itens FROM itenspedido INNER JOIN produtos ON itenspedido.id_produto = produtos.id_produto WHERE itenspedido.id_pedido=? ORDER BY Nome_produto";        
        try {
            conexao = ModuloConexao.conector();
            pst = conexao.prepareStatement(read);
            pst.setInt(1, idpe);
            rs = pst.executeQuery();
            while (rs.next()) {                 
                int id_produto = rs.getInt(1);
                String nome_produto = rs.getString(2);
                Double preco_produto = rs.getDouble(3);
                int quantidade_itenspedido = rs.getInt(4);
                int id_itens = rs.getInt(5);
                produtos.add(new com.mycompany.mercadinho.model.Produtos(id_produto,nome_produto,preco_produto,quantidade_itenspedido,id_itens));
                
            }
            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return produtos;
    }
    
    
    public com.mycompany.mercadinho.model.Produtos SelecionarProduto(){                
        return tblTabelaProdutos.getSelectionModel().getSelectedItem();        
    }    
    
    public void excluir(){
        com.mycompany.mercadinho.model.Produtos exproduto = tblTabelaPedido.getSelectionModel().getSelectedItem();                
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmaçao");
            alert.setHeaderText(null);
            alert.setContentText("Voce tem certeza que dejesa Excluir ? (esse Item ele não podera voltar depois)");
            ButtonType buttonTypeOK = new ButtonType("Sim");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            ButtonType resultado = alert.showAndWait().orElse(ButtonType.CANCEL);            
            if(resultado==buttonTypeOK){                
                String delete = "DELETE FROM itenspedido WHERE id_itens =?";                
                try {
                    Connection con = ModuloConexao.conector();
                    PreparedStatement pst = con.prepareStatement(delete);                   
                    pst.setInt(1, exproduto.getId_itens());
                    pst.executeUpdate();                    
                    con.close();
                    int linha = tblTabelaPedido.getSelectionModel().getSelectedIndex();        
                    tblTabelaPedido.getItems().remove(linha);
                } catch (Exception e) {
                    e.printStackTrace();
                }                
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exito");
                alert.setHeaderText(null);
                alert.setContentText("Deletado");
                alert.showAndWait();
            }else{                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    
    
    public ArrayList<com.mycompany.mercadinho.model.Produtos> listarprodutos() {
        ArrayList<com.mycompany.mercadinho.model.Produtos> produtos = new ArrayList<>();
        String read = "SELECT id_produto, Nome_produto, Preco_produto FROM produtos ORDER BY Nome_produto";        
        try {
            Connection con = ModuloConexao.conector();
            PreparedStatement pst = con.prepareStatement(read);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {                 
                int id_produto = rs.getInt(1);
                String nome_produto = rs.getString(2);
                Double preco_produto = rs.getDouble(3);
                
                produtos.add(new com.mycompany.mercadinho.model.Produtos(id_produto,nome_produto,preco_produto));
                
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return produtos;
    }
    
    public ObservableList<com.mycompany.mercadinho.model.Produtos> listar(){
        ObservableList<com.mycompany.mercadinho.model.Produtos> data = FXCollections.observableArrayList();
        try {
            ArrayList<com.mycompany.mercadinho.model.Produtos> produtos = listarprodutos();            
            data.addAll(produtos);            
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
        
    }  
    private ObservableList<com.mycompany.mercadinho.model.Produtos> addLista;
    public void addlistar(){        
        addLista = listar();           
        //System.out.println(addLista.get(0).getCPF());
        tbl_col_idproduto.setCellValueFactory(new PropertyValueFactory<>("id_produto"));
        tbl_col_nomeproduto.setCellValueFactory(new PropertyValueFactory<>("nome_produto"));
        tbl_col_precoproduto.setCellValueFactory(new PropertyValueFactory<>("preco_produto"));               
        tblTabelaProdutos.setItems(addLista);
    }
    
    private ObservableList<com.mycompany.mercadinho.model.Produtos> data1 = FXCollections.observableArrayList();
    public void inserir(){        
        produtoss = (com.mycompany.mercadinho.model.Produtos) tblTabelaProdutos.getSelectionModel().getSelectedItem();
        TextField txtQuantidade = new TextField();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quantidade");
        alert.setHeaderText("Quantidade a ser digitada");
        alert.setContentText("Digite a quantidade");
        alert.getDialogPane().setContent(txtQuantidade);
        Optional<ButtonType> result = alert.showAndWait();
        String quant = txtQuantidade.getText();
        int quantidade = Integer.parseInt(quant);
        produtoss.setQuantidade(quantidade);
        if(quantidade > 0){
            data1.addAll(produtoss);
            addLista = data1;
            tbl_col_idproduto1.setCellValueFactory(new PropertyValueFactory<>("id_produto"));
            tbl_col_nomeproduto1.setCellValueFactory(new PropertyValueFactory<>("nome_produto"));
            tbl_col_precoproduto1.setCellValueFactory(new PropertyValueFactory<>("preco_produto"));         
            tbl_col_quantidade1.setCellValueFactory(new PropertyValueFactory<>("quantidade"));             
            tblTabelaPedido.setItems(data1);    
        }else if(quantidade <= 0){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Digite uma quantidade válida");
            alert.showAndWait();
            return;
        }
        
          
    }
    
    private ObservableList<com.mycompany.mercadinho.model.Produtos> Listaconcluir = FXCollections.observableArrayList();
    public void concluir(){        
        Listaconcluir = tblTabelaPedido.getItems();
        int totalqtd=0;
        int id_pedido= Integer.parseInt(lblId_pedido.getText());
        Double totalpreco = null;  
        Double Total=0.0;
        for(com.mycompany.mercadinho.model.Produtos p : tblTabelaPedido.getItems()){
            totalqtd = p.getQuantidade();
            totalpreco = p.getPreco_produto(); 
            Total = (totalpreco * totalqtd)+ Total;
        }        
        try {
            Connection con = ModuloConexao.conector();             
            String read1 = "UPDATE pedidos SET data_pedido=?, total_pedido=? WHERE id_pedido=?";
            PreparedStatement pst = con.prepareStatement(read1);            
            java.util.Date date = new java.util.Date();
            Date sqldata = new Date(date.getTime());
            pst.setDate(1, sqldata);
            pst.setDouble(2, Total);
            pst.setInt(3, id_pedido);
            pst.executeUpdate();            
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ArrayList<com.mycompany.mercadinho.model.Produtos> pedidoExistente = new ArrayList<>();
        try {
            Connection con = ModuloConexao.conector();
            String read = "SELECT produtos.id_produto, produtos.Nome_produto, produtos.Preco_produto, itenspedido.quantidade_itens, itenspedido.id_itens FROM itenspedido INNER JOIN produtos ON itenspedido.id_produto = produtos.id_produto WHERE id_pedido = ?";
            PreparedStatement pst = con.prepareStatement(read);
            pst.setInt(1, id_pedido);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id_produto = rs.getInt(1);
                String nome_produto = rs.getString(2);
                Double preco_produto = rs.getDouble(3);
                int quantidade_itenspedido = rs.getInt(4);
                int id_itens = rs.getInt(5);
                pedidoExistente.add(new com.mycompany.mercadinho.model.Produtos(id_produto,nome_produto,preco_produto,quantidade_itenspedido,id_itens));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        // Compara os dados dos pedidos
        for (com.mycompany.mercadinho.model.Produtos itensproduto : Listaconcluir) {
            boolean existe = false;
            for (com.mycompany.mercadinho.model.Produtos itemPedidoExistente : pedidoExistente) {
                if (itensproduto.getId_itens()== itemPedidoExistente.getId_itens()) {
                    existe = true;                    
                    break;
                }
            }
            if (existe==false) {
                try {
                    String read= "INSERT INTO itenspedido (id_produto,id_pedido,quantidade_itens) VALUES (?,?,?)";
                    Connection con = ModuloConexao.conector();
                    PreparedStatement pst = con.prepareStatement(read);                    
                    pst.setInt(1, itensproduto.getId_produto());
                    pst.setInt(2, id_pedido);
                    pst.setInt(3, itensproduto.getQuantidade_itenspedido());                
                    pst.executeUpdate();                    
                    con.close();           
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmaçao");
            alert.setHeaderText(null);
            alert.setContentText("Pedido Atualizado");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaMenu.fxml"));
            root = loader.load();          
            Scene telaCadastro = new Scene(root);
            stage = (Stage) btnConcluir.getScene().getWindow();                
            stage1.setScene(telaCadastro);
            stage1.show();
            stage.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    public void TelaMenu (){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaMenu.fxml"));
            root = loader.load();          
            Scene telaCadastro = new Scene(root);
            stage = (Stage) btnConcluir.getScene().getWindow();                
            stage1.setScene(telaCadastro);
            stage1.show();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
