package pl.agh.edu.mwo.analiza;

import java.util.ArrayList;
import java.util.List;

import static pl.agh.edu.mwo.analiza.Cinema.checkingIfThisCinemaContainsCinemaRoom;

public class CinemaRoom {
    private final String name;
    private final String description;
    private List<Seat> seatsInCinemaRoom = new ArrayList<>();
    private List<FilmSchedule> filmSchedules = new ArrayList<>();

    public CinemaRoom(String name, String description) {
        this.name = name;
        this.description = description;
        this.seatsInCinemaRoom = seatProducer();
        this.filmSchedules = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }



    public List<Seat> getSeatsInCinemaRoom() {
        return seatsInCinemaRoom;
    }
    public Seat getSelectedSeat(String seatName) {
        return seatsInCinemaRoom.stream().filter(seat -> seat.getName().equals(seatName)).findFirst().orElse(null);
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


    public List<FilmSchedule> getFilmSchedules() {
        return filmSchedules;
    }
    public List<FilmSchedule> getFilmScheduleForGivenCinemaRoom() {
        if (checkingIfThisCinemaContainsCinemaRoom(this.name)) {
            return this.getFilmSchedules().stream()
                    .filter(filmSchedule -> filmSchedule.cinemaRoom().getName().equals(this.name))
                    .toList();
        } else {
            throw new RuntimeException(
                    "Cinema room " + this.name + " is not available in this cinema"
            );
        }
    }

}
