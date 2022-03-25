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
import static Model.Inventory.getProdInv;
import Model.Part;
import Model.Product;
import static View_Controller.MainScreenController.indexModProd;
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

public class ModifyProdController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button addAssociatedButton;
    @FXML
    private Button delAssociatedButton;
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
    private TableView<Part> modPartTV;
    @FXML
    private TableView<Part> modAssocPartTV;
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
    private int prodID;
    private int prodIndex = indexModProd();
    private String exceptionMsg = new String();
    private ObservableList<Part> assignedParts = FXCollections.observableArrayList();

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
                saveAlert.setTitle("Error changing product.");
                saveAlert.setContentText(exceptionMsg);
                saveAlert.showAndWait();
                exceptionMsg = "";
            } else {
                Product modProd = new Product();
                modProd.setProdID(prodID);
                modProd.setProdName(prodName);
                modProd.setProdInv(Integer.parseInt(prodInv));
                modProd.setProdPrice(Double.parseDouble(prodPrice));
                modProd.setProdMin(Integer.parseInt(prodMin));
                modProd.setProdMax(Integer.parseInt(prodMax));
                modProd.setAssocParts(assignedParts);
                Inventory.updateProd(prodIndex, modProd);
                Parent saveParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
                Scene saveScene = new Scene(saveParent);
                Stage saveWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                saveWindow.setScene(saveScene);
                saveWindow.show();
            }
        } catch (NumberFormatException e) {
            Alert catchAlert = new Alert(Alert.AlertType.INFORMATION);
            catchAlert.setHeaderText("");
            catchAlert.setContentText("All fields must be filled.");
            catchAlert.showAndWait();
        }
    }

    @FXML
    void cancelButtonPushed(ActionEvent event) throws IOException {
        Parent cancelParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Scene cancelScene = new Scene(cancelParent);
        Stage cancelWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        cancelWindow.setScene(cancelScene);
        cancelWindow.show();
    }

    @FXML
    void searchButtonPushed(ActionEvent event) throws IOException {
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
            modPartTV.setItems(foundPartList);
        }
    }

    @FXML
    void addButtonPushed(ActionEvent event) throws IOException {
        Part part = modPartTV.getSelectionModel().getSelectedItem();
        assignedParts.add(part);
        updateAssocPartTV();
    }

    @FXML
    void delButtonPushed(ActionEvent event) throws IOException {
        Part part = modAssocPartTV.getSelectionModel().getSelectedItem();
        Alert delAlert = new Alert(Alert.AlertType.CONFIRMATION);
        delAlert.initModality(Modality.NONE);
        delAlert.setHeaderText("");
        delAlert.setContentText("Disassociate this part?");
        Optional<ButtonType> userConfirm = delAlert.showAndWait();
        if (userConfirm.get() == ButtonType.OK) {
            assignedParts.remove(part);
        } else {

        }
    }

    public void updateAssocPartTV() {
        modAssocPartTV.setItems(assignedParts);
    }

    public void updatePartTV() {
        modPartTV.setItems(getPartInv());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product product = getProdInv().get(prodIndex);
        prodID = getProdInv().get(prodIndex).getProdID();
        prodIDTF.setText("Auto Gen: " + prodID);
        prodNameTF.setText(product.getProdName());
        prodInvTF.setText(Integer.toString(product.getProdInv()));
        prodPriceTF.setText(Double.toString(product.getProdPrice()));
        prodMinTF.setText(Integer.toString(product.getProdMin()));
        prodMaxTF.setText(Integer.toString(product.getProdMax()));
        assignedParts = product.getAssocParts();
        partIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProp().asObject());
        partNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProp());
        partInvCol.setCellValueFactory(cellData -> cellData.getValue().partInvProp().asObject());
        partPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProp().asObject());
        assocPartIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProp().asObject());
        assocPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProp());
        assocPartInvCol.setCellValueFactory(cellData -> cellData.getValue().partInvProp().asObject());
        assocPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProp().asObject());
        updatePartTV();
        updateAssocPartTV();

    }

}

/*


    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TableColumn<Part, Integer> assocPartIDCol;
    @FXML private TableColumn<Part, String> assocPartNameCol;
    @FXML private TableColumn<Part, Integer> assocPartInvCol;
    @FXML private TableColumn<Part, Double> assocPartPriceCol;

*/
