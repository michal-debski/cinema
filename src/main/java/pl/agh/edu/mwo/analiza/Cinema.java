package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cinema {

    private final String cinemaName;
    private final String cinemaAddress;
    private static final List<Booking> bookings = new ArrayList<>();
    private static final List<CinemaRoom> cinemaRooms = new ArrayList<>();
    private final FilmSchedule filmSchedule;

    public Cinema(String cinemaAddress, String cinemaName) {
        this.cinemaAddress = cinemaAddress;
        this.cinemaName = cinemaName;
        this.filmSchedule = new FilmSchedule();
    }


    public static List<CinemaRoom> getCinemaRooms() {
        return cinemaRooms;
    }

    public static List<Ticket> getListOfTicketsForGivenEmail(String email) {
        List<Ticket> collect = bookings.stream()
                .flatMap(booking -> booking.getTicketsForBooking().stream())
                .toList();
        return collect.stream().filter(t -> t.getEmail() != null && t.getEmail().equals(email)).toList();
    }

    public static void saveBookingInCinemaStorage(Booking newBooking) {
        bookings.add(newBooking);
    }

    public void displayAllBookings() {
        bookings.forEach(booking ->
                System.out.println("Booking Number: " + booking.getBookingNumber())
        );
    }

    public CinemaRoom createNewCinemaRoom(String name, String description) {
        CinemaRoom cinemaRoom = new CinemaRoom(name, description);
        cinemaRooms.add(cinemaRoom);
        return cinemaRoom;
    }

    public static boolean checkingIfThisCinemaContainsCinemaRoom(String name) {
        return getCinemaRooms().stream()
                .anyMatch(cinemaRoom1 -> cinemaRoom1.getName().equals(name));
    }

    public static void removeBookingIfTicketsAreEmpty(Booking booking) {
        if (booking.getTicketsForBooking().isEmpty()) {
            bookings.remove(booking);
        }
    }

    public void addNewFilmIntoFilmSchedule(Film film, LocalDateTime startAt, BigDecimal priceForAdult, BigDecimal priceForChild, String cinemaRoomName, FilmDetails.ScreeningType screeningType) {
        List<CinemaRoom> cinemaRoomList = getCinemaRooms().stream()
                .filter(cinemaRoom -> cinemaRoom.getName().equals(cinemaRoomName))
                .toList();
        if (!cinemaRoomList.isEmpty()) {
            FilmDetails filmDetails = new FilmDetails(
                    film,
                    startAt,
                    priceForAdult,
                    priceForChild,
                    cinemaRooms.stream()
                            .filter(a -> a.getName().equals(cinemaRoomName))
                            .toList()
                            .getFirst()
                            .getName(),
                    screeningType
            );
            filmSchedule.addNewFilmIntoSchedule(filmDetails, cinemaRoomList.getFirst());

        }
    }

    public List<FilmDetails> getFilmDetailsForNextWeek() {
        List<FilmDetails> filmDetails = filmSchedule.getFilmDetails();
        return filmDetails.stream()
                .filter(
                        film -> film.getStartTime().toLocalDate().isBefore(LocalDate.now().plusWeeks(1))
                ).toList();
    }

    public FilmSchedule getFilmSchedule() {
        return filmSchedule;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }
}
