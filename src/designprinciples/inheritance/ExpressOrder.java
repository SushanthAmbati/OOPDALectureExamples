package designprinciples.inheritance;

class ExpressOrder extends Order {
    public ExpressOrder(String item, double weight) { 
        super(item, weight); 
    }

    public double shippingCost() { 
        return 15 + weight * 1.0; 
    }


}
