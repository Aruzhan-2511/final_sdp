package strategy;

import builder.model.Seat;

import java.util.List;
import java.util.Optional;

public class SeatSelector {
    private SeatSelectionStrategy strategy;

    public SeatSelector(SeatSelectionStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SeatSelectionStrategy strategy) {
        this.strategy = strategy;
    }

    public Optional<Seat> selectSeat(List<Seat> availableSeats) {
        return strategy.selectSeat(availableSeats);
    }
}
