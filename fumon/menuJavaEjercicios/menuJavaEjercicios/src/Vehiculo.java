// Definición de la clase Vehiculo.
public class Vehiculo {
    // Atributos que almacenan información sobre el vehículo.
    String marca;
    String modelo;
    String color;
    String cilindrada;
    String puertas;
    String combustible;

    // Método para encender el vehículo.
    public void enciende() {
        System.out.println("El vehículo está encendido, run run !!");
    }

    // Método para acelerar el vehículo.
    public void acelera() {
        System.out.println("El vehículo está acelerando, velocímetro a 110 km/h ");
    }

    // Método para frenar el vehículo.
    public void frena() {
        System.out.println("El vehículo está frenando, velocímetro a 0 km/h");
    }

    // Método para la autodestrucción del vehículo (nota: esto es una función ficticia).
    public void autodestruccion() {
        System.out.println("El vehículo se va a destruir, ¡corre! 5, 4, 3, 2, 1... ¡KBOOMMM!");
    }
}
