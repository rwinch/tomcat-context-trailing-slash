package sample;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Emulate a DispatcherServlet. We write to the response directly since our
 * example maps /* to the request and we cannot map *.jsp to a JSP servlet when
 * working with multiple containers.
 *
 * @author Rob Winch
 *
 */
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(req.getRequestURI().endsWith("/login")) {
			resp.getWriter().write("<html>\n" +
					"<body>\n" +
					"<h2>Login</h2>\n" +
					"<form method=\"post\" action=\"./login\">\n" +
					"	<input type=\"text\" name=\"user\" value=\"user\"/>\n" +
					"	<div>\n" +
					"		<input type=\"submit\" value=\"Sign In\"/>\n" +
					"	</div>\n" +
					"</form>\n" +
					"</body>\n" +
					"</html>");
		} else {
			resp.getWriter().write("<html>\n" +
					"<body>\n" +
					"<h2>Hello</h2>\n" +
					"<a href=\"./logout\">Sign Out</a>\n" +
					"</body>\n" +
					"</html>\n" +
					"");
		}
	}

}
