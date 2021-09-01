package proton.parts;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.base.BaseDictView;
import proton.products.Product;
import proton.products.ProductService;
import proton.views.MainView;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Route(value = "part-view", layout = MainView.class)
@PageTitle("Детали")

public class PartView extends BaseDictView<Part, PartService> {

    ProductService productService;

    public enum ViewType {
        /** Тип формы: дочерняя - отображает отфильтрованные детали по выбранному в {@see #filteredProduct} Продукту */
        FILTERED,
        /** Тип формы: основная - все детали всех продуктов, без фильтрации */
        ALL_RECORDS
    }

    private ViewType viewType;
    private Product filteredProduct;

    @Autowired
    public PartView(PartService service, ProductService productService, PartViewEditor editor) {
        log.debug("CONSTRUCTOR");
        this.service = service;
        this.productService = productService;
        this.editor = editor;
    }

    @PostConstruct
    private void initNotFiltered() {
        log.debug("POSTCONSTRUCT");

        viewType = ViewType.ALL_RECORDS;
        setupView();
    }
    /**
     * @param filteredProduct Код изделия для фильтрации деталей
     */
    public void initFiltered(Product filteredProduct) {
        this.viewType = ViewType.FILTERED;
        this.filteredProduct = filteredProduct;
        setupView();
    }

    public void refreshProductFilter(Product filteredProduct) {
        this.filteredProduct = filteredProduct;
        refreshGrid();
    }


    /**
     * Переопределение базового обработчика обновления таблицы для фильтрации деталей по продукту
     */
    @Override
    protected void refreshGrid() {
        if (viewType == ViewType.ALL_RECORDS) {
            grid.setItems(service.findAll());
        } else {
            if (filteredProduct != null) {
                grid.setItems(((PartService) service).findByProductId(filteredProduct.getId()));
                insertButton.setEnabled(true);
                refreshButton.setEnabled(true);
            } else {
                grid.setItems(List.of());
                insertButton.setEnabled(false);
                refreshButton.setEnabled(false);
            }
        }
    }

    @Override
    protected Part getNewItem() {
        return new Part();
    }

    @Override
    public void setupGrid() {
        super.setupGrid();
        if (viewType == ViewType.ALL_RECORDS) {
            grid.addColumn(i -> i.getProduct().getName()).setHeader("Изделие").setFlexGrow(10);
        }
    }

    @Override
    protected void onInsertButtonClick(ClickEvent<Button> e) {
        super.onInsertButtonClick(e);
        if (viewType == ViewType.FILTERED && filteredProduct != null) {
            ((PartViewEditor) editor).productComboBox.setValue(filteredProduct);
            ((PartViewEditor) editor).productComboBox.setEnabled(false);
        }
    }

    @Override
    protected void onEditButtonClick(ClickEvent<Button> event) {
        super.onEditButtonClick(event);
        if (viewType == ViewType.FILTERED && filteredProduct != null) {
            ((PartViewEditor) editor).productComboBox.setValue(filteredProduct);
            ((PartViewEditor) editor).productComboBox.setEnabled(false);
        }
    }
}
