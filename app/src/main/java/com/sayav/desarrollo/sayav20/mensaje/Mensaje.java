package com.sayav.desarrollo.sayav20.mensaje;

import java.util.Date;

public class Mensaje {
    protected String id;
    protected Peer origen;
    protected Peer destino;
    protected Date fechaCreacion;
    protected Date fechaReenvio;
    protected String estado;
    protected String datos;
    protected String descripcion;
    protected String detalle;
    protected String tipoHandshake;
    protected TipoMensaje tipoMensaje;

    public Mensaje(String id, Peer origen, Peer destino, Date fechaCreacion, Date fechaReenvio, String estado, String datos, String descripcion, String detalle, String tipoHandshake, TipoMensaje tipoMensaje) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.fechaCreacion = fechaCreacion;
        this.fechaReenvio = fechaReenvio;
        this.estado = estado;
        this.datos = datos;
        this.descripcion = descripcion;
        this.detalle = detalle;
        this.tipoHandshake = tipoHandshake;
        this.tipoMensaje = tipoMensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Peer getOrigen() {
        return origen;
    }

    public void setOrigen(Peer origen) {
        this.origen = origen;
    }

    public Peer getDestino() {
        return destino;
    }

    public void setDestino(Peer destino) {
        this.destino = destino;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaReenvio() {
        return fechaReenvio;
    }

    public void setFechaReenvio(Date fechaReenvio) {
        this.fechaReenvio = fechaReenvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getTipoHandshake() {
        return tipoHandshake;
    }

    public void setTipoHandshake(String tipoHandshake) {
        this.tipoHandshake = tipoHandshake;
    }

    public TipoMensaje getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(TipoMensaje tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "origen=" + origen +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaReenvio=" + fechaReenvio +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
