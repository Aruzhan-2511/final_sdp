package facade;

import builder.model.Event;
import builder.model.Seat;
import decorator.TicketExtra;
import facade.model.Reservation;
import factory.creator.TicketCreatorProvider;
import factory.product.Ticket;
import observer.subject.SeatNotifier;
import observer.subject.SeatPublisher;
import observer.subscriber.SeatObserver;
import strategy.SeatSelectionStrategy;
import strategy.SeatSelector;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReservationFacade {
    private final Map<String, Event> events = new LinkedHashMap<>();
    private final ReservationRepository reservations;
    private final SeatNotifier seatNotifier;

    public ReservationFacade() {
        this(new ReservationRepository(), new SeatPublisher());
    }

    public ReservationFacade(ReservationRepository reservations, SeatNotifier seatNotifier) {
        this.reservations = reservations;
        this.seatNotifier = seatNotifier;
    }

    public void registerEvent(Event event) {
        if (events.putIfAbsent(event.getId(), event) != null) {
            throw new IllegalArgumentException("Event already registered: " + event.getId());
        }
    }

    public List<Event> getEvents() {
        return List.copyOf(events.values());
    }

    public List<Seat> getAvailableSeats(String eventId) {
        return findEvent(eventId).getSeats().stream()
                .filter(Seat::isAvailable)
                .toList();
    }

    public Optional<Seat> recommendSeat(String eventId, SeatSelectionStrategy strategy) {
        return new SeatSelector(strategy).selectSeat(getAvailableSeats(eventId));
    }

    public Reservation book(String eventId, String seatId, Collection<TicketExtra> extras) {
        Event event = findEvent(eventId);
        Seat seat = event.findSeat(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + seatId));
        Ticket ticket = TicketCreatorProvider.forEvent(event).createTicket(event, seat);
        for (TicketExtra extra : extras) {
            ticket = extra.applyTo(ticket);
        }
        seat.book();
        return reservations.create(event, seat, ticket);
    }

    public void cancel(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        reservation.cancel();
        reservation.getSeat().release();
        seatNotifier.publishSeatAvailable(reservation.getEvent(), reservation.getSeat());
    }

    public void subscribe(String eventId, SeatObserver observer) {
        seatNotifier.subscribe(findEvent(eventId).getId(), observer);
    }

    private Event findEvent(String eventId) {
        Event event = events.get(eventId);
        if (event == null) {
            throw new IllegalArgumentException("Event not found: " + eventId);
        }
        return event;
    }
}
