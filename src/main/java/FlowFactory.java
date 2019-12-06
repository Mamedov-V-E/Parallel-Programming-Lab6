import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import scala.concurrent.Future;


public class FlowFactory {
    private static final String SITE_PARAMETER_NAME = "url";
    private static final String COUNT_PARAMETER_NAME = "count";

    public static Flow<HttpRequest, HttpResponse, NotUsed> createFlow(ActorRef storeActor, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class).map(r -> {
            Query q = r.getUri().query();
            String site = q.get(SITE_PARAMETER_NAME).get();
            Integer count = Integer.parseInt(q.get(COUNT_PARAMETER_NAME).get());

            if (count > 0) {
                Future<Object> result = Patterns.ask(storeActor, new GetMessage(), 10000);
                result.onComplete();
            }
        })
    }
}
