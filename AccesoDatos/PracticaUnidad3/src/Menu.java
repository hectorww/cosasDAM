import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public static void muestraErrorSQL(SQLException e) {

        System.err.println("SQL ERROR mensaje: " + e.getMessage());
        System.err.println("SQL Estado: " + e.getSQLState());
        System.err.println("SQL código especifico: " + e.getErrorCode());

    }

    public static void conexion(){
            String basedatos = "practicaunidad3";
            String host = "localhost";
            String port = "3306";
            String parAdic = "";
            String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
            String user = "root";
            String pwd = "admin";


            try (Connection c = DriverManager.getConnection(urlConnection, user, pwd)) {
                System.out.println("Conexión realizada.");
            } catch (SQLException e) {
                muestraErrorSQL(e);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

    }

    public static void main(String[] args) {

        conexion();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            // Mostrar el menú
            System.out.println("┌──────────────────────────┐");
            System.out.println("│      Menú Principal      │");
            System.out.println("├──────────────────────────┤");
            System.out.println("│      1. Ejercicio1       │");
            System.out.println("│      2. Ejercicio2       │");
            System.out.println("│      3. Ejercicio3       │");
            System.out.println("│      4. Salir            │");
            System.out.println("├──────────────────────────┤");
            System.out.println("│     Elija una opción:    │");
            System.out.println("└──────────────────────────┘");

            // Leer la opción del usuario
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    Ejercicio1.main(args);
                    break;
                case 2:
                    Ejercicio2.main(args);
                    break;
                case 3:
                    Ejercicio3.main(args);
                    break;
                case 4:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        } while (opcion != 4);

        scanner.close();
    }
}

