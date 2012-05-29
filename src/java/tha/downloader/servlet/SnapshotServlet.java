package tha.downloader.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tha.downloader.HashtagDownloader;

public class SnapshotServlet extends HttpServlet {

	private static final long serialVersionUID = -7830243547240603167L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/plain");

		HashtagDownloader hg = new HashtagDownloader();
		hg.download();

		PrintWriter out = response.getWriter();
		try {
			out.println("started");
		} finally {
			out.println("finished");
			out.close();
		}
		
	}

}
