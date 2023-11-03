import java.sql.*;
import java.util.Scanner;

public class GestorProyectos {

    private static final String basedatos = "practicaunidad3";
    private static final String host = "localhost";
    private static final String port = "3306";
    private static final String parAdic = "";
    private static final String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
    private static final String user = "root";
    private static final String password = "admin";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Configurando base de datos...");
            setupDatabase();
            System.out.println("Base de datos configurada correctamente.");

            System.out.println("Introduce los datos del empleado:");
            System.out.print("DNI: ");
            String dni = scanner.nextLine();
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            nuevoEmpleado(dni, nombre);
            System.out.println("Empleado creado correctamente.");

            System.out.println("Introduce los datos del proyecto:");
            System.out.print("Nombre del proyecto: ");
            String nombreProyecto = scanner.nextLine();
            System.out.print("DNI del jefe del proyecto: ");
            String dniJefe = scanner.nextLine();
            System.out.print("Fecha de inicio (AAAA-MM-DD) o deja en blanco para usar la fecha actual: ");
            String fechaInicioStr = scanner.nextLine();
            Date fechaInicio = fechaInicioStr.isEmpty() ? null : Date.valueOf(fechaInicioStr);
            System.out.print("Fecha de finalización (AAAA-MM-DD) o deja en blanco si no está definida: ");
            String fechaFinStr = scanner.nextLine();
            Date fechaFin = fechaFinStr.isEmpty() ? null : Date.valueOf(fechaFinStr);
            nuevoProyecto(nombreProyecto, dniJefe, fechaInicio, fechaFin);
            System.out.println("Proyecto creado correctamente.");

        } catch (SQLException e) {
            muestraErrorSQL(e);
        }
    }

    public static void muestraErrorSQL(SQLException e) {
        System.err.println("SQL ERROR mensaje: " + e.getMessage());
        System.err.println("SQL Estado: " + e.getSQLState());
        System.err.println("SQL código específico: " + e.getErrorCode());
    }

    public static void setupDatabase() throws SQLException {
        try (Connection conexion = DriverManager.getConnection(urlConnection, user, password)) {
            Statement statement = conexion.createStatement();

            // Eliminar tablas en orden inverso debido a las claves foráneas
            statement.execute("DROP TABLE IF EXISTS ASIG_PROYECTOS");
            statement.execute("DROP TABLE IF EXISTS PROYECTOS");
            statement.execute("DROP TABLE IF EXISTS EMPLEADOS");

            // Crear las tablas
            statement.execute("CREATE TABLE EMPLEADOS(DNI_NIF CHAR(9) NOT NULL, NOMBRE VARCHAR(32) NOT NULL, PRIMARY KEY(DNI_NIF))");
            statement.execute("CREATE TABLE PROYECTOS(NUM_PROY INTEGER AUTO_INCREMENT NOT NULL, NOMBRE VARCHAR(32) NOT NULL, DNI_NIF_JEFE_PROY CHAR(9) NOT NULL, F_INICIO DATE NOT NULL, F_FIN DATE, PRIMARY KEY(NUM_PROY), FOREIGN KEY FK_PROY_JEFE(DNI_NIF_JEFE_PROY) REFERENCES EMPLEADOS(DNI_NIF))");
            statement.execute("CREATE TABLE ASIG_PROYECTOS(DNI_NIF_EMP CHAR(9), NUM_PROY INTEGER NOT NULL, F_INICIO DATE NOT NULL, F_FIN DATE, PRIMARY KEY(DNI_NIF_EMP,NUM_PROY, F_INICIO), FOREIGN KEY F_ASIG_EMP(DNI_NIF_EMP) REFERENCES EMPLEADOS(DNI_NIF), FOREIGN KEY F_ASIG_PROY(NUM_PROY) REFERENCES PROYECTOS(NUM_PROY))");
        }
    }

    public static boolean nuevoEmpleado(String dni, String nombre) throws SQLException {
        try (Connection conexion = DriverManager.getConnection(urlConnection, user, password)) {
            String sql = "INSERT INTO EMPLEADOS (DNI_NIF, NOMBRE) VALUES (?, ?)";
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, nombre);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static int nuevoProyecto(String nombre, String dniJefe, Date fInicio, Date fFin) throws SQLException {
        try (Connection conexion = DriverManager.getConnection(urlConnection, user, password)) {
            // Verificar si el DNI_NIF_JEFE_PROY existe en la tabla empleados
            String checkSql = "SELECT COUNT(*) FROM EMPLEADOS WHERE DNI_NIF = ?";
            PreparedStatement checkStmt = conexion.prepareStatement(checkSql);
            checkStmt.setString(1, dniJefe);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new SQLException("El DNI del jefe del proyecto no existe en la tabla de empleados.");
            }

            // Insertar el nuevo proyecto
            String sql = "INSERT INTO PROYECTOS (NOMBRE, DNI_NIF_JEFE_PROY, F_INICIO, F_FIN) VALUES (?, ?, COALESCE(?, NOW()), ?)";
            PreparedStatement preparedStatement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, dniJefe);
            preparedStatement.setDate(3, fInicio);
            preparedStatement.setDate(4, fFin);
            preparedStatement.executeUpdate();

            ResultSet rs2 = preparedStatement.getGeneratedKeys();
            if (rs2.next()) {
                return rs2.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID del proyecto.");
            }
        }
    }


    public static void asignaEmpAProyecto(String dniEmp, int numProy, Date fInicio, Date fFin) throws SQLException {
        try (Connection conexion = DriverManager.getConnection(urlConnection, user, password)) {
            String sql = "INSERT INTO ASIG_PROYECTOS (DNI_NIF_EMP, NUM_PROY, F_INICIO, F_FIN) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, dniEmp);
            preparedStatement.setInt(2, numProy);
            preparedStatement.setDate(3, fInicio);
            preparedStatement.setDate(4, fFin);
            preparedStatement.executeUpdate();
        }
    }
}