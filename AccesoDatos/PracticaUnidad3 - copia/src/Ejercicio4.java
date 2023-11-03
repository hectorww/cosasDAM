import java.sql.*;
import java.util.Scanner;

public class Ejercicio4 {
    private String dniNif;
    private String nombre;

    // Constructor sin parámetros
    public class Empleado {
        private String dniNif;
        private String nombre;

        // Variables estáticas para la conexión a la base de datos
        private static final String basedatos = "practicaunidad3";
        private static final String host = "localhost";
        private static final String port = "3306";
        private static final String parAdic = "";
        private static final String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
        private static final String user = "root";
        private static final String password = "admin";

        public Empleado() {
            // Inicialización por defecto
            this.dniNif = null;
            this.nombre = null;
        }

        public void setDniNif(String dniNif) {
            this.dniNif = dniNif;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void loadByDni(String dniNif) throws SQLException {
            try (Connection conexion = DriverManager.getConnection(urlConnection, user, password)) {
                String sql = "SELECT * FROM EMPLEADOS WHERE DNI_NIF = ?";
                PreparedStatement preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setString(1, dniNif);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    this.dniNif = resultSet.getString("DNI_NIF");
                    this.nombre = resultSet.getString("NOMBRE");
                } else {
                    throw new SQLException("No se encontró un empleado con el DNI: " + dniNif);
                }
            }
        }

        public void save() throws SQLException {
            try (Connection conexion = DriverManager.getConnection(urlConnection, user, password)) {
                String sqlInsert = "INSERT INTO EMPLEADOS (DNI_NIF, NOMBRE) VALUES (?, ?)";
                String sqlUpdate = "UPDATE EMPLEADOS SET NOMBRE = ? WHERE DNI_NIF = ?";

                // Verificar si el empleado ya existe
                PreparedStatement checkStmt = conexion.prepareStatement("SELECT * FROM EMPLEADOS WHERE DNI_NIF = ?");
                checkStmt.setString(1, dniNif);
                ResultSet resultSet = checkStmt.executeQuery();

                if (resultSet.next()) {
                    // Si existe, actualizamos
                    PreparedStatement updateStmt = conexion.prepareStatement(sqlUpdate);
                    updateStmt.setString(1, nombre);
                    updateStmt.setString(2, dniNif);
                    updateStmt.executeUpdate();
                } else {
                    // Si no existe, insertamos
                    PreparedStatement insertStmt = conexion.prepareStatement(sqlInsert);
                    insertStmt.setString(1, dniNif);
                    insertStmt.setString(2, nombre);
                    insertStmt.executeUpdate();
                }
            }
        }
    }
}