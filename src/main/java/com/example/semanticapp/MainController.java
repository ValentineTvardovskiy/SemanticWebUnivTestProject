package com.example.semanticapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainController", urlPatterns = {"/home"})
public class MainController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean subscribed = Boolean.parseBoolean(request.getParameter("subscribed"));

        String congratulationMessage;
        if (subscribed) {
            congratulationMessage = "Thanks for your subscription. We will send all the information to " + email;
        } else {
            congratulationMessage = "You are not pressed the subscription button so we will share your email: " + email +
                    " and password: " + password + " all over the internet. Haha!";
        }
        request.setAttribute("congratulationMessage", congratulationMessage);
        request.getRequestDispatcher("result.jsp").forward(request,response);
    }
}
