package org.example.camera.servlet;


import org.example.camera.model.CamerasModel;
import org.example.camera.service.CameraService;
import org.example.servlet.ServletUtility;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {
        CameraServlet.Paths.CAMERA + "/*",
})
public class CameraServlet extends HttpServlet {
    private final CameraService cameraService;

    public static class Paths {
        public static final String CAMERA = "/api/camera";
    }

    public static class Patterns {
        public static final String CAMERAS = "^/?$";
        public static final String CAMERA = "^/[0-9]+/?$";
    }

    @Inject
    public CameraServlet(CameraService cameraService) {
        this.cameraService = cameraService;
    }



    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.CAMERA.equals(servletPath)) {
            if (path.matches(Patterns.CAMERA)) {
                //getCamera(req, resp);
            } else if (path.matches(Patterns.CAMERAS)) {
                getCameras(req, resp);
            }
            else{
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
/*
    private void getCamerar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<Camera> camera = cameraService.find(id);

        if (camera.isPresent()) {
            response.getWriter()
                    .write(jsonb.toJson(CameraModel.entityToDtoMapper().apply(camera.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
*/
    private void getCameras(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter()
                .write(jsonb.toJson(CamerasModel.entityToDtoMapper().apply(cameraService.findAll()).getCameras()));
    }
}