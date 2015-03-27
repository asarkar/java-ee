package name.abhijitsarkar.javaee.servlet31.admin;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/*", asyncSupported = true, initParams = { @WebInitParam(name = "data", value = "/data.txt") })
public class ImportantServlet extends HttpServlet {
    private static final long serialVersionUID = 9092622024612663394L;

    private transient InputStream content;

    @Override
    public void init(ServletConfig sc) throws ServletException {
	super.init();

	String path = sc.getInitParameter("data");

	content = getClass().getResourceAsStream(path);

	if (content == null) {
	    throw new IllegalStateException("File not found: " + path);
	}
    }

    @Override
    public void destroy() {
	super.destroy();

	try {
	    if (content != null) {
		content.close();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	AsyncContext async = req.startAsync();
	ServletOutputStream out = resp.getOutputStream();
	out.setWriteListener(new StandardDataStream(content, async, out));
    }

    private static class StandardDataStream implements WriteListener {
	private final InputStream content;
	private final AsyncContext async;
	private final ServletOutputStream out;

	private StandardDataStream(InputStream content, AsyncContext async,
		ServletOutputStream out) {
	    this.content = content;
	    this.async = async;
	    this.out = out;
	}

	@Override
	public void onError(Throwable t) {
	    async.complete();

	    t.printStackTrace();
	}

	@Override
	public void onWritePossible() throws IOException {
	    byte[] buffer = new byte[1024];
	    int len = -1;

	    while (out.isReady() && (len = content.read(buffer)) >= 0) {
		out.write(buffer, 0, len);
	    }

	    async.complete();
	}
    }
}
