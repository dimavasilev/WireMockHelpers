package stubs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class RegisterStubsFile {
    public void registerStubs(String stubPath, String pathUrl) throws IOException {
        InputStream fileInputStream = new FileInputStream(stubPath);
        stubFor(get(pathUrl).willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(fileInputStream.readAllBytes())
                .withStatus(200)
        ));
    }
}
