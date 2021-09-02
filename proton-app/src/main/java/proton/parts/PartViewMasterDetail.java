package proton.parts;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import proton.base.BaseDictView;
import proton.products.Product;
import proton.views.MainView;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Route(value = "part-view-master-detail", layout = MainView.class)
@PageTitle("Детали (Вариант формы с признаком Master/Detail)")
@SpringComponent        // для подчиненных (Detail) форм дополнительно указываем для возможности внедрения в Master-форму
@Scope("prototype")     // View не могут создаваться вне контекста сессии (как singleton-ы)

public class PartViewMasterDetail extends BaseDictView<Part, PartService> {

    // TODO: Как вариант, реализовать через наследования: PartDetailView extends PartView
    private enum ViewType {
        /** Тип формы: дочерняя (подчиненная) - отображает отфильтрованные детали по выбранному в {@see #filteredProduct} Продукту */
        FILTERED,
        /** Тип формы: основная - все детали всех продуктов, без фильтрации */
        ALL_RECORDS
    }

    private ViewType viewType;
    private Product filteredProduct;

    private final Grid.Column<Part> productColumn =
            grid.addColumn(i -> i.getProduct().getName()).setHeader("Изделие").setFlexGrow(10);

    @Autowired
    public PartViewMasterDetail(PartService service, PartViewEditor editor) {
        log.debug("CONSTRUCTOR");
        this.service = service;
        this.editor = editor;
        editor.setOwnerDictView(this);
    }

    @PostConstruct
    private void initNotFiltered() {
        log.debug("POSTCONSTRUCT");
        viewType = ViewType.ALL_RECORDS;
        setupView();
    }
    /**
     * @param filteredProduct Код изделия по которому выбираются детали
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
     * Переопределение базового обработчика обновления таблицы для возможной выборки деталей по продукту
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
            productColumn.setVisible(true);
        } else {
            productColumn.setVisible(false);
        }
    }

    @Override
    protected void onInsertButtonClick(ClickEvent<Button> e) {
        super.onInsertButtonClick(e);
        if (viewType == ViewType.FILTERED && filteredProduct != null) {
            ((PartViewEditor) editor).setupFilteredProduct(filteredProduct);
        }
    }

    @Override
    protected void onEditButtonClick(ClickEvent<Button> event) {
        super.onEditButtonClick(event);
        if (viewType == ViewType.FILTERED && filteredProduct != null) {
            ((PartViewEditor) editor).setupFilteredProduct(filteredProduct);
        }
    }
}
