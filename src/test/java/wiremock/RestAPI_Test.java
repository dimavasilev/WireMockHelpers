package wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest()
public class RestAPI_Test {
    @Test
    public void test_get_user_stub() {
     stubFor(get("/user").willReturn(aResponse()
             .withBodyFile("user.json").withStatus(200)));
    }
}
