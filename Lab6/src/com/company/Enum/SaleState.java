package com.company.Enum;

public enum SaleState {
    NEW,
    USED,
    REFURBISHED;


    public String toString(){
        switch (this){
            case NEW: return"new";
            case USED: return "used";
            case REFURBISHED: return "refurbished";
            default: return "";
        }
    }
}
