package proton.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
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

import java.util.Optional;

@CssImport("./styles/views/main/main-view.css")
@CssImport(themeFor = "vaadin-menu-bar", value = "./styles/vertical-menu.css")
@JsModule("./styles/shared-styles.js")
public class MainView extends AppLayout {

    private Tabs mainMenu;
    private Tabs dictMenu;

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
        dictMenu = createDictMenu();

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

        MenuBar menuBar = new MenuBar();

        // Set menubar to be vertical with css
        menuBar.getElement().setAttribute("theme", "menu-vertical");
        Span span = new Span("Справочники");
        Div div = new Div(span, VaadinIcon.ANGLE_RIGHT.create());

        MenuItem first = menuBar.addItem(div);
        MenuItem item = first.getSubMenu().addItem("Справочник 1");
        item.add(new RouterLink("sdsds", TestDict1View.class));
        ComponentUtil.setData(item, Class.class, TestDict1View.class);


        first.getSubMenu().addItem("Справочник 2");
        menuBar.addItem("Отчеты");
        menuBar.addItem("Настройки");
        menuBar.setOpenOnHover(true);
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

        drawerLayout.add(menuBar);
        return drawerLayout;
    }

    private Tabs createMainMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
//        tabs.add(createMenuItems());
        tabs.add(createTab("References View", ReferencesView.class));
        tabs.add(createTab("Test Dict 1 (buffered)", TestDict1View.class));
        tabs.add(createTab("Test Dict 2 (buffered)", TestDict2View.class));
        tabs.add(createTab("CustomDict Grid Editor", CustomDictView.class));
        tabs.add(createTab("Upload Image to File", UploadImageToFile.class));
        tabs.add(createTab("Byte Array to Image", ByteArrayToImage.class));
        return tabs;
    }

    private Tabs createDictMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createTab("Test Dict 1 (editor)", TestFormDict1View.class));
        tabs.add(createTab("Test Dict 2 (editor)", TestFormDict2View.class));
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
