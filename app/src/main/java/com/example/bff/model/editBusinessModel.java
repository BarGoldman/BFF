package com.example.bff.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bff.entities.Business;
import com.example.bff.entities.User;
import com.example.bff.controller.EditUserProfileController;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.UUID;

public class editBusinessModel {

    private EditUserProfileController controller;
    private FirebaseUser mAuth;
    DatabaseReference reference; //for the database that save already
    private FirebaseAuth fAuth;
    private ImageView profilePic;
    private Context context;


    private FirebaseStorage storage;
    private StorageReference storageReference;

    public editBusinessModel(User user, Context context) {
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fAuth =  FirebaseAuth.getInstance();
        this.context = context;
    }

    public void EditProfileimage_controller(ImageView profilePic) {
        //        this.profilePic = profilePic;
        StorageReference profileRef = storageReference.child("user/"+ Objects.requireNonNull(fAuth.getCurrentUser()).getUid() +"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic);
            }
        });
    }

    public void uploadPicture_model(Uri imageUri) {
        //uplaod image to firebase storage
        ImageView profilePic = this.profilePic;
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle("Uploading Image...");
        pd.show();


        final  String randomKey = UUID.randomUUID().toString();


        StorageReference riversRef = storageReference.child("user/"+ Objects.requireNonNull(fAuth.getCurrentUser()).getUid() +"profile.jpg");
        // Register observers to listen for when the download is done or if it fails
        riversRef.putFile(imageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(context, "Failed To Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilePic);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                pd.setMessage("Percentage: " + (int)progressPercent + "%");
            }
        });

    }

    public boolean update(String newName, String newBusinessName, String newId, String newPhone, String newCity, String newStreet, String newHouseNumber, String newType, String newTime) {
        FirebaseDatabase.getInstance().getReference().child("Business").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Business user = dataSnapshot.getValue(Business.class);
                String name = user.getName();
                String businessName = user.getUsername();
                String ID = user.getId();
                String phone = user.getPhone();
                String city = user.getCity();
                String street = user.getStreet();
                String houseNumber = user.getHouseNumber();
                String type = user.getType();
                String time = user.getTime();
                if (!name.equals(newName)) {
                    reference.child(mAuth.getUid()).child("name").setValue(newName);
                }
                if (!businessName.equals(newBusinessName)) {
                    reference.child(mAuth.getUid()).child("username").setValue(newBusinessName);
                }
                if (!ID.equals(newId)) {
                    reference.child(mAuth.getUid()).child("id").setValue(newId);
                }
                if (!phone.equals(newPhone)) {
                    reference.child(mAuth.getUid()).child("phone").setValue(newPhone);
                }
                if (!city.equals(newCity)) {
                    reference.child(mAuth.getUid()).child("city").setValue(newCity);
                }
                if (!street.equals(newStreet)) {
                    reference.child(mAuth.getUid()).child("street").setValue(newStreet);
                }
                if (!houseNumber.equals(newHouseNumber)) {
                    reference.child(mAuth.getUid()).child("houseNumber").setValue(newHouseNumber);
                }
                if (!type.equals(newType)) {
                    reference.child(mAuth.getUid()).child("type").setValue(newType);
                }
                if (!time.equals(newTime)) {
                    reference.child(mAuth.getUid()).child("time").setValue(newTime);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return false;
    }
}
