package proton.reports;

import com.vaadin.flow.server.StreamResource;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import proton.base.BaseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс определяющий общий интерфейс для различных форматов отчётов
 * (для использования фабричным методом) и содержащий общие методы для работы с ними.
 */
@Getter
@Setter
public abstract class GridReport<E extends BaseEntity> {
    private List<E> reportContent;

    /**
     * @see ReportFactory#getReportResource()
     */
    public abstract StreamResource getReportResource();

    /**
     * @return Скомпилированный шаблон отчёта
     */
    private JasperReport reportTemplate()  {
        try {
            JasperDesign jasperDesign = new JasperDesignBuilder().build();
            return JasperCompileManager.compileReport(jasperDesign);
        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return Объект-отчет для дальнейшего его представления в различных форматах (Excel, PDF...)
     */
    public JasperPrint reportObject() {
        try {
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportContent);
            Map<String, Object> map = new HashMap<>();
            return JasperFillManager.fillReport(reportTemplate(), map, dataSource);
        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }
}
