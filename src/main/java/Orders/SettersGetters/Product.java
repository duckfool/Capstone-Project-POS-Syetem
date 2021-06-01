package Orders.SettersGetters;
import javafx.beans.property.*;

public class Product {
    //Setters and Getters for Product Table
    private final IntegerProperty ID = new SimpleIntegerProperty(this, "ID");
    private final StringProperty Name = new SimpleStringProperty(this, "Name");
    private final StringProperty Unit = new SimpleStringProperty(this, "Unit");
    private final FloatProperty Cost = new SimpleFloatProperty(this, "Cost");
    private final StringProperty Description = new SimpleStringProperty(this, "Description");


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
    public StringProperty UnitProperty(){
        return Unit;
    }
    public final String getUnit(){
        return Unit.get();
    }
    public final void setUnit(String Unit){
        this.Unit.set(Unit);
    }
    public final FloatProperty CostProperty(){
        return Cost;
    }
    public final Float getCost(){
        return Cost.get();
    }
    public final void setCost(Float Cost){
        this.Cost.set(Cost);
    }
    public final StringProperty DescriptionProperty(){
        return Description;
    }
    public final String getDescription(){
        return Description.get();
    }
    public final void setDescription(String Description){
        this.Description.set(Description);
    }

    public Product() {}

    //Class that implements Setters and Getters
    public Product(Integer ID, String Name, String Unit, Float Cost, String Description){
        setID(ID);
        setName(Name);
        setUnit(Unit);
        setCost(Cost);
        setDescription(Description);
    }
}
