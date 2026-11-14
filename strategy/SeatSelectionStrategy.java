package strategy;

import builder.model.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatSelectionStrategy {
    Optional<Seat> selectSeat(List<Seat> availableSeats);
}
