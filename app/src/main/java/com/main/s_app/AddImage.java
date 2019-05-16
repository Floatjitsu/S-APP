package com.main.s_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.annotations.NotNull;

import java.io.IOException;

public class AddImage extends Fragment implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;

    FragmentActivity mParentActivity; //Parent Activity
    ImageView mImageThumbnail;
    Button mButtonCamera, mButtonGallery;
    TextView mImageTitle, mImageDesc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_add_image, container, false);

        mImageTitle = myView.findViewById(R.id.editText_title_image);
        mImageDesc = myView.findViewById(R.id.editText_title_text);

        mButtonCamera = myView.findViewById(R.id.button_camera);
        mButtonCamera.setOnClickListener(this);

        mButtonGallery = myView.findViewById(R.id.button_gallery);
        mButtonGallery.setOnClickListener(this);

        mParentActivity = getActivity();
        mImageThumbnail = myView.findViewById(R.id.image_thumbnail);

        //Enable onOptionsItemSelected Event
        setHasOptionsMenu(true);

        return myView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.post_article) {
            //Check if the title or the image is empty
            if(mImageTitle.getText().toString().equals("")) {
                mImageTitle.setError("Please enter a title");
            } else if(mImageThumbnail.getDrawable() == null) {
                Toast.makeText(mParentActivity, "Please select an Image!", Toast.LENGTH_SHORT).show();
            }/* else {
                //TODO: Upload Image to Firebase Storage and create forum article
            } */
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    Resource for adding Images from Camera:
        https://developer.android.com/training/camera/photobasics
    Resource for adding Images from Gallery:
        https://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_camera) {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(mParentActivity != null) {
                //Determine the first Activity which can handle the open camera
                if(takePicture.resolveActivity(mParentActivity.getPackageManager()) != null) {
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                }
            }
        } else if(v.getId() == R.id.button_gallery) {
            Intent openGallery = new Intent(Intent.ACTION_GET_CONTENT);
            openGallery.setType("image/*");
            startActivityForResult(openGallery, REQUEST_IMAGE_GALLERY);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Image captured by Camera
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == FragmentActivity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if(extras != null) {
                setImageThumbnail((Bitmap) extras.get("data"));
            }
        //Image selected from Gallery
        } else if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == FragmentActivity.RESULT_OK) {
            Uri imageUri = data.getData();
            //Try-Catch block necessary
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(mParentActivity.getContentResolver(), imageUri);
                setImageThumbnail(imageBitmap);
            } catch (IOException ex) {
                Toast.makeText(mParentActivity, "Image couldn't get loaded!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void setImageThumbnail(Bitmap imageBitmap) {
        //Make 2 upload Buttons Invisible
        mButtonGallery.setVisibility(View.GONE);
        mButtonCamera.setVisibility(View.GONE);

        //Set mImageThumbnail to visible
        mImageThumbnail.setVisibility(View.VISIBLE);

        //Set imageBitmap as source for mImageThumbnail
        mImageThumbnail.setImageBitmap(imageBitmap);
    }
}
