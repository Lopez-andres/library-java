package Vista;

import Modelo.Bibliotecario;
import Modelo.ImagenUniversidad;

import javax.swing.*;
import java.awt.*;

public class Admin extends JFrame{
    private JPanel panelPrincipal;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton verButton;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel imagenAdmin;
    private JPanel funcionalidadButtons;
    private JPanel funcionalidadCrud;
    private final String nombreAdmin = "admin";
    private final String passwordAdmin = "1234";

    public Admin() {
        ImagenUniversidad img = new ImagenUniversidad("src/universidad.png"); //creamos una instancia de la clase ImageMesa
        imagenAdmin.setLayout(new BorderLayout());
        imagenAdmin.add(img);

        inicializarForma();
        agregarButton.addActionListener(e -> Bibliotecario.agregarBibliotecario());
        verButton.addActionListener(e -> Bibliotecario.verBibliotecarios());
        actualizarButton.addActionListener(e -> Bibliotecario.actualizarBibliotecario());
        eliminarButton.addActionListener(e -> Bibliotecario.eliminarBibliotecario());
        regresarButton.addActionListener(_ -> {
            this.dispose();
            Login login = new Login();
            login.setVisible(true);
        });
        salirButton.addActionListener(_ -> System.exit(0));
    }

    public void inicializarForma(){
        setTitle("");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(610,490);
        setLocationRelativeTo(null);
    }
}
