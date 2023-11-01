// La clase Indice se utiliza para representar un índice que mapea una clave a una posición en un archivo de registros.
class Indice {
    private String clave; // La clave que se mapea al registro.
    private long posicion; // La posición en el archivo donde se encuentra el registro correspondiente a la clave.

    // Constructor por defecto, no recibe argumentos.
    public Indice() {
        // Constructor por defecto, no realiza ninguna acción especial.
    }

    // Constructor que recibe una clave y una posición.
    public Indice(String clave, long posicion) {
        this.clave = clave;
        this.posicion = posicion;
    }

    // Método para obtener la clave del índice.
    public String getClave() {
        return clave;
    }

    // Método para establecer la clave del índice.
    public void setClave(String clave) {
        this.clave = clave;
    }

    // Método para establecer la posición del índice.
    public void setPosicion(long posicion) {
        this.posicion = posicion;
    }
}
