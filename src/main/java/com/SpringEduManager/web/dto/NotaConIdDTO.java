package com.SpringEduManager.web.dto;

public class NotaConIdDTO {
    private Long id;
    private Double nota;
    private java.util.Date fecha;

    public NotaConIdDTO() {
    }

    public NotaConIdDTO(Long id, Double nota, java.util.Date fecha) {
        this.id = id;
        this.nota = nota;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public java.util.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }
}
