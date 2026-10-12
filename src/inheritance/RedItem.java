package inheritance;

public class RedItem extends Item {
 
    public RedItem(String description, Color color, Size size) {
        // super(...) MUST be the first line. validateColor is static, so it
        // is allowed here, before the Item part of this object is built.
        super(description, validateColor(color), size);
    }
 
    @Override
    public void setColor(Color color) {
        super.setColor(validateColor(color));   // reuse Item's setter
    }
 
    public static Color validateColor(Color color) {
        if (color == Color.CRIMSON || color == Color.RED
                || color == Color.MAROON || color == Color.TOMATO) {
            return color;
        }
        return Color.RED;   // not red enough, so force it
    }
}