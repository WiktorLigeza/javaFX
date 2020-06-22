package com.company.Models;
import com.company.Comparators.CompareQuantity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FulfillmentCenterContainer {

    public List<FulfillmentCenterModel> CentersList;
    public void addCenter(String name, double maxCapacity,String City){
        CentersList.add(new FulfillmentCenterModel(name,maxCapacity,City));
    }

    public FulfillmentCenterModel findByName(String name){
        for (FulfillmentCenterModel center : CentersList) {
            if(center.name.equals(name)){
                return center;
            }

        }
       return null;
    }

    public void removeCenter(FulfillmentCenterModel center){
        if(center != null){
            CentersList.remove(center);
        }
    }

    public void summary(){
        for (FulfillmentCenterModel center : CentersList) {
            center.summary();
        }
    }

    public void sortByFillLvl(){
        CompareQuantity compareQuantity = new CompareQuantity();
        CentersList.sort(compareQuantity);
    }

    //constr
    public FulfillmentCenterContainer(){
      CentersList = new ArrayList<>();
    }
}




//////////////////////////////////////////////dumpster/////////////////////////////////////
//    public Map<String, FulfillmentCenterModel> centers = new HashMap();
//
//
//    //methods
//
//    //a
//    public void addCenter(String s, double d) {
//        FulfillmentCenterModel f = new FulfillmentCenterModel(s,d);
//        centers.put(s,f);
//    }
//    //b
//    public void removeCenter(String s){
//        centers.remove(s);
//    }
//    //c
////    public List findEmpty(){
////        List<FulfillmentCenterModel> empty = new ArrayList();
////        for(Map.Entry<String, FulfillmentCenterModel> entry : centers.entrySet()){
////            if(entry.getValue().totalQuantity() == 0){
////                empty.add(new FulfillmentCenterModel(entry.getKey(), entry.getValue().maximumCapacity));
////            }
////        }
////        return empty;
////    }
//    //d
//    public void summary() {
//        for(Map.Entry<String,FulfillmentCenterModel> entry : centers.entrySet()){
//            System.out.println("Center name: "+ entry.getKey());
//            System.out.println("Fill level: "+ (entry.getValue().totalWeight()/entry.getValue().maximumCapacity)*100 + "%");
//            System.out.println("--------------------------");
//        }
//    }