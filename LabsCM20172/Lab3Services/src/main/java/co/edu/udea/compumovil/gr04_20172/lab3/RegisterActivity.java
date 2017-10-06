package co.edu.udea.compumovil.gr04_20172.lab3;

import android.app.DatePickerDialog;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static co.edu.udea.compumovil.gr04_20172.lab3.R.id.imageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //PERMISOS
    private final int MY_PERMISSIONS_GALERIA = 100;
    private final int MY_PERMISSIONS_INTERNET = 150;
    private final int OPEN_GALERIA = 200;

    // LINK DEL SERIVDOR
    private final String HOST_CODE = "https://ensuenoservices-jersonlopez.c9users.io";

    // COMPLEMENTOS
    private final String URL_CUSTOMERS_COMPLEMENT = ":8080/api/Customers/";
    private final String URL_CONTAINER_DOWN_COMPLEMENT = ":8080/api/Containers/user/download/";
    private final String URL_CONTAINER_UP_COMPLEMENT = ":8080/api/Containers/user/upload";

    private final String URL_CUSTOMERS = HOST_CODE.concat(URL_CUSTOMERS_COMPLEMENT);
    private final String URL_CONTAINER_DOWN = HOST_CODE.concat(URL_CONTAINER_DOWN_COMPLEMENT);
    private final String URL_CONTAINER_UP = HOST_CODE.concat(URL_CONTAINER_UP_COMPLEMENT);

    private static final String TAG = "TAG";
    private Uri imageCaptureUri;
    private ImageView myImageView;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    static final int REQUEST_IMAGE_GET = 101;
    Button btn_choose_image;
    DbHelper dbHelper;
    SQLiteDatabase db;
    Button btnsave;
    private EditText eName, eLastname, eBorn, eDirection, eEmail, ePassword, ecPassword, ePhone, eCity;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    String textGender = "";
    String textImage = "";
    Bitmap bitmap;
    ByteArrayOutputStream baos;
    byte[] blob = null;


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
        dbHelper = new DbHelper(getBaseContext()); //Instancia de DbHelper
        db = dbHelper.getWritableDatabase(); //Obtenemos la instancia de la BD
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

            case R.id.btnSave:

                String textName = eName.getText().toString();
                String textLastname = eLastname.getText().toString();
                String textBorn = eBorn.getText().toString();
                String textDirection = eDirection.getText().toString();
                String textEmail = eEmail.getText().toString();
                String textPassword = ePassword.getText().toString();
                String textCPassword = ecPassword.getText().toString();
                String textPhone = ePhone.getText().toString();
                String textCity = eCity.getText().toString();

                if (bitmap == null ||textName.equals("") || textLastname.equals("") || textBorn.equals("") || textDirection.equals("") || textEmail.equals("") || textPassword.equals("") || textCPassword.equals("") || textPhone.equals("") || textCity.equals("") || textGender.equals("")) {
                    Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

                } else if (textPassword.equals(textCPassword)) {

                    //    Aqui va el codigo de guardar en el servidor
                    postCustomer();

                    /*baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,75,baos);
                    blob = baos.toByteArray();

                    ContentValues values = new ContentValues();
                    values.put(ApartmentsDB.ColumnUser.photo, blob);
                    values.put(ApartmentsDB.ColumnUser.email, textEmail);
                    values.put(ApartmentsDB.ColumnUser.userName, textName);
                    values.put(ApartmentsDB.ColumnUser.userLastName, textLastname);
                    values.put(ApartmentsDB.ColumnUser.birthdate, textBorn);
                    values.put(ApartmentsDB.ColumnUser.address, textDirection);
                    values.put(ApartmentsDB.ColumnUser.password, textPassword);
                    values.put(ApartmentsDB.ColumnUser.numberPhone, textPhone);
                    values.put(ApartmentsDB.ColumnUser.gender, textGender);
                    values.put(ApartmentsDB.ColumnUser.city, textCity);
                    //values.put(ApartmentsDB.ColumnUser.photo, "hola");
                    //Log.d("hola","pase por aqui");
                    db.insertWithOnConflict(ApartmentsDB.entityUser, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                    //String cosulta= "select email, city from "  + ApartmentsDB.entityUser;
                    String consulta = "select " + ApartmentsDB.ColumnUser.photo + " from " + ApartmentsDB.entityUser;
                    Cursor cursor = db.rawQuery(consulta, null);
                    if(cursor.getCount()>0)
                    {
                        //Toast.makeText(getApplicationContext(),"la imagen si se guardo", Toast.LENGTH_SHORT).show();
                        //Log.d("Excelente", "Hay datos en el cursor");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"la imagen no se guardo", Toast.LENGTH_SHORT).show();
                        Log.d("Ups","Don bochi sin elementos");
                    }
                    //Toast.makeText(getApplicationContext(), cursor.getCount() + " nacimiento: " + textBorn, Toast.LENGTH_LONG).show();
                    /*while (cursor.moveToNext()) {
                        //Log.d(TAG,cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.email)));
                        //Log.d(TAG,cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.city)));
                        Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.gender)), Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.city)), Toast.LENGTH_LONG).show();


                        //eEmail.setText(cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.email)));
                        //eCity.setText(cursor.getString(1));
                    }*/
                    Toast.makeText(getApplicationContext(), "Regitro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intentNavigation = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intentNavigation);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_choose_image:
                this.selectPicture();
                break;
        }

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
            Uri uri = data.getData();
            // Log.d(TAG, String.valueOf(bitmap));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,75,baos);
                blob = baos.toByteArray();
                myImageView.setImageBitmap(bitmap);
                textImage = "tengo foto";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getCustomer() {
        String id_Customer = eEmail.getText().toString();
        if ("".equals(id_Customer)){
            Toast.makeText(this, "Ingrese un Id", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_CUSTOMERS.concat(id_Customer), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Customer customer = new Gson().fromJson(response.toString(), Customer.class);

                        String prueba = customer.getId();
                        String prueba1 = customer.getPassword();
                        Toast.makeText(RegisterActivity.this, prueba, Toast.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this, prueba1, Toast.LENGTH_SHORT).show();

                        Glide.with(RegisterActivity.this)
                                .load(URL_CONTAINER_DOWN.concat(String.valueOf(customer.getId())).concat(customer.getPhoto()))
                                .into(myImageView);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error consultando información", Toast.LENGTH_SHORT).show();
                        //Log.d("nada2",error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

   private void postCustomer() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_CUSTOMERS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //lblApellido.setText(response);

                          //  Una vez agregado el Estudiante con éxito procedemos a cargar la imagen

                        //Suponiendo que salga todo bien

                        Customer customer = new Gson().fromJson(response, Customer.class);

                        String nombre = customer.getId()+customer.getPhoto(); //Nommbre de la imagen
                        sendImage(URL_CONTAINER_UP,nombre); //Subimos la imagen
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al crear el Cliente", Toast.LENGTH_SHORT).show();
                        //Log.d("nada",error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Id", eEmail.getText().toString());
                params.put("username", eName.getText().toString());
                params.put("userlastname", eLastname.getText().toString());
                params.put("birthdate", eBorn.getText().toString());
                params.put("address", eDirection.getText().toString());
                params.put("password", ePassword.getText().toString());
                params.put("numberphone", ePhone.getText().toString());
                params.put("gender", textGender);
                params.put("city", eCity.getText().toString());
                params.put("photo", "img.png"); //Siguiendo el formato que de definió, también puede ser "img.jpg"

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(postRequest);
       //Toast.makeText(this, "acabe de agregar", Toast.LENGTH_SHORT).show();
    }

    private void sendImage(String url, final String nameImage) {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                //lblNombre.setText(resultResponse);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error subiendo la imagen", Toast.LENGTH_SHORT).show();
                //Log.d("nada3", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("image", new DataPart(nameImage, blob, "image/png"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                //headers.put("SessionId", mSessionId);
                return headers;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }
}