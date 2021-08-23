package proton.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.TestDict1;
import proton.entities.TestDict2;
import proton.repositories.TestDict1Service;
import proton.repositories.TestDict2Service;
import util.ProtonDatePicker;
import util.ProtonStrings;

@SpringComponent
@UIScope
public class TestDict1FormEditor extends BaseDictFormEditor<TestDict1, TestDict1Service> {

    @Autowired
    TestDict2Service    dict2Service;

    public TestDict1FormEditor(TestDict1Service service) {
        super(service);
        repo = service.getRepository();
        binder = new Binder<>(TestDict1.class);
        setupView();
    }

    @Override
    public void setupFields() {
        super.setupFields();

        form.add(getNumberField());
        form.add(getCoefficientField());
        form.add(getDateField());
        form.add(getDateTimeField(),2);
        form.add(getCheckedField());
        form.add(getDict2Combobox());
    }

    @NotNull
    private Checkbox getCheckedField() {
        final Checkbox field = new Checkbox("Отметка");
        field.getElement().setAttribute("border", "1px solid blue;");
        binder.forField(field)
                .bind(TestDict1::getChecked, TestDict1::setChecked);
        return field;
    }

    @NotNull
    private DateTimePicker getDateTimeField() {
        final DateTimePicker field = new DateTimePicker("Дата/время");
        binder.forField(field).bind(TestDict1::getDateTime, TestDict1::setDateTime);
        return field;
    }

    @NotNull
    private ProtonDatePicker getDateField() {
        final ProtonDatePicker field = new ProtonDatePicker("Дата");
        //binder.forField(field).bind(TestDict1::getDate, TestDict1::setDate);  //// Bind based on property name
        binder.forField(field).bind("date");
        return field;
    }

    @NotNull
    private NumberField getCoefficientField() {
        final NumberField field = new NumberField("Коэффициент (Alt+K)");
        field.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        field.addFocusShortcut(Key.KEY_K, KeyModifier.ALT);
        //      coefficientField.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(field)
                .asRequired(ProtonStrings.REQUIRED)
                .withValidator(d -> !d.isNaN(), ProtonStrings.REQUIRED)
                .bind(TestDict1::getCoefficient, TestDict1::setCoefficient);
        return field;
    }

    @NotNull
    private IntegerField getNumberField() {
        final IntegerField field = new IntegerField("Количество");
        field.getStyle().set("color", "green");
        binder.forField(field)
                .withValidator(new IntegerRangeValidator(ProtonStrings.NOT_IN_RANGE, 1, Integer.MAX_VALUE))
                .asRequired(ProtonStrings.REQUIRED)
                .bind(TestDict1::getNumber, TestDict1::setNumber);
        return field;
    }

    @NotNull
    private ComboBox<TestDict2> getDict2Combobox() {
        final ComboBox<TestDict2> field = new ComboBox<TestDict2>("Справочник 2");
        field.setItems(dict2Service.getRepository().findAll());
        field.setItemLabelGenerator(TestDict2::getName);
//        binder.forField(field)
//                .withValidator(new IntegerRangeValidator(ProtonStrings.NOT_IN_RANGE, 1, Integer.MAX_VALUE))
//                .asRequired(ProtonStrings.REQUIRED)
//                .bind(TestDict1::getNumber, TestDict1::setNumber);
        return field;
    }

}
