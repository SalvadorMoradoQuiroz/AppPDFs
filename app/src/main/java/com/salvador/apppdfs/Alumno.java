package com.salvador.apppdfs;

public class Alumno
{
    String id;
    String nombre;
    String carrera;
    String telefono;
    String status;

    public Alumno(String id, String carrera, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.carrera = carrera;
        this.telefono=telefono;
        this.status="";
    }
    public Alumno(String id, String nombre, String carrera, String telefono, String status) {
        this.id = id;
        this.nombre = nombre;
        this.carrera = carrera;
        this.telefono=telefono;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
