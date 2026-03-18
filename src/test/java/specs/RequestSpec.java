package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
    public static RequestSpecification requestJsonSpec(){
        return  new RequestSpecBuilder()
                .setBaseUri("http://85.192.34.140:8080/")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
}
