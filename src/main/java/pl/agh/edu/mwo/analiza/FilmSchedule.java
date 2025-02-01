package pl.agh.edu.mwo.analiza;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilmSchedule {

    private List<FilmDetails> filmDetails = new ArrayList<>();


    public void addNewFilmIntoSchedule(FilmDetails film) {
        this.filmDetails.add(film);
    }

    public FilmDetails getFilmDetailsByFilmTitleInGivenDate(String title, LocalDateTime startDate) {
        return filmDetails.stream()
                .filter(film -> film.getFilm().title().equals(title) && film.getStartTime().equals(startDate))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("Film not found with title: " + title + " in given date " + startDate));
    }

    public List<FilmDetails> getFilmDetails() {
        return filmDetails;
    }
}
