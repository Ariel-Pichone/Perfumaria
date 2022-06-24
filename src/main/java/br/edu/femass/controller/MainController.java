package br.edu.femass.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button BtnCompra;

    @FXML
    private Button BtnVenda;

    @FXML
    private Button BtnPerfume;

    @FXML
    private Button BtnRelatorio;

    @FXML
    private void BtnCompra_Action(ActionEvent evento) {
        abrirTela("Compra", "Compras");
    }

    @FXML
    private void BtnVenda_Action(ActionEvent evento) {
        abrirTela("Venda", "Vendas");
    }

    @FXML
    private void BtnPerfume_Action(ActionEvent evento) {
        abrirTela("Perfume", "Perfumes");
    }

    @FXML
    private void BtnRelatorio_Action(ActionEvent evento) {
        abrirTela("Relatorio", "Relatorios");
    }

    private void abrirTela(String nome, String titulo){
        try{
            Parent root = null;
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/" + nome + ".fxml")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            scene.getRoot().setStyle("-fx-font-family: 'serif'");

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
