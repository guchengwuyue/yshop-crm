package co.yixiang.yshop.module.pay.http;

import com.egzosn.pay.common.bean.NoticeRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestNoticeNewParams implements NoticeRequest {
    private final HttpServletRequest httpServletRequest;

    public HttpRequestNoticeNewParams(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * 根据请求头名称获取请求头信息
     *
     * @param name 名称
     * @return 请求头值
     */
    @Override
    public String getHeader(String name) {
        return httpServletRequest.getHeader(name);
    }

    /**
     * 根据请求头名称获取请求头信息
     *
     * @param name 名称
     * @return 请求头值
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        return httpServletRequest.getHeaders(name);
    }

    /**
     * 获取所有的请求头名称
     *
     * @return 请求头名称
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return httpServletRequest.getHeaderNames();
    }

    /**
     * 输入流
     *
     * @return 输入流
     * @throws IOException IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return httpServletRequest.getInputStream();
    }

    /**
     * 获取所有的请求参数
     *
     * @return 请求参数
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        return httpServletRequest.getParameterMap();
    }
}
