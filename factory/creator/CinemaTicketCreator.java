package factory.creator;

import builder.model.Event;
import builder.model.Seat;
import factory.product.CinemaTicket;
import factory.product.Ticket;

public class CinemaTicketCreator implements TicketCreator {
    @Override
    public Ticket createTicket(Event event, Seat seat) {
        return new CinemaTicket(event, seat);
    }
}
