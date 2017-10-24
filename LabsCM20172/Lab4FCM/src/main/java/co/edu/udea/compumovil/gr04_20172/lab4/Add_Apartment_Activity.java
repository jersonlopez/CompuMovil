package co.edu.udea.compumovil.gr04_20172.lab4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;

public class Add_Apartment_Activity extends AppCompatActivity implements View.OnClickListener {



    DatabaseReference mFireBase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private Uri uri;

    EditText eType, ePrice, eArea, eRooms, eUbication, eShortDescription, eLargeDescription;
    private  String textType, textPrice, textArea, textRooms, textShort, textLarge, texUbication, nombre;
    private String email;
    private static String routeDowload;
    Button btnAdd;
    ImageView imageView;
    static final int REQUEST_IMAGE_GET = 101;
    Bitmap bitmap;



    public Add_Apartment_Activity(){
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getIntent().getStringExtra("email");
        //Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_add__apartment);
        imageView = (ImageView) findViewById(R.id.imageButton);
        imageView.setOnClickListener(this);
        eType = (EditText) findViewById(R.id.editTextType);
        ePrice = (EditText) findViewById(R.id.editTextValue);
        eArea = (EditText) findViewById(R.id.editTextArea);
        eRooms = (EditText) findViewById(R.id.editTextRoom);
        eUbication = (EditText) findViewById(R.id.editTextUbication);
        eShortDescription = (EditText) findViewById(R.id.editTextShortDescription);
        eLargeDescription = (EditText) findViewById(R.id.editTextLargeDescription);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonAdd:

                textType = eType.getText().toString();
                textPrice = ePrice.getText().toString();
                textArea = eArea.getText().toString();
                textRooms = eRooms.getText().toString();
                textShort = eShortDescription.getText().toString();
                textLarge = eLargeDescription.getText().toString();
                texUbication = eUbication.getText().toString();

                if (bitmap==null || textType.equals("") || textPrice.equals("") || textArea.equals("") || textRooms.equals("") || textShort.equals("") || textLarge.equals("") || texUbication.equals("")) {
                    Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

                } else{
                    nombre = texUbication; //Nommbre de la imagen
                    nombre = nombre.replace(" ", "");
                    nombre = nombre.replace("#", "");
                    nombre = nombre.replace("-", "");

                    String route = "Apartment/".concat(nombre.concat("img.png"));
                    StorageReference riversRef = mStorageRef.child(route);
                    riversRef.putFile(uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                     //Get a URL to the uploaded content
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    routeDowload = downloadUrl.toString();

                                    Apartment apartment = new Apartment(textArea, textLarge, routeDowload, textPrice, textRooms, textShort, textType, texUbication);

                                    mFireBase.child("Apartment").child(nombre).setValue(apartment);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...
                                }
                            });

                    //Toast.makeText(getApplicationContext(), routeDowload, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Apartamento agregado", Toast.LENGTH_SHORT).show();
                    Intent intentNavigation = new Intent(Add_Apartment_Activity.this, Navigation_Drawer.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentNavigation.putExtra("email",email);
                    //Toast.makeText(getApplicationContext(),"cogi el correo "+ email, Toast.LENGTH_SHORT).show();
                    startActivity(intentNavigation);
                    finish();
                }
                break;

            case R.id.imageButton:
                this.selectPicture();
                break;
        }

    }

    public void selectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GET);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            // Log.d(TAG, String.valueOf(bitmap));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
