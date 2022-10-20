package ro.bithat.dms.config;

import com.eaio.uuid.UUID;
import org.slf4j.MDC;
import ro.bithat.dms.security.SecurityUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LogbackFilter implements Filter {

    public static final String TRACE_ID = "traceid";
    public static final String REQUEST_ID_HEADER = "dr-trace-id";
    public static final String USERNAME = "username";
    public static final String USERID = "userid";
    public static final String CLIENT_IP = "clientip";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {

        String uuid = readOrCreateUuid((HttpServletRequest)servletRequest);
        
        MDC.put(TRACE_ID, uuid.toString());
        MDC.put(USERNAME, SecurityUtils.getUsername());
        MDC.put(USERID, SecurityUtils.getUserIdAsString());
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        MDC.put(CLIENT_IP, httpServletRequest.getHeader("X-Forwarded-For")!=null?httpServletRequest.getHeader("X-Forwarded-For"):servletRequest.getRemoteAddr());
        servletRequest.setAttribute(TRACE_ID, uuid);

        try {
          filterChain.doFilter(servletRequest, servletResponse);
        } finally {
          MDC.remove(TRACE_ID);
          MDC.remove(USERNAME);
          MDC.remove(USERID);
          MDC.remove(CLIENT_IP);
        }
    }

    private String readOrCreateUuid(HttpServletRequest request) {
        String uuid = request.getHeader(REQUEST_ID_HEADER);
        if (uuid != null) {
            return uuid;
        }
        Object uuidObj = request.getAttribute(TRACE_ID);
        if (uuidObj == null || !(uuidObj instanceof String)) {
            return new UUID().toString();
        }
        else {
            return uuidObj.toString();
        }
    }

    @Override
    public void destroy() {
    }

}
