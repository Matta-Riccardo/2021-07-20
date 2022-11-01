/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Giornalista;
import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.User;
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

    @FXML // fx:id="btnUtenteSimile"
    private Button btnUtenteSimile; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="cmbUtente"
    private ComboBox<User> cmbUtente; // Value injected by FXMLLoader

    @FXML // fx:id="txtX1"
    private TextField txtX1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	txtResult.clear();
    	cmbUtente.getItems().clear();
    	try {
    		
    		int nRecensioni = Integer.parseInt(txtN.getText());
    		
    		if(nRecensioni<=0) {
    			txtResult.appendText("Inserisci un valore che sia maggiore di zero\n");
    			return;
    		}
    		
    		if(cmbAnno.getValue() == null) {
    			txtResult.appendText("Seleziona un anno dalla tendina!\n");
    			return;
    		}
    		
    		int anno = cmbAnno.getValue();
    		txtResult.appendText(String.format("Stai cercando di creare un grafo dove ogni vertice è rappresentato da\nun utente che risulta aver scritto almeno %d recensioni nell'anno %d.\n", nRecensioni, anno));
    		model.creaGrafo(nRecensioni, anno);
    		
    		cmbUtente.getItems().addAll(model.getVertici());
    		txtResult.appendText(String.format("Grafo creato con %d vertici e %d archi\n", model.nVertici(), model.nArchi()));
    		
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.setText("Inserisci un valore numerico intero");
    		return;
    	}
    }

    @FXML
    void doUtenteSimile(ActionEvent event) {
    	User utente = cmbUtente.getValue();
    	txtResult.clear();
    	
    	if(utente == null) {
    		txtResult.appendText("Selezionare un utente prima di procedere con la ricerca!\n");
    		return;
    	}
    	
    	for(User user : model.getUtenteSimile(utente)) {
    		txtResult.appendText(String.format("UTENTE: '%s'       GRADO: %.0f\n", user.toString(), model.getPeso()));
    	}
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	try {
    		
    		int X1 = Integer.parseInt(txtX1.getText());
    		int X2 = Integer.parseInt(txtX2.getText());
    		
    		model.Simula(X1, X2);
    		
    		for(Giornalista g : model.getGiornalisti()) {
    			txtResult.appendText(g.toString()+"\n");
    		}
    		
    		txtResult.appendText(String.format("Hai impiegato %d giorni per concludere l'indagine!\n", model.getNGiorni()));
    		
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.appendText("Inserisci un valore numerico intero per entrambi i campi!");
    		return;
    	}
    	
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUtenteSimile != null : "fx:id=\"btnUtenteSimile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbUtente != null : "fx:id=\"cmbUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX1 != null : "fx:id=\"txtX1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	cmbAnno.getItems().clear();
    	for(Integer i = 2005; i<2014; i++ ) {
    		cmbAnno.getItems().add(i);
    	}
    }
}
