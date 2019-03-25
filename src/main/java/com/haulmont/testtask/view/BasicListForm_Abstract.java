package com.haulmont.testtask.view;

import com.haulmont.testtask.interfaces.BasicListFormActivities;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.dialogs.ConfirmDialog;

public abstract class BasicListForm_Abstract implements BasicListFormActivities {

    private Layout form = new VerticalLayout();
    private Grid grid = new Grid();

    BasicListForm_Abstract(){

        Button create = new Button("Добавить");
        create.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        create.addStyleName(ValoTheme.BUTTON_TINY);
        create.addClickListener((Button.ClickListener) clickEvent -> {
            //создаем клиента
            addBtnClick();
        });

        Button delete = new Button("Удалить");
        delete.addStyleName(ValoTheme.BUTTON_DANGER);
        delete.addStyleName(ValoTheme.BUTTON_TINY);
        delete.addClickListener((Button.ClickListener) clickEvent -> {
            if (!Common.isRowSelected(grid)) {
                return;
            }

            ConfirmDialog.show(UI.getCurrent(), "Подтвердите действие:", "Вы действительно хотите удалить запись?",
                    "Да", "Отмена", (ConfirmDialog.Listener) dialog -> {
                        if (dialog.isConfirmed()) {
                            deleteBtnClick();
                        }
                    });

        });

        Button edit = new Button("Изменить");
        edit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        edit.addStyleName(ValoTheme.BUTTON_TINY);
        edit.addClickListener((Button.ClickListener) clickEvent -> {
            if (!Common.isRowSelected(grid)) {
                return;
            }
            editBtnClick();
        });

        HorizontalLayout buttonsGroup = new HorizontalLayout();
        buttonsGroup.setId("buttonsGroup");
        buttonsGroup.addComponents(create, edit, delete);
        buttonsGroup.setMargin(true);

        form.addComponent(buttonsGroup);

        grid.setSizeFull();
        grid.setHeightMode(HeightMode.UNDEFINED);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addItemClickListener(event -> {
            if (event.isDoubleClick()){
                gridDoubleClick((Long) event.getItem().getItemProperty("id").getValue());
            }
        });
        form.addComponent(grid);
    }

    public Layout getForm() {
        return form;
    }

    public Grid getGrid() {
        return grid;
    }

}
