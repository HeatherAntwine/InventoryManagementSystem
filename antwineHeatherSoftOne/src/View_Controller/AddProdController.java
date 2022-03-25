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
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddProdController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Button delButton;
    @FXML
    private TextField prodIDTF;
    @FXML
    private TextField prodNameTF;
    @FXML
    private TextField prodInvTF;
    @FXML
    private TextField prodPriceTF;
    @FXML
    private TextField prodMaxTF;
    @FXML
    private TextField prodMinTF;
    @FXML
    private TextField searchTF;
    @FXML
    private TableView<Part> addPartTV;
    @FXML
    private TableView<Part> addAssocPartTV;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Part, Integer> assocPartIDCol;
    @FXML
    private TableColumn<Part, String> assocPartNameCol;
    @FXML
    private TableColumn<Part, Integer> assocPartInvCol;
    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;
    private final ObservableList<Part> assignedParts = FXCollections.observableArrayList();
    private String exceptionMsg = new String();
    private int prodID;

    public void searchButtonPushed(ActionEvent event) throws IOException {
        String findPart = searchTF.getText();
        int partIndex = -1;
        if (Inventory.searchPart(findPart) == -1) {
            Alert searchAlert = new Alert(Alert.AlertType.INFORMATION);
            searchAlert.setHeaderText("");
            searchAlert.setContentText("Part not found.");
            searchAlert.showAndWait();
        } else {
            partIndex = Inventory.searchPart(findPart);
            Part foundPart = Inventory.getPartInv().get(partIndex);
            ObservableList<Part> foundPartList = FXCollections.observableArrayList();
            foundPartList.add(foundPart);
            addPartTV.setItems(foundPartList);
        }
    }

    //Top TV
    public void addButtonPushed(ActionEvent event) throws IOException {
        Part part = addPartTV.getSelectionModel().getSelectedItem();
        assignedParts.add(part);
        modAssignedPartTV();

    }

    //Bottom TV
    public void delButtonPushed(ActionEvent event) throws IOException {
        Part part = addAssocPartTV.getSelectionModel().getSelectedItem();
        Alert delAlert = new Alert(Alert.AlertType.CONFIRMATION);
        delAlert.initModality(Modality.NONE);
        delAlert.setHeaderText("");
        delAlert.setContentText("Remove part from associated part list?");
        Optional<ButtonType> userConfirm = delAlert.showAndWait();

        if (userConfirm.get() == ButtonType.OK) {
            assignedParts.remove(part);
        } else {
        }
    }

    @FXML
    void saveButtonPushed(ActionEvent event) throws IOException {
        String prodName = prodNameTF.getText();
        String prodInv = prodInvTF.getText();
        String prodPrice = prodPriceTF.getText();
        String prodMin = prodMinTF.getText();
        String prodMax = prodMaxTF.getText();

        try {
            exceptionMsg = Product.isProdValid(prodName, Integer.parseInt(prodInv), Double.parseDouble(prodPrice), Integer.parseInt(prodMin), Integer.parseInt(prodMax), assignedParts, exceptionMsg);

            if (exceptionMsg.length() > 0) {
                Alert saveAlert = new Alert(Alert.AlertType.INFORMATION);
                saveAlert.setTitle("Error adding Product.");
                saveAlert.setContentText(exceptionMsg);
                saveAlert.showAndWait();
                exceptionMsg = "";
            } else {
                System.out.println("");
                Product newProd = new Product();

                newProd.setProdID(prodID);
                newProd.setProdName(prodName);
                newProd.setProdInv(Integer.parseInt(prodInv));
                newProd.setProdPrice(Double.parseDouble(prodPrice));
                newProd.setProdMin(Integer.parseInt(prodMin));
                newProd.setProdMax(Integer.parseInt(prodMax));
                newProd.setAssocParts(assignedParts);
                Inventory.addProd(newProd);

                Parent saveProd = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
                Scene saveScene = new Scene(saveProd);
                Stage saveWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                saveWindow.setScene(saveScene);
                saveWindow.show();
            }

        } catch (NumberFormatException e) {
            Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
            cancelAlert.setContentText("All fields must be filled!");
            cancelAlert.showAndWait();
        }
    }

    public void cancelButtonPushed(ActionEvent event) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.initModality(Modality.NONE);
        cancelAlert.setHeaderText("");
        cancelAlert.setContentText("Cancel creation of product?");
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
    //Bottom TV in Prod Window

    public void modAssignedPartTV() {
        addAssocPartTV.setItems(assignedParts);
    }

    //Top TV in Prod Window
    public void modPartTV() {
        addPartTV.setItems(getPartInv());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProp().asObject());
        partNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProp());
        partInvCol.setCellValueFactory(cellData -> cellData.getValue().partInvProp().asObject());
        partPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProp().asObject());
        assocPartIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProp().asObject());
        assocPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProp());
        assocPartInvCol.setCellValueFactory(cellData -> cellData.getValue().partInvProp().asObject());
        assocPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProp().asObject());
        modAssignedPartTV();
        modPartTV();

        prodID = Inventory.getProdCount();
        prodIDTF.setText("AUTO GEN: " + prodID);
    }

}


/*

@FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Part, Integer> assocPartIDCol;
    @FXML
    private TableColumn<Part, String> assocPartNameCol;
    @FXML
    private TableColumn<Part, Integer> assocPartInvCol;
    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;

 */
