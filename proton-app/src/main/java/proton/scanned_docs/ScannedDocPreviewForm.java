package proton.scanned_docs;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import proton.custom_dictionary.CustomDictViewEditor.ChangeHandler;
import util.ProtonStrings;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@SpringComponent
@UIScope
public class ScannedDocPreviewForm extends Dialog implements KeyNotifier {

    FileStorageService fileStorageService;
    ScannedDocService scannedDocService;

    private ChangeHandler changeHandler;
    private final FormLayout form = new FormLayout();
    private final Button closeButton = new Button(ProtonStrings.CLOSE, VaadinIcon.CLOSE.create());
    private final Button downloadButton = new Button(ProtonStrings.DOWNLOAD, VaadinIcon.DOWNLOAD.create());
    private Anchor downloadAnchor;

    @Autowired
    public ScannedDocPreviewForm(Long id, FileStorageService fileStorageService, ScannedDocService scannedDocService) throws IOException {
        this.fileStorageService = fileStorageService;
        this.scannedDocService = scannedDocService;
        Image image;
        byte[] bytes;
        FileSystemResource fileSystemResource = fileStorageService.findById(id);
        bytes = fileSystemResource.getInputStream().readAllBytes();

        if (bytes != null) {
            Optional<ScannedDoc> scannedDoc = scannedDocService.findById(id);

            StreamResource streamResource = new StreamResource(scannedDoc.get().getName(), () -> new ByteArrayInputStream(bytes));
            image = new Image(streamResource, "Image... ");

            streamResource.setContentType("image/tiff");
            downloadAnchor = new Anchor(streamResource, "");
            downloadAnchor.getElement().setAttribute("download", true);
            downloadAnchor.add(downloadButton);
            form.add(image);
            form.add(downloadAnchor);
        }
        setupLayout();
        log.debug("CONSTRUCTOR");
    }

    public void setupLayout() {
        setModal(true);
        setCloseOnOutsideClick(false);
        setCloseOnEsc(true);
        add(form);
        add(new Paragraph());
        add(setupButtons());
    }

    public HorizontalLayout setupButtons() {
        closeButton.addClickListener(e -> closeEditor());
        return new HorizontalLayout(downloadAnchor, closeButton);
    }

    void closeEditor() {
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
