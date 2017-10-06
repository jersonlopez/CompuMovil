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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

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
        dbHelper = new DbHelper(getBaseContext()); //Instancia de DbHelper
        db = dbHelper.getWritableDatabase(); //Obtenemos la instancia de la BD


        /*final String[] items = new String[]{"From Cam", "From SD Card"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select imagen");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(), "tmp_avatar" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                    imageCaptureUri = Uri.fromFile(file);
                    try {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
                        intent.putExtra("return data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    dialog.cancel();
                } else {
                    Intent intent = new Intent();
                    intent.setType("image*//*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Complete_action_using"), PICK_FROM_FILE);
                }
            }
        });
        final AlertDialog dialog = builder.create();

        btn_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });*/
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        Bitmap bitmap = null;
        String path = "";
        if (requestCode == PICK_FROM_FILE) {
            imageCaptureUri = data.getData();
            path = getRealPathfromURI(imageCaptureUri);
            if (path == null) {
                path = imageCaptureUri.getPath();
            }
            if (path != null) {
                bitmap = BitmapFactory.decodeFile(path);
            }
        } else {
            path = imageCaptureUri.getPath();
            bitmap = BitmapFactory.decodeFile(path);
        }
        myImageView.setImageBitmap(bitmap);

    }

    public String getRealPathfromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }*/

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


                    baos = new ByteArrayOutputStream();
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
                myImageView.setImageBitmap(bitmap);
                textImage = "tengo foto";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*private void obtenerEstudiante() {
        String id_Student = txtId.getText().toString();
        if ("".equals(id_Student)){
            Toast.makeText(this, "Ingrese un Id", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_STUDENTS.concat("/").concat(id_Student), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Student student = new Gson().fromJson(response.toString(), Student.class);

                        lblId.setText(String.valueOf(student.getId()));
                        lblNombre.setText(student.getFirstname());
                        lblApellido.setText(student.getLastname());
                        lblGenero.setText(student.getGender());

                        Glide.with(MainActivity.this)
                                .load(URL_CONTAINER_DOWN.concat(String.valueOf(student.getId())).concat(student.getPhoto()))
                                .into(img_mostrar);
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
    }*/

   /* private void agregarCustomer() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_CUSTOMERS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //lblApellido.setText(response);

                          //  Una vez agregado el Estudiante con éxito procedemos a cargar la imagen

                        //Suponiendo que salga todo bien

                        Student student = new Gson().fromJson(response, Student.class);

                        String nombre = student.getId()+student.getPhoto(); //Nommbre de la imagen
                        sendImage(URL_CONTAINER_UP,nombre); //Subimos la imagen
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al crear el Student", Toast.LENGTH_SHORT).show();
                        //Log.d("nada",error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("firstname", txtNombre.getText().toString());
                params.put("lastname", txtApellidos.getText().toString());
                params.put("gender", txtGenero.getText().toString());
                params.put("photo", "img.jpg"); //Siguiendo el formato que de definió, también puede ser "img.png"

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(postRequest);
    }

    private void sendImage(String url, final String nameImage) {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                lblNombre.setText(resultResponse);

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
                params.put("image", new DataPart(nameImage, imagenSeleccionada, "image/jpeg"));
                //params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));

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
    }*/
}