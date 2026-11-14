package decorator;

import decorator.extra.SnackVoucherDecorator;
import decorator.extra.SouvenirProgramDecorator;
import factory.product.Ticket;

import java.util.function.UnaryOperator;

public enum TicketExtra {
    SOUVENIR_PROGRAM("souvenir program", SouvenirProgramDecorator.PRICE, SouvenirProgramDecorator::new),
    SNACK_VOUCHER("snack voucher", SnackVoucherDecorator.PRICE, SnackVoucherDecorator::new);

    private final String title;
    private final double price;
    private final UnaryOperator<Ticket> wrapper;

    TicketExtra(String title, double price, UnaryOperator<Ticket> wrapper) {
        this.title = title;
        this.price = price;
        this.wrapper = wrapper;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }

    public Ticket applyTo(Ticket ticket) {
        return wrapper.apply(ticket);
    }
}
