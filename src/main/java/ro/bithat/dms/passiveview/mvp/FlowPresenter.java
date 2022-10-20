package ro.bithat.dms.passiveview.mvp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface FlowPresenter<V extends FlowView> {

    default Logger getLogger(){
        return LoggerFactory.getLogger(getClass());
    }

    V getView();

    void setView(V view);

}
