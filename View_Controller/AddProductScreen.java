package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductScreen {
    private Inventory entireInventory;
    private ObservableList<Part> addedParts = FXCollections.observableArrayList();

    @FXML
    private TextField textAddProductID;

    @FXML
    private TextField textAddProductName;

    @FXML
    private TextField textAddProductInv;

    @FXML
    private TextField textAddProductPrice;

    @FXML
    private TextField textAddProductMax;

    @FXML
    private TextField textAddProductMin;

    @FXML
    private Button btnSearchPartsAddProducts;

    @FXML
    private TextField textSearchPartsAddProducts;

    @FXML
    private TableView<Part> existingPartsTable;

    @FXML
    private TableColumn<?, ?> colPartIDAddProduct;

    @FXML
    private TableColumn<?, ?> colPartNameAddProduct;

    @FXML
    private TableColumn<?, ?> colPartInvAddProduct;

    @FXML
    private TableColumn<?, ?> colPartPriceAddProduct;

    @FXML
    private Button btnAddAddPartNewProd;

    @FXML
    private TableView<Part>AddedPartsTable;

    @FXML
    private TableColumn<?, ?> colAddedPartIDAddProd;

    @FXML
    private TableColumn<?, ?> colAddedPartNameAddProd;

    @FXML
    private TableColumn<?, ?> colAddedPartInvAddProd;

    @FXML
    private TableColumn<?, ?> colAddedPartPriceAddProd;

    @FXML
    private Button btnDeletePartFromProd;

    @FXML
    private Button btnSaveNewProduct;

    @FXML
    private Button btnCancelAddProduct;

    private void setTables(){
        colPartIDAddProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartNameAddProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvAddProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPriceAddProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        existingPartsTable.setItems(entireInventory.getAllParts());


        colAddedPartIDAddProd.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddedPartNameAddProd.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddedPartInvAddProd.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colAddedPartPriceAddProd.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddedPartsTable.setItems(addedParts);
    }

    @FXML
    void clickAddPartToProduct(ActionEvent event) {
        Part selectedPart = (Part) existingPartsTable.getSelectionModel().getSelectedItem();
        addedParts.add(selectedPart);
    }

    @FXML
    void clickCancelAddProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to cancel?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES){
            backToMainScreen();
        }
    }

    @FXML
    void clickDeletePartFromProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES){
            Part selectedPart = (Part) AddedPartsTable.getSelectionModel().getSelectedItem();
            addedParts.remove(selectedPart);
        }

    }

    @FXML
    void clickSaveNewProduct(ActionEvent event) throws IOException {
        int productId = new Integer(textAddProductID.getText());
        String productName = textAddProductName.getText();
        int productInv = new Integer(textAddProductInv.getText());
        double productPrice = new Double(textAddProductPrice.getText());
        int productMinInv = new Integer(textAddProductMin.getText());
        int productMaxInv = new Integer(textAddProductMax.getText());

        if(productInv > productMaxInv || productInv<productMinInv){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory is invalid");
            alert.showAndWait();
            return;
        }

        Product newProduct = new Product(productId, productName, productPrice, productInv, productMinInv, productMaxInv, addedParts);
        entireInventory.addProduct(newProduct);

        backToMainScreen();
    }

    @FXML
    void clickSearchParts(ActionEvent event) {

    }

    public void getInventory(Inventory inventory){
        entireInventory = inventory;
        textAddProductID.setText(Integer.toString(entireInventory.generateNewProductId()));

        setTables();
    }

    private void backToMainScreen() throws IOException {
        Stage stage;
        Parent root;

        stage=(Stage) btnSaveNewProduct.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "MainScreen.fxml"
        ));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        MainScreen controller = loader.getController();
    }
}
