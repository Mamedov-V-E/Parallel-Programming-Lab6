import akka.actor.AbstractActor;
import akka.http.javadsl.server.Route;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationStorageActor extends AbstractActor {
    private List<String> store = new ArrayList<>();

    public Receive createReceive() {
        return receiveBuilder()
                .match(StoreMessage.class, m -> {
                    store = new ArrayList<>().
                })

                .build();
    }
}
