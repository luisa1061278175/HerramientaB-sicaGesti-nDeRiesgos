package co.edu.uniquindio.herramientagestionderiesgos.gestorRiesgos;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class InformePdf {

    public void generarInforme(ObservableList<Riesgo> listaRiesgos) throws IOException {

        File file = new File("informe_riesgos.pdf");
        FileOutputStream fos = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(fos);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        Paragraph titulo = new Paragraph("Informe de Riesgos")
                .setFontSize(16);
        document.add(titulo);

        float[] columnWidths = {1, 3, 1, 1};
        Table table = new Table(columnWidths);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Probabilidad");
        table.addCell("Impacto");

        for (Riesgo riesgo : listaRiesgos) {
            table.addCell(String.valueOf(riesgo.getId()));
            table.addCell(riesgo.getNombre());
            table.addCell(String.valueOf(riesgo.getProbabilidad()));
            table.addCell(String.valueOf(riesgo.getImpacto()));
        }
        document.add(table);
        Paragraph texto = new Paragraph("Tabla de riesgos de la empresa");

        document.add(texto);

        document.close();

        System.out.println("Informe PDF generado exitosamente.");
    }
}

