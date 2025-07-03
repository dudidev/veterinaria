package modelo.vo;

public class MascotaVo {
    private int idMascota;
    private String nombre;
    private String raza;
    private String sexo;
    private String comentarios;
    private String documentoDueno;
    private String nombreDueno; // Para mostrar en la información

    public MascotaVo() {}

    public MascotaVo(int idMascota, String nombre, String raza, String sexo, String comentarios, String documentoDueno, String nombreDueno) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.raza = raza;
        this.sexo = sexo;
        this.comentarios = comentarios;
        this.documentoDueno = documentoDueno;
        this.nombreDueno = nombreDueno;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getDocumentoDueno() {
        return documentoDueno;
    }

    public void setDocumentoDueno(String documentoDueno) {
        this.documentoDueno = documentoDueno;
    }

    public String getNombreDueno() {
        return nombreDueno;
    }

    public void setNombreDueno(String nombreDueno) {
        this.nombreDueno = nombreDueno;
    }

    @Override
    public String toString() {
        return
                "idMascota=" + idMascota +
                ", nombre='" + nombre + '\'' +
                ", raza='" + raza + '\'' +
                ", sexo='" + sexo + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", documentoDueno='" + documentoDueno + '\'' +
                ", nombreDueno='" + nombreDueno + '\'' +
                '}';
    }
}
