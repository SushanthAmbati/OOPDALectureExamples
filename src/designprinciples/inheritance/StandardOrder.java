package designprinciples.inheritance;

class StandardOrder extends Order {
    StandardOrder(String item, double weight) { 
        super(item, weight); 
    }

    double shippingCost() { 
        return 5 + weight * 0.5; 
    }
}
