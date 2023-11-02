import java.sql.*;
import java.util.Scanner;

public class GestorProyectos {
    public static Connection obtenerConexion() throws SQLException {
        String bd = "ad23_24";
        String host = "localhost";
        String port = "3306";
        String parAdic = "";
        String URLConnection = "jdbc:mysql://" + host + ":" + port + "/" + bd + parAdic;
        String user = "root";
        String pwd = "admin";

        return DriverManager.getConnection(URLConnection, user, pwd);
    }

    public static void nuevoEmpleado() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        boolean dniVerify = false;
        String dniNif = null;

        while (!dniVerify){
            System.out.println("Introduzca DNI o NIF para un nuevo empleado:");
            dniNif = scanner.nextLine();
            if(dniNif.matches("\\d{8}[A-Z]")){
                dniVerify = true;
            } else {
                System.out.println("El DNI proporcionado no es correcto, introduzca 8 números y 1 letra");
            }
        }

        System.out.println("Introduzca el nombre del nuevo empleado (máx 32 caracteres):");
        String nombre = scanner.nextLine();

        while (nombre.length() > 32) {
            System.out.println("El nombre debe tener un máximo de 32 caracteres. Inténtelo de nuevo:");
            nombre = scanner.nextLine();
        }

        Connection c = obtenerConexion();
        String sql = "INSERT INTO EMPLEADOS (DNI_NIF, NOMBRE) VALUES (?, ?)";

        PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, dniNif);
            statement.setString(2, nombre);

            int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("El empleado ha sido creado");
        } else {
            System.out.println("ERROR, el empleado no se ha creado");
        }
    }

    public static void nuevoProyecto() throws SQLException {

        Scanner scanner = new Scanner(System.in);

            System.out.println("Introduzca un nombre para el proyecto:");
            String nombreproyecto = scanner.nextLine();

            while (nombreproyecto.length() > 32) {
                System.out.println("El nombre debe tener un máximo de 32 caracteres. Inténtelo de nuevo:");
                nombreproyecto = scanner.nextLine();
            }

            String dniNifJefe;

            do {
                System.out.println("Introduzca DNI o NIF del jefe de proyecto (tiene que coincidir con el de un empleado):");
                dniNifJefe = scanner.nextLine();

                if (!existeEmpleadoDNINIF(dniNifJefe)) {
                    System.out.println("El DNI o NIF proporcionado no coincide con ningún empleado. Inténtelo de nuevo.");
                }
            } while (!existeEmpleadoDNINIF(dniNifJefe));

            System.out.println("Introduzca la fecha de inicio del proyecto, formato: 'YYYY-MM-DD' (escribe 'null' para la fecha actual):");
            String fInicio = scanner.nextLine();

            System.out.println("Introduzca la fecha de fin del proyecto, formato: 'YYYY-MM-DD' (escribe 'null' si no hay una fecha prevista):");
            String fFin = scanner.nextLine();

        Connection c = obtenerConexion();

            String sql = "INSERT INTO Proyectos (NOMBRE, DNI_NIF_JEFE_PROY, F_INICIO, F_FIN) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, nombreproyecto);
                statement.setString(2, dniNifJefe);

                if (fInicio == null || fInicio.isEmpty()) {
                    statement.setObject(3, null);
                } else {
                    statement.setString(3, fInicio);
                }

                if (fFin != null) {
                    statement.setString(4, fFin);
                } else {
                    statement.setObject(4, null);
                }

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        generatedKeys.getInt(1);
                        return;
                    }
                }

                throw new SQLException("La creación del proyecto ha fallado no se ha podido obtener el NUM_PROY");
    }

    public static void asignarEmpleadoAProyecto() {
        Scanner scanner = new Scanner(System.in);

            System.out.println("Ingrese el DNI/NIF del empleado:");
            String dniNifEmp = scanner.nextLine();

            System.out.println("Ingrese el número de proyecto:");
            int numProyecto = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Introduzca la fecha de inicio del proyecto, formato: 'YYYY-MM-DD' (escribe 'null' para la fecha actual):");
            String fInicio = scanner.nextLine();

            System.out.println("Introduzca la fecha de fin del proyecto, formato: 'YYYY-MM-DD' (escribe 'null' si no hay una fecha prevista):");
            String fFin = scanner.nextLine();

        try (Connection c = obtenerConexion()) {
            //aquí comprobamos que el empleado y el proyecto existen antes de asignarlos ya que tienen claves foraneas
            if (existeEmpleado(c, dniNifEmp) && existeProyecto(c, numProyecto)) {
                String sql = "INSERT INTO ASIG_PROYECTOS (DNI_NIF_EMP, NUM_PROY, F_INICIO, F_FIN) VALUES (?, ?, ?, ?)";

                try (PreparedStatement statement = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    statement.setString(1, dniNifEmp);
                    statement.setInt(2, numProyecto);

                    if (fInicio == null || fInicio.isEmpty()) {
                        statement.setObject(3, null);
                    } else {
                        statement.setString(3, fInicio);
                    }

                    if (fFin != null) {
                        statement.setString(4, fFin);
                    } else {
                        statement.setObject(4, null);
                    }

                    int rowsAffected = statement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Empleado asignado al proyecto correctamente.");
                    } else {
                        System.out.println("La asignación del empleado al proyecto falló.");
                    }
                } catch (SQLException e) {
                    GestorProyectos.muestraErrorSQL(e);

                }
            } else {
                System.out.println("El empleado o el proyecto no existen. La asignación no se puede realizar.");
            }
        } catch (SQLException e) {
            GestorProyectos.muestraErrorSQL(e);
        }
    }

    private static boolean existeEmpleado(Connection connection, String dniNifEmp) throws SQLException {
        String sql = "SELECT 1 FROM EMPLEADOS WHERE DNI_NIF = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, dniNifEmp);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
    }

    private static boolean existeProyecto(Connection connection, int numProyecto) throws SQLException {
        String sql = "SELECT 1 FROM PROYECTOS WHERE NUM_PROY = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numProyecto);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
    }

    private static boolean existeEmpleadoDNINIF(String dniNif) throws SQLException {
        Connection c = obtenerConexion();

        String sql = "SELECT COUNT(*) FROM Empleados WHERE DNI_NIF = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setString(1, dniNif);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }

        return false;
    }

    public static void IniciarGestor() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        String comando;
        do {
            System.out.println("-----------Introduce un comando----------");
            System.out.println("Volver al menú principal ---------> ( . )");
            System.out.println("Crear un nuevo empleado ----------> ( 1 )");
            System.out.println("Crear un nuevo proyecto ----------> ( 2 )");
            System.out.println("Asignar un empleado a proyecto ---> ( 3 )");
            comando = scanner.nextLine();

            switch (comando) {
                case ".":
                    //terminar y liberar recursos
                    System.out.println("Programa terminado.");
                    break;

                case "1":
                    nuevoEmpleado();
                    break;

                case "2":
                    nuevoProyecto();
                    break;

                case "3":
                    asignarEmpleadoAProyecto();
                    break;
                default:
                    System.out.println("Introduzca una opción válida");
            }
        } while (!comando.equals("."));
    }

    public static void muestraErrorSQL(SQLException e) {
        System.err.println("SQL ERROR mensaje:" + e.getMessage());
        System.err.println("SQL Estado:" + e.getSQLState());
        System.err.println("SQL código específico: " + e.getErrorCode());
    }

    public static void main(String[] args) {
        try {
            IniciarGestor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
