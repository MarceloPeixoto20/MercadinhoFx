/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mercadinho;

import com.mycompany.mercadinho.model.Produtos;
import com.mycompany.mercadinho.ModuloConexao.ModuloConexao;
import static com.mycompany.mercadinho.ModuloConexao.ModuloConexao.conector;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 *
 * @author MICRO
 */
public class TelaCadPedidoController implements Initializable{
    
    @FXML
    private Button btnConcluir;

    @FXML
    private Button btnExcluirProduto;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableView<Produtos> tblTabelaProdutos;    
    
    @FXML
    private TableView<Produtos> tblTabelaPedido;
    
    @FXML
    private TableColumn<Produtos, String> tbl_col_idproduto;
    
    @FXML
    private TableColumn<Produtos, String> tbl_col_idproduto1;
    
    @FXML
    private TableColumn<Produtos, String> tbl_col_nomeproduto1;

    @FXML
    private TableColumn<Produtos, String> tbl_col_precoproduto1;

    @FXML
    private TableColumn<Produtos, String> tbl_col_nomeproduto;

    @FXML
    private TableColumn<Produtos, String> tbl_col_precoproduto;
    
    @FXML
    private TableColumn<Produtos, String> tbl_col_quantidade1;
    
    
    private Stage stage;
    private Stage stage1;
    private Scene scene;
    private Parent root;
    private Connection conexao;
    private PreparedStatement pst;
    private ResultSet rs;  
    private String[] sugestao;
    private Produtos produtoss = new Produtos();
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        88
        stage = new Stage();
        stage1 = new Stage();
        btnInserir.setDisable(true);
        addlistar();
        tblTabelaProdutos.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {            
            btnInserir.setDisable(false);        
    });
    }
    
    
    public Produtos SelecionarProduto(){                
        return tblTabelaProdutos.getSelectionModel().getSelectedItem();
        
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
                
                produtos.add(new Produtos(id_produto,nome_produto,preco_produto));
                
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
    private ObservableList<Produtos> addLista;
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
        produtoss = (Produtos) tblTabelaProdutos.getSelectionModel().getSelectedItem();
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

    public void excluir(){
        int linha = tblTabelaPedido.getSelectionModel().getSelectedIndex();        
        tblTabelaPedido.getItems().remove(linha);
    }
    
    private ObservableList<Produtos> Listaconcluir;
    public void concluir(){
        Listaconcluir = tblTabelaPedido.getItems();
        int totalqtd=0;
        int id_pedido=0;
        Double totalpreco = null;  
        Double Total=0.0;
        for(Produtos p : tblTabelaPedido.getItems()){
            totalqtd = p.getQuantidade();
            totalpreco = p.getPreco_produto(); 
            Total = (totalpreco * totalqtd)+ Total;
        }        
        try {
            Connection con = ModuloConexao.conector(); 
            int RETURN_GEENERATED_KEYS = Statement.RETURN_GENERATED_KEYS;
            String read1 = "INSERT INTO pedidos (data_pedido,total_pedido) VALUES (?,?)";
            PreparedStatement pst = con.prepareStatement(read1, RETURN_GEENERATED_KEYS);            
            java.util.Date date = new java.util.Date();
            Date sqldata = new Date(date.getTime());
            pst.setDate(1, sqldata);
            pst.setDouble(2, Total);
            pst.executeUpdate();
            ResultSet resultSet = pst.getGeneratedKeys();
            if(resultSet.next()){
                id_pedido = resultSet.getInt(1);
            }
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            String read= "INSERT INTO itenspedido (id_produto,id_pedido,quantidade_itens) VALUES (?,?,?)";
            Connection con = ModuloConexao.conector();            
            for(Produtos prod : tblTabelaPedido.getItems()){
                PreparedStatement pst = con.prepareStatement(read);
                int id_produto = prod.getId_produto();
                int quantidade_itens = prod.getQuantidade();
                pst.setInt(1, id_produto);
                pst.setInt(2, id_pedido);
                pst.setInt(3, quantidade_itens);                
                pst.executeUpdate();
            }
            con.close();            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmaçao");
            alert.setHeaderText(null);
            alert.setContentText("Pedido Cadastrado");
            alert.showAndWait();
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
