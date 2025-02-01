package pl.agh.edu.mwo;

import pl.agh.edu.mwo.analiza.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static pl.agh.edu.mwo.analiza.Cinema.getListOfTicketsForGivenEmail;

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

        cinema.addNewFilmIntoFilmSchedule(film1, LocalDateTime.of(2025, 2, 6, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_2D);
        cinema.addNewFilmIntoFilmSchedule(film2, LocalDateTime.of(2025, 2, 1, 2, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_3D);
        cinema.addNewFilmIntoFilmSchedule(film3, LocalDateTime.of(2025, 2, 2, 3, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom1.getName(), FilmDetails.ScreeningType.SCREENING_2D);
        cinema.addNewFilmIntoFilmSchedule(film4, LocalDateTime.of(2025, 2, 3, 4, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film5, LocalDateTime.of(2025, 2, 1, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film6, LocalDateTime.of(2025, 2, 2, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film7, LocalDateTime.of(2025, 2, 3, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film8, LocalDateTime.of(2025, 2, 20, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);
        cinema.addNewFilmIntoFilmSchedule(film9, LocalDateTime.of(2025, 2, 19, 1, 20), BigDecimal.valueOf(11), BigDecimal.valueOf(17), cinemaRoom.getName(), FilmDetails.ScreeningType.SCREENING_VIP);


        System.out.println(cinema.getFilmSchedule().getFilmDetailsForGivenDate(1, 2, 2025));
        System.out.println("----------------------------------");
        cinema.printFilmScheduleForNextWeek();
        customer.reserveSeatsForFilm(
                cinema.getFilmSchedule(),
                film1,
                LocalDateTime.of(2025, 2, 6, 1, 20),
                List.of(cinemaRoom.getSelectedSeat("A1")),
                List.of(cinemaRoom.getSelectedSeat("A2"), cinemaRoom.getSelectedSeat("A4"))

        );
        customer1.buyTickets(
                cinema.getFilmSchedule(),
                film6,
                LocalDateTime.of(2025, 2, 2, 1, 20),
                List.of(cinemaRoom.getSelectedSeat("A5"), cinemaRoom.getSelectedSeat("A6")),
                List.of(cinemaRoom.getSelectedSeat("A7"), cinemaRoom.getSelectedSeat("A8"))
        );

        System.out.println(customer.getMyTickets());
        cinema.displayAllBookings();
        System.out.println(getListOfTicketsForGivenEmail(customer.getEmail()));
    }
}

