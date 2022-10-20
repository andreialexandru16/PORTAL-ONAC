package ro.bithat.dms.mvp.component;

import java.util.Optional;

@Deprecated
public interface HasSelection<T> {

    void publishSelection(Optional<T> selection);

}
