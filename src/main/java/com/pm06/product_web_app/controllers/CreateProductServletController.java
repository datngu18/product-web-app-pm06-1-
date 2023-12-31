package com.pm06.product_web_app.controllers;

import java.io.IOException;
import java.sql.Connection;

import com.pm06.product_web_app.models.DBCrud;
import com.pm06.product_web_app.models.MySQLConnector;
import com.pm06.product_web_app.models.Product;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/createProduct")
public class CreateProductServletController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/CreateProductView.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //1. lay du lieu trong browser
       String code = req.getParameter("code");
       String name = req.getParameter("name");
       String priceStr = req.getParameter("price");
       double price = 0;
       try{
        price= Double.parseDouble(priceStr);
       }catch(Exception exception){
        exception.printStackTrace();
       }
       //2. lay du lieu trong product
       Product product = new Product(code, name, price);

       //3. ket noi CSDL
       Connection conn = MySQLConnector.getMySQLConnection();
       //4. them doi tuong product vao CSDL
       DBCrud.addProduct(conn, product);
       //5. close dong ket noi
       MySQLConnector.closeConnection(conn);
       //6. di chuyen sang productlistview.jsp
       resp.sendRedirect(req.getContextPath()+"/productList");

    }

}
