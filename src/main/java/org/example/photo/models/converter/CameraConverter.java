package org.example.photo.models.converter;

/*
@FacesConverter(forClass = CameraModel.class, managed = true)
public class CameraConverter implements Converter<CameraModel> {
    private CameraService service;

    @Inject
    public CameraConverter(CameraService service){
        this.service = service;
    }


    @Override
    public CameraModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        System.out.println("Converter");
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Camera> camera = service.find(Long.valueOf(value));
        return new CameraModel();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, CameraModel camera) {
        return camera == null ? "" : camera.getBrand().toString();
    }
}

 */
