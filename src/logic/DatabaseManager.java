package logic;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {

    private String db;

    public DatabaseManager(String db) {
        this.db = db;
    }

    public Connection connect() {
        Connection conn = null;

        try {
            String url = "jdbc:sqlite:" + db;
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public void executeUpdate(String sql) {
        Connection conn = connect();
        Statement statement = null;

        try {
            statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ResultSet getAll(String sql) {
        ResultSet resultSet = null;

        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultSet;
    }

    public void delete(String from, UUID uuid) {
        String sql = "DELETE FROM ? WHERE uuid = ?";

        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, from);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
