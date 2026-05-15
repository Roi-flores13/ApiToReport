package api;

// Especialización de EventoDeportivo que modela un encuentro específico de baloncesto.
public class PartidoNBA extends EventoDeportivo {

    public static final String LIGA = "NBA"; // Constante (static final) propia de la clase

    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int asistencia;

    public PartidoNBA(String id, String fecha, String estado, Equipo equipoLocal, Equipo equipoVisitante, int asistencia) {
        super(id, fecha, estado); // Llamada al constructor de la clase padre
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.asistencia = asistencia;
    }

    // Getters + Setters
    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public int getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(int asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public String toString() {
        return String.format("🏀 Partido %s [%s] | %s vs %s | Estado: %s",
                LIGA, fecha, equipoLocal.getNombre(), equipoVisitante.getNombre(), estado);
    }

    @Override
    public PartidoNBA clone() {
        PartidoNBA clon = (PartidoNBA) super.clone();
        // Clonación profunda (Deep Copy) para los objetos agregados
        clon.equipoLocal = this.equipoLocal != null ? this.equipoLocal.clone() : null;
        clon.equipoVisitante = this.equipoVisitante != null ? this.equipoVisitante.clone() : null;
        return clon;
    }
}