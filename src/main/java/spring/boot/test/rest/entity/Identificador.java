package spring.boot.test.rest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.sun.istack.NotNull;

@Entity
@Table(name="identificador")
public class Identificador implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	@Column(name="id", unique = true)
	private Long id;
	
	@NotEmpty(message = "No Puede estar vacio")
	@Size(min=4, max=12, message = "El tamaño tiene que estar entre 4 y 100 caracteres")
	@Column(nullable = false)
	private String descripcion;
	
	@Temporal(TemporalType.DATE)	
	private Date fechaCreacion;
	
	@NotEmpty(message = "No Puede estar vacio")
	@Column(nullable = false)
	private boolean vigente;
		
	public Identificador() {

	}

	@PrePersist
	public void prePersiste() {
		fechaCreacion=new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}
	
	

}
