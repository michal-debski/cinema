package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static pl.agh.edu.mwo.analiza.Booking.processBooking;

public class Main {
    public static void main(String[] args) {

        Cinema cinema = new Cinema("ul. Warszawska 1", "Kraków");
        Cinema cinema1 = new Cinema("ul. Krakowska 1", "Poznań");
        Customer customer = new Customer("Adam", "Kowalski", "jkowal@gmail.com","+48 000 111 000", 20,true);
        Customer customer1 = new Customer("Adam", "Kowalski", "jkowal@gmail.com","+48 000 111 000", 21,true);
        Film film1 = new Film( "AAA", "VA", "fajny film1", Duration.ofMinutes(60));
        Film film2 = new Film( "BBB", "VB", "fajny film2", Duration.ofMinutes(70));
//        Film film3 = new Film( "CCC", "VC", "fajny film3", Duration.ofMinutes(80));
//
//
        CinemaRoom cinemaRoom = cinema.createNewCinemaRoom("Vinci", "Duża");
        CinemaRoom cinemaRoom1 = cinema.createNewCinemaRoom("Zamojski", "Duża");

          FilmDetails filmDetails1 = new FilmDetails(film1, LocalDateTime.of(2026,10,1, 1,20), BigDecimal.valueOf(11), BigDecimal.valueOf(17));
        FilmDetails filmDetails2 = new FilmDetails(film2,LocalDateTime.of(2026,10,1, 1,20), BigDecimal.valueOf(13), BigDecimal.valueOf(16));
//        FilmDetails filmDetails3 = new FilmDetails(3,film3,LocalDateTime.of(2026,10,2, 1,20), BigDecimal.valueOf(60), BigDecimal.valueOf(40));
//
        FilmSchedule filmSchedule = new FilmSchedule("rozkład", List.of(filmDetails1), cinemaRoom1);
//        filmSchedule.getFilmDetailsForGivenDay(2).forEach(System.out::println);
//

//        Ticket ticket = new Ticket(List.of(cinemaRoom.getSelectedSeat("A1"),cinemaRoom.getSelectedSeat("A3")),List.of(cinemaRoom.getSelectedSeat("A2"),cinemaRoom.getSelectedSeat("A4")),filmDetails1, customer);
//        System.out.println(customer.getAvailableFilmScheduleForGivenTime(filmSchedule, 1));
//        System.out.println(cinemaRoom.getSeatsInCinemaRoom().size());
//        System.out.println(ticket);
//        filmSchedule.checkAvailableSeats(1, "AAA", List.of(cinemaRoom.getSelectedSeat("A1"),cinemaRoom.getSelectedSeat("A3")));
//
        //TODO IMPLEMENTATION OF VALIDATION WHETHER FILM IS AVAILABLE IN SELECTED DATE IN CINEMA
//

        processBooking(List.of(cinemaRoom.getSelectedSeat("A1"),cinemaRoom.getSelectedSeat("A3")),List.of(cinemaRoom.getSelectedSeat("A2"),cinemaRoom.getSelectedSeat("A4")),filmDetails1, customer, true);
        processBooking(List.of(cinemaRoom1.getSelectedSeat("A1"),cinemaRoom1.getSelectedSeat("A6")),List.of(cinemaRoom1.getSelectedSeat("A7"),cinemaRoom1.getSelectedSeat("A4")),filmDetails2, customer1, true);
//
    }


}