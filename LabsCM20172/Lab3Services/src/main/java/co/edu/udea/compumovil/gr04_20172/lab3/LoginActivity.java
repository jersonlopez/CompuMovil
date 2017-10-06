package co.edu.udea.compumovil.gr04_20172.lab3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import static android.Manifest.permission.INTERNET;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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


    Button Starting, Register;
    EditText email, password;
    DbHelper dbHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Starting = (Button) findViewById(R.id.buttonLogin);
        Register = (Button) findViewById(R.id.buttonLogUp);
        email = (EditText) findViewById(R.id.editTextEmailLogin);
        password = (EditText) findViewById(R.id.editTextPasswordLogin);
        Starting.setOnClickListener(this);
        Register.setOnClickListener(this);
        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.buttonLogin:
                Log.d("TAG", "hola");
                if (verificarPermisoInternet()) { //verificar los permisos
                    obtenerCustomer();
                    Toast.makeText(this, "Consultando", Toast.LENGTH_SHORT).show();
                } else
                    requestPermissions(new String[]{INTERNET}, MY_PERMISSIONS_INTERNET); //Solicitamos los permisos para el internet
                break;

            case R.id.buttonLogUp:
                //Log.d("TAG","pase por aca");
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
        }
    }

    private void obtenerCustomer() {
        String id_Customer = email.getText().toString();
        if ("".equals(id_Customer)) {
            Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_CUSTOMERS.concat(id_Customer), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Customer customer = new Gson().fromJson(response.toString(), Customer.class);

                        String prueba = customer.getId();
                        String prueba1 = customer.getPassword();
                        //Toast.makeText(LoginActivity.this, prueba, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(LoginActivity.this, prueba1, Toast.LENGTH_SHORT).show();

                        if ("".equals(password.getText().toString())) {
                            Toast.makeText(LoginActivity.this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
                        } else if (prueba1.equals(password.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_SHORT).show();
                            Intent intentNavigation = new Intent(LoginActivity.this, Navigation_Drawer.class);
                            intentNavigation.putExtra("email", prueba);
                            startActivity(intentNavigation);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error, el usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    public boolean verificarPermisoInternet() {
        /* Comprobar que la versión del dispositivo si sea la que admite los permisos en tiempo de
        *  de ejecución, es decir, de la versión de Android 6.0 o superior porque para versiones
        *  anteriores basta con colocar el permiso el el archivo manifiesto
        * */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        /* Aqui es donde comprobamos que los permisos ya hayan sido aceptdos por el usuario */
        if (checkSelfPermission(INTERNET) == PackageManager.PERMISSION_GRANTED)
            return true;

        return false; //Los permisos no han sido aceptados por el usuario
    }
}

