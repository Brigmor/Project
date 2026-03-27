package Controller;

import Controller.interfaces.RequestImpl;
import Controller.interfaces.RequestInterface;
import io.restassured.response.Response;
import utils.Endpoints;
import utils.FilesEndpoints;

public class DownloadApi {
    private static final RequestInterface request = new RequestImpl();



    public static Response downloadImage(){
        return request.get(Endpoints.downloadImage,null);
    }

    public static Response uploadImageServer(){

        return request.postUpload(Endpoints.uploadFile, FilesEndpoints.fileToUpload);

    }
}
