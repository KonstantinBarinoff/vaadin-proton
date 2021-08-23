package proton.views;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import proton.entities.TestDict2;
import proton.repositories.TestDict2Service;

@SpringComponent
@UIScope
public class TestDict2FormEditor extends BaseDictFormEditor<TestDict2, TestDict2Service> {

    public TestDict2FormEditor(TestDict2Service service) {
        super(service);
        repo = service.getRepository();
        binder = new Binder<>(TestDict2.class);
        setupView();
    }

}
