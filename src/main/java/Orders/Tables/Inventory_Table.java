package Orders.Tables;

import Orders.Connection_SQL;
import Orders.SettersGetters.Inventory;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import java.sql.SQLException;

public class Inventory_Table {

    //Class for connecting SQL Inventory Table to Main Menu
    private final Connection_SQL dataAccess;
    TableView<Inventory> invTable = new TableView<>();

    public Inventory_Table()throws SQLException {
        dataAccess = new Connection_SQL("jdbc:mysql://localhost:3306/items", "root", "P@ssword123");
        TableColumn<Inventory, Integer> IDCol = new TableColumn<>("Item ID");
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        TableColumn<Inventory, String> NameCol = new TableColumn<>("Item Name");
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TableColumn<Inventory, Float> QuantityCol = new TableColumn<>("Quantity");
        QuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        TableColumn<Inventory, String> UnitCol = new TableColumn<>("Unit of Measurement");
        UnitCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));

        //Editable Quantity
        QuantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        QuantityCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Inventory, Float>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Inventory, Float> event) {
                      ((Inventory) event.getTableView().getItems().get(event.getTablePosition().getRow())).setQuantity(event.getNewValue());
                      Float quantity = event.getNewValue();
                      Integer ID = event.getRowValue().getID();
                        try {
                            dataAccess.updateQuantity(quantity, ID);
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
        );

        //Inventory Screen
        Stage inv_stage = new Stage();
        inv_stage.initModality(Modality.APPLICATION_MODAL);

        //Layout for Inventory Table
        invTable.getColumns().addAll(IDCol, NameCol, QuantityCol, UnitCol);
        invTable.getItems().addAll(dataAccess.getInventoryList());
        invTable.setEditable(true);

        //Layout for Inventory Scene
        BorderPane inv_layout = new BorderPane();
        inv_layout.setTop(invTable);
        Scene inv_scene = new Scene(inv_layout, 400, 400);
        inv_stage.setScene(inv_scene);
        inv_stage.show();
        inv_stage.setOnCloseRequest(e -> Edit_Inventory());
    }

    //Sets Table to be Viewable only
    public void View_Inventory(){
        invTable.setEditable(false);
    }

    //Sets Table to be Editable
    public void Edit_Inventory(){
        invTable.setEditable(true);
    }
}
