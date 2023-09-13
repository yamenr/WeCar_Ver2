package com.example.wecar.D_FireBase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class FirebaseServices {

    private  static FirebaseServices instance;
    private FirebaseAuth  auth;
    private FirebaseFirestore fire;
    private FirebaseStorage storage;
    private Uri selectedImageURL;
    private User currentUser;

    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }

    public void setSelectedImageURL(Uri selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

    public  FirebaseServices ()
    {
        auth=FirebaseAuth.getInstance();
        fire=FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
        currentUser = null;
        selectedImageURL = null;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseFirestore getFire() {
        return fire;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }


    public  static FirebaseServices getInstance(){
        if (instance==null){
            instance=new FirebaseServices();

        }
        return instance;
    }

    public boolean updateUser(User user)
    {
        final boolean[] flag = {false};
        // Reference to the collection
        String collectionName = "users";
        String firstnameFieldName = "firstName";
        String lastnameFieldName = "lastName";
        String usernameFieldName = "username";
        String phoneFieldName = "phone";
        String addressFieldName = "address";
        String photoFieldName = "photo";
        String favouritsFieldName = "favourits";

        /*
        String barberNameValue = ap.getBarber();
        String customerFieldName = "customer";
        String customerNameValue = ap.getCustomer();
        String dateTimeFieldName = "dateTime";
        String dateTimeValue = ap.getDateTime(); */

        // Create a query for documents based on a specific field
        Query query = fire.collection(collectionName).
                whereEqualTo(usernameFieldName, user.getUsername());

        // Execute the query
        query.get()
                .addOnSuccessListener((QuerySnapshot querySnapshot) -> {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        // Get a reference to the document
                        DocumentReference documentRef = document.getReference();
                        // Update specific fields of the document
                        documentRef.update(
                                        firstnameFieldName, user.getFirstName(),
                                        lastnameFieldName, user.getLastName(),
                                        usernameFieldName, user.getUsername(),
                                        phoneFieldName, user.getPhone(),
                                        addressFieldName, user.getAddress(),
                                        photoFieldName, user.getPhoto(),
                                        favouritsFieldName, user.getFavourits()
                                        )
                                .addOnSuccessListener(aVoid -> {

                                    flag[0] = true;
                                })
                                .addOnFailureListener(e -> {
                                    System.err.println("Error updating document: " + e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error getting documents: " + e);
                });

        return flag[0];
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
