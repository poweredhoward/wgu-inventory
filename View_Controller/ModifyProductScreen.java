package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyProductScreen {

    private Product displayedProduct;
    private Inventory entireInventory;
    private int productIndex;
    private ObservableList<Part> addedParts = FXCollections.observableArrayList();


    @FXML
    private TextField textModifyProductID;

    @FXML
    private TextField textModifyProductName;

    @FXML
    private TextField textModifyProductInv;

    @FXML
    private TextField textModifyProductPrice;

    @FXML
    private TextField textModifyProductMax;

    @FXML
    private TextField textModifyProductMin;

    @FXML
    private Button btnSearchPartsAddProducts;

    @FXML
    private TableView<Part> existingPartsTable;

    @FXML
    private TextField textSearchPartsAddProducts;

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
        int productInv = new Integer(textModifyProductInv.getText());
        int productMinInv = new Integer(textModifyProductMin.getText());
        int productMaxInv = new Integer(textModifyProductMax.getText());

        displayedProduct.setName(textModifyProductName.getText());
        displayedProduct.setStock(new Integer(textModifyProductInv.getText()));
        displayedProduct.setPrice(new Double(textModifyProductPrice.getText()));
        displayedProduct.setMin(new Integer(textModifyProductMin.getText()));
        displayedProduct.setMax(new Integer(textModifyProductMax.getText()));


        if(productInv > productMaxInv || productInv<productMinInv){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory is invalid");
            alert.showAndWait();
            return;
        }

//        entireInventory.updateProduct(productIndex, displayedProduct);

        backToMainScreen();
    }

    @FXML
    void clickSearchParts(ActionEvent event) {

    }

    public void intakeNewProduct(Inventory inventory, int productId, int productIndex){
        this.entireInventory = inventory;
        this.displayedProduct = inventory.lookupProduct(productId);
        this.productIndex = productIndex;
        setProductFields();
        setTables();
    }

    private void setProductFields(){
        textModifyProductID.setText(Integer.toString(displayedProduct.getId()));
        textModifyProductName.setText(displayedProduct.getName());
        textModifyProductPrice.setText(Double.toString(displayedProduct.getPrice()));
        textModifyProductInv.setText(Integer.toString(displayedProduct.getStock()));
        textModifyProductMin.setText(Integer.toString(displayedProduct.getMin()));
        textModifyProductMax.setText(Integer.toString(displayedProduct.getMax()));


    }

    private void setTables(){
        System.out.println(entireInventory.getAllParts().size());

        colPartIDAddProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartNameAddProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvAddProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPriceAddProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        existingPartsTable.setItems(entireInventory.getAllParts());


        addedParts = displayedProduct.getAllAssociatedParts();

        colAddedPartIDAddProd.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddedPartNameAddProd.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddedPartInvAddProd.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colAddedPartPriceAddProd.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddedPartsTable.setItems(addedParts);
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
