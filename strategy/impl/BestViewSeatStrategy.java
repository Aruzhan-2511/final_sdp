package strategy.impl;

import builder.model.Seat;
import strategy.SeatSelectionStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BestViewSeatStrategy implements SeatSelectionStrategy {
    @Override
    public Optional<Seat> selectSeat(List<Seat> availableSeats) {
        return availableSeats.stream().max(Comparator.comparingInt(Seat::getViewRating));
    }
}
