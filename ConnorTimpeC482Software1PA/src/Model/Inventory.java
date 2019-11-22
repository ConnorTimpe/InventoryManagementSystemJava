package Model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 *
 * @author Connor Timpe
 */
public class Inventory extends Application{

    @Override
    public void start(Stage stage) {}
       
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Part> searchedParts = FXCollections.observableArrayList();
    private ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
    
    
    public void addPart(Part newPart)
    {
        allParts.add(newPart);
    }
    
    public void addAllParts(ObservableList<Part> addedParts)
    {
        for(Part p : addedParts)
        {
            allParts.add(p);
        }
    }
    
    public void addProduct (Product newProduct)
    {
        allProducts.add(newProduct);
    }
    
    public Part lookupPart(int partId)
    {
        for(Part part: getAllParts())
            if(part.getId() == partId)
            {
                return part;
            }
        return null;
    }
    
        public Part lookupSearchedPart(int partId)
    {
        for(Part part: getAllSearchedParts())
            if(part.getId() == partId)
            {
                return part;
            }
        return null;
    }
    
    public Product lookupProduct(int productId)
    {
        for(Product product: getAllProducts())
            if(product.getId() == productId)
            {
                return product;
            }
        return null;
    }
    
    public ObservableList<Part> lookupPart(String partName)
    {
        for(Part part: getAllParts())
        {
            if(part.getName().contains(partName))
            {
                getAllSearchedParts().add(part);
            }
        }
        return getAllSearchedParts();
    }
    
    public ObservableList<Product> lookupProduct(String productName)
    {
        for(Product product: getAllProducts())
        {
            if(product.getName().contains(productName))
            {
                getAllSearchedProducts().add(product);
            }
        }
        return getAllSearchedProducts();
    }
    
    public void updatePart(int index, Part selectedPart)
    {
        getAllParts().set(index, selectedPart);
    }
    
    public void updateProduct(int index, Product selectedProduct)
    {
        getAllProducts().set(index, selectedProduct);
    }
    
    public void deletePart(Part selectedPart)
    {
        getAllParts().remove(selectedPart);
    }
    
    public void deleteProduct(Product selectedProduct)
    {
        getAllProducts().remove(selectedProduct);
    }
    
    public ObservableList<Part> getAllParts()
    {
        return allParts;
    }
    
    public ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
    
    public ObservableList<Part> getAllSearchedParts()
    {
        return searchedParts;
    }
    
    public ObservableList<Product> getAllSearchedProducts()
    {
        return searchedProducts;
    }
}





//How to auto gen id
    //idCounter++ and remove id from constructor?
