import java.util.ArrayList;
import java.util.List;

public class EventBus {
    
    private List<EventHandler> eventHandlers = new ArrayList<>();

    public void register(EventHandler eventHandler) {
        this.eventHandlers.add(eventHandler);
    }

    public void post(Event event) {
        for (EventHandler eventHandler : this.eventHandlers) {
            eventHandler.handle(event);
        }
    }
}
