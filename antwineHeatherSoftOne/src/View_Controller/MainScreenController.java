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
import static Model.Inventory.delPart;
import static Model.Inventory.delPartValid;
import static Model.Inventory.delProd;
import static Model.Inventory.getPartInv;
import static Model.Inventory.getProdInv;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {

    @FXML
    private Button exitButton;
    @FXML
    private Button addPartButton;
    @FXML
    private Button modPartButton;
    @FXML
    private Button delPartButton;
    @FXML
    private Button searchPartButton;
    @FXML
    private Button addProdButton;
    @FXML
    private Button modProdButton;
    @FXML
    private Button delProdButton;
    @FXML
    private Button searchProdButton;
    @FXML
    private TextField searchPartTF;
    @FXML
    private TextField searchProdTF;
    @FXML
    private TableView<Part> mainPartTV;
    @FXML
    private TableView<Product> mainProdTV;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Product, Integer> prodIDCol;
    @FXML
    private TableColumn<Product, String> prodNameCol;
    @FXML
    private TableColumn<Product, Integer> prodInvCol;
    @FXML
    private TableColumn<Product, Double> prodPriceCol;
    private static Part modPart;
    private static Product modProd;
    private static int modPartIndex;
    private static int modProdIndex;

    public static int indexModPart() {
        return modPartIndex;
    }

    public static int indexModProd() {
        return modProdIndex;
    }

    @FXML
    void exitButtonPushed(ActionEvent event) throws IOException {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.initModality(Modality.NONE);
        exitAlert.setHeaderText("");
        exitAlert.setContentText("Exit program?");
        Optional<ButtonType> userConfirm = exitAlert.showAndWait();

        if (userConfirm.get() == ButtonType.OK) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("");
        }
    }

    @FXML
    void addPartButtonPushed(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("addPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage addPartWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartWindow.setScene(addPartScene);
        addPartWindow.setTitle("Add New Part");
        addPartWindow.show();
    }

    @FXML
    void modPartButtonPushed(ActionEvent event) throws IOException {
        Parent modPartParent = FXMLLoader.load(getClass().getResource("modifyPart.fxml"));
        Scene modPartScene = new Scene(modPartParent);
        Stage modPartWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modPartWindow.setScene(modPartScene);
        modPartWindow.setTitle("Modify Part");
        modPartWindow.show();
    }

    @FXML
    void delPartButtonPushed(ActionEvent event) throws IOException {
        Part part = mainPartTV.getSelectionModel().getSelectedItem();
        if (delPartValid(part)) {
            Alert delAlert = new Alert(AlertType.INFORMATION);
            delAlert.setTitle("Error deleting part. ");
            delAlert.setHeaderText("");
            delAlert.setContentText("Part cannot be deleted - Associated with a product.");
            delAlert.showAndWait();
        } else {
            Alert delAlert = new Alert(AlertType.CONFIRMATION);
            delAlert.initModality(Modality.NONE);
            delAlert.setHeaderText("");
            delAlert.setContentText("Delete this part?");
            Optional<ButtonType> userConfirm = delAlert.showAndWait();

            if (userConfirm.get() == ButtonType.OK) {
                delPart(part);
                updatePartTV();
            } else {
                System.out.println("");
            }
        }

    }

    @FXML
    void searchPartButtonPushed(ActionEvent event) throws IOException {
        String findPart = searchPartTF.getText();
        int partIndex = -1;
        if (Inventory.searchPart(findPart) == -1) {
            Alert searchAlert = new Alert(AlertType.INFORMATION);
            searchAlert.setContentText("No matching parts found.");
            searchAlert.setHeaderText("");
            searchAlert.showAndWait();
        } else {
            partIndex = Inventory.searchPart(findPart);
            Part foundPart = Inventory.getPartInv().get(partIndex);
            ObservableList<Part> foundPartList = FXCollections.observableArrayList();
            foundPartList.add(foundPart);
            mainPartTV.setItems(foundPartList);
        }
    }

    public void updatePartTV() {
        mainPartTV.setItems(getPartInv());
    }

    public void updateProdTV() {
        mainProdTV.setItems(getProdInv());
    }

    public void addProdButtonPushed(ActionEvent event) throws IOException {
        Parent addProdParent = FXMLLoader.load(getClass().getResource("addProd.fxml"));
        Scene addProdScene = new Scene(addProdParent);
        Stage addProdWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProdWindow.setScene(addProdScene);
        addProdWindow.setTitle("Add New Product");
        addProdWindow.show();

        /*
        
        Parent modPartParent = FXMLLoader.load(getClass().getResource("modifyPart.fxml"));
        Scene modPartScene = new Scene(modPartParent);
        Stage modPartWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        modPartWindow.setScene(modPartScene);
        modPartWindow.setTitle("Modify Part");
        modPartWindow.show();
        
         */
    }

    @FXML
    void modProdButtonPushed(ActionEvent event) throws IOException {
        Parent modProdParent = FXMLLoader.load(getClass().getResource("modifyProd.fxml"));
        Scene modProdScene = new Scene(modProdParent);
        Stage modProdWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modProdWindow.setScene(modProdScene);
        modProdWindow.setTitle("Modify Product");
        modProdWindow.show();
    }

    @FXML
    void delProdButtonPushed(ActionEvent event) throws IOException {
        Product product = mainProdTV.getSelectionModel().getSelectedItem();
        boolean noAssocParts = product.getAssocParts().isEmpty();
        if (noAssocParts == true) {
            Alert delAlert = new Alert(AlertType.CONFIRMATION);
            delAlert.initModality(Modality.NONE);
            delAlert.setHeaderText("");
            delAlert.setContentText("Delete Product?");
            Optional<ButtonType> userConfirm = delAlert.showAndWait();
            if (userConfirm.get() == ButtonType.OK) {
                delProd(product);
                updateProdTV();
            } else {
            }
        } else {
            Alert delAlert = new Alert(AlertType.INFORMATION);
            delAlert.setTitle("Error deleting product. ");
            delAlert.setHeaderText("");
            delAlert.setContentText("Product cannot be deleted - has associated parts. ");
            delAlert.showAndWait();
        }

    }

    @FXML
    void searchProdButtonPushed(ActionEvent event) throws IOException {
        String findProd = searchProdTF.getText();
        int prodIndex = -1;
        if (Inventory.searchProd(findProd) == -1) {
            Alert searchAlert = new Alert(AlertType.INFORMATION);
            searchAlert.setHeaderText("");
            searchAlert.setContentText("Product not found.");
            searchAlert.showAndWait();
        } else {
            prodIndex = Inventory.searchProd(findProd);
            Product foundProd = Inventory.getProdInv().get(prodIndex);
            ObservableList<Product> foundProdList = FXCollections.observableArrayList();
            foundProdList.add(foundProd);
            mainProdTV.setItems(foundProdList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProp().asObject());
        partNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProp());
        partInvCol.setCellValueFactory(cellData -> cellData.getValue().partInvProp().asObject());
        partPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProp().asObject());
        prodIDCol.setCellValueFactory(cellData -> cellData.getValue().prodIDProp().asObject());
        prodNameCol.setCellValueFactory(cellData -> cellData.getValue().prodNameProp());
        prodInvCol.setCellValueFactory(cellData -> cellData.getValue().prodInvProp().asObject());
        prodPriceCol.setCellValueFactory(cellData -> cellData.getValue().prodPriceProp().asObject());
        updatePartTV();
        updateProdTV();
    }

}


/*

    @FXML private TableView<Part> mainPartTV;
    @FXML private TableView<Product> mainProdTV;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TableColumn<Product, Integer> prodIDCol;
    @FXML private TableColumn<Product, String> prodNameCol;
    @FXML private TableColumn<Product, Integer> prodInvCol;
    @FXML private TableColumn<Product, Double> prodPriceCol;

 */
