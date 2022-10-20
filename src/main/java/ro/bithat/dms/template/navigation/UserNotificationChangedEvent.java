package ro.bithat.dms.template.navigation;

import java.util.EventObject;
import java.util.Optional;

@Deprecated
public class UserNotificationChangedEvent extends EventObject {

    public UserNotificationChangedEvent() {
        super(Optional.empty());
    }
}
