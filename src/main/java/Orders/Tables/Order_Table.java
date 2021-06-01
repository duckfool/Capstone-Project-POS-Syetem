package Orders.Tables;

import Orders.Alerts.Close_Program;
import Orders.Connection_SQL;
import Orders.SettersGetters.Order;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.sql.SQLException;

public class Order_Table {

    //Class for connecting SQL Order Table to Main Menu
    private final Connection_SQL dataAccess;
    Stage ord_stage = new Stage();

    public Order_Table() throws SQLException {
        dataAccess = new Connection_SQL("jdbc:mysql://localhost:3306/items", "root", "P@ssword123");
        TableColumn<Order, Integer> IDCol = new TableColumn<>("Item ID");
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        TableColumn<Order, String> NameCol = new TableColumn<>("Item Name");
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TableColumn<Order, Float> QuantityCol = new TableColumn<>("On-Hand");
        QuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        TableColumn<Order, Integer> OrderCol = new TableColumn<>("Order");
        OrderCol.setCellValueFactory(new PropertyValueFactory<>("Order"));
        TableColumn<Order, Integer> SuggestedCol = new TableColumn<>("Suggested Order");
        SuggestedCol.setCellValueFactory(new PropertyValueFactory<>("Suggested_Order"));
        TableColumn<Order, Integer> Order_His1Col = new TableColumn<>("Previous Order");
        Order_His1Col.setCellValueFactory(new PropertyValueFactory<>("Order_History1"));
        TableColumn<Order, Integer> Order_His2Col = new TableColumn<>("2 Orders Ago");
        Order_His2Col.setCellValueFactory(new PropertyValueFactory<>("Order_History2"));

        //Editable Orders
        OrderCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        OrderCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Order, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Order, Integer> event) {
                        ((Order) event.getTableView().getItems().get(event.getTablePosition().getRow())).setOrder(event.getNewValue());
                        Integer order = event.getNewValue();
                        Integer ID = event.getRowValue().getID();
                        try {
                            dataAccess.updateOrder(order, ID);
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
        );

        //Order Screen
        ord_stage.initModality(Modality.APPLICATION_MODAL);

        //Layout for Order Table
        TableView<Order> ordTable = new TableView<>();
        ordTable.setEditable(true);
        ordTable.getColumns().addAll(IDCol, NameCol, QuantityCol, OrderCol, SuggestedCol, Order_His1Col, Order_His2Col);
        ordTable.getItems().addAll(dataAccess.getOrderList());

        //Send Order Button
        Button send = new Button("Send Order Now");
        send.setOnAction(e -> {
            sendOrder();
        });

        //Layout for Inventory Scene
        BorderPane ord_layout = new BorderPane();
        ord_layout.setTop(ordTable);
        ord_layout.setBottom(send);
        Scene inv_scene = new Scene(ord_layout, 400, 400);
        ord_stage.setScene(inv_scene);
        ord_stage.show();
    }

    //Class that sends out an Order and Resets the Par and Order History
    public void sendOrder(){
        boolean answer = Close_Program.DisplayAlert("Send Order", "Send Order? (Cannot be undone)");
        if (answer) {
            dataAccess.adjustOrder();
            ord_stage.close();
        }
    }
}
