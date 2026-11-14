package facade;

import builder.model.Event;
import builder.model.Seat;
import facade.model.Reservation;
import factory.product.Ticket;

import java.util.HashMap;
import java.util.Map;

public class ReservationRepository {
    private final Map<String, Reservation> reservations = new HashMap<>();
    private int nextNumber = 1;

    public Reservation create(Event event, Seat seat, Ticket ticket) {
        String id = String.format("R%03d", nextNumber++);
        Reservation reservation = new Reservation(id, event, seat, ticket);
        reservations.put(id, reservation);
        return reservation;
    }

    public Reservation get(String id) {
        Reservation reservation = reservations.get(id);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found: " + id);
        }
        return reservation;
    }
}
