package Orders.SettersGetters;
import javafx.beans.property.*;

public class Order {
    //Setters and Getters for Order Table
    private final IntegerProperty ID = new SimpleIntegerProperty(this, "ID");
    private final StringProperty Name = new SimpleStringProperty(this, "Name");
    private final FloatProperty Quantity = new SimpleFloatProperty(this, "Quantity");
    private final IntegerProperty Order = new SimpleIntegerProperty(this, "Order");
    private final IntegerProperty Suggested_Order = new SimpleIntegerProperty(this, "Suggested_Order");
    private final IntegerProperty Order_History1 = new SimpleIntegerProperty(this, "Order_History1");
    private final IntegerProperty Order_History2 = new SimpleIntegerProperty(this, "Order_History2");

    public IntegerProperty IDProperty(){
        return ID;
    }
    public final Integer getID(){
        return ID.get();
    }
    public final void setID(Integer ID){
        this.ID.set(ID);
    }
    public StringProperty NameProperty(){
        return Name;
    }
    public final String getName(){
        return Name.get();
    }
    public final void setName(String Name){
        this.Name.set(Name);
    }
    public FloatProperty QuantityProperty(){
        return Quantity;
    }
    public final Float getQuantity(){
        return Quantity.get();
    }
    public final void setQuantity(Float Quantity){
        this.Quantity.set(Quantity);
    }
    public IntegerProperty OrderProperty(){
        return Order;
    }
    public final Integer getOrder(){
        return Order.get();
    }
    public final void setOrder(Integer Order){
        this.Order.set(Order);
    }
    public IntegerProperty Suggested_OrderProperty(){
        return Suggested_Order;
    }
    public final Integer getSuggestedOrder(){
        return Suggested_Order.get();
    }
    public final void setSuggestedOrder(Integer Suggested_Order){
        this.Suggested_Order.set(Suggested_Order);
    }
    public IntegerProperty Order_History1Property(){
        return Order_History1;
    }
    public final Integer getOrder_History1(){
        return Order_History1.get();
    }
    public final void setOrder_History1(Integer Order_History1){
        this.Order_History1.set(Order_History1);
    }
    public IntegerProperty Order_History2Property(){
        return Order_History2;
    }
    public final Integer getOrder_History2(){
        return Order_History2.get();
    }
    public final void setOrder_History2(Integer Order_History2){
        this.Order_History2.set(Order_History2);
    }

    public Order(){}

    //Class that implements Setters and Getters
    public Order(Integer ID, String Name, Float Quantity, Integer Order, Integer Suggested_Order, Integer Order_History1, Integer Order_History2){
        setID(ID);
        setName(Name);
        setQuantity(Quantity);
        setOrder(Order);
        setSuggestedOrder(Suggested_Order);
        setOrder_History1(Order_History1);
        setOrder_History2(Order_History2);
    }
}
