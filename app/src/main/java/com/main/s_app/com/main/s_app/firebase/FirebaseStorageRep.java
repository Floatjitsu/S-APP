package com.main.s_app.com.main.s_app.firebase;

import android.graphics.Bitmap;
import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class FirebaseStorageRep {

    private static final String IMAGE_REF = "images";
    private StorageReference storageRef;

    public FirebaseStorageRep() {
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    /**
     * Uploads a given Bitmap to the Firebase Storage
     * @param bitmap the image as bitmap
     * @param imagePath the path where the image should get stored
     */
    public void uploadToStorage(Bitmap bitmap, Uri imagePath) {
        if(imagePath.getLastPathSegment() != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] data = outputStream.toByteArray();
            StorageReference imageRef = storageRef.child(IMAGE_REF).child(imagePath.getLastPathSegment());
            imageRef.putBytes(data);
        }
    }
}
