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
import static Model.Inventory.getPartInv;
import Model.Part;
import Model.inhousePart;
import Model.outsourcedPart;
import static View_Controller.MainScreenController.indexModProd;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyPartController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton inRB;
    @FXML
    private RadioButton outRB;
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
    private ToggleGroup sourceTG;
    private boolean isInhouse;
    int partIndex = indexModProd();
    private String exceptionMsg = new String();
    private int partID;

    @FXML
    void saveButtonPushed(ActionEvent event) throws IOException {
        String partName = partNameTF.getText();
        String partInv = partInvTF.getText();
        String partPrice = partPriceTF.getText();
        String partMin = partMinTF.getText();
        String partMax = partMaxTF.getText();
        String partDelta = deltaTF.getText();
        try {
            exceptionMsg = Part.isPartValid(partName, Integer.parseInt(partInv), Double.parseDouble(partPrice), Integer.parseInt(partMax), Integer.parseInt(partMin), exceptionMsg);
            if (exceptionMsg.length() > 0) {
                Alert saveAlert = new Alert(Alert.AlertType.INFORMATION);
                saveAlert.setTitle("Error Changing Part");
                saveAlert.setContentText(exceptionMsg);
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
                    Inventory.modPart(partIndex, inPart);
                } else {
                    outsourcedPart outPart = new outsourcedPart();
                    outPart.setPartID(partID);
                    outPart.setPartName(partName);
                    outPart.setPartInv(Integer.parseInt(partInv));
                    outPart.setPartPrice(Double.parseDouble(partPrice));
                    outPart.setPartMax(Integer.parseInt(partMax));
                    outPart.setPartMin(Integer.parseInt(partMin));
                    outPart.setCompName(partDelta);
                    Inventory.modPart(partIndex, outPart);

                }

                Parent cancelParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
                Scene cancelScene = new Scene(cancelParent);
                Stage cancelWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                cancelWindow.setScene(cancelScene);
                cancelWindow.show();
            }
        } catch (NumberFormatException e) {
            Alert errAlert = new Alert(Alert.AlertType.INFORMATION);
            errAlert.setHeaderText("");
            errAlert.setContentText("All fields must be filled.");
            errAlert.showAndWait();
        }
    }

    @FXML
    void cancelButtonPushed(ActionEvent event) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.initModality(Modality.NONE);
        cancelAlert.setHeaderText("");
        cancelAlert.setContentText("Cancel the modification of this part?");
        Optional<ButtonType> userConfirm = cancelAlert.showAndWait();

        if (userConfirm.get() == ButtonType.OK) {
            Parent cancelParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
            Scene cancelScene = new Scene(cancelParent);
            Stage cancelWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            cancelWindow.setScene(cancelScene);
            cancelWindow.show();
        } else {

        }

    }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sourceTG = new ToggleGroup();
        this.inRB.setToggleGroup(sourceTG);
        this.outRB.setToggleGroup(sourceTG);

        partID = getPartInv().get(partIndex).getPartID();
        Part part = getPartInv().get(partIndex);
        partIDTF.setText("Auto Gen: " + partID);
        partNameTF.setText(part.getPartName());
        partInvTF.setText(Integer.toString(part.getPartInv()));
        partPriceTF.setText(Double.toString(part.getPartPrice()));
        partMaxTF.setText(Integer.toString(part.getPartMax()));
        partMinTF.setText(Integer.toString(part.getPartMin()));
        if (part instanceof inhousePart) {
            deltaTF.setText(Integer.toString(((inhousePart) getPartInv().get(partIndex)).getMachineID()));
            deltaLabel.setText("Machine ID");
            inRB.setSelected(true);
        } else {
            deltaTF.setText(((outsourcedPart) getPartInv().get(partIndex)).getCompName());
            deltaLabel.setText("Company Name");
            outRB.setSelected(true);
        }
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
