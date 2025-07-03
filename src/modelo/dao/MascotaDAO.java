package modelo.dao;

import config.ConexionDatabase;
import modelo.vo.MascotaVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {
    public boolean registrarMascota(MascotaVo mascota) {
        String sql = "INSERT INTO mascota (nombre, raza, sexo, comentarios, documento_dueno) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getRaza());
            stmt.setString(3, mascota.getSexo());
            stmt.setString(4, mascota.getComentarios());
            stmt.setString(5, mascota.getDocumentoDueno());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public MascotaVo consultarMascota(int idMascota) {
        String sql = "SELECT m.*, p.nombre as nombre_dueno FROM mascota m " +
                "LEFT JOIN persona p ON m.documento_dueno = p.documento " +
                "WHERE m.id_mascota = ?";
        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMascota);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                MascotaVo mascota = new MascotaVo();
                mascota.setIdMascota(rs.getInt("id_mascota"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setRaza(rs.getString("raza"));
                mascota.setSexo(rs.getString("sexo"));
                mascota.setComentarios(rs.getString("comentarios"));
                mascota.setDocumentoDueno(rs.getString("documento_dueno"));
                mascota.setNombreDueno(rs.getString("nombre_dueno"));
                return mascota;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarMascota(MascotaVo mascota) {
        String sql = "UPDATE mascota SET nombre = ?, raza = ?, sexo = ?, comentarios = ?, documento_dueno = ? WHERE id_mascota = ?";
        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getRaza());
            stmt.setString(3, mascota.getSexo());
            stmt.setString(4, mascota.getComentarios());
            stmt.setString(5, mascota.getDocumentoDueno());
            stmt.setInt(6, mascota.getIdMascota());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarMascota(int idMascota) {
        String sql = "DELETE FROM mascota WHERE id_mascota = ?";
        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMascota);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MascotaVo> listarMascotas() {
        List<MascotaVo> mascotas = new ArrayList<>();
        String sql = "SELECT m.*, p.nombre as nombre_dueno FROM mascota m " +
                "LEFT JOIN persona p ON m.documento_dueno = p.documento " +
                "ORDER BY m.nombre";

        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MascotaVo mascota = new MascotaVo();
                mascota.setIdMascota(rs.getInt("id_mascota"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setRaza(rs.getString("raza"));
                mascota.setSexo(rs.getString("sexo"));
                mascota.setComentarios(rs.getString("comentarios"));
                mascota.setDocumentoDueno(rs.getString("documento_dueno"));
                mascota.setNombreDueno(rs.getString("nombre_dueno"));
                mascotas.add(mascota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascotas;
    }

    public boolean existeDueno(String documento) {
        String sql = "SELECT COUNT(*) FROM persona WHERE documento = ?";
        try (Connection conn = ConexionDatabase.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, documento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
