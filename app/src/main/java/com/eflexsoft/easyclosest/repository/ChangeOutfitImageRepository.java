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
import com.eflexsoft.easyclosest.model.UpdateImage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ChangeOutfitImageRepository {

    Context context;
    public MutableLiveData<UpdateImage> updateImageMutableLiveData = new MutableLiveData<>();

    public ChangeOutfitImageRepository(Context context) {
        this.context = context;
    }

    public String getMimeTyp(Uri uri) {

        ContentResolver contentResolver = context.getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void changeImage(String oldUrl, String name, long id, int position, Uri uri) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("closetImages");

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

                    String downloadUri = task.getResult().toString();

                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                    DocumentReference reference = firestore.collection("Outfit").document(FirebaseAuth.getInstance().getUid())
                            .collection("items").document(String.valueOf(id));

                    Map<String, Object> map = new HashMap<>();
                    map.put(name, downloadUri);

                    reference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Go back and refresh layout", Toast.LENGTH_SHORT).show();

                            UpdateImage updateImage = new UpdateImage(name, downloadUri, position, id);

                            updateImageMutableLiveData.setValue(updateImage);
                            StorageReference deleteReference = firebaseStorage.getReferenceFromUrl(oldUrl);
                            deleteReference.delete();
//                            Toast.makeText(context, "fsdv  ssssssssssssssssssssssssssssss", Toast.LENGTH_SHORT).show();
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
//                isUploadInSuccessful.setValue(false);
            }

        });
    }

}
