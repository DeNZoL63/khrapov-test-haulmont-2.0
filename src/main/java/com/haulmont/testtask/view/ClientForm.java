package com.haulmont.testtask.view;


import com.haulmont.testtask.dao.ReadWriteDB;
import com.haulmont.testtask.entity.Client;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;

public class ClientForm extends BasicForm_Abstract {

    private Layout form;
    private TextField phoneNumb = new TextField("Номер телефона");
    private TextField firstName = super.getFirstNameFld();
    private TextField secondName = super.getSecondNameFld();
    private TextField patronymic = super.getPatronymicFld();
    private Long id = null;

    public ClientForm() {
        form = super.getForm();
        String patternNum = "^\\+[0-9]{10,12}$";
        String validationMessage = "Поле может содержать только цифры (код страны + 10 цифр) и знак \"+\" в начале!";
        RegexpValidator validator = new RegexpValidator(patternNum, validationMessage);

        //элементы формы
        phoneNumb.setId("phone_numberFld");
        phoneNumb.setInputPrompt("+79999999999");
        phoneNumb.addValidator(validator);
        phoneNumb.addBlurListener((FieldEvents.BlurListener) blurEvent -> {
            if(!phoneNumb.getValue().matches(patternNum)){
                phoneNumb.setValidationVisible(true);
            }
            else if(phoneNumb.isValid()==true){
                phoneNumb.setValidationVisible(false);
            }
        });

        //меняем местами группу кнопок с добавленным полем
        form.addComponent(phoneNumb);
        form.replaceComponent(super.getButtonsGroup(), phoneNumb);

        setContent(form);
        center();
        setWindowMode(WindowMode.NORMAL);

        this.setModal(true);
        UI.getCurrent().addWindow(this);
    }

    public void setId(Long id) {
        this.id = id;
        Client client = ReadWriteDB.readOneClient(id);
        firstName.setValue(client.getFirstName());
        secondName.setValue(client.getSecondName());
        patronymic.setValue(client.getPatronymic());
        phoneNumb.setValue(client.getPhoneNumber());
    }

    @Override
    public void okAction() {
        validateAll();
        Client newClient = new Client(firstName.getValue(), secondName.getValue(), patronymic.getValue(), phoneNumb.getValue());
        boolean res;
        String notificationMessage;

        if (id == null){
            res = ReadWriteDB.create(newClient);
            notificationMessage = "Создан элемент справочника \"Клиенты\"";
        }else{
            newClient.setId(id);
            res = ReadWriteDB.update(newClient);
            notificationMessage = "Изменен элемент справочника \"Клиенты\"";
        }

        if (res){
            Notification.show(notificationMessage, "", Notification.Type.TRAY_NOTIFICATION);
            this.close();
        }else{
            Notification.show("Не удалось создать элемент справочника \"Клиенты\"", "", Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void cancelAction() {
        close();
    }

    @Override
    public Layout getForm() {
        return form;
    }

    private void validateAll(){
        firstName.validate();
        secondName.validate();
        patronymic.validate();
        phoneNumb.validate();
    }
}
