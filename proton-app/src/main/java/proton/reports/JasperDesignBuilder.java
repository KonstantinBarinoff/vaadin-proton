package proton.reports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;
import net.sf.jasperreports.engine.type.TextAdjustEnum;

import java.time.LocalDateTime;

public class JasperDesignBuilder {
    public JasperDesign build() throws JRException {
        JasperDesign reportDesign = new JasperDesign();
        reportDesign.setName("Dynamic report");
        reportDesign.setPageWidth(842);
        reportDesign.setPageHeight(595);
        reportDesign.setOrientation(OrientationEnum.LANDSCAPE);
        reportDesign.setColumnWidth(802);
        reportDesign.setLeftMargin(20);
        reportDesign.setRightMargin(20);
        reportDesign.setTopMargin(20);
        reportDesign.setProperty("net.sf.jasperreports.export.xls.remove.empty.space.between.rows", "true");
        reportDesign.setProperty("net.sf.jasperreports.export.xls.remove.empty.space.between.columns", "true");
        reportDesign.setProperty("net.sf.jasperreports.export.xls.white.page.background", "false");
        reportDesign.setProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");
        reportDesign.setProperty("net.sf.jasperreports.export.xls.ignore.graphics", "false");
        reportDesign.setProperty("net.sf.jasperreports.page.break.no.pagination", "apply");
        reportDesign.setProperty("net.sf.jasperreports.export.xls.one.page.per.sheet", "false");
        reportDesign.setProperty("net.sf.jasperreports.print.keep.full.text", "true");
        reportDesign.setProperty("net.sf.jasperreports.exports.xls.font.size.fix.enabled", "false");

        JRDesignStyle reportStyle = new JRDesignStyle();
        reportStyle.setName("Test style");
        reportStyle.setDefault(true);
        reportStyle.setFontName("Arial");
        reportStyle.setFontSize(10f);
        reportStyle.setPdfFontName("/fonts/arial.ttf");
        reportStyle.setPdfEncoding("Cp1251");
        reportStyle.setPdfEmbedded(true);
        reportDesign.addStyle(reportStyle);

        JRDesignField fieldId = new JRDesignField();
        fieldId.setName("id");
        fieldId.setValueClass(Long.class);
        reportDesign.addField(fieldId);

        JRDesignField fieldName = new JRDesignField();
        fieldName.setName("name");
        fieldName.setValueClass(String.class);
        reportDesign.addField(fieldName);

        JRDesignField fieldDescription = new JRDesignField();
        fieldDescription.setName("description");
        fieldDescription.setValueClass(String.class);
        reportDesign.addField(fieldDescription);

        JRDesignField fieldCreatedAt = new JRDesignField();
        fieldCreatedAt.setName("createdAt");
        fieldCreatedAt.setValueClass(LocalDateTime.class);
        reportDesign.addField(fieldCreatedAt);

        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(20);
        detailBand.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignTextField textFieldId = new JRDesignTextField();
        textFieldId.setTextAdjust(TextAdjustEnum.SCALE_FONT);
        textFieldId.setMode(ModeEnum.TRANSPARENT);
        textFieldId.setX(0);
        textFieldId.setY(0);
        textFieldId.setWidth(50);
        textFieldId.setHeight(20);
        textFieldId.setExpression(new JRDesignExpression("$F{id}"));
        detailBand.addElement(textFieldId);

        JRDesignTextField textFieldName = new JRDesignTextField();
        textFieldName.setTextAdjust(TextAdjustEnum.SCALE_FONT);
        textFieldName.setMode(ModeEnum.TRANSPARENT);
        textFieldName.setX(60);
        textFieldName.setY(0);
        textFieldName.setWidth(200);
        textFieldName.setHeight(20);
        textFieldName.setExpression(new JRDesignExpression("$F{name}"));
        detailBand.addElement(textFieldName);

        JRDesignTextField textFieldDescription = new JRDesignTextField();
        textFieldDescription.setTextAdjust(TextAdjustEnum.SCALE_FONT);
        textFieldDescription.setMode(ModeEnum.TRANSPARENT);
        textFieldDescription.setX(260);
        textFieldDescription.setY(0);
        textFieldDescription.setWidth(200);
        textFieldDescription.setHeight(20);
        textFieldDescription.setExpression(new JRDesignExpression("$F{description}"));
        detailBand.addElement(textFieldDescription);

        JRDesignTextField textFieldCreatedAt = new JRDesignTextField();
        textFieldCreatedAt.setTextAdjust(TextAdjustEnum.SCALE_FONT);
        textFieldCreatedAt.setMode(ModeEnum.TRANSPARENT);
        textFieldCreatedAt.setX(460);
        textFieldCreatedAt.setY(0);
        textFieldCreatedAt.setWidth(100);
        textFieldCreatedAt.setHeight(20);
        textFieldCreatedAt.setExpression(new JRDesignExpression("$F{createdAt}"));
        detailBand.addElement(textFieldCreatedAt);

        ((JRDesignSection) reportDesign.getDetailSection()).addBand(detailBand);

        return reportDesign;
    }

}
