import java.sql.*;

public class Proyecto {
    private int numProyecto;
    private String nombre;
    private String dniNifJefeProy;
    private Date fInicio;
    private Date fFin;

    // Constructor sin parámetros
    public Proyecto() {
    }

    // Constructor con parámetros clave
    public Proyecto(int numProyecto) throws SQLException {
        this.numProyecto = numProyecto;
        if (!this.cargarDesdeBD()) {
            throw new SQLException("No existe un proyecto con el número " + numProyecto);
        }
    }

    // Métodos getter y setter
    public int getNumProyecto() {
        return numProyecto;
    }

    public void setNumProyecto(int numProyecto) {
        this.numProyecto = numProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDniNifJefeProy() {
        return dniNifJefeProy;
    }

    public void setDniNifJefeProy(String dniNifJefeProy) {
        this.dniNifJefeProy = dniNifJefeProy;
    }

    public Date getFInicio() {
        return fInicio;
    }

    public void setFInicio(Date fInicio) {
        this.fInicio = fInicio;
    }

    public Date getFFin() {
        return fFin;
    }

    public void setFFin(Date fFin) {
        this.fFin = fFin;
    }

    // Método para cargar los datos desde la BD
    private boolean cargarDesdeBD() throws SQLException {
        try (Connection connection = GestorProyectos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Proyectos WHERE NUM_PROY = ?")) {
            statement.setInt(1, this.numProyecto);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.nombre = resultSet.getString("NOMBRE");
                    this.dniNifJefeProy = resultSet.getString("DNI_NIF_JEFE_PROY");
                    this.fInicio = resultSet.getDate("F_INICIO");
                    this.fFin = resultSet.getDate("F_FIN");
                    return true;
                }
            }
        }
        return false;
    }

    // Método para guardar o actualizar la información en la BD
    public void save() throws SQLException {
        try (Connection connection = GestorProyectos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Proyectos (NUM_PROY, NOMBRE, DNI_NIF_JEFE_PROY, F_INICIO, F_FIN) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE NOMBRE = ?, DNI_NIF_JEFE_PROY = ?, F_INICIO = ?, F_FIN = ?")) {

            statement.setInt(1, this.numProyecto);
            statement.setString(2, this.nombre);
            statement.setString(3, this.dniNifJefeProy);
            statement.setDate(4, this.fInicio);
            statement.setDate(5, this.fFin);
            // Set parameters for update
            statement.setString(6, this.nombre);
            statement.setString(7, this.dniNifJefeProy);
            statement.setDate(8, this.fInicio);
            statement.setDate(9, this.fFin);

            statement.executeUpdate();
        }
    }
}
