/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class AddPartScreenOutsourcedController implements Initializable {

    Stage stage;
    Parent scene;
    Inventory inv;
    
    @FXML
    private RadioButton partInHouseRBtn;

    @FXML
    private ToggleGroup InHouseOutsourcedRBtns;

    @FXML
    private RadioButton partOutsourcedRBtn;

    @FXML
    private TextField partIdAutoGenTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partCompNmTxt;

    public AddPartScreenOutsourcedController(Inventory inv)
    {
        this.inv = inv;
    }
    
    @FXML
    void OnActionDisplayAddPartScreenInHouseRBtn(ActionEvent event) throws IOException {
            stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddPartScreenInHouse.fxml"));
            Controller.AddPartScreenInHouseController controller = new Controller.AddPartScreenInHouseController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
    }

    @FXML
    void OnActionDisplayAddPartScreenOutsourcedRBtn(ActionEvent event) throws IOException {
            stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddPartScreenOutsourced.fxml"));
            Controller.AddPartScreenOutsourcedController controller = new Controller.AddPartScreenOutsourcedController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
    }

    @FXML
    void OnActionReturnToMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK)
        {      
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
            Controller.MainScreenController controller = new Controller.MainScreenController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    void onActionSaveOutsourcedPart(ActionEvent event) {

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
