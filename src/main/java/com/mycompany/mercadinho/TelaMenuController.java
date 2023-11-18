/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mercadinho;

import com.mycompany.mercadinho.ModuloConexao.ModuloConexao;
import com.mycompany.mercadinho.model.Pedidos;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author MICRO
 */
public class TelaMenuController implements Initializable {
    
    @FXML
    private Button btnCadastrarPedido;

    @FXML
    private Button btnCadastrarProduto;

    @FXML
    private Button btnEditarPedido;
    
    @FXML
    private Button btnExcluir;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<Pedidos> tblTabelaPedido;

    @FXML
    private TableColumn<Pedidos, Date> tbl_col_DataPedido;

    @FXML
    private TableColumn<Pedidos, String> tbl_col_TotalPedido;

    @FXML
    private TableColumn<Pedidos, String> tbl_col_idPedido;
    
    private Stage stage;
    private Stage stage1;
    private Scene scene;
    private Parent root;
    private Connection conexao;
    private PreparedStatement pst;
    private ResultSet rs;
    private Pedidos pedido = new Pedidos();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        stage = new Stage();
        stage1 = new Stage();
        btnEditarPedido.setDisable(true);
        addlistar();
        tblTabelaPedido.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            btnEditarPedido.setDisable(false);
    });
    }
    
    
    public Pedidos SelecionarPedido(){
        btnEditarPedido.setDisable(false);        
        return tblTabelaPedido.getSelectionModel().getSelectedItem();
        
    }
    public ArrayList<Pedidos> listarpedidos() {
        ArrayList<Pedidos> pedidos = new ArrayList<>();
        String read = "SELECT * FROM pedidos";        
        try {
            Connection con = ModuloConexao.conector();
            PreparedStatement pst = con.prepareStatement(read);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {                
                String id_pedido = rs.getString(1);
                Date data_pedido = rs.getDate(2);
                Double total_pedido = rs.getDouble(3);
                
                pedidos.add(new Pedidos(id_pedido,data_pedido,total_pedido));
                
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }
    
    public ObservableList<Pedidos> listar(){
        ObservableList<Pedidos> data = FXCollections.observableArrayList();
        try {
            ArrayList<Pedidos> clientes = listarpedidos();            
            data.addAll(clientes);            
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
        
    }    
    
    private ObservableList<Pedidos> addLista;
    public void addlistar(){        
        addLista = listar();           
        //System.out.println(addLista.get(0).getCPF());
        tbl_col_idPedido.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));        
        tbl_col_TotalPedido.setCellValueFactory(new PropertyValueFactory<>("total_pedido"));               
        tbl_col_DataPedido.setCellValueFactory(cellData -> {
        SimpleObjectProperty<java.sql.Date> property = new SimpleObjectProperty<>(java.sql.Date.valueOf(cellData.getValue().getData_pedido().toLocalDate()));
        return property;
        });        
              
        
        tblTabelaPedido.setItems(addLista);
    }
    
    @FXML
    public void CadastrarProduto (){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaCadProduto.fxml"));
            root = loader.load();          
            Scene telaCadastro = new Scene(root);
            stage = (Stage) btnCadastrarProduto.getScene().getWindow();                
            stage1.setScene(telaCadastro);
            stage1.show();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void CadastrarPedido (){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaCadPedido.fxml"));
            root = loader.load();          
            Scene telaCadastrope = new Scene(root);
            stage = (Stage) btnCadastrarProduto.getScene().getWindow();                
            stage1.setScene(telaCadastrope);
            stage1.show();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void EditarPedido (){
        try {
            Pedidos p = new Pedidos();
            p = SelecionarPedido();
            int idpedido = Integer.parseInt(p.getId_pedido());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaEditPedido.fxml"));
            root = loader.load();
            TelaEditPedidoController Editar = new TelaEditPedidoController();
            Editar = loader.getController();                
            Editar.editar(idpedido); 
            Scene telaCadastrope = new Scene(root);
            stage = (Stage) btnCadastrarProduto.getScene().getWindow();                
            stage1.setScene(telaCadastrope);
            stage1.show();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void Excluir(){
        pedido = SelecionarPedido();
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmaçao");
            alert.setHeaderText(null);
            alert.setContentText("Voce tem certeza que dejesa Excluir esse Pedido");
            ButtonType buttonTypeOK = new ButtonType("Sim");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            ButtonType resultado = alert.showAndWait().orElse(ButtonType.CANCEL);            
            if(resultado==buttonTypeOK){
                addLista.remove(pedido);
                String delete = "DELETE FROM pedidos WHERE id_pedido =?";
                String deleteitens = "DELETE FROM itenspedido WHERE id_pedido=?";
                try {
                    Connection con = ModuloConexao.conector();
                    PreparedStatement pst = con.prepareStatement(delete);
                    PreparedStatement pst1 = con.prepareStatement(deleteitens);
                    pst.setString(1, pedido.getId_pedido());
                    pst1.setString(1, pedido.getId_pedido());
                    pst1.executeUpdate();
                    pst.executeUpdate();                    
                    con.close();
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
        }
    }
}
