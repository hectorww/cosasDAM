import java.sql.*;
import java.util.Scanner;

public class Ejercicio1 {

    public static void main(String[] args) {
        conexion();
    }

    public static void muestraErrorSQL(SQLException e) {
        System.err.println("SQL ERROR mensaje: " + e.getMessage());
        System.err.println("SQL Estado: " + e.getSQLState());
        System.err.println("SQL código específico: " + e.getErrorCode());
    }

    public static void conexion() {
        String basedatos = "practicaunidad3";
        String host = "localhost";
        String port = "3306";
        String parAdic = "";
        String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
        String user = "root";
        String pwd = "admin";

        try (Connection conexion = DriverManager.getConnection(urlConnection, user, pwd)) {
            // Eliminar y crear la tabla CLIENTES
            setupDatabase(conexion);

            // Insertar registros predeterminados
            insertarRegistrosPredeterminados(conexion);

            Statement statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CLIENTES");
            int filaActual = 0;

            while (true) {
                if (resultSet.absolute(filaActual + 1)) {
                    mostrarFila(resultSet, filaActual + 1);
                } else {
                    System.out.println("No se encontró una fila con ese número.");
                }

                Scanner scanner = new Scanner(System.in);
                System.out.print("Introduce un comando (k - siguiente, d - anterior, número - ir a fila, . - salir): ");
                String comando = scanner.nextLine();

                if (comando.equals(".")) {
                    break;
                } else if (comando.equals("k")) {
                    filaActual++;
                } else if (comando.equals("d")) {
                    filaActual--;
                } else if (esNumeroEntero(comando)) {
                    filaActual = Integer.parseInt(comando) - 1;
                } else {
                    System.out.println("Comando no válido.");
                }
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void setupDatabase(Connection conexion) throws SQLException {
        Statement statement = conexion.createStatement();
        statement.execute("DROP TABLE IF EXISTS clientes");
        statement.execute("CREATE TABLE `clientes` (`DNI` char(9) NOT NULL,`APELLIDOS` varchar(32) NOT NULL, `CP` char(5) DEFAULT NULL, PRIMARY KEY (`DNI`))");
    }

    private static void insertarRegistrosPredeterminados(Connection conexion) throws SQLException {
        String sql = "INSERT INTO clientes (DNI, APELLIDOS, CP) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conexion.prepareStatement(sql);

        // Registro 1
        preparedStatement.setString(1, "123456789");
        preparedStatement.setString(2, "García Pérez");
        preparedStatement.setString(3, "28001");
        preparedStatement.executeUpdate();

        // Registro 2
        preparedStatement.setString(1, "987654321");
        preparedStatement.setString(2, "López Martínez");
        preparedStatement.setString(3, "28002");
        preparedStatement.executeUpdate();

        // Registro 3
        preparedStatement.setString(1, "456789123");
        preparedStatement.setString(2, "Torres Sánchez");
        preparedStatement.setString(3, "28003");
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    private static void mostrarFila(ResultSet resultSet, int fila) throws SQLException {
        System.out.println("Fila " + fila + ":");
        System.out.println("DNI: " + resultSet.getString("DNI"));
        System.out.println("APELLIDOS: " + resultSet.getString("APELLIDOS"));
        System.out.println("CP: " + resultSet.getString("CP"));
    }

    private static boolean esNumeroEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
