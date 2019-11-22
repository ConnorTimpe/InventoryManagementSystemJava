package connortimpec482software1pa;

import Model.Inventory;
import Model.Part;
import Model.InHouse;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author conno
 */

public class ConnorTimpeC482Software1PA extends Application {
    
    public static void main(String[] args) { launch(args); }
        
    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        addTestData(inv); 
        
        //Display main screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
        Controller.MainScreenController controller = new Controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

     void addTestData(Inventory inv){
        //add inhouse parts
        Part inhouse1 = new InHouse(1, "Part 1", 5.00, 5, 0, 10, 1);
        Part inhouse2 = new InHouse(2, "Part 2", 4.00, 4, 0, 11, 2);
        inv.addPart(inhouse1);
        inv.addPart(inhouse2);
        //add outsourced parts
        Part outsourced1 = new Outsourced(3, "Part 3", 8.00, 10, 0, 20, "Company 1");
        Part outsourced2 = new Outsourced(4, "Part 4", 10.00, 15, 0, 20, "Company 2");
        inv.addPart(outsourced1);
        inv.addPart(outsourced2);
        //add products
        Product product1 = new Product(1, "Product 1", 5.00, 5, 0, 10);
        Product product2 = new Product(2, "Product 2", 6.00, 6, 0, 15);
        inv.addProduct(product1);
        inv.addProduct(product2);
        //add created parts to products (associated parts)
        
        product1.addAssociatedPart(inhouse1);
        product2.addAssociatedPart(inhouse2);
        product1.addAssociatedPart(outsourced1);
        product2.addAssociatedPart(outsourced2);


        
    }


}
