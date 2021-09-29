package proton.reports;

import com.vaadin.flow.server.StreamResource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Pdf extends Report {

    /**
     * @see ReportFactory#getReportResource()
     */
    @Override
    public StreamResource getReportResource() {
        return new StreamResource("report.pdf", this::pdfExportStream);
    }

    /**
     * @return
     */
    private InputStream pdfExportStream() {
        try {
            return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(reportObject()));
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }

}
