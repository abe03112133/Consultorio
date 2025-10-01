package com.tecm.consultorio;

public class Cita {
    private Doctor doctor;
    private Paciente paciente;
    private String fecha;

    public Cita(Doctor doctor, Paciente paciente, String fecha) {
        this.doctor = doctor;
        this.paciente = paciente;
        this.fecha = fecha;
    }

    public Doctor getDoctor() { return doctor; }
    public Paciente getPaciente() { return paciente; }
    public String getFecha() { return fecha; }

    @Override
    public String toString() {
        return "Cita: " + fecha + " | " + doctor.getNombre() + " con " + paciente.getNombre();
    }
}
