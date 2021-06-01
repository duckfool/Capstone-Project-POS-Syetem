package Orders;

import Orders.Alerts.Error_Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Class that Archives Inventory
public class Archive extends Main_Menu{
    Stage a = new Stage();

    //Weekly Inventory Function
    public void Weekly_Inventory(){
        Button fin = new Button("Finalize");
        a.setTitle("Weekly Inventory");
        TilePane t = new TilePane();
        t.setAlignment(Pos.CENTER);
        Label text = new Label("Select a date to finalize inventory.");
        DatePicker date = new DatePicker();

        //Event Handler for Setting Date
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                LocalDate local = date.getValue();
                String formattedDate = local.format(DateTimeFormatter.ofPattern("dd_MM_yy"));
                text.setText("Inventory_" + formattedDate);
            }
        };
        date.setShowWeekNumbers(true);
        date.setOnAction(event);

        //Weekly Scene Layout and Buttons
        t.getChildren().addAll(date, text, fin);
        Scene weekly = new Scene(t, 200, 200);
        a.setScene(weekly);
        a.initModality(Modality.APPLICATION_MODAL);
        a.show();

        fin.setOnAction(e -> {
            if(date.getValue() == null)
                new Error_Message().DisplayError("Error", "Please select a finalize date.");
            else{
            try {
                Close_Date(text);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }});
    }

    //Close Date Function Takes Date and Stores in Name
    public void Close_Date(Label t) throws SQLException {
        String Name = t.getText();
        a.close();
        postInventory(Name);
    }
}
