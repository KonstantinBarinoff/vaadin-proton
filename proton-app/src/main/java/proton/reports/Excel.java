package proton.reports;

import com.vaadin.flow.server.StreamResource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Excel extends Report {

    /**
     * @see ReportFactory#getReportResource()
     */
    @Override
    public StreamResource getReportResource() {
        StreamResource resource = new StreamResource("report.xlsx", this::excelExportStream);
        return resource;
    }

    //TODO 27.09.2021 ovsyannikov_sn: Разобраться с потоками output -> input, без преобразований
    public InputStream excelExportStream() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Exporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(reportObject()));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }
}
