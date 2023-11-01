// Definición de la clase Calculadora.
public class Calculadora {

    // Método para realizar una suma.
    public int suma(int valor1, int valor2) {
        int resultado = 0;
        if (valor1 >= 0 && valor2 >= 0) { // Verifica que ambos valores sean no negativos.
            resultado = valor1 + valor2; // Realiza la suma.
        }
        return resultado; // Devuelve el resultado de la suma.
    }

    // Método para realizar una resta.
    public int resta(int valor1, int valor2) {
        int resultado = 0;
        if (valor1 >= 0 && valor2 >= 0) { // Verifica que ambos valores sean no negativos.
            resultado = valor1 - valor2; // Realiza la resta.
        }
        return resultado; // Devuelve el resultado de la resta.
    }

    // Método para realizar una multiplicación.
    public int mult(int valor1, int valor2) {
        int resultado = 0;
        if (valor1 >= 0 && valor2 >= 0) { // Verifica que ambos valores sean no negativos.
            resultado = valor1 * valor2; // Realiza la multiplicación.
        }
        return resultado; // Devuelve el resultado de la multiplicación.
    }

    // Método para realizar una división (aquí se debería manejar la división por cero).
    public int divi(int valor1, int valor2) {
        int resultado = 0;
        if (valor1 >= 0 && valor2 > 0) { // Verifica que valor1 sea no negativo y valor2 sea positivo.
            resultado = valor1 / valor2; // Realiza la división entera.
        }
        return resultado; // Devuelve el resultado de la división (puede ser cero si no se cumple la condición).
    }
}
