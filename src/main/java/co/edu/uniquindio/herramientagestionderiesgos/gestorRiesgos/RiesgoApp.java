package co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RiesgoApp extends Application {
    private GestorDeRiesgos gestor = new GestorDeRiesgos();
    private TableView<Riesgo> tablaRiesgos = new TableView<>();
    private PieChart graficoRiesgos;

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
        btnAgregar.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnAgregar.setOnAction(e -> agregarRiesgo(txtNombre, txtDescripcion, spnProbabilidad, spnImpacto));

        // Botones de eliminar y exportar
        Button btnEliminar = new Button("Eliminar Riesgo Seleccionado");
        btnEliminar.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnEliminar.setOnAction(e -> eliminarRiesgoSeleccionado());

        Button btnExportar = new Button("Exportar en PDF");
        btnExportar.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnExportar.setOnAction(e -> exportarPDF());

        HBox hBoxBotones = new HBox(10, btnEliminar, btnExportar);

        // Configuración del gráfico de riesgos
        graficoRiesgos = new PieChart();
        graficoRiesgos.setTitle("Distribución de Riesgos");
        actualizarGrafico();

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

        VBox vBox = new VBox(10, gridPane, tablaRiesgos, hBoxBotones, graficoRiesgos);
        vBox.setPadding(new Insets(10));

        // Configuración de la escena
        Scene scene = new Scene(vBox, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        actualizarTabla();
    }

    private void agregarRiesgo(TextField txtNombre, TextArea txtDescripcion, Spinner<Integer> spnProbabilidad, Spinner<Integer> spnImpacto) {
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        int probabilidad = spnProbabilidad.getValue();
        int impacto = spnImpacto.getValue();

        gestor.agregarRiesgo(nombre, descripcion, probabilidad, impacto, probabilidad * impacto);

        txtNombre.clear();
        txtDescripcion.clear();
        spnProbabilidad.getValueFactory().setValue(1);
        spnImpacto.getValueFactory().setValue(1);

        actualizarTabla();
        actualizarGrafico();
    }

    private void eliminarRiesgoSeleccionado() {
        Riesgo seleccionado = tablaRiesgos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            gestor.eliminarRiesgo(seleccionado);
            actualizarTabla();
            actualizarGrafico();
        } else {
            mostrarAlerta("Seleccione un riesgo para eliminar.");
        }
    }

    private void exportarPDF() {

            try {
                new InformePdf().generarInforme(gestor.getListaRiesgos(), graficoRiesgos);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        ;

    }

    private void actualizarTabla() {
        ObservableList<Riesgo> listaRiesgos = gestor.getListaRiesgos();
        tablaRiesgos.setItems(listaRiesgos);
    }

    private void actualizarGrafico() {
        ObservableList<PieChart.Data> datosGrafico = FXCollections.observableArrayList();

        for (Riesgo riesgo : gestor.getListaRiesgos()) {
            datosGrafico.add(new PieChart.Data(riesgo.getNombre(), riesgo.getNivelRiesgo()));
        }

        graficoRiesgos.setData(datosGrafico);
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
