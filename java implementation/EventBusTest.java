import java.util.List;
import java.util.ArrayList;

public class EventBusTest {
  public static void main(String[] args) {
    MyEventBus eventBus = new MyEventBus();
    A a = new A(eventBus);
    B b = new B(eventBus);

    a.somethingHappens("Foo", 42);
  }
}

// En hendelse er en samling med verdier
record MyEvent(String msg, int num) {}

// Grensesnittet (en metode i) b må implementere 
@FunctionalInterface
interface MyEventHandler {
  void handle(MyEvent event);
}

// Selve EventBus -klassen
class MyEventBus {
  private List<MyEventHandler> eventHandlers = new ArrayList<>();

  public void register(MyEventHandler eventHandler) {
    this.eventHandlers.add(eventHandler);
  }

  public void post(MyEvent event) {
    for (MyEventHandler eventHandler : this.eventHandlers) {
      eventHandler.handle(event);
    }
  }
}

// Eksempel på klasse som produserer hendelser 
class A {
  private MyEventBus eventBus;

  public A(MyEventBus eventBus) {
    this.eventBus = eventBus;
  }

  public void somethingHappens(String msg, int num) {
    System.out.println(this + " method called with args: " + msg + ", " + num);
    this.eventBus.post(new MyEvent(msg, num));
  }
}

// Eksempel på klasse som konsumerer hendelser
class B {
  public B(MyEventBus eventBus) {
    eventBus.register(this::doReaction);
  }

  private void doReaction(MyEvent event) {
    String msg = event.msg();
    int num = event.num();
    System.out.println(this + " reacts to event w/info: " + msg + ", " + num);
  }
}
