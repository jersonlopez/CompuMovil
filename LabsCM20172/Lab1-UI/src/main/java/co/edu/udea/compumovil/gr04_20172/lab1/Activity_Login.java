package co.edu.udea.compumovil.gr04_20172.lab1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static co.edu.udea.compumovil.gr04_20172.lab1.R.id.editTextEmailLogin;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {

    Button Starting, Register;
    EditText correo, contraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Starting = (Button)findViewById(R.id.buttonLogin);
        Register = (Button)findViewById(R.id.buttonLogUp);
        correo = (EditText) findViewById(R.id.editTextEmailLogin);
        contraseña = (EditText) findViewById(R.id.editTextPasswordLogin);
        Starting.setOnClickListener(this);
        Register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v== Starting){

           /* String lEmail = correo.getText().toString();
            String lPassword = contraseña.getText().toString();

            SharedPreferences sharedPr = getSharedPreferences("archivoSP", Context.MODE_PRIVATE);
            String email = sharedPr.getString("correo","error");
            String password = sharedPr.getString("contraseña","error");

            if (lEmail == email & lPassword == password){
            }*/
            Intent intent = new Intent(Activity_Login.this, Starting_Activity.class);
            startActivity(intent);
        }
        if (v == Register){
            Intent intent = new Intent(Activity_Login.this, Register_Activity.class);
            startActivity(intent);
        }
    }
}
