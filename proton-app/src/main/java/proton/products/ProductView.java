package proton.products;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.base.BaseDictView;
import proton.parts.PartView;
import proton.views.MainView;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "product-view", layout = MainView.class)
@PageTitle("Изделия")
public class ProductView extends BaseDictView<Product, ProductService> {

    /** Дочерняя форма для отображения Деталей выбранного Изделия **/
    @Autowired
    private PartView partView;

    ProductView(ProductService productService, ProductViewEditor editor) {
        log.debug("CONSTRUCTOR");
        this.service = productService;
        this.editor = editor;
        editor.setOwnerDictView(this);
    }

    @PostConstruct
    public void init() {
        log.debug("POSTCONSTRUCT");
        setupView();
    }

    @Override
    public void setupGrid() {
        super.setupGrid();
        grid.getColumnByKey("description").setVisible(false);   // Скрыть дочернюю колонку
        grid.addColumn(Product::getNumber).setHeader("Количество").setFlexGrow(5);
        grid.addColumn(Product::getCoefficient).setHeader("Коэффициент").setFlexGrow(10);
        grid.addColumn(new LocalDateRenderer<>(Product::getDate, "dd.MM.yy")).setHeader("Дата").setFlexGrow(20);
        grid.addColumn(new LocalDateTimeRenderer<>(Product::getDateTime, "dd.MM.yy hh:mm")).setHeader("Дата/время").setFlexGrow(20);
        grid.addColumn(i -> i.getChecked() ? "Да" : "-").setHeader("Отметка").setFlexGrow(10);
        grid.addColumn(i -> i.getProduceEmployee().getId()).setHeader("Изг. Id").setFlexGrow(1);
        grid.addColumn(i -> i.getProduceEmployee().getName()).setHeader("Изготовил").setFlexGrow(1);
        grid.addColumn(i -> i.getCheckEmployee().getId()).setHeader("Пров. Id").setFlexGrow(1);
        grid.addColumn(i -> i.getCheckEmployee().getName()).setHeader("Проверил").setFlexGrow(1);
        grid.addColumn(i -> i.getCustomer().getName()).setHeader("Заказчик").setFlexGrow(1);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    @Override
    protected Product getNewItem() {
        return new Product();
    }

    @Override
    protected void onGridSelectionEvent(SelectionEvent<Grid<Product>, Product> e) {
        super.onGridSelectionEvent(e);
        if (e.getFirstSelectedItem().isPresent()) {
            partView.refreshProductFilter(e.getFirstSelectedItem().get());
        } else {
            partView.refreshProductFilter(null);
        }
    }

    @Override
    public void setupView() {
        super.setupView();
        partView.initFiltered(null);
        add(partView);
    }
}
