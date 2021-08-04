package proton.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;
import java.io.*;

@Slf4j
@Route(value = "upload-image-to-file", layout = MainView.class)
@PageTitle("Upload image to File")
public class UploadImageToFile extends VerticalLayout {

    private File file;
    private String originalFileName;
    private String mimeType;

    UploadImageToFile() {
	Upload upload = new Upload(this::receiveUpload);
	Div output = new Div(new Text("(no image file uploaded yet)"));
	add(upload, output);

	// Configure upload component
	upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
	upload.addSucceededListener(e -> {
	    output.removeAll();
	    output.add(
		    new Text("Uploaded: " + originalFileName + " to " + file.getAbsolutePath() + "Type: " + mimeType));
	    output.add(new Image(new StreamResource(this.originalFileName, this::loadFile), "Uploaded image"));
	});
	upload.addFailedListener(e -> {
	    output.removeAll();
	    output.add(new Text("Upload failed: " + e.getReason()));
	});
    }

    /** Load a file from local filesystem. */
    public InputStream loadFile() {
	try {
	    return new FileInputStream(file);
	} catch (FileNotFoundException e) {
	    log.warn("Failed to create InputStream for: {}", this.file.getAbsolutePath(), e);
	}
	return null;
    }

    /** Receive a uploaded file to a file. */
    public OutputStream receiveUpload(String originalFileName, String MIMEType) {
	this.originalFileName = originalFileName;
	this.mimeType = MIMEType;
	try {
	    // Create a temporary file for example, you can provide your file here.
	    this.file = File.createTempFile("prefix-", "-suffix");
	    file.deleteOnExit();
	    return new FileOutputStream(file);
	} catch (FileNotFoundException e) {
	    log.warn("Failed to create InputStream for: {}", this.file.getAbsolutePath(), e);
	} catch (IOException e) {
	    log.warn("Failed to create InputStream for: {}", this.file.getAbsolutePath(), e);
	}

	return null;
    }
}