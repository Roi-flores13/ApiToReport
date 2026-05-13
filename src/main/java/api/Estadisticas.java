package api;

public class Estadisticas implements Cloneable {
    private int rebotes;
    private int asistencias;
    private int puntos;
    private int tirosDeTres;

    // Constructores, Getters y Setters...
    public Estadisticas(int rebotes, int asistencias, int puntos, int tirosDeTres){
        setRebotes(rebotes);
        setAsistencias(asistencias);
        setPuntos(puntos);
        setTirosDeTres(tirosDeTres);
    }


    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getTirosDeTres() {
        return tirosDeTres;
    }

    public void setTirosDeTres(int tirosDeTres) {
        this.tirosDeTres = tirosDeTres;
    }

    @Override
    public String toString() {
        return String.format("PTS: %d | REB: %d | AST: %d | 3PM: %d", puntos, rebotes, asistencias, tirosDeTres);
    }

    @Override
    public Estadisticas clone() {
        try {
            return (Estadisticas) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}