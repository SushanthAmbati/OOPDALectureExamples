package inheritance;

class MisbehavingRedItem extends RedItem {
 
    public MisbehavingRedItem(String description, Color color, Size size) {
        super(description, color, size);  // RedItem forces the color to RED...
        setColor(color);                  // ...then our override undoes that
    }
 
    @Override
    public void setColor(Color color) {
        this.color = color;   // does NOT call super: skips validateColor
    }
}