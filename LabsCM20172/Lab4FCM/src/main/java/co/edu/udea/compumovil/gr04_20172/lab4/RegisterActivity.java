package co.edu.udea.compumovil.gr04_20172.lab4;

import android.app.DatePickerDialog;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static android.R.attr.bitmap;
import static co.edu.udea.compumovil.gr04_20172.lab4.R.id.imageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    DatabaseReference mFireBase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;
    private static String routeDowload;

    private ImageView myImageView;
    static final int REQUEST_IMAGE_GET = 101;
    Button btn_choose_image;
    Button btnsave;
    private EditText eName, eLastname, eBorn, eDirection, eEmail, ePassword, ecPassword, ePhone, eCity;
    private String textName,textLastname,textBorn,textDirection,textEmail, textPassword, textPhone, textCity;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    String textGender = "";
    String textImage = "";
    private Uri uri;
    Bitmap bitmap;
    ByteArrayOutputStream baos;
    byte[] blob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        mAuth = FirebaseAuth.getInstance();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)


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

            /*case R.id.imageViewProfile:

                break;*/

            case R.id.btnSave:

                textName = eName.getText().toString();
                textLastname = eLastname.getText().toString();
                textBorn = eBorn.getText().toString();
                textDirection = eDirection.getText().toString();
                textEmail = eEmail.getText().toString();
                textPassword = ePassword.getText().toString();
                String textCPassword = ecPassword.getText().toString();
                textPhone = ePhone.getText().toString();
                textCity = eCity.getText().toString();

                if (bitmap == null ||textName.equals("") || textLastname.equals("") || textBorn.equals("") || textDirection.equals("") || textEmail.equals("") || textPassword.equals("") || textCPassword.equals("") || textPhone.equals("") || textCity.equals("") || textGender.equals("")) {
                    Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

                } else if (textPassword.equals(textCPassword) && (textPassword.length() >= 6)){


                    mAuth.createUserWithEmailAndPassword(textEmail, textPassword)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());
                                        Register();
                                    } else{
                                        Toast.makeText(getApplicationContext(), "Error creating user", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });

                } else if (textPassword.length() < 6){
                    Toast.makeText(getApplicationContext(), "La contraseña debe tener minimo 6 digitos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_choose_image:
                this.selectPicture();
                break;
        }

    }

    public void Register(){

        String route = "user/".concat(textEmail.concat("img.png"));
        StorageReference riversRef = mStorageRef.child(route);
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

        Toast.makeText(getApplicationContext(), "Regitro exitoso", Toast.LENGTH_SHORT).show();
        Intent intentNavigation = new Intent(RegisterActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentNavigation);
        finish();
    }

    public void rbOnClick(View view) {
        int genderId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(genderId);
        textGender = radioButton.getText().toString();

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


}