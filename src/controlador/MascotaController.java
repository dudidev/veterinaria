package controlador;

import modelo.dao.MascotaDAO;
import modelo.vo.MascotaVo;

import java.util.List;

public class MascotaController {
    private MascotaDAO mascotaDAO;

    public MascotaController() {
        this.mascotaDAO = new MascotaDAO();
    }

    public String registrarMascota(String nombre, String raza, String sexo, String comentarios, String documentoDueno) {
        if (nombre.trim().isEmpty() || documentoDueno.trim().isEmpty()) {
            return "Error: El nombre de la mascota y documento del dueño son obligatorios.";
        }

        if (!mascotaDAO.existeDueno(documentoDueno)) {
            return "Error: No existe una persona registrada con el documento: " + documentoDueno;
        }

        MascotaVo mascota = new MascotaVo();
        mascota.setNombre(nombre);
        mascota.setRaza(raza);
        mascota.setSexo(sexo);
        mascota.setComentarios(comentarios);
        mascota.setDocumentoDueno(documentoDueno);
        boolean resultado = mascotaDAO.registrarMascota(mascota);

        if (resultado) {
            // Obtener la mascota recién registrada para mostrar información completa
            List<MascotaVo> mascotas = mascotaDAO.listarMascotas();
            MascotaVo mascotaRegistrada = mascotas.stream()
                    .filter(m -> m.getNombre().equals(nombre) && m.getDocumentoDueno().equals(documentoDueno))
                    .reduce((first, second) -> second) // Obtener la última registrada
                    .orElse(null);

            if (mascotaRegistrada != null) {
                return "Mascota registrada exitosamente:\n" + mascotaRegistrada;
            } else {
                return "Mascota registrada exitosamente.";
            }
        } else {
            return "Error al registrar la mascota.";
        }
    }

    public String consultarMascota(String idMascotaStr) {
        if (idMascotaStr.trim().isEmpty()) {
            return "Error: Debe ingresar el ID de la mascota.";
        }

        try {
            int idMascota = Integer.parseInt(idMascotaStr);
            MascotaVo mascota = mascotaDAO.consultarMascota(idMascota);

            if (mascota != null) {
                if (mascota.getNombreDueno() == null) {
                    return "Mascota encontrada:\n" + mascota +
                            "\nADVERTENCIA: El dueño no se encuentra registrado.";
                } else {
                    return "Mascota encontrada:\n" + mascota;
                }
            } else {
                return "No se encontró ninguna mascota con el ID: " + idMascota;
            }
        } catch (NumberFormatException e) {
            return "Error: El ID de la mascota debe ser un número válido.";
        }
    }

    public MascotaVo obtenerMascota(String idMascotaStr) {
        try {
            int idMascota = Integer.parseInt(idMascotaStr);
            return mascotaDAO.consultarMascota(idMascota);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String actualizarMascota(String idMascotaStr, String nombre, String raza, String sexo, String comentarios, String documentoDueno) {
        if (idMascotaStr.trim().isEmpty() || nombre.trim().isEmpty() || documentoDueno.trim().isEmpty()) {
            return "Error: El ID, nombre de la mascota y documento del dueño son obligatorios.";
        }

        try {
            int idMascota = Integer.parseInt(idMascotaStr);

            if (!mascotaDAO.existeDueno(documentoDueno)) {
                return "Error: No existe una persona registrada con el documento: " + documentoDueno;
            }

            MascotaVo mascota = mascotaDAO.consultarMascota(idMascota);
            if (mascota == null) {
                return "Error: No se encontró la mascota con ID: " + idMascota;
            }

            mascota.setNombre(nombre);
            mascota.setRaza(raza);
            mascota.setSexo(sexo);
            mascota.setComentarios(comentarios);
            mascota.setDocumentoDueno(documentoDueno);

            boolean resultado = mascotaDAO.actualizarMascota(mascota);
            if (resultado) {
                // Obtener la mascota actualizada con información del dueño
                mascota = mascotaDAO.consultarMascota(idMascota);
                return "Mascota actualizada exitosamente:\n" + mascota.toString();
            } else {
                return "Error al actualizar la mascota.";
            }
        } catch (NumberFormatException e) {
            return "Error: El ID de la mascota debe ser un número válido.";
        }
    }

    public String eliminarMascota(String idMascotaStr) {
        if (idMascotaStr.trim().isEmpty()) {
            return "Error: Debe ingresar el ID de la mascota.";
        }

        try {
            int idMascota = Integer.parseInt(idMascotaStr);
            MascotaVo mascota = mascotaDAO.consultarMascota(idMascota);

            if (mascota == null) {
                return "Error: No se encontró la mascota con ID: " + idMascota;
            }

            boolean resultado = mascotaDAO.eliminarMascota(idMascota);
            if (resultado) {
                return "Mascota eliminada exitosamente: " + mascota.getNombre();
            } else {
                return "Error al eliminar la mascota.";
            }
        } catch (NumberFormatException e) {
            return "Error: El ID de la mascota debe ser un número válido.";
        }
    }

    public String listarMascotas() {
        List<MascotaVo> mascotas = mascotaDAO.listarMascotas();
        if (mascotas.isEmpty()) {
            return "No se encontraron mascotas registradas.";
        }

        StringBuilder lista = new StringBuilder("Lista de mascotas registradas:\n\n");
        for (MascotaVo mascota : mascotas) {
            if (mascota.getNombreDueno() == null) {
                lista.append(mascota)
                        .append("ADVERTENCIA: El dueño no se encuentra registrado.\n")
                        .append("-------------------\n");
            } else {
                lista.append(mascota).append("\n");
            }
        }
        return lista.toString();
    }
}
