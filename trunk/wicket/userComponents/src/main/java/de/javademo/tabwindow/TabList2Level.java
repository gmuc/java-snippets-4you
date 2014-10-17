package de.javademo.tabwindow;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.iterator.ComponentHierarchyIterator;

import java.util.ArrayList;

/**
 * Die Wicketkomponente TabList2Level ermöglicht es, die Darstellung in einem Contentpanel über einen
 * Tab mit 2 Leveln zu steuern.
 *
 * TabList2Level ist entkoppelt vom Panel und ist damit universell als Steuereinheit einsetzbar.
 * Bei Initialisierung über den Konstruktor (s.u.) erhält das Objekt alle notwendigen Steuerdaten
 *
 * Dabei werden die Tabs dynamisch beschriftet. Ebenfalls ist die Anzahl der Tabs auf beiden Levels flexibel.
 * Über die Kombination der auf beiden Leveln ausgewählten Tabs wird bestimmt, welches Panel angezeigt wird.
 * Die Panels werden in einer Map hinterlegt. Dabei müssen die Panels von org.apache.wicket.markup.html.panel.Panel
 * abgeleitet sein, d.h. TabList2Level kann für beliebige Panels dieser Art verwendet werden.
 *
 * Zur Referenzierung des Panels in der Map wird die Position der Tabs verwendet.
 * Dabei beginnt die Zählung bei 0. Beide Postionen werden in folgender Syntax für die Referenzierung benutzt:
 *
 * PosL1-PosL2
 *
 * Beispiel: 0-3
 * entspricht: Tab Level1 Position 0 und Tab Level 2 Position 3
 *
 * Beispielcode für die Einbindung von TabList2Level:
 *
 *  panelMap = new HashMap<String, Panel>();
 *
 *  ArrayList<String> tab1labelList = new ArrayList<String>();
 *  tab1labelList.add("A");
 *  tab1labelList.add("B");
 *
 *  ArrayList<String> tab2labelList = new ArrayList<String>();
 *  tab2labelList.add("1");
 *  tab2labelList.add("2");
 *
 *  generatePanels(tab1labelList.size(), tab2labelList.size(),"replacedPanel");
 *
 *  for(int i=0; tab1labelList.size() >= i; i++){
 *      for(int j=0; tab2labelList.size() >= j; j++){
 *             String code = Integer.toString(i) + "-" + Integer.toString(j);
 *             Entry entry = new Entry("Panel " + code);
 *
 *             Model<Entry> entryIModel = new Model<Entry>(entry);
 *
 *             TabPanel replacedPanel = new TabPanel(panelName, entryIModel);
 *             replacedPanel.setOutputMarkupId(true);
 *             panelMap.put(code, "replacedPanel");
 *      }
 *  }
 *
 *  WebMarkupContainer tabListWrapper = new WebMarkupContainer("tabListWrapper");
 *  tabListWrapper.setOutputMarkupId(true);
 *
 *  TabList2Level tabList = new TabList2Level(
 *                                            "listNavigation",
 *                                             0,0,
 *                                             panelMap,
 *                                             tab1labelList, tab2labelList,
 *                                             "tabListWrapper","replacedPanel");
 *                                             tabList.setOutputMarkupId(true);
 *
 * // TabList2Level als Komponente mit Steuerfunktionialität im Wicket-Komponenten-Baum einfügen
 * tabListWrapper.add(tabList);
 *
 * // TabPanel als Komponente mit dem Inhalt im Wicket-Komponenten-Baum einfügen
 * // als Referenzierungscode wird hier '0-0' verwendet
 * tabListWrapper.add(panelMap.get("0-0"));
 * add(tabListWrapper);
 *
 * @author John Doe
 * @version 1.0
 */

public class TabList2Level extends Panel {

    private AbstractReloadablePanelMap panelMap;

    private int focusL1;     // ID des Tabs der in L1 den Fokus bekommen soll
    private int focusL2;     // ID des Tabs der in L1 den Fokus bekommen soll

    // Name des verwendeten Wrappers + Panel > liegt außerhalb der Komponente zwecks Entkoppelung
    private String wrapperName;
    private String panelName;

    /**
     * Konstruktor von TabList2Level
     *
     * @param selectedL1 ausgewählter Tab in Level1
     * @param selectedL2 ausgewählter Tab in Level2
     * @param newPanelMap Container der die Panels enthält
     * @param level1_label Liste der Labels für Level1
     * @param level2_label Liste der Labels für Level2
     * @param externWrapperName Name des Wrappers der für TabList2Level extern verwendet wird
     * @param externPanelName Name des Panels der für TabList2Level extern verwendet wird
     */

    public TabList2Level(String id,
                         final int selectedL1,
                         final int selectedL2,
                         AbstractReloadablePanelMap newPanelMap,
                         final ArrayList<String> level1_label,
                         final ArrayList<String> level2_label,
                         String externWrapperName,
                         String externPanelName) {

        // super damit die Basisklasse Panel einen validen Zustand zum Beginn der Verarbeitung hat
        super(id);

        wrapperName = externWrapperName;
        panelName = externPanelName;
        panelMap = newPanelMap;
        focusL1 = selectedL1;
        focusL2 = selectedL2;

        this.setOutputMarkupId(true);   // Sichtbarkeit einschalten

        Loop naviLevel1 = new Loop("level1Loop", 4) {

            @Override
            protected void populateItem(LoopItem item) {
                final int currentL1 = item.getIndex();

                // Label mit der definierten Beschriftung erzeugen
                String nameLabel = level1_label.get(currentL1);
                Label navLabel1 = new Label("navLevel1", new Model(nameLabel));

                // ??? soll der aktuelle Tab als ausgewählt erscheinen ???
                // ja: Attribut class auf 'active' setzten. Die Eigenschaften von "class='active' "
                //     müssen natürlich im zugehörigen Stylesheet definiert sein
                if ( currentL1 == focusL1 ) {
                    navLabel1.add( AttributeModifier.append("class", "active") );
                }

                // Componente vom Typ AjaxLink erzeugen, das onClick Verhalten überladen
                AjaxLink link = new AjaxLink("link") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                        focusL1 = currentL1;

                        if( panelMap.getReloadMode().equals("reloadOnTab1Change") ){
                            panelMap.reload();
                        }

                        Component wrapper = setTabPanel();
                        target.add(wrapper);
                    }
                };

                // Nur als Debughilfe !!!
                link.add(new AjaxEventBehavior("onclick"){
                    @Override
                    protected void onEvent(AjaxRequestTarget target) {
                        target.appendJavaScript("alert('clicked Tab L1:" + new Integer( currentL1 ).toString() + " ')");
                    }
                });

                link.add(navLabel1);
                item.add(link);
            }
        };

        final Loop naviLevel2 = new Loop("level2Loop", 3) {
            @Override
            protected void populateItem(final LoopItem item) {

                final int currentL2 = item.getIndex();

                String nameLabel = level2_label.get(currentL2);
                Label navLabel2 = new Label("navLevel2", new Model(nameLabel));

                if ( currentL2 == focusL2 ) {
                    navLabel2.add(AttributeModifier.append("class", "active"));
                }

                AjaxLink link = new AjaxLink("link") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        focusL2 = currentL2;

                        if( panelMap.getReloadMode().equals("reloadOnTab2Change") ){
                            panelMap.reload();
                        }

                        Component wrapper = setTabPanel();
                        target.add(wrapper);
                    }
                };

                // Nur als Debughilfe !!!
                link.add(new AjaxEventBehavior("onclick"){
                    @Override
                    protected void onEvent(AjaxRequestTarget target) {
                        target.appendJavaScript("alert('clicked Tab L2:" + new Integer( currentL2 ).toString() + "' )");
                    }
                });

                link.add(navLabel2);
                item.add(link);
            }
        };

        add(naviLevel1, naviLevel2);
    }

    //  setTabPanel - Panelcomponente suchen und durch ein neues Panel ersetzen

    private Component setTabPanel(){
        String componentKey = Integer.toString(focusL1) + "-" + Integer.toString(focusL2);

        Component wrapper = getComponentById(wrapperName);
        Component replacedPanel = getComponentById(panelName);

        Component panel;
        panel = panelMap.getPanelMap().get(componentKey);
        replacedPanel.replaceWith( panel );

        return wrapper;
    }

    protected Component getComponentById(String id) {
        ComponentHierarchyIterator iterator = getPage().visitChildren();
        org.apache.wicket.Component c = iterator.filterById(id).next();
        return c;
    }
}
