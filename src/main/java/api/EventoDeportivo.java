package api;

import java.util.Objects;

// Clase base que abstrae las propiedades universales de cualquier acontecimiento deportivo.

public abstract class EventoDeportivo {

    protected String id;
    protected String fecha;
    protected String estado;

    public EventoDeportivo(String id, String fecha, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Métodos estándar
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoDeportivo that = (EventoDeportivo) o;
        return Objects.equals(id, that.id); // Dos eventos son iguales si tienen el mismo ID
    }

    @Override
    public abstract String toString(); // Obligamos a las clases hijas a implementar su propio toString

    @Override
    public EventoDeportivo clone() {
        try {
            return (EventoDeportivo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}