package pl.agh.edu.mwo;

import pl.agh.edu.mwo.analiza.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static pl.agh.edu.mwo.analiza.Cinema.getListOfTicketsForGivenEmail;
import static pl.agh.edu.mwo.analiza.Customer.getTicketsForCustomer;

public class Main {
    public static void main(String[] args) {

        Cinema cinema = new Cinema("ul. Warszawska 1", "Kraków");
        Cinema cinema1 = new Cinema("ul. Krakowska 1", "Poznań");

        CinemaRoom cinemaRoom = cinema.createNewCinemaRoom("Vinci", "Duża");
        CinemaRoom cinemaRoom1 = cinema.createNewCinemaRoom("Zamojski", "Duża");

        Customer customer = new Customer("Jan", "Kowalski", "jkowal@gmail.com", "+48 000 111 000", 20, true);
        Customer customer1 = new Customer("Adam", "Kowalski", "akowal@gmail.com", "+48 000 111 000", 21, false);

        Film film1 = new Film("AAA", "VA", "fajny film1", Duration.ofMinutes(60));
        Film film2 = new Film("BBB", "VB", "fajny film2", Duration.ofMinutes(70));
        Film film3 = new Film("CCC", "VC", "fajny film3", Duration.ofMinutes(80));

        cinema.addNewFilmIntoFilmSchedule(film1, LocalDateTime.of(2025, 1, 27, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_2D);
        cinema.addNewFilmIntoFilmSchedule(film2, LocalDateTime.of(2025, 1, 28, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_3D);
        cinema.addNewFilmIntoFilmSchedule(film2, LocalDateTime.of(2025, 1, 29, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom1.getName(), FilmDetails.ScreeningType.SCREENING_2D);
        cinema.addNewFilmIntoFilmSchedule(film3, LocalDateTime.of(2025, 1, 30, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);




        System.out.println(cinema.getFilmSchedule().getFilmDetailsForGivenDate(27, 1,2025));
        System.out.println("--------");
        System.out.println(cinema.getFilmDetailsForNextWeek());
        customer.reserveSeatsForFilm(
                cinema.getFilmSchedule(),
                film1,
                LocalDateTime.of(2025, 1, 27, 1, 20),
                List.of(cinemaRoom.getSelectedSeat("A1"), cinemaRoom.getSelectedSeat("A3")),
                List.of(cinemaRoom.getSelectedSeat("A2"), cinemaRoom.getSelectedSeat("A4")),
                Booking.PaymentType.ALREADY_PAID_VIA_NET
        );
        customer1.reserveSeatsForFilm(
                cinema.getFilmSchedule(),
                film1,
                LocalDateTime.of(2025, 1, 27, 1, 20),
                List.of(cinemaRoom.getSelectedSeat("A5"), cinemaRoom.getSelectedSeat("A6")),
                List.of(cinemaRoom.getSelectedSeat("A7"), cinemaRoom.getSelectedSeat("A8")),
                Booking.PaymentType.ALREADY_PAID_VIA_NET
        );
        System.out.println(getTicketsForCustomer(customer));
        System.out.println(getListOfTicketsForGivenEmail(customer.getEmail()));
    }


}