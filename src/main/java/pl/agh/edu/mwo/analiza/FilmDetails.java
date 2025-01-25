package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FilmDetails {

    private final Film film;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final BigDecimal priceForAdult;
    private final BigDecimal priceForChildren;


    public FilmDetails(Film film, LocalDateTime startTime, BigDecimal priceForAdult, BigDecimal priceForChildren) {
        this.film = film;
        this.startTime = startTime;
        this.priceForAdult = priceForAdult;
        this.priceForChildren = priceForChildren;

        this.endTime = startTime.plus(film.duration());
    }


    public Film getFilm() {
        return film;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public BigDecimal getPriceForAdult() {
        return priceForAdult;
    }

    public BigDecimal getPriceForChildren() {
        return priceForChildren;
    }


    @Override
    public String toString() {
        return "FilmDetails{" +
                ", film=" + film +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", priceForAdult=" + priceForAdult +
                ", priceForChildren=" + priceForChildren +
                '}';
    }

    private FilmDetails createNewFilmDetails(
            Film film,
            LocalDateTime startTime,
            BigDecimal priceForAdult,
            BigDecimal priceForChildren
    ) {
        return new FilmDetails(film, startTime, priceForAdult, priceForChildren);
    }
//    public void editFilmTime(FilmSchedule filmSchedule, Film film, LocalDateTime newDateTime){
//        filmSchedule.getSchedule().setDateTime(film, newDateTime);
//    }
    //
//
//    public void removeFilm(FilmSchedule filmSchedule, Long filmId){
//        filmSchedule.removeFilm(id);
//    }
//

}
