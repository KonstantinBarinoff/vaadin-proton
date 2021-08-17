package util;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class ProtonWarningDialog extends Dialog {

    public ProtonWarningDialog(String text) {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);
        setModal(true);
        setWidth("600px");
        setHeight("400px");
        Button closeButton = new Button(ProtonStrings.CLOSE, VaadinIcon.CLOSE.create());
        closeButton.getElement().getThemeList().add("primary");
        closeButton.addClickListener(e -> close());

        HorizontalLayout horizLayout = new HorizontalLayout(closeButton);
        VerticalLayout vertLayout = new VerticalLayout(new Text(text), horizLayout);
        add(vertLayout);
        open();
    }

}
