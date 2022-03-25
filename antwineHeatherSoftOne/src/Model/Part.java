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

public abstract class Part {

    private final IntegerProperty partID;
    private final StringProperty partName;
    private final DoubleProperty partPrice;
    private final IntegerProperty partInv;
    private final IntegerProperty partMin;
    private final IntegerProperty partMax;

    public Part() {
        partID = new SimpleIntegerProperty();
        partInv = new SimpleIntegerProperty();
        partMin = new SimpleIntegerProperty();
        partMax = new SimpleIntegerProperty();
        partName = new SimpleStringProperty();
        partPrice = new SimpleDoubleProperty();
    }

    public IntegerProperty partIDProp() {
        return partID;
    }

    public StringProperty partNameProp() {
        return partName;
    }

    public DoubleProperty partPriceProp() {
        return partPrice;
    }

    public IntegerProperty partInvProp() {
        return partInv;
    }

    public IntegerProperty partMinProp() {
        return partMin;
    }

    public IntegerProperty partMaxProp() {
        return partMax;
    }

    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public int getPartID() {
        return this.partID.get();
    }

    public void setPartName(String partName) {
        this.partName.set(partName);
    }

    public String getPartName() {
        return this.partName.get();
    }

    public void setPartPrice(double partPrice) {
        this.partPrice.set(partPrice);
    }

    public double getPartPrice() {
        return this.partPrice.get();
    }

    public void setPartInv(int partInv) {
        this.partInv.set(partInv);
    }

    public int getPartInv() {
        return this.partInv.get();
    }

    public void setPartMin(int partMin) {
        this.partMin.set(partMin);
    }

    public int getPartMin() {
        return this.partMin.get();
    }

    public void setPartMax(int partMax) {
        this.partMax.set(partMax);
    }

    public int getPartMax() {
        return this.partMax.get();
    }

    public static String isPartValid(String partName, int partInv, double partPrice, int partMax, int partMin, String errMsg) {
        if (partName.length() < 1) {
            errMsg = errMsg + ("Name cannot be blank. ");
        }
        if (partInv < 1) {
            errMsg = errMsg + ("Inventory must be at least 1. ");
        }
        if (partPrice <= 0) {
            errMsg = errMsg + ("Price must be greater than $0. ");
        }
        if (partMax < partMin) {
            errMsg = errMsg + ("Maximum inventory must be greater than minimum. ");
        }
        if (partInv > partMax) {
            errMsg = errMsg + ("Inventory level must be less than the set maximum. ");
        }
        if (partInv < partMin) {
            errMsg = errMsg + ("Inventory level must be greater than the set minimum. ");
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
void setPartID
int getPartID

*/
