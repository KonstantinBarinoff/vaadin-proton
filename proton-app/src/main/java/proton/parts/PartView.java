package proton.parts;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.base.BaseDictView;
import proton.products.Product;
import proton.views.MainView;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "part-view", layout = MainView.class)
@PageTitle("Детали  (вариант с наследованием PartViewDetail extends PartView)")
public class PartView extends BaseDictView<Part, PartService> {

    private Product filteredProduct;

    protected final Grid.Column<Part> productColumn =
            grid.addColumn(i -> i.getProduct().getName()).setHeader("Изделие").setFlexGrow(10);

    @Autowired
    public PartView(PartService service, PartViewEditor editor) {
        log.debug("CONSTRUCTOR");
        this.service = service;
        this.editor = editor;
        editor.setOwnerDictView(this);
    }

    @PostConstruct
    private void init() {
        log.debug("POSTCONSTRUCT");
        setupView();
    }

    @Override
    protected Part getNewItem() {
        return new Part();
    }

}
