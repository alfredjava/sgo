$(document).ready(function(){
	var moduloActual = new moduloBase();

	moduloActual.urlBase='cisterna';
    moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
    moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
    moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
    moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
    moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
    moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';
    moduloActual.ordenGrilla=[[ 2, 'asc' ]];

    moduloActual.columnasGrilla.push({ "data": 'id'}); //Target1
    moduloActual.columnasGrilla.push({ "data": 'placa'});//Target2
    moduloActual.columnasGrilla.push({ "data": 'tracto.placa'});//Target3
    moduloActual.columnasGrilla.push({ "data": 'transportista.razonSocial'});//Target4
    moduloActual.columnasGrilla.push({ "data": 'estado'});//Target5

    moduloActual.definicionColumnas.push({ "targets": 1,  "searchable": true, "orderable": true, "visible":false });
    moduloActual.definicionColumnas.push({ "targets": 2,  "searchable": true, "orderable": true, "visible":true });
    moduloActual.definicionColumnas.push({ "targets": 3,  "searchable": true, "orderable": true, "visible":true });
    moduloActual.definicionColumnas.push({ "targets": 4,  "searchable": true, "orderable": true, "visible":true });
    moduloActual.definicionColumnas.push({ "targets": 5,  "searchable": true, "orderable": true, "visible":true, "render": utilitario.formatearEstado });
	 
    moduloActual.reglasValidacionFormulario={ 
    	cmpPlaca: 				{ required: true, maxlength: 15 },
    	cmpIdTracto: 			{ required: true },
    	cmpIdTransportista: 	{ required: true },
    	cmpEstado: 				{ required: true },
    	cmpCantCompartimentos: 	{ required: true,rangelength: [1, 1], number: true },
    };
  
    moduloActual.mensajesValidacionFormulario={
	    cmpPlaca: 				{ required: "El campo es obligatorio", maxlength: "El campo debe contener 15 caracteres como m&aacute;ximo." },
	    cmpIdTracto: 			{ required: "El campo es obligatorio" },
	    cmpIdTransportista: 	{ required: "El campo es obligatorio" },
	    cmpEstado: 				{ required: "El campo es obligatorio" },
	    cmpCantCompartimentos: 	{ required: "El campo es obligatorio",
	    						  rangelength: "El campo debe contener 1 caracter",
	    						  number: "El campo solo debe contener caracteres num&eacute;ricos" },
    };

  moduloActual.inicializarCampos= function(){
    //Campos de formulario
	this.obj.cmpFiltroEstado=$("#cmpFiltroEstado");
	this.obj.cmpPlaca=$("#cmpPlaca");
    this.obj.cmpCantCompartimentos=$("#cmpCantCompartimentos");
    this.obj.cmpIdTracto=$("#cmpIdTracto");
    this.obj.cmpIdTracto.tipoControl="select2";
    this.obj.cmpSelect2Tracto=$("#cmpIdTracto").select2({
    	  ajax: {
    		    url: "./tracto/listar",
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
  		        return "<div class='select2-user-result'>" + registro.placa + "</div>";
  		    },
  		    templateSelection: function (registro) {
  		    	//console.log("templateSelection");
  		        return registro.placa || registro.text;
  		    },
    		minimumInputLength: 3
      });
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
  		    	//console.log("templateSelection");
  		        return registro.razonSocial || registro.text;
  		    },
    		minimumInputLength: 3
      });
    
    this.obj.cmpEstado=$("#cmpEstado");
    this.obj.cmpEstado.removeClass("disabled");
    
    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaPlaca=$("#vistaPlaca");
    this.obj.vistaCantCompartimentos=$("#vistaCantCompartimentos");
    this.obj.vistaIdTracto=$("#vistaIdTracto");
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
  
  moduloActual.inicializaCamposFormulario = function(){
  	console.log("inicializaCamposFormulario");
  	try {
  		$('#cmpPlaca').removeClass("error");
  		$('#cmpCantCompartimentos').removeClass("error");
  		
  		$('#cmpPlaca-error').text(null);
  		$('#cmpCantCompartimentos-error').text(null);
  		$('#cmpIdTracto-error').text(null);
  		$('#cmpIdTransportista-error').text(null);
  	}  catch(error){
       console.log(error.message);
    }
  },

  moduloActual.grillaDespuesSeleccionar= function(indice){
	  var referenciaModulo=this;
		var estadoRegistro = referenciaModulo.obj.datClienteApi.cell(indice,5).data();
		referenciaModulo.estadoRegistro=estadoRegistro;
		if (estadoRegistro == constantes.ESTADO_ACTIVO) {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-download"></i>'+constantes.TITULO_DESACTIVAR_REGISTRO);			
		} else {
			referenciaModulo.obj.btnModificarEstado.html('<i class="fa fa-cloud-upload"></i>'+constantes.TITULO_ACTIVAR_REGISTRO);			
		}
  };
  
  
  moduloActual.llenarFormulario = function(registro){
    this.idRegistro= registro.id;
    
   
    
    this.obj.cmpPlaca.val(registro.placa);
    this.obj.cmpCantCompartimentos.val(registro.cantidadCompartimentos);
    this.obj.cmpIdTracto.val(registro.idTracto);
    this.obj.cmpIdTransportista.val(registro.idTransportista);
    this.obj.cmpEstado.val(registro.estado);
    
    var elemento1=constantes.PLANTILLA_OPCION_SELECTBOX;
    elemento1 = elemento1.replace(constantes.ID_OPCION_CONTENEDOR,registro.tracto.id);
    elemento1 = elemento1.replace(constantes.VALOR_OPCION_CONTENEDOR,registro.tracto.placa);
    this.obj.cmpIdTracto.empty().append(elemento1).val(registro.tracto.id).trigger('change');
    
    var elemento2=constantes.PLANTILLA_OPCION_SELECTBOX;
    elemento2 = elemento2.replace(constantes.ID_OPCION_CONTENEDOR,registro.transportista.id);
    elemento2 = elemento2.replace(constantes.VALOR_OPCION_CONTENEDOR,registro.transportista.razonSocial);
    this.obj.cmpIdTransportista.empty().append(elemento2).val(registro.transportista.id).trigger('change');
  };

  moduloActual.llenarDetalles = function(registro){
    this.idRegistro= registro.id;
    this.obj.vistaId.text(registro.id);
    this.obj.vistaPlaca.text(registro.placa);
    this.obj.vistaCantCompartimentos.text(registro.cantidadCompartimentos);
    this.obj.vistaIdTracto.text(registro.tracto.placa);
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
    eRegistro.placa = referenciaModulo.obj.cmpPlaca.val().toUpperCase();
    eRegistro.cantidadCompartimentos = parseInt(referenciaModulo.obj.cmpCantCompartimentos.val());
    eRegistro.idTracto = referenciaModulo.obj.cmpIdTracto.val();
    eRegistro.idTransportista = referenciaModulo.obj.cmpIdTransportista.val();
    eRegistro.estado = referenciaModulo.obj.cmpEstado.val();
    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
  moduloActual.inicializar();
});