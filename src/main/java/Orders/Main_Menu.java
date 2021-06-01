package Orders;

import Orders.Alerts.Close_Program;
import Orders.Tables.Archived_Inventory_Table;
import Orders.Tables.Inventory_Table;
import Orders.Tables.Order_Table;
import Orders.Tables.Product_Table;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import java.sql.SQLException;


//Class for Main Menu Screen
public class Main_Menu extends Application {
    private Connection_SQL dataAccess;
    Button product, orders, inventory, back_main_inv, back_main_ord, close;
    Stage menu;
    Scene main_scene, ord_scene, inv_scene, inv_view_scene, inv_post_scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        menu = primaryStage;
        menu.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        //Product Button
        product = new Button("Store Products");
        product.setOnAction(e -> {
            try {
                new Product_Table();
            } catch (SQLException | ClassNotFoundException throwable) {
                throwable.printStackTrace();
            }
        });

        //Orders Button
        orders = new Button("Ordering");
        orders.setOnAction(e -> menu.setScene(ord_scene));

        //Inventory Button
        inventory = new Button("Inventory");
        inventory.setOnAction(e -> menu.setScene(inv_scene));


        //Back Button
        back_main_inv = new Button("Back");
        back_main_inv.setOnAction(e -> menu.setScene(main_scene));
        back_main_ord = new Button("Back");
        back_main_ord.setOnAction(e -> menu.setScene(main_scene));

        //Close Button
        close = new Button("Exit Program");
        close.setOnAction(e -> closeProgram());

        //Main Menu Layout
        VBox main_layout = new VBox(20);
        main_layout.getChildren().addAll(product, orders, inventory, close);
        main_layout.setAlignment(Pos.CENTER);
        main_scene = new Scene(main_layout, 300, 250);
        primaryStage.setScene(main_scene);
        primaryStage.setTitle("Main");
        primaryStage.show();


        //Orders Layout and Buttons
        Button ord_now = new Button("Place Order");
        VBox ord_layout = new VBox(20);
        ord_layout.getChildren().addAll(ord_now, back_main_ord);
        ord_layout.setAlignment(Pos.CENTER);
        ord_scene = new Scene(ord_layout, 300, 250);

        ord_now.setOnAction(e -> {
            try {
                dataAccess = new Connection_SQL("jdbc:mysql://localhost:3306/items", "root", "P@ssword123");
                dataAccess.adjustPar();
                new Order_Table();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });

        //Inventory Layout and Buttons
        Button inv_post = new Button("Inventory Posting");
        Button inv_view = new Button("View Inventory");
        VBox inv_layout = new VBox(20);
        inv_layout.getChildren().addAll(inv_post, inv_view, back_main_inv);
        inv_layout.setAlignment(Pos.CENTER);
        inv_scene = new Scene(inv_layout, 300, 250);
        ComboBox inv_list = new ComboBox();
        inv_view.setOnAction(e -> {
            menu.setScene(inv_view_scene);
            try {
                dropDownInventory(inv_list);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
        inv_post.setOnAction(e -> menu.setScene(inv_post_scene));

        //Inventory View Layout and Buttons
        VBox inv_view_layout = new VBox(20);
        Label text = new Label();
        text.setText("Which week would you like to view?");
        Button current_inv = new Button("Current Inventory");
        Button back_inv = new Button("Back");
        Button view_past = new Button("View");
        inv_view_layout.getChildren().addAll(text, current_inv, inv_list, view_past, back_inv);
        inv_view_layout.setAlignment(Pos.CENTER);
        inv_view_scene = new Scene(inv_view_layout, 300, 250);
        inv_list.setPromptText("Select Inventory");
        view_past.setOnAction(e -> {
           String select = inv_list.getValue().toString();
            try {
                nameOldInventory(select);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });

        current_inv.setOnAction(e -> {
            try {
                new Inventory_Table().View_Inventory();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
        back_inv.setOnAction(e -> menu.setScene(inv_scene));

        //Inventory Post Layout and Buttons
        VBox inv_post_layout = new VBox(20);
        Button inv_edit = new Button("Edit Current Inventory");
        Button inv_new = new Button("New Inventory");
        Button back_inv2 = new Button("Back");
        inv_post_layout.getChildren().addAll(inv_edit, inv_new, back_inv2);
        inv_post_layout.setAlignment(Pos.CENTER);
        inv_post_scene = new Scene(inv_post_layout, 300, 250);
        back_inv2.setOnAction(e -> menu.setScene(inv_scene));
        inv_edit.setOnAction(e -> {
            try {
                new Inventory_Table();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
        inv_new.setOnAction(e -> {
            try {
                nameInventory();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
    }

    //Close Program
    private void closeProgram() {
        boolean answer = Close_Program.DisplayAlert("Exit Window", "Close the Program?");
        if (answer) {
            menu.close();
        }
    }

    //Function to Name Inventory, Calls Archive
    private void nameInventory() throws SQLException {
        boolean answer = Close_Program.DisplayAlert("New Inventory", "Would you like to update inventory with new values?");
        if (answer) {
            new Archive().Weekly_Inventory();
        }

    }

    //Takes Named Inventory and Sets Archived Table to Name
    public void postInventory(String Name) throws SQLException {
        dataAccess = new Connection_SQL("jdbc:mysql://localhost:3306/items", "root", "P@ssword123");
        dataAccess.archiveInventory(Name);
        try {
            new Inventory_Table();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //Sets Values from Inventory Names and Stores them into ComboBox
    public void dropDownInventory(ComboBox combo) throws SQLException {
        dataAccess = new Connection_SQL("jdbc:mysql://localhost:3306/items", "root", "P@ssword123");
        combo.setItems(dataAccess.menuInventory());
    }

    //Renames Live Inventory to an Archived Inventory
    public void nameOldInventory(String Name) throws SQLException {
        dataAccess = new Connection_SQL("jdbc:mysql://localhost:3306/items", "root", "P@ssword123");
        dataAccess.oldInventory(Name);
        viewOldInventory(Name);
    }

    //Calls Table for Archived Inventory
    public void viewOldInventory(String Name){
        try{
            new Archived_Inventory_Table(Name);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}