package com.haulmont.testtask.view;

import com.haulmont.testtask.interfaces.BasicFormActivities;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

public abstract class BasicForm_Abstract extends Window implements BasicFormActivities {

    private TextField secondNameFld = new TextField("Фамилия");
    private TextField firstNameFld = new TextField("Имя");
    private TextField patronymicFld = new TextField("Отчество");
    private HorizontalLayout buttonsGroup = new HorizontalLayout();
    private VerticalLayout form = new VerticalLayout();

    BasicForm_Abstract(){

        form.setMargin(true);

        String patternChars = "^[a-zA-Zа-яА-Я]{2,50}$";
        String validationMessage = "Поле может содержать только буквы!";
        RegexpValidator validator = new RegexpValidator(patternChars, validationMessage);
        StringLengthValidator nullValidator = new StringLengthValidator(validationMessage,
                                                                        2,
                                                                        50,
                                                                        false);
        //элементы формы
        secondNameFld.setId("second_nameFld");
        secondNameFld.setInputPrompt("Иванов");
        secondNameFld.addValidator(validator);
        secondNameFld.addValidator(nullValidator);
        secondNameFld.addBlurListener((FieldEvents.BlurListener) blurEvent -> setVisibleValidator(secondNameFld, patternChars));

        firstNameFld.setId("first_nameFld");
        firstNameFld.setInputPrompt("Иван");
        firstNameFld.addValidator(validator);
        firstNameFld.addValidator(nullValidator);
        firstNameFld.addBlurListener((FieldEvents.BlurListener) blurEvent -> setVisibleValidator(firstNameFld, patternChars));

        patronymicFld.setId("patronymicFld");
        patronymicFld.setInputPrompt("Иванович");
        patronymicFld.addValidator(validator);
        patronymicFld.addValidator(nullValidator);
        patronymicFld.addBlurListener((FieldEvents.BlurListener) blurEvent -> setVisibleValidator(patronymicFld, patternChars));

        Button okBtn = new Button("Сохранить");
        okBtn.setId("saveBtn");
        okBtn.addClickListener((Button.ClickListener) clickEvent -> okAction());

        Button cancelBtn = new Button("Отмена");
        cancelBtn.setId("cancelBtn");
        cancelBtn.addClickListener((Button.ClickListener) clickEvent -> cancelAction());

        buttonsGroup.setId("buttons_group");
        buttonsGroup.addComponents(okBtn, cancelBtn);
        buttonsGroup.setMargin(true);

        form.addComponents(secondNameFld, firstNameFld, patronymicFld, buttonsGroup);
    }

    public Layout getForm() {
        return form;
    }

    public HorizontalLayout getButtonsGroup() {
        return buttonsGroup;
    }

    public TextField getFirstNameFld() {
        return firstNameFld;
    }

    public TextField getPatronymicFld() {
        return patronymicFld;
    }

    public TextField getSecondNameFld() {
        return secondNameFld;
    }

    private void setVisibleValidator(TextField component, String pattern){
        if(!component.getValue().matches(pattern)){
            component.setValidationVisible(true);
        }
        else if(component.isValid()==true){
            component.setValidationVisible(false);
        }
    }

}
