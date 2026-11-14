package observer.subscriber;

import builder.model.Event;
import builder.model.Seat;

import java.util.Objects;

public class UserNotifier implements SeatObserver {
    private final String userName;

    public UserNotifier(String userName) {
        this.userName = userName;
    }

    @Override
    public void onSeatAvailable(Event event, Seat seat) {
        System.out.println("Notification for " + userName + ": seat " + seat.getId()
                + " is now available for " + event.getTitle());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof UserNotifier notifier && userName.equals(notifier.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
