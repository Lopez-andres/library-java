package Modelo;

public class UsuarioMora {
    private String nombre;
    private String tituloLibro;
    private int multa;

    public UsuarioMora(String nombre, String tituloLibro, int multa) {
        this.nombre = nombre;
        this.tituloLibro = tituloLibro;
        this.multa = multa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public int getMulta() {
        return multa;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Libro: " + tituloLibro + ", Multa: $" + multa;
    }
}
