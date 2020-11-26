package com.eflexsoft.easyclosest.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.model.ClosetItem;
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

public class UpdateClosetItemRepository {

    Context context;
   public MutableLiveData<Boolean> isUpdateSuccessful = new MutableLiveData<>();

    public UpdateClosetItemRepository(Context context) {
        this.context = context;
    }

    public void updateWithStringImage(long id, String category, String season, String note) {

//        ClosetItem closetItem = new ClosetItem(id, imageUrl, category, season, note, isFavourite);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference reference = firestore.collection("Closets").document(FirebaseAuth.getInstance().getUid())
                .collection(category).document(String.valueOf(id));

        Map<String, Object> map = new HashMap<>();
        map.put("season", season);
        map.put("note", note);

        reference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                isUpdateSuccessful.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isUpdateSuccessful.setValue(false);
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getMimeType(Uri uri) {

        ContentResolver contentResolver = context.getContentResolver();

        return MimeTypeMap.getFileExtensionFromUrl(contentResolver.getType(uri));

    }

    public void uploadImageByte(long id, byte[] bytes, String category, String season, String note) {
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

                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                    DocumentReference reference = firestore.collection("Closets").document(FirebaseAuth.getInstance().getUid())
                            .collection(category).document(String.valueOf(id));

                    Map<String, Object> map = new HashMap<>();
                    map.put("season", season);
                    map.put("note", note);
                    map.put("imageUrl", task.getResult().toString());

                    reference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            isUpdateSuccessful.setValue(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            isUpdateSuccessful.setValue(false);
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                isUpdateSuccessful.setValue(false);
            }

        });

    }

    public void uploadImageUri(Uri uri, String category, String season, String note, long id) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("closetImages");

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

                    DocumentReference reference = firestore.collection("Closets").document(FirebaseAuth.getInstance().getUid())
                            .collection(category).document(String.valueOf(id));

                    Map<String, Object> map = new HashMap<>();
                    map.put("season", season);
                    map.put("note", note);
                    map.put("imageUrl", task.getResult().toString());

                    reference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            isUpdateSuccessful.setValue(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            isUpdateSuccessful.setValue(false);
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                isUpdateSuccessful.setValue(false);
            }

        });

    }

}
