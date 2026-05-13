package telegram;

public interface Notificador {

    boolean enviarMensaje(String destinatario, String mensaje);
}
