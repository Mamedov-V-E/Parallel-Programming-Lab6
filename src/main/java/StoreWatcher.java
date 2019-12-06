import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class StoreWatcher implements Watcher {
    public synchronized void processWathcEvent(ZooKeeper zk, WatchedEvent event) {

    }

//    public void process(WatchedEvent event) {
//        if (event.getType() == Watcher.Event.EventType.NodeCreated) {
//            .getChildren("/servers", this)
//        }
//    }
}
