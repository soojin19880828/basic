package com.whminwei.controller;

import javafx.scene.control.Label;

public class TransitPageController {
    //提示文字
    public Label transitMessage;

    public void changeMessage(String message){
        transitMessage.setText(message);
    }
}
