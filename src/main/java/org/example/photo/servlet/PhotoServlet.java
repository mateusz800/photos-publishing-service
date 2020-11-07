package org.example.photo.servlet;

import org.example.photo.entity.Photo;
import org.example.photo.models.PhotosModel;
import org.example.photo.service.PhotoService;
import org.example.servlet.ServletUtility;
import org.example.user.servlet.UserServlet;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {
        PhotoServlet.Paths.PHOTO + "/*",
})
public class PhotoServlet extends HttpServlet {
    private final PhotoService service;

    private final Jsonb jsonb = JsonbBuilder.create();

    public static class Paths {
        public static final String PHOTO = "/api/photo";
    }

    public static class Patterns {
        public static final String PHOTOS = "^/?$";
        public static final String PHOTO = "^/[0-9]+/?$";
    }

    @Inject
    public PhotoServlet(PhotoService service){
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.PHOTO.equals(servletPath)) {
            if (path.matches(UserServlet.Patterns.USER)) {
                getPhoto(req, resp);
            } else if (path.matches(UserServlet.Patterns.USERS)) {
                getPhotos(req, resp);
            }
            else{
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private void getPhoto(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<Photo> photo = service.find(id);

        if (photo.isPresent() && photo.get().getImage() != null) {
            //Type should be stored in the database but in this project we assume everything to be png.
            resp.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
            resp.setContentLength(photo.get().getImage().length);
            resp.getOutputStream().write(photo.get().getImage());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getPhotos(HttpServletRequest req, HttpServletResponse  resp) throws IOException {
        resp.getWriter()
                .write(jsonb.toJson(PhotosModel.entityToDtoMapper().apply(service.findAll()).getPhotos()));
    }
}
