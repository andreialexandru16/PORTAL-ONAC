package ro.bithat.dms.mvp.component.verticallayout;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.selection.SelectionEvent;
import ro.bithat.dms.mvp.FlowEntityPresenter;
import ro.bithat.dms.mvp.component.HasNew;
import ro.bithat.dms.mvp.component.HasSelection;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Deprecated
public abstract class VerticalLayoutGridCrudFlowEntityView<P extends FlowEntityPresenter,T> extends VerticalLayoutCrudFlowEntityView<P, T> {


    private Button addNewBtn = new Button("button.create.label", VaadinIcon.PLUS.create());

    private HorizontalLayout gridActions = new HorizontalLayout(addNewBtn);

    private Grid<T> crudGrid;

    @PostConstruct
    public void init() {
        crudGrid = new Grid<>(getPresenter().getEntityType());
        addComponentAsFirst(crudGrid);
        addComponentAsFirst(gridActions);
        super.init();
        crudGrid.setWidthFull();
        gridActions.setWidthFull();
        crudGrid.addSelectionListener(this::gridSelectionChangedFired);
        addNewBtn.addClickListener(this::addNewBtnFired);
    }
    
    protected void setCreateButtonLabel(String label) {
    	addNewBtn.setText(label);
    }

    private void gridSelectionChangedFired(SelectionEvent<Grid<T>, T> gridTSelectionEvent) {
        setEditorFormVisibility(gridTSelectionEvent.getFirstSelectedItem().isPresent());
        if(gridTSelectionEvent.getFirstSelectedItem().isPresent()) {
            getPresenter().setEntity(gridTSelectionEvent.getFirstSelectedItem().get());
        }
        if(HasSelection.class.isAssignableFrom(getPresenter().getClass())) {
            ((HasSelection)getPresenter()).publishSelection(gridTSelectionEvent.getFirstSelectedItem());
        }
    }

    protected void addNewBtnFired(ClickEvent<Button> buttonClickEvent) {
        setEditorFormVisibility(true);
        if(HasNew.class.isAssignableFrom(getPresenter().getClass())) {
            ((HasNew)getPresenter()).createItem();
        }
    }

    public HorizontalLayout getGridActions() {
        return gridActions;
    }

    public Grid<T> getCrudGrid() {
        return crudGrid;
    }

    @Override
    public void setItems(Collection<T> collection) {
        crudGrid.setItems(collection);
    }

    public void disableCreateItem() {
        addNewBtn.setVisible(false);
    }

    public void enableCreateItem() {
        addNewBtn.setVisible(true);
    }

    public void refreshCrudGrid() {
        crudGrid.getDataProvider().refreshAll();
    }
    
    public void refreshCrudGridItem(T item) {
        crudGrid.getDataProvider().refreshItem(item);
    }

    public void setCrudGridDataProvider(DataProvider<T, ?> dataProvider) {
        crudGrid.setDataProvider(dataProvider);
        recalculateCrudGridColumnWidths();
//        refreshCrudGrid();
    }

    public void selectCrudGridItem(T item) {
        crudGrid.select(item);
    }


    public void setCrudGridColumnsAutoWidthAndResizeable() {
        crudGrid.getColumns()
                .forEach(column -> column.setAutoWidth(true).setFlexGrow(0).setResizable(true));
    }

    public void recalculateCrudGridColumnWidths() {
        crudGrid.recalculateColumnWidths();
    }

}
