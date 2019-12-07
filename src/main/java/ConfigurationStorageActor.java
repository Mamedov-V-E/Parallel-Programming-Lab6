import akka.actor.AbstractActor;
import akka.http.javadsl.server.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigurationStorageActor extends AbstractActor {
    private List<String> store = new ArrayList<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StoreMessage.class, m -> {
                    store = Arrays.asList(m.getServers());
                })
                .match(GetMessage.class, m -> {
                    String randomServer = store.get((int)(Math.random() * store.size()));
                    sender().tell(randomServer, self());
                })
                .build();
    }
}
