package co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos;

import co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos.Riesgo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestorDeRiesgos {
    private ObservableList<Riesgo> listaRiesgos = FXCollections.observableArrayList();
    private int contadorId = 1;

    public void agregarRiesgo(String nombre, String descripcion, int probabilidad, int impacto, int nivelRiesgo) {
        Riesgo nuevoRiesgo = new Riesgo(contadorId++, nombre, descripcion, probabilidad, impacto,nivelRiesgo);
        listaRiesgos.add(nuevoRiesgo);
    }

    public ObservableList<Riesgo> getListaRiesgos() {
        return listaRiesgos;
    }

    public void eliminarRiesgo(Riesgo riesgo) {
        listaRiesgos.remove(riesgo);
    }
}
