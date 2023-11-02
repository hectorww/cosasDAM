import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AsignacionEmpAProyecto {
    private String dniNifEmp;
    private int numProyecto;
    private String fechaInicio;
    private String fechaFin;

    // Constructor sin parámetros
    public AsignacionEmpAProyecto() {
    }

    // Constructor con clave primaria
    public AsignacionEmpAProyecto(String dniNifEmp, int numProyecto) throws SQLException {
        this.dniNifEmp = dniNifEmp;
        this.numProyecto = numProyecto;
        cargarDatos();
    }

    // Getters y setters
    public String getDniNifEmp() {
        return dniNifEmp;
    }

    public void setDniNifEmp(String dniNifEmp) {
        this.dniNifEmp = dniNifEmp;
    }

    public int getNumProyecto() {
        return numProyecto;
    }

    public void setNumProyecto(int numProyecto) {
        this.numProyecto = numProyecto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    // Cargar datos desde la base de datos
    private void cargarDatos() throws SQLException {
        try (Connection conn = GestorProyectos.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM ASIG_PROYECTOS WHERE DNI_NIF_EMP = ? AND NUM_PROY = ?")) {
            stmt.setString(1, this.dniNifEmp);
            stmt.setInt(2, this.numProyecto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.fechaInicio = rs.getString("F_INICIO");
                    this.fechaFin = rs.getString("F_FIN");
                } else {
                    throw new SQLException("Asignación no encontrada");
                }
            }
        }
    }

    // Guardar o actualizar la asignación en la base de datos
    public void save() throws SQLException {
        try (Connection conn = GestorProyectos.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO ASIG_PROYECTOS (DNI_NIF_EMP, NUM_PROY, F_INICIO, F_FIN) VALUES (?, ?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE F_INICIO = ?, F_FIN = ?")) {
            stmt.setString(1, this.dniNifEmp);
            stmt.setInt(2, this.numProyecto);
            stmt.setString(3, this.fechaInicio);
            stmt.setString(4, this.fechaFin);
            stmt.setString(5, this.fechaInicio);
            stmt.setString(6, this.fechaFin);

            stmt.executeUpdate();
        }
    }
}
