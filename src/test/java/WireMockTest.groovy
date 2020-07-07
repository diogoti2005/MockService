import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.apache.http.HttpResponse
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

class WireMockTest extends Specification {

    def "ServerPageStatus"() {
        setup:
        CloseableHttpClient httpClient = HttpClients.createDefault();
        new CreateWire();

        when:
        org.apache.http.client.methods.HttpGet request = new org.apache.http.client.methods.HttpGet("http://localhost:8090/api/status");
        HttpResponse httpResponse = httpClient.execute(request);

        then:
        httpResponse.getStatusLine().getStatusCode() == 200;
        httpResponse.getStatusLine().getReasonPhrase() == "OK";

    }

}