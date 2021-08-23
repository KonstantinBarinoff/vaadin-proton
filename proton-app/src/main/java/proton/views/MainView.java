package proton.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@CssImport("./styles/views/main/main-view.css")
@CssImport(themeFor = "vaadin-menu-bar", value = "./styles/vertical-menu.css")
@JsModule("./styles/shared-styles.js")
public class MainView extends AppLayout {

    private Tabs mainMenu;

    private H1 viewTitle;

    public MainView() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);
//        layout.add(new Image("images/user.svg", "Avatar"));
        return layout;
    }

    private Component createDrawerContent() {
        mainMenu = createMainMenu();

        VerticalLayout drawerLayout = new VerticalLayout();

        drawerLayout.setSizeFull();
        drawerLayout.setPadding(false);
        drawerLayout.setSpacing(false);
        drawerLayout.getThemeList().set("spacing-s", true);
        drawerLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        //logoLayout.add(new Image("images/logo.png", "My Project logo"));
        logoLayout.add(new H1("PROTON"));

        drawerLayout.add(logoLayout, mainMenu);
        drawerLayout.add(createMenuBar());
        return drawerLayout;
    }

    @NotNull
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        menuBar.getElement().setAttribute("theme", "menu-vertical");
        MenuItem dictMenuItem = menuBar.addItem(new Div(new Span("Справочники"), VaadinIcon.ANGLE_RIGHT.create()));
        dictMenuItem.getElement().setAttribute("style","justify-content: left;");

        MenuItem dict1MenuItem = dictMenuItem.getSubMenu().addItem("Справочник 1");
        dict1MenuItem.addClickListener(e -> UI.getCurrent().navigate(TestDict1FormView.class));

        MenuItem dict2MenuItem = dictMenuItem.getSubMenu().addItem("Справочник 2");
        dict1MenuItem.getElement().setAttribute("style","justify-content: left;");
        dict2MenuItem.addClickListener(e -> UI.getCurrent().navigate(TestDict2FormView.class));

        MenuItem reportsMenuItam = menuBar.addItem("Отчеты");
        reportsMenuItam.getElement().setAttribute("style","justify-content: left;");

        MenuItem settMenuItam = menuBar.addItem("Настройки");
        settMenuItam.getElement().setAttribute("style","justify-content: left;");

        menuBar.setOpenOnHover(true);
        menuBar.getItems().forEach(item -> item.getElement().setAttribute("style","justify-content: left;")); //TODO: Почему не работает???

        // Adjust the opening position with JavaScript
        menuBar.getElement().executeJs("""
                this._subMenu.addEventListener('opened-changed', function(e) { 
                const rootMenu = e.target;
                const button = rootMenu._context.target;
                if(!button) return;
                const rect = button.getBoundingClientRect();
                rootMenu.__x = rect.right;
                rootMenu.__y = rect.top;
                rootMenu.__alignOverlayPosition();
                });""");
        return menuBar;
    }

    private Tabs createMainMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
//        tabs.add(createMenuItems());
        tabs.add(createTab("References View", ReferencesView.class));
        tabs.add(createTab("Test Dict 1 Buff Editor", TestDict1GridBuffView.class));
        tabs.add(createTab("Upload Image to File", UploadImageToFile.class));
        tabs.add(createTab("Byte Array to Image", ByteArrayToImage.class));
        return tabs;
    }


    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
//        getTabForComponent(getContent()).ifPresent(mainMenu::setSelectedTab);
//        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return mainMenu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }

}
