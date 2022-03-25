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
package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    private IntegerProperty prodID;
    private StringProperty prodName;
    private DoubleProperty prodPrice;
    private IntegerProperty prodInv;
    private IntegerProperty prodMin;
    private IntegerProperty prodMax;

    public Product() {
        prodID = new SimpleIntegerProperty();
        prodInv = new SimpleIntegerProperty();
        prodMin = new SimpleIntegerProperty();
        prodMax = new SimpleIntegerProperty();
        prodName = new SimpleStringProperty();
        prodPrice = new SimpleDoubleProperty();
    }

    public IntegerProperty prodIDProp() {
        return prodID;
    }

    public StringProperty prodNameProp() {
        return prodName;
    }

    public DoubleProperty prodPriceProp() {
        return prodPrice;
    }

    public IntegerProperty prodInvProp() {
        return prodInv;
    }

    public IntegerProperty prodMinProp() {
        return prodMin;
    }

    public IntegerProperty prodMaxProp() {
        return prodMax;
    }

    public void setProdID(int prodID) {
        this.prodID.set(prodID);
    }

    public int getProdID() {
        return this.prodID.get();
    }

    public void setProdName(String prodName) {
        this.prodName.set(prodName);
    }

    public String getProdName() {
        return this.prodName.get();
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice.set(prodPrice);
    }

    public double getProdPrice() {
        return this.prodPrice.get();
    }

    public void setProdInv(int prodInv) {
        this.prodInv.set(prodInv);
    }

    public int getProdInv() {
        return this.prodInv.get();
    }

    public void setProdMin(int prodMin) {
        this.prodMin.set(prodMin);
    }

    public int getProdMin() {
        return this.prodMin.get();
    }

    public void setProdMax(int prodMax) {
        this.prodMax.set(prodMax);
    }

    public int getProdMax() {
        return this.prodMax.get();
    }

    public ObservableList getAssocParts() {
        return assocParts;
    }

    public void setAssocParts(ObservableList<Part> assocParts) {
        this.assocParts = assocParts;
    }

    public static String isProdValid(String prodName, int prodInv, double prodPrice, int prodMin, int prodMax, ObservableList<Part> parts, String errMsg) {
        double sumPriceParts = 0.00;

        for (int i = 0; i < parts.size(); i++) {
            sumPriceParts = sumPriceParts + parts.get(i).getPartPrice();
        }
        if (prodName.length() < 1) {
            errMsg = errMsg + ("Name must not be blank. ");
        }
        if (prodInv < prodMin || prodInv > prodMax){
            errMsg = errMsg + ("Inventory level must be greater than minimum and less than maximum. ");  
        }
        if (prodPrice <= 0) {
            errMsg = errMsg + ("Price must be greater than $0. ");
        }
        if (prodMin < 0) {
            errMsg = errMsg + ("Minimum inventory level must not be less than 0. ");
        }
        if (prodMax < prodMin) {
            errMsg = errMsg + ("Minimum inventory level must not be greater than maximum. ");  
        }
        if (sumPriceParts > prodPrice) {
            errMsg = errMsg + ("The product price must be greater than the combined cost of parts. ");
        }
        if (parts.size() < 1) {
            errMsg = errMsg + ("There must be at least 1 part associated with this product. ");
        }
        return errMsg;
    }

}

/*

void setName
String getName
void setPrice
double getPrice
void setInv
int getInv
void setMin
int getMin
void setMax
int getMax

void addAssocPart
boolean delAssocPart
part searchAssocPart
void setProdID
int getProdID

*/
