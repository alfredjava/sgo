$(document).ready(function(){
  var moduloActual = new moduloBase();  
  moduloActual.urlBase='cliente';
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
  moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
  moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';
  moduloActual.ordenGrilla=[[ 2, 'asc' ]];
  //Agregar columnas a la grilla
  moduloActual.columnasGrilla.push({ "data": 'id'}); //Target1
  moduloActual.columnasGrilla.push({ "data": 'nombreCorto'});//Target2
  moduloActual.columnasGrilla.push({ "data": 'razonSocial'});//Target3
  moduloActual.columnasGrilla.push({ "data": 'ruc'});//Target4
  moduloActual.columnasGrilla.push({ "data": 'estado'});//Target5

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
	"orderable": true,
	"visible":true,
    "render": utilitario.formatearEstado
});
  
  moduloActual.reglasValidacionFormulario={
	cmpRazonSocial: {
		required: true,
		maxlength: 150
	},
	cmpNombreCorto: {
		required: true,
		maxlength: 20
	},
	cmpRuc: {
		required: true,
		rangelength: [11, 11],
		number: true
	}
  };
  
  moduloActual.mensajesValidacionFormulario={
	cmpRazonSocial: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 150 caracteres como m&aacute;ximo."
	},
	cmpNombreCorto: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 20 caracteres como m&aacute;ximo."
	},
	cmpRuc: {
		required: "El campo es obligatorio",
		rangelength: "El campo debe contener 11 caracteres",
		number: "El campo solo debe contener caracteres num&eacute;ricos"
	}
  };
  
  moduloActual.inicializarCampos= function(){
    //Campos de formulario
    this.obj.cmpRazonSocial=$("#cmpRazonSocial");
    this.obj.cmpNombreCorto=$("#cmpNombreCorto");
    this.obj.cmpRuc=$("#cmpRuc");
    this.obj.cmpRuc.inputmask("99999999999");
    this.obj.cmpEstado=$("#cmpEstado");
    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaRazonSocial=$("#vistaRazonSocial");
    this.obj.vistaNombreCorto=$("#vistaNombreCorto");
    this.obj.vistaRuc=$("#vistaRuc");
    this.obj.vistaEstado=$("#vistaEstado");
    this.obj.vistaCreadoEl=$("#vistaCreadoEl");
    this.obj.vistaCreadoPor=$("#vistaCreadoPor");
    this.obj.vistaActualizadoEl=$("#vistaActualizadoEl");
    this.obj.vistaActualizadoPor=$("#vistaActualizadoPor");	
    this.obj.vistaIPCreacion=$("#vistaIPCreacion");
    this.obj.vistaIPActualizacion=$("#vistaIPActualizacion");	
  };

  moduloActual.grillaDespuesSeleccionar= function(indice){
	  var referenciaModulo=this;
		var estadoRegistro = referenciaModulo.obj.datClienteApi.cell(indice,5).data();
		referenciaModulo.estadoRegistro=estadoRegistro;
		if (estadoRegistro == constantes.ESTADO_ACTIVO) {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-download"></i> '+constantes.TITULO_DESACTIVAR_REGISTRO);			
		} else {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-upload"></i>  '+constantes.TITULO_ACTIVAR_REGISTRO);			
		}
  };

  moduloActual.llenarFormulario = function(registro){
    this.idRegistro= registro.id;
    this.obj.cmpRazonSocial.val(registro.razonSocial);
    this.obj.cmpNombreCorto.val(registro.nombreCorto);
    this.obj.cmpEstado.val(registro.estado);
    this.obj.cmpRuc.val(registro.ruc);	
  };

  moduloActual.llenarDetalles = function(registro){
    this.idRegistro= registro.id;
    this.obj.vistaId.text(registro.id);
    this.obj.vistaRazonSocial.text(registro.razonSocial);
    this.obj.vistaNombreCorto.text(registro.nombreCorto);
    this.obj.vistaRuc.text(registro.ruc);
    this.obj.vistaEstado.text(utilitario.formatearEstado(registro.estado));
    this.obj.vistaCreadoEl.text(registro.fechaCreacion);
    this.obj.vistaCreadoPor.text(registro.usuarioCreacion);
    this.obj.vistaActualizadoEl.text(registro.fechaActualizacion);
    this.obj.vistaActualizadoPor.text(registro.usuarioActualizacion);
    this.obj.vistaIPCreacion.text(registro.ipCreacion);
    this.obj.vistaIPActualizacion.text(registro.ipActualizacion);
  };

  moduloActual.recuperarValores = function(registro){
    var eRegistro = {};
    var referenciaModulo=this;
    try {
	    eRegistro.id = parseInt(referenciaModulo.idRegistro);
	    eRegistro.razonSocial = referenciaModulo.obj.cmpRazonSocial.val().toUpperCase();
	    eRegistro.nombreCorto = referenciaModulo.obj.cmpNombreCorto.val().toUpperCase();
	    eRegistro.estado = referenciaModulo.obj.cmpEstado.val();
	    eRegistro.ruc = parseInt(referenciaModulo.obj.cmpRuc.val());
    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
  moduloActual.inicializar();
});

