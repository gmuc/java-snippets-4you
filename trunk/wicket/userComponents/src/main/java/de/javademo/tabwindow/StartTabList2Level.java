package de.javademo.tabwindow;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.HashMap;

public class StartTabList2Level extends WebPage {

    private HashMap<String,Panel> panelMap;

    public StartTabList2Level(final PageParameters parameters) {

/*
        List<String> choices = new ArrayList<String>();
        choices.add("panel1");
        choices.add("panel2");

        final RadioChoice<String> rChoice = new RadioChoice<String>("rChoice");
        rChoice.setModel(new Model<String>("panel1"));
        rChoice.setChoices(choices);

        rChoice.add(new AjaxFormChoiceComponentUpdatingBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                Panel newPanel = null;

                if ("panel1".equals(rChoice.getModelObject())){
                    Entry entry = new Entry("Panel1");
                    Model<Entry> entryIModel = new Model<Entry>(entry);

                    newPanel = new TestPanel("replacedPanel", entryIModel);
                }else{
                    Entry entry = new Entry("Panel2");
                    Model<Entry> entryIModel = new Model<Entry>(entry);

                    newPanel = new TestPanel("replacedPanel", entryIModel);
                }
                newPanel.setOutputMarkupId(true);
                replacedPanel.replaceWith(newPanel);
                target.add(newPanel);
                replacedPanel = newPanel;
            }
        });
*/
        panelMap = new HashMap<String, Panel>();

        ArrayList<String> tab1labelList = new ArrayList<String>();
        tab1labelList.add("A");
        tab1labelList.add("B");
        tab1labelList.add("C");
        tab1labelList.add("D");

        ArrayList<String> tab2labelList = new ArrayList<String>();
        tab2labelList.add("1");
        tab2labelList.add("2");
        tab2labelList.add("3");

        generatePanels(tab1labelList.size(), tab2labelList.size(),"replacedPanel");

        WebMarkupContainer tabListWrapper = new WebMarkupContainer("tabListWrapper");
        tabListWrapper.setOutputMarkupId(true);

        ReloadableTestPanelMap reloadableTestPanelMap = new ReloadableTestPanelMap(panelMap, "reloadOnTab2Change");

        TabList2Level tabList = new TabList2Level(
                "listNavigation",
                0,0,
                reloadableTestPanelMap,
                tab1labelList, tab2labelList,
                "tabListWrapper","replacedPanel");

        tabList.setOutputMarkupId(true);

        // TabList2Level als Komponente mit Steuerfunktionialität im Wicket-Komponenten-Baum einfügen
        tabListWrapper.add(tabList);

        // TabPanel als Komponente mit dem Inhalt im Wicket-Komponenten-Baum einfügen
        // als Referenzierungscode wird hier '0-0' verwendet
        tabListWrapper.add(panelMap.get("0-0"));

        add(tabListWrapper);
    }

    private void generatePanels(Integer tab1Size, Integer tab2Size, String panelName){

        for(int i=0; tab1Size >= i; i++){
            for(int j=0; tab2Size >= j; j++){

                String code = Integer.toString(i) + "-" + Integer.toString(j);
                Entry entry = new Entry("Panel " + code);

                Model<Entry> entryIModel = new Model<Entry>(entry);

                TabPanel replacedPanel = new TabPanel(panelName, entryIModel);
                replacedPanel.setOutputMarkupId(true);

                panelMap.put(code, replacedPanel);
            }
        }
    }
}