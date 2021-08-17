package proton.views;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import proton.entities.SimpleDict2;
import proton.repositories.SimpleDict2Service;

@SpringComponent
@UIScope
public class TestFormDict2Editor extends BaseFormDictEditor<SimpleDict2, SimpleDict2Service> {

    public TestFormDict2Editor(SimpleDict2Service service) {
        super(service);
        repo = service.getRepository();
        binder = new Binder<>(SimpleDict2.class);
        setupView();
    }

}
