package ro.bithat.dms.template.navigation;

import java.util.EventObject;
import java.util.Optional;

@Deprecated
public class SearchSlideEvent extends EventObject {

    public SearchSlideEvent() {
        super(Optional.empty());
    }
}
