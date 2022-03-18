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
import android.widget.Toast;
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

        //
        this.m_ViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return UserListFragment.newInstance(UserPagerFragment.this.m_User.getIdUser());
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        });

        this.m_ViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                tabLayout.selectTab(tabLayout.getTabAt(m_ViewPager.getCurrentItem()));
            }
        });

        //Retorno la view
        return view;

    }

    //Metodo que se ejecuta para asociar un menu a la view
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.fragment_user_pager,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Metodo para definir el comportamiento de los objetos del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Defino un switch para las funciones del menu
        switch (item.getItemId()){

            case R.id.new_pager_user:

                Toast.makeText(getContext(), "Insert item", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
