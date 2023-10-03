package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import utils.ServletUtils;

public class SetThreadPoolSizeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ServletUtils.setSizeOfThreadPool(getServletContext(), 1);
    }
}
