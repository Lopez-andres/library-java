package Vista;
import Modelo.Bibliotecario;
import Modelo.ImagenUniversidad;
import javax.swing.*;
import java.awt.*;

public class Admin extends JFrame{
    private JPanel panelPrincipal;
    private JButton agregarButton, actualizarButton, eliminarButton, verButton, regresarButton, salirButton;
    private JPanel imagenAdmin, funcionalidadButtons, funcionalidadCrud;

    public Admin() {
        ImagenUniversidad img = new ImagenUniversidad("src/universidad.png"); // Creamos una instancia de la clase ImagenUniversidad
        imagenAdmin.setLayout(new BorderLayout()); //configuramos el diseño de la imagenUniversidad
        imagenAdmin.add(img); //Lo añadimos al JPanel imagenAdmin

        /*
        Configura las propiedades de la ventana de administración.
        Establece el título, contenido, operación de cierre, tamaño y posición.
        */
        setTitle("Admin"); //titulo de la ventana
        setContentPane(panelPrincipal); //establecemos el panelPrincipal como lo que se vera en el JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE); //la aplicacion termina cuando el usuario cierra la ventana
        setSize(610,490); //damos el tamaño a la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Configuramos la funcionalidad de los botones
        agregarButton.addActionListener(e -> Bibliotecario.agregarBibliotecario());
        verButton.addActionListener(e -> Bibliotecario.verBibliotecarios());
        actualizarButton.addActionListener(e -> Bibliotecario.actualizarBibliotecario());
        eliminarButton.addActionListener(e -> Bibliotecario.eliminarBibliotecario());
        regresarButton.addActionListener(e -> {
            this.dispose();
            Login login = new Login();
            login.setVisible(true);
        });
        salirButton.addActionListener(_ -> System.exit(0));
    }
}
