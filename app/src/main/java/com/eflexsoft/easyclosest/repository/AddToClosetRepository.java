package com.eflexsoft.easyclosest.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.model.ClosetItem;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddToClosetRepository {

    Context context;
    public MutableLiveData<Boolean> isUploadInSuccessful = new MutableLiveData<>();


    public AddToClosetRepository(Context context) {
        this.context = context;
    }

    public void uploadImageByte(byte[] bytes, String category, String season, String note) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("closetImages");


        StorageReference camera = storageReference.child("imageCameraUpload" + System.currentTimeMillis());
        UploadTask uploadTask = camera.putBytes(bytes);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return camera.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    String downloadUri = task.getResult().toString();

                    long time = System.currentTimeMillis();

                    String id = String.valueOf(time);

                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    DocumentReference reference = firestore.collection(category).document(id);

                    ClosetItem closetItem = new ClosetItem(time, downloadUri, category, season, note);

                    reference.set(closetItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            isUploadInSuccessful.setValue(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            isUploadInSuccessful.setValue(false);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                isUploadInSuccessful.setValue(false);
            }

        });

    }

    public String getMineType(Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadImageUri(Uri uri, String category, String season, String note) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("closetImages");

        StorageReference gallery = storageReference.child("imageFileUpload" + System.currentTimeMillis() + getMineType(uri));
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

                    String downloadUri = task.getResult().toString();

                    long time = System.currentTimeMillis();

                    String id = String.valueOf(time);

                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    DocumentReference reference = firestore.collection(category).document(id);

                    ClosetItem closetItem = new ClosetItem(time, downloadUri, category, season, note);

                    reference.set(closetItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            isUploadInSuccessful.setValue(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            isUploadInSuccessful.setValue(false);
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                isUploadInSuccessful.setValue(false);
            }

        });

    }

}
