package wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import dto.User;
import org.junit.BeforeClass;
import org.junit.Test;
import services.UserServiceClient;
import stubs.RegisterStubsFile;

import java.io.IOException;


@WireMockTest()
public class UserServiceV1Tests {
    @BeforeClass
    public static void register() throws IOException {
        new RegisterStubsFile().registerStubs(System.getProperty("user.dir") + "/src/test/resources/user_stub.json", "/user");
    }

    @Test
    public void test_get_user_stub() {
        User user = new UserServiceClient().getUserInfo();
    }
}
