package br.edu.femass.controller;

import br.edu.femass.dao.*;
import br.edu.femass.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioController implements Initializable {

    private final RelatorioCompraDao relatorioCompraDao = new RelatorioCompraDao();
    private final DetalheCompraDao detalheCompraDao = new DetalheCompraDao();
    private final RelatorioVendaDao relatorioVendaDao = new RelatorioVendaDao();
    private final DetalheVendaDao detalheVendaDao = new DetalheVendaDao();
    private final SaldoCompraDao saldoCompraDao = new SaldoCompraDao();
    private final SaldoVendaDao saldoVendaDao = new SaldoVendaDao();

    @FXML
    private ListView<Compra> LstCompra;
    @FXML
    private ListView<Venda> LstVenda;
    @FXML
    private ListView<DetalheCompra> LstDC;
    @FXML
    private ListView<DetalheVenda> LstDV;
    @FXML
    private Label LbCompras;
    @FXML
    private Label LbVendas;
    @FXML
    private Label LbSaldo;
    @FXML
    private void LstCompra_MouseClicked (javafx.scene.input.MouseEvent evento){
        atualizarListaDC();
    }
    @FXML
    private void LstCompra_KeyPressed(javafx.scene.input.KeyEvent evento) {
        atualizarListaDC();
    }
    @FXML
    private void LstVenda_MouseClicked (javafx.scene.input.MouseEvent evento){
        atualizarListaDV();
    }
    @FXML
    private void LstVenda_KeyPressed(javafx.scene.input.KeyEvent evento) {
        atualizarListaDV();
    }
    @FXML

    private void atualizarListaCompra(){
        List<Compra> compras;
        try{
            compras = relatorioCompraDao.listar();
        } catch (Exception e) {
            compras = new ArrayList<>();
        }
        ObservableList<Compra> compras1 = FXCollections.observableArrayList(compras);
        LstCompra.setItems(compras1);
    }
    private void atualizarListaVenda(){
        List<Venda> vendas;
        try{
            vendas = relatorioVendaDao.listar();
        } catch (Exception e) {
            vendas = new ArrayList<>();
        }
        ObservableList<Venda> vendas1 = FXCollections.observableArrayList(vendas);
        LstVenda.setItems(vendas1);
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

    private void atualizarSaldo(){
        List<Saldo> saldoCompras;
        try{
            saldoCompras = saldoCompraDao.listar();
        } catch (Exception e) {
            saldoCompras = new ArrayList<>();
        }
        ObservableList<Saldo> saldoCompras1 = FXCollections.observableArrayList(saldoCompras);
        LbCompras.setText(saldoCompras1.get(saldoCompras1.size()-1).total.toString());

        List<Saldo> saldoVendas;
        try {
            saldoVendas = saldoVendaDao.listar();
        } catch (Exception e){
            saldoVendas = new ArrayList<>();
        }
        ObservableList<Saldo> saldoVendas1 = FXCollections.observableArrayList(saldoVendas);
        LbVendas.setText(saldoVendas1.get(saldoVendas1.size()-1).total.toString());
        Double saldo = (saldoVendas1.get(saldoVendas1.size()-1).total) - (saldoCompras1.get(saldoCompras1.size()-1).total);
        LbSaldo.setText("Saldo do dia: R$" + (saldo));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarListaCompra();
        atualizarListaVenda();
        atualizarSaldo();
    }
}
