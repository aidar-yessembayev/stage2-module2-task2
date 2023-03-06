package com.example.servlet;

import com.example.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("--- In login get section ---");

        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login.jsp");

        } else {
            response.sendRedirect("/user/hello.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("--- In login post section ---");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Users users = Users.getInstance();
        List<String> allUsers = users.getUsers();

        boolean rightLogin = allUsers.contains(login);
        boolean rightPassword = (password != null) && (!password.trim().isEmpty());

        HttpSession session = request.getSession();

        if (rightLogin && rightPassword){
            session.setAttribute("user", login);

            try {
                response.sendRedirect("/user/hello.jsp");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
