package com.sayav.desarrollo.sayav20.vinculacion;

public class CentralData {
    private String nombre;
    private String apellido;
    private String token;

    public CentralData(String nombre, String apellido, String token) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CentralData{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
