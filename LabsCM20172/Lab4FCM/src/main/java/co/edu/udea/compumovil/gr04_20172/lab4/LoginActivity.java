package co.edu.udea.compumovil.gr04_20172.lab4;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference mFireBase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SignInButton btnGoogle;
    private Button Starting, Register;
    private LoginButton loginButton;
    CallbackManager mCallbackManager;
    private EditText email, password;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId("131095257646547");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        mFireBase.child("Devices").child(refreshedToken).setValue(refreshedToken);
        btnGoogle = (SignInButton) findViewById(R.id.signInButton);
        Starting = (Button)findViewById(R.id.buttonLogin);
        Register = (Button)findViewById(R.id.buttonLogUp);
        email = (EditText) findViewById(R.id.editTextEmailLogin);
        password = (EditText) findViewById(R.id.editTextPasswordLogin);
        Starting.setOnClickListener(this);
        Register.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();
        loginButton=(LoginButton)findViewById(R.id.fbLoginButton);
        loginButton.setReadPermissions("email","public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Facebook","Exitoso");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Facebook", "Cancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebook","Error");

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Intent intentNavigation = new Intent(LoginActivity.this, Navigation_Drawer.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intentNavigation.putExtra("email", user.getEmail());
                    //Toast.makeText(getApplicationContext(), user, Toast.LENGTH_SHORT).show();
                    //startActivity(intentNavigation);
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Un error ha ocurrido", Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void handleFacebookAccessToken(final AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = mAuth.getCurrentUser();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FacebookOncomplete", "signInWithCredential:success");
                            Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_SHORT).show();
                            Intent intentNavigation = new Intent(LoginActivity.this, Navigation_Drawer.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intentNavigation.putExtra("email", user.getEmail());
                            intentNavigation.putExtra("name", user.getDisplayName());
                            intentNavigation.putExtra("lastname", "null");
                            intentNavigation.putExtra("photo", user.getPhotoUrl().toString());
                            intentNavigation.putExtra("type", 3);
                            //Toast.makeText(getApplicationContext(), user.getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();
                            startActivity(intentNavigation);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FacebookFailure", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Autenticación fallida.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }else{
            Log.d("ActivityResult","ActivityResult");
            mCallbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_SHORT).show();
                            Intent intentNavigation = new Intent(LoginActivity.this, Navigation_Drawer.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intentNavigation.putExtra("email", account.getEmail());
                            intentNavigation.putExtra("name", acct.getGivenName());
                            intentNavigation.putExtra("lastname", acct.getFamilyName());
                            intentNavigation.putExtra("photo", acct.getPhotoUrl().toString());
                            intentNavigation.putExtra("type", 2);
                            //Toast.makeText(getApplicationContext(), acct.getFamilyName(), Toast.LENGTH_SHORT).show();
                            startActivity(intentNavigation);
                            finish();
                            Log.d("TAG", "signInWithCredential:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Autenticación fallida.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
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
                final String pass_Customer = password.getText().toString();
                if ("".equals(id_Customer)) {
                    Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(pass_Customer)) {
                    Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                Login(id_Customer, pass_Customer);
                break;

            case R.id.buttonLogUp:
                //Log.d("TAG","pase por aca");
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);

            case  R.id.signInButton:
                signIn();
                break;
        }
    }

    public void Login(final String user, String pass){
        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_SHORT).show();
                            Intent intentNavigation = new Intent(LoginActivity.this, Navigation_Drawer.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intentNavigation.putExtra("email", user);
                            intentNavigation.putExtra("type", 1);
                            //Toast.makeText(getApplicationContext(), user, Toast.LENGTH_SHORT).show();
                            startActivity(intentNavigation);
                            finish();
                        } else{
                            Toast.makeText(getApplicationContext(), "Usuario invalido", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}

