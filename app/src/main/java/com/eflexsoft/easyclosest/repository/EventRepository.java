package com.eflexsoft.easyclosest.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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
import java.util.List;
import java.util.Map;

public class EventRepository {

    Context context;
    public MutableLiveData<Boolean> isUploadInSuccessful = new MutableLiveData<>();

    public EventRepository(Context context) {
        this.context = context;
    }

    public String getMimeTyp(Uri uri) {

        ContentResolver contentResolver = context.getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void delete(String id) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference reference = firestore.collection("Events").document(FirebaseAuth.getInstance().getUid())
                .collection("items").document(id);

        reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addToOutfit(List<Uri> uriList, String note, String date) {

        long timeMillis = System.currentTimeMillis();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("closetImages");

        String id = String.valueOf(timeMillis);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference reference = firestore.collection("Events").document(FirebaseAuth.getInstance().getUid())
                .collection("items").document(id);

        Map<String, Object> map = new HashMap<>();
        map.put("id", timeMillis);
        map.put("note", note);
        map.put("date", date);

        final int[] imageUploadCount = {0};

        reference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                for (Uri uri : uriList) {

                    StorageReference gallery = storageReference.child("imageFileUpload" + System.currentTimeMillis() + getMimeTyp(uri));
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
                                imageUploadCount[0] += 1;
                                String downloadUrl = task.getResult().toString();
                                Map<String, Object> map = new HashMap<>();
                                map.put("imageUrl" + imageUploadCount[0], downloadUrl);

                                DocumentReference reference = firestore.collection("Events").document(FirebaseAuth.getInstance().getUid())
                                        .collection("items").document(id);

                                reference.update(map);

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
        });

    }

}
