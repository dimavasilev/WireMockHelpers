package wiremock;


import java.io.*;
import java.nio.charset.*;
import org.json.*;
import org.apache.commons.io.*;
import org.junit.*;
import dto.User;
import services.UserServiceClient;
import stubs.RegisterStubsFile;
import com.github.tomakehurst.wiremock.junit5.*;


@WireMockTest()
public class UserServiceV1Tests {
  private String stubFilePath = System.getProperty("user.dir") + "/src/test/resources/user_stub.json";

  @BeforeClass
  public static void register() throws IOException {
    new RegisterStubsFile().registerStubs(stubFilePath, "/user");
  }

  @Test
  public void test_get_user_stub() throws IOException {
    User user = new UserServiceClient().getUserInfo();
    InputStream inputStream = new FileInputStream(stubFilePath);

//    JSONObject userJson = new JSONObject(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
//    userJson.getString("name");
  }
}
