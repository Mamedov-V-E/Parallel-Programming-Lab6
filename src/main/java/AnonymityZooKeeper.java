import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class AnonymityZooKeeper implements Watcher {
    public static final String ZOOKEEPER_ID = "localhost";
    public static final int ZOOKEEPER_PORT = 2181;

    private final ZooKeeper zkClient;

    public AnonymityZooKeeper() throws  Exception{
        zkClient = new ZooKeeper(
                ZOOKEEPER_ID + ':' + ZOOKEEPER_PORT,
                3000,
                this
        );
    }

    public void process(WatchedEvent event) {
        if (event.getType() == Watcher.Event.EventType.NodeCreated) {
            try {
                List<String> zkServers = zkClient.getChildren("/servers", this);
                List<String> serversIdPort;
                for (String s : zkServers) {
                    byte[] port = zkClient.getData("")
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
    }
}
