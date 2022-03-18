//Asocio la clase al paquete
package com.criminalintent;

//Importo las librerias necesarias
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.UUID;

//Declaro una clase y heredo
public class UserActivity extends AppCompatActivity {

    //Instancio una varaible para intercambiar informacion
    public static String ARG_ACTIVITY_USER_ID = "user_activity_ID";

    //Declaro un user
    private UserPOJO m_User;

    //Metodo para ejecutar esta activity con un ID en el intent
    public static Intent newIntent(Context packageContext, UUID userID){
        Intent intent = new Intent(packageContext, UserActivity.class);
        intent.putExtra(ARG_ACTIVITY_USER_ID,userID.toString());
        return intent;
    }

    //Metodo que se ejecuta segun el ciclo de vida de una activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Asocio la view el layout
        setContentView(R.layout.activity_user);

        //Obtenemos el ID del usuario
        UUID userID = UUID.fromString(getIntent().getStringExtra(UserActivity.ARG_ACTIVITY_USER_ID));

        //Recorro la lista de usuarios
        for(int i = 0 ; i < ObjectLab.get(this).getList("users").size() ; i++){

            //Si encuentro el User lo asocio a su variable
            if(((UserPOJO)ObjectLab.get(this).getList("users").get(i)).getIdUser().equals(userID)){
                this.m_User = (UserPOJO)ObjectLab.get(this).getList("users").get(i);
                break;
            }

        }

        //Llamo al metodo para insertar un fragment
        insertFragment();

    }

    //Metodo para insertar un fragment
    private void insertFragment(){

        //Cambio el fragment actual por el del user
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.id_fragment_user,
                        UserFragment.newInstance(this.m_User.getIdUser()))
                .commit();

    }

    //Metodo para asociar el menu a la activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_user_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Metodo que define los items del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.delete_user:
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.return_button:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}