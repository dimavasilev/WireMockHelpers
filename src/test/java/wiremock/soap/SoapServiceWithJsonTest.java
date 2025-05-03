package wiremock.soap;

import com.github.tomakehurst.wiremock.*;
import com.github.tomakehurst.wiremock.client.*;
import org.junit.*;

import java.net.*;
import java.net.http.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

public class SoapServiceWithJsonTest {
  private WireMockServer wireMockServer;
  private static final int PORT = 8089;
  private static final String SOAP_ENDPOINT = "soap/service";
  private static final String JSON_ENDPOINT = "/user";

  @Before
  public void setUp() {
    // Запускаем WireMock сервер
    wireMockServer = new WireMockServer(PORT);
    wireMockServer.start();
    WireMock.configureFor("127.0.0.1", PORT);

    // Заглушка для JSON API
    stubFor(get(urlEqualTo(JSON_ENDPOINT))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\n" +
                "  \"name\":\"Test user\",\n" +
                "  \"course\":\"QA\",\n" +
                "  \"email\":\"test@test.test\",\n" +
                "  \"age\": 23\n" +
                "}")));

    stubFor(post(urlEqualTo(SOAP_ENDPOINT))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "text/xml")
            .withBody("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soap:Body>\n" +
                "      <ns2:getUserResponse xmlns:ns2=\"http://example.com/soap/service\">\n" +
                "         <user>\n" +
                "            <name>Test user</name>\n" +
                "            <course>QA</course>\n" +
                "            <email>test@test.test</email>\n" +
                "            <age>23</age>\n" +
                "         </user>\n" +
                "      </ns2:getUserResponse>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>")));
  }

  @After
  public void tearDown() {
    wireMockServer.stop();
  }

  @Test
  public void testJsonEndpoint() {
    // Тестируем JSON endpoint
    String response = WireMockHttpClient.get(System.getProperty("base.url.soap") + ":" + PORT + JSON_ENDPOINT);

    assertNotNull(response);
    assertTrue(response.contains("\"name\":\"Test user\""));
    assertTrue(response.contains("\"course\":\"QA\""));
    assertTrue(response.contains("\"email\":\"test@test.test\""));
  }

  //HTTP клиент для тестирования
  static class WireMockHttpClient {
    public static String get(String url) {
      try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
