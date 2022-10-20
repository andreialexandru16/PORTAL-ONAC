package ro.bithat.dms.passiveview;

import ro.bithat.dms.passiveview.mvp.FlowView;

public interface FlowViewFactory {

    FlowView getView(String instanceId, Class<? extends FlowView> viewType);

    FlowView getView(String instanceId);

    int containsViewOfType(Class<?> viewType);

    boolean containsView(Object view);

    boolean containsView(String instanceId);

}
