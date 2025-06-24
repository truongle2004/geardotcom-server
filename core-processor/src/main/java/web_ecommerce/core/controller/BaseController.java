package web_ecommerce.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import web_ecommerce.core.utils.Constants;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/" + Constants.API + "/")
public abstract class BaseController {
    public static final String V1 = Constants.V1;
    public static final String V2 = Constants.V2;
    private static final String SLASH = "/";
    public static final String API = SLASH + Constants.API + SLASH;

    public String getUserId(HttpServletRequest httpRequest) {
        String userId = httpRequest.getHeader("X-User-Id");
        return userId;
    }
}
