package pl.agh.edu.mwo.analiza;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Cinema {

    private final String cinemaName;
    private final String cinemaAddress;
    private static final List<Booking> bookings = new ArrayList<>();
    private static final List<CinemaRoom> cinemaRooms = new ArrayList<>();
    private FilmSchedule filmSchedule = new FilmSchedule();

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
        boolean isOverlapping = filmSchedule.getFilmDetails().stream()
                .filter(filmDetails -> filmDetails.getCinemaRoom().equals(cinemaRoomName)) // tylko w danej sali
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
            log.error("You cannot add film: " + film.title() + " in the schedule for selected Cinema room: {} and date: {}. " +
                            "Please add film: " + film.title() + " in another date",
                    cinemaRoomName,
                    startAt);
        }

    }

    public void printFilmScheduleForNextWeek() {

        List<FilmDetails> filmDetailsForNextWeek = getFilmDetailsForNextWeek();
        System.out.println("Film schedule for next week: \n----------------------------------");
        Set<LocalDate> localDates = filmDetailsForNextWeek.stream()
                .map(t -> t.getStartTime().toLocalDate())
                .collect(Collectors.toSet());

        for (LocalDate localDate : localDates) {
            System.out.println(localDate);
            List<FilmDetails> list = new ArrayList<>(filmDetailsForNextWeek.stream()
                    .filter(t -> t.getStartTime().toLocalDate().equals(localDate))
                    .toList());
            list.forEach(System.out::println);
            System.out.println("\n");
        }

    }


    private List<FilmDetails> getFilmDetailsForNextWeek() {
        List<FilmDetails> filmDetails = filmSchedule.getFilmDetails();

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
