package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.ReadWriteDB;
import com.haulmont.testtask.dao.TableNames;
import com.haulmont.testtask.entity.Client;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.util.List;

public class ClientsListForm extends BasicListForm_Abstract{
    private Layout form = super.getForm();
    private Grid grid = super.getGrid();
    private Grid.HeaderRow filterRow = grid.appendHeaderRow();


    public ClientsListForm() {
        refreshGrid();
    }

    public Layout getForm() {
        return form;
    }

    @Override
    public void addBtnClick() {
        ClientForm clientForm = new ClientForm();
        clientForm.addCloseListener((Window.CloseListener) closeEvent -> {
            refreshGrid();
        });
    }

    @Override
    public void editBtnClick() {
        ClientForm clientForm = new ClientForm();
        clientForm.setId(Common.getIDfromSelectedRow(grid));
        clientForm.addCloseListener((Window.CloseListener) closeEvent -> {
            refreshGrid();
        });
    }

    @Override
    public void deleteBtnClick() {
        boolean isDelete = ReadWriteDB.delete(TableNames.Clients, Common.getIDfromSelectedRow(grid));
        if (isDelete) Notification.show("Запись успешно удалена", "", Notification.Type.TRAY_NOTIFICATION);
        refreshGrid();
    }

    @Override
    public void refreshGrid(){
        List<Client> personList = ReadWriteDB.readAll(TableNames.Clients);
        BeanItemContainer<Client> container = new BeanItemContainer<>(Client.class, personList);

        grid.setContainerDataSource(container);
        grid.setColumnOrder("id", "viewForChoose", "phoneNumber");

        grid.getColumn("id")
                .setHeaderCaption("Код клиента");
        grid.getColumn("phoneNumber")
                .setHeaderCaption("Телефон");
        grid.getColumn("viewForChoose")
                .setHeaderCaption("ФИО");

        //скрываем фио как отдельные столбцы
        Common.hideColumnsFIO(grid);

        Common.refreshFilter(container, grid, filterRow);
    }

    @Override
    public void gridDoubleClick(Long id) {
        ClientForm clientForm = new ClientForm();
        clientForm.setId(id);
        clientForm.addCloseListener((Window.CloseListener) closeEvent -> {
            refreshGrid();
        });
    }

}
