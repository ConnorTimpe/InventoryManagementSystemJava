 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Connor Timpe
 */
public class MainScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
 
    Inventory inv;
    
    int idCounter;
    public static int selectedId;

    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Part> searchedParts = FXCollections.observableArrayList();
    private ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
    
    @FXML
    private Button mainPartSearchBtn;

    @FXML
    private TextField mainPartSearchTxt;
    
    @FXML
    public TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvLvlCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private Button mainProductSearchBtn;

    @FXML
    private TextField mainProductSearchTxt;

    @FXML
    private TableView<Product> productTableView;
    
    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvLvlCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    public MainScreenController(Inventory inv)
    {
        this.inv = inv;
    }
    
    @FXML
    void OnActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
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
    void OnActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddProductScreen.fxml"));
        Controller.AddProductScreenController controller = new Controller.AddProductScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    void OnActionModifyPart(ActionEvent event) throws IOException {
        
        
        if(partTableView.getSelectionModel().getSelectedItem() instanceof InHouse)
        {
            //Get selected item's id
            selectedId = partTableView.getSelectionModel().getSelectedItem().getId(); 
        
            //Go to modify part InHouse screen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyPartScreenInHouse.fxml"));
            Controller.ModifyPartScreenInHouseController controller = new Controller.ModifyPartScreenInHouseController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            
            InHouse inhouse = (InHouse) partTableView.getSelectionModel().getSelectedItem();
            controller.setPart(inhouse);
        }
        if(partTableView.getSelectionModel().getSelectedItem() instanceof Outsourced)
        {
            //Get selected item's id
            selectedId = partTableView.getSelectionModel().getSelectedItem().getId(); 
        
            //Go to modify part InHouse screen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyPartScreenOutsourced.fxml"));
            Controller.ModifyPartScreenOutsourcedController controller = new Controller.ModifyPartScreenOutsourcedController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            
            Outsourced outsourced = (Outsourced) partTableView.getSelectionModel().getSelectedItem();
            controller.setPart(outsourced);
        }
        
      
    }
    
    @FXML
    void OnActionModifyProduct(ActionEvent event) throws IOException {
        //Get selected item
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        //Go to modify product screen
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyProductScreen.fxml"));
        Controller.ModifyProductScreenController controller = new Controller.ModifyProductScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        controller.setProduct(selectedProduct);
    }

    @FXML
    void OnActionDeletePart(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        inv.deletePart(selectedPart);
        partTableView.getItems().remove(selectedPart);
    }

    @FXML
    void OnActionDeleteProduct(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        inv.deleteProduct(selectedProduct);
        productTableView.getItems().remove(selectedProduct);
    }
    
    

    @FXML
    void OnActionSearchParts(ActionEvent event) {
    searchedParts.clear();
    generatePartsTable();  
    
    String searchItem = mainPartSearchTxt.getText();
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
                partTableView.getItems().clear();
                //add searched list to table
                partTableView.setItems(searchedParts);
                partTableView.refresh();
            }
        
            if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Part not found");

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
                partTableView.getItems().clear();
                //add searched list to table
                partTableView.setItems(searchedParts);
                partTableView.refresh();
                }
         if (found==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Part not found");

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
    void OnActionSearchProducts(ActionEvent event) {

    searchedProducts.clear();
    generateProductsTable();  
    
    String searchItem = mainProductSearchTxt.getText();
    boolean found = false;
    try{
        int searchId = Integer.parseInt(searchItem);
        for(Product p: allProducts){
            if(p.getId() == searchId){
                found = true;
                //add found part to searched list
                searchedProducts.add(p);
            }
        }
            if (found == true){
                //clear table
                productTableView.getItems().clear();
                //add searched list to table
                productTableView.setItems(searchedProducts);
                productTableView.refresh();
            }
        
            if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Product not found");

                alert.showAndWait();
            }
    }
    catch(NumberFormatException e){
        for(Product p: allProducts){
            if(p.getName().contains(searchItem)){
                found = true;
                //add found part to searched list
                searchedProducts.add(p);
                }
        }
        if(found == true){
                //clear table
                productTableView.getItems().clear();
                //add searched list to table
                productTableView.setItems(searchedProducts);
                productTableView.refresh();
                }
         if (found==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Product not found");

            alert.showAndWait();
        }
    }
    finally{
        if (searchItem.isEmpty()){
            searchedProducts.clear();
            generateProductsTable(); 
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Please enter a product ID or Name");

            alert.showAndWait();
        }
    }
        
    }

    @FXML
    void OnActionExit(ActionEvent event) {
        System.exit(0);
    }


   
  private void generatePartsTable()
    {
        allParts.setAll(inv.getAllParts());
        partTableView.setItems(allParts);
        partTableView.refresh();
    }
  
  private void generateProductsTable()
    {
        allProducts.setAll(inv.getAllProducts());
        productTableView.setItems(allProducts);
        productTableView.refresh();
    }
  
  public void generateId()
  {
      //use idCounter; Use this for random product id
  }
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        generatePartsTable();
        generateProductsTable();

    }    
    
}
