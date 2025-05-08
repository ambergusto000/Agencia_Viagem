package util;
import java.sql.*;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/agencia_viagens";
    private static final String USER = "root";
    private static final String PASS = "senha";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
