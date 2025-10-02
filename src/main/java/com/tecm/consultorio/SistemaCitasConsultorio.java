package com.tecm.consultorio;

import java.util.*;
import java.io.*;

public class SistemaCitasConsultorio {

    private static Scanner sc = new Scanner(System.in);

    // Listas para almacenar la información
    private static List<Doctor> doctores = new ArrayList<>();
    private static List<Paciente> pacientes = new ArrayList<>();
    private static List<Cita> citas = new ArrayList<>();
    private static List<Usuario> usuarios = new ArrayList<>();

    public static void clearScreen(int c) {
        for (int i = 0; i < c; i++) {
            System.out.println();
        }
    }

    public static void pause() {
        System.out.println("\nPresione ENTER para continuar...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static void main(String[] args) {
        cargarUsuarios();
        cargarDoctores();
        cargarPacientes();
        cargarCitas();

        System.out.println("===== Sistema de Citas del Consultorio =====");

        System.out.print("Ingrese usuario: ");
        String usuarioIngresado = sc.nextLine();

        System.out.print("Ingrese contraseña: ");
        String contrasenaIngresada = sc.nextLine();

// Buscar usuario
        Usuario user = usuarios.stream()
                .filter(u -> u.getNombreUsuario().equalsIgnoreCase(usuarioIngresado))
                .findFirst()
                .orElse(null);

        if (user == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

// Si la contraseña es "pendiente", pedir nueva
        if (user.getContrasena().equals("pendiente")) {
            System.out.println("Primera vez ingresando, por favor cree su contraseña:");
            String nuevaPass = sc.nextLine();
            user.setContrasena(nuevaPass);
            guardarUsuarios();
            System.out.println("Contraseña registrada. Inicie sesion nuevamente.");
            return;
        }

// Validar login
        if (user.validarLogin(contrasenaIngresada)) {
            switch (user.getRol()) {
                case "admin" ->
                    menuAdmin();
                case "doctor" ->
                    menuDoctor();
                case "cliente" ->
                    menuCliente();
            }
        } else {
            System.out.println("Contraseña incorrecta.");
        }
    }

    // menu
    private static void menuAdmin() {
        int opcion;
        do {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Registrar Doctor");
            System.out.println("2. Registrar Paciente");
            System.out.println("3. Crear Cita");
            System.out.println("4. Listar Citas");
            System.out.println("5. Listar Doctores");
            System.out.println("6. Listar Pacientes");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
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
                case 5 ->
                    listarDoctores();
                case 6 ->
                    listarPacientes();
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

        System.out.print("Ingrese historial medico: ");
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

    private static void listarDoctores() {
        if (doctores.isEmpty()) {
            System.out.println("No hay doctores registrados.");
        } else {
            System.out.println("\n--- Lista de Doctores ---");
            for (Doctor d : doctores) {
                System.out.println(d);
            }
        }
    }

    private static void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            System.out.println("\n--- Lista de Pacientes ---");
            for (Paciente p : pacientes) {
                System.out.println(p);
            }
        }
    }

    // BD 
    private static int leerEntero() {
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                return num;
            } catch (NumberFormatException e) {
                System.out.print(" Ingrese un numero valido: ");
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
            System.out.println("Archivo de doctores no encontrado, se creara nuevo.");
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
            System.out.println("Archivo de pacientes no encontrado, se creara nuevo.");
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
            System.out.println("Archivo de citas no encontrado, se creara nuevo.");
        }
    }
    // metodos login

    private static void cargarUsuarios() {
        usuarios.clear();
        File f = new File("usuarios.csv");
        if (!f.exists()) {
            System.out.println("Archivo usuarios.csv no encontrado. Se creara al guardar usuarios.");
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.isEmpty()) {
                        continue;
                    }
                    if (linea.toLowerCase().startsWith("nombreusuario") || linea.toLowerCase().startsWith("nombre_usuario")) {
                        continue;
                    }
                    String[] partes = linea.split(",", -1); // -1 para no perder campos vacíos
                    if (partes.length < 3) {
                        continue;
                    }
                    String nombreUsuario = partes[0].trim();
                    String rol = partes[1].trim();
                    String contrasena = partes[2].trim();
                    Usuario u = new Usuario(nombreUsuario, rol);
                    u.setContrasena(contrasena);
                    usuarios.add(u);
                }
            } catch (IOException e) {
                System.out.println("Error cargando usuarios: " + e.getMessage());
            }
        }
        // Asegurar que exista un admin por defecto
        boolean adminExiste = usuarios.stream().anyMatch(u -> "admin".equalsIgnoreCase(u.getRol()));
        if (!adminExiste) {
            Usuario admin = new Usuario("admin", "admin");
            admin.setContrasena("pendiente");
            usuarios.add(admin);
            guardarUsuarios();
            System.out.println("Se creó usuario admin por defecto (usuario: admin, contraseña: pendiente).");
        }
    }

    //  Guardar usuarios 
    private static void guardarUsuarios() {
        File f = new File("usuarios.csv");
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.println("nombreUsuario,rol,contrasena");
            for (Usuario u : usuarios) {
                String nombre = u.getNombreUsuario() == null ? "" : u.getNombreUsuario().replace(",", "");
                String rol = u.getRol() == null ? "" : u.getRol().replace(",", "");
                String pass = u.getContrasena() == null ? "" : u.getContrasena().replace(",", "");
                pw.println(nombre + "," + rol + "," + pass);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    // Buscar usuario por nombre
    private static Usuario buscarUsuarioPorNombre(String nombreUsuario) {
        if (nombreUsuario == null) {
            return null;
        }
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario() != null && u.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }

    // Actualizar contraseña
    private static void actualizarContrasenaUsuario(Usuario u, String nuevaContrasena) {
        if (u == null) {
            return;
        }
        u.setContrasena(nuevaContrasena);
        guardarUsuarios();
    }

}
