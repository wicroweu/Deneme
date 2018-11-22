package foreigner.ibrahim.com.foreigner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirstScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    AutoCompleteTextView email, password;
    Button signupButton, loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signupButton=findViewById(R.id.signupButton);
        loginButton=findViewById(R.id.loginButton);

    }

    public  void signUp(View view){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(FirstScreen.this,"User Created",Toast.LENGTH_LONG).show();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString());
                    Intent intent=new Intent(getApplicationContext(),ProfileUpdateScreen.class);
                    startActivity(intent);

                }
            }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FirstScreen.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
        }

    public  void  signIn(View view){
        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Intent intent=new Intent(getApplicationContext(),AppScreenActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FirstScreen.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
