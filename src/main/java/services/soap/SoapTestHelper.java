package services.soap;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class SoapTestHelper {

  public static void stubSoapResponse(String urlPath,
                                      String soapAction,
                                      String expectedRequestXpath,
                                      String responseBody) {

    String soapResponse = """
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
            <soapenv:Header/>
            <soapenv:Body>
                %s
            </soapenv:Body>
        </soapenv:Envelope>
        """.formatted(responseBody);

    stubFor(post(urlPathEqualTo(urlPath))
        .withHeader("Content-Type", containing("text/xml"))
        .withHeader("SOAPAction", equalTo(soapAction))
        .withRequestBody(matchingXPath(expectedRequestXpath))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "text/xml")
            .withBody(soapResponse)));
  }

  public static void verifySoapRequest(String urlPath,
                                       String soapAction,
                                       String expectedRequestXpath) {
    verify(postRequestedFor(urlPathEqualTo(urlPath))
        .withHeader("Content-Type", containing("text/xml"))
        .withHeader("SOAPAction", equalTo(soapAction))
        .withRequestBody(matchingXPath(expectedRequestXpath)));
  }
}