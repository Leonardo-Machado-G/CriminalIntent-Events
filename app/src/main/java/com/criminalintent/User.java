//Asociamos el paquete a la clase
package com.criminalintent;

//Importamos las librerias necesarias
import java.util.UUID;

//Declaramos la clase
public class User {

    //Definimos los atributos de la clase
    private UUID idUser;
    private String nameUser;
    private String emailUser;
    private String passwordUser;
    private long photoUser;
    private TypeUser typeUser;

    //Defino el constructor de la clase
    public User(TypeUser typeUser,
                  String nameUser,
                  String emailUser,
                  String passwordUser,
                  long photoUser){

        //Si el tipo de usuario es nulo, establecemos por defecto type_client
        this.typeUser = (typeUser == null) ? TypeUser.TYPE_CLIENT : typeUser;
        this.idUser = UUID.randomUUID();
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.photoUser = photoUser;

    }

    //Defino los getters de la clase
    public UUID getIdUser() {return idUser;}
    public String getNameUser() {return nameUser;}
    public String getEmailUser() {return emailUser;}
    public String getPasswordUser() {return passwordUser;}
    public long getPhotoUser() {return photoUser;}
    public TypeUser getTypeUser() {return typeUser;}

    //Defino los setters de la clase
    public void setIdUser(UUID idUser) {this.idUser = idUser;}
    public void setNameUser(String nameUser) {this.nameUser = nameUser;}
    public void setEmailUser(String emailUser) {this.emailUser = emailUser;}
    public void setPasswordUser(String passwordUser) {this.passwordUser = passwordUser;}
    public void setPhotoUser(long photoUser) {this.photoUser = photoUser;}
    public void setTypeUser(TypeUser typeUser) {this.typeUser = typeUser;}

}
