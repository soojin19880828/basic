package com.thfdcsoft.estate.printer.dto;

import javafx.beans.property.SimpleStringProperty;

public class Number {

    private final SimpleStringProperty  id;
    private final SimpleStringProperty  number;
    
    public Number(String fId, String fName) {
        this.id = new SimpleStringProperty(fId);
        this.number = new SimpleStringProperty(fName);
    }

    public String getId() {
        return id.get();
    }
    public void setId(String fId) {
        id.set(fId);
    }
    
    public String getNumber() {
        return number.get();
    }
    public void setNumber(String fName) {
        number.set(fName);
    }
	
}
