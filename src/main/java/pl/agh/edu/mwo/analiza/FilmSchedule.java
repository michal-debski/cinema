package pl.agh.edu.mwo.analiza;

import java.util.List;

public record FilmSchedule(
        String description,
        List<FilmDetails> filmDetails,
        CinemaRoom cinemaRoom
) {
    public FilmSchedule(String description, List<FilmDetails> filmDetails, CinemaRoom cinemaRoom) {
        this.description = description;
        this.filmDetails = filmDetails;
        this.cinemaRoom = cinemaRoom;
        cinemaRoom.getFilmSchedules().add(this);
    }

    public List<FilmDetails> getFilmDetailsForGivenDay(int day) {
        return filmDetails.stream()
                .filter(
                        film -> film.getStartTime().getDayOfMonth() == day
                ).toList();
    }

    public List<FilmDetails> getFilmScreeningDaysFromFilmDetailsByFilmName(String title) {
        return filmDetails.stream().filter(film -> film.getFilm().title().equals(title)).toList();
    }


    public boolean checkAvailableSeats(int day, String filmTitle, List<Seat> seats) {
        FilmDetails filmDetails1 = filmDetails.stream()
                .filter(
                        f -> f.getFilm().title().equals(filmTitle)
                                && f.getStartTime().getDayOfMonth() == day
                )
                .findFirst()
                .orElseThrow();
        // TODO Implement validation whether selected seats are available
        return true;
    }




}
