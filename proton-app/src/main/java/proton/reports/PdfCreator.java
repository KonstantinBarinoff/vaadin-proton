package proton.reports;

public class PdfCreator extends ReportFactory {

    @Override
   protected Report createReport() {
       return new Pdf();
   }

}
