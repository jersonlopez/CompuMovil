package co.edu.udea.compumovil.gr04_20172.lab3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.Map;

public class Add_Apartment_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String CUSTOM_ACTION = "co.edu.udea.compumovil.gr04_20172.lab3.BOOT";

    //PERMISOS
    private final int MY_PERMISSIONS_GALERIA = 100;
    private final int MY_PERMISSIONS_INTERNET = 150;
    private final int OPEN_GALERIA = 200;

    // LINK DEL SERIVDOR
    private final String HOST_CODE = "https://ensuenoservices-jersonlopez.c9users.io";

    // COMPLEMENTOS
    private final String URL_APARTMENTS_COMPLEMENT = ":8080/api/Apartments/";
    private final String URL_CONTAINER_DOWN_COMPLEMENT = ":8080/api/Containers/all/download/";
    private final String URL_CONTAINER_UP_COMPLEMENT = ":8080/api/Containers/all/upload";

    private final String URL_APARTMENTS = HOST_CODE.concat(URL_APARTMENTS_COMPLEMENT);
    private final String URL_CONTAINER_DOWN = HOST_CODE.concat(URL_CONTAINER_DOWN_COMPLEMENT);
    private final String URL_CONTAINER_UP = HOST_CODE.concat(URL_CONTAINER_UP_COMPLEMENT);

    DbHelper dbHelper;
    SQLiteDatabase db;
    EditText eType, ePrice, eArea, eRooms, eUbication, eShortDescription, eLargeDescription;
    String email;
    Button btnAdd;
    ImageView imageView;
    static final int REQUEST_IMAGE_GET = 101;
    Bitmap bitmap;
    ByteArrayOutputStream baos;
    byte[] blob = null;


    public Add_Apartment_Activity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getIntent().getStringExtra("email");
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
        dbHelper = new DbHelper(getBaseContext()); //Instancia de DbHelper
        db = dbHelper.getWritableDatabase(); //Obtenemos la instancia de la BD

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonAdd:

                String textType = eType.getText().toString();
                String textPrice = ePrice.getText().toString();
                String textArea = eArea.getText().toString();
                String textRooms = eRooms.getText().toString();
                String textShort = eShortDescription.getText().toString();
                String textLarge = eLargeDescription.getText().toString();
                String texUbication = eUbication.getText().toString();

                if (bitmap == null || textType.equals("") || textPrice.equals("") || textArea.equals("") || textRooms.equals("") || textShort.equals("") || textLarge.equals("") || texUbication.equals("")) {
                    Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

                } else {
                    postApartment();
                    baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 75, baos);
                    blob = baos.toByteArray();
                    ContentValues values1 = new ContentValues();
                    ContentValues values = new ContentValues();
                    values.put(ApartmentsDB.ColumnApartment.ubicationApartment, texUbication);
                    values.put(ApartmentsDB.ColumnApartment.typeApartment, textType);
                    values.put(ApartmentsDB.ColumnApartment.priceApartment, textPrice);
                    values.put(ApartmentsDB.ColumnApartment.areaApartment, textArea);
                    values.put(ApartmentsDB.ColumnApartment.roomsApartment, textRooms);
                    values.put(ApartmentsDB.ColumnApartment.ShortDescriptionApartment, textShort);
                    values.put(ApartmentsDB.ColumnApartment.LargeDescriptionApartment, textLarge);

                    //Log.d("hola","pase por aqui");
                    db.insertWithOnConflict(ApartmentsDB.entityApartment, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                    String consulta = "select " + ApartmentsDB.ColumnApartment.ubicationApartment + " from " + ApartmentsDB.entityApartment + " where " + ApartmentsDB.ColumnApartment.typeApartment + "=" + "\"" + textType + "\"" +
                            " and " + ApartmentsDB.ColumnApartment.priceApartment + "=" + "\"" + textPrice + "\"" + " and " + ApartmentsDB.ColumnApartment.roomsApartment + "=" + "\"" + textRooms + "\"" +
                            " and " + ApartmentsDB.ColumnApartment.areaApartment + "=" + "\"" + textArea + "\"" + " and " + ApartmentsDB.ColumnApartment.ubicationApartment + "=" + "\"" +
                            texUbication + "\"";
                    Cursor cursor = db.rawQuery(consulta, null);
                    if (cursor.moveToNext()) {
                        values1.put(ApartmentsDB.ColumnResource.photo, blob);
                        values1.put(ApartmentsDB.ColumnResource.ubicationApartment, texUbication);
                        db.insertWithOnConflict(ApartmentsDB.entityResource, null, values1, SQLiteDatabase.CONFLICT_IGNORE);
                    }

                    String consulta1 = "select " + ApartmentsDB.ColumnResource.photo + " from " + ApartmentsDB.entityResource;
                    Cursor cursor1 = db.rawQuery(consulta1, null);
                    if (cursor1.getCount() > 0) {
                        //Toast.makeText(getApplicationContext(),"la imagen si guardo", Toast.LENGTH_SHORT).show();
                        Log.d("Excelente", "Hay datos en el cursor");
                    } else {
                        Toast.makeText(getApplicationContext(), "la imagen no entro", Toast.LENGTH_SHORT).show();
                        //Log.d("Ups","Don bochi sin elementos");
                    }
                    /*while (cursor.moveToNext()) {
                        Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.ubicationApartment)), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.priceApartment)), Toast.LENGTH_LONG).show();
                    }*/
                    //startService();
                    Toast.makeText(getApplicationContext(), "Apartamento agregado", Toast.LENGTH_SHORT).show();
                    Intent intentNavigation = new Intent(Add_Apartment_Activity.this, Navigation_Drawer.class);
                    intentNavigation.putExtra("email", email);
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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), OPEN_GALERIA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_GALERIA && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            // Log.d(TAG, String.valueOf(bitmap));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 75, baos);
                blob = baos.toByteArray();
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void getApartment() {
        String id_Apartment = eUbication.getText().toString();
        if ("".equals(id_Apartment)) {
            Toast.makeText(this, "Ingrese una Ubicación", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_APARTMENTS.concat(id_Apartment), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Apartment apartment = new Gson().fromJson(response.toString(), Apartment.class);

                        /*lblId.setText(String.valueOf(student.getId()));
                        lblNombre.setText(student.getFirstname());
                        lblApellido.setText(student.getLastname());
                        lblGenero.setText(student.getGender());*/

                        Glide.with(Add_Apartment_Activity.this)
                                .load(URL_CONTAINER_DOWN.concat(String.valueOf(apartment.getId())).concat(apartment.getPhotoapartment()))
                                .into(imageView);
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

    private void postApartment() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_APARTMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  Una vez agregado el Estudiante con éxito procedemos a cargar la imagen

                        //Suponiendo que salga todo bien

                        Apartment apartment = new Gson().fromJson(response, Apartment.class);

                        String nombre = apartment.getId() + apartment.getPhotoapartment(); //Nommbre de la imagen
                        nombre = nombre.replace(" ", "");
                        nombre = nombre.replace("#", "");
                        nombre = nombre.replace("-", "");
                        sendImage(URL_CONTAINER_UP, nombre); //Subimos la imagen
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al crear el Student", Toast.LENGTH_SHORT).show();
                        //Log.d("nada",error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Id", eUbication.getText().toString());
                params.put("photoapartment", "img.png"); //Siguiendo el formato que de definió, también puede ser "img.jpg"
                params.put("typeapartment", eType.getText().toString());
                params.put("priceapartment", ePrice.getText().toString());
                params.put("areaapartment", eArea.getText().toString());
                params.put("roomsapartment", eRooms.getText().toString());
                params.put("shortdescriptionapartment", eShortDescription.getText().toString());
                params.put("largedescriptionapartment", eLargeDescription.getText().toString());

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


    public void startService() {

        Intent intent = new Intent();
        intent.setAction(CUSTOM_ACTION);
        sendBroadcast(intent);
    }
}
