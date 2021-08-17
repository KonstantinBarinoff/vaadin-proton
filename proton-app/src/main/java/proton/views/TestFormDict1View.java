package proton.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import proton.entities.SimpleDict1;
import proton.repositories.SimpleDict1Service;

@Slf4j
@Route(value = "simple-form-dict-1", layout = MainView.class)
@PageTitle("SimpleDict1 Form Editor")
public class TestFormDict1View extends BaseFormDictView<SimpleDict1, SimpleDict1Service> {

    //@Autowired
    public TestFormDict1View(SimpleDict1Service service) {
        super(service);
        repo = service.getRepository();
        editor = new TestFormDict1Editor(service);
        setupView();
    }

    @Override
    protected SimpleDict1 getNewItem() {
        return new SimpleDict1();
    }

//    @Override
//    protected TestFormDict1Editor getNewEditor(SimpleDict1Service service) {
//        return new TestFormDict1Editor(service);
//    }


}
