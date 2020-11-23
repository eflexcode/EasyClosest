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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
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

public class AddToClosetRepository {

    //TODO make all repositories a singleton

    Context context;
    public MutableLiveData<Boolean> isUploadInSuccessful = new MutableLiveData<>();

    boolean isUpdated = true;

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
                    DocumentReference reference = firestore.collection("Closets").document(FirebaseAuth.getInstance().getUid())
                            .collection(category).document(id);


                    ClosetItem closetItem = new ClosetItem(time, downloadUri, category, season, note,false);

                    reference.set(closetItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            isUploadInSuccessful.setValue(true);

                            DocumentReference reference = firestore.collection("ClosetsItemCount").document(FirebaseAuth.getInstance().getUid());

                            reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                                    if (value.exists()) {


                                        if (value.contains(category.replaceAll("\\s",""))) {
                                            if (isUpdated) {

                                                firestore.runTransaction(new Transaction.Function<Void>() {
                                                    @Nullable
                                                    @Override
                                                    public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
//                                                Toast.makeText(context, "vbbbbbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();
                                                        DocumentReference reference = firestore.collection("ClosetsItemCount").document(FirebaseAuth.getInstance().getUid());

                                                        DocumentSnapshot documentSnapshot = transaction.get(reference);

                                                        long count = documentSnapshot.getLong(category.replaceAll("\\s","")) + 1;

                                                        transaction.update(reference, category.replaceAll("\\s",""), count);

                                                        isUpdated = false;

                                                        return null;
                                                    }

                                                });
                                            }

                                        } else {
                                            Map<String, Object> map = new HashMap<>();
                                            map.put(category.replaceAll("\\s",""), 1);
                                            reference.update(map);
                                            isUpdated = false;
                                        }
                                    } else {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put(category.replaceAll("\\s",""), 1);
                                        reference.set(map);
                                        isUpdated = false;
                                    }

                                }
                            });

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

    private void doCategoryCount(String category) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

               return null;
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

                    DocumentReference reference = firestore.collection("Closets").document(FirebaseAuth.getInstance().getUid())
                            .collection(category).document(id);

                    ClosetItem closetItem = new ClosetItem(time, downloadUri, category, season, note,false);

                    reference.set(closetItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            isUploadInSuccessful.setValue(true);

                            DocumentReference reference = firestore.collection("ClosetsItemCount").document(FirebaseAuth.getInstance().getUid());

                            reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                                    if (value.exists()) {


                                        if (value.contains(category.replaceAll("\\s",""))) {
                                            if (isUpdated) {

                                                firestore.runTransaction(new Transaction.Function<Void>() {
                                                    @Nullable
                                                    @Override
                                                    public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
//                                                Toast.makeText(context, "vbbbbbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();
                                                        DocumentReference reference = firestore.collection("ClosetsItemCount").document(FirebaseAuth.getInstance().getUid());

                                                        DocumentSnapshot documentSnapshot = transaction.get(reference);

                                                        long count = documentSnapshot.getLong(category.replaceAll("\\s","")) + 1;

                                                        transaction.update(reference, category.replaceAll("\\s",""), count);

                                                        isUpdated = false;

                                                        return null;
                                                    }

                                                });
                                            }

                                        } else {
                                            Map<String, Object> map = new HashMap<>();
                                            map.put(category.replaceAll("\\s",""), 1);
                                            reference.update(map);
                                            isUpdated = false;
                                        }
                                    } else {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put(category.replaceAll("\\s",""), 1);
                                        reference.set(map);
                                        isUpdated = false;
                                    }

                                }
                            });


//                            firestore.runTransaction(new Transaction.Function<Void>() {
//                                @Nullable
//                                @Override
//                                public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
//                                    Toast.makeText(context, "vbbbbbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();
//                                    DocumentReference reference = firestore.collection("Closets").document(FirebaseAuth.getInstance().getUid());
//
//                                    DocumentSnapshot documentSnapshot = transaction.get(reference);
//
//                                    if (documentSnapshot.contains(category)) {
//                                        long count = documentSnapshot.getLong(category) + 1;
//
//                                        transaction.update(reference, category, count);
//                                        Toast.makeText(context, "vbbbbbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(context, "ggggggggggggggggggggggg", Toast.LENGTH_SHORT).show();
//                                        long count = 0;
//
//                                        Map<String, Object> map = new HashMap<>();
//                                        map.put("count", count);
//
//                                        reference.update(map);
//                                    }
//
//
//                                    return null;
//                                }
//                            });
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
