import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class AnonymityZooKeeper implements Watcher {
    private final ZooKeeper zkClient;

    public AnonymityZooKeeper() throws  Exception{
        zkClient = new ZooKeeper(
                "sa" + ':' + 1233,
                3000,
                this
        );
    }

    public void process(WatchedEvent event) {
        if (event.getType() == Watcher.Event.EventType.NodeCreated) {
            List<String> servers = zkClient.getChildren("/servers", this);
        }
    }
}
