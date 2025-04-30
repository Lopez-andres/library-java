package Modelo;

public class UsuarioMora {
    private final String nombre;
    private String tituloLibro;
    private final int multa;

    public UsuarioMora(String nombre, String tituloLibro, int multa) {
        this.nombre = nombre;
        this.tituloLibro = tituloLibro;
        this.multa = multa;
    }

    //getters y setters
    public String getNombre() {
        return nombre;
    }
    public String getTituloLibro() {
        return tituloLibro;
    }
    public int getMulta() {
        return multa;
    }

    //obtiene la representacion en texto del objeto UsuarioMora
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Libro: " + tituloLibro + ", Multa: $" + multa;
    }
}
