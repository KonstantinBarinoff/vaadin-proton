package proton.views;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;

@Slf4j
@Route(value = "byte-array-to-image", layout = MainView.class)
@PageTitle("Byte Array to Image")

public class ByteArrayToImage extends VerticalLayout {
    String imageName = "META-INF/resources/img/sumotori.png"; // w/o path: src/main/resources/sumotori.png > target/classes/sumotori.png

    public ByteArrayToImage() {
	StreamResource streamResource;

	try {
	    streamResource = new StreamResource("sumotori.png",
		    () -> new ByteArrayInputStream(getImageAsByteArray()));
	} catch (Exception e) {
	    log.error("Error loading image {}", imageName);
	    return;

	}
	Image image = new Image(streamResource, "Sumotori");
	add(image);
    }

    private byte[] getImageAsByteArray() {
	try {
	    return IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream(imageName));
	} catch (IOException e) {
	    return null;
	}
    }
}