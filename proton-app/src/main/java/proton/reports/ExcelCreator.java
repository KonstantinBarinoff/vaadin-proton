package proton.reports;

import proton.base.BaseEntity;

public class ExcelCreator <E extends BaseEntity> extends ReportFactory<E> {

    @Override
    protected GridReport<E> createGridReport() {
       return new ExcelReport<>();
   }
}
