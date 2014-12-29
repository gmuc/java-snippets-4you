package de.javademo.tabwindow;

/**
 * Created by mucha on 02.04.14.
 */
import java.io.Serializable;

public class Entry implements Serializable {
    public String getTitle() {
        return title;
    }

    public Entry(final String title) {
        super();
        this.title = title;
    }

    private final String title;
}