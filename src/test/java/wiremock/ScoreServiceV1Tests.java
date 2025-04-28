package wiremock;

import static org.assertj.core.api.Assertions.*;

import com.github.tomakehurst.wiremock.junit5.*;
import org.apache.commons.io.*;
import org.json.*;
import org.junit.*;
import services.ScoreServiceClient;
import stubs.RegisterStubsFile;
import java.io.*;
import java.nio.charset.*;


@WireMockTest()
public class ScoreServiceV1Tests {
  private static String stubFilePath = System.getProperty("user.dir") + "/src/test/resources/score_stub.json";

  @BeforeClass
  public static void register() throws IOException {
    new RegisterStubsFile().registerStubs(stubFilePath, "/score");
  }

  @Test
  public void test_get_score_stub() throws IOException {
    String scoreInfo = new ScoreServiceClient().getScoreInfo();
    InputStream inputStream = new FileInputStream(this.stubFilePath);

    JSONObject scoreJson = new JSONObject(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
    assertThat(scoreInfo)
        .as("Error when retrieving score info")
        .contains(scoreJson.getString("name"));

    assertThat(scoreInfo)
        .as("Error when retrieving score info")
        .contains(String.valueOf(scoreJson.getInt("score")));
  }
}
