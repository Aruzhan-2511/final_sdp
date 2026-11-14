package observer.subject;

import builder.model.Event;
import builder.model.Seat;
import observer.subscriber.SeatObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatPublisher implements SeatNotifier {
    private final Map<String, List<SeatObserver>> subscribers = new HashMap<>();

    @Override
    public void subscribe(String eventId, SeatObserver observer) {
        List<SeatObserver> observers = subscribers.computeIfAbsent(eventId, id -> new ArrayList<>());
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void publishSeatAvailable(Event event, Seat seat) {
        for (SeatObserver observer : subscribers.getOrDefault(event.getId(), List.of())) {
            observer.onSeatAvailable(event, seat);
        }
    }
}
