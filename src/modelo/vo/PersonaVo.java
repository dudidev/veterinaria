package modelo.vo;

public class PersonaVo {
    private String documento;
    private String nombre;
    private String telefono;
    private String email;

    public PersonaVo() {}

    public PersonaVo(String documento, String nombre, String telefono, String email) {
        this.documento = documento;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "PersonaVo{" +
                "documento='" + documento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
