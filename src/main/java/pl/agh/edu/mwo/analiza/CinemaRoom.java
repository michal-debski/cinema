package pl.agh.edu.mwo.analiza;

import java.util.ArrayList;
import java.util.List;

public class CinemaRoom {
    private final String name;
    private final String description;
    private List<Seat> seatsInCinemaRoom = new ArrayList<>();

    public CinemaRoom(String name, String description) {
        this.name = name;
        this.description = description;
        this.seatsInCinemaRoom = seatProducer();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }


    private List<Seat> seatProducer() {
        List<String> rowsInCinemaRoom = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
        for (String s : rowsInCinemaRoom) {
            for (int j = 1; j <= 10; j++) {
                Seat seat = new Seat(s + j, true);
                seatsInCinemaRoom.add(seat);
            }
        }
        return seatsInCinemaRoom;
    }
    public Seat getSelectedSeat(String seatName) {
        return seatsInCinemaRoom.stream().filter(seat -> seat.getName().equals(seatName)).findFirst().orElse(null);
    }


}
