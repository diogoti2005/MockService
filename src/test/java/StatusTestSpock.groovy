import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.apache.http.HttpResponse
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import spock.lang.Specification
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo

class StatusTestSpock extends Specification {

    def "checkPageStatus"() {
        setup:
        CloseableHttpClient httpClient = HttpClients.createDefault();
        WireMockServer server = new WireMockServer(9080);
        and:
        server.start();
        and:
        WireMock.configureFor("localhost", 9080);
        server.stubFor(get(urlEqualTo("/api/status"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("OK")));
        when:
        org.apache.http.client.methods.HttpGet request = new org.apache.http.client.methods.HttpGet("http://localhost:9080/api/status");
        HttpResponse httpResponse = httpClient.execute(request);

        then:
        httpResponse.getStatusLine().getStatusCode() == 200;
        httpResponse.getStatusLine().getReasonPhrase() == "OK";

        cleanup:
        httpResponse.close();
        httpClient.close();
        server.stop();
    }


}