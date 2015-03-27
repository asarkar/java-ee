package name.abhijitsarkar.javaee.servlet31.security;

import static java.lang.String.join;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getEncoder;
import static javax.servlet.http.HttpServletRequest.BASIC_AUTH;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GatekeeperFilter implements Filter {
    private static final String USERNAME_PASSWORD_DELIMITER = ":";
    private static final String AUTH_HEADER = "Authorization";

    private String base64EncCipher;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
	    FilterChain fc) throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) req;

	String cipher = request.getHeader(AUTH_HEADER);

	if (base64EncCipher.equals(cipher)) {
	    fc.doFilter(req, resp);
	} else {
	    resp.reset();
	    ((HttpServletResponse) resp).setStatus(SC_FORBIDDEN);
	}
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
	base64EncCipher = newBase64EncCipher(fc.getInitParameter("username"),
		fc.getInitParameter("password"));
    }

    private String newBase64EncCipher(String username, String password) {
	String token = join(USERNAME_PASSWORD_DELIMITER, username, password);

	return join(" ", BASIC_AUTH,
		new String(getEncoder().encode(token.getBytes(UTF_8))));
    }
}
