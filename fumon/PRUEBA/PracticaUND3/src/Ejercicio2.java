import java.sql.*;
import java.util.Scanner;

public class Ejercicio2 {
    public static void ejecutarEjercicio2() {
        Scanner sn1 = new Scanner(System.in); // permite introducir información al programa.

        // Datos que permiten la conexión a la base de datos.
        String bd = "ad_23_24";
        String host = "localhost";
        String port = "3306";
        String parAdic = "?allowMultiQueries=true"; // Agrega allowMultiQueries=true para ejecutar múltiples consultas en una.
        String URLConnection = "jdbc:mysql://" + host + ":" + port + "/" + bd + parAdic;
        String user = "root";
        String pwd = "root";

        try (Connection c = DriverManager.getConnection(URLConnection, user, pwd); Statement s = c.createStatement()) {
            System.out.println("Conexión realizada.");

            // Verificar si existen tablas en la base de datos.
            DatabaseMetaData metaData = c.getMetaData();
            ResultSet tablas = metaData.getTables(null, null, null, new String[]{"TABLE"});

           

            // Crear las tablas en la base de datos MySQL.
            System.out.println("Creando tablas EMPLEADOS, PROYECTOS y ASIG_PROYECTOS.");
            s.execute("CREATE TABLE EMPLEADOS(DNI_NIF CHAR(9) NOT NULL, NOMBRE VARCHAR(32) NOT NULL, PRIMARY KEY(DNI_NIF));");
            s.execute("CREATE TABLE PROYECTOS(NUM_PROY INTEGER AUTO_INCREMENT NOT NULL, NOMBRE VARCHAR(32) NOT NULL, DNI_NIF_JEFE_PROY CHAR(9) NOT NULL, F_INICIO DATE NOT NULL, F_FIN DATE, PRIMARY KEY(NUM_PROY), FOREIGN KEY FK_PROY_JEFE(DNI_NIF_JEFE_PROY) REFERENCES EMPLEADOS(DNI_NIF));");
            s.execute("CREATE TABLE ASIG_PROYECTOS(DNI_NIF_EMP CHAR(9), NUM_PROY INTEGER NOT NULL, F_INICIO DATE NOT NULL, F_FIN DATE, PRIMARY KEY(DNI_NIF_EMP,NUM_PROY, F_INICIO), FOREIGN KEY F_ASIG_EMP(DNI_NIF_EMP) REFERENCES EMPLEADOS(DNI_NIF), FOREIGN KEY F_ASIG_PROY(NUM_PROY) REFERENCES PROYECTOS(NUM_PROY));");
            System.out.println("Se han creado las tablas EMPLEADOS,PROYECTOS y ASIG_PROYECTOS exitosamente.");

            // Solicitar al usuario el nombre de la tabla.
            System.out.print("Ingrese el nombre de la tabla con la que desea trabajar: ");
            String nombreTabla = sn1.nextLine();

            // Ahora creamos las tablas en la base de datos mysql.
            s.execute("CREATE TABLE CLIENTES (DNI CHAR(9) NOT NULL,"
                    + "APELLIDOS VARCHAR(32) NOT NULL, CP CHAR(5) DEFAULT NULL,"
                    + "PRIMARY KEY(DNI));");
            System.out.println("Se ha creado la tabla CLIENTES exitosamente.");

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
                                    numeroFila--; // Decrementar número de fila.
                                    if (numeroFila < 1) {
                                        numeroFila = 1;
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
