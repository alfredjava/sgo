$(document).ready(function(){
  var moduloActual = new moduloBase();
  
  moduloActual.urlBase='conductor';
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
  moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
  moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';
  moduloActual.ordenGrilla=[[ 2, 'asc' ]];
  
  moduloActual.columnasGrilla.push({ "data": 'id'}); //Target1
  moduloActual.columnasGrilla.push({ "data": 'brevete'});//Target2
  moduloActual.columnasGrilla.push({ "data": 'apellidos'});//Target3
  moduloActual.columnasGrilla.push({ "data": 'nombres'});//Target4
  moduloActual.columnasGrilla.push({ "data": 'dni'});//Target5
  moduloActual.columnasGrilla.push({ "data": 'fechaNacimiento'});//Target6
  moduloActual.columnasGrilla.push({ "data": 'transportista.razonSocial'});//Target7
  moduloActual.columnasGrilla.push({ "data": 'estado'});//Target8
  
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
	    "searchable": true,
	    "orderable": true,
	    "visible":true//,
	    //"render": utilitario.formatearStringToDate2
		});
	  moduloActual.definicionColumnas.push({
	  	"targets": 7,
	    "searchable": true,
	    "orderable": true,
	    "visible":true
		});
	  
	  moduloActual.definicionColumnas.push({
	  	"targets": 8,
		"orderable": true,
		"visible":true,
	    "render": utilitario.formatearEstado
	});
	  
  moduloActual.reglasValidacionFormulario={
	cmpBrevete: {
		required: true,
		maxlength: 15
	},
	cmpApellidos: {
		required: true,
		maxlength: 150
	},
	cmpNombres: {
		required: true,
		maxlength: 150
	},
	cmpDni: {
		required: true,
		number: true,
		maxlength: 8
	},
	cmpFechaNacimiento: {
		required: true,
		date: true
	},
	cmpIdTransportista: {
		required: true
	},
	cmpEstado: {
		required: true
	}
  };
  
  moduloActual.mensajesValidacionFormulario={
	cmpBrevete: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 15 caracteres como m&aacute;ximo.",
		text:"red"
	},
	cmpApellidos: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 150 caracteres como m&aacute;ximo."			
	},
	cmpNombres: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 150 caracteres como m&aacute;ximo."
	},
	cmpDni: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 8 caracteres como m&aacute;ximo.",
		number: "El campo solo debe contener caracteres num&eacute;ricos",
		color: "#dd4b39 !important"
	},
	cmpFechaNacimiento: {
		required: "El campo es obligatorio",
		date: "El campo solo debe contener caracteres de fecha",
	},
	cmpIdTransportista: {
		required: "El campo es obligatorio"
	},
	cmpEstado: {
		required: "El campo es obligatorio"
	}
  };
  
  moduloActual.inicializarCampos= function(){
    //Campos de formulario
    this.obj.cmpBrevete=$("#cmpBrevete");
    this.obj.cmpApellidos=$("#cmpApellidos");
    this.obj.cmpNombres=$("#cmpNombres");
    this.obj.cmpDni=$("#cmpDni");
    this.obj.cmpFechaNacimiento=$("#cmpFechaNacimiento");
    this.obj.cmpFechaNacimiento.inputmask(constantes.FORMATO_FECHA, 
            { 
                "placeholder": constantes.FORMATO_FECHA,
                onincomplete: function(){
                    $(this).val('');
                }
            }
        );
    this.obj.cmpIdTransportista=$("#cmpIdTransportista");
    this.obj.cmpIdTransportista.tipoControl="select2";
    
    this.obj.cmpSelect2Transportista=$("#cmpIdTransportista").select2({
  	  ajax: {
  		    url: "./transportista/listar",
  		    dataType: 'json',
  		    delay: 250,
  		    data: function (parametros) {
  		      return {
  		    	valorBuscado: parametros.term, // search term
  		        page: parametros.page,
  		        paginacion:0
  		      };
  		    },
  		    processResults: function (respuesta, pagina) {
  		    	console.log("processResults");
  		    	var resultados= respuesta.contenido.carga;
  		    	console.log(resultados);
  		      return { results: resultados};
  		    },
  		    cache: true
  		  },
  		language: "es",
  		escapeMarkup: function (markup) { return markup; },
  		templateResult: function (registro) {
  			console.log("templateResult");
  			if (registro.loading) {
  				return registro.text;
  			}		    	
		        return "<div class='select2-user-result'>" + registro.razonSocial + "</div>";
		    },
		    templateSelection: function (registro) {
		    	console.log("templateSelection");
		        return registro.razonSocial || registro.text;
		    },
  		minimumInputLength: 3
    });
    
    this.obj.cmpEstado=$("#cmpEstado");
    this.obj.cmpSincronizadoEl=$("#cmpSincronizadoEl");
    this.obj.cmpFechaReferencia=$("#cmpFechaReferencia");
    this.obj.cmpCodigoReferencia=$("#cmpCodigoReferencia");
    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaBrevete=$("#vistaBrevete");
    this.obj.vistaApellidos=$("#vistaApellidos");
    this.obj.vistaNombres=$("#vistaNombres");
    this.obj.vistaDni=$("#vistaDni");
    this.obj.vistaFechaNacimiento=$("#vistaFechaNacimiento");
    this.obj.vistaIdTransportista=$("#vistaIdTransportista");
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
		var estadoRegistro = referenciaModulo.obj.datClienteApi.cell(indice,8).data();
		referenciaModulo.estadoRegistro=estadoRegistro;
		if (estadoRegistro == constantes.ESTADO_ACTIVO) {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-download"></i>'+constantes.TITULO_DESACTIVAR_REGISTRO);			
		} else {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-upload"></i>'+constantes.TITULO_ACTIVAR_REGISTRO);			
		}
  };
  
  moduloActual.llenarFormulario = function(registro){
    this.idRegistro= registro.id;
    this.obj.cmpBrevete.val(registro.brevete);
    this.obj.cmpApellidos.val(registro.apellidos);
    this.obj.cmpNombres.val(registro.nombres);
    this.obj.cmpDni.val(registro.dni);
    this.obj.cmpFechaNacimiento.val(registro.fechaNacimiento);
    this.obj.cmpIdTransportista.val(registro.idTransportista);
    this.obj.cmpEstado.val(registro.estado);
    this.obj.cmpSincronizadoEl.val(registro.sincronizadoEl);
    this.obj.cmpFechaReferencia.val(registro.fechaReferencia);
    this.obj.cmpCodigoReferencia.val(registro.codigoReferencia);
    
    var elemento=constantes.PLANTILLA_OPCION_SELECTBOX;
    elemento = elemento.replace(constantes.ID_OPCION_CONTENEDOR,registro.transportista.id);
    elemento = elemento.replace(constantes.VALOR_OPCION_CONTENEDOR,registro.transportista.razonSocial);
    this.obj.cmpIdTransportista.empty().append(elemento).val(registro.transportista.id).trigger('change');
  };

  moduloActual.llenarDetalles = function(registro){
    this.idRegistro= registro.id;
    this.obj.vistaId.text(registro.id);
    this.obj.vistaBrevete.text(registro.brevete);
    this.obj.vistaApellidos.text(registro.apellidos);
    this.obj.vistaNombres.text(registro.nombres);
    this.obj.vistaDni.text(registro.dni);
    this.obj.vistaFechaNacimiento.text(registro.fechaNacimiento);
    this.obj.vistaIdTransportista.text(registro.transportista.razonSocial);
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
    eRegistro.brevete = referenciaModulo.obj.cmpBrevete.val().toUpperCase();
    eRegistro.apellidos = referenciaModulo.obj.cmpApellidos.val().toUpperCase();
    eRegistro.nombres = referenciaModulo.obj.cmpNombres.val().toUpperCase();
    eRegistro.dni = referenciaModulo.obj.cmpDni.val();
    eRegistro.fechaNacimiento= utilitario.formatearStringToDate(referenciaModulo.obj.cmpFechaNacimiento.val());
    eRegistro.idTransportista = referenciaModulo.obj.cmpIdTransportista.val();
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
