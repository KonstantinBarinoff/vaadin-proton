package proton.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import proton.entities.TestDict1;
import proton.repositories.TestDict1Service;
import util.ProtonDatePicker;
import util.ProtonStrings;

@SpringComponent
@UIScope
public class TestDict1FormEditor extends BaseDictFormEditor<TestDict1, TestDict1Service> {
//    private final IntegerField numberField = new IntegerField("Количество (Alt+K)");
//    private final NumberField coefficientField = new NumberField("Коэффициент (Alt+K)");


    public TestDict1FormEditor(TestDict1Service service) {
        super(service);
        repo = service.getRepository();
        binder = new Binder<>(TestDict1.class);
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
                .bind(TestDict1::getNumber, TestDict1::setNumber);
        form.add(numberField);

        final NumberField coefficientField = new NumberField("Коэффициент (Alt+K)");
        coefficientField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        coefficientField.addFocusShortcut(Key.KEY_K, KeyModifier.ALT);
        //      coefficientField.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(coefficientField)
                .withValidator(d -> !d.isNaN(), ProtonStrings.REQUIRED)
                .bind(TestDict1::getCoefficient, TestDict1::setCoefficient);
        form.add(coefficientField);

        final ProtonDatePicker dateField = new ProtonDatePicker("Дата");
        binder.forField(dateField)
                .bind(TestDict1::getDate, TestDict1::setDate);
        form.add(dateField);

        final DateTimePicker dateTimeField = new DateTimePicker("Дата/время");
        binder.forField(dateTimeField)
                .bind(TestDict1::getDateTime, TestDict1::setDateTime);
        form.add(dateField);

        final Checkbox checkedField = new Checkbox("Отметка");
        
        checkedField.getElement().setAttribute("border", "1px solid blue;");
        binder.forField(checkedField)
                .asRequired(ProtonStrings.REQUIRED)
                .bind(TestDict1::getChecked, TestDict1::setChecked);
        form.add(checkedField);
    }

}
