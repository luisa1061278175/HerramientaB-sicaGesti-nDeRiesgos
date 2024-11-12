package co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RiesgoApp extends Application {
    private GestorDeRiesgos gestor = new GestorDeRiesgos();
    private TableView<Riesgo> tablaRiesgos = new TableView<>();

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Evaluación y Seguimiento de Riesgos");

        // Crear columnas para la tabla
        TableColumn<Riesgo, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));

        TableColumn<Riesgo, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombre()));

        TableColumn<Riesgo, Integer> colNivelRiesgo = new TableColumn<>("Nivel de Riesgo");
        colNivelRiesgo.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getNivelRiesgo()));

        tablaRiesgos.getColumns().addAll(colId, colNombre, colNivelRiesgo);

        // Crear campos de entrada
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del Riesgo");

        TextArea txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Descripción");
        txtDescripcion.setPrefRowCount(3);

        Spinner<Integer> spnProbabilidad = new Spinner<>(1, 5, 1);
        Spinner<Integer> spnImpacto = new Spinner<>(1, 5, 1);

        Button btnAgregar = new Button("Agregar Riesgo");
        btnAgregar.setOnAction(e -> agregarRiesgo(txtNombre, txtDescripcion, spnProbabilidad, spnImpacto));

        Button btnEliminar = new Button("Eliminar Riesgo Seleccionado");
        btnEliminar.setOnAction(e -> eliminarRiesgoSeleccionado());

        // Organizar layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Nombre:"), 0, 0);
        gridPane.add(txtNombre, 1, 0);
        gridPane.add(new Label("Descripción:"), 0, 1);
        gridPane.add(txtDescripcion, 1, 1);
        gridPane.add(new Label("Probabilidad:"), 0, 2);
        gridPane.add(spnProbabilidad, 1, 2);
        gridPane.add(new Label("Impacto:"), 0, 3);
        gridPane.add(spnImpacto, 1, 3);
        gridPane.add(btnAgregar, 1, 4);

        HBox hBox = new HBox(10, btnEliminar);
        VBox vBox = new VBox(10, gridPane, tablaRiesgos, hBox);
        vBox.setPadding(new Insets(10));


        Scene scene = new Scene(vBox, 600, 500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        primaryStage.show();
        primaryStage.setScene(scene);

        actualizarTabla();
    }

    private void agregarRiesgo(TextField txtNombre, TextArea txtDescripcion, Spinner<Integer> spnProbabilidad, Spinner<Integer> spnImpacto) {
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        int probabilidad = spnProbabilidad.getValue();
        int impacto = spnImpacto.getValue();

        gestor.agregarRiesgo(nombre, descripcion, probabilidad, impacto, probabilidad*impacto);

        txtNombre.clear();
        txtDescripcion.clear();
        spnProbabilidad.getValueFactory().setValue(1);
        spnImpacto.getValueFactory().setValue(1);

        actualizarTabla();
    }

    private void eliminarRiesgoSeleccionado() {
        Riesgo seleccionado = tablaRiesgos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            gestor.eliminarRiesgo(seleccionado);
            actualizarTabla();
        } else {
            mostrarAlerta("Seleccione un riesgo para eliminar.");
        }
    }

    private void actualizarTabla() {
        ObservableList<Riesgo> listaRiesgos = gestor.getListaRiesgos();
        tablaRiesgos.setItems(listaRiesgos);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

