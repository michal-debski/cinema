package pl.agh.edu.mwo.analiza;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class Cinema {

    private final String cinemaName;
    private final String cinemaAddress;
    private static final List<Booking> bookings = new ArrayList<>();
    private static final List<CinemaRoom> cinemaRooms = new ArrayList<>();
    private final FilmSchedule filmSchedule = new FilmSchedule();

    public Cinema(String cinemaAddress, String cinemaName) {
        this.cinemaAddress = cinemaAddress;
        this.cinemaName = cinemaName;
    }


    public static List<CinemaRoom> getCinemaRooms() {
        return cinemaRooms;
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


    public static List<Ticket> getListOfTicketsForGivenEmail(String email) {
        System.out.println("All tickets for selected customer with email: " + email);
        List<Ticket> allTickets = bookings.stream()
                .flatMap(booking -> booking.getTicketsForBooking().stream())
                .toList();
        return allTickets.stream().filter(t -> t.getEmail() != null && t.getEmail().equals(email)).toList();
    }

    public static void saveBookingInCinemaStorage(Booking newBooking) {
        bookings.add(newBooking);
    }

    public void displayAllBookings() {
        bookings.forEach(booking ->
                System.out.println("Booking Number: " + booking.getBookingNumber() + ", is Paid: " + booking.isPaid())
        );
    }

    public CinemaRoom createNewCinemaRoom(String name, String description) {
        CinemaRoom cinemaRoom = new CinemaRoom(name, description);
        cinemaRooms.add(cinemaRoom);
        return cinemaRoom;
    }


    public static void removeBookingIfTicketsAreEmpty(Booking booking) {
        if (booking.getTicketsForBooking().isEmpty()) {
            bookings.remove(booking);
        }
    }

    public void addNewFilmIntoFilmSchedule(
            Film film,
            LocalDateTime startAt,
            BigDecimal priceForAdult,
            BigDecimal priceForChild,
            String cinemaRoomName,
            FilmDetails.ScreeningType screeningType
    ) {

        LocalDateTime endAt = startAt.plusMinutes(film.duration().toMinutes());
        boolean isOverlapping = filmSchedule.getSchedule().stream()
                .filter(filmDetails -> filmDetails.getCinemaRoom().equals(cinemaRoomName))
                .anyMatch(existingFilm -> {
                    LocalDateTime existingStart = existingFilm.getStartTime();
                    LocalDateTime existingEnd = existingStart.plusMinutes(existingFilm.getFilm().duration().toMinutes());

                    return (startAt.isBefore(existingEnd) && endAt.isAfter(existingStart));
                });

        if (!isOverlapping) {
            FilmDetails filmDetails = new FilmDetails(
                    film,
                    startAt,
                    priceForAdult,
                    priceForChild,
                    cinemaRooms.stream()
                            .filter(a -> a.getName().equals(cinemaRoomName))
                            .findFirst()
                            .orElseThrow(() -> new NoCinemaRoomFoundException("Cinema room not found"))
                            .getName(),
                    screeningType
            );
            filmSchedule.addNewFilmIntoSchedule(filmDetails);
        } else {
            log.error("You cannot add film: {} in the schedule for selected Cinema room: {} and date: {}. Please add film: {} in another date",
                    film.title(),
                    cinemaRoomName,
                    startAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    film.title());
        }

    }

    public void printFilmScheduleForNextWeek() {
        List<FilmDetails> filmDetailsForNextWeek = getFilmDetailsForNextWeek();
        if (filmDetailsForNextWeek.isEmpty()) {
            System.out.println("Brak zaplanowanych filmów na kolejny tydzień.");
            return;
        }
        List<FilmDetails> sortedFilms = filmDetailsForNextWeek.stream()
                .sorted(Comparator.comparing(FilmDetails::getStartTime))
                .toList();

        String tableFormat = "| %-10s | %-5s | %-18s | %-5s | %-9s | %-9s | %-10s |\n";
        System.out.println("Film schedule for next week:");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf(tableFormat, "Date", "Time", "Title", "Type", "Adult PLN", "Child PLN", "Room");
        System.out.println("----------------------------------------------------------------------------------------");

        for (FilmDetails film : sortedFilms) {
            System.out.printf(
                    tableFormat,
                    film.getStartTime().toLocalDate(),
                    film.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    film.getFilm().title(),
                    film.getScreeningType().getName(),
                    film.getPriceForAdult(),
                    film.getPriceForChildren(),
                    film.getCinemaRoom()
            );
        }

        System.out.println("----------------------------------------------------------------------------------------");


    }


    private List<FilmDetails> getFilmDetailsForNextWeek() {
        List<FilmDetails> filmDetails = filmSchedule.getSchedule();

        return filmDetails.stream()
                .filter(
                        film -> {
                            LocalDate filmDate = film.getStartTime().toLocalDate();
                            return !filmDate.isBefore(LocalDate.now())
                                    && !filmDate.isAfter(LocalDate.now().plusDays(7));
                        })
                .toList();
    }
}
