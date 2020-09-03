$(document).ready(function(){
  var moduloActual = new moduloBase();  
  moduloActual.urlBase='operacion';
  moduloActual.SEPARADOR_MILES = ","
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
  moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
  moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';
  moduloActual.ordenGrilla=[[ 2, 'asc' ]];
  //Agregar columnas a la grilla
  moduloActual.columnasGrilla.push({ "data": 'id'}); //Target1
  moduloActual.columnasGrilla.push({ "data": 'nombre'});//Target2
  moduloActual.columnasGrilla.push({ "data": 'cliente.razonSocial'});//Target3
  moduloActual.columnasGrilla.push({ "data": 'referenciaPlantaRecepcion'});//Target4
  moduloActual.columnasGrilla.push({ "data": 'referenciaDetinatarioMercaderia'});//Target5
  moduloActual.columnasGrilla.push({ "data": 'estado'});//Target6
  moduloActual.definicionColumnas.push({
  "targets" : 1,
  "searchable" : true,
  "orderable" : true,
  "visible" : false
  });
	moduloActual.definicionColumnas.push({
		"targets" : 2,
		"searchable" : true,
		"orderable" : true,
		"visible" : true
	});
	moduloActual.definicionColumnas.push({
		"targets" : 3,
		"searchable" : true,
		"orderable" : true,
		"visible" : true
	});
	moduloActual.definicionColumnas.push({
		"targets" : 4,
		"searchable" : true,
		"orderable" : true,
		"visible" : true
	});
	moduloActual.definicionColumnas.push({
		"targets" : 5,
		"searchable" : true,
		"orderable" : true,
		"visible" : true
	});
	moduloActual.definicionColumnas.push({
		"targets" : 6,
		"orderable" : true,
		"visible" : true,
		"render" : utilitario.formatearEstado
	});
  
  moduloActual.reglasValidacionFormulario={
	cmpNombre: {
		required: true,
		maxlength: 150
	},
	cmpIdRefPlantaRecepcion: {
		required: true,
		maxlength: 20
	},
	cmpIdRefDestMercaderia: {
		required: true,
		maxlength: 20
	},
	
	cmpIdCliente: "required",
	cmpVolumenPromedioCisterna: "required",
	cmpSincronizadoEl: "required"
  };
  
  moduloActual.mensajesValidacionFormulario={
    cmpNombre: {
    required: "El campo es obligatorio",
    maxlength: "El campo debe contener 150 caracteres como m&aacute;ximo."
    },
    cmpIdCliente: {
    required: "El campo es obligatorio",
    },
    cmpIdRefDestMercaderia:{
    required: "El campo es obligatorio",
    maxlength: "El campo debe contener 20 caracteres como m&aacute;ximo."
    },
    cmpIdRefPlantaRecepcion :{
    required: "El campo es obligatorio",
    maxlength: "El campo debe contener 20 caracteres como m&aacute;ximo."
    },
    cmpSincronizadoEl: {
    required: "El campo es obligatorio",
    },
    cmpVolumenPromedioCisterna: {
    required: "El campo es obligatorio",
    }
  };
  moduloActual.inicializarCampos= function(){
    //Campos de formulario
    this.obj.cmpNombre=$("#cmpNombre");
    this.obj.cmpIdRefPlantaRecepcion=$("#cmpIdRefPlantaRecepcion");
    this.obj.cmpIdRefDestMercaderia=$("#cmpIdRefDestMercaderia");
    this.obj.cmpVolumenPromedioCisterna=$("#cmpVolumenPromedioCisterna");
    this.obj.cmpVolumenPromedioCisterna.inputmask('decimal', {digits: 2, groupSeparator:moduloActual.SEPARADOR_MILES,autoGroup:true,groupSize:3});
    this.obj.cmpSincronizadoEl=$("#cmpSincronizadoEl");
    this.obj.cmpIdCliente=$("#cmpIdCliente");
    this.obj.cmpIdCliente.tipoControl="select2";    
    this.obj.cmpSelect2Cliente=$("#cmpIdCliente").select2();   
    
    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaNombre=$("#vistaNombre");
    this.obj.vistaIdCliente=$("#vistaIdCliente");
    this.obj.vistaIdRefPlantaRecepcion=$("#vistaIdRefPlantaRecepcion");
    this.obj.vistaIdRefDestMercaderia=$("#vistaIdRefDestMercaderia");
    this.obj.vistaVolumenPromedioCisterna=$("#vistaVolumenPromedioCisterna");
    this.obj.vistaSincronizadoEl=$("#vistaSincronizadoEl");
    this.obj.vistaEstado=$("#vistaEstado");
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
    this.obj.cmpNombre.val(registro.nombre);
    this.obj.cmpIdRefPlantaRecepcion.val(registro.referenciaPlantaRecepcion);
    this.obj.cmpIdRefDestMercaderia.val(registro.referenciaDetinatarioMercaderia);
    this.obj.cmpVolumenPromedioCisterna.val(registro.volumenPromedioCisterna);
    this.obj.cmpSincronizadoEl.val(registro.sincronizadoEl);        
    var elemento=constantes.PLANTILLA_OPCION_SELECTBOX;
    elemento = elemento.replace(constantes.ID_OPCION_CONTENEDOR,registro.cliente.id);
    elemento = elemento.replace(constantes.VALOR_OPCION_CONTENEDOR,registro.cliente.razonSocial);
    this.obj.cmpIdCliente.empty().append(elemento).val(registro.cliente.id).trigger('change');
  };

  moduloActual.llenarDetalles = function(registro){
    this.idRegistro= registro.id;    
    this.obj.vistaId.text(registro.id);
    this.obj.vistaNombre.text(registro.nombre);
    this.obj.vistaIdCliente.text(registro.cliente.razonSocial); 
    this.obj.vistaIdRefPlantaRecepcion.val(registro.referenciaPlantaRecepcion);
    this.obj.vistaIdRefDestMercaderia.val(registro.referenciaDetinatarioMercaderia);
    this.obj.vistaVolumenPromedioCisterna.text(registro.volumenPromedioCisterna);
    this.obj.vistaSincronizadoEl.val(registro.sincronizadoEl);
    this.obj.vistaEstado.text(utilitario.formatearEstado(registro.estado));   
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
    eRegistro.nombre = referenciaModulo.obj.cmpNombre.val().toUpperCase();
    eRegistro.idCliente = referenciaModulo.obj.cmpIdCliente.val();
    eRegistro.referenciaPlantaRecepcion = referenciaModulo.obj.cmpIdRefPlantaRecepcion.val();
    eRegistro.referenciaDetinatarioMercaderia = referenciaModulo.obj.cmpIdRefDestMercaderia.val();
    eRegistro.volumenPromedioCisterna = parseFloat(referenciaModulo.obj.cmpVolumenPromedioCisterna.val().replace(moduloActual.SEPARADOR_MILES,""));   
    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
  moduloActual.inicializar();
});