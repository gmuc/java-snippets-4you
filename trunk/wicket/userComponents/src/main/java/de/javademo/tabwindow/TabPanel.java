package de.javademo.tabwindow;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class TabPanel extends Panel {
    public PropertyModel<String> getLabelPanel() {
        return labelPanel;
    }

    public void setLabelPanel(PropertyModel<String> labelPanel) {
        this.labelPanel = labelPanel;
    }

    private PropertyModel<String> labelPanel;

    public TabPanel(String id, IModel<Entry> panelData) {
        super(id);

        labelPanel = new PropertyModel<String>(panelData, "title");
        this.add(new Label("myText", labelPanel));
    }


}