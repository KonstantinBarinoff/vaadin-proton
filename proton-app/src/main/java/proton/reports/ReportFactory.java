package proton.reports;

import com.vaadin.flow.server.StreamResource;
import java.util.List;

/**
 * Абстрактный класс, включающий в себя фабричный метод для создания отчётов различных форматов (Excel, PDF)
 */
public abstract class ReportFactory {

    Report report = createReport();

    /**
     * Фабричный метод
     * @return Экземпляр, наследующий класс Report
     */
    protected abstract Report createReport();

    /**
     * Файл-разметка отчета *.jrxml
     * @param reportForm Путь к файлу отчёта (например "classpath:FirstReport.jrxml")
     */
    //TODO 27.09.2021 ovsyannikov_sn: Пока жестко указывается путь к файлу.
    // Требуется доработка, для автоматического выбора формы отчета, зависящей от
    // текущего представления. Разобраться с фиксированными формами отчёта.
    public void setReportForm(String reportForm) {
        report.setReportForm(reportForm);
    }

    /**
     * @param reportContent Коллекция данных для отчета
     */
    public void setReportContent(List reportContent) {
        report.setReportContent(reportContent);
    }

    /**
     * @return Ресурс отчета (динамически сгенерированные данные)
     */
    public StreamResource getReportResource() {
        return report.getReportResource();
    }
}
