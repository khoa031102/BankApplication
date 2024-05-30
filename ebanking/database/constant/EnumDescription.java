package com.example.ebanking.database.constant;

public interface EnumDescription {
    default String getDescription(){
        return this.getClass().getSimpleName() + "." + this.name() + ".description";
    }

    String name();
}
