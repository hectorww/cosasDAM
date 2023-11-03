import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("----- MENÚ PRINCIPAL -----");
            System.out.println("1. Ejercicio 1");
            System.out.println("2. Ejercicio 2");
            System.out.println("3. Ejercicio 3");
            System.out.println("4. PruebaGestorProyectos");
            System.out.println("5. Ejercicio4");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 0:
                    System.out.println("Eliminando tablas y saliendo...");
                    GestorProyectos.borrarTablas();
                    System.exit(0);
                    break;
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
                    PruebaGestorProyectos.ejecutarPrueba();
                    break;
                case 5:
                    Ejercicio4.main(args);
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
    }

    public static class PruebaGestorProyectos {

        public static void ejecutarPrueba() {
            try {
                System.out.println("Configurando base de datos...");
                GestorProyectos.setupDatabase();
                System.out.println("Base de datos configurada correctamente.");

                System.out.println("Creando empleados...");
                GestorProyectos.nuevoEmpleado("123456789", "Juan Pérez");
                GestorProyectos.nuevoEmpleado("987654321", "Ana García");
                GestorProyectos.nuevoEmpleado("111222333", "Luis Torres");
                System.out.println("Empleados creados correctamente.");

                System.out.println("Creando proyectos...");
                int proyecto1 = GestorProyectos.nuevoProyecto("Proyecto A", "123456789", Date.valueOf("2023-01-01"), null);
                int proyecto2 = GestorProyectos.nuevoProyecto("Proyecto B", "987654321", null, Date.valueOf("2023-12-31"));
                int proyecto3 = GestorProyectos.nuevoProyecto("Proyecto C", "111222333", Date.valueOf("2023-02-01"), Date.valueOf("2023-10-31"));
                System.out.println("Proyectos creados correctamente.");

                System.out.println("Asignando empleados a proyectos...");
                GestorProyectos.asignaEmpAProyecto("123456789", proyecto1, Date.valueOf("2023-01-02"), Date.valueOf("2023-11-30"));
                GestorProyectos.asignaEmpAProyecto("987654321", proyecto2, Date.valueOf("2023-02-01"), null);
                GestorProyectos.asignaEmpAProyecto("111222333", proyecto3, Date.valueOf("2023-02-15"), Date.valueOf("2023-09-15"));
                System.out.println("Asignaciones realizadas correctamente.");

            } catch (SQLException e) {
                GestorProyectos.muestraErrorSQL(e);
            }
        }
    }
}
