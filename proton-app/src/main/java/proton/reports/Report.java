package proton.reports;

import com.vaadin.flow.server.StreamResource;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс определяющий общий интерфейс для различных форматов отчётов
 * (для использования фабричным методом) и содержащий общие методы для работы с ними.
 */
@Getter
@Setter
public abstract class Report {
    private String reportForm;
    private List reportContent;

    /**
     * @see ReportFactory#getReportResource()
     */
    public abstract StreamResource getReportResource();

    /**
     * Компиляция файла отчёта *.jasper
     * @return Путь к скомпилированному файлу
     * @throws FileNotFoundException
     * @throws JRException
     */
    private String reportFile() throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile(reportForm);
        return JasperCompileManager.compileReportToFile(file.getAbsolutePath());
    }

    /**
     * @return Объект отчета для дальнейшего его представления в различных форматах (Excel, PDF...)
     */
    public JasperPrint reportObject() {
        try {
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportContent);
            Map<String, Object> map = new HashMap<>();
            return JasperFillManager.fillReport(reportFile(), map, dataSource);
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
