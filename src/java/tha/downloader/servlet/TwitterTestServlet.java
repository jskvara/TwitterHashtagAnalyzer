package tha.downloader.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tha.downloader.TwitterApiTest;

public class TwitterTestServlet extends HttpServlet {

	private static final long serialVersionUID = 3247934733804154526L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");

        resp.getWriter().println(
			new TwitterApiTest(req.getParameter("tag") == null ? "cvut" :
				req.getParameter("tag"), 2).getData());
    }
}
