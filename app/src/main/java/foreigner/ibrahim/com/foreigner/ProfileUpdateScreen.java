package foreigner.ibrahim.com.foreigner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class ProfileUpdateScreen extends AppCompatActivity {

    ImageView yazi, tahta;
    private StorageReference mStorageRef;
    Uri selectedImage;
    private FirebaseAuth mAuth;
    AutoCompleteTextView username;
    AutoCompleteTextView story;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update_screen);
        yazi = findViewById(R.id.yazi);
        tahta = findViewById(R.id.tahta);
        username=findViewById(R.id.username);
        story=findViewById(R.id.story);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();




    }

    public void upload(View view){
        UUID uuid=UUID.randomUUID();
        final String imageName="images/"+uuid+".jpg";

            StorageReference storageReference=mStorageRef.child(imageName);
            storageReference.putFile(selectedImage).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //download url
                        StorageReference newReference=FirebaseStorage.getInstance().getReference(imageName);
                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final  String downloadURL=uri.toString();
                                FirebaseUser user=mAuth.getCurrentUser();
                                final  String userMail=user.getEmail();
                                final  String userStory=story.getText().toString();
                                final  String userName=username.getText().toString();
                                Query userNameQuery=FirebaseDatabase.getInstance().getReference().child("info").orderByChild("username").equalTo(userName);
                                userNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getChildrenCount()>0) {
                                            Toast.makeText(ProfileUpdateScreen.this, "baska sec ", Toast.LENGTH_LONG).show();
                                        }else {
                                            UUID uuid1 = UUID.randomUUID();
                                            String uuidString = uuid1.toString();
                                            myRef.child("info").child(uuidString).child("usermail").setValue(userMail);
                                            myRef.child("info").child(uuidString).child("story").setValue(userStory);
                                            myRef.child("info").child(uuidString).child("url").setValue(downloadURL);
                                            myRef.child("info").child(uuidString).child("username").setValue(userName);
                                            Toast.makeText(ProfileUpdateScreen.this, "okay", Toast.LENGTH_LONG).show();

                                            Intent intent=new Intent(getApplicationContext(),AppScreenActivity.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileUpdateScreen.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }
            });

    }

    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                tahta.setImageBitmap(bitmap);
                yazi.setVisibility(View.INVISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}



