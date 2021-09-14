package proton.scanned_docs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import proton.base.BaseDictView;
import proton.views.MainView;
import util.ProtonStrings;
import util.ProtonWarningDialog;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "scan-docs-view", layout = MainView.class)
@PageTitle("Сканированные документы")
public class ScannedDocView extends BaseDictView<ScannedDoc, ScannedDocService> {

    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    ScannedDocService scannedDocService;

    @Autowired
    public ScannedDocView(ScannedDocService service, ScannedDocViewEditor editor) {
        log.debug("CONSTRUCTOR");
        this.service = service;
        this.editor = editor;
        editor.setOwnerDictView(this);
    }

    @PostConstruct
    public void init() {
        log.debug("POSTCONSTRUCT");
        setupView();
    }

    @Override
    public HorizontalLayout setupTopLayout() {
        Button imageButton = new Button(ProtonStrings.IMAGE, VaadinIcon.PICTURE.create());

        imageButton.addClickListener(e -> {
            Long id = grid.getSelectedItems().stream().findFirst().get().getId();
            try {
                ScannedDocPreviewForm scannedDocPreviewForm = new ScannedDocPreviewForm(id, fileStorageService, scannedDocService);
                scannedDocPreviewForm.setChangeHandler(scannedDocPreviewForm::close);
                scannedDocPreviewForm.open();
            } catch (Exception ex) {
                new ProtonWarningDialog("Ошибка загрузки файла.", NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
                ex.printStackTrace();
            }
        });
        return new HorizontalLayout(super.setupTopLayout(), imageButton);
    }

    @Override
    public void setupGrid() {
        super.setupGrid();
        grid.addColumn(ScannedDoc::getFileName)
                .setHeader("Файл")
                .setFlexGrow(100);
        grid.addColumn(ScannedDoc::getLevel1)
                .setHeader("L1")
                .setFlexGrow(1);
        grid.addColumn(ScannedDoc::getLevel2)
                .setHeader("L2")
                .setFlexGrow(1);
        grid.addColumn(ScannedDoc::getGuid)
                .setHeader("GUID")
                .setFlexGrow(200);
        grid.addColumn(ScannedDoc::getExtension)
                .setHeader("Ext")
                .setFlexGrow(1);
    }

    @Override
    protected ScannedDoc getNewItem() {
        return new ScannedDoc();
    }

}
