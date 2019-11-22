/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class ModifyProductScreenController implements Initializable {

    Stage stage;
    Parent scene;
    Inventory inv;
    Product thisProduct;
    
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> searchedParts = FXCollections.observableArrayList();
    private ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
    
    @FXML
    private TextField ProductIdTxt;

    @FXML
    private TextField ProductNameTxt;

    @FXML
    private TextField ProductInvTxt;

    @FXML
    private TextField ProductPriceTxt;

    @FXML
    private TextField ProductMaxTxt;

    @FXML
    private TextField ProductMinTxt;

    @FXML
    private TableView<Part> AvailablePartTableView;
        
    @FXML
    private TableColumn<Part, Integer> PartIdCol;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, Integer> PartInvLvlCol;

    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    @FXML
    private TableView<Part> AssociatedPartTableView;
        
    @FXML
    private TableColumn<Part, Integer> ModifyPartIdCol;

    @FXML
    private TableColumn<Part, String> ModifyPartNameCol;

    @FXML
    private TableColumn<Part, Integer> ModifyPartInvLvlCol;

    @FXML
    private TableColumn<Part, Double> ModifyPartPriceCol;

    @FXML
    private TextField ProductSearchTxt;
    
    public ModifyProductScreenController(Inventory inv)
    {
        this.inv = inv;
    }

    @FXML
    void OnActionReturnToMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK)
        {   
            //Return to main screen
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
    void OnActionSaveProduct(ActionEvent event) throws IOException {
        try
        {
            //retrieve values from text fields
            String name = ProductNameTxt.getText();
            int inventory = Integer.parseInt(ProductInvTxt.getText());
            double price = Double.parseDouble(ProductPriceTxt.getText());
            int max = Integer.parseInt(ProductMaxTxt.getText());
            int min = Integer.parseInt(ProductMinTxt.getText());

            //Value tests (limited for assessment)
            if(min < inventory && inventory < max)
            {
                //update product
                Product thisProduct = inv.lookupProduct(Integer.parseInt(ProductIdTxt.getText()));
                thisProduct.setName(name);
                thisProduct.setStock(inventory);
                thisProduct.setPrice(price); 
                thisProduct.setMax(max);
                thisProduct.setMin(min);
                
                //Restore part inv
                for (Part p : associatedParts)
                {
                    allParts.add(p);
                }

                //Return to main screen
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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter inventory value between minimum and maximum values.");
                alert.showAndWait();
            }


        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter valid values in text fields.");
            alert.showAndWait();
        }
    }

    @FXML
    void OnActionSearchAssociatedParts(ActionEvent event) {
        searchedParts.clear();
        generatePartsTable();  
    
    String searchItem = ProductSearchTxt.getText();
    boolean found = false;
    try{
        int searchId = Integer.parseInt(searchItem);
        for(Part p: allParts){
            if(p.getId() == searchId){
                found = true;
                //add found part to searched list
                searchedParts.add(p);
            }
        }
            if (found == true){
                //clear table
                AvailablePartTableView.getItems().clear();
                //add searched list to table
                AvailablePartTableView.setItems(searchedParts);
                AvailablePartTableView.refresh();
            }
        
            if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Available part not found");

                alert.showAndWait();
            }
    }
    catch(NumberFormatException e){
        for(Part p: allParts){
            if(p.getName().contains(searchItem)){
                found = true;
                //add found part to searched list
                searchedParts.add(p);
                }
        }
        if(found == true){
                //clear table
                AvailablePartTableView.getItems().clear();
                //add searched list to table
                AvailablePartTableView.setItems(searchedParts);
                AvailablePartTableView.refresh();
                }
         if (found==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Available part not found");

            alert.showAndWait();
        }
    }
    finally{
        if (searchItem.isEmpty()){
            searchedParts.clear();
            generatePartsTable(); 
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Please enter a part ID or Name");

            alert.showAndWait();
            }
        }
    }
      
    @FXML
    void OnActionAddAssociatedPart(ActionEvent event){
        //Add part to product
        Part selectedPart = AvailablePartTableView.getSelectionModel().getSelectedItem();
        thisProduct.addAssociatedPart(selectedPart);
        //Remove part from available part table/inventory
        inv.deletePart(selectedPart);
        //Refresh tables
        generatePartsTable();
        generateAssociatedPartsTable();
    }
    
    @FXML
    void OnActionDeleteAssociatedPart(ActionEvent event) {
        //Remove part from product
        Part selectedPart = AssociatedPartTableView.getSelectionModel().getSelectedItem();
        thisProduct.deleteAssociatedPart(selectedPart);
        //Add part to inventory
        inv.addPart(selectedPart);
        //refresh Available parts table
        generatePartsTable();
        //Add part to available part table
        generateAssociatedPartsTable();
    }
    
    public void setProduct(Product product)
    {
        thisProduct = product;
        //set fields //changed product to thisProduct
        ProductIdTxt.setText(Integer.toString((thisProduct.getId())));
        ProductNameTxt.setText(thisProduct.getName()); 
        ProductInvTxt.setText(Integer.toString((thisProduct.getStock())));
        ProductPriceTxt.setText(Double.toString((thisProduct.getPrice())));
        ProductMaxTxt.setText(Integer.toString((thisProduct.getMax())));
        ProductMinTxt.setText(Integer.toString((thisProduct.getMin())));
        associatedParts.setAll(thisProduct.getAllAssociatedParts());
        //Initialize associated parts table
        AssociatedPartTableView.setItems(associatedParts);
        //Initialize available parts table
        allParts = inv.getAllParts();
        for (Part p : associatedParts)
                {
                    allParts.remove(p);
                }
        AvailablePartTableView.setItems(allParts);
        
    }
        
    private void generatePartsTable()
    {
        AvailablePartTableView.setItems(allParts);
        AvailablePartTableView.refresh();
    }
      
    private void generateAssociatedPartsTable()
    {
       associatedParts.setAll(thisProduct.getAllAssociatedParts());
       AssociatedPartTableView.setItems(associatedParts);
       AssociatedPartTableView.refresh();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
    }    
    
}
