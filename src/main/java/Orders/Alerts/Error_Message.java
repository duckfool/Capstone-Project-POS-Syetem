package Orders.Alerts;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Error_Message {

    //Class to Call an Error Message
    public static void DisplayError(String title, String message) {

        //Stage Layout
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(100);
        Label text = new Label();
        text.setText(message);

        Button confirm = new Button("Back");
        confirm.setOnAction(e -> window.close());

        //Scene Layout
        VBox layout = new VBox();
        layout.getChildren().addAll(text, confirm);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    }
