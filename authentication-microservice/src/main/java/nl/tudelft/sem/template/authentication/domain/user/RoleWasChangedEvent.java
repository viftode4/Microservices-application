package nl.tudelft.sem.template.authentication.domain.user;

public class RoleWasChangedEvent {
    private final AppUser user;

    public RoleWasChangedEvent(AppUser user) {
        this.user = user;
    }

    public AppUser getUser() {
        return this.user;
    }
}
