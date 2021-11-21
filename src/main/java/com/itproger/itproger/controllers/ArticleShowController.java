package com.itproger.itproger.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.itproger.itproger.DB;
import com.itproger.itproger.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ArticleShowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label show_text;

    @FXML
    private Label show_title;

    @FXML
    void back_btn(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        HelloApplication.setScene("articles-panel.fxml", stage);

    }

    @FXML
    void initialize() throws SQLException {
        DB db = new DB();
        int id = ArticlesPanelController.globalId;

        show_title.setText(db.getTitle(id));
        show_text.setText(db.getText(id));
        System.out.println(id);

    }

}
