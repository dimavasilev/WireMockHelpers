package wiremock.soap;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;
import static org.junit.Assert.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.*;
import org.junit.*;
import services.soap.SoapClient;

@WireMockTest(httpPort = DYNAMIC_PORT)
public class UserSoapApiTest {

  private static final String WSDL_NAMESPACE = "http://127.0.0.1:8080/wsdl";

  @BeforeClass
  public static void setup() {

    // Настройка SOAP stub с правильным телом запроса
    stubFor(WireMock.post(WireMock.urlEqualTo("/soap/userService"))
        .withHeader("Content-Type", WireMock.containing("text/xml"))
        .withHeader("SOAPAction", WireMock.equalTo("getUser"))
        .willReturn(WireMock.aResponse()
            .withHeader("Content-Type", "text/xml")
            .withBodyFile("soap_response.xml")));
  }

  @Test
  public void testGetUserSoap() throws Exception {
    SoapClient client = new SoapClient(
        "http://127.0.0.1:8080/soap/userService",
        WSDL_NAMESPACE
    );

    String response = client.callSoapService("getUser", "");
    System.out.println("Response: " + response.getBytes().toString());
    assertTrue(response.contains("Test user"));
  }
}
