import java.sql.*;
import java.util.Scanner;

public class Apartado1 {

    public static void muestraErrorSQL(SQLException e) {
        System.err.println("SQL ERROR mensaje:" + e.getMessage());
        System.err.println("SQL Estado:" + e.getSQLState());
        System.err.println("SQL código específico: " + e.getErrorCode());
    }

    public static boolean tablaExiste(Connection connection, String nombreTabla) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet rs = meta.getTables(null, null, nombreTabla, new String[] {"TABLE"});
        return rs.next();
    }

    public static ResultSet obtenerResultSet(Connection connection, String nombreTabla) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery("SELECT * FROM " + nombreTabla);
    }


    public static void mostrarFila(ResultSet rs) throws SQLException {
        System.out.println("Fila " + rs.getRow());
        System.out.println("DNI: " + rs.getString("DNI"));
        System.out.println("APELLIDOS: " + rs.getString("APELLIDOS"));
        System.out.println("CP: " + rs.getString("CP"));
        System.out.println();
    }

    public static void mostrarbbdd(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM CLIENTES");

        while (rs.next()) {
            mostrarFila(rs);
        }
    }

    public static void IniciarMenu() {
        String bd = "ad23_24";
        String host = "localhost";
        String port = "3306";
        String parAdic = "";
        String URLConnection = "jdbc:mysql://" + host + ":" + port + "/" + bd + parAdic;
        String user = "root";
        String pwd = "admin";
        String nombreTabla = "CLIENTES";

        ResultSet rs = null;

        try (
                Connection c = DriverManager.getConnection(URLConnection, user, pwd);
                Statement s = c.createStatement()) {

            //verificar si la tabla ya existe
            if (!tablaExiste(c, nombreTabla)) {
                //vrear la tabla
                s.execute("CREATE TABLE CLIENTES (DNI CHAR(9) NOT NULL,"
                        + " APELLIDOS VARCHAR(32) NOT NULL, CP CHAR(5),"
                        + " PRIMARY KEY(DNI));");

                //insertar datos
                int nFil = s.executeUpdate("INSERT INTO CLIENTES (DNI,APELLIDOS,CP) VALUES "
                        + "('78901234X','NADALES','44126'),"
                        + "('89012345E','HOJAS','null'),"
                        + "('56789012B','SAMPER','29730'),"
                        + "('09876543K','LAMIQUIZ','null');");

                System.out.println(nFil + " Filas insertadas.");
            }

            // Obtener un nuevo ResultSet después de la creación de la tabla
            rs = obtenerResultSet(c, nombreTabla);

            mostrarbbdd(s);

            // Manejar comandos por teclado
            Scanner scanner = new Scanner(System.in);
            String comando;

            do {
                System.out.println("-----------Introduce un comando----------");
                System.out.println("Para terminar y liberar recursos -> ( . )");
                System.out.println("Ir a la siguiente fila y mostrar -> ( k )");
                System.out.println("Ir a la fila anterior y mostrar  -> ( d )");
                comando = scanner.nextLine();

                switch (comando) {
                    case ".":
                        //terminar y liberar recursos
                        System.out.println("Programa terminado.");
                        break;
                    case "k":
                        //ir a la siguiente fila y mostrar sus contenidos
                        if (rs.next()) {
                            mostrarFila(rs);
                        } else {
                            System.out.println("Ya estás en la última fila.");
                        }
                        break;
                    case "d":
                        //ir a la fila anterior y mostrar sus contenidos
                        if (rs.previous()) {
                            mostrarFila(rs);
                        } else {
                            System.out.println("Ya estás en la primera fila.");
                        }
                        break;
                    default:

                        try {
                            int numero = Integer.parseInt(comando);

                            if (rs.absolute(numero)) {
                                mostrarFila(rs);
                            } else {
                                System.out.println("Número de fila no válido.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Comando no válido. Introduce '.', 'k', 'd' o un número.");
                        } catch (SQLException e) {
                            muestraErrorSQL(e);
                        }
                }

            } while (!comando.equals("."));

        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            IniciarMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
