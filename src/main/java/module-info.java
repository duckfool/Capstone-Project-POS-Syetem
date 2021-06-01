module Capstone_Project.main {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens Orders;
    opens Orders.Tables;
    opens Orders.SettersGetters;
    opens Orders.Alerts;
}