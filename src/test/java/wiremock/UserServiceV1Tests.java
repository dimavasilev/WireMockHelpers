package wiremock;

import static org.assertj.core.api.Assertions.*;

import com.github.tomakehurst.wiremock.junit5.*;
import org.apache.commons.io.*;
import org.json.*;
import org.junit.*;
import services.UserServiceClient;
import stubs.RegisterStubsFile;
import java.io.*;
import java.nio.charset.*;


@WireMockTest()
public class UserServiceV1Tests {
  private static String stubFilePath = System.getProperty("user.dir") + "/src/test/resources/user_stub.json";

  @BeforeClass
  public static void register() throws IOException {
    new RegisterStubsFile().registerStubs(stubFilePath, "/user");
  }

  @Test
  public void test_get_user_stub() throws IOException {
    String user = new UserServiceClient().getUserInfo();
    InputStream inputStream = new FileInputStream(this.stubFilePath);

    JSONObject userJson = new JSONObject(IOUtils.toString(inputStream, StandardCharsets.UTF_8));

    assertThat(user)
        .as("Error when retrieving user info")
        .contains(userJson.getString("name"));

    assertThat(user)
        .as("Error when retrieving user info")
        .contains(userJson.getString("course"));

    assertThat(user)
        .as("Error when retrieving user info")
        .contains(userJson.getString("email"));

    assertThat(user)
        .as("Error when retrieving user info")
        .contains(String.valueOf(userJson.getInt("age")));

  }
}
