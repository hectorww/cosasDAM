package jdbc_connection;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static jdk.internal.org.jline.utils.Colors.s;

public class Main {
        public static void muestraErrorSQL(SQLException e) {

            System.err.println("SQL ERROR mensaje: " + e.getMessage());
            System.err.println("SQL Estado: " + e.getSQLState());
            System.err.println("SQL código especifico: " + e.getErrorCode());

        }

        public static void main(String[] args) {
            String basedatos = "dbpractica3";
            String host = "localhost";
            String port = "3306";
            String parAdic = "";
            String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
            String user = "root";
            String pwd = "admin";


                try (Connection c = DriverManager.getConnection(urlConnection, user, pwd)) {
                    System.out.println("Conexión realizada.");
                } catch (SQLException e) {
                    muestraErrorSQL(e);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }

                try (
                        Connection c = DriverManager.getConnection(urlConnection, user, pwd);
                        Statement s = c.createStatement()) {
                    ((Statement) s).execute("CREATE TABLE CLIENTES (DNI CHAR(9) NOT NULL,"
                            +" APELLIDOS VARCHAR(32) NOT NULL, CP CHAR(5),"
                            +" PRIMARY KEY(DNI));");
                }catch (SQLException e) {
                    muestraErrorSQL(e);
                }catch (Exception e) {
                    e.printStackTrace(System.err);
                }

            String nFIL = s.executeUpdate();


            }

        }