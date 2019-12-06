import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;


public class FlowFactory {
    private static final String SITE_PARAMETER_NAME = "url";
    private static final String COUNT_PARAMETER_NAME = "count";

    public static Flow<HttpRequest, HttpResponse, NotUsed> createFlow(ActorSystem system, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class).map(r -> {
            Query q = r.getUri().query();
            String site = q.get(SITE_PARAMETER_NAME).get();
            Integer count = Integer.parseInt(q.get(COUNT_PARAMETER_NAME).get());

            return (count > 0) ?

        })
    }
}
