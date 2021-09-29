package proton.reports;

public class ExcelCreator extends ReportFactory {

    @Override
    protected Report createReport() {
       return new Excel();
   }
}
