package com.example.bff.model;

import androidx.annotation.NonNull;

import com.example.bff.controller.historyController;
import com.example.bff.entities.sale;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class historyModel {
    historyController controller;
    DatabaseReference mroot;
    String id;

    public historyModel(historyController controller, String id) {
        this.id = id;
        this.controller = controller;
        mroot = FirebaseDatabase.getInstance().getReference("Sales");

    }

    public void sendModelAdapter(ArrayList<sale> lst) {
        mroot.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    sale s = dataSnapshot.getValue(sale.class);
                    if (!s.getStatus().equals("ok"))
                    {
                        lst.add(s);
                    }
                }
                controller.setListController(lst);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void returnSaleModel(sale s) {
        s.setStatus("ok");
        FirebaseDatabase.getInstance().getReference("Sales").child(id).child(s.getKey()).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    controller.setScreenController();
                }
                else {}

            }
        });
    }
}
