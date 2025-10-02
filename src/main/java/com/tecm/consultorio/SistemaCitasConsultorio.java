package com.tecm.consultorio;

import java.util.*;
import java.io.*;

public class SistemaCitasConsultorio {

    private static Scanner sc = new Scanner(System.in);

    // Listas para almacenar la información
    private static List<Doctor> doctores = new ArrayList<>();
    private static List<Paciente> pacientes = new ArrayList<>();
    private static List<Cita> citas = new ArrayList<>();

    public static void main(String[] args) {
        
        cargarDoctores();
        cargarPacientes();
        cargarCitas();

        System.out.println("===== Sistema de Citas del Consultorio =====");

        System.out.print("Ingrese usuario: ");
        String usuario = sc.nextLine();

        System.out.print("Ingrese contraseña: ");
        String contrasena = sc.nextLine();

        // Control simple de usuarios
        if (usuario.equals("admin") && contrasena.equals("123")) {
            menuAdmin();
        } else if (usuario.equals("doctor") && contrasena.equals("123")) {
            menuDoctor();
        } else if (usuario.equals("cliente") && contrasena.equals("123")) {
            menuCliente();
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    // menu
    private static void menuAdmin() {
        int opcion;
        do {
            System.out.println("\n--- Menú Admin ---");
            System.out.println("1. Registrar Doctor");
            System.out.println("2. Registrar Paciente");
            System.out.println("3. Crear Cita");
            System.out.println("4. Listar Citas");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 ->
                    altaDoctor();
                case 2 ->
                    altaPaciente();
                case 3 ->
                    crearCita();
                case 4 ->
                    listarCitas();
            }
        } while (opcion != 0);
    }

    private static void menuDoctor() {
        int opcion;
        do {
            System.out.println("\n--- Menu Doctor ---");
            System.out.println("1. Registrar Paciente");
            System.out.println("2. Crear Cita");
            System.out.println("3. Listar Citas");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 ->
                    altaPaciente();
                case 2 ->
                    crearCita();
                case 3 ->
                    listarCitas();
            }
        } while (opcion != 0);
    }

    private static void menuCliente() {
        System.out.println("\n--- Citas de Cliente ---");
        listarCitas();
    }

    // Metodos del menu
    private static void altaDoctor() {
        System.out.print("Ingrese nombre del Doctor: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese edad del Doctor: ");
        int edad = leerEntero();

        System.out.print("Ingrese especialidad: ");
        String especialidad = sc.nextLine();

        Doctor d = new Doctor(nombre, edad, especialidad);
        doctores.add(d);
        guardarDoctores(); 
        System.out.println(" Doctor registrado: " + d);
    }

    private static void altaPaciente() {
        System.out.print("Ingrese nombre del Paciente: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese edad del Paciente: ");
        int edad = leerEntero();

        System.out.print("Ingrese historial médico: ");
        String historial = sc.nextLine();

        Paciente p = new Paciente(nombre, edad, historial);
        pacientes.add(p);
        guardarPacientes();
        System.out.println("Paciente registrado: " + p);
    }

    private static void crearCita() {
        if (doctores.isEmpty() || pacientes.isEmpty()) {
            System.out.println(" Debe haber al menos un Doctor y un Paciente registrados.");
            return;
        }

        System.out.println("\nSeleccione Doctor:");
        for (int i = 0; i < doctores.size(); i++) {
            System.out.println((i + 1) + ". " + doctores.get(i));
        }
        int idxDoc = leerEntero() - 1;

        System.out.println("Seleccione Paciente:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i));
        }
        int idxPac = leerEntero() - 1;

        System.out.print("Ingrese fecha de la cita (dd/mm/aaaa): ");
        String fecha = sc.nextLine();

        Cita c = new Cita(doctores.get(idxDoc), pacientes.get(idxPac), fecha);
        citas.add(c);
        guardarCitas();
        System.out.println(" Cita registrada: " + c);
    }

    private static void listarCitas() {
        if (citas.isEmpty()) {
            System.out.println(" No hay citas registradas.");
        } else {
            System.out.println("\n--- Lista de Citas ---");
            for (Cita c : citas) {
                System.out.println(c);
            }
        }
    }

    // ===================== BD =====================
    private static int leerEntero() {
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                return num;
            } catch (NumberFormatException e) {
                System.out.print(" Ingrese un numero válido: ");
            }
        }
    }

    private static void guardarDoctores() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("doctores.csv"))) {
            for (Doctor d : doctores) {
                pw.println(d.getNombre() + "," + d.getEdad() + "," + d.getEspecialidad());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar doctores: " + e.getMessage());
        }
    }

    private static void guardarPacientes() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("pacientes.csv"))) {
            for (Paciente p : pacientes) {
                pw.println(p.getNombre() + "," + p.getEdad() + "," + p.getHistorialMedico());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    private static void guardarCitas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("citas.csv"))) {
            for (Cita c : citas) {
                pw.println(c.getFecha() + "," + c.getDoctor().getNombre() + "," + c.getPaciente().getNombre());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar citas: " + e.getMessage());
        }
    }

    private static void cargarDoctores() {
        doctores.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("doctores.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    String nombre = datos[0];
                    int edad = Integer.parseInt(datos[1]);
                    String especialidad = datos[2];
                    doctores.add(new Doctor(nombre, edad, especialidad));
                }
            }
        } catch (IOException e) {
            System.out.println("Archivo de doctores no encontrado, se creará nuevo.");
        }
    }

    private static void cargarPacientes() {
        pacientes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("pacientes.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    String nombre = datos[0];
                    int edad = Integer.parseInt(datos[1]);
                    String historial = datos[2];
                    pacientes.add(new Paciente(nombre, edad, historial));
                }
            }
        } catch (IOException e) {
            System.out.println("Archivo de pacientes no encontrado, se creará nuevo.");
        }
    }

    private static void cargarCitas() {
        citas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("citas.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    String fecha = datos[0];
                    String nombreDoc = datos[1];
                    String nombrePac = datos[2];

                    // buscar objetos existentes
                    Doctor doctor = doctores.stream()
                            .filter(d -> d.getNombre().equals(nombreDoc))
                            .findFirst().orElse(null);
                    Paciente paciente = pacientes.stream()
                            .filter(p -> p.getNombre().equals(nombrePac))
                            .findFirst().orElse(null);

                    if (doctor != null && paciente != null) {
                        citas.add(new Cita(doctor, paciente, fecha));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Archivo de citas no encontrado, se creará nuevo.");
        }
    }

}
