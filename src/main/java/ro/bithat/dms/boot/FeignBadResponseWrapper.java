//package ro.bithat.dms.boot;
//
//import com.netflix.hystrix.exception.HystrixBadRequestException;
//import org.springframework.http.HttpHeaders;
//
//public class FeignBadResponseWrapper extends HystrixBadRequestException {
//    private final int status;
//    private final HttpHeaders headers;
//    private final String body;
//
//    public FeignBadResponseWrapper(int status, HttpHeaders headers, String body) {
//        super("Bad request");
//        this.status = status;
//        this.headers = headers;
//        this.body = body;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public HttpHeaders getHeaders() {
//        return headers;
//    }
//
//    public String getBody() {
//        return body;
//    }
//}