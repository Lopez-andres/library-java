package Modelo;

import java.time.LocalDate;

public class Libro {
    private final String titulo;
    private String autor;
    private boolean prestado;
    private String personaPrestamo;
    private int diasPrestamo;
    private boolean multaPagada;
    private int precioMulta;
    private LocalDate fechaPrestamo;

    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    //getters y setters

    public String getTitulo() {
        return titulo;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }

    public String getPersonaPrestamo() {
        return personaPrestamo;
    }

    public void setPersonaPrestamo(String personaPrestamo) {
        this.personaPrestamo = personaPrestamo;
    }

    public void setMultaPagada(boolean multaPagada) {
        this.multaPagada = multaPagada;
    }

    public void setPrecioMulta(int precioMulta) {
        this.precioMulta = precioMulta;
    }

    public void setDiasPrestamo(int diasPrestamo) {
        this.diasPrestamo = diasPrestamo;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

}
