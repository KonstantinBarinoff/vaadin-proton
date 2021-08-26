package proton.parts;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import proton.base.BaseDictViewEditor;
import proton.products.Product;
import proton.products.ProductService;
import util.ProtonStrings;

@SpringComponent
@UIScope
public class PartViewEditor extends BaseDictViewEditor<Part, PartService> {


    private final ProductService productService;

    public ComboBox<Product> productComboBox;

    @Autowired
    public PartViewEditor(PartService service, ProductService productService) {
        this.productService = productService;
        this.service = service;
        binder = new Binder<>(Part.class);
        setupView();
    }

    @NotNull
    private ComboBox<Product> getProductCombobox() {
        final ComboBox<Product> comboBox = new ComboBox<>("Изделие");
        comboBox.setItems(productService.findAll());
        comboBox.setItemLabelGenerator(i -> i.getId() + " - " + i.getName());
        comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "24em");
        binder.forField(comboBox)
                .asRequired(ProtonStrings.REQUIRED)
                .bind(Part::getProduct, Part::setProduct);
        return comboBox;
    }

    @Override
    public void setupFields() {
        super.setupFields();
        productComboBox = getProductCombobox();
        form.add(productComboBox);
    }
}
