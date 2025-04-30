package Vista;

import javax.swing.*;
import Modelo.Bibliotecario;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    private JPanel panelPrincipal;
    private JTextField campoUsuarioTexto;
    private JPasswordField passwordTexto;
    private JButton ingresarButton, modeButton, salirButton;
    private JLabel UsuarioJLabel, PasswordJLabel, LoginJLabel;
    private boolean modoOscuro = true; // Estado inicial en oscuro

    public Login() {
        //Inicializa y configura las propiedades de la ventana de login.
        setTitle("Login"); //titulo
        setContentPane(panelPrincipal); //lo que el JFrame debe de mostrar
        setDefaultCloseOperation(EXIT_ON_CLOSE); //la aplicacion se cierre correctamente
        setSize(410,490); //tamaño de la ventana
        setLocationRelativeTo(null); // Se centra la ventana

        ingresarButton.addActionListener(_ -> validar()); // Se asocia este botón con su respectiva validación
        modeButton.addActionListener(_ -> cambioColor()); // Se asocia este botón a cambiar el tema cuando se presione
        salirButton.addActionListener(_ -> System.exit(0)); //Boton con la logica de salir de la aplicacion
    }


    /**
     * Cambia entre el modo oscuro y claro utilizando la librería FlatLaf.
     * Actualiza la interfaz de usuario y restaura las fuentes personalizadas.
     */
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

    /**
     * Establece fuentes personalizadas para los componentes de texto.
     * Aplica una fuente monoespaciada a todos los elementos de la interfaz
     * para mantener una apariencia consistente.
     */
    private void restaurarFuente() {
        // Se define la fuente de cada componente
        Font fuente = new Font("Monospaced", Font.PLAIN, 20); // Fuente Monospaced, tamaño 20, estilo normal
        Font fuente2 = new Font("Monospaced", Font.BOLD, 30);
        LoginJLabel.setFont(fuente2);
        UsuarioJLabel.setFont(fuente);
        PasswordJLabel.setFont(fuente);
        campoUsuarioTexto.setFont(fuente);
        passwordTexto.setFont(fuente);
        ingresarButton.setFont(fuente);
        modeButton.setFont(fuente);
        salirButton.setFont(fuente);
    }

    /*
      Valida los datos ingresados en el login.
      Si son válidos, abre la ventana correspondiente según el tipo de usuario:
      - Si es administrador, abre la ventana de administrador
      - Si es bibliotecario, abre la ventana de biblioteca
      Muestra mensajes de error si las credenciales son incorrectas.
     */
    private void validar() {
        String usuario = campoUsuarioTexto.getText().trim();
        String password = new String(passwordTexto.getPassword()).trim();

        // Verificar si los campos están vacíos primero
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos antes de continuar.",
                    "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Determinar el tipo de usuario basado en el nombre de usuario es admin o bibliotecario
        if (usuario.equals("admin")) {// Validación para admin
            if (password.equals("1234")) {
                Admin ventanaAdmin = new Admin();
                ventanaAdmin.setVisible(true);
                this.dispose(); // cerrar la ventana actual si es necesario
            } else {
                mostrarMensaje("Contraseña incorrecta para el admin.");
            }
        } else {// Validación de bibliotecario en lista
            Bibliotecario bib = new Bibliotecario("usuario", "2006");
            Bibliotecario.listaBibliotecarios.add(bib);

            // Recorre la lista de bibliotecarios para validar credenciales
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

    //Se encarga de mostrar un mensaje emergente con el texto proporcionado.
    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        Login log = new Login();
        log.setVisible(true);
    }
}
