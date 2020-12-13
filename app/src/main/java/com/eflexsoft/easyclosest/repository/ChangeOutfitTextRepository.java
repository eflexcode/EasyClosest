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

public class ChangeOutfitTextRepository {

    Context context;
    public MutableLiveData<Boolean> isUploadInSuccessful = new MutableLiveData<>();

    public ChangeOutfitTextRepository(Context context) {
        this.context = context;
    }

    public void UpdateTet(String id, String note, String date, String season) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference reference = firestore.collection("Outfit").document(FirebaseAuth.getInstance().getUid())
                .collection("items").document(id);

        Map<String, Object> map = new HashMap<>();
        map.put("season", season);
        map.put("note", note);
        map.put("date", date);

        reference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Go back and refresh layout", Toast.LENGTH_SHORT).show();
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
