package com.example.semanticapp;

import com.example.semanticapp.jena.ModelResource;
import com.example.semanticapp.jena.RDF;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ResourceController", urlPatterns = {"/resource"})
public class ResourceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getContextPath();
        String personName = request.getParameter("name");
        String personSurname = request.getParameter("surname");
        ModelResource.create(personName, personSurname);

        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ResourceController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResourceController at " + request.getContextPath() + "</h1>");
            out.println("<p>Resource created </p>");

            RDF.model.write(out);

            out.println("<p> <a href = \""+path+"\"> Home </a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
