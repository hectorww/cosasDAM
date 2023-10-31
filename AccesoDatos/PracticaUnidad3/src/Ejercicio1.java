import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            Statement statement = conexion.createStatement();
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

    private static void mostrarFila(ResultSet resultSet, int numeroFila) throws SQLException {
        System.out.println("Fila " + numeroFila);
        System.out.println("DNI: " + resultSet.getString("DNI"));
        System.out.println("APELLIDOS: " + resultSet.getString("APELLIDOS"));
        System.out.println("CP: " + resultSet.getString("CP"));
    }

    private static boolean esNumeroEntero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
