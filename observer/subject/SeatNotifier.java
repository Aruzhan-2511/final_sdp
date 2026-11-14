package observer.subject;

import builder.model.Event;
import builder.model.Seat;
import observer.subscriber.SeatObserver;

public interface SeatNotifier {
    void subscribe(String eventId, SeatObserver observer);
    void publishSeatAvailable(Event event, Seat seat);
}
