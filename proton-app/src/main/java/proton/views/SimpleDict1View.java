package proton.views;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.SimpleDict1;
import proton.repositories.SimpleDict1Service;

@Slf4j
@Route(value = "simple-dict-1", layout = MainView.class)
@PageTitle("SimpleDict1 Buffered Editor")
public class SimpleDict1View extends BaseDictView<SimpleDict1, SimpleDict1Service> {

    @Override
    SimpleDict1 getNewItem() { return new SimpleDict1(); }

    @Autowired
    public SimpleDict1View(SimpleDict1Service service) {
        super(service);
        this.repo = service.getRepository();
        binder = new Binder<>(SimpleDict1.class);
        setupView();
    }

}
