package Vista;

import javax.swing.*;
import Modelo.Bibliotecario;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;

public class Login extends JFrame{
    private JPanel panelPrincipal;
    private JTextField campoUsuarioTexto;
    private JPasswordField passwordTexto;
    private JButton ingresarButton, modeButton;
    private JLabel UsuarioJLabel, PasswordJLabel, LoginJLabel ;

    private boolean modoOscuro = true; // Estado inicial en oscuro

    public Login() {
        inicializarForma();
        ingresarButton.addActionListener(_ -> validar()); //Se asocia este boton con su respectiva validacion
        modeButton.addActionListener(_ -> cambioColor()); //Se asocia este boton a cambiar el tema cuando se presione
    }

    private void cambioColor() {
        try {
            if (modoOscuro) {
                UIManager.setLookAndFeel(new FlatLightLaf()); // Modo claro
            } else {
                UIManager.setLookAndFeel(new FlatDarculaLaf()); // Modo oscuro
            }
            modoOscuro = !modoOscuro; // Alternar estado

            SwingUtilities.updateComponentTreeUI(this); // Aplicar cambios y actualizar la interfaz
            restaurarFuente(); // Volver a aplicar la fuente
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void restaurarFuente() {
        //se define la fuente de cada componente
        Font fuente = new Font("Monospaced", Font.PLAIN, 20); // Fuente Monospaced, tamaño 20, estilo normal
        Font fuente2 = new Font("Monospaced", Font.BOLD, 30);
        LoginJLabel.setFont(fuente2);
        UsuarioJLabel.setFont(fuente);
        PasswordJLabel.setFont(fuente);
        campoUsuarioTexto.setFont(fuente);
        passwordTexto.setFont(fuente);
        ingresarButton.setFont(fuente);
        modeButton.setFont(fuente);
    }

    private void validar() {
        String usuario = campoUsuarioTexto.getText().trim();
        String password = new String(passwordTexto.getPassword()).trim();

        // Verificar si los campos están vacíos primero
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos antes de continuar.",
                    "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Determinar el tipo de usuario basado en el nombre de usuario
        if (usuario.equals("admin")) {// Validación para admin
            if (password.equals("1234")) {
                Admin ventanaAdmin = new Admin();
                ventanaAdmin.setVisible(true);
                this.dispose(); // cerrar la ventana actual si es necesario
            } else {
                mostrarMensaje("Contraseña incorrecta para el admin.");
            }
        } else {// Validación de bibliotecario en lista
            Bibliotecario bib = new Bibliotecario("andres", "2006");
            Bibliotecario.listaBibliotecarios.add(bib);

            boolean encontradoBibliotecario = false;
            for (Bibliotecario b : Bibliotecario.listaBibliotecarios) {
                if (b.validarBibliotecario(usuario, password)) {
                    System.out.println(Bibliotecario.listaBibliotecarios);
                    JOptionPane.showMessageDialog(this, "Bienvenido, Bibliotecario " + b.getUsuario());
                    Biblioteca ventanaBiblioteca = new Biblioteca();
                    ventanaBiblioteca.setVisible(true);
                    this.dispose();
                    encontradoBibliotecario = true;
                    break;
                }
            }

            // Si no se encontró ningún bibliotecario
            if (!encontradoBibliotecario) {
                mostrarMensaje("Usuario o contraseña de bibliotecario incorrectos.");
            }
        }
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void inicializarForma (){
        setTitle("Login");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(410,490);
        setLocationRelativeTo(null); //se centra la ventana
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        Login log = new Login();
        log.setVisible(true);
    }
}
