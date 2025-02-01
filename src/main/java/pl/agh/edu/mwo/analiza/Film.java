package pl.agh.edu.mwo.analiza;

import java.time.Duration;

public record Film (

        String title,
        FilmType filmType,
        String description,
        Duration duration
) {
    @Override
    public String toString() {
        return  title + '\'' +
                ", filmType: '" + filmType.name + '\'' +
                ", duration: " + duration.getSeconds()/3600 +" h";
    }

    public enum FilmType {
        COMEDY("Comedy"),
        DRAMA("Drama"),
        ROMANCE("Romance"),
        HISTORY("History");

        final String name;
        FilmType(String name) {
            this.name = name;
        }

    }
}
