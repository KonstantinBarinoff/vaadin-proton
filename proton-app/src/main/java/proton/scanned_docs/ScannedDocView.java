package proton.scanned_docs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.base.BaseDictView;
import proton.views.MainView;
import util.ProtonProperties;
import util.ProtonStrings;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "scan-docs-view", layout = MainView.class)
@PageTitle("Сканированные документы")
public class ScannedDocView extends BaseDictView<ScannedDoc, ScannedDocService> {

    @Autowired
    ProtonProperties properties;

    @Autowired
    FileStorageService fileStorageService;


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
            ImageFileStorageForm imageFileStorageForm = new ImageFileStorageForm(id, properties.getFileStoragePath(), fileStorageService);
            imageFileStorageForm.setChangeHandler(imageFileStorageForm::close);
            imageFileStorageForm.open();
        });
        return new HorizontalLayout(super.setupTopLayout(), imageButton);
    }

    @Override
    public void setupGrid() {
        super.setupGrid();
        grid.addColumn(ScannedDoc::getFilePath)
//                .setKey("id")       // Назначаем ключи колонкам для доступа к колонкам из наследников
                .setHeader("Файл")
                .setFlexGrow(100);
    }


    @Override
    protected ScannedDoc getNewItem() {
        return new ScannedDoc();
    }

}
