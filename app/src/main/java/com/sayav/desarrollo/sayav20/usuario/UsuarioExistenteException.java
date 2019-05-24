package com.sayav.desarrollo.sayav20.usuario;

public class UsuarioExistenteException extends Exception {
    private String message;

    public UsuarioExistenteException() {
        this.message = "El usuario ya existe";
    }

    public UsuarioExistenteException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
