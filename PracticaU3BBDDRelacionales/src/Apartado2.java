import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class Apartado2 {

    public static void muestraErrorSQL(SQLException e) {
        System.err.println("SQL ERROR mensaje:" + e.getMessage());
        System.err.println("SQL Estado:" + e.getSQLState());
        System.err.println("SQL código específico: " + e.getErrorCode());
    }

    public static boolean tablaExiste(Connection connection, String nombreTabla) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet rs = meta.getTables(null, null, nombreTabla, new String[]{"TABLE"});
        return rs.next();
    }

    public static ResultSet obtenerResultSet(Connection connection, String nombreTabla) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery("SELECT * FROM " + nombreTabla);
    }

    public static void mostrarFila(ResultSet rs, ResultSetMetaData metaData) throws SQLException {
        System.out.println("Fila " + rs.getRow());
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            System.out.println(columnName + ": " + rs.getString(columnName));
        }
        System.out.println();
    }

    public static void IniciarMenu() {
        Scanner scanner = new Scanner(System.in);

        String bd = "ad23_24";
        String host = "localhost";
        String port = "3306";
        String parAdic = "";
        String URLConnection = "jdbc:mysql://" + host + ":" + port + "/" + bd + parAdic;
        String user = "root";
        String pwd = "admin";

        ResultSet rs = null;

        try (
                Connection c = DriverManager.getConnection(URLConnection, user, pwd);
                Statement s = c.createStatement()) {

                String nombreTabla;
                boolean tablaValida = false;

            do {
                System.out.println("Si introduces la palabra 'empleados', crearás las tablas empleados, proyectos y asig_proyectos");
                System.out.println("Introduzca el nombre de la tabla para buscar:");

                nombreTabla = scanner.nextLine();

                //COMPROBAMOS LA PALABRA EMPLEADOS PARA CREAR LAS TABLAS
                if(Objects.equals(nombreTabla, "empleados")){

                    //EMPLEADOS Y SUS DATOS
                    s.execute("CREATE TABLE EMPLEADOS(DNI_NIF CHAR(9) NOT NULL,"
                            + " NOMBRE VARCHAR(32) NOT NULL, PRIMARY KEY(DNI_NIF));");

                    s.executeUpdate("INSERT INTO EMPLEADOS (DNI_NIF,NOMBRE) VALUES "
                            + "('12345678L','MANU'),"
                            + "('23456789P','ALVARO'),"
                            + "('34567891K','PABLO'),"
                            + "('45678912R','RAUL');");
                    //TABLA PROYECTOS Y SUS DATOS
                    s.execute("CREATE TABLE PROYECTOS (NUM_PROY INTEGER AUTO_INCREMENT NOT NULL,"
                            + " NOMBRE VARCHAR(32) NOT NULL, DNI_NIF_JEFE_PROY CHAR(9) NOT NULL,"
                            + " F_INICIO DATE NOT NULL, F_FIN DATE, PRIMARY KEY(NUM_PROY),"
                            + " FOREIGN KEY FK_PROY_JEFE(DNI_NIF_JEFE_PROY) REFERENCES EMPLEADOS(DNI_NIF));");

                    s.executeUpdate("INSERT INTO PROYECTOS (NOMBRE,DNI_NIF_JEFE_PROY,F_INICIO,F_FIN) VALUES "
                            + "('Genesis','12345678L','2020-12-12','2021-12-12'),"
                            + "('Opaco','23456789P','2010-10-07','2011-10-07'),"
                            + "('Berilio','45678912R','2021-01-01','2025-01-01');");
                    //TABLA ASIG_PROYECTOS Y SUS DATOS
                    s.execute("CREATE TABLE ASIG_PROYECTOS (DNI_NIF_EMP CHAR(9), NUM_PROY INTEGER NOT NULL,"
                            + " F_INICIO DATE NOT NULL, F_FIN DATE, PRIMARY KEY(DNI_NIF_EMP,NUM_PROY, F_INICIO),"
                            + " FOREIGN KEY F_ASIG_EMP(DNI_NIF_EMP) REFERENCES EMPLEADOS(DNI_NIF),"
                            + " FOREIGN KEY  F_ASIG_PROY(NUM_PROY) REFERENCES PROYECTOS(NUM_PROY));");

                    s.executeUpdate("INSERT INTO ASIG_PROYECTOS (DNI_NIF_EMP,NUM_PROY,F_INICIO,F_FIN) VALUES "
                            + "('34567891K','2','2010-10-07','2011-10-07')");
                    System.out.println("Hemos creado las tablas e insertado datos de ejemplo");
                    tablaValida = true;
                }

                if (tablaExiste(c, nombreTabla)) {
                    tablaValida = true;
                } else {
                    System.out.println("La tabla no existe. Ingrese otro nombre de tabla.");
                }

            } while (!tablaValida);

            rs = obtenerResultSet(c, nombreTabla);
            ResultSetMetaData metaData = rs.getMetaData();

            String comando;
            do {
                System.out.println("-----------Introduce un comando----------");
                System.out.println("Para terminar y liberar recursos -> ( . )");
                System.out.println("Ir a la siguiente fila y mostrar -> ( k )");
                System.out.println("Ir a la fila anterior y mostrar  -> ( d )");
                comando = scanner.nextLine();

                switch (comando) {
                    case ".":
                        System.out.println("Programa terminado.");
                        break;
                    case "k":
                        if (rs.next()) {
                            mostrarFila(rs, metaData);
                        } else {
                            System.out.println("Ya estás en la última fila.");
                        }
                        break;
                    case "d":
                        if (rs.previous()) {
                            mostrarFila(rs, metaData);
                        } else {
                            System.out.println("Ya estás en la primera fila.");
                        }
                        break;
                    default:
                        try {
                            int numero = Integer.parseInt(comando);
                            if (rs.absolute(numero)) {
                                mostrarFila(rs, metaData);
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