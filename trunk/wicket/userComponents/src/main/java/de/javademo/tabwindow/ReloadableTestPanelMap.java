package de.javademo.tabwindow;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import java.util.HashMap;

/**
 * Created by mucha on 06.10.14.
 */
public class ReloadableTestPanelMap extends AbstractReloadablePanelMap {

    protected ReloadableTestPanelMap(HashMap<String, Panel> panelMap, String reloadMode) {
        super(panelMap, reloadMode);
    }

    @Override
    public void reload() {

        for (String key: panelMap.keySet()){
            TabPanel nodeEntry = (TabPanel) panelMap.get(key);

            PropertyModel<String> labelPanel = nodeEntry.getLabelPanel();

            String oldLabelText = labelPanel.getObject();

            labelPanel.setObject(oldLabelText + "+");
        }
    }
}
