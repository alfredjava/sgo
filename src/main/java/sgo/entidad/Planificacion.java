package sgo.entidad;
public class Planificacion extends EntidadBase {
	private int id_doperativo;
	private int id_planificacion;
	private int id_producto;
	private float volumen_propuesto;
	private float volumen_solicitado;
	private int cantidad_cisternas;
	private Producto producto;
	private String descProductoVolRequerido;

	public String getDescProductoVolRequerido() {
		if(this.getProducto() != null && this.getProducto().getNombre() != null){
		descProductoVolRequerido = this.getProducto().getNombre() + "(" + String.valueOf((int)this.getVolumenSolicitado()) + ")";
		} else {
			descProductoVolRequerido = "";
		}			
		return descProductoVolRequerido;
	}

	public void setDescProductoVolRequerido(String descProductoVolRequerido) {
		this.descProductoVolRequerido = descProductoVolRequerido;
	}

	public int getIdDoperativo() {
		return id_doperativo;
	}

	public void setIdDoperativo(int id_doperativo) {
		this.id_doperativo = id_doperativo;
	}

	public int getId() {
		return id_planificacion;
	}

	public void setId(int Id) {
		this.id_planificacion = Id;
	}

	public int getIdProducto() {
		return id_producto;
	}

	public void setIdProducto(int IdProducto) {
		this.id_producto = IdProducto;
	}

	public float getVolumenPropuesto() {
		return volumen_propuesto;
	}

	public void setVolumenPropuesto(float VolumenPropuesto) {
		this.volumen_propuesto = VolumenPropuesto;
	}

	public float getVolumenSolicitado() {
		return volumen_solicitado;
	}

	public void setVolumenSolicitado(float VolumenSolicitado) {
		this.volumen_solicitado = VolumenSolicitado;
	}

	public int getCantidadCisternas() {
		return cantidad_cisternas;
	}

	public void setCantidadCisternas(int CantidadCisternas) {
		this.cantidad_cisternas = CantidadCisternas;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}