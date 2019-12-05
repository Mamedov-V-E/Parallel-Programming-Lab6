import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ZooKeeperApp {
    public static final String ACTOR_SYSTEM_NAME = "zooKeeperSystem";
    public static final String CONFIG_ACTOR_NAME = "configActor";

    public static void main (String[] args) {
        if (args.length < 1) {
            System.out.println("Server port specified");
            System.exit(-1);
        }

        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef configActor = system.actorOf(Props.create(
                ConfigurationStorageActor.class,
                CONFIG_ACTOR_NAME));

        final
    }
}
