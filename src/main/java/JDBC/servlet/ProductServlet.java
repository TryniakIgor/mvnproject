package JDBC.servlet;

import JDBC.model.Product;
import JDBC.service.ProductService;
import JDBC.service.impl.ProductServiceImpl;
import JDBC.util.RestUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = RestUtil.getFromJson(req, Product.class);
        int status = productService.create(product);
        resp.setStatus(status);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }
        try {
            long id = Long.parseLong(idStr);
            Product product = RestUtil.getFromJson(request, Product.class);
            product.setId(id);
            int status = productService.update(product);
            response.setStatus(status);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id parameter: " + idStr);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }
        try {
            long id = Long.parseLong(idStr);
            Product product = productService.getById(id);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }
            String respJson = RestUtil.toJson(product);
            response.getWriter().write(respJson);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id parameter: " + idStr);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("id");
        if (productId != null) {
            int status = productService.deleteById(Integer.parseInt(productId));
            if (status ==202) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Product deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Failed to delete product");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Product ID not provided");
        }
    }
}
