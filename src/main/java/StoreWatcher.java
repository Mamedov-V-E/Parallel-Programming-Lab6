import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class StoreWatcher implements Watcher {
    private final ZooKeeper zkClient;
//    public synchronized void processWathcEvent(ZooKeeper zk, WatchedEvent event) {
//
//    }
    public StoreWatcher () throws  Exception{
        zkClient = new ZooKeeper(
                "sa" + ':' + 1233,
                3000,
                this
        );
    }

    public void process(WatchedEvent event) {
        if (event.getType() == Watcher.Event.EventType.NodeCreated) {
            .getChildren("/servers", this)
        }
    }
}
