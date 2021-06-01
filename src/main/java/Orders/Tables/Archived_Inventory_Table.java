package Orders.Tables;
import Orders.Connection_SQL;
import Orders.SettersGetters.Old_Inventory;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Archived_Inventory_Table {

    //Class for connecting Archived Inventory Tables to Main Menu
    private final Connection_SQL dataAccess;
    TableView<Old_Inventory> invTable = new TableView<>();

    public Archived_Inventory_Table(String Name)throws SQLException {
        dataAccess = new Connection_SQL("jdbc:mysql://localhost:3306/items", "root", "P@ssword123");
        TableColumn<Old_Inventory, Integer> IDCol = new TableColumn<>("Item ID");
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        TableColumn<Old_Inventory, String> NameCol = new TableColumn<>("Item Name");
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TableColumn<Old_Inventory, Float> QuantityCol = new TableColumn<>("Quantity");
        QuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        TableColumn<Old_Inventory, String> UnitCol = new TableColumn<>("Unit of Measurement");
        UnitCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));

        //Inventory Screen
        Stage inv_stage = new Stage();
        inv_stage.initModality(Modality.APPLICATION_MODAL);

        //Layout for Inventory Table
        invTable.getColumns().addAll(IDCol, NameCol, QuantityCol, UnitCol);
        invTable.getItems().addAll(dataAccess.getOldInventoryList(Name));

        //Layout for Inventory Scene
        BorderPane inv_layout = new BorderPane();
        inv_layout.setTop(invTable);
        Scene inv_scene = new Scene(inv_layout, 400, 400);
        inv_stage.setScene(inv_scene);
        inv_stage.show();
    }
}
