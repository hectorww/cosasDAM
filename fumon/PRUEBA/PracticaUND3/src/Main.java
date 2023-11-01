
import java.util.*;
// JDK - 21
public class Main {
    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in); //permite introducir informacion al programa.
        boolean salir = false;
        int opcion;
        while (!salir) {
            System.out.println("--------------------------------------");
            System.out.println("--PRACTICA ACCESO A DATOS: UNIDAD 03--");
            System.out.println("1. Ejercicio: 1");
            System.out.println("2. Ejercicio: 2");
            System.out.println("3. Ejercicio: 3");
            System.out.println("4. Ejercicio: 4");
            System.out.println("5. Ejercicio: 5");
            System.out.println("6. Ejercicio: 6");
            System.out.println("7. Ejercicio: 7");
            System.out.println("8. Salir");

            try {
                System.out.print("Introduce un numero: ");
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("--EJERCICIO 1--");
                        Ejercicio1.ejecutarEjercicio();
                        break;
                    case 2:
                        System.out.println("--EJERCICIO 2--");
                        Ejercicio2.ejecutarEjercicio2();
                        break;
                    case 3:
                        System.out.println("--EJERCICIO 3--");
                        break;
                    case 4:
                        System.out.println("--EJERCICIO 4--");

                        break;
                    case 5:
                        System.out.println("--EJERCICIO 5--");

                        break;
                    case 6:
                        System.out.println("--EJERCICIO 6--");

                        break;
                    case 7:
                        System.out.println("--EJERCICIO 7--");
                        break;
                    case 8:
                        salir = true; // Logica para salir del programa.
                        break;
                    default:
                        System.out.println("Las opciones son entre 1 y 8 , por favor vualva a introducirlo");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes introducir un numero entre 1 y 8");
                sn.next();
            }
        }
        System.out.println("Gracias vuelva pronto,FIN!!");
    }
}