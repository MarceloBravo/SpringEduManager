package com.SpringEduManager.web.dto;

public class CursoNotasDTO {
    Long id;
    EstudianteDTO estudiante;
    CursoDTO curso;
    NotaConIdDTO nota1;
    NotaConIdDTO nota2;
    NotaConIdDTO nota3;
    NotaConIdDTO nota4;
    NotaConIdDTO nota5;
    NotaConIdDTO nota6;
    NotaConIdDTO nota7;
    NotaConIdDTO nota8;
    NotaConIdDTO nota9;
    NotaConIdDTO nota10;

    public CursoNotasDTO() {
    }
    
    public CursoNotasDTO(Long id, EstudianteDTO estudiante, CursoDTO curso, NotaConIdDTO nota1, NotaConIdDTO nota2, NotaConIdDTO nota3, NotaConIdDTO nota4, NotaConIdDTO nota5, NotaConIdDTO nota6, NotaConIdDTO nota7, NotaConIdDTO nota8, NotaConIdDTO nota9, NotaConIdDTO nota10) {
        this.id = id;
        this.estudiante = estudiante;
        this.curso = curso;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.nota5 = nota5;
        this.nota6 = nota6;
        this.nota7 = nota7;
        this.nota8 = nota8;
        this.nota9 = nota9;
        this.nota10 = nota10;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public EstudianteDTO getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstudianteDTO estudiante) {
        this.estudiante = estudiante;
    }

    public CursoDTO getCurso() {
        return curso;
    }

    public void setCurso(CursoDTO curso) {
        this.curso = curso;
    }

    public NotaConIdDTO getNota1() {
        return nota1;
    }

    public void setNota1(NotaConIdDTO nota1) {
        this.nota1 = nota1;
    }

    public NotaConIdDTO getNota2() {
        return nota2;
    }

    public void setNota2(NotaConIdDTO nota2) {
        this.nota2 = nota2;
    }

    public NotaConIdDTO getNota3() {
        return nota3;
    }

    public void setNota3(NotaConIdDTO nota3) {
        this.nota3 = nota3;
    }

    public NotaConIdDTO getNota4() {
        return nota4;
    }

    public void setNota4(NotaConIdDTO nota4) {
        this.nota4 = nota4;
    }

    public NotaConIdDTO getNota5() {
        return nota5;
    }

    public void setNota5(NotaConIdDTO nota5) {
        this.nota5 = nota5;
    }

    public NotaConIdDTO getNota6() {
        return nota6;
    }

    public void setNota6(NotaConIdDTO nota6) {
        this.nota6 = nota6;
    }

    public NotaConIdDTO getNota7() {
        return nota7;
    }

    public void setNota7(NotaConIdDTO nota7) {
        this.nota7 = nota7;
    }

    public NotaConIdDTO getNota8() {
        return nota8;
    }

    public void setNota8(NotaConIdDTO nota8) {
        this.nota8 = nota8;
    }

    public NotaConIdDTO getNota9() {
        return nota9;
    }

    public void setNota9(NotaConIdDTO nota9) {
        this.nota9 = nota9;
    }

    public NotaConIdDTO getNota10() {
        return nota10;
    }

    public void setNota10(NotaConIdDTO nota10) {
        this.nota10 = nota10;
    }

}
