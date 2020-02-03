package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartScreen implements Initializable {
    public ToggleGroup category;
    private boolean isOutsourcedPart = false;

    private Inventory entireInventory;

    @FXML
    private RadioButton RadioInhouse;

    @FXML
    private RadioButton RadioOutsourced;

    @FXML
    private Label ToggleLabel;

    @FXML
    private Button btnSaveNewPart;

    @FXML
    private Button btnCancelNewPart;

    @FXML
    private TextField textPartID;

    @FXML
    private TextField textPartPrice;

    @FXML
    private TextField textPartInventory;

    @FXML
    private TextField textPartName;

    @FXML
    private TextField textMinPartInventory;

    @FXML
    private TextField textMaxPartInventory;

    @FXML
    private TextField textPartCompanyName;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        category.selectToggle(RadioInhouse);

    }


    @FXML
    void clickCancelNewPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to cancel?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES){
            backToMainScreen();
        }
    }

    @FXML
    void clickInhouseRadio(ActionEvent event) {
        RadioButton checked = (RadioButton)category.getSelectedToggle();
        String partType = checked.getText();
        if(partType.equals("In-house")){
            System.out.println("inhouse");

            isOutsourcedPart = false;
            ToggleLabel.setText("Machine ID");
            textPartCompanyName.setPromptText("Machine ID");
        } else{
            isOutsourcedPart = true;
            ToggleLabel.setText("Company Name");
            textPartCompanyName.setPromptText("Company Name");

        }

        System.out.println(partType);
    }

    @FXML
    void clickSaveNewPart(ActionEvent event) throws IOException {

        int partId = new Integer(textPartID.getText());
        String partName = textPartName.getText();
        int partInv = new Integer(textPartInventory.getText());
        double partPrice = new Double(textPartPrice.getText());
        int partManInv = new Integer(textMaxPartInventory.getText());
        int partMinInv = new Integer(textMinPartInventory.getText());

        if(partInv > partManInv || partInv<partMinInv){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory is invalid");
            alert.showAndWait();
            return;
        }

        if(isOutsourcedPart){
            String companyName = textPartCompanyName.getText();
            OutsourcedPart oPart = new OutsourcedPart(partId, partName, partPrice, partInv, partMinInv, partManInv, companyName);
            entireInventory.addPart(oPart);
        } else{
            int machineId = new Integer(textPartCompanyName.getText());
            InhousePart IPart = new InhousePart(partId, partName, partPrice, partInv, partMinInv, partManInv, machineId);
            entireInventory.addPart(IPart);
        }

        backToMainScreen();
    }

    private void backToMainScreen() throws IOException {
        Stage stage;
        Parent root;

        stage=(Stage) btnSaveNewPart.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "MainScreen.fxml"
        ));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        MainScreen controller = loader.getController();
    }

    public void getInventory(Inventory inventory){
        entireInventory = inventory;
        textPartID.setText(Integer.toString(entireInventory.generateNewPartId()));

    }

}
