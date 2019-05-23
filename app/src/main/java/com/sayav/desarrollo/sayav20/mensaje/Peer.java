package com.sayav.desarrollo.sayav20.mensaje;

public class Peer {
    protected String id;
    protected String direccion;
    protected int puerto;

    public Peer(String id, String direccion, int puerto) {
        this.id = id;
        this.direccion = direccion;
        this.puerto = puerto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public String toString() {
        return "Peer{" +
                "id='" + id + '\'' +
                ", direccion='" + direccion + '\'' +
                ", puerto=" + puerto +
                '}';
    }
}
