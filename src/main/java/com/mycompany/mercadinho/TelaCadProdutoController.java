/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mercadinho;

import com.mycompany.mercadinho.ModuloConexao.ModuloConexao;
import static com.mycompany.mercadinho.ModuloConexao.ModuloConexao.conector;
import com.mycompany.mercadinho.model.Produtos;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MICRO
 */
public class TelaCadProdutoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnCadastrar;
    
    @FXML
    private Button btnEditarProduto;

    @FXML
    private Button btnVoltar;
    
    @FXML
    private TableView<Produtos> tblTabelaProdutos;

    @FXML
    private TableColumn<Produtos, String> tbl_col_descricao_produto;

    @FXML
    private TableColumn<Produtos, String> tbl_col_estoqueproduto;

    @FXML
    private TableColumn<Produtos, String> tbl_col_idproduto;

    @FXML
    private TableColumn<Produtos, String> tbl_col_nomeproduto;

    @FXML
    private TableColumn<Produtos, String> tbl_col_precoproduto;

    @FXML
    private TextField txtDescricaoProduto;

    @FXML
    private TextField txtEstoqueProduto;

    @FXML
    private TextField txtNomeProduto;

    @FXML
    private TextField txtPrecoProduto;
    
    private Stage stage;
    private Stage stage1;
    private Scene scene;
    private Parent root;
    private Connection conexao;
    private PreparedStatement pst;
    private ResultSet rs;
    private Produtos produtos = new Produtos();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addlistar();
        stage = new Stage();
        stage1 = new Stage();
    }

    public void Cadastrar(ActionEvent event){        
        String NomeProduto = txtNomeProduto.getText();
        String DescricaoProduto = txtDescricaoProduto.getText();
        String PrecoProduto = txtPrecoProduto.getText();
        String EstoqueProduto = txtEstoqueProduto.getText();        
        if (NomeProduto.isEmpty() || DescricaoProduto.isEmpty() || PrecoProduto.isEmpty() || EstoqueProduto.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos");
            alert.showAndWait();
            return;
        }
        try {
            Double preco = Double.parseDouble(PrecoProduto);
            produtos.setNome_produto(NomeProduto);
            produtos.setDescricao_produto(DescricaoProduto);
            produtos.setPreco_produto(preco);
            produtos.setEstoque_produto(EstoqueProduto);
            
            String create = "INSERT INTO produtos (Nome_produto, Descricao_produto, Preco_produto, Estoque_produto)"
                + "VALUES(?,?,?,?) ";
        try {
            Connection con = conector();
            PreparedStatement pst = con.prepareStatement(create);
            pst.setString(1, produtos.getNome_produto());
            pst.setString(2, produtos.getDescricao_produto());
            pst.setDouble(3, produtos.getPreco_produto());
            pst.setString(4, produtos.getEstoque_produto());
            pst.executeUpdate();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
            txtNomeProduto.setText(null);
            txtDescricaoProduto.setText(null);
            txtPrecoProduto.setText(null);
            txtEstoqueProduto.setText(null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmaçao");
            alert.setHeaderText(null);
            alert.setContentText("Cadastrado");
            alert.showAndWait();
            addlistar();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public com.mycompany.mercadinho.model.Produtos SelecionarProduto(){
        btnEditarProduto.setDisable(false);        
        return tblTabelaProdutos.getSelectionModel().getSelectedItem();
        
    }
    public ArrayList<com.mycompany.mercadinho.model.Produtos> listarprodutos() {
        ArrayList<com.mycompany.mercadinho.model.Produtos> produtos = new ArrayList<>();
        String read = "SELECT * FROM produtos ORDER BY Nome_produto";        
        try {
            Connection con = ModuloConexao.conector();
            PreparedStatement pst = con.prepareStatement(read);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) { 
                int id_produto = rs.getInt(1);
                String nome_produto = rs.getString(2);
                String Descricao_produto = rs.getString(3);                
                Double preco_produto = rs.getDouble(4);
                String estoque_produto = rs.getString(5);
                
                produtos.add(new Produtos(id_produto,nome_produto,Descricao_produto,preco_produto,estoque_produto));
                
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
        tbl_col_descricao_produto.setCellValueFactory(new PropertyValueFactory<>("Descricao_produto"));               
        tbl_col_precoproduto.setCellValueFactory(new PropertyValueFactory<>("preco_produto"));               
        tbl_col_estoqueproduto.setCellValueFactory(new PropertyValueFactory<>("estoque_produto"));               
              
              
        
        tblTabelaProdutos.setItems(addLista);
    }
    @FXML
    public void TelaMenu (){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaMenu.fxml"));
            root = loader.load();          
            Scene telaCadastro = new Scene(root);
            stage = (Stage) btnCadastrar.getScene().getWindow();                
            stage1.setScene(telaCadastro);
            stage1.show();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Editar(ActionEvent Event){        
        String NomeProduto = txtNomeProduto.getText();
        String DescricaoProduto = txtDescricaoProduto.getText();
        String PrecoProduto = txtPrecoProduto.getText();
        String EstoqueProduto = txtEstoqueProduto.getText(); 
        if (NomeProduto.isEmpty() || DescricaoProduto.isEmpty() || PrecoProduto.isEmpty() || EstoqueProduto.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos");
            alert.showAndWait();
            return;
        }
        try {
            Double preco = Double.parseDouble(PrecoProduto);
            produtos.setNome_produto(NomeProduto);
            produtos.setDescricao_produto(DescricaoProduto);
            produtos.setPreco_produto(preco);
            produtos.setEstoque_produto(EstoqueProduto);
            
            String create = "UPDATE Produtos SET Nome_produto=?, Descricao_produto=?, Preco_produto=?, Estoque_produto=? WHERE id_produto=?";

            try {
                Connection con = conector();
                PreparedStatement pst = con.prepareStatement(create);
                pst.setString(1, produtos.getNome_produto());
                pst.setString(2, produtos.getDescricao_produto());
                pst.setDouble(3, produtos.getPreco_produto());
                pst.setString(4, produtos.getEstoque_produto());
                pst.setInt(5, produtos.getId_produto());
                pst.executeUpdate();
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
                       
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmaçao");
            alert.setHeaderText(null);
            alert.setContentText("Alterado");
            alert.showAndWait();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
