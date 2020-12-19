package com.eflexsoft.easyclosest.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileRepository {

    Context context;

    public ProfileRepository(Context context) {
        this.context = context;
    }

    public void updateProfile(String filed,String value){

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference reference = firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid());

        Map<String ,Object> map = new HashMap<>();
        map.put(filed,value);

        reference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public String getMimeType(Uri uri) {

        ContentResolver contentResolver = context.getContentResolver();

        return MimeTypeMap.getFileExtensionFromUrl(contentResolver.getType(uri));

    }
    public void uploadImageUri(Uri uri,String oldImage) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("profileImages");

        StorageReference gallery = storageReference.child("imageFileUpload" + System.currentTimeMillis() + getMimeType(uri));
        UploadTask uploadTask = gallery.putFile(uri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return gallery.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                    DocumentReference reference = firestore.collection("Users").document(FirebaseAuth.getInstance().getUid());
                    Map<String, Object> map = new HashMap<>();
                    map.put("profilePictureUlr", task.getResult().toString());

                    reference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            try {
                                StorageReference storageReference1 = FirebaseStorage.getInstance().getReferenceFromUrl(oldImage);
                                storageReference1.delete();
                            }catch (Exception e){

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

}
