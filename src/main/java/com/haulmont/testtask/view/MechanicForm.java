package com.haulmont.testtask.view;


import com.haulmont.testtask.dao.ReadWriteDB;
import com.haulmont.testtask.entity.Mechanic;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class MechanicForm extends BasicForm_Abstract {

    private Layout form = super.getForm();
    private TextField firstName = super.getFirstNameFld();
    private TextField secondName = super.getSecondNameFld();
    private TextField patronymic = super.getPatronymicFld();
    private TextField rate = new TextField("Часовая ставка");
    private Long id;

    MechanicForm() {

        String patternNum = "^[0-9]{1,4}[.,]{0,1}[0-9]{0,2}$";
        String validationMessage = "Поле может содержать только цифры (не более 4 знаков целой части и 2 знаков дробной)!";
        RegexpValidator validator = new RegexpValidator(patternNum, validationMessage);
        StringLengthValidator nullValidator = new StringLengthValidator("Поле не может быть пустым!",
                2,
                50,
                false);

        //элементы формы
        rate.setId("rate");
        rate.setInputPrompt("35.40");
        rate.addValidator(validator);
        rate.addValidator(nullValidator);
        rate.addBlurListener((FieldEvents.BlurListener) blurEvent -> {
            if(!rate.getValue().matches(patternNum)){
                rate.setValidationVisible(true);
            }
            else if(rate.isValid()){
                rate.setValidationVisible(false);
            }
        });

        //меняем местами группу кнопок с добавленным полем
        form.addComponent(rate);
        form.replaceComponent(super.getButtonsGroup(), rate);

        setContent(form);
        center();
        setWindowMode(WindowMode.NORMAL);

        this.setModal(true);
        UI.getCurrent().addWindow(this);
    }

    @Override
    public Layout getForm() {
        return form;
    }

    @Override
    public void okAction() {
        validateAll();
        Mechanic newMechanic = new Mechanic(firstName.getValue(), secondName.getValue(), patronymic.getValue(), Double.parseDouble(rate.getValue()));
        boolean res;
        String notificationMessage;

        if (id == null){
            res = ReadWriteDB.create(newMechanic);
            notificationMessage = "Создан элемент справочника \"Механики\"";
        }else{
            newMechanic.setId(id);
            res = ReadWriteDB.update(newMechanic);
            notificationMessage = "Изменен элемент справочника \"Механики\"";
        }

        if (res){
            Notification.show(notificationMessage, "", Notification.Type.TRAY_NOTIFICATION);
            this.close();
        }else{
            Notification.show("Не удалось создать элемент справочника \"Механики\"", "", Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void cancelAction() {
        close();
    }

    public void setId(Long id) {
        this.id = id;
        Mechanic mechanic = ReadWriteDB.readOneMechanic(id);
        firstName.setValue(mechanic.getFirstName());
        secondName.setValue(mechanic.getSecondName());
        patronymic.setValue(mechanic.getPatronymic());
        rate.setValue(String.valueOf(mechanic.getRate()));
    }

    private void validateAll(){
        firstName.validate();
        secondName.validate();
        patronymic.validate();
        rate.validate();
    }
}
