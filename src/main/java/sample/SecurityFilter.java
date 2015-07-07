package sample;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter {
	private static final String USER_ATTR = "user";
	private static final String LOGIN_PAGE = "/login";
	private static final String ORIGIONAL_REQUEST_ATTR = "saved-request";


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession();
		String username = (String) session.getAttribute(USER_ATTR);

		if(httpRequest.getRequestURI().endsWith("/logout")) {
			session.invalidate();
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
			return;
		}

		boolean isLoginRequest = httpRequest.getRequestURI().endsWith(LOGIN_PAGE);

		if(isLoginRequest) {
			if("POST".equals(httpRequest.getMethod())) {
				session.setAttribute(USER_ATTR, request.getParameter(USER_ATTR));
				String toRedirect = (String) session.getAttribute(ORIGIONAL_REQUEST_ATTR);

				httpResponse.sendRedirect(toRedirect);
			} else {
				chain.doFilter(request, response);
			}
			return;
		}

		if(username == null) {
			String toRedirect = httpRequest.getRequestURL().toString();
			System.out.println("On success log in will redirect to " + toRedirect);
			session.setAttribute(ORIGIONAL_REQUEST_ATTR, toRedirect);
			httpResponse.sendRedirect("login");
			return;
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig cofig) throws ServletException {
	}

	public void destroy() {
	}
}
