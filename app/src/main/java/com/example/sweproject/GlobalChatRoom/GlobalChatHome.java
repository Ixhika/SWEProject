package com.example.sweproject.GlobalChatRoom;

import static com.example.sweproject.GlobalChatRoom.FirebaseCords.MAIN_CHAT_DATABASE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sweproject.MainActivity;
import com.example.sweproject.R;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class GlobalChatHome extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        chatAdapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.logout) {
//            mAuth.signOut();
//            startActivity(new Intent(this, MainActivity.class));
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    Toolbar toolbar;
    EditText chat_box;
    RecyclerView chat_list;

    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.global_chat_home);
        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        chat_box = findViewById(R.id.chat_box);
        chat_list = findViewById(R.id.chat_list);
        initChatList();
    }

    private void initChatList() {
        chat_list.setHasFixedSize(true);
        chat_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));


        Query query = MAIN_CHAT_DATABASE.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ChatModel> option  = new FirestoreRecyclerOptions.Builder<ChatModel>()
                .setQuery(query,ChatModel.class)
                .build();
        chatAdapter = new ChatAdapter(option);
        chat_list.setAdapter(chatAdapter);
        chatAdapter.startListening();

    }

    public void addMessage(View view) {
        String message = chat_box.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        if(!TextUtils.isEmpty(message)){

            /*Generate messageID using the current date. */
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String messageID = format.format(today);

            /*Getting user image from Google account*/
            String user_image_url = "";
            Uri photoUrl = user.getPhotoUrl();
            String originalUrl = "s96-c/photo.jpg";
            String resizeImageUrl = "s400-c/photo.jpg";
            if(photoUrl!=null){
                String photoPath = photoUrl.toString();
                user_image_url = photoPath.replace(originalUrl,resizeImageUrl);
            }

            HashMap<String,Object> messageObj = new HashMap<>();
            messageObj.put("message",message);
            messageObj.put("user_name",user.getDisplayName());
            messageObj.put("timestamp", FieldValue.serverTimestamp());
            messageObj.put("messageID",messageID);
            messageObj.put("user_image_url",user_image_url);


            MAIN_CHAT_DATABASE.document(messageID).set(messageObj).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(GlobalChatHome.this, "Message Send", Toast.LENGTH_SHORT).show();
                    chat_box.setText("");
                }else {
                    Toast.makeText(GlobalChatHome.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void OpenExplorer(View view) {
        Toast.makeText(this, "Nahi ho pyega abhi baad ma imgae dekhte ha", Toast.LENGTH_SHORT).show();
    }
}