import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;


public class FlowFactory {

    public static Flow<HttpRequest, HttpResponse, NotUsed> createFlow(ActorSystem system, ActorMaterializer materializer) {

    }
}
