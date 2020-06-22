package com.company.Models;
import com.company.Annotations.Column;
import com.company.Enum.SaleState;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.Comparator;


public class ItemModel implements Comparable<ItemModel>, Serializable {
    @Column(columnName = "name") public String name;
    public transient SaleState state;
    @Column(columnName = "weight") public double weight;
    @Column(columnName = "quantity") public int quantity;
    @Column(columnName = "price") public double price;
    @Column(columnName = "parent") public FulfillmentCenterModel parent;

    public void summary(){
        System.out.println("name: " + this.name);
        System.out.println("condition: " + this.state);
        System.out.println("weight: " + this.weight);
        System.out.println("quantity: " + this.quantity);
        System.out.println("--------------------------");
    }

    //constr
    public ItemModel( String name, SaleState state, double weight, double price) {
        this.name = name;
        this.state = state;
        this.weight = weight;
        this.price = price;
        this.quantity = 1;

    }

    @Override
    public int compareTo(ItemModel item) {
        //does
        if (this.name.equals(item.name)) {
            return 0;
        }
        //does not
        else {
            return -1;
        }
    }


    public String getName() {
        return name;
    }

    public SaleState getState() {
        return state;
    }

    public double getWeight() {
        return weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public FulfillmentCenterModel getParent() {
        return parent;
    }


    @Override
    public String toString() {
        return name;
    }



}
