package Orders.Alerts;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Class to Close Program
public class Close_Program {
    static boolean answer = true;

    //DisplayAlert Function as Pop-Up
    public static boolean DisplayAlert(String title, String message){

        //Stage Layout
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(100);

        Label text = new Label();
        text.setText(message);

        //Buttons that set to True or False
        Button close = new Button("Yes");
        Button keep = new Button("No");
        close.setOnAction(e -> {
            answer = true;
            window.close();
        });
        keep.setOnAction(e -> {
            answer = false;
            window.close();
        });

        //Scene Layout
        VBox layout = new VBox();
        layout.getChildren().addAll(text, close, keep);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}
