$(document).ready(function(){
  var moduloActual = new moduloUsuario();
  
  moduloActual.urlBase='usuario';
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
  moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
  moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';
  moduloActual.URL_GUARDAR_AUTORIZACION = moduloActual.urlBase + '/crearAutorizaciones';
  moduloActual.URL_RECUPERAR_AUTORIZACION = moduloActual.urlBase + '/recuperarAutorizaciones';  
  
  moduloActual.ordenGrilla=[[ 2, 'asc' ]];
  //Agregar columnas a la grilla
  moduloActual.columnasGrilla.push({ "data": 'id'}); //Target1
  moduloActual.columnasGrilla.push({ "data": 'nombre'});//Target2
  moduloActual.columnasGrilla.push({ "data": 'identidad'});//Target3
  moduloActual.columnasGrilla.push({ "data": 'rol.nombre'});//Target4
  moduloActual.columnasGrilla.push({ "data": 'operacion.nombre'});//Target5
  moduloActual.columnasGrilla.push({ "data": 'estado'});//Target6
  
  moduloActual.definicionColumnas.push({ "targets": 1, "searchable": true, "orderable": true, "visible":false });
  moduloActual.definicionColumnas.push({ "targets": 2, "searchable": true, "orderable": true, "visible":true });
  moduloActual.definicionColumnas.push({ "targets": 3, "searchable": true, "orderable": true, "visible":true });
  moduloActual.definicionColumnas.push({ "targets": 4, "searchable": true, "orderable": true, "visible":true });
  moduloActual.definicionColumnas.push({ "targets": 5, "searchable": true, "orderable": true, "visible":true });
  moduloActual.definicionColumnas.push({ "targets": 6, "searchable": true, "orderable": true, "visible":true, "render": utilitario.formatearEstado });
  
  //esto para el dataTable secundario
  moduloActual.ordenGrillaSecundaria=[[ 2, 'asc' ]];
  //Agregar columnas a la grilla
  moduloActual.columnasGrillaSecundaria.push({ "data": 'id'});//Target1
  moduloActual.columnasGrillaSecundaria.push({ "data": 'nombre'});//Target2
  
  moduloActual.definicionColumnasSecundaria.push({ "targets": 1, "searchable": true, "orderable": true, "visible":false });
  moduloActual.definicionColumnasSecundaria.push({ "targets": 2, "searchable": true, "orderable": true, "visible":true });
  
  moduloActual.reglasValidacionFormulario={ 
      cmpNombre: 	{ required: true, maxlength: 16 },
	  cmpClave:  	{ required: true, maxlength: 64 },
	  cmpIdentidad: {  required: true, maxlength: 120 },
	  cmpZonaHoraria: { required: true, maxlength: 20 },
      cmpEmail: 	{ required: true, maxlength: 120 },
	  cmpIdRol: 	{ required: true},
	  cmpTipo: 		{ required: true},
	  cmpIdOperacion:{ required: true},
	  cmpEstado: 	{ required: true},
  };
	  
  moduloActual.mensajesValidacionFormulario={
      cmpNombre: { required: "El campo es obligatorio", maxlength: "El campo debe contener 16 caracteres como m&aacute;ximo."},
		cmpClave: {
			required: "El campo es obligatorio",
			maxlength: "El campo debe contener 64 caracteres como m&aacute;ximo."
		},
		cmpIdentidad: {
			required: "El campo es obligatorio",
			maxlength: "El campo debe contener 120 caracteres como m&aacute;ximo."
		},
		cmpZonaHoraria: {
			required: "El campo es obligatorio",
			maxlength: "El campo debe contener 20 caracteres como m&aacute;ximo."
		},
		cmpEmail: {
			required: "El campo es obligatorio",
			maxlength: "El campo debe contener 250 caracteres como m&aacute;ximo."
		},
		cmpIdRol: {
			required: "El campo es obligatorio."
		},
		cmpTipo: {
			required: "El campo es obligatorio."
		},
		cmpIdOperacion: {
			required: "El campo es obligatorio."
		},
		cmpEstado: {
			required: "El campo es obligatorio."
		}
	  };

  moduloActual.inicializarCampos= function(){
	this.entidad =$("#entidad");
	
	this.obj.btnGuardarAutorizacion=$("#btnGuardarAutorizacion");
	this.obj.btnCancelarAutorizacion=$("#btnCancelarAutorizacion");

	this.obj.idUsuario = $("#idUsuario");
	this.obj.usuario = $("#usuario");
	this.obj.RolUsuario = $("#RolUsuario");
	this.obj.OperacionUsuario = $("#OperacionUsuario");
	
	moduloActual.obj.btnGuardarAutorizacion.on("click",function(){
	  var referenciaModulo=this;
	  try {
		  moduloActual.modoEdicion=constantes.MODO_NUEVO;
		  moduloActual.obj.cmpTituloFormulario.text(constantes.TITULO_AGREGAR_REGISTRO);
		  moduloActual.resetearFormulario();
		  moduloActual.obj.cntTabla.hide();
		  moduloActual.obj.cntVistaRegistro.hide();
		  moduloActual.obj.cntFormulario.hide();
		  moduloActual.obj.cntAutorizacion.show();
		  moduloActual.guardarAutorizacion();
	  } catch(error){
		console.log(error.message);
	  };
	});

	moduloActual.obj.btnCancelarAutorizacion.on("click",function(){
	  var referenciaModulo=this;
	  try {
		  moduloActual.obj.cntFormulario.hide();
		  moduloActual.obj.cntVistaRegistro.hide();
		  moduloActual.obj.cntAutorizacion.hide();
		  moduloActual.obj.cntTabla.show();
	  } catch(error){
		console.log(error.message);
	  };
	});
	  
	//Campos de formulario
    this.obj.cmpNombre=$("#cmpNombre");
    this.obj.cmpClave=$("#cmpClave");
    this.obj.cmpIdentidad=$("#cmpIdentidad");
    this.obj.cmpZonaHoraria=$("#cmpZonaHoraria");
    this.obj.cmpEmail=$("#cmpEmail");
    this.obj.cmpCambioClave=$("#cmpCambioClave");
    this.obj.cmpIdRol=$("#cmpIdRol");
    this.obj.cmpIdRol.tipoControl="select2";
    this.obj.cmpSelect2Rol=$("#cmpIdRol").select2({
  	  ajax: {
  		    url: "./rol/listar",
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
  		    	var resultados= respuesta.contenido.carga;
  		      return { results: resultados};
  		    },
  		    cache: true
  		  },
  		language: "es",
  		escapeMarkup: function (markup) { return markup; },
  		templateResult: function (registro) {
  			if (registro.loading) {
  				return registro.text;
  			}		    	
		        return "<div class='select2-user-result'>" + registro.nombre + "</div>";
		    },
		    templateSelection: function (registro) {
		        return registro.nombre || registro.text;
		    },
  		minimumInputLength: 3
    });
    
    this.obj.cmpTipo=$("#cmpTipo");
    
    this.obj.cmpIdOperacion=$("#cmpIdOperacion");
    this.obj.cmpIdOperacion.tipoControl="select2";
    this.obj.cmpSelect2Operacion=$("#cmpIdOperacion").select2({
    	  ajax: {
    		    url: "./operacion/listar",
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
    		    	var resultados= respuesta.contenido.carga;
    		    	console.log(resultados);
    		      return { results: resultados};
    		    },
    		    cache: true
    		  },
    		language: "es",
    		escapeMarkup: function (markup) { return markup; },
    		templateResult: function (registro) {
    			if (registro.loading) {
    				return registro.text;
    			}		    	
  		        return "<div class='select2-user-result'>" + registro.nombre + "</div>";
  		    },
  		    templateSelection: function (registro) {
  		        return registro.nombre || registro.text;
  		    },
    		minimumInputLength: 3
      });

    this.obj.cmpEstado=$("#cmpEstado");

    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaNombre=$("#vistaNombre");
    this.obj.vistaClave=$("#vistaClave");
    this.obj.vistaIdentidad=$("#vistaIdentidad");
    this.obj.vistaZonaHoraria=$("#vistaZonaHoraria");
    this.obj.vistaEstado=$("#vistaEstado");
    this.obj.vistaEmail=$("#vistaEmail");
    this.obj.vistaCambioClave=$("#vistaCambioClave");
    this.obj.vistaIdRol=$("#vistaIdRol");
    this.obj.vistaCreadoEl=$("#vistaCreadoEl");
    this.obj.vistaCreadoPor=$("#vistaCreadoPor");
    this.obj.vistaActualizadoPor=$("#vistaActualizadoPor");
    this.obj.vistaActualizadoEl=$("#vistaActualizadoEl");
    this.obj.vistaIpCreacion=$("#vistaIpCreacion");
    this.obj.vistaIpActualizacion=$("#vistaIpActualizacion");
    this.obj.vistaTipo=$("#vistaTipo");
    this.obj.vistaIdOperacion=$("#vistaIdOperacion");
  };

  moduloActual.grillaDespuesSeleccionar= function(indice){
	  var referenciaModulo=this;
	  moduloActual.obj.idUsuario = referenciaModulo.obj.datClienteApi.cell(indice,1).data();
	  moduloActual.obj.usuario.text(referenciaModulo.obj.datClienteApi.cell(indice,3).data());
	  moduloActual.obj.RolUsuario.text(referenciaModulo.obj.datClienteApi.cell(indice,4).data());
	  moduloActual.obj.OperacionUsuario.text(referenciaModulo.obj.datClienteApi.cell(indice,5).data());
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
    this.obj.cmpClave.val(registro.clave);
    this.obj.cmpIdentidad.val(registro.identidad);
    this.obj.cmpZonaHoraria.val(registro.zonaHoraria);
    this.obj.cmpEstado.val(registro.estado);
    this.obj.cmpEmail.val(registro.email);
    //this.obj.cmpCambioClave.val(registro.cambioClave);
    this.obj.cmpIdRol.val(registro.idRol);
    this.obj.cmpTipo.val(registro.tipo);
    this.obj.cmpIdOperacion.val(registro.idOperacion);
    
    var elemento1=constantes.PLANTILLA_OPCION_SELECTBOX;
    
    elemento1 = elemento1.replace(constantes.ID_OPCION_CONTENEDOR,registro.rol.id);
    elemento1 = elemento1.replace(constantes.VALOR_OPCION_CONTENEDOR,registro.rol.nombre);
    this.obj.cmpIdRol.empty().append(elemento1).val(registro.rol.id).trigger('change');
    
    var elemento2=constantes.PLANTILLA_OPCION_SELECTBOX;
    
    elemento2 = elemento2.replace(constantes.ID_OPCION_CONTENEDOR,registro.operacion.id);
    elemento2 = elemento2.replace(constantes.VALOR_OPCION_CONTENEDOR,registro.operacion.nombre);
    this.obj.cmpIdOperacion.empty().append(elemento2).val(registro.operacion.id).trigger('change');
    
  };

  moduloActual.llenarDetalles = function(registro){
    this.idRegistro= registro.id;
    this.obj.vistaId.text(registro.id);
    this.obj.vistaNombre.text(registro.nombre);
    this.obj.vistaClave.text(registro.clave);
    this.obj.vistaIdentidad.text(registro.identidad);
    this.obj.vistaZonaHoraria.text(registro.zonaHoraria);
    this.obj.vistaEstado.text(utilitario.formatearEstado(registro.estado));
    this.obj.vistaEmail.text(registro.email);
    this.obj.vistaCambioClave.text(registro.cambioClave);
    this.obj.vistaIdRol.text(registro.rol.nombre);
    this.obj.vistaTipo.text(utilitario.formatearTipoUsuario(registro.tipo));
    this.obj.vistaIdOperacion.text(registro.operacion.nombre);
    
  };

  moduloActual.recuperarValores = function(registro){
    var eRegistro = {};
    var referenciaModulo=this;
    try {
    eRegistro.id = parseInt(referenciaModulo.idRegistro);
    eRegistro.nombre = referenciaModulo.obj.cmpNombre.val().toUpperCase();
    eRegistro.clave = referenciaModulo.obj.cmpClave.val();
    eRegistro.identidad = referenciaModulo.obj.cmpIdentidad.val().toUpperCase();
    eRegistro.zonaHoraria = referenciaModulo.obj.cmpZonaHoraria.val();
    eRegistro.estado = parseInt(referenciaModulo.obj.cmpEstado.val());
    eRegistro.email = referenciaModulo.obj.cmpEmail.val();
    eRegistro.cambioClave = referenciaModulo.obj.cmpCambioClave.val();
    eRegistro.id_rol = parseInt(referenciaModulo.obj.cmpIdRol.val());
    eRegistro.tipo = parseInt(referenciaModulo.obj.cmpTipo.val());
    eRegistro.id_operacion = parseInt(referenciaModulo.obj.cmpIdOperacion.val());
    
    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
//------------------ Contenedor Autorizar -----------------------------------//
  moduloActual.recuperarAutorizaciones = function(){
  var referenciaModulo = this;
  	try{
		$.ajax({
		    type: "GET",
		    url: referenciaModulo.URL_RECUPERAR_AUTORIZACION, 
		    contentType: "application/json", 
		    data: {ID:referenciaModulo.idRegistro},	
		    success: function(respuesta) {
		    	moduloActual.entidad = respuesta.contenido.carga;
		    },			    		    
		    error: function() {
		    	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la peticion");
		    }
		});
  	}  catch(error){
      console.log(error.message);
  	}
  };

moduloActual.asignarAutorizacion= function(idSeleccionado, autorizacion){
	var referenciaModulo = this;
	try{
		for (var i=0; i< moduloActual.entidad.length; i++){
			
			if(moduloActual.entidad[i].id == idSeleccionado){
				if(autorizacion == 1){
					moduloActual.entidad[i].tieneAutorizacion = 1;
				}
				else{
					moduloActual.entidad[i].tieneAutorizacion = 0;
				}
			} 
		}
	}  catch(error){
       console.log(error.message);
    }
};	

moduloActual.recuperarValoresAutorizacion = function(registro){
    var referenciaModulo=this;
    var eRegistro = {};
    try {
	    eRegistro.id = parseInt(referenciaModulo.idRegistro);
	    eRegistro.idUsuario = parseInt(moduloActual.obj.idUsuario);
	    eRegistro.codigoAutorizacion = "";
	    eRegistro.autorizacion=[];
	    eRegistro.usuario=[];

		for (var i = 0; i< moduloActual.entidad.length; i++){
			var eAutorizacion = {};
			var autorizacion = moduloActual.entidad[i].tieneAutorizacion;
		//	if(autorizacion == 1){
				eAutorizacion.id =  parseInt(moduloActual.entidad[i].id);
				eAutorizacion.nombre = moduloActual.entidad[i].nombre;
				eAutorizacion.estado = autorizacion;
				eRegistro.autorizacion.push(eAutorizacion);
		//	} 
		}
    }  catch(error){
       console.log(error.message);
    }
    return eRegistro;
  };
  
 moduloActual.iniciarListado= function(){
	var referenciaModulo = this;
	try{
		moduloActual.listarRegistros();
		moduloActual.obj.cntFormulario.hide();	
		//moduloActual.protegeFormulario(false);
		moduloActual.obj.cntAutorizacion.hide();
		moduloActual.obj.cntTabla.show();
	} catch(error){
		console.log(error.message);
	};
};
	  

  moduloActual.guardarAutorizacion= function(){
	var referenciaModulo = this;
	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
	//referenciaModulo.protegeFormulario(true);
	var eRegistro = moduloActual.recuperarValoresAutorizacion();
	$.ajax({
	    type: "POST",
	    url: referenciaModulo.URL_GUARDAR_AUTORIZACION, 
	    contentType: "application/json", 
	    data: JSON.stringify(eRegistro),	
	    success: function(respuesta) {
	    	if (!respuesta.estado) {
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
	    		//referenciaModulo.protegeFormulario(false);
	    	} 	else {		    				    			    		
	    		moduloActual.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
	    		referenciaModulo.iniciarListado();
	    		
    		}
	    },			    		    
	    error: function() {
	        referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
	      }
	});
  };	
	
  moduloActual.inicializar();

});
