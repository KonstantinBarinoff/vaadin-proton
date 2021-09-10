package proton.parts;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.base.BaseDictView;
import proton.products.Product;
import proton.views.MainView;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Route(value = "part-view", layout = MainView.class)
@PageTitle("Детали")
//@SpringComponent        // для подчиненных (Detail) форм дополнительно указываем для возможности внедрения в Master-форму
//@Scope("prototype")     // View не могут создаваться вне контекста сессии (как singleton-ы)

public class PartView extends BaseDictView<Part, PartService> {

    private enum ViewType {
        /**
         * Тип формы: мастер (главная) - все детали всех продуктов, без фильтрации
         */
        MASTER,
        /**
         * Тип формы: подчиненная (в составе другой мастер-формы) - отображает отфильтрованные детали по выбранному в {@see #filteredProduct} Продукту
         */
        SLAVE
    }

    private ViewType viewType;
    private Product filteredProduct;

    @Autowired
    public PartView(PartService service, PartViewEditor editor) {
        log.debug("CONSTRUCTOR");
        this.service = service;
        this.editor = editor;
        editor.setOwnerDictView(this);
    }

    @PostConstruct
    private void initMasterView() {
        log.debug("POSTCONSTRUCT");
        viewType = ViewType.MASTER;
        setupView();
    }

    /**
     * @param filteredProduct Код изделия по которому выбираются детали
     */
    public void initSlaveView(Product filteredProduct) {
        this.viewType = ViewType.SLAVE;
        if (filteredProduct != null) {
            this.filteredProduct = filteredProduct;
        }
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
        if (viewType == ViewType.MASTER) {
            grid.setItems(service.findAll(filterField.getValue()));
        } else {
            if (filteredProduct != null) {
                grid.setItems(((PartService) service).findByProductId(filteredProduct.getId(), filterField.getValue()));
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

        grid.addColumn(i -> i.getProduct().getName())
                .setHeader("Изделие")
                .setFlexGrow(10)
                .setVisible(viewType == ViewType.MASTER);
    }

    @Override
    protected void onInsertButtonClick(ClickEvent<Button> e) {
        super.onInsertButtonClick(e);
        if (viewType == ViewType.SLAVE && filteredProduct != null) {
            ((PartViewEditor) editor).setupFilteredProduct(filteredProduct);
        }
    }

    @Override
    protected void onEditButtonClick(ClickEvent<Button> event) {
        super.onEditButtonClick(event);
        if (viewType == ViewType.SLAVE && filteredProduct != null) {
            ((PartViewEditor) editor).setupFilteredProduct(filteredProduct);
        }
    }
}
