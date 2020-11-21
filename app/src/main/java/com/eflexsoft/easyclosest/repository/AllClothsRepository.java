package com.eflexsoft.easyclosest.repository;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.model.CategoryCount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AllClothsRepository {

    Context context;
   public MutableLiveData<CategoryCount> categoryCountMutableLiveData = new MutableLiveData<>();

    public AllClothsRepository(Context context) {
        this.context = context;
    }

    public void getCategoryCount() {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference reference = firestore.collection("ClosetsItemCount").document(FirebaseAuth.getInstance().getUid());

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                CategoryCount categoryCount = value.toObject(CategoryCount.class);
                categoryCountMutableLiveData.setValue(categoryCount);

            }
        });

    }

}
