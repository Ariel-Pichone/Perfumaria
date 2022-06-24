package br.edu.femass.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import br.edu.femass.dao.PerfumeDao;
import br.edu.femass.model.Perfume;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PerfumeController implements Initializable {
    private final PerfumeDao perfumeDao = new PerfumeDao();
    @FXML
    private ListView<Perfume> LstPerfume;
    @FXML
    private Button BtnIncluir;
    @FXML
    private Button BtnAlterar;
    @FXML
    private Button BtnExcluir;
    @FXML
    private Button BtnCancelar;
    @FXML
    private Button BtnGravar;
    @FXML
    private TextField TxtNome;
    @FXML
    private TextField Txtml;
    @FXML
    private TextField TxtPrecoVenda;

    @FXML
    private void LstPerfume_MouseClicked (javafx.scene.input.MouseEvent evento){
        BtnAlterar.setDisable(false);
        exibirPerfume();
    }

    @FXML
    private void LstPerfume_KeyPressed(javafx.scene.input.KeyEvent evento) {
        BtnAlterar.setDisable(false);
        exibirPerfume();
    }
    @FXML
    private void BtnIncluir_Action (ActionEvent evento){
        atualizarLista();
        habilitarInterface(true);
        limparTela();
        TxtNome.requestFocus();
    }
    @FXML
    private void BtnAlterar_Action (ActionEvent evento){
        Perfume perfume = LstPerfume.getSelectionModel().getSelectedItem();
        if (perfume==null) return;
        exibirPerfume();
        atualizarLista();
        habilitarInterface(true);
        TxtNome.requestFocus();
    }
    @FXML
    private void BtnExcluir_Action (ActionEvent evento){
        Perfume perfume = LstPerfume.getSelectionModel().getSelectedItem();
        if (perfume==null) return;
        try {
            perfumeDao.excluir(perfume);
        } catch (Exception e) {
            e.printStackTrace();
        }
        atualizarLista();
    }
    @FXML
    private void BtnCancelar_Action (ActionEvent evento) {
        habilitarInterface(false);
    }
    @FXML
    private void BtnGravar_Action (ActionEvent evento){
        Perfume perfume = new Perfume();
        perfume.setNome(TxtNome.getText());
        perfume.setMl(Integer.parseInt(Txtml.getText()));
        perfume.setPrecoVenda(Double.parseDouble(TxtPrecoVenda.getText()));

        Perfume perfume1 = LstPerfume.getSelectionModel().getSelectedItem();
        if (Objects.equals(TxtNome.getText(), perfume1.getNome())){
            perfume.setId(perfume1.getId());
            try {
                perfumeDao.alterar(perfume);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else{
            try {
                perfumeDao.gravar(perfume);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        atualizarLista();
        habilitarInterface(false);
    }

    private void limparTela(){
        TxtNome.setText("");
        Txtml.setText("");
        TxtPrecoVenda.setText("");
    }
    private void habilitarInterface(Boolean incluir){
        TxtNome.setDisable(!incluir);
        Txtml.setDisable(!incluir);
        TxtPrecoVenda.setDisable(!incluir);
        BtnGravar.setDisable(!incluir);
        BtnCancelar.setDisable(!incluir);
        BtnIncluir.setDisable(incluir);
        BtnAlterar.setDisable(incluir);
        BtnExcluir.setDisable(incluir);
    }

    private void exibirPerfume(){
        Perfume perfume = LstPerfume.getSelectionModel().getSelectedItem();
        if (perfume==null) return;
        TxtNome.setText(perfume.getNome());
        Txtml.setText(perfume.getMl().toString());
        TxtPrecoVenda.setText(perfume.getPrecoVenda().toString());
    }

    private void atualizarLista(){
        List<Perfume> perfumes;
        try{
            perfumes = perfumeDao.listar();
        } catch (Exception e) {
            perfumes = new ArrayList<>();
        }
        ObservableList<Perfume> perfumes1 = FXCollections.observableArrayList(perfumes);
        LstPerfume.setItems(perfumes1);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarLista();
    }

}
