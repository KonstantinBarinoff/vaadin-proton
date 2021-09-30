package proton.reports;

import com.vaadin.flow.server.StreamResource;
import proton.base.BaseEntity;

import java.util.List;

/**
 * Абстрактный класс, включающий в себя фабричный метод для создания отчётов различных форматов (Excel, PDF)
 */
public abstract class ReportFactory<E extends BaseEntity> {

    GridReport<E> report = createGridReport();

    /**
     * Фабричный метод
     * @return Экземпляр, наследующий класс GridReport
     */
    protected abstract GridReport<E> createGridReport();

    /**
     * @param reportContent Коллекция данных для отчета
     */
    public void setReportContent(List <E> reportContent) {
        report.setReportContent(reportContent);
    }

    /**
     * @return Ресурс отчета (динамически сгенерированные данные)
     */
    public StreamResource getReportResource() {
        return report.getReportResource();
    }
}
