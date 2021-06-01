package Orders.SettersGetters;

import javafx.beans.property.*;

public class Old_Inventory {
    //Setters and Getters for Archived Inventory Tables
    private final IntegerProperty ID = new SimpleIntegerProperty(this, "ID");
    private final StringProperty Name = new SimpleStringProperty(this, "Name");
    private final FloatProperty Quantity = new SimpleFloatProperty(this, "Quantity");
    private final StringProperty Unit = new SimpleStringProperty(this, "Unit");

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
    public StringProperty UnitProperty(){
        return Unit;
    }
    public final String getUnit(){ return Unit.get(); }
    public final void setUnit(String Unit){ this.Unit.set(Unit); }

    public Old_Inventory(){}

    //Class that implements Setters and Getters
    public Old_Inventory(Integer ID, String Name, Float Quantity, String Unit){
        setID(ID);
        setName(Name);
        setQuantity(Quantity);
        setUnit(Unit);
}
}
