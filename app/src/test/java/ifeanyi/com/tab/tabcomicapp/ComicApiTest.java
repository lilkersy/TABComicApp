package ifeanyi.com.tab.tabcomicapp;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ifeanyi.com.tab.tabcomicapp.marvelapi.response.ServiceResponse;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class ComicApiTest {
    private static final String FILE_ENCODING = "UTF-8";
    protected static final String ANY_TIME_ZONE = "PST";
    private static final int OK_CODE = 200;

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        System.setProperty("user.timezone", ANY_TIME_ZONE);
        MockitoAnnotations.initMocks(this);
        this.server = new MockWebServer();
        this.server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    protected void enqueueMockResponse() throws IOException {
        enqueueMockResponse(OK_CODE);
    }

    protected void enqueueMockResponse(int code) throws IOException {
        enqueueMockResponse(OK_CODE, "{}");
    }

    protected void enqueueMockResponse(int code, String response) throws IOException {
        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(code);
        mockResponse.setBody(response);
        server.enqueue(mockResponse);
    }

    protected void enqueueMockResponse(String fileName) throws IOException {
        MockResponse mockResponse = new MockResponse();
        String fileContent = getContentFromFile(fileName);
        mockResponse.setBody(fileContent);
        server.enqueue(mockResponse);
    }

    protected void assertRequestSentTo(String url) throws InterruptedException {
        RecordedRequest request = server.takeRequest();
        assertEquals(url, request.getPath());
    }

    protected void assertRequestSentToContains(String... paths) throws InterruptedException {
        RecordedRequest request = server.takeRequest();

        for (String path : paths) {
            Assert.assertThat(request.getPath(), containsString(path));
        }
    }

    protected <T> void assertBasicMarvelResponse(ServiceResponse<T> marvelResponse) {
        assertEquals(200, marvelResponse.code);
        assertEquals("Ok", marvelResponse.status);
        assertEquals("55122b303dfbc9fdfd12c080eded740b83354269", marvelResponse.etag);
    }

    protected String getBaseEndpoint() {
        return server.getUrl("/").toString();
    }

    private String getContentFromFile(String fileName) throws IOException {
        fileName = getClass().getResource("/" + fileName).getFile();
        File file = new File(fileName);
        List<String> lines = FileUtils.readLines(file, FILE_ENCODING);
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
