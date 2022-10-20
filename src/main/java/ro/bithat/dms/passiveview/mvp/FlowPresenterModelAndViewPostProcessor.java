package ro.bithat.dms.passiveview.mvp;

import ro.bithat.dms.passiveview.mvp.observer.*;

public interface FlowPresenterModelAndViewPostProcessor {

    default void prepareModelAndView(FlowPresenter presenter, String state) {
        FlowView view = presenter.getView();
        if(FlowViewBeforeBindingObserver.class.isAssignableFrom(view.getClass())) {
            ((FlowViewBeforeBindingObserver)view).beforeBinding();
        }
        if(FlowPresenterPrepareModelObserver.class.isAssignableFrom(presenter.getClass())) {
            ((FlowPresenterPrepareModelObserver)presenter).prepareModel(state);
        }
        if(FlowViewPrepareBindingObserver.class.isAssignableFrom(view.getClass())) {
            ((FlowViewPrepareBindingObserver)view).prepareBinding();
        }
        if(FlowPresenterAfterPrepareModelObserver.class.isAssignableFrom(presenter.getClass())) {
            ((FlowPresenterAfterPrepareModelObserver)presenter).afterPrepareModel(state);
        }
        if(FlowViewAfterBindingObserver.class.isAssignableFrom(view.getClass())) {
            ((FlowViewAfterBindingObserver)view).afterBinding();
        }
    }


}
