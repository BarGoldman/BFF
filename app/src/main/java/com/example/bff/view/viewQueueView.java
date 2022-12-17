package com.example.bff.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.bff.R;
import com.example.bff.controller.viewQueueController;
import com.example.bff.entities.Client;
import com.example.bff.entities.User;
import com.example.bff.entities.queue;
import com.example.bff.view.animalActivityView;
import com.example.bff.view.queueAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class viewQueueView extends AppCompatActivity{

    RecyclerView recyclerView;
    ArrayList<queue> lst;
    queueAdapter myadapt;
    HashMap<String,String> names;
    viewQueueController controller;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(viewQueueView.this, animalActivityView.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_list);
        controller = new viewQueueController(this);
        Intent intent = getIntent();
        names = (HashMap<String, String>) intent.getSerializableExtra("key");
        recyclerView = findViewById(R.id.Recycleview);
        lst = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myadapt = new queueAdapter(lst,this);
        recyclerView.setAdapter(myadapt);
        controller.getListController(names,lst);

    }

    public void setListView(ArrayList<queue> lst){
        this.lst = lst;
    }

    public void notifyView(){
        myadapt.notifyDataSetChanged();
    }
}

