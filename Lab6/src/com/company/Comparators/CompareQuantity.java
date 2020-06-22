package com.company.Comparators;
import com.company.Models.FulfillmentCenterModel;
import java.util.Comparator;

public class CompareQuantity implements Comparator<FulfillmentCenterModel> {
    @Override
    public int compare(FulfillmentCenterModel s1, FulfillmentCenterModel s2) {
        if (s1.fillLvl() > s2.fillLvl()) {
            return 1;
        }
        //does not
        else {
            return -1;
        }
    }
}
