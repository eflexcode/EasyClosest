package com.eflexsoft.easyclosest.repository;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.model.ClosetItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {

    Context context;
    public MutableLiveData<List<ClosetItem>> liveData = new MutableLiveData<>();
    List<ClosetItem> closetItems = new ArrayList<>();

    public FavoritesRepository(Context context) {
        this.context = context;
    }

    public void getAllFavorite() {

        Query shirts = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Shirts").whereEqualTo("favourite", true);

        Query Trousers = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Trousers").whereEqualTo("favourite", true);

        Query Hats = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Hats").whereEqualTo("favourite", true);

        Query Caps = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Caps").whereEqualTo("favourite", true);

        Query Head_warmers = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Head warmers").whereEqualTo("favourite", true);

        Query Belts = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Belts").whereEqualTo("favourite", true);

        Query Coats = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Coats").whereEqualTo("favourite", true);

        Query Sweaters = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Sweaters").whereEqualTo("favourite", true);


        Query Glasses = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Glasses").whereEqualTo("favourite", true);


        Query Jewries = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Jewries").whereEqualTo("favourite", true);


        Query Accessories = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Accessories").whereEqualTo("favourite", true);

        Query Shoes = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Shoes").whereEqualTo("favourite", true);

        Query Underwear = FirebaseFirestore.getInstance().collection("Closets")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Underwear").whereEqualTo("favourite", true);


        CollectionReference query1 = FirebaseFirestore.getInstance().collection("Closets");
//        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//            }
//        });

        shirts.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Trousers.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Hats.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Caps.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Head_warmers.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });
        Belts.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Coats.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Sweaters.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Glasses.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Jewries.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Accessories.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Shoes.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });

        Underwear.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ClosetItem closetItem = documentSnapshot.toObject(ClosetItem.class);
                    closetItems.add(closetItem);
                }
                liveData.setValue(closetItems);
            }
        });


    }

}
