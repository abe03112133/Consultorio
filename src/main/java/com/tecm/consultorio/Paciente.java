package com.tecm.consultorio;

public class Paciente extends Persona {

    private String historialMedico;

    public Paciente(String nombre, int edad, String historialMedico) {
        super(nombre, edad);
        this.historialMedico = historialMedico;
    }

    public String getHistorialMedico() {
        return historialMedico;
    }

    @Override
    public void mostrarInfo() {
        System.out.println(this.toString());
    }
    
    @Override
    public String getRol() {
        return "Paciente";
    }

    @Override
    public String toString() {
        return "Paciente: " + nombre + ", Edad: " + edad;
    }

}
