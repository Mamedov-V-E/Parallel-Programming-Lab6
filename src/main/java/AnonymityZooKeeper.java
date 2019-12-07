import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.apache.zookeeper.*;
import scala.Int;

import java.util.ArrayList;
import java.util.List;

public class AnonymityZooKeeper implements Watcher {
    public static final String ZOOKEEPER_ID = "localhost";
    public static final int ZOOKEEPER_PORT = 2181;

    private final ZooKeeper zkClient;
    private final ActorRef storeActor;

    public AnonymityZooKeeper(Integer port, ActorRef storeActor) throws  Exception{
        this.zkClient = new ZooKeeper(
                ZOOKEEPER_ID + ':' + ZOOKEEPER_PORT,
                3000,
                this
        );
//        zkClient.create("/servers",
//                port.toString().getBytes(),
//                ZooDefs.Ids.OPEN_ACL_UNSAFE,
//                CreateMode.PERSISTENT
//        );
        zkClient.create("/servers/" + port,
                port.toString().getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL
        );
        this.storeActor = storeActor;
    }

    public void process(WatchedEvent event) {
        System.out.println();
        if (event.getState() == Event.KeeperState.SyncConnected ||
                event.getState() == Event.KeeperState.Disconnected) {
            try {
                System.out.println("NODE CHANGED");
                List<String> zkServers = zkClient.getChildren("/servers", this);
                List<String> serversIdPort = new ArrayList<>();
                for (String s : zkServers) {
                    byte[] port = zkClient.getData("/servers/" + s, false, null);
                    serversIdPort.add(ZOOKEEPER_ID + ':' + Integer.parseInt(port.toString()));
                }
                storeActor.tell(new StoreMessage(serversIdPort.toArray(new String[0])), ActorRef.noSender());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
    }
}
