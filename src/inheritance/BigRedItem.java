package inheritance;

public class BigRedItem extends RedItem {
 
    public BigRedItem(String description, Color color) {
        super(description, color, Size.LARGE);  // goes to RedItem, not Item
    }
 
    @Override
    public void setSize(Size size) {
        super.setSize(Size.LARGE);              // ignores the argument
    }
}