package controlador;

import modelo.dao.PersonaDAO;
import modelo.vo.PersonaVo;

import java.util.List;

public class PersonaController {
    private PersonaDAO personaDAO;

    public PersonaController() {
        this.personaDAO = new PersonaDAO();
    }

    public String registrarPersona(String documento, String nombre, String telefono, String email) {
        if (documento.trim().isEmpty() || nombre.trim().isEmpty()) {
            return "Error: El documento y nombre son obligatorios.";
        }

        PersonaVo persona = new PersonaVo(documento, nombre, telefono, email);
        boolean resultado = personaDAO.registrarUsuario(persona);

        if (resultado) {
            return "Persona registrada exitosamente:\n" + persona;
        } else {
            return "Error al registrar la persona. Verifique que el documento no esté duplicado.";
        }
    }

    public String consultarPersona(String documento) {
        if (documento.trim().isEmpty()) {
            return "Error: Debe ingresar un documento.";
        }

        PersonaVo persona = personaDAO.consultarPersona(documento);
        if (persona != null) {
            return "Persona encontrada:\n" + persona;
        } else {
            return "No se encontró ninguna persona con el documento: " + documento;
        }
    }

    public PersonaVo obtenerPersona(String documento) {
        return personaDAO.consultarPersona(documento);
    }

    public String actualizarPersona(String documento, String nombre, String telefono, String email) {
        if (documento.trim().isEmpty() || nombre.trim().isEmpty()) {
            return "Error: El documento y nombre son obligatorios.";
        }

        PersonaVo persona = personaDAO.consultarPersona(documento);
        if (persona == null) {
            return "Error: No se encontró la persona con documento: " + documento;
        }

        persona.setNombre(nombre);
        persona.setTelefono(telefono);
        persona.setEmail(email);

        boolean resultado = personaDAO.actualizarPersona(persona);
        if (resultado) {
            return "Persona actualizada exitosamente:\n" + persona;
        } else {
            return "Error al actualizar la persona.";
        }
    }

    public String eliminarPersona(String documento) {
        if (documento.trim().isEmpty()) {
            return "Error: Debe ingresar un documento.";
        }

        PersonaVo persona = personaDAO.consultarPersona(documento);
        if (persona == null) {
            return "Error: No se encontró la persona con documento: " + documento;
        }

        boolean resultado = personaDAO.eliminarPersona(documento);
        if (resultado) {
            return "Persona eliminada exitosamente: " + persona.getNombre();
        } else {
            return "Error al eliminar la persona.";
        }
    }

    public String listarPersonas() {
        List<PersonaVo> personas = personaDAO.listarPersonas();
        if (personas.isEmpty()) {
            return "No se encontraron personas registradas.";
        }

        StringBuilder lista = new StringBuilder("Lista de personas registradas:\n\n");
        for (PersonaVo persona : personas) {
            lista.append(persona.toString()).append("\n-------------------\n");
        }
        return lista.toString();
    }
}
