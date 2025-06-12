package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String context = req.getContextPath();

        boolean isLoginPage = uri.endsWith("login.jsp");
        boolean isLoginServlet = uri.endsWith("login"); // ex: UsersController com rota "/login"
        boolean isStaticResource = uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/") || uri.contains("/fonts/");
        boolean isPublicFile = uri.endsWith(".html");

        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("usuario_logado") != null);

        if (loggedIn || isLoginPage || isLoginServlet || isStaticResource || isPublicFile) {
            chain.doFilter(request, response); // deixa passar
        } else {
            resp.sendRedirect(context + "/login.jsp");
        }
    }

    public void init(FilterConfig config) throws ServletException {}
    public void destroy() {}
}
