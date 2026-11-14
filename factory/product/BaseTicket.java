package factory.product;

import builder.model.Event;
import builder.model.Seat;

public abstract class BaseTicket implements Ticket {
    protected final Event event;
    protected final Seat seat;

    protected BaseTicket(Event event, Seat seat) {
        this.event = event;
        this.seat = seat;
    }

    @Override
    public double getPrice() {
        return seat.getPrice();
    }
}
