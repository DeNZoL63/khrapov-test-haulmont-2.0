package com.haulmont.testtask;

import com.haulmont.testtask.view.ClientsListForm;
import com.haulmont.testtask.view.MechanicsListForm;
import com.haulmont.testtask.view.OrdersListForm;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

@Theme("mytheme")
public class MainUI extends UI {

    private MenuBar.MenuItem previous = null;

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout layout = new VerticalLayout();

        MenuBar mainMenu = new MenuBar();
        mainMenu.addStyleName("mybarmenu");
        mainMenu.setSizeFull();

        Layout bodyLayout = new VerticalLayout();

        MenuBar.Command clickOrders = (MenuBar.Command) menuItem -> {
            replaceBody(bodyLayout, new OrdersListForm().getForm());
            menuSelected(menuItem);
        };

        MenuBar.Command clickClients = (MenuBar.Command) menuItem -> {
            replaceBody(bodyLayout, new ClientsListForm().getForm());
            menuSelected(menuItem);
        };

        MenuBar.Command clickMechanics = (MenuBar.Command) menuItem -> {
            replaceBody(bodyLayout, new MechanicsListForm().getForm());
            menuSelected(menuItem);
        };

        mainMenu.addItem("Заказы", null, clickOrders);
        previous = mainMenu.getItems().get(0);
        previous.setStyleName("highlight");
        mainMenu.addItem("Клиенты", null, clickClients);
        mainMenu.addItem("Механики", null, clickMechanics);

        layout.addComponent(mainMenu);
        layout.addComponent(bodyLayout);
        bodyLayout.addComponent(new OrdersListForm().getForm());

        setContent(layout);
    }

    private void replaceBody(Layout body, Component forFillBody){
        body.removeAllComponents();
        body.addComponent(forFillBody);
    }

    private void menuSelected(MenuBar.MenuItem selectedItem) {
        if (previous != null)
            previous.setStyleName(null);

        selectedItem.setStyleName("highlight");
        previous = selectedItem;
    }
}