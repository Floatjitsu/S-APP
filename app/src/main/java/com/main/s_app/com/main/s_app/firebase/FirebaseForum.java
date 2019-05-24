package com.main.s_app.com.main.s_app.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.s_app.ForumAdapter;
import com.main.s_app.PostCommentsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
            TextPost textPost = new TextPost(
                    generateUniqueId(), title, new Date().getTime(),
                    TextPost.POST_KIND, postUser, content
            );
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
     * Add a new link post to the Firebase DB
     * @param title title of post
     * @param url link url
     */
    public void addLinkPostToFirebase(String title, String url) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            User postUser = new User(user.getDisplayName(), user.getUid());
            LinkPost linkPost =
                    new LinkPost(
                            generateUniqueId(), title, new Date().getTime(),
                            LinkPost.POST_KIND, postUser, url
                    );
            DatabaseReference forumReference = mDatabase.getReference(FORUM_REFERENCE);
            forumReference.push().setValue(linkPost);
        }
    }

    /**
     * Add a new comment to a specific post
     * @param comment new comment
     * @param postId id of the post
     */
    public void addCommentToPost(String comment, String postId) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            final User user = new User(currentUser.getDisplayName(), currentUser.getUid());
            final Comment comment1 = new Comment(comment, user, new Date().getTime());
            DatabaseReference forumReference = mDatabase.getReference(FORUM_REFERENCE);
            forumReference.orderByChild("postId").equalTo(postId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot post : dataSnapshot.getChildren()) {
                            post.getRef().child("comments").push().setValue(comment1);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        }
    }

    /**
     * Fill given RecyclerView with data from the Firebase Realtime DB
     * @param forum the recycler view
     * @param activityContext the context where the recycler view is located
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
                            case(LinkPost.POST_KIND):
                                LinkPost linkPost = data.getValue(LinkPost.class);
                                if(linkPost != null)
                                    posts.add(linkPost);
                                break;
                        }
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
     * Fill given RecyclerView with comments for a post from the Firebase Realtime DB
     * @param postId to identify the commented post
     * @param comments the recycler view
     */
    public void getPostCommentsToRecyclerView(String postId, final RecyclerView comments) {
        DatabaseReference forumReference = mDatabase.getReference(FORUM_REFERENCE);
        forumReference.orderByChild("postId").equalTo(postId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Comment> comments1 = new ArrayList<>();
                        for(DataSnapshot post : dataSnapshot.getChildren()) {
                            for(DataSnapshot comment : post.child("comments").getChildren()) {
                                comments1.add(comment.getValue(Comment.class));
                            }
                        }
                        RecyclerView.Adapter commentsAdapter = new PostCommentsAdapter(comments1);
                        comments.setAdapter(commentsAdapter);
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
