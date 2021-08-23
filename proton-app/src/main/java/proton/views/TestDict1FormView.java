package proton.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import proton.entities.TestDict1;
import proton.repositories.TestDict1Service;

@Slf4j
@Route(value = "simple-form-dict-1", layout = MainView.class)
@PageTitle("TestDict1 Form View")
public class TestDict1FormView extends BaseDictFormView<TestDict1, TestDict1Service> {

//    private final Grid.Column<TestDict1> coefficientColumn = grid.addColumn(TestDict1::getCoefficient).setHeader("Коэффициент").setFlexGrow(1);
//    private final Grid.Column<TestDict1> dateColumn = grid.addColumn(TestDict1::getDate).setHeader("Дата").setFlexGrow(1);
//    private final Grid.Column<TestDict1> dateTimeColumn = grid.addColumn(TestDict1::getDateTime).setHeader("Дата/время").setFlexGrow(2);
//    private final Grid.Column<TestDict1> checkedColumn = grid.addColumn(i -> i.getChecked() ? "Да" : "").setHeader("Отметка").setFlexGrow(1);

    //@Autowired
    public TestDict1FormView(TestDict1Service service) {
        super(service);
        repo = service.getRepository();
        editor = new TestDict1FormEditor(service);

        grid.addColumn(TestDict1::getCoefficient).setHeader("Коэффициент").setFlexGrow(10);
        grid.addColumn(TestDict1::getDate).setHeader("Дата").setFlexGrow(20);
        grid.addColumn(TestDict1::getDateTime).setHeader("Дата/время").setFlexGrow(20);
        grid.addColumn(i -> i.getChecked() ? "Да" : "-").setHeader("Отметка").setFlexGrow(10);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        setupView();
    }

    @Override
    protected TestDict1 getNewItem() {
        return new TestDict1();
    }

}
