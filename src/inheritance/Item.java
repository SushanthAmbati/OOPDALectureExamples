package inheritance;

public class Item {
    protected String description;
    protected Color color;
    protected Size size;
 
    public Item(String description, Color color, Size size) {
        this.description = description;
        this.color = color;
        this.size = size;
    }
 
    public void setColor(Color color){ 
        this.color = color; 
    }
 
    public void setSize(Size size){ 
        this.size = size; 
    }
 
    public Color getColor(){ 
        return color; 
    }
 
    public Size getSize(){ 
        return size; 
    }
 
    // getClass() is the ACTUAL object's class, not the variable's type
    public String dynamicType() { 
        return getClass().getSimpleName(); 
    }
 
    @Override
    public String toString() {
        return description + " (" + color + ", " + size + ")";
    }
}
