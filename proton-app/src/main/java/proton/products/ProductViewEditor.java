package proton.products;

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
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import proton.base.BaseDictViewEditor;
import proton.customers.Customer;
import proton.customers.CustomerService;
import proton.employees.Employee;
import proton.employees.EmployeeService;
import util.ProtonDatePicker;
import util.ProtonStrings;

@SpringComponent
@Scope("prototype")
@Slf4j
public class ProductViewEditor extends BaseDictViewEditor<Product, ProductService> {

    EmployeeService employeeService;
    CustomerService customerService;

    public ProductViewEditor(ProductService productService, EmployeeService employeeService, CustomerService customerService) {
        log.debug("CONSTRUCTOR");
        this.service = productService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        binder = new Binder<>(Product.class);
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
        form.add(getEmployeeCombobox());
        form.add(getCheckEmployeeCombobox());
        form.add(getCustomerCombobox());
    }

    @NotNull
    private Checkbox getCheckedField() {
        final Checkbox field = new Checkbox("Отметка");
        field.getElement().setAttribute("border", "1px solid blue;");
        binder.forField(field)
                .bind(Product::getChecked, Product::setChecked);
        return field;
    }

    @NotNull
    private DateTimePicker getDateTimeField() {
        final DateTimePicker field = new DateTimePicker("Дата/время");
        binder.forField(field).bind(Product::getDateTime, Product::setDateTime);
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
                .bind(Product::getCoefficient, Product::setCoefficient);
        return field;
    }

    @NotNull
    private IntegerField getNumberField() {
        final IntegerField field = new IntegerField("Количество");
        field.getStyle().set("color", "green");
        binder.forField(field)
                .withValidator(new IntegerRangeValidator(ProtonStrings.NOT_IN_RANGE, 1, Integer.MAX_VALUE))
                .asRequired(ProtonStrings.REQUIRED)
                .bind(Product::getNumber, Product::setNumber);
        return field;
    }

    @NotNull
    private ComboBox<Employee> getEmployeeCombobox() {
        final ComboBox<Employee> comboBox = new ComboBox<>("Изготовил");
        comboBox.setItems(employeeService.findAll());
        comboBox.setItemLabelGenerator(i -> i.getId() + " - " + i.getName());
        comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "24em");
        binder.forField(comboBox)
                  .asRequired(ProtonStrings.REQUIRED)
                  .bind(Product::getProduceEmployee, Product::setProduceEmployee);
        return comboBox;
    }

    @NotNull
    private ComboBox<Employee> getCheckEmployeeCombobox() {
        final ComboBox<Employee> comboBox = new ComboBox<>("Проверил");
        comboBox.setItems(employeeService.findAll());
        comboBox.setItemLabelGenerator(i -> i.getId() + " - " + i.getName());
        comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "24em");
        binder.forField(comboBox)
                  .asRequired(ProtonStrings.REQUIRED)
                  .bind(Product::getCheckEmployee, Product::setCheckEmployee);
        return comboBox;
    }

    @NotNull
    private ComboBox<Customer> getCustomerCombobox() {
        final ComboBox<Customer> comboBox = new ComboBox<>("Заказчик");
        comboBox.setItems(customerService.findAll());
        comboBox.setItemLabelGenerator(i -> i.getId() + " - " + i.getName());
        comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "24em");
        binder.forField(comboBox)
                  .asRequired(ProtonStrings.REQUIRED)
                  .bind(Product::getCustomer, Product::setCustomer);
        return comboBox;
    }


}
