package de.javademo.tabwindow;

import java.util.ArrayList;

/**
 * Created by mucha on 20.10.14.
 */
public class LevelTabOutOfRageException extends Exception{

    LevelTabOutOfRageException( int level, int tab, int levelSize ){

        super("Level: '" + level + "' Es wurde das nicht zulässige LevelTab '"
                + tab + "' ausgewählt! Zulässiger Bereich: 0 - " + --levelSize);
    }
}
