//Asociamos el paquete a la clase
package com.criminalintent;

//Importamos las librerias necesarias
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//Declaramos la clase y heredamos
public class LogInActivity extends AppCompatActivity {

    //Declaramos los widget necesarios
    private EditText m_EmailUserText;
    private EditText m_PasswordUserText;
    private Button m_LogInButton;

    //Declaro una variable para intercambiar informacion
    public static final String ARG_USER_ID = "user_id";

    //Declaramos una lista de usuarios
    private List<UserPOJO>users;

    //Metodo que se ejecuta segun el ciclo de vida de la activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Asociamos un layout a la view
        setContentView(R.layout.log_in_activity);

        //Actualizamos la lista de usuarios
        updateUsers();

        //Serializo los widget
        this.m_EmailUserText = (EditText) findViewById(R.id.editText_Email);
        this.m_PasswordUserText = (EditText) findViewById(R.id.editText_Password);
        this.m_LogInButton = (Button) findViewById(R.id.button_log_in);

        //Damos comportamiento al button para saber si existe el usuario
        this.m_LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Declaro una variable boolean para saber si existe el usuario
                boolean validation = false;

                //Recorro la lista de usuarios
                for(int i = 0; i < users.size(); i++){

                    //Si coincide el texto del edittext con el usuario y contraseña correspondientes accedemos
                    if(LogInActivity.this.m_EmailUserText.getText().toString().equals(users.get(i).getEmailUser()) &&
                       LogInActivity.this.m_PasswordUserText.getText().toString().equals(users.get(i).getPasswordUser())){

                        //Instancio un intent
                        Intent intent = new Intent(LogInActivity.this,MenuActivity.class);

                        //Inserto el UUID
                        intent.putExtra(ARG_USER_ID,users.get(i).getIdUser().toString());

                        //Comienzo el intent
                        startActivity(intent);

                        //Valido la variable para indicar que se ha encontrado el usuario
                        validation = true;

                    }

                }

                //Si no existe el usuario mostramos el mensaje
                if(!validation){Toast.makeText(LogInActivity.this,getString(R.string.alert_exist_user),
                        Toast.LENGTH_SHORT).show();}

            }

        });

    }

    //Al volver a la activity actualizamos la lista
    @Override
    protected void onPause() {
        super.onPause();
        updateUsers();
    }

    //Metodo para rellenar la lista de usuarios
    private void updateUsers(){

        //Obtengo la lista de usuarios
        this.users = new ArrayList<>();
        for(int i = 0; i< ObjectLab.get(this).getList("users").size();i++){
            users.add((UserPOJO) ObjectLab.get(this).getList("users").get(i));
        }

    }

}