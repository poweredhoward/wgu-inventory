package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartScreen {
    public ToggleGroup category;
    private boolean isOutsourcedPart = false;

    private Inventory entireInventory;
    private Part displayedPart;
    private int partIndex;


    @FXML
    private RadioButton RadioInhouse;

    @FXML
    private RadioButton RadioOutsourced;

    @FXML
    private Label ToggleLabelModifyPart;

    @FXML
    private Button btnSaveModifyPart;

    @FXML
    private Button btnCancelNewPart;

    @FXML
    private TextField partID;

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
    void clickInhouseRadio() {
        RadioButton checked = (RadioButton)category.getSelectedToggle();
        String partType = checked.getText();
        if(partType.equals("In-house")){
            System.out.println("inhouse");

            isOutsourcedPart = false;
            ToggleLabelModifyPart.setText("Machine ID");
            textPartCompanyName.setPromptText("Machine ID");
        } else{
            isOutsourcedPart = true;
            ToggleLabelModifyPart.setText("Company Name");
            textPartCompanyName.setPromptText("Company Name");

        }

    }

    @FXML
    void clickSaveModifyPart(ActionEvent event) throws IOException {
        int partId = new Integer(partID.getText());
        String partName = textPartName.getText();
        int partInv = new Integer(textPartInventory.getText());
        double partPrice = new Double(textPartPrice.getText());
        int partManInv = new Integer(textMaxPartInventory.getText());
        int partMinInv = new Integer(textMinPartInventory.getText());

//        displayedPart.setName( textPartName.getText());
//        displayedPart.setStock(new Integer(textPartInventory.getText()));
//        displayedPart.setPrice( new Double(textPartPrice.getText()));
//        displayedPart.setMax(new Integer(textMaxPartInventory.getText()));
//        displayedPart.setMin( new Integer(textMinPartInventory.getText()));

        if(partInv > partManInv || partInv<partMinInv){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory is invalid");
            alert.showAndWait();
            return;
        }


        if(isOutsourcedPart){
            String companyName = textPartCompanyName.getText();
            OutsourcedPart updatedPart = new OutsourcedPart(this.displayedPart.getId(), partName, partPrice, partInv, partMinInv, partManInv, companyName);
//            ((OutsourcedPart) displayedPart).setCompanyName(companyName);
            entireInventory.updatePart(partIndex, updatedPart);

        } else{
            int machineId = new Integer(textPartCompanyName.getText());
            InhousePart updatedPart = new InhousePart(this.displayedPart.getId(), partName, partPrice, partInv, partMinInv, partManInv, machineId);
//            ((InhousePart) displayedPart).setMachineId(machineId);
            entireInventory.updatePart(partIndex, updatedPart);

        }

        backToMainScreen();
    }

    public void intakeNewPart(Inventory inventory, int partId, int partIndex){
        this.entireInventory = inventory;
        this.displayedPart = inventory.lookupPart(partId);
        this.partIndex = partIndex;
//        category.selectToggle(RadioInhouse);

        setPartFields();
    }

    private void setPartFields(){
        partID.setText(Integer.toString(this.displayedPart.getId()));
        textPartName.setText(this.displayedPart.getName());
        textPartInventory.setText(Integer.toString(this.displayedPart.getStock()));
        textPartPrice.setText(Double.toString(this.displayedPart.getPrice()));
        textMaxPartInventory.setText(Integer.toString(this.displayedPart.getMax()));
        textMinPartInventory.setText(Integer.toString(this.displayedPart.getMin()));

        if (this.displayedPart instanceof OutsourcedPart){
//            this.displayedPart = (OutsourcedPart) this.displayedPart;
            textPartCompanyName.setText(((OutsourcedPart) this.displayedPart).getCompanyName());
            category.selectToggle(RadioOutsourced);
            clickInhouseRadio();
        } else {
//            this.displayedPart = (InhousePart) this.displayedPart;
            textPartCompanyName.setText( Integer.toString(((InhousePart) this.displayedPart).getMachineId()) );
            category.selectToggle(RadioInhouse);
            clickInhouseRadio();
        }

    }


    private void backToMainScreen() throws IOException {
        Stage stage;
        Parent root;

        stage=(Stage) btnSaveModifyPart.getScene().getWindow();
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
