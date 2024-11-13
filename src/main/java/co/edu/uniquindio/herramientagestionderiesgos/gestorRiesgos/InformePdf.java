package co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.io.image.ImageDataFactory;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.PieChart;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class InformePdf {

    public void generarInforme(List<Riesgo> listaRiesgos, PieChart graficoRiesgos) throws IOException {
        // Crear documento PDF
        String destino = "InformeRiesgos.pdf";
        PdfWriter writer = new PdfWriter(destino);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Título
        document.add(new Paragraph("Informe de Evaluación y Seguimiento de Riesgos").setBold());

        // Crear tabla
        Table table = new Table(3);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Nivel de Riesgo");

        // Llenar la tabla con los datos de riesgos
        for (Riesgo riesgo : listaRiesgos) {
            table.addCell(String.valueOf(riesgo.getId()));
            table.addCell(riesgo.getNombre());
            table.addCell(String.valueOf(riesgo.getNivelRiesgo()));
        }

        document.add(table);

        // Capturar el gráfico como imagen y agregarlo al PDF
        File imagenTemporal = new File("graficoRiesgos.png");
        ImageIO.write(SwingFXUtils.fromFXImage(graficoRiesgos.snapshot(null, null), null), "png", imagenTemporal);

        // Agregar imagen al documento
        com.itextpdf.layout.element.Image imagenGrafico = new com.itextpdf.layout.element.Image(
                com.itextpdf.io.image.ImageDataFactory.create(imagenTemporal.getAbsolutePath()));
        document.add(new Paragraph("Distribución de Riesgos").setBold());
        document.add(imagenGrafico);

        // Cerrar documento
        document.close();

        // Eliminar el archivo temporal
        imagenTemporal.delete();
    }
}
