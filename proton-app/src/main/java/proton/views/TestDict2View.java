package proton.views;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.TestDict2;
import proton.repositories.TestDict2Service;

@Slf4j
@Route(value = "simple-dict-2", layout = MainView.class)
@PageTitle("SimpleDict2 Buffered Editor")
public class TestDict2View extends BaseDictView<TestDict2, TestDict2Service> {

    @Override
    protected TestDict2 getNewItem() {
        return new TestDict2();
    }

    @Autowired
    public TestDict2View(TestDict2Service service) {
        super(service);
        this.repo = service.getRepository();
        binder = new Binder<>(TestDict2.class);

        setupView();
    }


}
