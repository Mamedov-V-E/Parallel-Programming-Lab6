import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import scala.concurrent.Future;

import java.util.concurrent.CompletionStage;


public class FlowFactory {
    private static final String SITE_PARAMETER_NAME = "url";
    private static final String COUNT_PARAMETER_NAME = "count";

    public static Flow<HttpRequest, HttpResponse, NotUsed> createFlow(ActorSystem system,
                                                                      ActorRef storeActor,
                                                                      ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class).map(r -> {
            Query q = r.getUri().query();
            String site = q.get(SITE_PARAMETER_NAME).get();
            Integer count = Integer.parseInt(q.get(COUNT_PARAMETER_NAME).get());

            final Http http = Http.get(system);
            if (count > 0) {
                Future<Object> server = Patterns.ask(storeActor, new GetMessage(), 10000);
                return http.singleRequest(HttpRequest.create(
                        "http://" + server + "/?"
                                + SITE_PARAMETER_NAME + "=" + site + "&"
                                + COUNT_PARAMETER_NAME + "=" + (count-1))
                );
            } else {
                return http.singleRequest(HttpRequest.create(site));
            }
        });
    }

    public CompletionStage<HttpResponse> fetch(String url) {
        return http.singleRequest(HttpRequest.create(url));
    }
}
