package factory.creator;

import builder.model.Event;
import builder.model.Seat;
import factory.product.ConcertTicket;
import factory.product.Ticket;

public class ConcertTicketCreator implements TicketCreator {
    @Override
    public Ticket createTicket(Event event, Seat seat) {
        return new ConcertTicket(event, seat);
    }
}
