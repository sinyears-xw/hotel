package com.jiuzhe.hotel.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuzhe.hotel.utils.IPUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Configuration
@Order(1)
@WebFilter(filterName = "tokenFilter", urlPatterns = "/*")
public class TokenFilterConfig implements Filter {
    private Log logger = LogFactory.getLog(this.getClass());

    @Value("${filtertoken}")
    private boolean filterToken;


    @Override
    public void init(FilterConfig filterConfig) {
    }

    private boolean checkIP(HttpServletRequest request) {
        String ip = IPUtil.getRemoteHost(request);

        if (ip.equals("127.0.0.1"))
            return true;
        return false;
    }

    private void setResponse(ServletResponse response, Map rs) {
        PrintWriter writer = null;
        OutputStreamWriter osw = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String resultJson = mapper.writeValueAsString(rs);
            osw = new OutputStreamWriter(response.getOutputStream(),
                    StandardCharsets.UTF_8);
            writer = new PrintWriter(osw, true);
            writer.write(resultJson);
            writer.flush();
            writer.close();
            osw.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (null != writer) {
                writer.close();
            }
            if (null != osw) {
                try {
                    osw.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        // Change the req and res to HttpServletRequest and HttpServletResponse
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        //验证ip是否为本机
        if (filterToken && checkIP(request)) {

            // Get authorization from Http request
            final String token = request.getHeader("token");

            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                if (token == null) {
//                    setResponse(res, RtCodeConstant.getResult("-1", "Request头缺失token"));
                    return;
                }
                //校验token
            }
        }
        chain.doFilter(req, res);
    }


    @Override
    public void destroy() {
    }


}
