package com.stone.consts;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class UserContext {
public static final String USER_IN_SESSION       = "USER_IN_SESSION";
public static final String EXPRESSION_IN_SESSION = "EXPRESSION_IN_SESSION";

public static List<String> getExpressions() {
return (List<String>) getSession().getAttribute(EXPRESSION_IN_SESSION);
}

public static void setExpressions(List<String> expressions) {
getSession().setAttribute(EXPRESSION_IN_SESSION, expressions);
}

public static <T> T getCurrentUser() {
  return (T) getSession().getAttribute(USER_IN_SESSION);
}

public static <T> void setCurrentUser(T currentUser) {
getSession().setAttribute(USER_IN_SESSION, currentUser);
}

public static HttpSession getSession() {
// RequestContextHolder.getRequestAttributes().getAttribute(USER_IN_SESSION, RequestAttributes.SCOPE_SESSION);
ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
return attrs.getRequest().getSession();
}
}


