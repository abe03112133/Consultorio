package com.tecm.consultorio;

public class Usuario {
    private String nombreUsuario;   
    private String rol;            
    private String contrasena;

    public Usuario(String nombreUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.contrasena = "pendiente"; // por defecto
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // Método para validar login
    public boolean validarLogin(String passwordIngresada) {
        return contrasena.equals(passwordIngresada);
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombreUsuario=" + nombreUsuario + ", rol=" + rol + '}';
    }
}
