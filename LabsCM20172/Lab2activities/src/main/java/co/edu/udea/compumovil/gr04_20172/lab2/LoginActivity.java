package co.edu.udea.compumovil.gr04_20172.lab2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button Starting, Register;
    EditText email, password;
    DbHelper dbHelper;
    SQLiteDatabase db;


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
        dbHelper = new DbHelper(this);
        db= dbHelper.getWritableDatabase();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.buttonLogin:

                String consulta = "select " + ApartmentsDB.ColumnUser.email + ", " + ApartmentsDB.ColumnUser.password + " from " + ApartmentsDB.entityUser +" where " +ApartmentsDB.ColumnUser.email + "="+ "\"" +
                        email.getText().toString() + "\"" +"and User.password=" + "\"" + password.getText().toString() + "\"";
                //Toast.makeText(getApplicationContext(),consulta.toString(), Toast.LENGTH_SHORT).show();
                    Cursor cursor=db.rawQuery(consulta,null);

                    if (cursor.moveToNext() != false ){
                    Toast.makeText(getApplicationContext(),"Login exitoso",Toast.LENGTH_SHORT).show();
                        Intent intentNavigation = new Intent(LoginActivity.this, Navigation_Drawer.class);
                        intentNavigation.putExtra("email",cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.email)));
                        //Toast.makeText(getApplicationContext(),cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.email)),Toast.LENGTH_SHORT).show();
                        startActivity(intentNavigation);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Correo o contraseña incorrectos",Toast.LENGTH_SHORT).show();

                    }
                break;

            case R.id.buttonLogUp:
                //Log.d("TAG","pase por aca");
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
        }
    }
}

