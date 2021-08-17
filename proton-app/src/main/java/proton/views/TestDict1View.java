package proton.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.TestDict1;
import proton.repositories.TestDict1Service;


@Slf4j
@Route(value = "simple-dict-1", layout = MainView.class)
@PageTitle("SimpleDict1 Buffered Editor")
public class TestDict1View extends BaseDictView<TestDict1, TestDict1Service> {

    protected final Grid.Column<TestDict1> coefficientColumn = grid.addColumn(TestDict1::getCoefficient)
            .setHeader("Коэффициент").setFlexGrow(1);
    protected final NumberField coefficientField = new NumberField();

    @Override
    protected TestDict1 getNewItem() { return new TestDict1(); }

    @Autowired
    public TestDict1View(TestDict1Service service) {
        super(service);
        repo = service.getRepository();
        binder = new Binder<>(TestDict1.class);
        setupView();
    }

    @Override
    public void setupFields() {
        super.setupFields();
        binder.forField(coefficientField).bind(TestDict1::getCoefficient, TestDict1::setCoefficient);
        coefficientColumn.setEditorComponent(coefficientField);
    }

}
