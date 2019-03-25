package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.ReadWriteDB;
import com.haulmont.testtask.dao.TableNames;
import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.entity.Mechanic;
import com.haulmont.testtask.entity.Order;
import com.haulmont.testtask.entity.OrderStatus_Constant;
import com.haulmont.testtask.interfaces.BasicFormActivities;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

import java.util.Date;
import java.util.List;

public class OrderForm extends Window implements BasicFormActivities {

    private TextArea detail = new TextArea("Описание заказа");
    private ComboBox orderStatus = new ComboBox("Статус");
    private DateField createDate = new DateField("Дата заказа");
    private ComboBox client = new ComboBox("Клиент");
    private ComboBox mechanic = new ComboBox("Механик");
    private DateField finishDate = new DateField("Дата окончания работ");
    private TextField price = new TextField("Стоимость");
    private Long id = null;

    OrderForm(){
        Layout form = new VerticalLayout();
        form.addComponents(detail, orderStatus, createDate, client, mechanic, finishDate, price);

        detail.setSizeFull();
        orderStatus.setSizeFull();
        createDate.setSizeFull();
        client.setSizeFull();
        mechanic.setSizeFull();
        finishDate.setSizeFull();
        price.setSizeFull();

        orderStatus.addItems((Object[]) OrderStatus_Constant.values());

        List<Client> clients = ReadWriteDB.readAll(TableNames.Clients);
        BeanItemContainer<Client> containerClients = new BeanItemContainer<>(Client.class, clients);
        client.setContainerDataSource(containerClients);
        client.setItemCaptionPropertyId("viewForChoose");
        client.setFilteringMode(FilteringMode.CONTAINS);

        List<Mechanic> mechanics = ReadWriteDB.readAll(TableNames.Mechanics);
        BeanItemContainer<Mechanic> containerMechanics = new BeanItemContainer<>(Mechanic.class, mechanics);
        mechanic.setContainerDataSource(containerMechanics);
        mechanic.setItemCaptionPropertyId("viewForChoose");
        mechanic.setFilteringMode(FilteringMode.CONTAINS);
        
        Button okBtn = new Button("Сохранить");
        okBtn.setId("saveBtn");
        okBtn.addClickListener((Button.ClickListener) clickEvent -> okAction());

        Button cancelBtn = new Button("Отмена");
        cancelBtn.setId("cancelBtn");
        cancelBtn.addClickListener((Button.ClickListener) clickEvent -> cancelAction());

        HorizontalLayout buttonsGroup = new HorizontalLayout();
        buttonsGroup.setId("buttons_group");
        buttonsGroup.addComponents(okBtn, cancelBtn);
        buttonsGroup.setMargin(true);

        form.addComponent(buttonsGroup);

        setValidators();

        setContent(form);
        center();
        setWindowMode(WindowMode.NORMAL);

        this.setModal(true);
        UI.getCurrent().addWindow(this);
    }

    public void setId(Long id){
        this.id = id;
        Order order = ReadWriteDB.readOneOrder(id);

        Long tmpID = order.getClient();
        for (Object o : client.getItemIds()) {
            Client tmpClient = (Client) o;
            if (tmpID.equals(tmpClient.getId())){
                client.setValue(o);
                break;
            }
        }

        tmpID = order.getMechanic();
        for (Object o : mechanic.getItemIds()) {
            Mechanic tmpMechanic = (Mechanic) o;
            if (tmpID.equals(tmpMechanic.getId())){
                mechanic.setValue(o);
                break;
            }
        }

        for (Object o : orderStatus.getItemIds()) {
            if (o.toString().equals(order.getOrderStatus())){
                orderStatus.setValue(o);
                break;
            }
        }

        detail.setValue(order.getDetail());
        createDate.setValue(order.getCreateDate());
        finishDate.setValue(order.getFinishDate());
        price.setValue(order.getPrice().toString());
    }

    @Override
    public void okAction() {
        validateAll();
        Client clientObj = (Client) client.getValue();
        Mechanic mechanicObj = (Mechanic) mechanic.getValue();
        Order newOrder =
                new Order(createDate.getValue(),
                        detail.getValue(),
                        clientObj.getId(),
                        mechanicObj.getId(),
                        finishDate.getValue(),
                        Double.parseDouble(price.getValue()),
                        (OrderStatus_Constant) orderStatus.getValue());

        boolean res;
        String notificationMessage;

        if (id == null){
            res = ReadWriteDB.create(newOrder);
            notificationMessage = "Создан новый заказ";
        }else{
            newOrder.setId(id);
            res = ReadWriteDB.update(newOrder);
            notificationMessage = "Изменен заказ";
        }

        if (res){
            Notification.show(notificationMessage, "", Notification.Type.TRAY_NOTIFICATION);
            this.close();
        }else{
            Notification.show("Не удалось создать заказ", "", Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void cancelAction() {
        close();
    }

    private void setValidators(){
        StringLengthValidator nullValidator = new StringLengthValidator("Поле не может быть пустым!",
                1,
                5000,
                true);

        String patternNum = "^[0-9]{1,10}[.,]{0,1}[0-9]{0,2}$";
        String validationNumMessage = "Поле может содержать только цифры (не более 10 знаков целой части и 2 знаков дробной)!";
        RegexpValidator num = new RegexpValidator(patternNum, validationNumMessage);

        detail.addValidator(nullValidator);
        price.addValidator(nullValidator);
        price.addValidator(num);

        orderStatus.setNullSelectionAllowed(false);
        orderStatus.addValidator(new Validator() {
            @Override
            public void validate(Object o) throws InvalidValueException {
//                OrderStatus_Constant constant = (OrderStatus_Constant) o;
                if (o == null){
                    throw new InvalidValueException("Статус обязателен к заполнению");
                }
            }
        });

        client.setNullSelectionAllowed(false);
        client.addValidator(new Validator() {
            @Override
            public void validate(Object o) throws InvalidValueException {
//                Client constant = (Client) o;
                if (o == null){
                    throw new InvalidValueException("Необходимо указать покупателя");
                }
            }
        });

        mechanic.setNullSelectionAllowed(false);
        mechanic.addValidator(new Validator() {
            @Override
            public void validate(Object o) throws InvalidValueException {
//                Mechanic constant = (Mechanic) o;
                if (o == null){
                    throw new InvalidValueException("Необходимо указать механика");
                }
            }
        });




        createDate.addValidator(new Validator() {
            @Override
            public void validate(Object value) throws InvalidValueException {
                Date dateValue = (Date) value;
                Date now = new Date();

                if (dateValue == null)
                    throw new InvalidValueException("Дату заказа необходимо указать");
                else if (dateValue.after(now))
                    throw new InvalidValueException("Дата заказа не может быть указана будущим числом");
            }
        });

        finishDate.addValidator(new Validator() {
            @Override
            public void validate(Object value) throws InvalidValueException {
                Date dateValue = (Date) value;
                Date orderDate = createDate.getValue();

                if (dateValue == null)
                    throw new InvalidValueException("Дату завершения необходимо указать");
                else if (orderDate == null){
                    throw new InvalidValueException("Необходимо указать дату заказа");
                }
                else if (dateValue.before(orderDate))
                    throw new InvalidValueException("Дата завершения заказа не может быть раньше самого заказа!");

            }
        });
    }

    private void validateAll(){
        detail.validate();
        orderStatus.validate();
        createDate.validate();
        client.validate();
        mechanic.validate();
        finishDate.validate();
        price.validate();
    }

}
