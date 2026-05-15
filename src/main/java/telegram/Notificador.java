package telegram;

// Define el contrato estándar para cualquier servicio de mensajería implementado en el sistema.
public interface Notificador {

    /*
    Método abstracto que requiere el identificador del receptor y el contenido en texto.
    Retorna un booleano indicando el éxito o fracaso de la transacción.
    */

    boolean enviarMensaje(String destinatario, String mensaje);
}
