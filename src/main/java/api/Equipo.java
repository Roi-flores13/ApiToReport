package api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Equipo {

    /*
    Representa una franquicia deportiva que compite en un partido.
    Contiene asociaciones hacia sus estadísticas y sus jugadores destacados.
     */

    private String id;
    private String nombre;
    private String abreviacion;
    private int puntaje;
    private boolean esGanador;

    private Estadisticas estadisticas; // Usamos la clase Estadisticas como tipo de datos
    private List<JugadorLider> lideres; // Creamos una lista de todos los lideres de estadística

    // Constructor que inicializa los datos básicos y prepara una lista vacía para los líderes.
    public Equipo(String id, String nombre, String abreviacion, int puntaje, boolean esGanador) {
        this.id = id;
        this.nombre = nombre;
        this.abreviacion = abreviacion;
        this.puntaje = puntaje;
        this.esGanador = esGanador;
        this.lideres = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public Estadisticas getEstadisticas() { return estadisticas; }
    public void setEstadisticas(Estadisticas estadisticas) { this.estadisticas = estadisticas; }
    public List<JugadorLider> getLideres() {return this.lideres; }
    public void setLideres(List<JugadorLider> lideres){ this.lideres = lideres; }
    public int getPuntaje() { return this.puntaje; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return Objects.equals(id, equipo.id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" (").append(puntaje).append(" pts)\n");
        if (estadisticas != null) {
            sb.append("  ").append(estadisticas.toString()).append("\n");
        }
        if (!lideres.isEmpty()) {
            sb.append("  Líderes:\n");
            for (JugadorLider lider : lideres) {
                sb.append("    ").append(lider.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public Equipo clone() {
        try {
            Equipo clon = (Equipo) super.clone();
            clon.estadisticas = this.estadisticas != null ? this.estadisticas.clone() : null;
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}