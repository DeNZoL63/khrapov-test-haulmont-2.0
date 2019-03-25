package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.ReadWriteDB;
import com.haulmont.testtask.dao.TableNames;
import com.haulmont.testtask.entity.OrderStatus_Constant;
import com.haulmont.testtask.entity.OrderWithFIO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Window;

import java.util.List;

public class OrdersListForm extends BasicListForm_Abstract {

    private Layout form = super.getForm();
    private Grid grid = super.getGrid();
    private Grid.HeaderRow filterRow = grid.appendHeaderRow();

    public OrdersListForm(){
        grid.setRowStyleGenerator(rowRef -> {
            if (OrderStatus_Constant.Выполнен.toString()
                    .equals(rowRef.getItem()
                    .getItemProperty("orderStatus")
                    .getValue()))
                return "grayed";
            else
                return null;
        });

        refreshGrid();
    }

    @Override
    public void addBtnClick() {
        OrderForm orderForm = new OrderForm();
        orderForm.addCloseListener((Window.CloseListener) closeEvent -> {
            refreshGrid();
        });
    }

    @Override
    public void editBtnClick() {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(Common.getIDfromSelectedRow(grid));
        orderForm.addCloseListener((Window.CloseListener) closeEvent -> {
            refreshGrid();
        });
    }

    @Override
    public void deleteBtnClick() {
        ReadWriteDB.delete(TableNames.Orders, Common.getIDfromSelectedRow(grid));
        refreshGrid();
    }

    @Override
    public void refreshGrid() {
        List<OrderWithFIO> ordersList = ReadWriteDB.readAll(TableNames.Orders_With_FIO);
        BeanItemContainer<OrderWithFIO> container = new BeanItemContainer<>(OrderWithFIO.class, ordersList);
        grid.setContainerDataSource(container);
        grid.setColumnOrder("id", "createDate", "orderStatus", "price", "clientFio", "mechanicFio", "detail", "finishDate");

        //переименуем колонки
        grid.getColumn("id")
                .setHeaderCaption("Номер заказа");
        grid.getColumn("createDate")
                .setHeaderCaption("Дата создания");
        grid.getColumn("orderStatus")
                .setHeaderCaption("Статус");
        grid.getColumn("price")
                .setHeaderCaption("Сумма");
        grid.getColumn("clientFio")
                .setHeaderCaption("Клиент (ФИО)");
        grid.getColumn("mechanicFio")
                .setHeaderCaption("Механик (ФИО)");
        grid.getColumn("detail")
                .setHeaderCaption("Описание");
        grid.getColumn("finishDate")
                .setHeaderCaption("Дата завершения");

        //скрываем колонки с id механика и клиента
        grid.getColumn("client")
                .setHidden(true);
        grid.getColumn("mechanic")
                .setHidden(true);

        Common.refreshFilter(container, grid, filterRow);
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public Layout getForm() {
        return form;
    }

    @Override
    public void gridDoubleClick(Long id) {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(id);
        orderForm.addCloseListener((Window.CloseListener) closeEvent -> {
            refreshGrid();
        });
    }
}
