//Asociamos el paquete a la clase
package com.criminalintent;

//Importamos las librerias necesarias
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Declaramos la clase y heredamos
public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        CrimeListFragment.Callbacks, CrimeFragment.Callbacks {

    //
    private static String SAVED_INSTANCE_ID = "saved_ID";

    //Declaramos un ID del usuario
    private UserPOJO m_User;

    //Declaramos una toolbar
    private Toolbar m_Toolbar;

    //Actua como un contenedor de vistas
    private DrawerLayout m_DrawerLayout;

    //Representa un menu de navegacion estandar para la aplicacion
    private NavigationView m_NavigationView;

    //Metodo que se ejecuta segun el ciclo de la vida de una activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Asociamos un layout a la view
        setContentView(R.layout.activity_lateral_menu);

        //Configuramos nuestra navigation view
        configureNavigationView();

        //Configuramos nuestra toolbar
        configureToolBar();

        //Configuramos nuestro drawerLayout
        configureDrawerLayout();

        //Obtenemos el ID del usuario
        UUID userID = UUID.fromString(getIntent().getStringExtra(LogInActivity.ARG_USER_ID));

        //Obtengo la lista de usuarios
        List<UserPOJO>users = new ArrayList<>();
        for(int i = 0; i < ObjectLab.get(this).getList("users").size();i++){
            users.add((UserPOJO) ObjectLab.get(this).getList("users").get(i));
        }

        //Recorro la lista de usuarios
        for(int i = 0 ; i < users.size() ; i++){

            //Si encuentro el User lo asocio a su variable
            if(users.get(i).getIdUser().equals(userID)){
                this.m_User = users.get(i);
                break;
            }

        }

        //Hacemos invisible los menus en funcion del tipo de usuario
        if(this.m_User.getTypeUser() == TypeUser.TYPE_ADMIN){
            this.m_NavigationView.getMenu().findItem(R.id.drawer_settings).setVisible(true);
            this.m_NavigationView.getMenu().findItem(R.id.drawer_users).setVisible(true);
            this.m_NavigationView.getMenu().findItem(R.id.drawer_crimes).setVisible(false);
        }
        else if(this.m_User.getTypeUser() == TypeUser.TYPE_ORG){
            this.m_NavigationView.getMenu().findItem(R.id.drawer_settings).setVisible(true);
            this.m_NavigationView.getMenu().findItem(R.id.drawer_users).setVisible(false);
            this.m_NavigationView.getMenu().findItem(R.id.drawer_crimes).setVisible(true);
        }
        else if(this.m_User.getTypeUser() == TypeUser.TYPE_CLIENT){
            this.m_NavigationView.getMenu().findItem(R.id.drawer_settings).setVisible(true);
            this.m_NavigationView.getMenu().findItem(R.id.drawer_users).setVisible(false);
            this.m_NavigationView.getMenu().findItem(R.id.drawer_crimes).setVisible(true);
        }
        else if(this.m_User.getTypeUser() == TypeUser.TYPE_ORG_ADMIN){
            this.m_NavigationView.getMenu().findItem(R.id.drawer_settings).setVisible(true);
            this.m_NavigationView.getMenu().findItem(R.id.drawer_users).setVisible(true);
            this.m_NavigationView.getMenu().findItem(R.id.drawer_crimes).setVisible(true);
        }

        //Selecciono el fragment home y lo marco como seleccionado
        this.m_NavigationView.setCheckedItem(R.id.drawer_home);
        selectFirstFragment();

    }

    //
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //En funcion del item pulsamos hacemos una cosa u otra
        switch (item.getItemId()) {

            case R.id.drawer_home:

                //Cambio el fragment actual por el de crimelist
                selectFirstFragment();

                break;

            case R.id.drawer_settings:


                break;

            case R.id.drawer_crimes:

                //Cambio el fragment actual por el de crimelist
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_frame_layout,
                                 CrimeListFragment.newInstance(this.m_User.getIdUser()))
                        .commit();

                break;

            case R.id.drawer_users:



                break;

            default:
                break;

        }

        //Cerramos el drawer
        this.m_DrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Metodo para seleccionar el primer fragment
    private void selectFirstFragment(){

        //Cambio el fragment actual por el de crimelist
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_frame_layout,
                        WelcomeFragment.newInstance(this.m_User.getIdUser()))
                .commit();

    }

    //Metodo para configurar la toolbar
    private void configureToolBar(){

        //Serializamos la toolbar
        this.m_Toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);

        //Asociamos la toolbar a la activity
        setSupportActionBar(m_Toolbar);

    }

    //Metodo para configurar el drawerlayout
    private void configureDrawerLayout(){

        //Serializamos el drawerlayout
        this.m_DrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);

        //Sirve para asociar el drawerlayout y el marco de actionbar(boton hamburguesa)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                                                                       this.m_DrawerLayout,
                                                                       this.m_Toolbar,
                                                                       R.string.navigation_drawer_open,
                                                                       R.string.navigation_drawer_close);

        //Añadimos un listener que detectara la interaccion con el drawer
        this.m_DrawerLayout.addDrawerListener(toggle);

        //Sincroniza con el drawerlayout
        toggle.syncState();

    }

    //Metodo para configurar el navigationview
    private void configureNavigationView(){

        //Serializamos el navigationview
        this.m_NavigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);

        //Configuramos el listener para cuando se seleccione el elemento
        this.m_NavigationView.setNavigationItemSelectedListener(this);

    }

    //Metodo para reemplazar un fragment o insertar uno nuevo
    @Override
    public void onCrimeSelected(CrimePOJO crime) {

        //Comprobamos si existe un detail_fragment_container
        if(findViewById(R.id.detail_fragment_container) == null){

            //Instanciamos un CrimePagerActivity con un ID
            startActivity(CrimePagerActivity.newIntent(this, crime.getId()));

        }else{

            //Instaciamos un crimefragment con un ID
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            //Reemplazamos el anterior por un nuevo fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();

        }

    }

    //Metodo para actualizar un listfragment con otro crime
    @Override
    public void onCrimeUpdated(CrimePOJO crime) {

        //Instanciamos un listfragment y le asociamos mediante un manager un ID
        CrimeListFragment listFragment = (CrimeListFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        //Actualizamos su UI
        listFragment.updateUI();

    }

}