package com.tecm.consultorio;

public class Doctor extends Persona {
    private String especialidad;

    public Doctor(String nombre, int edad, String especialidad) {
        super(nombre, edad);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() { return especialidad; }

    @Override
    public String getRol() {
        return "Doctor";
    }

    @Override
    public String toString() {
        return "Doctor: " + nombre + " (" + especialidad + ")";
    }
    @Override
    public void mostrarInfo() {
        System.out.println(this.toString());
    }
}
