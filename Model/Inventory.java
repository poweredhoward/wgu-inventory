package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public Inventory(){

    }


    public void addPart(Part newPart){
        allParts.add(newPart);
    }

    public void addProduct(Product newProduct){
        allProducts.add(newProduct);

    }

    public Part lookupPart(int partId){
        for(int i=0 ; i<allParts.size() ; i++){
            if(allParts.get(i).getId() == partId){
                return allParts.get(i);
            }
        }

        return null;
    }

    public Product lookupProduct(int productId){
        for(int i=0 ; i<allProducts.size() ; i++){
            if(allProducts.get(i).getId() == productId){
                return allProducts.get(i);
            }
        }

        return null;
    }

    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> results = FXCollections.observableArrayList();
        for (Part part : allParts){
            if(part.getName().contains(partName)){
                results.add(part);
            }
        }
        return results;
    }

    public ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> results = FXCollections.observableArrayList();
        for (Product product : allProducts){
            if(product.getName().contains(productName)){
                results.add(product);
            }
        }
        return results;

    }

    public void updatePart(int index, Part SelectedPart){
        allParts.set(index, SelectedPart);
    }

    public void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    public boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts(){
        return allParts;

    }

    public ObservableList<Product> getAllProducts(){
        return allProducts;

    }

    public int generateNewPartId(){
        return allParts.size() + 1;
    }

    public int generateNewProductId(){
        return allProducts.size() + 1;
    }

}
