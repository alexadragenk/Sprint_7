package test;

public class Order {
    private String[] color;

    public Order(String[] color) {
        this.color = color;
    }
    public Order() {
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }
}
