//Asocio el paquete a la clase
package com.criminalintent;

//Importo las librerias necesarias
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import java.util.UUID;

//Declaro la clase y heredo
public class UserPagerFragment extends Fragment {

    //Declaro una variable para intercambiar informacion
    private static final String ARG_USERPAGER_ID = "userpager_id";

    //Declaro un usuario
    private UserPOJO m_User;

    //Declaro un view pager para ver varias listas
    private ViewPager2 m_ViewPager;

    //Metodo para instanciar un fragment con un ID
    public static UserPagerFragment newInstance(UUID userId){

        Bundle args = new Bundle();
        args.putSerializable(ARG_USERPAGER_ID,userId);
        UserPagerFragment fragment = new UserPagerFragment();
        fragment.setArguments(args);
        return fragment;

    }

    //Metodo que es llamado segun el ciclo de vida del fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Indico que tenemos un menu en este fragment
        setHasOptionsMenu(true);

        //Obtengo el ID del user del fragment
        UUID userID = (UUID)getArguments().getSerializable(ARG_USERPAGER_ID);

        //Recorro la lista de users
        for(int i = 0; i < ObjectLab.get(getActivity()).getList("users").size(); i++){

            //Comparo el UUID de la lista con el obtenido del intent
            if(((UserPOJO)ObjectLab
                    .get(getActivity())
                    .getList("users")
                    .get(i)).getIdUser()
                    .equals(userID)){

                //Obtengo el usuario de la lista
                this.m_User = (UserPOJO)ObjectLab.get(getActivity()).getList("users").get(i);

            }

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Instancio una view y la asocio al fragment
        View view = inflater.inflate(R.layout.fragment_users_pager,container,false);

        //Serializamos el viewpager
        this.m_ViewPager = (ViewPager2) view.findViewById(R.id.viewpager_user_list);

        //Instancio un tablayout
        TabLayout tabLayout = view.findViewById(R.id.fragment_tabs);

        //Añado los nombres de las pestañas
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_view_type_client)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_view_type_org)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_view_type_admin)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.text_view_type_org_admin)));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        //Le doy un comportamiento al tablayout para cambiar la pestaña
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                UserPagerFragment.this.m_ViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        //Asignamos un adapter al viewpager
        this.m_ViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {

                if(position == 0){
                    return UserListFragment.newInstance(TypeUser.TYPE_CLIENT);
                }else if(position == 1) {
                    return UserListFragment.newInstance(TypeUser.TYPE_ORG);
                } else if(position == 2) {
                    return UserListFragment.newInstance(TypeUser.TYPE_ADMIN);
                } else if(position == 3) {
                    return UserListFragment.newInstance(TypeUser.TYPE_ORG_ADMIN);
                }
                return new Fragment();
            }

            //Definimos el numero de pestañas del viewpager
            @Override
            public int getItemCount() {
                return 4;
            }

        });

        //Le doy un comportamiento al viewpager al cambiar la pagina
        this.m_ViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {super.onPageSelected(position); }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                //Cambio el tab actual
                tabLayout.selectTab(tabLayout.getTabAt(m_ViewPager.getCurrentItem()));

            }

        });

        //Retorno la view
        return view;

    }

    //Metodo que se ejecuta para asociar un menu a la view
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.fragment_user_pager_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Metodo para definir el comportamiento de los objetos del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Defino un switch para las funciones del menu
        switch (item.getItemId()){

            case R.id.new_pager_user:

                UserPOJO user = new UserPOJO(TypeUser.TYPE_CLIENT,
                                    "nodefined",
                                    "nodefined@hotmail",
                                    "nodefined",
                                             R.mipmap.ic_launcher_round);

                //Añado el objeto por defecto vacio
                ObjectLab.get(getContext()).addObject(null,user);

                //Instancio una activity con el nuevo user
                startActivity(UserActivity.newIntent(getActivity(),user.getIdUser()));
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

}
