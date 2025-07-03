package modelo.dao;

import config.ConexionDatabase;
import modelo.vo.PersonaVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    public boolean registrarUsuario(PersonaVo miPersonaVo){
        String consulta = "INSERT INTO persona (documento, nombre, telefono, email)" + "VALUES (?,?,?,?)";

        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
            PreparedStatement preStatement = conn.prepareStatement(consulta)){

            preStatement.setString(1, miPersonaVo.getDocumento());
            preStatement.setString(2, miPersonaVo.getNombre());
            preStatement.setString(3, miPersonaVo.getTelefono());
            preStatement.setString(4, miPersonaVo.getEmail());
            preStatement.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("No se pudo registrar el dato: " + e.getMessage());
            return false;
        }

    }

    public PersonaVo consultarPersona(String documento) {
        String consulta = "SELECT * FROM persona WHERE documento = ?";
        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {

            stmt.setString(1, documento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PersonaVo persona = new PersonaVo();
                persona.setDocumento(rs.getString("documento"));
                persona.setNombre(rs.getString("nombre"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setEmail(rs.getString("email"));
                return persona;
            }
        } catch (SQLException e) {
            System.err.println("No se pudo consultar el dato: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarPersona(PersonaVo persona) {
        String sql = "UPDATE persona SET nombre = ?, telefono = ?, email = ? WHERE documento = ?";
        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getTelefono());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getDocumento());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPersona(String documento) {
        String sql = "DELETE FROM persona WHERE documento = ?";
        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, documento);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<PersonaVo> listarPersonas() {
        List<PersonaVo> personas = new ArrayList<>();
        String sql = "SELECT * FROM persona ORDER BY nombre";

        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PersonaVo persona = new PersonaVo();
                persona.setDocumento(rs.getString("documento"));
                persona.setNombre(rs.getString("nombre"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setEmail(rs.getString("email"));
                personas.add(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

}
