package com.salvador.apppdfs;

public enum NombresDirectorios
{
    NOMBRE_DIRECTORIO("PDFsNuevoIngreso"),NOMBRE_DOCUMENTO("PDF_NuevoIngreso"),ETIQUETA_ERROR("ERROR");

    public String texto;
    NombresDirectorios(String texto)
    {
        this.texto=texto;
    }
}
