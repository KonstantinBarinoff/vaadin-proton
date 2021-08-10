package sumo.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import sumo.entities.SapDepartment;
import sumo.repositories.SapDepartmentsDAO;
import sumo.util.Paths;

@Slf4j
@Route(Paths.BZEM_DEPARTMENTS) // Страница департаментов
public class SapDepartmentsView extends VerticalLayout {

    @Autowired
    public SapDepartmentsView(SapDepartmentsDAO sapDepartmentsDAO) {
        log.debug("CONSTRUCTOR");
        TreeGrid<SapDepartment> treeGrid = new TreeGrid<>();
        treeGrid.addHierarchyColumn(SapDepartment::getDepartmentName).setHeader("Подразделение");
        setSizeFull();
        treeGrid.setSizeFull();
        treeGrid.setItems(sapDepartmentsDAO.getRootDepartments(), sapDepartmentsDAO::getChildDepartments);
        add(treeGrid);

    }
}