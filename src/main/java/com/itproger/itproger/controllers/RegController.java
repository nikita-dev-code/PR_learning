package com.itproger.itproger.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.itproger.itproger.DB;
import com.itproger.itproger.HelloApplication;
import com.itproger.itproger.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField reg_login;

    @FXML
    private TextField reg_email;

    @FXML
    private PasswordField reg_pass;

    @FXML
    private CheckBox reg_rights;

    @FXML
    private Button reg_btn;

    @FXML
    private TextField auth_login;

    @FXML
    private PasswordField auth_pass;

    @FXML
    private Button auth_btn;

    private DB db = new DB();

    @FXML
    void initialize() {
        reg_btn.setOnAction(event -> {
            registrationUser();
        });
        auth_btn.setOnAction(event -> {
            try {
                authUser(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void authUser(ActionEvent event) throws IOException {
        String login = auth_login.getCharacters().toString();
        String pass = auth_pass.getCharacters().toString();

        auth_login.setStyle("-fx-border-color: #fafafa");
        auth_pass.setStyle("-fx-border-color: #fafafa");

        if(login.length() <= 1)
            auth_login.setStyle("-fx-border-color: #e06249");
        else if(pass.length() <= 3)
            auth_pass.setStyle("-fx-border-color: #e06249");
        else if(!db.authUser(login, md5String(pass)))
            auth_btn.setText("Пользователя нет");
        else {
            auth_login.setText("");
            auth_pass.setText("");
            auth_btn.setText("Все готово :)");

            FileOutputStream fos = new FileOutputStream("user.settings");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new User(login));
            oos.close();

            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            HelloApplication.setScene("articles-panel.fxml", stage);
        }
    }

    private void registrationUser() {
        String login = reg_login.getCharacters().toString();
        String email = reg_email.getCharacters().toString();
        String pass = reg_pass.getCharacters().toString();

        reg_login.setStyle("-fx-border-color: #fafafa");
        reg_email.setStyle("-fx-border-color: #fafafa");
        reg_pass.setStyle("-fx-border-color: #fafafa");

        if(login.length() <= 1)
            reg_login.setStyle("-fx-border-color: #e06249");
        else if(email.length() <= 5 || !email.contains("@") || !email.contains("."))
            reg_email.setStyle("-fx-border-color: #e06249");
        else if(pass.length() <= 3)
            reg_pass.setStyle("-fx-border-color: #e06249");
        else if(!reg_rights.isSelected())
            reg_btn.setText("Поставьте галочку");
        else if(db.isExistsUser(login))
            reg_btn.setText("Введите другой логин");
        else {
            db.regUser(login, email, md5String(pass));
            reg_login.setText("");
            reg_email.setText("");
            reg_pass.setText("");
            reg_btn.setText("Все готово :)");
        }
    }

    public static String md5String(String pass) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        String m5dHex = bigInteger.toString(16);

        while(m5dHex.length() < 32)
            m5dHex = "0" + m5dHex;

        return m5dHex;
    }

}
