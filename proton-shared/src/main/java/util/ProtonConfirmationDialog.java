package util;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class ProtonConfirmationDialog extends Dialog {

    private final Button confirmButton = new Button(ProtonStrings.CONFIRM, VaadinIcon.CHECK.create());
    private final Button cancelButton = new Button(ProtonStrings.REVERT, VaadinIcon.CLOSE.create());
    private final String messageText;

    public ProtonConfirmationDialog(String text) {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);
        setModal(true);
        setWidth("400px");
        setHeight("150px");
        this.messageText = text;
    }

    public void showConfirmation(ComponentEventListener<ClickEvent<Button>> clickListener) {

        confirmButton.getElement().getThemeList().add("error");
        confirmButton.addClickListener(clickListener);

        cancelButton.getElement().getThemeList().add("primary");
        cancelButton.addClickListener(e -> close());

        HorizontalLayout horizLayout = new HorizontalLayout(confirmButton, cancelButton);
        VerticalLayout vertLayout = new VerticalLayout(new Text(messageText), horizLayout);
        add(vertLayout);
        open();
    }

}
