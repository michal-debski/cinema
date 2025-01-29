package pl.agh.edu.mwo.analiza;

import java.time.Duration;

public record Film (

        String title,
        String filmType,
        String description,
        Duration duration
) {
    @Override
    public String toString() {
        return  title + '\'' +
                ", filmType: '" + filmType + '\'' +
                ", description: '" + description + '\'' +
                ", duration: " + duration.getSeconds()/3600 +" h";
    }
}
