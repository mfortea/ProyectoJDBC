package com.dual.JDBCEjercicio1;

import java.util.Date;

public class Coche {

	private String marca;
	private String modelo;
	private String matricula;
	private Date fecha_matriculacion;
	private String color;
	private boolean hibrido;
	private String precio;

	public Coche(String marca, String modelo, String matricula, Date fecha_matriculacion, String color, boolean hibrido,
			String precio) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.matricula = matricula;
		this.fecha_matriculacion = fecha_matriculacion;
		this.color = color;
		this.hibrido = hibrido;
		this.precio = precio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Date getFecha_matriculacion() {
		return fecha_matriculacion;
	}

	public void setFecha_matriculacion(Date fecha_matriculacion) {
		this.fecha_matriculacion = fecha_matriculacion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isHibrido() {
		return hibrido;
	}

	public void setHibrido(boolean hibrido) {
		this.hibrido = hibrido;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

}
