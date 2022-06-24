package br.edu.femass.controller;

import br.edu.femass.dao.CompraDao;
import br.edu.femass.dao.DetalheCompraDao;
import br.edu.femass.dao.PerfumeDao;
import br.edu.femass.model.Compra;
import br.edu.femass.model.DetalheCompra;
import br.edu.femass.model.Perfume;
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

public class CompraController implements Initializable {
    private final PerfumeDao perfumeDao = new PerfumeDao();
    private final CompraDao compraDao = new CompraDao();
    private final DetalheCompraDao detalheCompraDao = new DetalheCompraDao();
    @FXML
    private ListView<Perfume> LstPerfume;
    @FXML
    private ListView<Compra> LstCompra;
    @FXML
    private ListView<DetalheCompra> LstDC;
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
        Compra compra = new Compra();
        compra.setDataCompra(LocalDate.now());
        try{
            compraDao.gravar(compra);
        } catch (Exception e){
            e.printStackTrace();
        }
        atualizarListaCompra();
    }
    @FXML
    private void BtnExcluir_Action (ActionEvent evento){
        Compra compra = LstCompra.getSelectionModel().getSelectedItem();
        if (compra==null) return;;
        try {
            compraDao.excluir(compra);
        } catch (Exception e) {
            e.printStackTrace();
        }
        habilitarInterface(false);
        atualizarListaDC();
    }
    @FXML
    private void BtnCancelar_Action (ActionEvent evento) {
        Compra compra = LstCompra.getSelectionModel().getSelectedItem();
        if (compra==null) return;;
        try {
            compraDao.excluir(compra);
        } catch (Exception e) {
            e.printStackTrace();
        }
        habilitarInterface(false);
        atualizarListaDC();
    }
    @FXML
    private void BtnAdicionar_Action (ActionEvent evento) {
        DetalheCompra detalheCompra = new DetalheCompra();
        detalheCompra.setPerfume(LstPerfume.getSelectionModel().getSelectedItem().getId());
        detalheCompra.setPedido(LstCompra.getSelectionModel().getSelectedItem().getNrPedido());
        detalheCompra.setQuantidade(Integer.parseInt(TxtQuantidade.getText()));
        detalheCompra.setPrecoCompra(Double.parseDouble(TxtPreco.getText()));

        try {
            detalheCompraDao.gravar(detalheCompra);
        } catch (Exception e){
            e.printStackTrace();
        }
        atualizarListaDC();
        limparTela();
    }

    private void limparTela(){
        TxtQuantidade.setText("");
        TxtPreco.setText("");
    }
    private void habilitarInterface(Boolean incluir){
        LstPerfume.setDisable(!incluir);
        LstCompra.setDisable(!incluir);
        LstDC.setDisable(!incluir);
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
    private void atualizarListaCompra(){
        List<Compra> compras;
        try{
            compras = compraDao.listar();
        } catch (Exception e) {
            compras = new ArrayList<>();
        }
        //Mostra somente o último pedido onde serão adicionados os produtos:
        ObservableList<Compra> compras1 = FXCollections.observableArrayList(compras.get(compras.size()-1));
        LstCompra.setItems(compras1);
    }
    private void atualizarListaDC(){
        List<DetalheCompra> detalheCompras;
        try{
            detalheCompras = detalheCompraDao.listar();
        } catch (Exception e) {
            detalheCompras = new ArrayList<>();
        }
        ObservableList<DetalheCompra> detalheCompras1 = FXCollections.observableArrayList(detalheCompras);
        //Passa somente os dados do pedido que está a mostra na tela.
        LstDC.setItems(detalheCompras1.filtered(t -> t.getPedido().equals(LstCompra.getSelectionModel().getSelectedItem().getNrPedido())));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarListaPerfume();
    }
}
