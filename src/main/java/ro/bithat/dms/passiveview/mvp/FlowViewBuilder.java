package ro.bithat.dms.passiveview.mvp;

public interface FlowViewBuilder<P extends FlowPresenter> extends FlowView<P> {

    void buildView(P presenter);

}
