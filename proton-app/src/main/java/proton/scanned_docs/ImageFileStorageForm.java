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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Slf4j
@SpringComponent
@UIScope
public class ImageFileStorageForm extends Dialog implements KeyNotifier {

    @Autowired
    FileStorageService fileStorageService;


    private String imagePath;
    private ChangeHandler changeHandler;
    private final FormLayout form = new FormLayout();
    private final Button closeButton = new Button(ProtonStrings.CLOSE, VaadinIcon.CLOSE.create());
    private final Button downloadButton = new Button(ProtonStrings.DOWNLOAD, VaadinIcon.DOWNLOAD.create());
    private Anchor downloadAnchor;

    @Autowired
    public ImageFileStorageForm(Long id, String imagePath, FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
        this.imagePath = imagePath;

//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        Query query = em.createQuery("SELECT u.image FROM CustomDict u WHERE u.id = :id");
//        query.setParameter("id", id);

        Image image;

        StreamResource streamResource;
        FileSystemResource fileSystemResource = fileStorageService.find(id);

        byte[] bytes = new byte[0];
        try {
            bytes = fileSystemResource.getInputStream().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bytes != null) {
            byte[] finalBytes = bytes;
            streamResource = new StreamResource("image.jpg", () -> new ByteArrayInputStream(finalBytes));
            image = new Image(streamResource, "Image... ");

            streamResource.setContentType("image/jpg");
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
        log.debug("IMAGE PATH : {}", imagePath);

        add(form);
        add(new Paragraph());
        add(setupButtons());
    }

    public byte[] extractBytes(String ImageName) throws IOException {
        // open image
        BufferedImage bufferedImage = ImageIO.read(new File(ImageName));

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
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
