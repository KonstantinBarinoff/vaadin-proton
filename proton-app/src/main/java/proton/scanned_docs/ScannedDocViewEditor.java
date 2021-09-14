package proton.scanned_docs;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import proton.base.BaseDictViewEditor;
import util.ProtonStrings;

@SpringComponent
@Scope("prototype")
@Slf4j
public class ScannedDocViewEditor extends BaseDictViewEditor<ScannedDoc, ScannedDocService> {

    @Autowired
    FileStorageService fileStorageService;

    public ScannedDocViewEditor(ScannedDocService service) {
        log.debug("CONSTRUCTOR");
        this.service = service;
        binder = new Binder<>(ScannedDoc.class);
        setupView();
    }

    @Override
    public HorizontalLayout setupButtons() {
        Button downloadButton = new Button(ProtonStrings.DOWNLOAD, VaadinIcon.DOWNLOAD.create());
        //downloadButton.addClickListener(e -> closeEditor());
        return new HorizontalLayout(super.setupButtons(), downloadButton);
    }


    @Override
    public void setupFields() {
        super.setupFields();

        form.add(getUploadField());
    }

    public Upload getUploadField() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload uploadField = new Upload(buffer);

        Div output = new Div(new Text("(no image file uploaded yet)"));
        add(uploadField, output);

        // Configure upload component
        uploadField.setAcceptedFileTypes("image/jpeg", "image/png", "image/tiff");
        uploadField.addSucceededListener(event -> {
            try {
                nameField.setValue(event.getFileName());
                byte[] bytes = IOUtils.toByteArray(buffer.getInputStream());
                item = fileStorageService.save(bytes, event.getFileName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        uploadField.addFailedListener(event -> {
            output.removeAll();
            output.add(new Text("Upload failed: " + event.getReason()));
        });
        return uploadField;
    }


}
