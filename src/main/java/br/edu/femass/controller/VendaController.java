package br.edu.femass.controller;

import br.edu.femass.dao.*;
import br.edu.femass.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VendaController implements Initializable {
    private final PerfumeDao perfumeDao = new PerfumeDao();
    private final VendaDao vendaDao = new VendaDao();
    private final DetalheVendaDao detalheVendaDao = new DetalheVendaDao();
    @FXML
    private ListView<Perfume> LstPerfume;
    @FXML
    private ListView<Venda> LstVenda;
    @FXML
    private ListView<DetalheVenda> LstDV;
    @FXML
    private Button BtnIncluir;
    @FXML
    private Button BtnExcluir;
    @FXML
    private Button BtnCancelar;
    @FXML
    private Button BtnAdicionar;
    @FXML
    private TextField TxtQuantidade;
    @FXML
    private TextField TxtPreco;
    @FXML
    private Label LblQuantidade;
    @FXML
    private Label LblPreco;
    @FXML
    private void LstPerfume_MouseClicked (javafx.scene.input.MouseEvent evento){}
    @FXML
    private void LstPerfume_KeyPressed(javafx.scene.input.KeyEvent evento) {}
    @FXML
    private void LstDC_MouseClicked (javafx.scene.input.MouseEvent evento){}
    @FXML
    private void LstDC_KeyPressed(javafx.scene.input.KeyEvent evento){}
    @FXML
    private void BtnIncluir_Action (ActionEvent evento){
        habilitarInterface(true);
        Venda venda = new Venda();
        venda.setDataVenda(LocalDate.now());
        try{
            vendaDao.gravar(venda);
        } catch (Exception e){
            e.printStackTrace();
        }
        atualizarListaVenda();
    }
    @FXML
    private void BtnExcluir_Action (ActionEvent evento){
        Venda venda = LstVenda.getSelectionModel().getSelectedItem();
        if (venda==null) return;;
        try {
            vendaDao.excluir(venda);
        } catch (Exception e) {
            e.printStackTrace();
        }
        habilitarInterface(false);
        atualizarListaDV();
    }
    @FXML
    private void BtnCancelar_Action (ActionEvent evento) {
        Venda venda = LstVenda.getSelectionModel().getSelectedItem();
        if (venda==null) return;;
        try {
            vendaDao.excluir(venda);
        } catch (Exception e) {
            e.printStackTrace();
        }
        habilitarInterface(false);
        atualizarListaDV();
    }
    @FXML
    private void BtnAdicionar_Action (ActionEvent evento) {
        DetalheVenda detalheVenda = new DetalheVenda();
        detalheVenda.setPerfume(LstPerfume.getSelectionModel().getSelectedItem().getId());
        detalheVenda.setPedido(LstVenda.getSelectionModel().getSelectedItem().getNrPedido());
        detalheVenda.setQuantidade(Integer.parseInt(TxtQuantidade.getText()));
        detalheVenda.setPrecoVenda(Double.parseDouble(TxtPreco.getText()));

        try {
            detalheVendaDao.gravar(detalheVenda);
        } catch (Exception e){
            e.printStackTrace();
        }
        atualizarListaDV();
        limparTela();
    }

    private void limparTela(){
        TxtQuantidade.setText("");
        TxtPreco.setText("");
    }
    private void habilitarInterface(Boolean incluir){
        LstPerfume.setDisable(!incluir);
        LstVenda.setDisable(!incluir);
        LstDV.setDisable(!incluir);
        LblQuantidade.setDisable(!incluir);
        TxtQuantidade.setDisable(!incluir);
        LblPreco.setDisable(!incluir);
        TxtPreco.setDisable(!incluir);
        BtnCancelar.setDisable(!incluir);
        BtnAdicionar.setDisable(!incluir);
        BtnExcluir.setDisable(!incluir);
        BtnIncluir.setDisable(incluir);
    }
    private void atualizarListaPerfume(){
        List<Perfume> perfumes;
        try{
            perfumes = perfumeDao.listar();
        } catch (Exception e) {
            perfumes = new ArrayList<>();
        }
        ObservableList<Perfume> perfumes1 = FXCollections.observableArrayList(perfumes);
        LstPerfume.setItems(perfumes1);
    }
    private void atualizarListaVenda(){
        List<Venda> vendas;
        try{
            vendas = vendaDao.listar();
        } catch (Exception e) {
            vendas = new ArrayList<>();
        }
        //Mostra somente o último pedido onde serão adicionados os produtos:
        ObservableList<Venda> vendas1 = FXCollections.observableArrayList(vendas.get(vendas.size()-1));
        LstVenda.setItems(vendas1);
    }
    private void atualizarListaDV(){
        List<DetalheVenda> detalheVendas;
        try{
            detalheVendas = detalheVendaDao.listar();
        } catch (Exception e) {
            detalheVendas = new ArrayList<>();
        }
        ObservableList<DetalheVenda> detalheVendas1 = FXCollections.observableArrayList(detalheVendas);
        //Passa somente os dados do pedido que está a mostra na tela.
        LstDV.setItems(detalheVendas1.filtered(t -> t.getPedido().equals(LstVenda.getSelectionModel().getSelectedItem().getNrPedido())));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarListaPerfume();
    }
}
