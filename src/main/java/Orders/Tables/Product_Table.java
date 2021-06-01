package Orders.Tables;
import Orders.Connection_SQL;
import Orders.SettersGetters.Product;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;

import java.sql.*;

//Class for Connecting SQL Product Table to Main Menu
public class Product_Table{
    //Connection Variable
    private final Connection_SQL dataAccess;
    private final TextField nameInput, unitInput, costInput, descInput;
    private final TableView<Product> proTable;

    //TableView with Appropriate Columns.
    public Product_Table() throws SQLException, ClassNotFoundException {
            dataAccess = new Connection_SQL("jdbc:mysql://localhost:3306/items", "root", "P@ssword123");
            TableColumn<Product, Integer> IDCol = new TableColumn<>("Item ID");
            IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            TableColumn<Product, String> NameCol = new TableColumn<>("Item Name");
            NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            TableColumn<Product, String> UnitCol = new TableColumn<>("Unit of Measurement");
            UnitCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));
            TableColumn<Product, Float> CostCol = new TableColumn<>("Cost of Item");
            CostCol.setCellValueFactory(new PropertyValueFactory<>("Cost"));
            TableColumn<Product, String> DescCol = new TableColumn<>("Description of Item");
            DescCol.setCellValueFactory(new PropertyValueFactory<>("Description"));

            //Edit Name Column
            NameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            NameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, String> event) {
                        ((Product) event.getTableView().getItems().get(event.getTablePosition().getRow())).setName(event.getNewValue());
                        String name = event.getNewValue();
                        Integer ID = event.getRowValue().getID();
                        try {
                            dataAccess.updateName(name, ID);
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
        );

            //Edit Unit Column
            UnitCol.setCellFactory(TextFieldTableCell.forTableColumn());
            UnitCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, String> event) {
                        ((Product) event.getTableView().getItems().get(event.getTablePosition().getRow())).setUnit(event.getNewValue());
                        String unit = event.getNewValue();
                        Integer ID = event.getRowValue().getID();
                        try {
                            dataAccess.updateUnit(unit, ID);
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
        );
            //Editable Quantity
            CostCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            CostCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, Float>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, Float> event) {
                        ((Product) event.getTableView().getItems().get(event.getTablePosition().getRow())).setCost(event.getNewValue());
                        Float quantity = event.getNewValue();
                        Integer ID = event.getRowValue().getID();
                        try {
                            dataAccess.updateCost(quantity, ID);
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
        );
            //Edit Description Column
            DescCol.setCellFactory(TextFieldTableCell.forTableColumn());
            DescCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, String> event) {
                        ((Product) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDescription(event.getNewValue());
                        String desc = event.getNewValue();
                        Integer ID = event.getRowValue().getID();
                        try {
                            dataAccess.updateDesc(desc, ID);
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
        );

            //Product Database Screen
            Stage pro_stage = new Stage();
            pro_stage.initModality(Modality.APPLICATION_MODAL);

            //Table Inputs and Buttons
            nameInput = new TextField();
            nameInput.setPromptText("Name");
            nameInput.setMinWidth(100);

            unitInput = new TextField();
            unitInput.setPromptText("Unit");

            costInput = new TextField();
            costInput.setPromptText("Cost");

            descInput = new TextField();
            descInput.setPromptText("Description");

            Button addProduct = new Button("Add");
            addProduct.setOnAction(e -> {
                try {
                    addProductClicked();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            });

            //Layout for Inputs
            HBox Product_Inputs = new HBox();
            Product_Inputs.setPadding(new Insets(10));
            Product_Inputs.setSpacing(10);
            Product_Inputs.getChildren().addAll(nameInput, unitInput, costInput, descInput, addProduct);

            //Layout for Product Table
            proTable = new TableView<>();
            proTable.setEditable(true);
            proTable.getColumns().addAll(IDCol, NameCol, UnitCol, CostCol, DescCol);
            proTable.getItems().addAll(dataAccess.getProductList());


            //Layout for Product Scene
            BorderPane pro_layout = new BorderPane();
            pro_layout.setTop(proTable);
            pro_layout.getChildren();
            pro_layout.setBottom(Product_Inputs);
            Scene pro_scene = new Scene(pro_layout, 400, 400);
            pro_stage.setScene(pro_scene);
            pro_stage.show();


    }
    //Event When Add Button is Clicked
    public void addProductClicked() throws SQLException {
        Product product = new Product();
        product.setName(nameInput.getText());
        product.setUnit(unitInput.getText());
        product.setCost(Float.parseFloat(costInput.getText()));
        product.setDescription(descInput.getText());
        String name = nameInput.getText();
        String unit = unitInput.getText();
        Float cost = Float.parseFloat(costInput.getText());
        String desc = descInput.getText();
        proTable.getItems().add(product);
        dataAccess.updateInputs(name, unit, cost, desc);
        nameInput.clear();
        unitInput.clear();
        costInput.clear();
        descInput.clear();
    }
}
