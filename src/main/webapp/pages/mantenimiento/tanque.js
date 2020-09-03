$(document).ready(function(){
  var moduloActual = new moduloBase();
  
  moduloActual.urlBase='tanque';
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
  moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
  moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';  
  moduloActual.ordenGrilla=[[ 2, 'asc' ]];
  
  moduloActual.columnasGrilla.push({ "data": 'id'});
  moduloActual.columnasGrilla.push({ "data": 'descripcion'});
  moduloActual.columnasGrilla.push({ "data": 'volumenTotal'});
  moduloActual.columnasGrilla.push({ "data": 'volumenTrabajo'});
  moduloActual.columnasGrilla.push({ "data": 'estacion.nombre'});
  moduloActual.columnasGrilla.push({ "data": 'estado'});
    
  moduloActual.definicionColumnas.push({
	  	"targets": 1,
	    "searchable": true,
	    "orderable": true,
	    "visible":false
	  });
	  moduloActual.definicionColumnas.push({
	  	"targets": 2,
	    "searchable": true,
	    "orderable": true,
	    "visible":true
	  });
	  moduloActual.definicionColumnas.push({
	  	"targets": 3,
	    "searchable": true,
	    "orderable": true,
	    "visible":true
	});
	  moduloActual.definicionColumnas.push({
	  	"targets": 4,
	    "searchable": true,
	    "orderable": true,
	    "visible":true
	});
	  moduloActual.definicionColumnas.push({
		  	"targets": 5,
		    "searchable": true,
		    "orderable": true,
		    "visible":true
		});
	  moduloActual.definicionColumnas.push({
	  	"targets": 6,
		"orderable": true,
		"visible":true,
	    "render": utilitario.formatearEstado
	});
  
  moduloActual.reglasValidacionFormulario={
	cmpDescripcion: {
		required: true,
		maxlength: 20
	},
	cmpVolumenTotal: {
		required: true,
		maxlength: 10
	},
	cmpVolumenTrabajo: {
		required: true,
		maxlength: 10
	},
	cmpIdEstacion: {
		required: true
	},
	cmpIdEstado: {
		required: true
	}
  };
  
  moduloActual.mensajesValidacionFormulario={
	cmpDescripcion: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 20 caracteres como m&aacute;ximo."
			
	},
	cmpVolumenTotal: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 10 caracteres como m&aacute;ximo."
	},
	cmpVolumenTrabajo: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 10 caracteres como m&aacute;ximo."
	},
	cmpIdEstacion: {
		required: "El campo es obligatorio"
	},
	cmpIdEstado: {
		required: "El campo es obligatorio"
	}
  };
  moduloActual.inicializarCampos= function(){
    //Campos de formulario
	this.obj.cmpDescripcion=$("#cmpDescripcion"); 
    this.obj.cmpVolumenTotal=$("#cmpVolumenTotal");
    this.obj.cmpVolumenTrabajo=$("#cmpVolumenTrabajo");
    this.obj.cmpIdEstacion=$("#cmpIdEstacion");
    
    this.obj.cmpIdEstacion.tipoControl="select2";
    this.obj.cmpSelect2Estacion=$("#cmpIdEstacion").select2();
    
    this.obj.cmpEstado=$("#cmpEstado");
    this.obj.cmpSincronizadoEl=$("#cmpSincronizadoEl");
    this.obj.cmpFechaReferencia=$("#cmpFechaReferencia");
    this.obj.cmpCodigoReferencia=$("#cmpCodigoReferencia");
    //Campos de vista
    this.obj.vistaVolumenTotal=$("#vistaVolumenTotal");
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaDescripcion=$("#vistaDescripcion");
    this.obj.vistaVolumenTrabajo=$("#vistaVolumenTrabajo");
    this.obj.vistaIdEstacion=$("#vistaIdEstacion");
    this.obj.vistaEstado=$("#vistaEstado");
    this.obj.vistaSincronizadoEl=$("#vistaSincronizadoEl");
    this.obj.vistaFechaReferencia=$("#vistaFechaReferencia");
    this.obj.vistaCodigoReferencia=$("#vistaCodigoReferencia");
    this.obj.vistaCreadoEl=$("#vistaCreadoEl");
    this.obj.vistaCreadoPor=$("#vistaCreadoPor");
    this.obj.vistaActualizadoPor=$("#vistaActualizadoPor");
    this.obj.vistaActualizadoEl=$("#vistaActualizadoEl");
    this.obj.vistaIpCreacion=$("#vistaIpCreacion");
    this.obj.vistaIpActualizacion=$("#vistaIpActualizacion");
    
  };

  moduloActual.grillaDespuesSeleccionar= function(indice){
	  var referenciaModulo=this;
		var estadoRegistro = referenciaModulo.obj.datClienteApi.cell(indice,6).data();
		referenciaModulo.estadoRegistro=estadoRegistro;
		if (estadoRegistro == constantes.ESTADO_ACTIVO) {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-download"></i>'+constantes.TITULO_DESACTIVAR_REGISTRO);			
		} else {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-upload"></i>'+constantes.TITULO_ACTIVAR_REGISTRO);			
		}
  };
  
  moduloActual.llenarFormulario = function(registro){
    this.idRegistro= registro.id;
    this.obj.cmpVolumenTotal.val(registro.volumenTotal);
    this.obj.cmpDescripcion.val(registro.descripcion);
    this.obj.cmpVolumenTrabajo.val(registro.volumenTrabajo);
    this.obj.cmpEstado.val(registro.estado);
    this.obj.cmpSincronizadoEl.val(registro.sincronizadoEl);
    this.obj.cmpFechaReferencia.val(registro.fechaReferencia);
    this.obj.cmpCodigoReferencia.val(registro.codigoReferencia);
    
    var elemento=constantes.PLANTILLA_OPCION_SELECTBOX;
    elemento = elemento.replace(constantes.ID_OPCION_CONTENEDOR,registro.estacion.id);
    elemento = elemento.replace(constantes.VALOR_OPCION_CONTENEDOR,registro.estacion.nombre);
    this.obj.cmpIdEstacion.empty().append(elemento).val(registro.estacion.id).trigger('change');
    
  };

  moduloActual.llenarDetalles = function(registro){
    this.idRegistro= registro.id;
    this.obj.vistaId.text(registro.id);
    this.obj.vistaVolumenTotal.text(registro.volumenTotal);
    this.obj.vistaDescripcion.text(registro.descripcion);
    this.obj.vistaVolumenTrabajo.text(registro.volumenTrabajo);
    this.obj.vistaIdEstacion.text(registro.estacion.nombre);
    this.obj.vistaEstado.text(utilitario.formatearEstado(registro.estado));
    this.obj.vistaSincronizadoEl.text(registro.sincronizadoEl);
    this.obj.vistaFechaReferencia.text(registro.fechaReferencia);
    this.obj.vistaCodigoReferencia.text(registro.codigoReferencia);
    this.obj.vistaCreadoEl.text(registro.fechaCreacion);
    this.obj.vistaCreadoPor.text(registro.usuarioCreacion);
    this.obj.vistaActualizadoEl.text(registro.fechaActualizacion);
    this.obj.vistaActualizadoPor.text(registro.usuarioActualizacion);
    this.obj.vistaIpCreacion.text(registro.ipCreacion);
    this.obj.vistaIpActualizacion.text(registro.ipActualizacion);
  };

  moduloActual.recuperarValores = function(registro){
    var eRegistro = {};
    var referenciaModulo=this;
    try {
    eRegistro.id = parseInt(referenciaModulo.idRegistro);
    eRegistro.descripcion = referenciaModulo.obj.cmpDescripcion.val().toUpperCase();
    eRegistro.volumenTotal = referenciaModulo.obj.cmpVolumenTotal.val();
    eRegistro.volumenTrabajo = referenciaModulo.obj.cmpVolumenTrabajo.val();
    eRegistro.idEstacion = referenciaModulo.obj.cmpIdEstacion.val();
    eRegistro.estado = referenciaModulo.obj.cmpEstado.val();
    eRegistro.sincronizadoEl = referenciaModulo.obj.cmpSincronizadoEl.val();
    eRegistro.fechaReferencia = referenciaModulo.obj.cmpFechaReferencia.val();
    eRegistro.codigoReferencia = referenciaModulo.obj.cmpCodigoReferencia.val();
    
    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
  moduloActual.inicializar();
});
