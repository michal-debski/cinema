package pl.agh.edu.mwo.analiza;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilmSchedule {

    private final List<FilmDetails> schedule = new ArrayList<>();

    public void addNewFilmIntoSchedule(FilmDetails film) {
        this.schedule.add(film);
    }

    public FilmDetails getFilmDetailsByFilmTitleInGivenDate(String title, LocalDateTime startDate) {
        return schedule.stream()
                .filter(film -> film.getFilm().title().equals(title) && film.getStartTime().equals(startDate))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("Film not found with title: " + title + " in given date " + startDate));
    }

    public List<FilmDetails> getSchedule() {
        return schedule;
    }
}
