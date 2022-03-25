/*
HA
   `..---..`                                    `..---..`
`ommmmmmmmmmdhyo/-` .:+syhddmmmmddhys+:. `-/oyhdmmmmmmmmmmo`
  `+dmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmd+`
     :ymmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmy:
       .odmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmdo.
         `/hmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmh/`
      .s/`  -ohmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmho-  `/h+
     -dmmh`  -sdmmmmmmmmmmyosdmmmmmmmmmmmmmmms:   smmms
    `hmmd. `ymmhooydmmmmm+   .mmmmmmmmh++ydmmmmy.  smmm/
    :mmm+  ymmo /mmmmmdmm/    mmmmmmm+ /mmmmmmmmh  `dmmh
    /mmm: `mmd  ommmmmyym/    mmmmmmh  ommmmmyhmm-  ymmm`
    :mmm+  dmm-  /syy+.dm/    mmmmmmm-  /syy+-dmd`  hmmd
    `dmmd` -dmmo-   .+dmm-    mmmmhdmd+.   .+dmm:  /mmmo
     :mmmh. .odmmmdmmmms-     mmmmo.sdmmmdmmmms.  /mmmh`
      :dmmd+` `-/+oo+:`       mmmmy` `-/ooo/-`  -ymmmh`
       .smmmmy+-.`   `-/y/    mmmmmmy+-.`  `.:ohmmmd+
         .odmmmmmmmmmmmmmmd/  mmmmmmmmmmmmmmmmmmmy/`
            ./oyhddddhhmmmmmd/mmmmmmmdyhdddhys+:`
                       .smmmmmmmmmmh/
                         `+dmmmmmy:
                           `/hms-
                              `
Created by Heather R Antwine
C482 Software I
Western Governors University
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.inhousePart;
import Model.outsourcedPart;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddPartController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField partIDTF;
    @FXML
    private TextField partNameTF;
    @FXML
    private TextField partInvTF;
    @FXML
    private TextField partPriceTF;
    @FXML
    private TextField partMaxTF;
    @FXML
    private TextField partMinTF;
    @FXML
    private TextField deltaTF;
    @FXML
    private Label deltaLabel;
    @FXML
    private RadioButton inRB;
    @FXML
    private RadioButton outRB;
    private ToggleGroup sourceTG;
    private boolean isInhouse;
    private String exceptionMsg = new String();
    private int partID;

    @FXML
    void outRBPushed(ActionEvent event) {
        isInhouse = false;
        deltaLabel.setText("Company Name");
    }

    @FXML
    void inRBPushed(ActionEvent event) {
        isInhouse = true;
        deltaLabel.setText("Machine ID");
    }

    @FXML
    void saveButtonPushed(ActionEvent event) throws IOException {
        String partName = partNameTF.getText();
        String partInv = partInvTF.getText();
        String partPrice = partPriceTF.getText();
        String partMax = partMaxTF.getText();
        String partMin = partMinTF.getText();
        String partDelta = deltaTF.getText();

        try {
            exceptionMsg = Part.isPartValid(partName, Integer.parseInt(partInv), Double.parseDouble(partPrice), Integer.parseInt(partMax), Integer.parseInt(partMin), exceptionMsg);
            if (exceptionMsg.length() > 0) {
                Alert saveAlert = new Alert(AlertType.INFORMATION);
                saveAlert.setTitle("Error Creating New Part");
                saveAlert.setContentText(exceptionMsg);
                saveAlert.setTitle("Error");
                saveAlert.showAndWait();
                exceptionMsg = "";
            } else {
                if (isInhouse == true) {
                    inhousePart inPart = new inhousePart();
                    inPart.setPartID(partID);
                    inPart.setPartName(partName);
                    inPart.setPartInv(Integer.parseInt(partInv));
                    inPart.setPartPrice(Double.parseDouble(partPrice));
                    inPart.setPartMax(Integer.parseInt(partMax));
                    inPart.setPartMin(Integer.parseInt(partMin));
                    inPart.setMachineID(Integer.parseInt(partDelta));
                    Inventory.addPart(inPart);
                } else {
                    outsourcedPart outPart = new outsourcedPart();
                    outPart.setPartID(partID);
                    outPart.setPartName(partName);
                    outPart.setPartInv(Integer.parseInt(partInv));
                    outPart.setPartPrice(Double.parseDouble(partPrice));
                    outPart.setPartMax(Integer.parseInt(partMax));
                    outPart.setPartMin(Integer.parseInt(partMin));
                    outPart.setCompName(partDelta);
                    Inventory.addPart(outPart);
                }
                Parent partSavedParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
                Scene savedScene = new Scene(partSavedParent);
                Stage savedWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                savedWindow.setScene(savedScene);
                savedWindow.show();
            }
        } catch (NumberFormatException e) {
            Alert saveCatchAlert = new Alert(AlertType.INFORMATION); //Show alert as only information
            saveCatchAlert.setContentText("Every field must be filled.");
            saveCatchAlert.setTitle("Error");
            saveCatchAlert.showAndWait();
        }
    }

    public void cancelButtonPushed(ActionEvent event) throws IOException {
        Alert cancelAlert = new Alert(AlertType.CONFIRMATION); //Show alert as confirmation type
        cancelAlert.initModality(Modality.NONE); //Must set Modality to NONE to keep from blocking windows
        cancelAlert.setHeaderText("");
        cancelAlert.setContentText("Cancel part creation?");
        Optional<ButtonType> userConfirmation = cancelAlert.showAndWait(); //Optional because object may or may not contain a non-null value - but should because of the exception handling from addPart creation!

        if (userConfirmation.get() == ButtonType.OK) { //ButtonType.YES is a pre-defined ButtonType that displays Yes and has a ButtonBar
            Parent cancelAddParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
            Scene cancelAddScene = new Scene(cancelAddParent);
            Stage cancelAddWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            cancelAddWindow.setScene(cancelAddScene);
            cancelAddWindow.show();
        } else {
            System.out.println("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sourceTG = new ToggleGroup();
        this.inRB.setToggleGroup(sourceTG);
        this.outRB.setToggleGroup(sourceTG);

        partID = Inventory.getPartCount();
        partIDTF.setText("AUTO GEN: " + partID);

    }

}


/*

    @FXML private TextField partIDTF;
    @FXML private TextField partNameTF;
    @FXML private TextField partInvTF;
    @FXML private TextField partPriceTF;
    @FXML private TextField partMaxTF;
    @FXML private TextField partMinTF;
    @FXML private TextField deltaTF;
    @FXML private Label deltaLabel;

*/
