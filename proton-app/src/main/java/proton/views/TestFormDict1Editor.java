package proton.views;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import proton.entities.TestDict1;
import proton.repositories.TestDict1Service;

@SpringComponent
@UIScope
public class TestFormDict1Editor extends BaseFormDictEditor<TestDict1, TestDict1Service> {

    public TestFormDict1Editor(TestDict1Service service) {
        super(service);
        repo = service.getRepository();
        binder = new Binder<>(TestDict1.class);
        setupView();
    }

}
