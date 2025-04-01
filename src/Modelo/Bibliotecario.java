package Modelo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bibliotecario {
    private static int contadorId = 1;
    public static List<Bibliotecario> listaBibliotecarios = new ArrayList<>();
    private String usuario;
    private String password;
    private int id;

    public Bibliotecario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
        this.id = contadorId++;
    }

    public int getId() {
        return id;
    }

    //  Crear bibliotecario
    public static void agregarBibliotecario() {
        String usuario = JOptionPane.showInputDialog(null, "Ingrese el nombre del bibliotecario:");
        String password = JOptionPane.showInputDialog(null, "Ingrese la contraseña del bibliotecario:");

        if (usuario != null && password != null && !usuario.isEmpty() && !password.isEmpty()) {
            // Verificar que usuario y contraseña no sean iguales
            if (usuario.equals(password)) {
                JOptionPane.showMessageDialog(null, "El usuario y la contraseña no pueden ser iguales.");
                return;
            }
            // Verificar que no haya un bibliotecario con el mismo usuario o contraseña
            for (Bibliotecario b : listaBibliotecarios) {
                if (b.getUsuario().equals(usuario)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un bibliotecario con esas credenciales.");
                    return;
                }
                if (b.getPasswordUsuario().equals(password)) {
                    JOptionPane.showMessageDialog(null, "La contraseña ya está siendo utilizada por otro bibliotecario.");
                    return;
                }
            }
            Bibliotecario nuevo = new Bibliotecario(usuario, password);
            listaBibliotecarios.add(nuevo);
            JOptionPane.showMessageDialog(null, "Bibliotecario agregado correctamente.");
            System.out.println("\n📥 Bibliotecario agregado:\n" + nuevo);
        } else {
            JOptionPane.showMessageDialog(null, "Datos inválidos. No se agregó el bibliotecario.");
        }
    }

    //  Leer todos los bibliotecarios
    public static void verBibliotecarios() {
        if (listaBibliotecarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay bibliotecarios registrados.");
            return;
        }

        StringBuilder textoPlano = new StringBuilder("📘 LISTA DE BIBLIOTECARIOS\n\n");

        for (Bibliotecario b : listaBibliotecarios) {
            textoPlano.append("ID: ").append(b.getId())
                    .append(" | Usuario: ").append(b.getUsuario())
                    .append(" | Contraseña: ").append(b.getPasswordUsuario())
                    .append("\n");
        }

        JTextArea areaTexto = new JTextArea(textoPlano.toString());
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        //areaTexto.setBackground(new Color(245, 245, 245));
        areaTexto.setBackground(Color.BLACK); // Fondo negro
        areaTexto.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setPreferredSize(new Dimension(400, 250));

        JOptionPane.showMessageDialog(null, scrollPane, "Listado de Bibliotecarios", JOptionPane.INFORMATION_MESSAGE);
        imprimirListaPorConsola();
    }

    // Actualizar usuario y contraseña por ID
    public static void actualizarBibliotecario() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del bibliotecario a actualizar:");
            int id = Integer.parseInt(idStr);

            for (Bibliotecario b : listaBibliotecarios) {
                if (b.getId() == id) {
                    String nuevoUsuario = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre de usuario:");
                    String nuevaPassword = JOptionPane.showInputDialog(null, "Ingrese la nueva contraseña:");

                    if (nuevoUsuario != null && nuevaPassword != null &&
                            !nuevoUsuario.isEmpty() && !nuevaPassword.isEmpty()) {
                        System.out.println("\n✏️ Actualizando Bibliotecario con ID: " + id);
                        b.setUsuario(nuevoUsuario);
                        b.setPasswordUsuario(nuevaPassword);
                        JOptionPane.showMessageDialog(null, "Bibliotecario actualizado correctamente.");
                        System.out.println("✅ Datos actualizados:\n" + b);
                    } else {
                        JOptionPane.showMessageDialog(null, "Datos inválidos. No se actualizó el bibliotecario.");
                    }
                    return;
                }
            }

            JOptionPane.showMessageDialog(null, "No se encontró el bibliotecario con ese ID.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    // Eliminar bibliotecario por ID
    public static void eliminarBibliotecario() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del bibliotecario a eliminar:");
            int id = Integer.parseInt(idStr);

            for (Bibliotecario b : listaBibliotecarios) {
                if (b.getId() == id) {
                    listaBibliotecarios.remove(b);
                    JOptionPane.showMessageDialog(null, "Bibliotecario eliminado correctamente.");
                    System.out.println("\n🗑️ Bibliotecario eliminado:\n" + b);
                    return;
                }
            }

            JOptionPane.showMessageDialog(null, "No se encontró el bibliotecario con ese ID.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    // Validación (por nombre y contraseña)
    public boolean validarBibliotecario(String usuario, String password) {
        return this.usuario.equals(usuario) && this.password.equals(password);
    }

    // toString personalizado
    @Override
    public String toString() {
        return "ID: " + id +
                " | Usuario: " + usuario +
                " | Contraseña: " + password;
    }

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String nuevoUsuario) {
        this.usuario = nuevoUsuario;
    }

    public String getPasswordUsuario() {
        return password;
    }

    public void setPasswordUsuario(String nuevaPassword) {
        this.password = nuevaPassword;
    }

    // Imprimir lista en consola
    public static void imprimirListaPorConsola() {
        System.out.println("\n\n\n📋 Estado actual de listaBibliotecarios:");
        if (listaBibliotecarios.isEmpty()) {
            System.out.println("⚠️ No hay bibliotecarios registrados.");
        } else {
            for (Bibliotecario b : listaBibliotecarios) {
                System.out.println(b);
            }
        }
        System.out.println("-----------------------------------------------------\n");
    }

}

