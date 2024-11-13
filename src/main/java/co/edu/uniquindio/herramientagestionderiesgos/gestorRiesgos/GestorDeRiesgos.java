package co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Collections;
import java.util.Comparator;

public class GestorDeRiesgos {
    private ObservableList<Riesgo> listaRiesgos = FXCollections.observableArrayList();
    private int contadorId = 1;

    public void agregarRiesgo(String nombre, String descripcion, int probabilidad, int impacto, int nivelRiesgo) {
        Riesgo nuevoRiesgo = new Riesgo(contadorId++, nombre, descripcion, probabilidad, impacto, nivelRiesgo);
        listaRiesgos.add(nuevoRiesgo);
        ordenarRiesgosPorNivel(); // Ordena la lista después de agregar un riesgo
    }

    public void ordenarRiesgosPorNivel() {
        listaRiesgos.sort(Comparator.comparingInt(Riesgo::getNivelRiesgo).reversed());
    }

    public ObservableList<Riesgo> getListaRiesgos() {
        return listaRiesgos;
    }

    public void eliminarRiesgo(Riesgo riesgo) {
        listaRiesgos.remove(riesgo);
    }
}
