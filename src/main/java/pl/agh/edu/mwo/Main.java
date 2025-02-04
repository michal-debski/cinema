package pl.agh.edu.mwo;

import pl.agh.edu.mwo.analiza.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Cinema cinema = new Cinema("ul. Warszawska 1", "Kraków");
        Cinema cinema1 = new Cinema("ul. Krakowska 1", "Poznań");

        CinemaRoom cinemaRoom = cinema.createNewCinemaRoom("Vinci", "Duża");
        CinemaRoom cinemaRoom1 = cinema.createNewCinemaRoom("Zamojski", "Duża");

        Customer customer = new Customer("Jan", "Kowalski", "jkowal@gmail.com", "+48 000 111 000", 20, true);
        Customer customer1 = new Customer("Adam", "Kowalski", "akowal@gmail.com", "+48 000 111 000", 21, false);

        Film film1 = new Film("Ogniem i mieczem", Film.FilmType.HISTORY, "Adaptacja powieści Henryka Sienkiewicza. XVII wiek, na Kresach Wschodnich zbliża się wojna pomiędzy Rzeczpospolitą a Kozakami", Duration.ofMinutes(180));
        Film film2 = new Film("Potop", Film.FilmType.HISTORY, "Polski film historyczny z 1974 roku w reżyserii Jerzego Hoffmana, będący trzecią ekranizacją powieści Henryka Sienkiewicza pod tym samym tytułem.", Duration.ofMinutes(160));
        Film film3 = new Film("Pan Wołodyjowski", Film.FilmType.HISTORY, "Polski barwny film fabularny Jerzego Hoffmana z 1969 roku na podstawie powieści Henryka Sienkiewicza pod tym samym tytułem.", Duration.ofMinutes(200));
        Film film4 = new Film("Listy do M.", Film.FilmType.COMEDY, "Listy do M. to kinowy hit z 2011 roku z reżyserii Mitja Okorna.", Duration.ofMinutes(80));
        Film film5 = new Film("Listy do M. 5", Film.FilmType.COMEDY, "polska komedia romantyczna z 2022 roku, której reżyserem jest Łukasz Jaworski", Duration.ofMinutes(96));
        Film film6 = new Film("Listy do M. 6", Film.FilmType.COMEDY, "Listy do M. Pożegnania i powroty – polska komedia romantyczna z 2024 roku, której reżyserem jest Łukasz Jaworski. ", Duration.ofMinutes(100));
        Film film7 = new Film("Gladiator", Film.FilmType.DRAMA, "Film dramatyczny w starożytnym Rzymie", Duration.ofMinutes(100));
        Film film8 = new Film("La La Land", Film.FilmType.ROMANCE, "Film romantyczny", Duration.ofMinutes(100));
        Film film9 = new Film("Gladiator II", Film.FilmType.DRAMA, "Film dramatyczny", Duration.ofMinutes(100));

        cinema.addNewFilmIntoFilmSchedule(film1, LocalDateTime.of(2025, 2, 6, 15, 0), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_2D);
        cinema.addNewFilmIntoFilmSchedule(film2, LocalDateTime.of(2025, 2, 7, 16, 0), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_3D);
        cinema.addNewFilmIntoFilmSchedule(film3, LocalDateTime.of(2025, 2, 21, 18, 0), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom1.getName(), FilmDetails.ScreeningType.SCREENING_2D);
        cinema.addNewFilmIntoFilmSchedule(film4, LocalDateTime.of(2025, 2, 5, 12, 0), BigDecimal.valueOf(44), BigDecimal.valueOf(28), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film5, LocalDateTime.of(2025, 2, 10, 11, 0), BigDecimal.valueOf(44), BigDecimal.valueOf(28), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film6, LocalDateTime.of(2025, 2, 11, 9, 0), BigDecimal.valueOf(44), BigDecimal.valueOf(28), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film7, LocalDateTime.of(2025, 2, 11, 19, 0), BigDecimal.valueOf(44), BigDecimal.valueOf(28), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film8, LocalDateTime.of(2025, 2, 14, 21, 0), BigDecimal.valueOf(44), BigDecimal.valueOf(28), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film9, LocalDateTime.of(2025, 2, 19, 20, 0), BigDecimal.valueOf(44), BigDecimal.valueOf(28), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);


        System.out.println("----------------------------------");
        //Cinema should show the film schedule for next week
        cinema.printFilmScheduleForNextWeek();
        System.out.println("----------------------------------");
        //Customer with account should be able to book and buy the tickets
        customer.reserveSeatsForFilm(
                cinema,
                film9,
                LocalDateTime.of(2025, 2, 19, 20, 0),
                List.of(cinemaRoom.getSelectedSeat("A1")),
                List.of(cinemaRoom.getSelectedSeat("A2"), cinemaRoom.getSelectedSeat("A4"))

        );
        //Customer without account should be able to book and buy tickets
        customer1.buyTickets(
                cinema,
                film8,
                LocalDateTime.of(2025, 2, 14, 21, 0),
                List.of(cinemaRoom.getSelectedSeat("A5"), cinemaRoom.getSelectedSeat("A6")),
                List.of(cinemaRoom.getSelectedSeat("A7"), cinemaRoom.getSelectedSeat("A8"))
        );

        System.out.println("----------------------------------");

        //Cinema should see the all bookings in its storage
        cinema.displayAllBookings();
        System.out.println("----------------------------------");
        //Customer with account should see his/her tickets on the list
        System.out.println(customer.checkMyTicketsInGivenCinema(cinema));
        System.out.println("----------------------------------");
        //Cinema should see all tickets for given customer
        System.out.println(cinema.getListOfTicketsForGivenEmail(customer.getEmail()));

        //Customer without account cannot see his/her tickets, so the list with tickets should be
        System.out.println("----------------------------------");
        System.out.println(customer1.checkMyTicketsInGivenCinema(cinema));
    }
}

