package org.example.user.servlet;

import lombok.SneakyThrows;
import org.example.servlet.ServletUtility;
import org.example.user.entity.User;
import org.example.user.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {
        AvatarServlet.Paths.AVATAR + "/*",
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {
    private UserService service;

    @Inject
    public AvatarServlet(UserService service) {
        this.service = service;
    }

    public static class Paths {
        public static final String AVATAR = "/api/avatar";
    }

    public static class Patterns {
        public static final String AVATAR = "^/[0-9]+/?$";
    }

    public static class Parameters {
        public static final String AVATAR = "avatar";
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                getAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                removeAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                addAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                changeAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent() && user.get().getAvatar() != null) {
            //Type should be stored in the database but in this project we assume everything to be png.
            resp.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
            resp.setContentLength(user.get().getAvatar().length);
            resp.getOutputStream().write(user.get().getAvatar());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void removeAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            service.removeAvatar(user.get().getId());
            resp.getWriter().println("Avatar was removed");
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @SneakyThrows
    private void addAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            if(user.get().getAvatar() == null){
                Part avatar = req.getPart(Parameters.AVATAR);
                if (avatar != null) {
                    service.updateAvatar(id, avatar.getInputStream());
                }
                //resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("Avatar was added");
            }
            else{
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void changeAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            if(user.get().getAvatar() != null){
                Part avatar = req.getPart(Parameters.AVATAR);
                if (avatar != null) {
                    service.updateAvatar(id, avatar.getInputStream());
                }
                //resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("Avatar was changed");
            }
            else{
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
