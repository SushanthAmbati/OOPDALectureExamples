package designprinciples.inheritance;

abstract class Order {
    protected String item;
    protected double weight;

    Order(String item, double weight) {
        this.item = item;
        this.weight = weight;
    }

    abstract double shippingCost();
}
