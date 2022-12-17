package com.example.bff.controller;

import com.example.bff.Business;
import com.example.bff.model.seeMoreModel;
import com.example.bff.view.seeMoreView;

public class seeMoreController {
    seeMoreModel model;
    seeMoreView view;

    public seeMoreController(seeMoreView view) {
        this.view = view;
        model = new seeMoreModel(this);
    }
    public void data(String email){
        model.setDataModel(email);
    }
    public void setDataController(Business business){
        view.setDataView(business);
    }
}