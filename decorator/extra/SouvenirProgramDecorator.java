package decorator.extra;

import decorator.TicketDecorator;
import factory.product.Ticket;

public class SouvenirProgramDecorator extends TicketDecorator {
    public static final double PRICE = 1000.0;

    public SouvenirProgramDecorator(Ticket ticket) {
        super(ticket);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Souvenir program";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + PRICE;
    }
}
