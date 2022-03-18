//Asocio el paquete a la clase
package com.criminalintent;

//Importo los librerias necesarias
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Declaro la clase y heredo
public class UserListFragment extends Fragment {

    //Declaro una variable para intercambiar informacion
    private static final String ARG_USERLIST_ID = "userlist_id";

    //Defino una interface para
    public interface CallUser{
        void onUserSelected(UserPOJO user);
    }

    //Declaro los widget necesarios
    private RecyclerView m_UserRecyclerView;
    private UserAdapter m_Adapter;

    //Variable que determinara que tipo de lista es
    private TypeUser m_TypeUser;

    //Declaro una interface para comunicarse con los users
    private CallUser m_CallUser;

    //
    private List<UserPOJO>users;

    //Metodo para instanciar un fragment con typeuser
    public static UserListFragment newInstance(TypeUser typeUser){
        Bundle args = new Bundle();
        args.putSerializable(ARG_USERLIST_ID,typeUser);
        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Metodo que es llamado segun el ciclo de vida del fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Obtengo el typeuser del fragment
        this.m_TypeUser = (TypeUser)getArguments().getSerializable(ARG_USERLIST_ID);

    }

    //Metodo que actualiza la view segun el ciclo de vida del fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Instancio una view y la asocio al fragment
        View view = inflater.inflate(R.layout.fragment_users_list,container,false);

        //Serializo la recyclerview
        this.m_UserRecyclerView = (RecyclerView) view.findViewById(R.id.user_recycler_view);

        //Indico el recyclerview como se van a disponer las view
        this.m_UserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Llamo al metodo para actualizar la lista de users
        updateList();

        //Inserto en el adapter los crimes
        this.m_Adapter = new UserAdapter(users);

        //Asociamos el adapter a la recyclerview
        this.m_UserRecyclerView.setAdapter(this.m_Adapter);

        //Devuelvo la view
        return view;

    }

    //Metodo que es llamado al salir el fragment de segundo plano
    public void onResume(){
        super.onResume();
        updateUI();
    }

    //Metodo que actualiza nuestra UI
    public void updateUI(){

        //Llamo al metodo para actualizar la lista de users
        updateList();

        //Si el adapter es nulo accedemos
        if(this.m_Adapter == null){

            //Creamos un nuevo adapter y le insertamos los users
            this.m_Adapter = new UserAdapter(this.users);

            //Asociamos el adapter a la recyclerview
            this.m_UserRecyclerView.setAdapter(this.m_Adapter);

        }else{

            //Actualizamos la lista que debe usar el adaptador para mostrar los crimenes
            this.m_Adapter.setUsers(this.users);

            //Notificamos que ha habido un cambio en los datos
            this.m_Adapter.notifyDataSetChanged();

        }

    }

    //Metodo que rellena la lista de users
    private void updateList(){

        //Instancio una lista de user
        this.users = new ArrayList<>();

        //Añadimos los users a la lista en funcion de su tipo y el tipo de lista
        for(int i = 0; i < ObjectLab.get(getActivity()).getList("users").size() ; i++){

            //Si este tipo de lista es client y el objeto obtenido de la lista es client, lo añadimos a la lista
            if(this.m_TypeUser == TypeUser.TYPE_CLIENT &&
                    ((UserPOJO) ObjectLab.get(getActivity())
                            .getList("users")
                            .get(i))
                            .getTypeUser() == TypeUser.TYPE_CLIENT){

                users.add((UserPOJO) ObjectLab.get(getActivity()).getList("users").get(i));

            }
            else if(this.m_TypeUser == TypeUser.TYPE_ORG &&
                    ((UserPOJO) ObjectLab.get(getActivity())
                            .getList("users")
                            .get(i))
                            .getTypeUser() == TypeUser.TYPE_ORG){

                users.add((UserPOJO) ObjectLab.get(getActivity()).getList("users").get(i));

            }
            else if(this.m_TypeUser == TypeUser.TYPE_ADMIN &&
                    ((UserPOJO) ObjectLab.get(getActivity())
                            .getList("users")
                            .get(i))
                            .getTypeUser() == TypeUser.TYPE_ADMIN){

                users.add((UserPOJO) ObjectLab.get(getActivity()).getList("users").get(i));

            }
            else if(this.m_TypeUser == TypeUser.TYPE_ORG_ADMIN &&
                    ((UserPOJO) ObjectLab.get(getActivity())
                            .getList("users")
                            .get(i))
                            .getTypeUser() == TypeUser.TYPE_ORG_ADMIN){

                users.add((UserPOJO) ObjectLab.get(getActivity()).getList("users").get(i));

            }

        }

    }

    //Metodo para obtener el contexto una vez se asocia el fragment
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.m_CallUser = (CallUser) getContext();
    }

    //Metodo para retirar la interface antes de ser eliminado el fragment
    @Override
    public void onDetach() {
        super.onDetach();
        this.m_CallUser = null;
    }

    //Declaro una clase interna privada
    private class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Declaro los widget necesarios
        private TextView m_TitleUserText;
        private TextView m_TypeUserText;
        private UserPOJO m_User;

        //Defino un constructor donde serializamos sus componentes
        public UserHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_user,parent,false));
            this.m_TitleUserText = (TextView) itemView.findViewById(R.id.user_title);
            this.m_TypeUserText = (TextView) itemView.findViewById(R.id.rol_title);
            this.itemView.setOnClickListener(this);

        }

        //Metodo que carga el contenido de un crime al ser llamado
        public void bind(UserPOJO user){
            this.m_User = user;
            this.m_TitleUserText.setText(getString(R.string.name_user_list) + user.getNameUser());
            this.m_TypeUserText.setText(getString(R.string.type_user_list) + user.getTypeUser().name());
        }

        //Metodo que se ejecuta al hacer click en la view enviando un crime
        @Override
        public void onClick(View v) {m_CallUser.onUserSelected(this.m_User);}

    }

    //Declaro una clase interna privada
    private class UserAdapter extends RecyclerView.Adapter<UserListFragment.UserHolder>{

        //Declaro una lista de crimes privada
        private List<UserPOJO> m_Users;

        //Defino un constructor
        public UserAdapter(List<UserPOJO> users){this.m_Users = users;}

        //Metodo que define la view del holder
        @NonNull
        @Override
        public UserListFragment.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserListFragment.UserHolder(LayoutInflater.from(getActivity()),parent);
        }

        //Metodo que es llamado al estar en una determinada posicion
        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {

            //Llamado al metodo para actualizar el contenido de sus widget
            holder.bind(this.m_Users.get(position));

        }

        //Metodo que obtiene el tamaño de la lista
        @Override
        public int getItemCount() {return m_Users.size();}

        //Metodo que reemplaza la lista de users que se muestran
        public void setUsers (List<UserPOJO> users){this.m_Users = users;}

    }

}
