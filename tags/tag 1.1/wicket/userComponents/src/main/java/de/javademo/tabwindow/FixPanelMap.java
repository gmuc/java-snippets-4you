package de.javademo.tabwindow;

import org.apache.wicket.markup.html.panel.Panel;

import java.util.HashMap;

/**
 * Created by mucha on 06.10.14.
 */
public class FixPanelMap extends AbstractReloadablePanelMap {

    protected FixPanelMap(HashMap<String, Panel> panelMap) {
        super(panelMap);
    }

    @Override
    public void reload() {

    }
}
