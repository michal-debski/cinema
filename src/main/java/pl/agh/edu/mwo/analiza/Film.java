package pl.agh.edu.mwo.analiza;

import java.time.Duration;

public record Film (

        long id,
        String title,
        String filmType,
        String description,
        Duration duration
) {

}
