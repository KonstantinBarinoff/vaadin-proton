package proton.views;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.SimpleDict2;
import proton.repositories.SimpleDict2Service;

@Slf4j
@Route(value = "simple-dict-2", layout = MainView.class)
@PageTitle("SimpleDict2 Buffered Editor")
public class SimpleDict2View extends BaseDictView<SimpleDict2, SimpleDict2Service> {

    @Override
    SimpleDict2 getNewItem() {
        return new SimpleDict2();
    }

    @Autowired
    public SimpleDict2View(SimpleDict2Service service) {
        super(service);
        this.repo = service.getRepository();
        binder = new Binder<>(SimpleDict2.class);

        setupView();
    }


}
