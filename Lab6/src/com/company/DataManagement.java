package com.company;

import com.company.Models.FulfillmentCenterModel;
import com.company.Models.ItemModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.Enum.SaleState.NEW;

public class DataManagement {
    static File ItemsFile = new File("centers.csv");

    //export
    public static void saveCurrentStateToCSV(List<FulfillmentCenterModel> centers)
            throws IOException {

        FileOutputStream fos = new FileOutputStream(ItemsFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for (FulfillmentCenterModel center : centers) {
            oos.writeObject(center);
        }
    }


    //import
    public static List<FulfillmentCenterModel> importDataFromCSV() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(ItemsFile);
        ArrayList<FulfillmentCenterModel> centers = new ArrayList<>();
        ObjectInputStream ois = new ObjectInputStream(fis);

        FulfillmentCenterModel center = null;
        boolean isExist = true;
        while(isExist){
            if(fis.available() != 0){
                center = (FulfillmentCenterModel) ois.readObject();
                //set state of imported items to NEW
                for (ItemModel item: center.itemsList) {
                    item.state = NEW;;
                }
                centers.add(center);
            }
            else{
                isExist =false;
            }
        }
        return centers;
    }
}
