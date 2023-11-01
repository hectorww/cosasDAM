// La clase Registro representa un objeto que contiene información sobre un registro en el archivo de registros.
public class Registro {
    private String codigo;    // El código asociado al registro.
    private String nombre;    // El nombre asociado al registro.
    private String apellido;  // El apellido asociado al registro.

    // Constructor que recibe el código, nombre y apellido del registro.
    public Registro(String codigo, String nombre, String apellido) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Método para obtener el código del registro.
    public String getCodigo() {
        return codigo;
    }

    // Método para establecer el código del registro.
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // Método para obtener el nombre del registro.
    public String getNombre() {
        return nombre;
    }

    // Método para obtener el apellido del registro.
    public String getApellido() {
        return apellido;
    }

    // Método estático para crear un objeto Registro a partir de una línea de texto.
    public static Registro parse(String linea) {
        if (linea.length() >= 44) {
            // Extrae el código, nombre y apellido de la línea de texto en el formato esperado.
            String codigo = linea.substring(0, 4).trim();
            String nombre = linea.substring(4, 19).trim();
            String apellido = linea.substring(19, 44).trim();
            return new Registro(codigo, nombre, apellido);
        }
        return null; // Devuelve null si la línea de texto no tiene el formato esperado.
    }

    // Método para obtener una representación en formato de cadena del registro.
    @Override
    public String toString() {
        return "Código: " + codigo + "\nNombre: " + nombre + "\nApellido: " + apellido;
    }
}
