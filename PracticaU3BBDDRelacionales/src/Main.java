import java.sql.SQLException;
import java.util.Scanner;

public class Main {     //mostramos el menu en el main con diversas opciones

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            mostrarMenu();
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(scanner.nextLine()); //lee toda la linea y convierte en entero

            switch (opcion) {
                case 1:
                    Apartado1.IniciarMenu();
                    break;
                case 2:
                    Apartado2.IniciarMenu();
                    break;
                case 3:
                    try {
                        GestorProyectos.IniciarGestor();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 7:
                    mostrarCreditos();
                    break;
                case 8:
                    System.out.println("Saliendo del programa, adios!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion != 8);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n-------- Menú --------");
        System.out.println("1. Apartado 1");
        System.out.println("2. Apartado 2");
        System.out.println("3. Gestor de Proyectos");
        System.out.println("4. Apartado 4");
        System.out.println("5. Apartado 5");
        System.out.println("6. Apartado 6");
        System.out.println("7. Créditos");
        System.out.println("8. Salir del programa");
        System.out.println("-----------------------");
    }

    private static void mostrarCreditos() {
        System.out.println("\n------------ Créditos -------------");
        System.out.println("  Desarrollado por Marcos Alfonso");
        System.out.println("  2ºDAM Práctica 2 Acceso a Datos");
        System.out.println("-----------------------------------");
    }
}