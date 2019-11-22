/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Connor Timpe
 */
public class AddPartScreenInHouseController implements Initializable {

    Stage stage;
    Parent scene;
    Inventory inv;
    
    @FXML
    private RadioButton partInHouseRBtn;

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
    private TextField partMachineIdTxt; 
    
    public AddPartScreenInHouseController(Inventory inv)
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
    void onActionSaveInhousePart(ActionEvent event) throws IOException {
        try
        {
            //retrieve values from text fields
            int id = 0; //Uses idCounter in constructor
            String name = partNameTxt.getText();
            double price= Double.parseDouble(partPriceTxt.getText());
            int stock = Integer.parseInt(partInvTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int machineId = Integer.parseInt(partMachineIdTxt.getText());
            
            //Value tests (limited for assessment)
            if(min < stock && stock < max)
            {
                //create part
                inv.addPart(new InHouse(id, name, price, stock, min, max, machineId)); 
            }
            else{  
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter stock value between minimum and maximum values.");
                alert.showAndWait();
            }
            
            //Return to main menu after pressing save
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
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter valid values in text fields.");
            alert.showAndWait();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
}
