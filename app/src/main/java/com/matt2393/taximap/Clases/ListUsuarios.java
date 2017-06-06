package com.matt2393.taximap.Clases;


import java.util.List;

public class ListUsuarios {

    private List<Usuarios> users;

    public ListUsuarios() {
    }

    public ListUsuarios(List<Usuarios> users) {
        this.users = users;
    }

    public List<Usuarios> getUsers() {
        return users;
    }

    public void setUsers(List<Usuarios> users) {
        this.users = users;
    }
}
