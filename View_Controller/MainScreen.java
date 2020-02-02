package View_Controller;

import Model.*;
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

public class MainScreen implements Initializable {

    private static Inventory inventory = null;


    @FXML
    private Button SearchButtonParts;

    @FXML
    private TextField SearchTextParts;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<?, ?> colPartID;

    @FXML
    private TableColumn<?, ?> colPartName;

    @FXML
    private TableColumn<?, ?> colPartInv;

    @FXML
    private TableColumn<?, ?> colPartPrice;

    @FXML
    private Button btnAddPart;

    @FXML
    private Button btnModifyPart;

    @FXML
    private Button btnDeletePart;

    @FXML
    private Button SearchButtonProducts;

    @FXML
    private TextField SearchTextProducts;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<?, ?> colProductID;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colProductInv;

    @FXML
    private TableColumn<?, ?> colProductPrice;

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnModifyProduct;

    @FXML
    private Button btnDeleteProduct;

    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventory = new Inventory();
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTable.setItems(inventory.getAllParts());

        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(inventory.getAllProducts());

    }

    @FXML
    void addHandlerPart(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btnAddPart.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "AddPartScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AddPartScreen controller = loader.getController();
        controller.getInventory(inventory);
    }

    @FXML
    void addHandlerProduct(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) btnAddProduct.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "AddProductScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AddProductScreen controller = loader.getController();
        controller.getInventory(inventory);

//        Product product=partTable1.getSelectionModel().getSelectedItem();

//        Person person=table.getSelectionModel().getSelectedItem();
//        controller.setPerson(person);
    }

    @FXML
    void deleteHandlerPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to cancel?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES){
            inventory.deletePart((Part) partTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void deleteHandlerProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES){
            inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void exitHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit the application?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES){
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void modifyHandlerPart(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btnModifyPart.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPartScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ModifyPartScreen controller = loader.getController();
//        controller.getInventory(inventory);

        Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();
        int partIndex = partTable.getSelectionModel().getSelectedIndex();
        System.out.println(selectedPart.getId());

        controller.intakeNewPart(inventory, selectedPart.getId(), partIndex);


    }

    @FXML
    void modifyHandlerProduct(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btnModifyProduct.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProductScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ModifyProductScreen controller = loader.getController();
//        controller.getInventory(inventory);

        Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        int productIndex = productTable.getSelectionModel().getSelectedIndex();
        System.out.println(selectedProduct.getId());

        controller.intakeNewProduct(inventory, selectedProduct.getId(), productIndex);
    }

    @FXML
    void searchHandlerParts(ActionEvent event) {
        String partName = SearchTextParts.getText();
        partTable.setItems(inventory.lookupPart(partName));

    }

    @FXML
    void searchHandlerProducts(ActionEvent event) {
        String productName = SearchTextProducts.getText();
        productTable.setItems(inventory.lookupProduct(productName));

    }

}
