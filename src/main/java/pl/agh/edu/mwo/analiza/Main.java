package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Film film1 = new Film(1, "AAA", "VA", "fajny film1", Duration.ofMinutes(60));
        Film film2 = new Film(2, "BBB", "VB", "fajny film2", Duration.ofMinutes(70));
        Film film3 = new Film(3, "CCC", "VC", "fajny film3", Duration.ofMinutes(80));

        CinemaRoom cinemaRoom = new CinemaRoom(1, "Vinci", "Duża", 100);

        FilmDetails filmDetails1 = new FilmDetails(1,film1,LocalDateTime.of(2026,10,1, 1,20), BigDecimal.valueOf(10), BigDecimal.valueOf(5),cinemaRoom);
        FilmDetails filmDetails2 = new FilmDetails(2,film2,LocalDateTime.of(2026,10,1, 1,20), BigDecimal.valueOf(10), BigDecimal.valueOf(5),cinemaRoom);
        FilmDetails filmDetails3 = new FilmDetails(3,film3,LocalDateTime.of(2026,10,2, 1,20), BigDecimal.valueOf(10), BigDecimal.valueOf(5),cinemaRoom);

        FilmSchedule filmSchedule = new FilmSchedule(1, "rozkład", List.of(filmDetails1,filmDetails2, filmDetails3));
        filmSchedule.getFilmDetailsForGivenDay(2).forEach(System.out::println);

    }
}