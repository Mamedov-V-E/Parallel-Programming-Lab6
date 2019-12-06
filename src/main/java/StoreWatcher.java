import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class StoreWatcher implements Watcher {
    private final ZooKeeper zoo;
//    public synchronized void processWathcEvent(ZooKeeper zk, WatchedEvent event) {
//
//    }
    public StoreWatcher () throws  Exception{
        zoo = new ZooKeeper(
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
