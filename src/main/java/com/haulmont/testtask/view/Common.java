package com.haulmont.testtask.view;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class Common {

    public static boolean isRowSelected (Grid grid){
        boolean result = true;

        if (grid.getSelectedRow() == null){
            Notification.show("Выберите строку!", "", Notification.Type.WARNING_MESSAGE);
            result = false;
        }

        return result;
    }

    public static Long getIDfromSelectedRow(Grid grid){

        return (Long) grid.getContainerDataSource()
                .getItem(grid.getSelectedRow())
                .getItemProperty("id").getValue();
    }

    public static <T> void refreshFilter(BeanItemContainer<T> container, Grid grid, Grid.HeaderRow filterRow){
        // Set up a filter for all columns
        for (Object pid: grid.getContainerDataSource()
                .getContainerPropertyIds()) {
            Grid.HeaderCell cell = filterRow.getCell(pid);

            // Have an input field to use for filter
            TextField filterField = new TextField();
            filterField.setColumns(8);
            filterField.setInputPrompt("Отбор");
            filterField.addStyleName(ValoTheme.TEXTFIELD_TINY);

            // Update filter When the filter input is changed
            filterField.addTextChangeListener(change -> {
                // Can't modify filters so need to replace
                container.removeContainerFilters(pid);

                // (Re)create the filter if necessary
                if (!change.getText().isEmpty())
                    container.addContainerFilter(
                            new SimpleStringFilter(pid,
                                    change.getText(), true, false));
            });
            cell.setComponent(filterField);
        }
    }

    public static void hideColumnsFIO(Grid grid){
        grid.getColumn("secondName")
                .setHidden(true);
        grid.getColumn("firstName")
                .setHidden(true);
        grid.getColumn("patronymic")
                .setHidden(true);
    }
}
