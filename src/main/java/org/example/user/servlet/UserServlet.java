package org.example.user.servlet;


import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.entity.User;
import org.example.user.service.UserService;
import org.example.servlet.ServletUtility;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {
        UserServlet.Paths.USER + "/*",
})
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Inject
    public UserServlet(UserService userService) {
        this.userService = userService;
    }
    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static class Paths {
        public static final String USER = "/user";
    }

    public static class Patterns {
        public static final String USERS = "^/?$";
        public static final String USER = "^/[0-9]+/?$";
    }



    /**
     * JSON-B mapping object. According to open liberty documentation creating those is expensive. The JSON-B is only
     * one of many solutions. JSON strings can be build by hand {@link StringBuilder} or with JSON-P API. Both JSON-B
     * and JSON-P are part of Jakarta EE whereas JSON-B is newer standard.
     */
    private final Jsonb jsonb = JsonbBuilder.create();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.USER.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                getUser(req, resp);
                return;
            } else if (path.matches(Patterns.USERS)) {
                getUsers(req, resp);
                return;
            }
        }
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);

        if (user.isPresent()) {
            response.getWriter()
                    .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter()
                .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(userService.findAll())));
    }
}