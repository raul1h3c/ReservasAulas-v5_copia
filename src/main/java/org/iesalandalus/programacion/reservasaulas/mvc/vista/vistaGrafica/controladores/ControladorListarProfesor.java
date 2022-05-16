package org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControladorListarProfesor{
	

	
	@FXML private TableView<Profesor> tvProfesores;
    @FXML private TableColumn<Profesor, String> nombreColumn;
    @FXML private TableColumn<Profesor, String> correoColumn;
    @FXML private TableColumn<Profesor, String> telefonoColumn;
    
    private List<Profesor> listaProfesores;
    private ObservableList<Profesor> obsProfesores = FXCollections.observableArrayList();    
	
	private IControlador controladorPrincipal;
	
	public void setControladorPrincipal(IControlador controlador) {
		this.controladorPrincipal = controlador;
	}
	
	
	@FXML
	public void initialize() {
		//nombreColumn.setCellValueFactory(profesor->new SimpleStringProperty(profesor.getValue().getNombre()));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<Profesor,String>("nombre"));
		correoColumn.setCellValueFactory(profesor->new SimpleStringProperty(profesor.getValue().getCorreo()));
		telefonoColumn.setCellValueFactory(profesor->new SimpleStringProperty(profesor.getValue().getTelefono()));
		
	    tvProfesores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	        
	     

	}
	
	public void cargaListadoProfesores(List<Profesor> coleccionProfesores)
    {
		listaProfesores = coleccionProfesores;
    	obsProfesores.setAll(coleccionProfesores);
    	
    }
	
		
	@FXML private Button BTSalir;
	
	@FXML
	private void salir(ActionEvent evento) {
		((Stage) BTSalir.getParent().getScene().getWindow()).close();
	}
}