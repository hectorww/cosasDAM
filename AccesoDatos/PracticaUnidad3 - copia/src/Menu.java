import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("----- MENÚ PRINCIPAL -----");
            System.out.println("1. Método 1");
            System.out.println("2. Método 2");
            System.out.println("3. Método 3");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    Ejercicio1.main(args);
                    break;
                case 2:
                    Ejercicio2.main(args);
                    break;
                case 3:
                    GestorProyectos.main(args);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
    }
}

