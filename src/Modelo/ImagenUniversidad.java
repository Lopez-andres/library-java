package Modelo;

import javax.swing.*;
import java.awt.*;

public class ImagenUniversidad extends JPanel {
    private final Image imagen;

    public ImagenUniversidad(String rutaImagen) {
        ImageIcon icono = new ImageIcon(rutaImagen);
        this.imagen = icono.getImage();
        setPreferredSize(new Dimension(250, 250));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 20, 15, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.RED);
            g.drawString("Imagen no encontrada", 10, 20);
        }
    }
}

