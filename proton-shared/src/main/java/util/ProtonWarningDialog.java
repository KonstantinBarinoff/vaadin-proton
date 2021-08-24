package util;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class ProtonWarningDialog extends Dialog {

    public ProtonWarningDialog(String message, String detail) {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);
        setModal(true);
        setWidth("600px");
        setHeight("400px");
        Button closeButton = new Button(ProtonStrings.CLOSE, VaadinIcon.CLOSE.create());
        closeButton.getElement().getThemeList().add("error");
        closeButton.addClickListener(e -> close());

        VerticalLayout base = new VerticalLayout();
//        base.getStyle().set("background-color", "#bccddd");
        base.setSizeFull();
        base.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout bl = new HorizontalLayout(closeButton);
        bl.setAlignItems(FlexComponent.Alignment.END);
//        bl.getStyle().set("background-color", "#bddddd");

        VerticalLayout ml = new VerticalLayout(new Text(message));
//        ml.getStyle().set("background-color", "#cddddd");

        VerticalLayout dl = new VerticalLayout(new Text(detail));
//        dl.getStyle().set("background-color", "#addddd");

        base.add(ml);
//        base.add(new Html("<span>&nbsp;</span>")); // spacer
        base.add(dl);
//        base.add(new Html("<span>&nbsp;</span>")); // spacer
        base.add(bl);
        base.setHorizontalComponentAlignment(FlexComponent.Alignment.END, bl);
        add(base);
        open();
    }

}




//        hl.getStyle().set("background-color", "#dddddd");
//        add(new Text(caption));
//        add(hl);
//        add(new Html("<span>&nbsp;</span>")); // spacer









