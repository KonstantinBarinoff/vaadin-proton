package sumo.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

//import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.HasHierarchicalDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import lombok.extern.slf4j.Slf4j;
import oracle.security.o3logon.b;
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
        treeGrid.addHierarchyColumn((BzemDepartment department) -> department.getDepartmentName()).setHeader("Подразделение");
        setSizeFull();
        treeGrid.setSizeFull();
        
        List<BzemDepartment> departments = bzemDepartmentsDAO.getAllDepartments();
        
        for (BzemDepartment department : departments) {
            if (!treeGrid.getTreeData().contains(department)) {
                List<BzemDepartment> depatmentBranch = new ArrayList<BzemDepartment>();
                bzemDepartmentsDAO.getDepartmentBranch(department, depatmentBranch);
                     
                for (int i = depatmentBranch.size() - 2; i > 0; i--) {
                    if (!treeGrid.getTreeData().contains(depatmentBranch.get(i))) {
                        treeGrid.getTreeData().addItem(null, depatmentBranch.get(i));
                        }
                    if (!treeGrid.getTreeData().contains(depatmentBranch.get(i - 1))) {
                        treeGrid.getTreeData().addItem(depatmentBranch.get(i), depatmentBranch.get(i - 1));
                        }
                    }
                }
            }
        add(treeGrid);
    }
}
