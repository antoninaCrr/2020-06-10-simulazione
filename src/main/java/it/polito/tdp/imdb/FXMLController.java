/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {
    	this.txtResult.clear();
    	Actor a = this.boxAttore.getValue();
    	
    	if(a == null) {
    		this.txtResult.appendText("Selezionare un attore\n");
    		return;
    	}
    	
    	List<Actor> simili = new ArrayList<>(model.getSimili(a));
    	this.txtResult.appendText("ATTORI SIMILI A: "+a.toString()+"\n");
    	for(Actor ai : simili) {
    		if(!ai.equals(a))
    		this.txtResult.appendText(ai.toString()+"\n");
    	}
    	

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String genere = this.boxGenere.getValue();
    	
    	if(genere == null) {
    		this.txtResult.setText("Selezionare un genere di interesse\n");
    		return;
    	}
    	
    	model.creaGrafo(genere);
    	this.txtResult.appendText("GRAFO CREATO\n");
    	this.txtResult.appendText("#VERTICI: "+this.model.nVertici()+"\n");
    	this.txtResult.appendText("#ARCHI: "+this.model.nArchi()+"\n");
    	
    	this.boxAttore.setDisable(false);
    	this.boxAttore.getItems().clear();
    	this.boxAttore.getItems().addAll(model.getAllActors());
    	this.btnSimili.setDisable(false);
    	this.btnSimulazione.setDisable(false);
    	this.txtGiorni.setDisable(false);

    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	int giorni;
    	
    	try {
    		giorni = Integer.parseInt(this.txtGiorni.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Selezionare un input numerico\n");
    		return;
    	}
    	model.simula(giorni);
    	this.txtResult.appendText("Numero pause = "+model.getPause()+"\n");
    	List<Actor> attoriIntervistati = model.getAttoriIntervistati();
    	
    	this.txtResult.appendText("Attori intervistati: "+"\n");
    	for(Actor ai : attoriIntervistati) {
    		this.txtResult.appendText(ai.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.boxGenere.getItems().addAll(model.getAllGenres());
    	
    	this.boxAttore.setDisable(true);
    	this.btnSimili.setDisable(true);
    	this.txtGiorni.setDisable(true);
    	this.btnSimulazione.setDisable(true);
    }
}
