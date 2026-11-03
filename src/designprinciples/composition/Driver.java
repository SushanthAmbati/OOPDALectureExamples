package designprinciples.composition;

public class Driver {
    public static void main(String[] args){
        PaymentMethod card   = amt -> System.out.println("Charging card: $" + amt);
        PaymentMethod paypal = amt -> System.out.println("Redirecting to PayPal: $" + amt);
        PaymentMethod crypto = amt -> System.out.println("Sending crypto: $" + amt);

        ShippingStrategy standard = w -> 5 + w * 0.5;
        ShippingStrategy express  = w -> 15 + w * 1.0;
        ShippingStrategy drone = w -> 50 + w * 2.0;

        Order a = new Order("Book", 2.0, standard, card);
        a.setShipping(express); // changes mind
        a.checkout();

        Order b = new Order("Lamp", 4.0, express, paypal);
        b.checkout();

        Order c = new Order("Laptop", 10.0, express, crypto);
        c.checkout();


    }
    
}
