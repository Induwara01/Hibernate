package org.example.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField,String text){
        String filed="";
        switch(textField){
            case CONTACT : filed = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
            case EMAIL: filed ="^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case ID:filed = "^([A-Z][0-9]{3})$";
                break;
            case PASSWORD: filed =  " ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

        }
        Pattern pattern = Pattern.compile(filed);


        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();

    }

    public static boolean setTextColour(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location,textField.getText())){
            textField.setStyle("-fx-text-fill: #53ff1a;-fx-border-color: #53ff1a");
            return true;
        }else {
            textField.setStyle("-fx-text-fill: #ff3333;-fx-border-color: #ff3333");
            return false;
        }
    }
}
