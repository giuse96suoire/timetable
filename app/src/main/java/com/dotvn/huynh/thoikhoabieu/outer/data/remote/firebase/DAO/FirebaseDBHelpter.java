package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.DAO;

import android.support.annotation.NonNull;

import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Manh Hoang Huynh on 24/09/2017.
 */

public class FirebaseDBHelpter<F> {
    private FirebaseDatabase mDatabase;

    public FirebaseDBHelpter() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void write(final F object, final String reference, final ThreeCallback<Void> callback) {
        mDatabase.getReference(reference)
                .setValue(object)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null)
                            callback.onComplete();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (callback != null)
                            callback.onSuccess(aVoid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }
    public void writeProperty(final String reference, final Object value, final ThreeCallback<Void> callback) {
        mDatabase.getReference(reference)
                .setValue(value)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null)
                            callback.onComplete();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (callback != null)
                            callback.onSuccess(aVoid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }
    public void writeWithRandomId(final F object, final String reference, final ThreeCallback<String> callback) {
        final String key = mDatabase.getReference(reference).push().getKey();
        write(object, reference + "/" + key, new ThreeCallback<Void>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Void result) {
                callback.onSuccess(key);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void writeIfNotExist(final F object, final String reference, final ThreeCallback<Void> callback) {
        readOne(reference, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    write(object, reference, callback);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void read(final String reference, final ValueEventListener callback) {
        mDatabase.getReference(reference).addValueEventListener(callback);
    }

    public void readOne(final String reference, final ValueEventListener callback) {
        mDatabase.getReference(reference).addListenerForSingleValueEvent(callback);
    }
}
