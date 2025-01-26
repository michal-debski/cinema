package pl.agh.edu.mwo.analiza;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilmSchedule{

    private final List<FilmDetails> filmDetails;

    public FilmSchedule() {
        this.filmDetails = new ArrayList<>();
    }


    public void addNewFilmIntoSchedule(FilmDetails film, CinemaRoom cinemaRoom) {
        filmDetails.add(film);
    }

    public List<FilmDetails> getFilmDetailsForGivenDay(int day) {
        return filmDetails.stream()
                .filter(
                        film -> film.getStartTime().getDayOfMonth() == day
                ).toList();
    }


    public FilmDetails getFilmDetailsByFilmTitleInGivenDate(String title, LocalDateTime startDate) {
        return filmDetails.stream()
                .filter(film -> film.getFilm().title().equals(title) && film.getStartTime().equals(startDate))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("Film not found with title: " + title + " in given date " + startDate));
    }




    public void printFilmSchedule(int day) {
        List<FilmDetails> list = this.filmDetails.stream().filter(filmDetails1 -> filmDetails1.getStartTime().getDayOfMonth() == day).toList();
        System.out.println("Please find below list with all available films");
        for (FilmDetails details : list) {
            System.out.println(details.toString());
        }
    }

    public List<FilmDetails> getFilmDetails() {
        return filmDetails;
    }
}
