package telegram;

import api.Equipo;
import api.JugadorLider;
import api.PartidoNBA;

import java.util.List;

// Clase estática enfocada en la presentación (capa de vista en texto).
public class ReporteGenerator {

    public static String generarResumenTelegram(List<PartidoNBA> partidos) {

        /*
        Recibe la colección de datos, itera sobre ella y utiliza un StringBuilder para construir
        una cadena con formato de marcado HTML enriquecido (negritas, cursivas),
        lista para ser renderizada nativamente por los clientes de Telegram.
         */

        if (partidos == null || partidos.isEmpty()) {
            return "No hay partidos registrados para la fecha solicitada.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("🏀 <b>RESUMEN DE LA JORNADA NBA</b> 🏀\n\n");

        for (PartidoNBA partido : partidos) {
            Equipo local = partido.getEquipoLocal();
            Equipo visitante = partido.getEquipoVisitante();

            sb.append("<b>").append(local.getNombre()).append(" (").append(local.getPuntaje()).append(")</b>");
            sb.append(" vs ");
            sb.append("<b>").append(visitante.getNombre()).append(" (").append(visitante.getPuntaje()).append(")</b>\n");

            sb.append("<i>Líderes Locales:</i>\n");
            formatearLideres(sb, local.getLideres());

            sb.append("<i>Líderes Visitantes:</i>\n");
            formatearLideres(sb, visitante.getLideres());

            sb.append("-----------------------------------\n");
        }

        return sb.toString();
    }

    private static void formatearLideres(StringBuilder sb, List<JugadorLider> lideres) {
        if (lideres != null && !lideres.isEmpty()) {
            for (JugadorLider lider : lideres) {
                // Solo incluimos Puntos, Rebotes y Asistencias para no hacer el mensaje tan largo
                if (lider.getCategoria().equals("Points") ||
                        lider.getCategoria().equals("Rebounds") ||
                        lider.getCategoria().equals("Assists")) {
                    sb.append("  • ").append(lider.getCategoria()).append(": ").append(lider.getNombre())
                            .append(" (").append(lider.getValor()).append(")\n");
                }
            }
        } else {
            sb.append("  • Sin datos de líderes.\n");
        }
    }
}