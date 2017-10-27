package co.edu.udea.compumovil.gr04_20172.lab4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {


    private String email;
    private int type;

    private FirebaseAuth mAuth;
    private DatabaseReference mFireBase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private FirebaseUser customer;
    private Customer user;

    static final int REQUEST_IMAGE_GET = 101;
    private Button btn_choose_image;
    private Button btnsave;
    private static String routeDowload;
    private ImageView myImageView;
    private EditText eName, eLastname, eBorn, eDirection, eEmail, ePassword, ecPassword, ePhone, eCity;
    private String textName, textLastname, textBorn, textDirection, textEmail, textPassword, textPhone, textCity;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String textGender = "";
    private String textImage = "";
    private Uri uri;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 5);
        email = getIntent().getStringExtra("email");
        mAuth = FirebaseAuth.getInstance();
        customer = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Customer");

        //Toast.makeText(getApplicationContext(), String.valueOf(type), Toast.LENGTH_SHORT).show();
        if (type == 1) {
            setContentView(R.layout.activity_edit_profile);

            myImageView = (ImageView) findViewById(R.id.img_show);
            btn_choose_image = (Button) findViewById(R.id.btn_choose_image);
            btn_choose_image.setOnClickListener(this);
            btnsave = (Button) findViewById(R.id.btnSave);
            eName = (EditText) findViewById(R.id.editTextNombre);
            eLastname = (EditText) findViewById(R.id.editTextApellido);
            radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
            eBorn = (EditText) findViewById(R.id.editTextDate);
            eDirection = (EditText) findViewById(R.id.editTextAddress);
            eEmail = (EditText) findViewById(R.id.editTextEmail);
            ePassword = (EditText) findViewById(R.id.editTextPassword);
            ecPassword = (EditText) findViewById(R.id.editTextCpassword);
            ePhone = (EditText) findViewById(R.id.editTextPhone);
            eCity = (EditText) findViewById(R.id.editTextCity);
            eBorn.setOnClickListener(this);
            btnsave.setOnClickListener(this);
            downloadUser();

        } else if (type == 2) {
            setContentView(R.layout.activity_edit_profile_google);

            myImageView = (ImageView) findViewById(R.id.img_show);
            btn_choose_image = (Button) findViewById(R.id.btn_choose_image);
            btn_choose_image.setOnClickListener(this);
            btnsave = (Button) findViewById(R.id.btnSave);
            eName = (EditText) findViewById(R.id.editTextNombre);
            eLastname = (EditText) findViewById(R.id.editTextApellido);
            radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
            eBorn = (EditText) findViewById(R.id.editTextDate);
            eDirection = (EditText) findViewById(R.id.editTextAddress);
            ePhone = (EditText) findViewById(R.id.editTextPhone);
            eCity = (EditText) findViewById(R.id.editTextCity);
            eBorn.setOnClickListener(this);
            btnsave.setOnClickListener(this);

            downloadUserGoogle();

        } else if (type == 3) {
            setContentView(R.layout.edit_profile_facebook);

            myImageView = (ImageView) findViewById(R.id.img_show);
            btn_choose_image = (Button) findViewById(R.id.btn_choose_image);
            btn_choose_image.setOnClickListener(this);
            btnsave = (Button) findViewById(R.id.btnSave);
            eName = (EditText) findViewById(R.id.editTextNombre);
            radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
            eBorn = (EditText) findViewById(R.id.editTextDate);
            eDirection = (EditText) findViewById(R.id.editTextAddress);
            ePhone = (EditText) findViewById(R.id.editTextPhone);
            eCity = (EditText) findViewById(R.id.editTextCity);
            eBorn.setOnClickListener(this);
            btnsave.setOnClickListener(this);

            downloadUserFacebook();

        } else {
            Toast.makeText(getApplicationContext(), String.valueOf(type), Toast.LENGTH_SHORT).show();
        }

    }

    private void downloadUserFacebook() {
        //descargar datos si es logueado con Facebook

        //si es nuevo
        String name, uri;
        name = getIntent().getStringExtra("name");
        uri = getIntent().getStringExtra("photo");
        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        eName.setText(name);
        Picasso.with(getApplicationContext()).load(uri).into(myImageView);
        textImage = "ya";

        //si ya se encuentra registrado
        ref.orderByChild("id").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso", "¿Entró?");
                user = dataSnapshot.getValue(Customer.class);
                eName.setText(user.getUsername());
                ePhone.setText(String.valueOf(user.getNumberphone()));
                eBorn.setText(user.birthdate);
                eDirection.setText(user.getAddress());
                eCity.setText(user.getCity());
                Picasso.with(getApplicationContext()).load(user.getPhoto()).into(myImageView);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void downloadUser() {
        //descargar datos del usuario si se logueo con correo y contraseña
        ref.orderByChild("id").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso", "¿Entró?");
                user = dataSnapshot.getValue(Customer.class);
                eName.setText(user.getUsername());
                ePhone.setText(String.valueOf(user.getNumberphone()));
                eLastname.setText(user.getUserlastname());
                eEmail.setText(user.getId());
                eBorn.setText(user.birthdate);
                eDirection.setText(user.getAddress());
                ePassword.setText(user.getPassword());
                ecPassword.setText(user.getPassword());
                eCity.setText(user.getCity());
                Picasso.with(getApplicationContext()).load(user.getPhoto()).into(myImageView);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        textImage = "ya";
    }

    public void downloadUserGoogle() {
        //descargar datos si es logueado con Google

        //si es nuevo
        String name, lastname, uri;
        name = getIntent().getStringExtra("name");
        lastname = getIntent().getStringExtra("lastname");
        uri = getIntent().getStringExtra("photo");
        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        eName.setText(name);
        eLastname.setText(lastname);
        Picasso.with(getApplicationContext()).load(uri).into(myImageView);
        textImage = "ya";

        //si ya se encuentra registrado
        ref.orderByChild("id").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso", "¿Entró?");
                user = dataSnapshot.getValue(Customer.class);
                eName.setText(user.getUsername());
                eLastname.setText(user.getUserlastname());
                ePhone.setText(String.valueOf(user.getNumberphone()));
                eBorn.setText(user.birthdate);
                eDirection.setText(user.getAddress());
                eCity.setText(user.getCity());
                Picasso.with(getApplicationContext()).load(user.getPhoto()).into(myImageView);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.editTextDate:
                //Log.d("hola","pase por fecha");
                final Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int dayOfMonth, int monthOfYear, int year) {
                        eBorn.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
                break;

            case R.id.btnSave:

                if (type == 1) {
                    //se logueo con correo y contraseña
                    withEmailPass();

                } else if (type == 2) {
                    //se logueo con Google
                    withGoogle();

                } else if (type == 3) {
                    //se logueo con Google
                    withFacebook();

                } else {
                    //en cualquier otro caso
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btn_choose_image:
                this.selectPicture();
                break;
        }

    }

    private void withFacebook() {
        //lo que debe hacer si se logueo con Google
        textName = eName.getText().toString();
        textBorn = eBorn.getText().toString();
        textDirection = eDirection.getText().toString();
        textPhone = ePhone.getText().toString();
        textCity = eCity.getText().toString();

        if (textName.equals("") || textBorn.equals("") || textDirection.equals("") || textPhone.equals("") || textCity.equals("") || textGender.equals("")) {
            Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

        } else {
            RegisterWithFacebook();
        }
    }

    private void RegisterWithFacebook() {
        String route = "user/".concat(email.concat("img.png"));
        StorageReference riversRef = mStorageRef.child(route);

        //si no modifico la foto
        if (textImage.equals("ya")) {
            routeDowload = getIntent().getStringExtra("photo");
            Customer user = new Customer(email, textName, "null", textBorn, textDirection, "null", textPhone, textGender, textCity, routeDowload);
            mFireBase.child("Customer").child(email.replace(".", ",")).setValue(user);
        } else {
            //si modifico la foto
            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            routeDowload = downloadUrl.toString();

                            Customer user = new Customer(email, textName, "null", textBorn, textDirection, "null", textPhone, textGender, textCity, routeDowload);
                            mFireBase.child("Customer").child(email.replace(".", ",")).setValue(user);
                            //Toast.makeText(getApplicationContext(), "subi la imagen", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
        Toast.makeText(getApplicationContext(), "Datos actulizados", Toast.LENGTH_SHORT).show();
        Intent intentNavigation = new Intent(EditProfile.this, Navigation_Drawer.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentNavigation.putExtra("email", email);
        intentNavigation.putExtra("type", 3);
        startActivity(intentNavigation);
        finish();
    }

    public void withEmailPass() {

        //lo que debe hacer si se logueo con correo y contraseña
        textName = eName.getText().toString();
        textLastname = eLastname.getText().toString();
        textBorn = eBorn.getText().toString();
        textDirection = eDirection.getText().toString();
        textEmail = eEmail.getText().toString();
        textPassword = ePassword.getText().toString();
        String textCPassword = ecPassword.getText().toString();
        textPhone = ePhone.getText().toString();
        textCity = eCity.getText().toString();

        if (textName.equals("") || textLastname.equals("") || textBorn.equals("") || textDirection.equals("") || textEmail.equals("") || textPassword.equals("") || textCPassword.equals("") || textPhone.equals("") || textCity.equals("") || textGender.equals("")) {
            Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

        } else if (textPassword.equals(textCPassword) && (textPassword.length() >= 6)) {

            if (user.getId().equals(textEmail) && user.getPassword().equals(textPassword)) {
                //no modifico correo ni contraseña
                RegisterEmailAndPass();
            } else {
                //modifico correo o contraseña
                customer.updateEmail(textEmail);
                customer.updatePassword(textPassword);
                RegisterEmailAndPass();
            }

        } else if (textPassword.length() < 6) {
            Toast.makeText(getApplicationContext(), "La contraseña debe tener minimo 6 digitos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

    }

    public void RegisterEmailAndPass() {
        String route = "user/".concat(textEmail.concat("img.png"));
        StorageReference riversRef = mStorageRef.child(route);

        //si no modifico la foto
        if (textImage.equals("ya")) {
            routeDowload = user.getPhoto();
            Customer user = new Customer(textEmail, textName, textLastname, textBorn, textDirection, textPassword, textPhone, textGender, textCity, routeDowload);
            mFireBase.child("Customer").child(textEmail.replace(".", ",")).setValue(user);
        } else {
            //si modifico la foto
            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            routeDowload = downloadUrl.toString();

                            Customer user = new Customer(textEmail, textName, textLastname, textBorn, textDirection, textPassword, textPhone, textGender, textCity, routeDowload);
                            mFireBase.child("Customer").child(textEmail.replace(".", ",")).setValue(user);
                            //Toast.makeText(getApplicationContext(), "subi la imagen", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
        Toast.makeText(getApplicationContext(), "Datos actulizados", Toast.LENGTH_SHORT).show();
        Intent intentNavigation = new Intent(EditProfile.this, Navigation_Drawer.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentNavigation.putExtra("email", email);
        intentNavigation.putExtra("type", 1);
        startActivity(intentNavigation);
        finish();
    }

    public void withGoogle() {
        //lo que debe hacer si se logueo con Google
        textName = eName.getText().toString();
        textLastname = eLastname.getText().toString();
        textBorn = eBorn.getText().toString();
        textDirection = eDirection.getText().toString();
        textPhone = ePhone.getText().toString();
        textCity = eCity.getText().toString();

        if (textName.equals("") || textLastname.equals("") || textBorn.equals("") || textDirection.equals("") || textPhone.equals("") || textCity.equals("") || textGender.equals("")) {
            Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

        } else {
            RegisterWithGoogle();
        }
    }

    public void RegisterWithGoogle() {
        String route = "user/".concat(email.concat("img.png"));
        StorageReference riversRef = mStorageRef.child(route);

        //si no modifico la foto
        if (textImage.equals("ya")) {
            routeDowload = getIntent().getStringExtra("photo");
            Customer user = new Customer(email, textName, textLastname, textBorn, textDirection, "null", textPhone, textGender, textCity, routeDowload);
            mFireBase.child("Customer").child(email.replace(".", ",")).setValue(user);
        } else {
            //si modifico la foto
            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            routeDowload = downloadUrl.toString();

                            Customer user = new Customer(email, textName, textLastname, textBorn, textDirection, "null", textPhone, textGender, textCity, routeDowload);
                            mFireBase.child("Customer").child(email.replace(".", ",")).setValue(user);
                            //Toast.makeText(getApplicationContext(), "subi la imagen", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
        Toast.makeText(getApplicationContext(), "Datos actulizados", Toast.LENGTH_SHORT).show();
        Intent intentNavigation = new Intent(EditProfile.this, Navigation_Drawer.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentNavigation.putExtra("email", email);
        intentNavigation.putExtra("type", 2);
        startActivity(intentNavigation);
        finish();
    }

    public void selectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GET);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            // Log.d(TAG, String.valueOf(bitmap));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                myImageView.setImageBitmap(bitmap);
                textImage = "foto";

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void rbOnClick(View view) {
        int genderId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(genderId);
        textGender = radioButton.getText().toString();

    }
}
