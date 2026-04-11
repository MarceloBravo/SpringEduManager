package com.SpringEduManager.web.dto;

/**
 * Clase que representa un objeto de transferencia de datos para la paginación.
 */
public class PaginationDTO {
    /**
     * Número de enlaces de paginación a mostrar.
     */
    private int cantEnlaces = 5;
    /**
     * Página de inicio del conjunto de enlaces.
     */
    private int inicio = 1;
    /**
     * Página de fin del conjunto de enlaces.
     */
    private int fin = 1;
    /**
     * Número total de registros.
     */
    private Integer totReg;
    /**
     * Número total de páginas.
     */
    private Integer totPag;
    /**
     * Página actual.
     */
    private int paginaActual = 1;
    /**
     * Número de filas por página.
     */
    private Integer filas = 10;
    /**
     * URL base para los enlaces de paginación.
     */
    private String url = "";
    

    /**
     * Constructor por defecto.
     */
    public PaginationDTO() {
    }

    /**
     * Obtiene el número total de registros.
     *
     * @return el número total de registros
     */
    public Integer getTotReg() {
        return totReg;
    }

    /**
     * Establece el número total de registros.
     *
     * @param totReg el número total de registros
     */
    public void setTotReg(Integer totReg) {
        this.totReg = totReg;
    }

    /**
     * Obtiene el número total de páginas.
     *
     * @return el número total de páginas
     */
    public Integer getTotPag() {
        return totPag;
    }

    /**
     * Establece el número total de páginas.
     *
     * @param totPag el número total de páginas
     */
    public void setTotPag(Integer totPag) {
        this.totPag = totPag;
    }

    /**
     * Obtiene la URL base para los enlaces de paginación.
     *
     * @return la URL base
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL base para los enlaces de paginación.
     *
     * @param url la URL base
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene el número de enlaces de paginación a mostrar.
     *
     * @return el número de enlaces de paginación
     */
    public int getCantEnlaces(){
        return this.cantEnlaces;
    }

    
    /**
     * Obtiene el número de filas por página.
     *
     * @return el número de filas por página
     */
    public Integer getFilas(){
        return this.filas;
    }


    /**
     * Obtiene la página de inicio del conjunto de enlaces.
     *
     * @return la página de inicio
     */
    public int getInicio(){
        return this.inicio;
    }

    /**
     * Obtiene la página de fin del conjunto de enlaces.
     *
     * @return la página de fin
     */
    public int getFin(){
        return this.fin;
    }

    /**
     * Obtiene la página actual.
     *
     * @return la página actual
     */
    public int getPaginaActual(){
        return this.paginaActual;
    }
    
    /**
     * Calcula los valores de paginación.
     *
     * @param desde el registro inicial
     * @param filas el número de filas por página
     */
    public void CalcularPaginas(Integer desde, Integer filas){
        int radio = 2;
        this.filas = filas;
        totPag = (totReg / filas) % 2 == 0 ? (totReg / filas) + 1 : (totReg / filas) + 2;
        this.paginaActual = (desde / filas) + 1;
        this.inicio = Math.max(1, paginaActual - radio);
        this.fin = Math.min(totPag, paginaActual + radio);

        // Ajuste para mostrar siempre 5 si hay suficientes páginas
        if (fin - inicio + 1 < cantEnlaces) {
            if (paginaActual <= radio) {
                // Estamos cerca del comienzo
                fin = Math.min(totPag, cantEnlaces);
            } else {
                // Estamos cerca del final
                inicio = Math.max(1, totPag - cantEnlaces + 1);
            }
        }

    }




}
