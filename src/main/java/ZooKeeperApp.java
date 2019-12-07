import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CompletionStage;

public class ZooKeeperApp {
    public static final String ACTOR_SYSTEM_NAME = "zooKeeperSystem";
    public static final String CONFIG_ACTOR_NAME = "configActor";
    public static final String HOST_NAME = "localhost";


    public static void main (String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Server port is not specified");
            System.exit(-1);
        }
        Integer port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Wrong port format");
            System.exit(-1);
        }

        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef configActor = system.actorOf(Props.create(
                ConfigurationStorageActor.class,
                CONFIG_ACTOR_NAME));
        AnonymityZooKeeper zoo = new AnonymityZooKeeper(port, configActor);

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                FlowFactory.createFlow(system, configActor, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(HOST_NAME, port),
                materializer
        );

        System.out.println("Server online at http://" + HOST_NAME + ":" + port + "\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenApply(unbound -> system.terminate());
    }
}
