package factory.creator;

import builder.model.Event;
import builder.model.Seat;
import factory.product.Ticket;

public interface TicketCreator {
    Ticket createTicket(Event event, Seat seat);
}
