package co.edu.udea.compumovil.gr04_20172.lab4;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button Starting, Register;
    EditText email, password;
    //DbHelper dbHelper;
    //SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Starting = (Button)findViewById(R.id.buttonLogin);
        Register = (Button)findViewById(R.id.buttonLogUp);
        email = (EditText) findViewById(R.id.editTextEmailLogin);
        password = (EditText) findViewById(R.id.editTextPasswordLogin);
        Starting.setOnClickListener(this);
        Register.setOnClickListener(this);
        //dbHelper = new DbHelper(this);
        //db= dbHelper.getWritableDatabase();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.buttonLogin:
                Log.d("TAG","hola");
                final String id_Customer = email.getText().toString();
                String pass_Customer = password.getText().toString();
                if ("".equals(id_Customer)) {
                    Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(pass_Customer)) {
                    Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(id_Customer, pass_Customer)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                                Toast.makeText(getApplicationContext(),"Login exitoso",Toast.LENGTH_SHORT).show();
                                Intent intentNavigation = new Intent(LoginActivity.this, Navigation_Drawer.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentNavigation.putExtra("email", id_Customer);
                                Toast.makeText(getApplicationContext(), id_Customer,Toast.LENGTH_SHORT).show();
                                startActivity(intentNavigation);
                                finish();
                                if (!task.isSuccessful()) {
                                    Log.w("TAG", "signInWithEmail:failed", task.getException());
                                }

                            }
                        });


                break;

            case R.id.buttonLogUp:
                //Log.d("TAG","pase por aca");
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
        }
    }
}

