package facade.model;

import builder.model.Event;
import builder.model.Seat;
import factory.product.Ticket;

public class Reservation {
    private final String id;
    private final Event event;
    private final Seat seat;
    private final Ticket ticket;
    private ReservationStatus status = ReservationStatus.ACTIVE;

    public Reservation(String id, Event event, Seat seat, Ticket ticket) {
        this.id = id;
        this.event = event;
        this.seat = seat;
        this.ticket = ticket;
    }

    public String getId() { return id; }
    public Event getEvent() { return event; }
    public Seat getSeat() { return seat; }
    public Ticket getTicket() { return ticket; }
    public ReservationStatus getStatus() { return status; }

    public void cancel() {
        if (status != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Reservation is not active: " + id);
        }
        status = ReservationStatus.CANCELLED;
    }
}
