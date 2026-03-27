package tests;

import Controller.DownloadApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class FilesTest extends BaseTestApi {

    @Test
    public void DownloadImageServer(){
        Response downResp = DownloadApi.downloadImage();
        downResp.then()
                .statusCode(200)
                .header("Content-Type", "application/octet-stream");
    }
    @Test
    public void UploadFileServer(){
        Response uploadResp = DownloadApi.uploadImageServer();
        uploadResp.then()
                .statusCode(200);
        assertEquals("success", uploadResp.jsonPath().getString("info.status"));
        assertEquals("file uploaded to server",uploadResp.jsonPath().getString("info.message"));

        Response downResp = DownloadApi.downloadImage();
        downResp.then()
                .statusCode(200)
                .header("Content-Type","application/octet-stream");





    }
}
