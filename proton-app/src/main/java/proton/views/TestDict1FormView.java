package proton.views;

import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import proton.entities.TestDict1;
import proton.repositories.TestDict1Service;

@Slf4j
@Route(value = "simple-form-dict-1", layout = MainView.class)
@PageTitle("TestDict1 Form View")
public class TestDict1FormView extends BaseDictFormView<TestDict1, TestDict1Service> {

    //@Autowired
    public TestDict1FormView(TestDict1Service service) {
        super(service);
        repo = service.getRepository();
        editor = new TestDict1FormEditor(service);

        grid.getColumnByKey("description").setVisible(false);   // Скрыть дочернюю колонку
        grid.addColumn(TestDict1::getNumber).setHeader("Количество").setFlexGrow(5);
        grid.addColumn(TestDict1::getCoefficient).setHeader("Коэффициент").setFlexGrow(10);
        grid.addColumn(new LocalDateRenderer<>(TestDict1::getDate, "dd.MM.yy")).setHeader("Дата").setFlexGrow(20);
        grid.addColumn(new LocalDateTimeRenderer<>(TestDict1::getDateTime, "dd.MM.yy hh:mm")).setHeader("Дата/время").setFlexGrow(20);
        grid.addColumn(i -> i.getChecked() ? "Да" : "-").setHeader("Отметка").setFlexGrow(10);
        grid.addColumn(TestDict1::getDict2Id).setHeader("Спр2 Id").setFlexGrow(1);
        grid.addColumn(i -> i.getTestDict2().getName()).setHeader("Спр2 Наименование").setFlexGrow(1);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        setupView();
    }

    @Override
    protected TestDict1 getNewItem() {
        return new TestDict1();
    }

}
