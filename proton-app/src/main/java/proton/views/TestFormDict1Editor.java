package proton.views;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import proton.entities.SimpleDict1;
import proton.repositories.SimpleDict1Service;

@SpringComponent
@UIScope
public class TestFormDict1Editor extends BaseFormDictEditor<SimpleDict1, SimpleDict1Service> {

    public TestFormDict1Editor(SimpleDict1Service service) {
        super(service);
        repo = service.getRepository();
        binder = new Binder<>(SimpleDict1.class);
        setupView();
    }

}
