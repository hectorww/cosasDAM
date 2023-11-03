import java.sql.*;
import java.util.Scanner;

public class Ejercicio2{

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
            // Crear tablas y registros predeterminados
            setupDatabase(conexion);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce el nombre de la tabla: ");
            String nombreTabla = scanner.nextLine();

            Statement statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + nombreTabla);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numeroColumnas = metaData.getColumnCount();

            int filaActual = 0;

            while (true) {
                if (resultSet.absolute(filaActual + 1)) {
                    mostrarFila(resultSet, metaData, numeroColumnas, filaActual + 1);
                } else {
                    System.out.println("No se encontró una fila con ese número.");
                }

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

        // Eliminar tabla ASIG_PROYECTOS
        statement.execute("DROP TABLE IF EXISTS ASIG_PROYECTOS");

        // Eliminar tabla PROYECTOS
        statement.execute("DROP TABLE IF EXISTS PROYECTOS");

        // Eliminar tabla EMPLEADOS
        statement.execute("DROP TABLE IF EXISTS EMPLEADOS");

        // Crear tabla EMPLEADOS
        statement.execute("CREATE TABLE EMPLEADOS(DNI_NIF CHAR(9) NOT NULL, NOMBRE VARCHAR(32) NOT NULL, PRIMARY KEY(DNI_NIF))");

        // Crear tabla PROYECTOS
        statement.execute("CREATE TABLE PROYECTOS(NUM_PROY INTEGER AUTO_INCREMENT NOT NULL, NOMBRE VARCHAR(32) NOT NULL, DNI_NIF_JEFE_PROY CHAR(9) NOT NULL, F_INICIO DATE NOT NULL, F_FIN DATE, PRIMARY KEY(NUM_PROY), FOREIGN KEY FK_PROY_JEFE(DNI_NIF_JEFE_PROY) REFERENCES EMPLEADOS(DNI_NIF))");

        // Crear tabla ASIG_PROYECTOS
        statement.execute("CREATE TABLE ASIG_PROYECTOS(DNI_NIF_EMP CHAR(9), NUM_PROY INTEGER NOT NULL, F_INICIO DATE NOT NULL, F_FIN DATE, PRIMARY KEY(DNI_NIF_EMP,NUM_PROY, F_INICIO), FOREIGN KEY F_ASIG_EMP(DNI_NIF_EMP) REFERENCES EMPLEADOS(DNI_NIF), FOREIGN KEY F_ASIG_PROY(NUM_PROY) REFERENCES PROYECTOS(NUM_PROY))");

        // Insertar registros predeterminados en EMPLEADOS
        statement.execute("INSERT INTO EMPLEADOS (DNI_NIF, NOMBRE) VALUES ('123456789', 'Juan Pérez')");
        statement.execute("INSERT INTO EMPLEADOS (DNI_NIF, NOMBRE) VALUES ('987654321', 'Ana García')");

        // Insertar registros predeterminados en PROYECTOS (asumiendo que las fechas son solo ejemplos)
        statement.execute("INSERT INTO PROYECTOS (NOMBRE, DNI_NIF_JEFE_PROY, F_INICIO, F_FIN) VALUES ('Proyecto A', '123456789', '2023-01-01', '2023-12-31')");
        statement.execute("INSERT INTO PROYECTOS (NOMBRE, DNI_NIF_JEFE_PROY, F_INICIO) VALUES ('Proyecto B', '987654321', '2023-02-01')");

        statement.close();
    }

    private static void mostrarFila(ResultSet resultSet, ResultSetMetaData metaData, int numeroColumnas, int fila) throws SQLException {
        System.out.println("Fila " + fila + ":");
        for (int i = 1; i <= numeroColumnas; i++) {
            System.out.println(metaData.getColumnName(i) + ": " + resultSet.getString(i));
        }
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
