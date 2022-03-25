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

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Inventory {

    private static ObservableList<Product> prodInv = FXCollections.observableArrayList();
    private static ObservableList<Part> partInv = FXCollections.observableArrayList();
    private static int partCount = 0;
    private static int prodCount = 0;

    public static void updateProd(int prodIndex, Product modProd) {
        prodInv.set(prodIndex, modProd);
    }

    public Inventory() {
    }

    public static ObservableList<Part> getPartInv() {
        return partInv;
    }

    public static ObservableList<Product> getProdInv() {
        return prodInv;
    }

    public static void addProd(Product product) {
        prodInv.add(product);
    }

    public static void delProd(Product product) {
        prodInv.remove(product);
    }

    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int searchProd(String searchPhrase) {
        boolean isLocated = false;
        int index = 0;
        if (isInt(searchPhrase)) {
            for (int i = 0; i < prodInv.size(); i++) {
                if ((prodInv.get(i).getProdID()) == Integer.parseInt(searchPhrase)) {
                    index = i;
                    isLocated = true;
                }
            }
        } else {
            for (int i = 0; i < prodInv.size(); i++) {
                if (searchPhrase.equals(prodInv.get(i).getProdName())) {
                    index = i;
                    isLocated = true;
                }
            }
        }
        if (isLocated = true) {
            return index;
        } else {
            System.out.println("Product not found.");
            return -1;
        }
    }
//
//    public static void modProd(int index, Product product) {
//        prodInv.set(index, product);
//    }

    public static void addPart(Part part) {
        partInv.add(part);
    }

    public static void delPart(Part part) {
        partInv.remove(part);
    }

    public static int searchPart(String searchPhrase) {
        boolean isLocated = false;
        int index = 0;
        if (isInt(searchPhrase)) {
            for (int i = 0; i < partInv.size(); i++) {
                if ((partInv.get(i).getPartID()) == Integer.parseInt(searchPhrase)) {
                    index = i;
                    isLocated = true;
                }
            }
        } else {
            for (int i = 0; i < partInv.size(); i++) {
                if (searchPhrase.equals(partInv.get(i).getPartName())) {
                    index = i;
                    isLocated = true;
                }
            }
        }
        if (isLocated = true) {
            return index;
        } else {
            System.out.println("Part not found.");
            return -1;
        }
    }

    public static void modPart(int index, Part part) {
        partInv.set(index, part);
    }

    public static int getProdCount() {
        prodCount++;
        return prodCount;
    }

    public static int getPartCount() {
        partCount++;
        return partCount;
    }

    public static boolean delPartValid(Part part) {
        boolean isLocated = false;
        for (int i = 0; i < prodInv.size(); i++) {
            if (prodInv.get(i).getAssocParts().contains(part)) {
                isLocated = true;
            }
        }
        return isLocated;
    }
//    public static boolean delProdValid(Product product){
//        boolean noAssocParts = product.getAssocParts().isEmpty();
//        return noAssocParts;
//    }

}


/*

void addProd
boolean delProd
product searchProd
void modProd
void addPart
boolean delPart
part searchPart
void modPart

 */
