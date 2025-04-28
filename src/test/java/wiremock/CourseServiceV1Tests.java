package wiremock;

import static org.assertj.core.api.Assertions.*;

import com.github.tomakehurst.wiremock.junit5.*;
import org.apache.commons.io.*;
import org.json.*;
import org.junit.*;
import services.CourseServiceClient;
import stubs.RegisterStubsFile;
import java.io.*;
import java.nio.charset.*;

@WireMockTest()
public class CourseServiceV1Tests {
  private static String stubFilePath = System.getProperty("user.dir") + "/src/test/resources/courses_stub.json";

  @BeforeClass
  public static void register() throws IOException {
    new RegisterStubsFile().registerStubs(stubFilePath, "/course");
  }

  @Test
  public void test_get_course_stub() throws IOException {
    String courseInfo = new CourseServiceClient().getCourseInfo();
    InputStream inputStream = new FileInputStream(this.stubFilePath);

    JSONObject coursesJson = new JSONObject(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
    assertThat(courseInfo)
        .as("Error when retrieving course info")
        .contains(coursesJson.getJSONArray("jsonBody").getJSONObject(0).getString("name"));

    assertThat(courseInfo)
        .as("Error when retrieving course info")
        .contains(String.valueOf(coursesJson.getJSONArray("jsonBody").getJSONObject(0).getInt("price")));
  }
}
