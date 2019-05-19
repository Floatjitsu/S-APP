package com.main.s_app.com.main.s_app.firebase;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.s_app.ForumAdapter;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Date;

public class FirebaseForum {

    private static final String FORUM_REFERENCE = "forum";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    public FirebaseForum() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    /**
     * Add a new text post to the Firebase DB
     * @param title title of post
     * @param content text content of post
     */
    public void addTextPostToFirebase(String title, String content) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            User postUser = new User(user.getDisplayName(), user.getUid());
            TextPost textPost = new TextPost(generateUniqueId(), title, new Date().getTime(), TextPost.POST_KIND, postUser, content);
            DatabaseReference forumReference = mDatabase.getReference(FORUM_REFERENCE);
            forumReference.push().setValue(textPost);
        }
    }

    /**
     * Add a new image post to the Firebase DB
     * @param title title of post
     * @param desc image description
     * @param imageUri image uri
     */
    public void addImagePostToFirebase(String title, String desc, String imageUri) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            User postUser = new User(user.getDisplayName(), user.getUid());
            ImagePost imagePost =
                    new ImagePost(
                            generateUniqueId(), title, new Date().getTime(),
                            ImagePost.POST_KIND, postUser, imageUri, desc
                    );
            DatabaseReference forumReference = mDatabase.getReference(FORUM_REFERENCE);
            forumReference.push().setValue(imagePost);
        }
    }

    /**
     * Fill given RecyclerView with data from the Firebase Realtime DB
     * @param forum the recycler view
     */
    public void getForumToRecyclerView(final RecyclerView forum, final Context activityContext) {
        DatabaseReference forumReference = mDatabase.getReference(FORUM_REFERENCE);
        forumReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Post> posts = new ArrayList<>();
                //Collect Data
                for (DataSnapshot data : dataSnapshot.getChildren() ) {
                    Post post = data.getValue(Post.class);
                    if(post != null) {
                        switch (post.getPostKind()) {
                            case (TextPost.POST_KIND):
                                TextPost textPost = data.getValue(TextPost.class);
                                if(textPost != null)
                                    posts.add(textPost);
                                break;
                            case (ImagePost.POST_KIND):
                                ImagePost imagePost = data.getValue(ImagePost.class);
                                if(imagePost != null)
                                    posts.add(imagePost);
                                break;
                        }
                        //TODO: Add other cases for image and link post to switch
                    }
                }
                RecyclerView.Adapter forumAdapter = new ForumAdapter(posts, activityContext);
                forum.setAdapter(forumAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Generates a random UUID and returns it as a String
     * Used for the Post-IDs in the Forum
     * @return new unique id
     */
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}