package co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos;

public class Riesgo implements Comparable<Riesgo> {

    private int id;
    private String nombre;
    private String descripcion;
    private int probabilidad; // Valor de 1 a 5
    private int impacto; // Valor de 1 a 5

    private int nivelRiesgo;

    public Riesgo(int id, String nombre, String descripcion, int probabilidad, int impacto, int nivelRiesgo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.probabilidad = probabilidad;
        this.impacto = impacto;
        this.nivelRiesgo = nivelRiesgo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(int probabilidad) {
        this.probabilidad = probabilidad;
    }

    public int getImpacto() {
        return impacto;
    }

    public void setImpacto(int impacto) {
        this.impacto = impacto;
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(int nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    @Override
    public int compareTo(Riesgo otroRiesgo) {

        return Integer.compare(otroRiesgo.getNivelRiesgo(), this.nivelRiesgo);
    }
}