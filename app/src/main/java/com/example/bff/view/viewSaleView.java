package com.example.bff.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bff.R;
import com.example.bff.controller.ViewClientController;
import com.example.bff.controller.viewSaleController;
import com.example.bff.entities.Client;
import com.example.bff.entities.sale;

import java.util.ArrayList;

public class viewSaleView extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<sale> lst;
    saleAdapter myadapt;
    viewSaleController controller;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(viewSaleView.this , businessActivityView.class));
        finish();
    }

    public viewSaleView(){

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        recyclerView = findViewById(R.id.Recycleview);
        controller=new viewSaleController(this);
        lst=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myadapt = new saleAdapter(lst,this);
        recyclerView.setAdapter(myadapt);
        controller.SendControllerAdapter(lst);
    }


    public void setScreenView(){
        Intent intent = new Intent(viewSaleView.this, viewSaleView.class);
        startActivity(intent);
        finish();
    }

    public void setListView(ArrayList<sale> lst) {
        this.lst = lst;
        myadapt.notifyDataSetChanged();
    }
}