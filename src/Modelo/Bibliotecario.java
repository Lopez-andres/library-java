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

    /*
      Metodo estatico para agregar un nuevo bibliotecario al sistema.
      Solicita al usuario ingresar nombre de usuario y contraseña mediante cuadros de diálogo.
      Realiza validaciones para asegurar que:
      - El usuario y contraseña no sean vacíos
      - El usuario y contraseña no sean iguales
      - No exista otro bibliotecario con el mismo usuario o contraseña
     */

    public static void agregarBibliotecario() {
        String usuario = JOptionPane.showInputDialog(null, "Ingrese el nombre del bibliotecario:");
        String password = JOptionPane.showInputDialog(null, "Ingrese la contraseña del bibliotecario:");

        //evalua que usuario y contraseña no sean vacios
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

            //agregamos un bibliotecario al sistema con los datos ingresados por el usuario
            Bibliotecario nuevo = new Bibliotecario(usuario, password);
            listaBibliotecarios.add(nuevo);

            JOptionPane.showMessageDialog(null, "Bibliotecario agregado correctamente.");
            System.out.println("\n📥 Bibliotecario agregado:\n" + nuevo);
        } else {
            JOptionPane.showMessageDialog(null, "Datos inválidos. No se agregó el bibliotecario.");
        }
    }

    /*
      Metodo estático para mostrar todos los bibliotecarios registrados en el sistema.
      Muestra la información en un cuadro de diálogo con formato de tabla.
      Si no hay bibliotecarios registrados, muestra un mensaje indicándolo.
      También imprime la lista en la consola para fines de depuración.
     */

    public static void verBibliotecarios() {
        //verifica si la lista esta vacia para mostrar un mensaje indicandolo
        if (listaBibliotecarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay bibliotecarios registrados.");
            return;
        }

        //preparamos el contenido del texto
        StringBuilder textoPlano = new StringBuilder("📘 LISTA DE BIBLIOTECARIOS\n\n");

        //Crea un texto bonito y ordenado con el ID, usuario y contraseña de cada bibliotecario.
        for (Bibliotecario b : listaBibliotecarios) {
            textoPlano.append("ID: ").append(b.getId())
                    .append(" | Usuario: ").append(b.getUsuario())
                    .append(" | Contraseña: ").append(b.getPasswordUsuario())
                    .append("\n");
        }

        //creamos un JTextArea con el contenido del texto y lo mostramos en un JScrollPane
        JTextArea areaTexto = new JTextArea(textoPlano.toString());
        areaTexto.setEditable(false);

        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaTexto.setBackground(Color.BLACK); // Fondo negro
        areaTexto.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setPreferredSize(new Dimension(400, 250));

        JOptionPane.showMessageDialog(null, scrollPane, "Listado de Bibliotecarios", JOptionPane.INFORMATION_MESSAGE);
        imprimirListaPorConsola();
    }

    /*
      Metodo estatico para actualizar la información de un bibliotecario existente.
      Solicita al usuario ingresar el ID del bibliotecario a actualizar y luego
      los nuevos valores para el nombre de usuario y contraseña.
      Realiza validaciones para asegurar que los nuevos datos sean válidos.
      Muestra mensajes de confirmación o error según corresponda.
     */

    public static void actualizarBibliotecario() {
        try {
            //Solicita el ID del bibliotecario
            String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del bibliotecario a actualizar:");
            int id = Integer.parseInt(idStr);

            //Busca el bibliotecario en la lista
            for (Bibliotecario b : listaBibliotecarios) {
                if (b.getId() == id) {

                    //Pide los nuevos datos del bibliotecario al usuario y valida que no sean vacios ni iguales
                    String nuevoUsuario = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre de usuario:");
                    String nuevaPassword = JOptionPane.showInputDialog(null, "Ingrese la nueva contraseña:");

                    // Verificar que no haya un bibliotecario con el mismo usuario o contraseña
                    for (Bibliotecario c : listaBibliotecarios) {
                        if (c.getUsuario().equals(nuevoUsuario)) {
                            JOptionPane.showMessageDialog(null, "Ya existe un bibliotecario con esas credenciales.");
                            return;
                        }
                        if (c.getPasswordUsuario().equals(nuevaPassword)) {
                            JOptionPane.showMessageDialog(null, "La contraseña ya está siendo utilizada por otro bibliotecario.");
                            return;
                        }
                    }

                    if (nuevoUsuario != null && nuevaPassword != null && !nuevoUsuario.isEmpty() && !nuevaPassword.isEmpty()) {
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

    /*
      Metodo estático para eliminar un bibliotecario del sistema.
      Solicita al usuario ingresar el ID del bibliotecario a eliminar.
      Si encuentra un bibliotecario con el ID especificado, lo elimina de la lista.
      Muestra mensajes de confirmación o error según corresponda.
     */

    public static void eliminarBibliotecario() {
        try {
            //Solicita el ID al usuario
            String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del bibliotecario a eliminar:");
            int id = Integer.parseInt(idStr);

            //Busca en la lista al bibliotecario con ese ID
            for (Bibliotecario b : listaBibliotecarios) {
                if (b.getId() == id) {

                    //Elimino al bibliotecario y lo notifico
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

    //Metodo para validar las credenciales de un bibliotecario.
    //Compara el nombre de usuario y contraseña proporcionados con los del objeto actual.

    public boolean validarBibliotecario(String usuario, String password) {
        return this.usuario.equals(usuario) && this.password.equals(password);
    }

    //representacion en texto de un objeto
    @Override
    public String toString() {
        return "ID: " + id +
                " | Usuario: " + usuario +
                " | Contraseña: " + password;
    }

    /*
     Metodo estático para imprimir la lista de bibliotecarios en la consola.
     Útil para depuración y seguimiento del estado del sistema.
     Si la lista está vacía, muestra un mensaje indicándolo.
     */

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

    //Getters y Setters

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

    public int getId() {
        return id;
    }
}
