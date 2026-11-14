package decorator.extra;

import decorator.TicketDecorator;
import factory.product.Ticket;

public class SnackVoucherDecorator extends TicketDecorator {
    public static final double PRICE = 500.0;

    public SnackVoucherDecorator(Ticket ticket) {
        super(ticket);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Snack voucher";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + PRICE;
    }
}
