package com.antisciman;

import java.util.Hashtable;

import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weasis.core.api.gui.Insertable;
import org.weasis.core.api.gui.Insertable.Type;
import org.weasis.core.api.gui.InsertableFactory;
import org.weasis.core.api.gui.util.GuiUtils;
import org.weasis.core.ui.util.Toolbar;

@org.osgi.service.component.annotations.Component(
        service = InsertableFactory.class,
        property = {"org.weasis.dicom.viewer2d.View2dContainer=true"})
public class TumorToolbarFactory implements InsertableFactory {
    private final Logger LOGGER = LoggerFactory.getLogger(TumorToolbarFactory.class);

    private TumorToolBar toolbar = null;

    @Override
    public Type getType() {
        return Type.TOOLBAR;
    }

    @Override
    public Insertable createInstance(Hashtable<String, Object> properties) {
        toolbar = new TumorToolBar();
        List<Toolbar> toolbars = GuiUtils.getUICore().getExplorerPluginToolbars();
        toolbars.add(toolbar);
        return toolbar;
    }

    @Override
    public boolean isComponentCreatedByThisFactory(Insertable component) {
        return component instanceof TumorToolBar;
    }

    @Override
    public void dispose(Insertable bar) {
        if (bar != null) {
            // Remove all the registered listeners or other behaviors links with other existing components
            // if exists.
            if (bar instanceof TumorToolBar) {
                ((TumorToolBar) bar).getStartButton().removeAll();
            }
        }
    }


    // ================================================================================
    // OSGI service implementation
    // ================================================================================

    @Activate
    protected void activate(ComponentContext context) {
        LOGGER.info("Activate the Sample tool bar");
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        LOGGER.info("Deactivate the Sample tool bar");
        if (toolbar != null) {
            GuiUtils.getUICore()
                    .getExplorerPluginToolbars()
                    .removeIf(b -> b == toolbar);
        }
    }
}
