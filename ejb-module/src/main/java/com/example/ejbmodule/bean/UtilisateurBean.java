package com.example.ejbmodule.bean;

import com.example.ejbmodule.entity.UtilisateurEntity;
import com.example.ejbmodule.pojo.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless(name = "UtilisateurEJB")
public class UtilisateurBean implements  UtilisateurRemote , UtilisateurLocal {

    @PersistenceContext(unitName = "todoPU")
    private EntityManager entityManager;

    public UtilisateurBean() {
    }

    public boolean createUser(String login, String password) {
        if (login.length() > 0 && password.length() > 0) {
            return false;
        }

        UtilisateurEntity userEntity = entityManager.find(UtilisateurEntity.class, login);
        if (userEntity != null) {
            return false;
        }

        userEntity = new UtilisateurEntity();
        userEntity.setLogin(login);
        userEntity.setPassword(password);
        entityManager.persist(userEntity);
        return true;

    }

    public String createUser(Utilisateur user) {
        System.out.println(" ejb createUser"+user.getLogin().length()+ user.getPassword().length());
        if(!this.verificationParametre(user.getLogin(), user.getPassword())){
           return  "mot de passe ou login  incorrect ";
        }

        if(!this.verificationAlreadyExist(user.getLogin())){
                return  "l utilisateur existe deja ";
        }
        System.out.println(" validation createUser");

       UtilisateurEntity userEntity = new UtilisateurEntity();
        userEntity.setLogin(user.getLogin());
        userEntity.setPassword(user.getPassword());
        entityManager.persist(userEntity);
        System.out.println(" apres creation");
        return "{'code': 200, 'message': 'utilisateur creer avec succes' } ";

    }

    @Override
    public boolean verificationParametre(String login, String password) {

        if (login.length() == 0 || password.length() == 0) {
            return false;
        }
        System.out.println(" validation des donnees ");
        return true;
    }

    @Override
    public boolean verificationAlreadyExist(String login) {
        UtilisateurEntity userEntity = entityManager.find(UtilisateurEntity.class, login);
        System.out.println("userEntity....>"+userEntity);
        if (userEntity != null) {
            return false;
        }
        return true;
    }
}
