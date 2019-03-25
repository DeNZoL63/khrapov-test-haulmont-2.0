package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.ReadWriteDB;
import com.haulmont.testtask.dao.TableNames;
import com.haulmont.testtask.entity.MechanicsStatistic;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.util.List;

public class MechanicsListForm extends BasicListForm_Abstract {
    private Layout form = super.getForm();
    private Grid grid = super.getGrid();
    private Grid.HeaderRow filterRow = grid.appendHeaderRow();

    public MechanicsListForm(){
        refreshGrid();
    }

    @Override
    public void addBtnClick() {
        //создаем механика
        MechanicForm mechanicForm = new MechanicForm();
        mechanicForm.addCloseListener((Window.CloseListener) closeEvent -> {
            refreshGrid();
        });
    }

    @Override
    public void editBtnClick() {
        MechanicForm mechanicForm = new MechanicForm();
        mechanicForm.setId(Common.getIDfromSelectedRow(grid));
        mechanicForm.addCloseListener((Window.CloseListener) closeEvent -> {
            refreshGrid();
        });
    }

    @Override
    public void deleteBtnClick() {
        ReadWriteDB.delete(TableNames.Mechanics, Common.getIDfromSelectedRow(grid));
        refreshGrid();
    }

    @Override
    public void refreshGrid() {
        List<MechanicsStatistic> personList = ReadWriteDB.readAll(TableNames.Mechanics_Statistic);
        BeanItemContainer<MechanicsStatistic> containerNew = new BeanItemContainer<>(MechanicsStatistic.class, personList);
        grid.setContainerDataSource(new BeanItemContainer<>(MechanicsStatistic.class));
        grid.setContainerDataSource(containerNew);
        grid.setColumnOrder("id", "viewForChoose", "rate", "orders_new", "orders_confirm", "orders_finish");

        grid.getColumn("id")
                .setHeaderCaption("Код механика");
        grid.getColumn("rate")
                .setHeaderCaption("Часовая ставка");
        grid.getColumn("viewForChoose")
                .setHeaderCaption("ФИО");
        grid.getColumn("orders_new")
                .setHeaderCaption("Новых заказов");
        grid.getColumn("orders_confirm")
                .setHeaderCaption("Подтвержденных заказов");
        grid.getColumn("orders_finish")
                .setHeaderCaption("Выполненных заказов");

        //скрываем фио как отдельные столбцы
        Common.hideColumnsFIO(grid);

        Common.refreshFilter(containerNew, grid, filterRow);
    }


    public Layout getForm() {
        return form;
    }

    @Override
    public void gridDoubleClick(Long id) {
        MechanicForm mechanicForm = new MechanicForm();
        mechanicForm.setId(id);
        mechanicForm.addCloseListener((Window.CloseListener) closeEvent -> {
            refreshGrid();
        });
    }
}
