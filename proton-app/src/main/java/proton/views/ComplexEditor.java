package proton.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import proton.base.BaseDictViewEditor;
import proton.products.Product;
import proton.products.ProductService;
import util.ProtonDatePicker;
import util.ProtonStrings;

@SpringComponent
@UIScope
public class ComplexEditor extends BaseDictViewEditor<Product, ProductService> {

    public ComplexEditor(ProductService service) {
        //repo = service.getRepository();
        binder = new Binder<>(Product.class);
        setupView();
    }

    @Override
    public void setupFields() {
        super.setupFields();

        final IntegerField numberField = new IntegerField("Количество");
        numberField.getStyle().set("color", "green");
        binder.forField(numberField)
                .withValidator(new IntegerRangeValidator(ProtonStrings.NOT_IN_RANGE, 1, Integer.MAX_VALUE))
                .asRequired(ProtonStrings.REQUIRED)
                .bind(Product::getNumber, Product::setNumber);
        form.add(numberField);

        final NumberField coefficientField = new NumberField("Коэффициент (Alt+K)");
        coefficientField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        coefficientField.addFocusShortcut(Key.KEY_K, KeyModifier.ALT);
        //      coefficientField.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(coefficientField)
                .withValidator(d -> !d.isNaN(), ProtonStrings.REQUIRED)
                .bind(Product::getCoefficient, Product::setCoefficient);
        form.add(coefficientField);

        final ProtonDatePicker dateField = new ProtonDatePicker("Дата");
        binder.forField(dateField)
                .bind(Product::getDate, Product::setDate);
        form.add(dateField);

        final DateTimePicker dateTimeField = new DateTimePicker("Дата/время");
        binder.forField(dateTimeField)
                .bind(Product::getDateTime, Product::setDateTime);
        form.add(dateField);

        final Checkbox checkedField = new Checkbox("Отметка");
        checkedField.getElement().setAttribute("border", "1px solid blue;");
        binder.forField(checkedField)
                .asRequired(ProtonStrings.REQUIRED)
                .bind(Product::getChecked, Product::setChecked);
        form.add(checkedField);

        for (int i = 0; i < 12; i++) {
            IntegerField f = new IntegerField("Поле %d".formatted(i));
            form.add(f, 1);
        }

        Label label = new Label("__________________________________________");
        label.setHeight("2px");

        //label.getElement().setAttribute("style", "background-color: blue;");
        //form.getElement().setAttribute("border", "1px solid black;");

        form.add(label);


        for (int i = 0; i < 8; i++) {
            IntegerField f = new IntegerField("Поле %d".formatted(i));
            form.add(f, 2);
        }
    }

}
