import java.sql.*;
import java.util.Scanner;

public class Ejercicio1 {
    public static void ejecutarEjercicio() {
        Scanner sn1 = new Scanner(System.in); // permite introducir información al programa.

        // Datos que permiten la conexión a la base de datos.
        String bd = "ad_23_24"; // ad23_24 crear en casa
        String host = "localhost";
        String port = "3306";
        String parAdic = "";
        String URLConnection = "jdbc:mysql://" + host + ":" + port + "/" + bd + parAdic;
        String user = "root";
        String pwd = "admin"; // Cambiar a root.

        try (Connection c = DriverManager.getConnection(URLConnection, user, pwd); Statement s = c.createStatement()) {
            System.out.println("Conexión realizada.");

            // Verificar si la tabla CLIENTES ya existe y eliminarla si es necesario.
            DatabaseMetaData meta = c.getMetaData();
            ResultSet tabla = meta.getTables(null, null, "CLIENTES", null);

            var verdadero = false;

            if (tabla.next()) {
                verdadero = tabla.next();
            }

            if (verdadero) {
                // La tabla CLIENTES ya existe, la eliminamos.
                s.execute("DROP TABLE CLIENTES");
                System.out.println("Se ha eliminado la tabla CLIENTES.");
            } else {
                // La tabla CLIENTES no existe, créala.
                s.execute("CREATE TABLE CLIENTES (DNI CHAR(9) NOT NULL,"
                        + "APELLIDOS VARCHAR(32) NOT NULL, CP CHAR(5) DEFAULT NULL,"
                        + "PRIMARY KEY(DNI));");
                System.out.println("creamos tabla");
            }
            // Insertamos los datos de los clientes.
            int numFila = s.executeUpdate("INSERT INTO CLIENTES (DNI,APELLIDOS,CP) VALUES"
                    + "('78901234X','NADALES','44126'),"
                    + "('44905634V','HOJAS',null),"
                    + "('78901454B','SAMPER','29730'),"
                    + "('33901234K','LAMIQUIZ',null);");
            System.out.println(numFila + " " + "Filas insertadas.");

            // Realizar una consulta para contar las filas.
            int totalFilas = 0;
            String colsulta = "SELECT COUNT(*) AS totalFilas FROM CLIENTES";
            try (ResultSet rs = s.executeQuery(colsulta)) {
                if (rs.next()) {
                    totalFilas = rs.getInt("totalFilas");
                }
            }

            // Mostramos todos los clientes de la tabla CLIENTES.
            boolean terminado = false;
            int numeroFila = 1; // Inicializamos el número de fila.
            while (!terminado) {
                try (ResultSet rs = s.executeQuery("SELECT * FROM CLIENTES ORDER BY DNI")) {
                    // Desplazamos el cursor del ResultSet al número de fila actual
                    for (int i = 1; i < numeroFila; i++) {
                        rs.next();
                    }
                    // Obtener los datos del cliente actual.
                    if (rs.next()) {
                        System.out.println("Cliente " + numeroFila + ":");
                        System.out.println("DNI: " + rs.getString("DNI"));
                        System.out.println("Apellidos: " + rs.getString("APELLIDOS"));
                        System.out.println("CP: " + rs.getString("CP"));
                        System.out.println("---------------------------");

                        System.out.println("Comandos:");
                        System.out.println("Total de Clientes: " + totalFilas);
                        System.out.println("- \"k\" para ir al siguiente cliente.");
                        System.out.println("- \"d\" para ir al cliente anterior.");
                        System.out.println("- \"n\" para ir a un cliente específico.");
                        System.out.println("- \".\" para salir.");
                        System.out.print("Ingrese un comando: ");
                        String comando = sn1.nextLine();

                        if (comando.matches("[kdnt.]")) {
                            switch (comando) {
                                case ".":
                                    terminado = true;
                                    break;
                                case "k":
                                    numeroFila++; // Incrementar número de fila.
                                    break;
                                case "d":
                                    if (numeroFila > 1) {
                                        numeroFila--; // Decrementar número de fila.
                                    } else {
                                        System.out.println("No hay más registros por delante del actual.");
                                    }
                                    break;
                                case "n":
                                    System.out.print("Ingrese el número de fila al que desea ir: ");
                                    String numeroFilaStr = sn1.nextLine();

                                    try {
                                        int filaDeseada = Integer.parseInt(numeroFilaStr);

                                        if (filaDeseada >= 1 && filaDeseada <= totalFilas) {
                                            numeroFila = filaDeseada;
                                        } else {
                                            System.out.println("La fila especificada no existe.");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Ingrese un número válido para la fila.");
                                    }
                                    break;
                                default:
                                    try {
                                        int numero = Integer.parseInt(comando);
                                        // Verificar si el número está dentro del rango de filas válidas.
                                        if (numero >= 1 && numero <= totalFilas) {
                                            numeroFila = numero;
                                        } else {
                                            System.out.println("Fila no válida. Introduzca un número válido.");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Comando no válido.");
                                    }
                                    break;
                            }
                        } else {
                            System.out.println("Comando no válido. Los comandos válidos son \"k\", \"d\", \"n\", y \".\".");
                        }
                    } else {
                        System.out.println("No hay más registros por delante del actual.");
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    // Metodo para gestionar las excepciones.
    public static void muestraErrorSQL(SQLException e) {
        System.err.println("SQL ERROR mensaje: " + e.getMessage());
        System.err.println("SQL ESTADO: " + e.getSQLState());
        System.err.println("SQL codigo especifico: " + e.getErrorCode());
    }
}
