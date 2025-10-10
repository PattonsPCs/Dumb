package com.gui;

import com.anthony.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) {
        ToggleButton h2Btn = new ToggleButton("H2");
        ToggleButton sqliteBtn = new ToggleButton("SQLite");
        ToggleGroup dbToggleGroup = new ToggleGroup();


        h2Btn.setToggleGroup(dbToggleGroup);
        sqliteBtn.setToggleGroup(dbToggleGroup);

        final String[] selectedDb = {"H2"};

        dbToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == h2Btn) {
                selectedDb[0] = "H2";
            } else if (newToggle == sqliteBtn) {
                selectedDb[0] = "SQLite";
            }
            System.out.println("Selected DB: " + selectedDb[0]);
        });


        h2Btn.setSelected(true);
        Button createBtn = new Button("Create a Task");
        Button readBtn = new Button("Read a Task");
        Button updBtn = new Button("Update a Task");
        Button deleteBtn = new Button("Delete a Task");
        Button quitBtn = new Button("End the program");


        Label numberLabel = new Label("Task ID:");
        Spinner<Integer> numberSpinner = new Spinner<>(1, 100, 1);
        numberSpinner.setEditable(true);

        Label textLabel = new Label("Task Name:");
        TextArea ta = new TextArea();
        ta.setPrefRowCount(5);

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(5);

        createBtn.setOnAction(e -> {
            try {
                DatabaseManager dbManager = new DatabaseManager(selectedDb[0].toLowerCase());
                Database db = dbManager.getDatabase();
                outputArea.setText("Creating task: " + numberSpinner.getValue() + " with name " + ta.getText());
                AbstractTask task = new BasicTask(ta.getText(), Status.TODO, "User created task.");
                db.createTable();
                db.createTask(numberSpinner.getValue(), task);
            } catch (SQLException ex) {
                outputArea.setText("Error creating task: " + ex);
            }
        });

        readBtn.setOnAction(e -> {
            try {
                DatabaseManager dbManager = new DatabaseManager(selectedDb[0].toLowerCase());
                Database db = dbManager.getDatabase();
                outputArea.setText("Reading task: " + numberSpinner.getValue());
                outputArea.setText(db.readTask(numberSpinner.getValue()).toString());
            } catch (SQLException ex) {
                outputArea.setText("Error reading task: " + ex);
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                DatabaseManager dbManager = new DatabaseManager(selectedDb[0].toLowerCase());
                Database db = dbManager.getDatabase();
                outputArea.setText("Deleting task: " + numberSpinner.getValue());
                db.deleteTask(numberSpinner.getValue());
            } catch (SQLException ex) {
                outputArea.setText("Error deleting task: " + ex);
            }
        });

        quitBtn.setOnAction(e -> {
            Platform.exit();
        });


        HBox buttonBox = new HBox(10, createBtn, readBtn, deleteBtn, quitBtn);
        HBox idBox = new HBox(10, numberLabel, numberSpinner);
        HBox dbBox = new HBox(10, h2Btn, sqliteBtn);


        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                dbBox,
                buttonBox,
                idBox,
                textLabel,
                ta,
                outputArea
        );

        Scene scene = new Scene(root, 1024, 680);
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
