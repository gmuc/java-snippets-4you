package de.javademo.tabwindow;

import org.apache.wicket.markup.html.panel.Panel;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by mucha on 26.09.14.
 */
public abstract class AbstractReloadablePanelMap implements Serializable{

    protected HashMap<String, Panel> panelMap;

    private String reloadmode = "none";

    protected AbstractReloadablePanelMap(HashMap<String, Panel> panelMap, String reloadmode) {
        this(panelMap);
        this.reloadmode = reloadmode;
    }

    protected AbstractReloadablePanelMap(HashMap<String, Panel> panelMap) {
        this.panelMap = panelMap;
    }


    public HashMap<String, Panel> getPanelMap(){
        return panelMap;
    }

    public String getReloadMode(){
        return reloadmode;
    }

    public abstract void reload();
}
