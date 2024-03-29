import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.*;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import scala.concurrent.Future;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class FlowFactory {
    private static final String SITE_PARAMETER_NAME = "url";
    private static final String COUNT_PARAMETER_NAME = "count";
    private static final int MAX_SIMULTANEOUS_REQUESTS = 10;
    private static final Duration TIMOUT_MILLIS = Duration.ofMillis(10000);

    public static Flow<HttpRequest, HttpResponse, NotUsed> createFlow(ActorSystem system,
                                                                      ActorRef storeActor) {
        return Flow.of(HttpRequest.class).mapAsync(MAX_SIMULTANEOUS_REQUESTS, r -> {
            Query q = r.getUri().query();
            String site = q.get(SITE_PARAMETER_NAME).get();
            Integer count = Integer.parseInt(q.get(COUNT_PARAMETER_NAME).get());
            System.out.println("host got request with count: " + count);

            final Http http = Http.get(system);
            if (count > 0) {
                return Patterns.ask(storeActor, new GetMessage(), TIMOUT_MILLIS)
                        .thenCompose(server ->
                            http.singleRequest(HttpRequest.create(
                                    formUri((String)server, site, count)
                            )));
            } else {
                return http.singleRequest(HttpRequest.create(site));
            }
        });
    }

    private static String formUri(String server, String site, int count) {
        return "http://" + server + "/?"
                + SITE_PARAMETER_NAME + "=" + site + "&"
                + COUNT_PARAMETER_NAME + "=" + (count-1);
    }
}
