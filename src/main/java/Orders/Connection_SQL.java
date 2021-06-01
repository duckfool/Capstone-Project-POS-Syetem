package Orders;

import Orders.SettersGetters.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.*;

//Connection Class
public class Connection_SQL {

    //Connection to SQL Database
    private final Connection connect;
    public Connection_SQL(String url, String username, String password) throws SQLException {
        connect = DriverManager.getConnection(url, username, password);
        connect.setAutoCommit(true);
    }

    //Array for Product Table
    public List<Product> getProductList() {
        try (
                Statement state = connect.createStatement();
                ResultSet rs = state.executeQuery("SELECT * FROM product")
        ) {
            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                Integer ID = rs.getInt("ID");
                String Name = rs.getString("Name");
                String Unit = rs.getString("Unit");
                Float Cost = rs.getFloat("Cost");
                String Description = rs.getString("Description");
                Product product = new Product(ID, Name, Unit, Cost, Description);
                productList.add(product);
            }
            return productList;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError " + ex.getErrorCode());
        }
        return getProductList();
    }

    //Array for Inventory Table
    public List<Inventory> getInventoryList() {
        try (
                Statement state = connect.createStatement();
                ResultSet rs = state.executeQuery("SELECT * FROM stock")
        ) {
            List<Inventory> inventoryList = new ArrayList<>();
            while (rs.next()) {
                Integer ID = rs.getInt("ID");
                String Name = rs.getString("Name");
                Float Quantity = rs.getFloat("Quantity");
                String Unit = rs.getString("Unit");
                Inventory inventory = new Inventory(ID, Name, Quantity, Unit);
                inventoryList.add(inventory);
            }
            return inventoryList;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError " + ex.getErrorCode());
        }
        return getInventoryList();
    }

    //Array for Order Table
    public List<Order> getOrderList() {
        try (
                Statement state = connect.createStatement();
                ResultSet rs = state.executeQuery("SELECT * FROM order_purchase")
        ) {
            List<Order> orderList = new ArrayList<>();
            while (rs.next()) {
                Integer ID = rs.getInt("ID");
                String Name = rs.getString("Name");
                Float Quantity = rs.getFloat("Quantity");
                Integer Purchase = rs.getInt("Purchase");
                Integer Suggested_Purchase = rs.getInt("Suggested_Purchase");
                Integer Purchase_Previous1 = rs.getInt("Purchase_Previous1");
                Integer Purchase_Previous2 = rs.getInt("Purchase_Previous2");
                Order order = new Order(ID, Name, Quantity, Purchase, Suggested_Purchase, Purchase_Previous1, Purchase_Previous2);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError " + ex.getErrorCode());
        }
        return getOrderList();
    }

    //Updates Product List
    public void updateInputs(String name, String unit, Float cost, String desc) {
        try (
                PreparedStatement inv_state = connect.prepareStatement("INSERT INTO live_inventory()" +
                        "VALUES()");
                PreparedStatement ord_state = connect.prepareStatement("INSERT INTO orders()" +
                        "VALUES()");
                PreparedStatement state = connect.prepareStatement("INSERT INTO product(Name, Unit, Cost, Description)" +
                        "VALUES(?, ?, ?, ?);")
        ) {
            inv_state.execute();
            ord_state.execute();
            state.setString(1, name);
            state.setString(2, unit);
            state.setFloat(3, cost);
            state.setString(4, desc);
            state.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError " + ex.getErrorCode());
        }
    }

    //Update Quantity Column
    public void updateQuantity(Float quantity, Integer ID) throws SQLException {
        try (
                PreparedStatement state = connect.prepareStatement("UPDATE live_inventory SET Quantity=? WHERE ID=?")
        ) {
            state.setFloat(1, quantity);
            state.setInt(2, ID);
            state.executeUpdate();
        }
    }

    //Update Name Column
    public void updateName(String name, Integer ID) throws SQLException {
        try (
                PreparedStatement state = connect.prepareStatement("UPDATE product SET Name=? WHERE ID=?")
        ) {
            state.setString(1, name);
            state.setInt(2, ID);
            state.executeUpdate();
        }
    }

    //Update Unit Column
    public void updateUnit(String unit, Integer ID) throws SQLException {
        try (
                PreparedStatement state = connect.prepareStatement("UPDATE product SET Unit=? WHERE ID=?")
        ) {
            state.setString(1, unit);
            state.setInt(2, ID);
            state.executeUpdate();
        }
    }

    //Update Cost Column
    public void updateCost(Float cost, Integer ID) throws SQLException {
        try (
                PreparedStatement state = connect.prepareStatement("UPDATE product SET Cost=? WHERE ID=?")
        ) {
            state.setFloat(1, cost);
            state.setInt(2, ID);
            state.executeUpdate();
        }
    }

    //Update Description Column
    public void updateDesc(String desc, Integer ID) throws SQLException {
        try (
                PreparedStatement state = connect.prepareStatement("UPDATE product SET Description=? WHERE ID=?")
        ) {
            state.setString(1, desc);
            state.setInt(2, ID);
            state.executeUpdate();
        }
    }

    //Update Order Column
    public void updateOrder(Integer order, Integer ID) throws SQLException {
        try (
                PreparedStatement state = connect.prepareStatement("UPDATE orders SET Purchase=? WHERE ID=?")
        ) {
            state.setInt(1, order);
            state.setInt(2, ID);
            state.executeUpdate();
        }
    }

    //Inserts Row into Names
    public void updateNames(String Name) {
        try (
                PreparedStatement state = connect.prepareStatement("INSERT INTO names (Name)" +
                        "VALUES (?)")
        ) {
            state.setString(1, Name);
            state.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //Archive Inventory
    public void archiveInventory(String Name) {
        try (
                PreparedStatement state = connect.prepareStatement("ALTER TABLE live_inventory RENAME TO " + Name);
                PreparedStatement new_state = connect.prepareStatement("CREATE TABLE live_inventory(" +
                        "ID INT NOT NULL AUTO_INCREMENT KEY, " +
                        "Quantity FLOAT," +
                        "Previous_Stock1 FLOAT," +
                        "Previous_Stock2 FLOAT" +
                        ")");
                PreparedStatement auto_state = connect.prepareStatement("ALTER TABLE live_inventory AUTO_INCREMENT 100001");
                PreparedStatement move_state = connect.prepareStatement("UPDATE live_inventory" +
                        " SET Previous_Stock2=(SELECT Previous_Stock1 FROM "+Name+" " +
                        "WHERE live_inventory.ID = "+Name+".ID);");
                PreparedStatement move_state1 = connect.prepareStatement("UPDATE live_inventory" +
                        " SET Previous_Stock1=(SELECT Quantity FROM "+Name+" " +
                        "WHERE live_inventory.ID = "+Name+".ID);")
        ) {
            state.execute();
            new_state.execute();
            auto_state.execute();
            invIncrement();
            move_state.executeUpdate();
            move_state1.executeUpdate();
            updateNames(Name);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //Creates new Live Inventory with Correct Number of Rows
    public void invIncrement() throws SQLException {
        int count = 0;
        try (
                PreparedStatement state = connect.prepareStatement("SELECT COUNT(ID) FROM product");
                PreparedStatement inv_state = connect.prepareStatement("INSERT INTO live_inventory() VALUES()")
        ) {
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            for (int i = 0; i <= count; i++) {
                inv_state.execute();
            }
        }
    }

    //Observable List to get Names of Previous Inventories
    public ObservableList menuInventory() {
        ObservableList data = FXCollections.observableArrayList();
        try (
                PreparedStatement state = connect.prepareStatement("SELECT Name FROM names")
        ) {
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                String test = rs.getString("Name");
                data.add(test);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return data;
    }

    //Creates View for Archived Inventory
    public void oldInventory(String Name) {
        try (
                PreparedStatement state = connect.prepareStatement("CREATE OR REPLACE VIEW " + Name + "_stock AS " +
                        "SELECT product.ID, product.Name, " + Name + ".Quantity, product.Unit " +
                        "FROM product " +
                        "INNER JOIN " + Name + " ON product.ID = " + Name + ".ID")
        ) {
            state.execute();
            getOldInventoryList(Name);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //Array for Archived Inventory
    public List<Old_Inventory> getOldInventoryList(String invName) {
        try (
                Statement state = connect.createStatement();
                ResultSet rs = state.executeQuery("SELECT * FROM " + invName + "_stock")
        ) {
            List<Old_Inventory> oldinventoryList = new ArrayList<>();
            while (rs.next()) {
                Integer ID = rs.getInt("ID");
                String Name = rs.getString("Name");
                Float Quantity = rs.getFloat("Quantity");
                String Unit = rs.getString("Unit");
                Old_Inventory oldInventory = new Old_Inventory(ID, Name, Quantity, Unit);
                oldinventoryList.add(oldInventory);
            }
            return oldinventoryList;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError " + ex.getErrorCode());
        }
        return getOldInventoryList(invName);
    }

    //Updates Order Tables with new Order
    public void adjustOrder(){
        try(
                PreparedStatement state = connect.prepareStatement("UPDATE orders SET Purchase_Previous2 = Purchase_Previous1");
                PreparedStatement state1 = connect.prepareStatement("UPDATE orders SET Purchase_Previous1 = Purchase");
                PreparedStatement state2 = connect.prepareStatement("UPDATE orders SET Purchase = NULL");
                PreparedStatement state3 = connect.prepareStatement("UPDATE orders SET Suggested_Purchase =(((((" +
                        " (SELECT Quantity FROM live_inventory WHERE orders.ID = live_inventory.ID) +" +
                        " (SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) +" +
                        " (SELECT Previous_Stock2 FROM live_inventory WHERE orders.ID = live_inventory.ID))/3) -" +
                        " (SELECT Quantity FROM live_inventory WHERE orders.ID = live_inventory.ID)) +" +
                        " ((((SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous1) +" +
                        " ((SELECT Previous_Stock2 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous2))/2)) *" +
                        " ((((SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous1) -" +
                        " (SELECT Quantity FROM live_inventory WHERE orders.ID = live_inventory.ID)) /" +
                        " ((SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous1))) -" +
                        " ((SELECT Quantity FROM live_inventory WHERE orders.ID = live_inventory.ID) /" +
                        " ((SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous1))")
                ) {
            state.executeUpdate();
            state1.executeUpdate();
            state2.executeUpdate();
            state3.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public void adjustPar(){
        try(
                PreparedStatement state = connect.prepareStatement("UPDATE orders SET Suggested_Purchase =(((((" +
                        " (SELECT Quantity FROM live_inventory WHERE orders.ID = live_inventory.ID) +" +
                        " (SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) +" +
                        " (SELECT Previous_Stock2 FROM live_inventory WHERE orders.ID = live_inventory.ID))/3) -" +
                        " (SELECT Quantity FROM live_inventory WHERE orders.ID = live_inventory.ID)) +" +
                        " ((((SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous1) +" +
                        " ((SELECT Previous_Stock2 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous2))/2)) *" +
                        " ((((SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous1) -" +
                        " (SELECT Quantity FROM live_inventory WHERE orders.ID = live_inventory.ID)) /" +
                        " ((SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous1))) -" +
                        " ((SELECT Quantity FROM live_inventory WHERE orders.ID = live_inventory.ID) /" +
                        " ((SELECT Previous_Stock1 FROM live_inventory WHERE orders.ID = live_inventory.ID) + Purchase_Previous1))")
                ) {
            state.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}