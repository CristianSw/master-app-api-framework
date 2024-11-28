package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Utils {
    public static RequestSpecification rec;

    public RequestSpecification requestSpecification() throws IOException {
        if (rec == null) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            PrintStream ps = new PrintStream(Files.newOutputStream(Paths.get("log_" + formattedDateTime + ".txt")));
            rec = new RequestSpecBuilder()
                    .setBaseUri(getGlobalProperties("baseUrl"))
                    .addHeader("Content-Type", "application/json")
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(ps))
                    .addFilter(ResponseLoggingFilter.logResponseTo(ps))
                    .build();
        }
        return rec;
    }

    public String getGlobalProperties(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/org/example/resources/global.properties");
        properties.load(fis);
        return properties.getProperty(key);

    }

    public String getJsonPathValue(Response response, String key) {
        String strResponse = response.asString();
        JsonPath js = new JsonPath(strResponse);

        return js.get(key).toString();
    }
}
