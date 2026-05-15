package api;

/*
Almacena el nombre de un atleta, la categoría que lideró (ej. "Rebounds")
y el valor numérico alcanzado.
 */

public class JugadorLider {
    private String categoria;
    private String nombre;
    private String valor;

    public JugadorLider(String categoria, String nombre, String valor) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.valor = valor;
    }

    // Getters
    public String getCategoria() { return categoria; }
    public String getNombre() { return nombre; }
    public String getValor() { return valor; }

    @Override
    public String toString() {
        return String.format("- %s: %s (%s)", categoria, nombre, valor);
    }

    @Override
    public JugadorLider clone() {
        try {
            return (JugadorLider) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}