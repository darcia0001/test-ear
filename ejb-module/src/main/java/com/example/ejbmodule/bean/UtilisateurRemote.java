package com.example.ejbmodule.bean;

import com.example.ejbmodule.pojo.Utilisateur;
import jakarta.ejb.Remote;

@Remote
public interface UtilisateurRemote {

    public boolean createUser(String login , String password);
    public String createUser(Utilisateur user);
}
