package sumo.views;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import sumo.entities.BzemDepartment;
import sumo.repositories.BzemDepartmentsDAO;
import sumo.util.Paths;

@Slf4j
@Route( Paths.BZEM_DEPARTMENTS) // Страница департаментов
public class BzemDepartmentsView extends VerticalLayout {

    @Autowired
    public BzemDepartmentsView(BzemDepartmentsDAO bzemDepartmentsDAO) {  
	log.debug("CONSTRUCTOR");
	TreeGrid<BzemDepartment> treeGrid = new TreeGrid<>();
    treeGrid.addHierarchyColumn(BzemDepartment::getDepartmentName).setHeader("Подразделение");
    setSizeFull();
    treeGrid.setSizeFull();
    treeGrid.setItems(bzemDepartmentsDAO.getRootDepartments(), bzemDepartmentsDAO::getChildDepartments);
    add(treeGrid);

    }
}