package Vista;

import Modelo.Libro;
import Modelo.UsuarioMora;
import Modelo.ImagenUniversidad;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Biblioteca extends JFrame {
    private JPanel panelPrincipal;
    private JButton registrarPrestamoButton, registrarDevolucionButton, verLibrosButton;
    private JButton verUsuariosEnMoraButton, regresarButton, salirButton;
    private JPanel imagenBiblioteca, funcionalidadButtons, funcionalidadAcciones;
    private ArrayList<UsuarioMora> listaUsuariosMora = new ArrayList<>();
    private ArrayList<Libro> libros;

    public Biblioteca() {
        ImagenUniversidad img2 = new ImagenUniversidad("src/biblioteca.png");
        imagenBiblioteca.setLayout(new BorderLayout()); //configuramos el diseño de la imagen en un layout
        imagenBiblioteca.add(img2); //agregamos la direccion de esta imagen

        inicializarForma(); //inicializamos las dimensiones de la ventana
        cargarLibros(); //actualizamos los libros que hay en la biblioteca

        registrarPrestamoButton.addActionListener(e -> registrarPrestamo());
        registrarDevolucionButton.addActionListener(e -> registrarDevolucion());
        verLibrosButton.addActionListener(e -> mostrarLibrosPorCategoria());
        verUsuariosEnMoraButton.addActionListener(e -> mostrarUsuariosEnMora());
        salirButton.addActionListener(e -> System.exit(0));
        regresarButton.addActionListener(_ -> {
                this.dispose();
                Login login = new Login();
                login.setVisible(true);
        });
    }

    /*
     Configura las propiedades de la ventana grafica de la biblioteca.
     Establece el título, contenido, operación de cierre, tamaño y posición.
     */

    public void inicializarForma() {
        setTitle("Biblioteca"); //titulo a la ventana
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(710, 590);
        setLocationRelativeTo(null); // Se centra la ventana
    }

    /*
     Inicializa la lista de libros de la biblioteca.
     Crea objetos Libro para diferentes categorías (Terror, Novelas clásicas, Ingeniería)
     y los añade a la lista de libros disponibles.
     */

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

    /*
      Muestra los libros disponibles por categoría.
      Permite al usuario seleccionar una categoría de libro y
      muestra todos los libros que pertenecen a esa categoría,
      indicando si están disponibles o prestados.
     */

    private void mostrarLibrosPorCategoria() {
        //definimos un array de tipo String con las catagorias
        String[] categorias = {"Terror", "Novelas clásicas", "Ingeniería"};

        //cuadro de dialogo que permite al usuario seleccionar la categoria
        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione una categoría:",
                "Categorías", JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]);

        //verifica que el usuario halla seleccionado una categoria
        if (seleccion != null) {

            //construir el mensaje que mostrará los libros de la categoría seleccionada.
            StringBuilder sb = new StringBuilder("Libros en categoría: " + seleccion + "\n");

            //recorreomos la lista de libros para Verifica si el título del libro contiene la categoría seleccionada.
            for (Libro libro : libros) {
                //pasamos el titulo a minuscula para comparaciones y contains() verificamos que la categoria este contenida en el titulo del libro.
                if (libro.getTitulo().toLowerCase().contains(seleccion.toLowerCase())) {
                    //agregar al mensaje si esta prestado o disponible usando operador ternario
                    sb.append(libro.getTitulo()).append(" - ").append(libro.isPrestado() ? "Prestado" : "Disponible").append("\n");
                }
            }
            //finalmente es mostrado
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    /*
     Registra el préstamo de un libro a un usuario.
     Muestra un diálogo para seleccionar un libro disponible,
     solicita el nombre del usuario y la fecha de préstamo,
     y actualiza el estado del libro.
     */

    private void registrarPrestamo() {
        //creamos una lista con los libros disponibles (que no se han prestado)
        ArrayList<Libro> disponibles = new ArrayList<>();
        for (Libro l : libros) { //recorremos la lista de libros y verificamos que no se haya prestado
            if (!l.isPrestado()) {
                disponibles.add(l); //lo añadimos a la lista de libros disponibles si no se ha prestado previamente
            }
        }

        // Si no hay libros disponibles, mostrar mensaje y salir
        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay libros disponibles para préstamo.");
            return;
        }

        JComboBox<String> comboLibros = new JComboBox<>();//creamos un JComboBox con los libros disponibles

        for (Libro l : disponibles) {
            comboLibros.addItem(l.getTitulo()); //añadimos el titulo del libro a la lista del JComboBox
        }

        // Mostramos un cuadro de diálogo para seleccionar un libro
        //seleccion en int porque los JoptionPane devuelven 0 para ok, 2 si salio con la x y -1 si dio cancelar
        int seleccion = JOptionPane.showConfirmDialog(null, comboLibros, "Seleccione un libro para prestar", JOptionPane.OK_CANCEL_OPTION);

        if (seleccion == JOptionPane.OK_OPTION) {
            //obtenemos el indice del libro seleccionado en el JComboBox
            Libro libro = disponibles.get(comboLibros.getSelectedIndex());

            //pedimos el nombre y fecha de la persona que tomara el prestamo
            String nombrePersona = JOptionPane.showInputDialog("Nombre de la persona que toma el préstamo:");

            if (nombrePersona == null || nombrePersona.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Datos incorrectos para el usuario, agregue el nombre completo");
                return; //cierra el JOptionPane si el nombre de Usuario esta vacio
            }

            String fechaStr = JOptionPane.showInputDialog("Ingrese la fecha de préstamo (formato: AAAA-MM-DD):");

            if (fechaStr == null || fechaStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Datos incorrectos para la fecha. Use el formato correcto (AAAA-MM-DD).");
                return; //cierra el JOptionPane si la fecha esta vacia
            }

            try {
                //convertimos la fecha a LocalDate y lo guardamos en la variable fechaPrestamo  
                LocalDate fechaPrestamo = LocalDate.parse(fechaStr);

                //Registrar el préstamo en el libro
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

    /*
     Registra la devolución de un libro prestado.
     Muestra un diálogo para seleccionar un libro prestado,
     solicita la fecha de devolución, calcula si hay multa por retraso,
     y actualiza el estado del libro y la lista de usuarios en mora si corresponde.
     */

    private void registrarDevolucion() {
        //Crear una lista con los libros actualmente prestados

        ArrayList<Libro> prestados = new ArrayList<>();
        for (Libro l : libros) {
            if (l.isPrestado()) {
                prestados.add(l); //lo añadimos a la lista de libros prestados si se ha prestado previamente
            }
        }

        // Si no hay libros prestados, mostrar mensaje y salir
        if (prestados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay libros en préstamo actualmente.");
            return;
        }

        // Crear JComboBox con los títulos de los libros prestados (incluyendo a quién se prestaron)
        JComboBox<String> comboLibros = new JComboBox<>();

        for (Libro l : prestados) {
            comboLibros.addItem(l.getTitulo() + " (prestado a " + l.getPersonaPrestamo() + ")");
        }

        // Mostrar cuadro de diálogo para seleccionar libro a devolver
        int seleccion = JOptionPane.showConfirmDialog(null, comboLibros, "Seleccione un libro a devolver", JOptionPane.OK_CANCEL_OPTION);
        if (seleccion == JOptionPane.OK_OPTION) {

            //Obtenemos el libro seleccionado en el JComboBox y lo guardamos en la variable libro
            Libro libro = prestados.get(comboLibros.getSelectedIndex());

            //Obtenemos la fecha de devolucion
            String fechaStr = JOptionPane.showInputDialog("Ingrese la fecha de devolución (AAAA-MM-DD):");
            if (fechaStr == null || fechaStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese una fecha de devolucion!");
                return;
            }
            try {
                //convierte las cadenas de texto a un objeto localdate
                LocalDate fechaDevolucion = LocalDate.parse(fechaStr);
                LocalDate fechaPrestamo = libro.getFechaPrestamo();

                // Validar que la fecha de devolución no sea anterior a la de préstamo
                if (fechaDevolucion.isBefore(fechaPrestamo)) {
                    JOptionPane.showMessageDialog(null, "⚠️ La fecha de devolución no puede ser anterior a la fecha de préstamo (" + fechaPrestamo + ").");
                    return;
                }

                //calcula la diferencia entre la fecha de prestamo y devolucion
                int dias = (int) java.time.temporal.ChronoUnit.DAYS.between(fechaPrestamo, fechaDevolucion);

                int multa = 0;
                boolean pagoMulta = true;

                // Guardar datos antes de limpiarlos
                String nombrePersona = libro.getPersonaPrestamo();
                String tituloLibro = libro.getTitulo();

                // Calcular multa si hay más de 7 días de préstamo
                if (dias > 7) {
                    int diasAtraso = dias - 7;
                    multa = diasAtraso * 1000;
                    libro.setPrecioMulta(multa);

                    int pagar = JOptionPane.showConfirmDialog(null,
                            "El libro estuvo prestado por " + dias + " días.\nTiene " + diasAtraso + " días de atraso.\nMulta: $" + multa + "\n¿Desea pagar la multa?",
                            "Pago de Multa", JOptionPane.YES_NO_OPTION);


                    //si se paga la multa, se guarda en la variable pagoMulta y se limpia la lista de usuarios en mora
                    pagoMulta = (pagar == JOptionPane.YES_OPTION);
                    libro.setMultaPagada(pagoMulta);

                    // Si no paga la multa, agregar a lista de usuarios morosos
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

                // Preparar mensaje de confirmación
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
                // Si ocurre un error al interpretar la fecha
                JOptionPane.showMessageDialog(null, "Fecha inválida. Use el formato correcto (AAAA-MM-DD).");
            }
        }
    }

    /*
     Muestra la lista de usuarios en mora y permite pagar multas.
     Si hay usuarios en mora, muestra sus datos (nombre, libro, monto de multa)
     y permite seleccionar uno para pagar su multa.
     Si no hay usuarios en mora, muestra un mensaje indicándolo.
     */

    private void mostrarUsuariosEnMora() {
        //verifica si hay usuarios en mora y muestra el mensaje correspondiente.
        if (listaUsuariosMora.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios en mora.");
            return;
        } else {
            //contruimos un mensaje con los datos de los usuarios en mora.
            StringBuilder sb = new StringBuilder("Usuarios en mora:\n");
            int index = 1; //enumeramos cada usuario en mora

            //se recorre la lista de usuarios en mora y se agregan los datos al mensaje
            for (UsuarioMora usuario : listaUsuariosMora) {
                sb.append(index).append(". Nombre: ").append(usuario.getNombre())
                        .append(", Libro: ").append(usuario.getTituloLibro())
                        .append(", Multa: $").append(usuario.getMulta()).append("\n");
                index++;
            }

            //mostramos el mensaje con los datos de los usuarios en mora.
            JOptionPane.showMessageDialog(null, sb.toString());

            //cuadro de dialogo para ingresar el usuario al que desea pagar la multa
            String input = JOptionPane.showInputDialog("Ingrese el número del usuario al que desea pagar la multa (o deje vacío para cancelar):");

            //verificar que se halla sido ingresado el id correspondiente al usuario en mora
            if (input != null && !input.trim().isEmpty()) {
                try {
                    //convertimos el input a un entero y verificamos que sea mayor que 0 y menor que la cantidad de usuarios en mora
                    int seleccion = Integer.parseInt(input);
                    if (seleccion >= 1 && seleccion <= listaUsuariosMora.size()) {
                        //restamos uno recordando que las listas son 0-indexed
                        UsuarioMora usuarioSeleccionado = listaUsuariosMora.get(seleccion - 1);

                        //confirmamos el pago de la multa y la eliminamos de la lista de usuarios en mora si se paga.
                        int confirmar = JOptionPane.showConfirmDialog(null,
                                "¿Desea pagar la multa de $" + usuarioSeleccionado.getMulta() +
                                        " por el libro \"" + usuarioSeleccionado.getTituloLibro() + "\"?",
                                "Confirmar pago", JOptionPane.YES_NO_OPTION);

                        if (confirmar == JOptionPane.YES_OPTION) {
                            // Aquí se realiza el pago y se elimina al usuario de la lista
                            listaUsuariosMora.remove(usuarioSeleccionado);
                            JOptionPane.showMessageDialog(null, "Pago realizado con éxito. El usuario ya no está en mora.");
                        } else{
                            JOptionPane.showMessageDialog(null, "Pago No realizado. El usuario sigue en mora.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Número de usuario no ingresado.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada no válida.");
                }
            }
            else{
                return;
            }
        }
    }
}
