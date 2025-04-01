package Vista;

import Modelo.Libro;
import Modelo.UsuarioMora;
import Modelo.ImagenUniversidad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class Biblioteca extends JFrame {
    private JPanel panelPrincipal;
    private JButton registrarPrestamoButton;
    private JButton registrarDevolucionButton;
    private JButton verLibrosButton;
    private JButton verUsuariosEnMoraButton;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel imagenBiblioteca;
    private JPanel funcionalidadButtons;
    private JPanel funcionalidadAcciones;
    private ArrayList<UsuarioMora> listaUsuariosMora = new ArrayList<>();
    private ArrayList<Libro> libros;

    public Biblioteca() {
        ImagenUniversidad img2 = new ImagenUniversidad("src/biblioteca.png");
        imagenBiblioteca.setLayout(new BorderLayout());
        imagenBiblioteca.add(img2);

        inicializarForma();
        cargarLibros();

        registrarPrestamoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPrestamo();
            }
        });

        registrarDevolucionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDevolucion();
            }
        });

        verLibrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarLibrosPorCategoria();
            }
        });

        verUsuariosEnMoraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarUsuariosEnMora();
            }
        });

        salirButton.addActionListener(e -> System.exit(0));

        regresarButton.addActionListener(_ -> {
                this.dispose();
                Login login = new Login();
                login.setVisible(true);
        });
    }

    private void registrarPrestamo() {
        ArrayList<Libro> disponibles = new ArrayList<>();
        for (Libro l : libros) {
            if (!l.isPrestado()) {
                disponibles.add(l);
            }
        }

        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay libros disponibles para préstamo.");
            return;
        }

        JComboBox<String> comboLibros = new JComboBox<>();
        for (Libro l : disponibles) {
            comboLibros.addItem(l.getTitulo());
        }

        int seleccion = JOptionPane.showConfirmDialog(null, comboLibros, "Seleccione un libro para prestar", JOptionPane.OK_CANCEL_OPTION);
        if (seleccion == JOptionPane.OK_OPTION) {
            Libro libro = disponibles.get(comboLibros.getSelectedIndex());

            String nombrePersona = JOptionPane.showInputDialog("Nombre de la persona que toma el préstamo:");
            String fechaStr = JOptionPane.showInputDialog("Ingrese la fecha de préstamo (formato: AAAA-MM-DD):");
            try {
                LocalDate fechaPrestamo = LocalDate.parse(fechaStr);

                libro.setPrestado(true);
                libro.setPersonaPrestamo(nombrePersona);
                libro.setMultaPagada(false);
                libro.setPrecioMulta(0);
                libro.setFechaPrestamo(fechaPrestamo);

                JOptionPane.showMessageDialog(null, "Libro prestado correctamente a " + nombrePersona);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fecha inválida. Use el formato correcto (AAAA-MM-DD).");
            }
        }
    }

    private void registrarDevolucion() {
        ArrayList<Libro> prestados = new ArrayList<>();
        for (Libro l : libros) {
            if (l.isPrestado()) {
                prestados.add(l);
            }
        }

        if (prestados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay libros en préstamo actualmente.");
            return;
        }

        JComboBox<String> comboLibros = new JComboBox<>();
        for (Libro l : prestados) {
            comboLibros.addItem(l.getTitulo() + " (prestado a " + l.getPersonaPrestamo() + ")");
        }

        int seleccion = JOptionPane.showConfirmDialog(null, comboLibros, "Seleccione un libro a devolver", JOptionPane.OK_CANCEL_OPTION);
        if (seleccion == JOptionPane.OK_OPTION) {
            Libro libro = prestados.get(comboLibros.getSelectedIndex());

            String fechaStr = JOptionPane.showInputDialog("Ingrese la fecha de devolución (AAAA-MM-DD):");
            try {
                LocalDate fechaDevolucion = LocalDate.parse(fechaStr);
                LocalDate fechaPrestamo = libro.getFechaPrestamo();
                int dias = (int) java.time.temporal.ChronoUnit.DAYS.between(fechaPrestamo, fechaDevolucion);
                int multa = 0;
                boolean pagoMulta = true;

                // Guardar datos antes de limpiarlos
                String nombrePersona = libro.getPersonaPrestamo();
                String tituloLibro = libro.getTitulo();

                if (dias > 7) {
                    multa = (dias - 7) * 1000;
                    libro.setPrecioMulta(multa);

                    int pagar = JOptionPane.showConfirmDialog(null,
                            "El libro tiene " + (dias - 7) + " días de atraso.\nMulta: $" + multa + "\n¿Desea pagar la multa?",
                            "Pago de Multa", JOptionPane.YES_NO_OPTION);

                    pagoMulta = (pagar == JOptionPane.YES_OPTION);
                    libro.setMultaPagada(pagoMulta);

                    if (!pagoMulta) {
                        listaUsuariosMora.add(new UsuarioMora(nombrePersona, tituloLibro, multa));
                    }
                } else {
                    libro.setMultaPagada(true);
                    libro.setPrecioMulta(0);
                }

                // Limpiar datos del libro después de registrar al moroso
                libro.setPrestado(false);
                libro.setPersonaPrestamo(null);
                libro.setDiasPrestamo(0);
                libro.setFechaPrestamo(null);

                String mensaje = "Libro devuelto correctamente.\nDías de préstamo: " + dias + "\n";
                if (multa > 0) {
                    mensaje += "Multa: $" + multa + "\n";
                    if (!pagoMulta) {
                        mensaje += "⚠️ El usuario NO pagó la multa y fue agregado a la lista de morosos.";
                    }
                } else {
                    mensaje += "No hay multa.";
                }

                JOptionPane.showMessageDialog(null, mensaje);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fecha inválida. Use el formato correcto (AAAA-MM-DD).");
            }
        }
    }

    private void mostrarLibrosPorCategoria() {
        String[] categorias = {"Terror", "Novelas clásicas", "Ingeniería"};
        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione una categoría:",
                "Categorías", JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]);

        if (seleccion != null) {
            StringBuilder sb = new StringBuilder("Libros en categoría: " + seleccion + "\n");
            for (Libro libro : libros) {
                if (libro.getTitulo().toLowerCase().contains(seleccion.toLowerCase())) {
                    sb.append(libro.getTitulo()).append(" - ").append(libro.isPrestado() ? "Prestado" : "Disponible").append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    private void mostrarUsuariosEnMora() {
        if (listaUsuariosMora.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios en mora.");
        } else {
            StringBuilder sb = new StringBuilder("Usuarios en mora:\n");
            int index = 1;
            for (UsuarioMora usuario : listaUsuariosMora) {
                sb.append(index).append(". Nombre: ").append(usuario.getNombre())
                        .append(", Libro: ").append(usuario.getTituloLibro())
                        .append(", Multa: $").append(usuario.getMulta()).append("\n");
                index++;
            }

            JOptionPane.showMessageDialog(null, sb.toString());

            String input = JOptionPane.showInputDialog("Ingrese el número del usuario al que desea pagar la multa (o deje vacío para cancelar):");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int seleccion = Integer.parseInt(input);
                    if (seleccion >= 1 && seleccion <= listaUsuariosMora.size()) {
                        UsuarioMora usuarioSeleccionado = listaUsuariosMora.get(seleccion - 1);

                        int confirmar = JOptionPane.showConfirmDialog(null,
                                "¿Desea pagar la multa de $" + usuarioSeleccionado.getMulta() +
                                        " por el libro \"" + usuarioSeleccionado.getTituloLibro() + "\"?",
                                "Confirmar pago", JOptionPane.YES_NO_OPTION);

                        if (confirmar == JOptionPane.YES_OPTION) {
                            // Aquí se realiza el pago y se elimina al usuario de la lista
                            listaUsuariosMora.remove(usuarioSeleccionado);
                            JOptionPane.showMessageDialog(null, "Pago realizado con éxito. El usuario ya no está en mora.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Número de usuario no válido.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada no válida.");
                }
            }
        }
    }

    private void cargarLibros() {
        libros = new ArrayList<>();
        libros.add(new Libro("El Resplandor - Terror", "Stephen King"));
        libros.add(new Libro("Drácula - Terror", "Bram Stoker"));
        libros.add(new Libro("Frankenstein - Terror", "Mary Shelley"));
        libros.add(new Libro("It - Terror", "Stephen King"));
        libros.add(new Libro("La llamada de Cthulhu - Terror", "H.P. Lovecraft"));

        libros.add(new Libro("Don Quijote - Novelas clásicas", "Miguel de Cervantes"));
        libros.add(new Libro("Cien Años de Soledad - Novelas clásicas", "Gabriel García Márquez"));
        libros.add(new Libro("Orgullo y Prejuicio - Novelas clásicas", "Jane Austen"));
        libros.add(new Libro("Crimen y Castigo - Novelas clásicas", "Dostoyevski"));
        libros.add(new Libro("Madame Bovary - Novelas clásicas", "Flaubert"));

        libros.add(new Libro("Fund. de Circuitos - Ingeniería", "Alexander"));
        libros.add(new Libro("Álgebra Lineal - Ingeniería", "Lay"));
        libros.add(new Libro("Termodinámica - Ingeniería", "Cengel"));
        libros.add(new Libro("Estructuras - Ingeniería", "Hibbeler"));
        libros.add(new Libro("Cálculo - Ingeniería", "Stewart"));
    }

    public void inicializarForma() {
        setTitle("Biblioteca");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(710, 590);
        setLocationRelativeTo(null); // se centra la ventana
    }
}
