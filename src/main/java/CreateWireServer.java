import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CreateWireServer {
    public WireMockServer wireMockServer;

    public CreateWireServer(int port) {
        this.wireMockServer = new WireMockServer(new WireMockConfiguration().port(port));
    }

    public void startServer() {
        wireMockServer.start();
    }
    public void stopServer() {
        wireMockServer.stop();
    }

    public void page(String host, int port, String url, String body) {
        WireMock.configureFor(host, port);
        wireMockServer.stubFor(get(urlEqualTo(url)).willReturn(aResponse().withBody(body)));
    }

    public String pageStatus(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(request);
        return httpResponse.getStatusLine().getReasonPhrase();
    }
}

