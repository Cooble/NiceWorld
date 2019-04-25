package cs.cooble.nice.event;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Handles all kinds of delayed/repeating/normal events
 * If some event is added -> the last thing in gameTick is to run those events with method processEvents(
 */
public final class CEventBus {
    private Queue<Event> eventQueue;
    private Queue<TimeEvent> delayedEvents;
    private Queue<TimeEvent> repeatingEvents;
    private Queue<Integer> toRemoveEvents;
    private int time;
    private boolean pauseEvents;

    private int currentEventID;

    public CEventBus() {
        eventQueue = new ConcurrentLinkedDeque<>();
        delayedEvents = new ConcurrentLinkedDeque<>();
        repeatingEvents = new ConcurrentLinkedDeque<>();
        toRemoveEvents=new ConcurrentLinkedDeque<>();

    }

    public void addEvent(Event event) {
        eventQueue.add(event);
    }

    public int addDelayedEvent(int tickDelay, Event event) {
        if (tickDelay == 0) {
            addEvent(event);
            return -1;
        }
        currentEventID++;
        delayedEvents.add(new TimeEvent(currentEventID, event, tickDelay + time));
        return currentEventID;
    }

    private boolean unregisterEventSrc(int eventID) {
        Stream<TimeEvent> delayed = delayedEvents.stream();
        final TimeEvent[] removed = {null};
        delayed.forEach(new Consumer<TimeEvent>() {
            @Override
            public void accept(TimeEvent timeEvent) {
                if (timeEvent.eventID == eventID)
                    removed[0] = timeEvent;
            }
        });
        if (removed[0] != null)
            delayedEvents.remove(removed[0]);
        else{
            Stream<TimeEvent> repeated = repeatingEvents.stream();
            repeated.forEach(new Consumer<TimeEvent>() {
                @Override
                public void accept(TimeEvent timeEvent) {
                    if (timeEvent.eventID == eventID)
                        removed[0] = timeEvent;
                }
            });
            if (removed[0] != null)
                repeatingEvents.remove(removed[0]);
        }
        return removed[0]!=null;
    }

    public void unregisterEvent(int eventID){
        toRemoveEvents.add(eventID);
    }

    public int addRepeatingEvent(int period, Event event) {
        currentEventID++;
        TimeEvent e = new TimeEvent(currentEventID,event, period + time);
        e.period = period;
        repeatingEvents.add(e);
        return currentEventID;
    }

    public boolean hasEvents() {
        return eventQueue.size() != 0;
    }

    public void proccessEvents() {
        time++;
        while (hasEvents()&&!pauseEvents) {
            eventQueue.remove().dispatchEvent();
        }
        ArrayList<TimeEvent> toRemove = new ArrayList<>();
        delayedEvents.stream().forEach(new Consumer<TimeEvent>() {
            @Override
            public void accept(TimeEvent event) {
                if (event == null||pauseEvents)
                    return;
                if (event.time == time) {
                    event.event.dispatchEvent();
                    toRemove.add(event);
                }
            }
        });
        repeatingEvents.stream().forEach(new Consumer<TimeEvent>() {
            @Override
            public void accept(TimeEvent event) {
                if (event == null||pauseEvents)
                    return;
                if (event.time == time) {
                    event.event.dispatchEvent();
                    event.time += event.period;
                }
            }
        });

        delayedEvents.removeAll(toRemove);
        while (toRemoveEvents.peek()!=null){
            int i = toRemoveEvents.remove();
            unregisterEventSrc(i);
        }
        pauseEvents=false;

    }

    /**
     * stops doing events for one tick
     * all other events will be called next tick
     */
    public void pauseEvents(){
        pauseEvents=true;
    }

    private class TimeEvent {
        Event event;
        int time;
        int period;
        int eventID;

        TimeEvent(int eventID, Event event, int time) {
            this.eventID = eventID;
            this.event = event;
            this.time = time;
        }
    }
}

