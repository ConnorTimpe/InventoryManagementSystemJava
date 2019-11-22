/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.InHouse;
import Model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Connor Timpe
 */
public class ModifyPartScreenInHouseController implements Initializable {

    Stage stage;
    Parent scene;
    Inventory inv;
    InHouse inhouse;
    
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Part> searchedParts = FXCollections.observableArrayList();
    
    @FXML
    private RadioButton partInHouseRBtn;

    @FXML
    private ToggleGroup modifyPartRBtn;

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
    
    @FXML
    private Label partMachineIdLabel;
    
    public ModifyPartScreenInHouseController(Inventory inv)
    {
        this.inv = inv;
    }
    
    @FXML
    void OnActionDisplayModifyPartScreenInHouseRBtn(ActionEvent event) throws IOException {
        partMachineIdLabel.setText("Machine ID");
        partMachineIdTxt.clear();
        partMachineIdTxt.setPromptText("Mach ID");
    }
    

    @FXML
    void OnActionDisplayModifyPartScreenOutsourcedRBtn(ActionEvent event) throws IOException {
        partMachineIdLabel.setText("Company Name");
        partMachineIdTxt.clear();
        partMachineIdTxt.setPromptText("Comp name");
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
       //retrieve values from text fields
            int id = Integer.parseInt(partIdAutoGenTxt.getText());
            String name = partNameTxt.getText();
            double price= Double.parseDouble(partPriceTxt.getText());
            int stock = Integer.parseInt(partInvTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            
            //Value tests (limited for assessment)
            if(min < stock && stock < max)
            {
                if(partMachineIdLabel.getText().contains("Machine ID"))
                {
                    int machineId = Integer.parseInt(partMachineIdTxt.getText());
                    
                    //Delete original part
                    inv.deletePart(inv.lookupPart(id));
                    
                    //create updated part
                    InHouse newInhouse = new InHouse(id, name, price, stock, min, max, machineId);
                    newInhouse.setId(id);
                    inv.addPart(newInhouse); 
                    
                    //Return to main menu
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
            
                else
                {
                    String companyName = partMachineIdTxt.getText();
                    
                    //Delete original part
                    inv.deletePart(inv.lookupPart(id));
                   
                    //Create updated part
                    Outsourced newOutsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                    newOutsourced.setId(id);
                    inv.addPart(newOutsourced);
                    
                    //Return to main menu
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
            else
            {  
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter stock value between minimum and maximum values.");
                alert.showAndWait();
            }
    }
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    public void setPart(InHouse inhouse)
    {
        this.inhouse = inhouse;
        //set fields
        partIdAutoGenTxt.setText(Integer.toString((inhouse.getId())));
        partNameTxt.setText(inhouse.getName()); 
        partInvTxt.setText(Integer.toString((inhouse.getStock())));
        partPriceTxt.setText(Double.toString((inhouse.getPrice())));
        partMaxTxt.setText(Integer.toString((inhouse.getMax())));
        partMinTxt.setText(Integer.toString((inhouse.getMin())));
        partMachineIdTxt.setText(Integer.toString(inhouse.getMachineId()));
    }
}
