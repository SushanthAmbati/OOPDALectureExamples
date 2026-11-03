package designprinciples.composition;

public class Order {
    private String item;
    private double weight;
    private ShippingStrategy shipping;   // HAS-A
    private PaymentMethod payment;       // HAS-A

    Order(String item, double weight, ShippingStrategy shipping, PaymentMethod payment) {
        this.item = item;
        this.weight = weight;
        this.shipping = shipping;
        this.payment = payment;
    }

    public double shippingCost() {
        return shipping.cost(weight);
    }

    public void checkout() {
        double total = shippingCost();
        payment.pay(total);
    }

    public void setShipping(ShippingStrategy s) { 
        this.shipping = s; 
    }

    public void setPayment(PaymentMethod p){ 
        this.payment = p; 
    }
}
