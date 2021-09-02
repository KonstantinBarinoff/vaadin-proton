package proton.parts;

import lombok.extern.slf4j.Slf4j;

/**
 * Создание Detail-формы через наследование Master пока не работает нужным образом.
 * Переход по part-view приводит к созданию экземпляра наследника PartViewDetail,
 * вместо ожидаемого PartView (?????)
 */
@Slf4j
//@PageTitle("Детали")
//@SpringComponent        // для подчиненных (Detail) форм дополнительно указываем для возможности внедрения в Master-форму
//@Scope("prototype")     // View не могут создаваться вне контекста сессии (как singleton-ы)
public class PartViewDetail extends PartView {

    public PartViewDetail(PartService service, PartViewEditor editor) {
        super(service, editor);
    }

//    private Product filteredProduct;
//
//
////    @PostConstruct
////    private void initNotFiltered() {
////        log.debug("POSTCONSTRUCT");
////        setupView();
////    }
//
//    public void initFiltered(Product filteredProduct) {
//        this.filteredProduct = filteredProduct;
//        setupView();
//    }
//
//    public void refreshProductFilter(Product filteredProduct) {
//        this.filteredProduct = filteredProduct;
//        refreshGrid();
//    }
//
//
//    /**
//     * Переопределение базового обработчика обновления таблицы для возможной выборки деталей по продукту
//     */
//    @Override
//    protected void refreshGrid() {
//        super.refreshGrid();
////        if (filteredProduct != null) {
////            grid.setItems(((PartService) service).findByProductId(filteredProduct.getId()));
////            insertButton.setEnabled(true);
////            refreshButton.setEnabled(true);
////        } else {
////            grid.setItems(List.of());
////            insertButton.setEnabled(false);
////            refreshButton.setEnabled(false);
////        }
//    }
//
//    @Override
//    protected Part getNewItem() {
//        return new Part();
//    }
//
//    @Override
//    public void setupGrid() {
//        super.setupGrid();
//        productColumn.setVisible(false);
//    }
//
//    @Override
//    protected void onInsertButtonClick(ClickEvent<Button> e) {
//        super.onInsertButtonClick(e);
//        if (filteredProduct != null) {
//            ((PartViewEditor) editor).setupFilteredProduct(filteredProduct);
//        }
//    }
//
//    @Override
//    protected void onEditButtonClick(ClickEvent<Button> event) {
//        super.onEditButtonClick(event);
//        if (filteredProduct != null) {
//            ((PartViewEditor) editor).setupFilteredProduct(filteredProduct);
//        }
//    }
}
