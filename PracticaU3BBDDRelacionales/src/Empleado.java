import java.sql.*;

public class Empleado {
    private String dniNif;
    private String nombre;

    // Constructor sin parámetros
    public Empleado() {}

    // Constructor con parámetros clave primaria
    public Empleado(String dniNif) throws SQLException {
        if (!existeEmpleado(dniNif)) {
            throw new SQLException("Empleado con DNI/NIF " + dniNif + " no encontrado.");
        }
        this.dniNif = dniNif;
    }

    // Métodos getter y setter
    public String getDNINIF() {
        return dniNif;
    }

    public void setDNINIF(String dniNif) {
        this.dniNif = dniNif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método save
    public void save() throws SQLException {
        try (Connection c = GestorProyectos.obtenerConexion()) {
            String sql;
            if (!existeEmpleado(this.dniNif)) {
                sql = "INSERT INTO EMPLEADOS (DNI_NIF, NOMBRE) VALUES (?, ?)";
            } else {
                sql = "UPDATE EMPLEADOS SET NOMBRE = ? WHERE DNI_NIF = ?";
            }

            try (PreparedStatement statement = c.prepareStatement(sql)) {
                statement.setString(1, this.nombre);
                statement.setString(2, this.dniNif);

                statement.executeUpdate();
            }
        }
    }

    private static boolean existeEmpleado(String dniNif) throws SQLException {
        try (Connection c = GestorProyectos.obtenerConexion()) {
            String sql = "SELECT COUNT(*) FROM EMPLEADOS WHERE DNI_NIF = ?";
            try (PreparedStatement statement = c.prepareStatement(sql)) {
                statement.setString(1, dniNif);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1) > 0;
                    }
                }
            }
        }
        return false;
    }
}
