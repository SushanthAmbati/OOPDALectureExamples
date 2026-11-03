package designprinciples.inheritance;

public class Driver {
    public static void main(String[] args) {
        Order a = new StandardOrder("Book", 2.0);
        
        Order temp = new ExpressOrder("Book", 2.0);
        a = temp; // changes mind


        Order b = new ExpressOrder("Lamp", 4.0);

        System.out.println(a.shippingCost());   // 17.0 (express now, not 6.0)
        System.out.println(b.shippingCost());   // 19.0
    }
}
