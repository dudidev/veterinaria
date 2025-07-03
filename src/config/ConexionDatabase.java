package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDatabase {
    private static ConexionDatabase instancia;
    private String user;
    private String passw;
    private String url;
    private String driver;
    private String base;
    private Connection conn;
    private static final String CONFIG_FILE = "config/config.properties";

    private ConexionDatabase(){
        Properties propiedades = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null){
                throw new RuntimeException("No se encontró el archivo");
            }

            propiedades.load(input);
            user = propiedades.getProperty("data.user");
            passw = propiedades.getProperty("data.passw");
            url = propiedades.getProperty("data.url");
            driver = propiedades.getProperty("data.driver");
            base = propiedades.getProperty("data.base");

            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, passw);

            if (conn == null){
                System.err.println("Conexion Fallida a la BD: " + base);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Ocurre un ClassNotFoundException:" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Ocurre un SQLException: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para obtener la instancia única (Singleton)
    public static ConexionDatabase getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDatabase();
        }
        return instancia;
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url, user, passw);
            if (conn == null || conn.isClosed()) {
                throw new RuntimeException("No se pudo conectar a la base de datos: " + base);
            }
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión: " + e.getMessage());
        }
    }

}
