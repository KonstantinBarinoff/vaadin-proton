package proton.parts;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.base.BaseDictView;
import proton.products.ProductService;
import proton.views.MainView;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "Part-view", layout = MainView.class)
@PageTitle("Детали")
public class PartView extends BaseDictView<Part, PartService> {

    ProductService productService;

    @Autowired
    public PartView(PartService service, ProductService productService) {
        this.service = service;
        this.productService = productService;
        editor = new PartViewEditor(service, productService);
    }

    @PostConstruct
    public void init() {
        setupView();
    }

    @Override
    protected Part getNewItem() {
        return new Part();
    }

    @Override
    public void setupGrid() {
        super.setupGrid();
        grid.addColumn(i -> i.getProduct().getName()).setHeader("Изделие").setFlexGrow(1);

    }
}
