package com.company.Models;
import com.company.Comparators.CompareQuantity;
import com.company.Enum.SaleState;

import java.io.Serializable;
import java.util.*;


public class FulfillmentCenterModel implements Serializable {
    public String name;
    public double maximumCapacity; //by weight
    public List<ItemModel> itemsList;
    public String City;


    //local methods
    double totalWeight() {
        double total = 0;
        for (ItemModel item : itemsList) {
            total += item.weight;
        }
        return total;
    }

    int isItOnTheList(ItemModel item) {
        int value = -1;
        for (ItemModel i : itemsList) {
            value = item.compareTo(i);
            if(value == 0) break;
        }
        return value;
    }

    public double fillLvl(){
        return (this.totalWeight()/this.maximumCapacity)*100;
    }

    public boolean addProduct(ItemModel item){
        if((item.weight + totalWeight()) > maximumCapacity) {
            return false;
        }
        else {
            if(isItOnTheList(item) < 0){
                item.parent = this;
                itemsList.add(item);
            }
            else {
                item.quantity++;
            }
            return true;
        }
    }
    public void getProduct(ItemModel item) {
        if( item.quantity > 1) {
            item.quantity--;
        }
        else {
            removeProduct(item);
        }
    }

    public ItemModel findByName(String name){
        for (ItemModel item : itemsList) {
            if(item.name.equals(name)){
                return item;
            }

        }
        return null;
    }

    public List<ItemModel> searchPartial(String pattern) {
        String text;
        List<ItemModel> matchings = new ArrayList<ItemModel>();
        for (ItemModel item : itemsList) {
            boolean isFound = item.name.contains(pattern);
            if(isFound) {
                matchings.add(item);
            }
        }
        return matchings;
    }

    public void removeProduct(ItemModel item) {
        if(item != null){
            itemsList.remove(item);
        }
    }

    public  List<ItemModel> filterByState(String state){
        List<ItemModel> matchings = new ArrayList<ItemModel>();
        for (ItemModel item : itemsList) {
            if(item.state.toString().equals(state)) {
                matchings.add(item);
            }
        }
    return matchings;
    }

    public  List<ItemModel> filterByProduct(String name){
        List<ItemModel> matchings = new ArrayList<ItemModel>();
        for (ItemModel item : itemsList) {
            if(item.name.equals(name)) {
                matchings.add(item);
            }
        }
        return matchings;
    }

    public void summary() {
        System.out.println("Center name: "+ this.name);
        System.out.println("City: "+ this.City);
        System.out.println("Maximum capacity: "+ this.maximumCapacity);
        System.out.println("Fill level: "+ this.fillLvl() + "%");
    }

    //constr
    public FulfillmentCenterModel(String name,double maximumCapacity,String City){
        this.name = name;
        this.City = City;
        this.maximumCapacity = maximumCapacity;
        itemsList = new ArrayList<ItemModel>();
    }

    @Override
    public String toString() {
        return name;
    }

}


//////////////////////////////////////////////dumpster/////////////////////////////////////


        //itemsList.sort((s1,s2)->compare(s1,s2));
        // return Collections.frequency(itemsList.getState() , condition);

//Map<String, Integer> recurence = new HashMap<>();
//int count;
//for (String name : names) {
//    if (recurence.containsKey(name)) {
//        count = recurence.get(name) + 1;
//    } else {
//        count = 1;
//    }
//    recurence.put(name, count);
//}


//    public void getProduct(ItemModel item) {
//        if(isItOnTheList(item) < 0){
//            System.out.println("There is no such product!");
//        }
//        else {
//            for (ItemModel i : itemsList) {
//                if(item.compareTo(i) == 0) {
//                    if(i.quantity > 1) {
//                        i.quantity--;
//                        System.out.println("current quantity:" + i.quantity);
//                    }
//                    else {
//                        System.out.println("This product is currently out of stock!");
//                        itemsList.remove(i);
//                    }
//                }
//            }
//        }
//    }

//-----------------------own matching algorithm--------------------
//    public List<ItemModel> searchPartial(String pattern) {
//        String text;
//        List<ItemModel> matchings = new ArrayList<ItemModel>();
//        for (ItemModel item : itemsList) {
//            text = item.name;
//            int breaker = 0;
//            int i =0;
//            while( i<pattern.length() && text.length() >= pattern.length()) {
//                if(text.toCharArray()[i] == pattern.toCharArray()[i]) {
//                    breaker++;
//                }
//                if(breaker == pattern.length()) {
//                    matchings.add(item);
//                }
//                i++;
//            }
//        }
//        return matchings;
//    }

//    //a
//    public bo addProduct(ItemModel item) {
//
//        if((item.weight + totalWeight()) <= maximumCapacity) {
//
//        }
//        //b
//        public void getProduct(ItemModel item) {
//            if( item.quantity > 1) {
//                item.quantity--;
//                System.out.println("current quantity:" + item.quantity);
//            }
//            else {
//                System.out.println("This product is currently out of stock!");
//                removeProduct(item);
//            }
//        }
//        //c
//        public void removeProduct(ItemModel item) {
//            itemsList.remove(item);
//        }
//        //d
//        public ItemModel search(String s) {
//            ItemModel item =  new ItemModel(null, null, 0, 0);
//            item.name = s;
//            for (ItemModel i : itemsList) {
//                if(item.compareTo(i) == 0) {
//                    return i;
//                }
//            }
//            System.out.println("There is no such product!");
//            return item;
//        }
//        //e
//        public List<ItemModel> searchPartial(String pattern) {
//            String text;
//            List<ItemModel> matchings = new ArrayList<ItemModel>();
//            for (ItemModel item : itemsList) {
//                boolean isFound = item.name.contains(pattern);
//                if(isFound) {
//                    matchings.add(item);
//                }
//            }
//            return matchings;
//        }
//        //f
//        public int countByCondition(ItemCondition condition) {
//            int count = 0;
//            for (ItemModel i: itemsList) {
//                if(i.state == condition) {
//                    count++;
//                }
//            }
//            return count;
//        }
//        //g
//        public void summary() {
//            for (ItemModel i : itemsList) {
//                i.printInf();
//            }
//        }
//        //h
//        public void sortByName() {
//            itemsList.sort(Comparator.comparing(ItemModel::getName));
//        }
//        //i
//        public void sortByAmount() {
//            CompareQuantity compareQuantity = new CompareQuantity();
//            itemsList.sort((ItemModel s1, ItemModel s2)->compareQuantity.compare(s1,s2));
//            Collections.reverse(itemsList);
//        }
//        //j
//        public ItemModel max() {
//            CompareQuantity compareQuantity = new CompareQuantity();
//            return  Collections.max(itemsList,compareQuantity);
//        }
//
//
//        //constr
//    public FulfillmentCenterModel(String name,double maximumCapacity){
//            this.name = name;
//            this.maximumCapacity = maximumCapacity;
//            itemsList = new ArrayList<ItemModel>();
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public double getMaximumCapacity() {
//            return maximumCapacity;
//        }
//
//        public List<ItemModel> getItemsList() {
//            return itemsList;
//        }
//    }

//    int totalQuantity() {
//        int total = 0;
//        for (ItemModel item : itemsList) {
//            total += item.quantity;
//        }
//        return total;
//    }


//
//    int isItOnTheList(ItemModel item) {
//        int value = -1;
//        for (ItemModel i : itemsList) {
//            value = item.compareTo(i);
//            if (value == 0) break;
//        }
//        return value;
//    }

