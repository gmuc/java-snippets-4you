package de.javademo.tabwindow;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class TabPanel2 extends Panel {
    public TabPanel2(String id, final IModel<Entry> panelData) {
        super(id);

        this.add(new Label("myText", new PropertyModel<String>(panelData, "title")));
    }
}