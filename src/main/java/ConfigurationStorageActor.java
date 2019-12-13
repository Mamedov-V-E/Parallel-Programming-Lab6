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
                    System.out.println("changing servers' list");
                    store = Arrays.asList(m.getServers());
                })
                .match(GetMessage.class, m -> {
                    String randomServer = store.get((int)(Math.random() * store.size()));
                    System.out.println("getting random server: " + randomServer);
                    sender().tell(randomServer, self());
                })
                .build();
    }
}
