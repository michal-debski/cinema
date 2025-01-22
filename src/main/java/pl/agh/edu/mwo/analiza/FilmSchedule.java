package pl.agh.edu.mwo.analiza;

import java.util.List;

public record FilmSchedule(
        long id,
        String description,
        List<FilmDetails> filmDetails
) {

    public List<FilmDetails> getFilmDetailsForGivenDay(int day) {
        return filmDetails.stream()
                .filter(
                        film -> film.getStartTime().getDayOfMonth() == day
                ).toList();
    }


    //todo dodać jeszcze czas jako parametr, bo musi nam też odpowiadać godzina :)

    public static boolean checkAvailableSeats(FilmSchedule filmSchedule, Film film, int seats) {
        FilmDetails filmDetails1 = filmSchedule.filmDetails.stream()
                .filter(
                        f -> f.getFilm().equals(film)
                )
                .findFirst()
                .orElseThrow();
        return filmDetails1.getCinemaRoom().getNumberOfAvailableSeats() >= seats;
    }
}
