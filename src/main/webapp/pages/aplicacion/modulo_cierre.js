function moduloCierre (){
  this.obj={};
  this.NUMERO_REGISTROS_PAGINA = constantes.NUMERO_REGISTROS_PAGINA;
  this.TOPES_PAGINACION = constantes.TOPES_PAGINACION;
  this.URL_LENGUAJE_GRILLA = "tema/datatable/language/es-ES.json";
  this.TIPO_CONTENIDO=constantes.TIPO_CONTENIDO_JSON;
  this.NOMBRE_EVENTO_CLICK=constantes.NOMBRE_EVENTO_CLICK;
  this.modoEdicion=constantes.MODO_LISTAR;
  this.depuracionActivada=true;
  this.estaCargadaInterface=false;
  //Inicializar propiedades
  this.urlBase='';  
  this.mensajeEsMostrado=false;
  this.idRegistro = 0;
  this.columnasGrilla={};
  this.definicionColumnas=[];
  this.reglasValidacionFormulario={};
  this.mensajesValidacionFormulario={};
  this.ordenGrilla=[[ 1, 'asc' ]];
  this.columnasGrilla=[{ "data": null} ];//Target 0
  // this.cmpFitlroEstado.val("2");
  this.definicionColumnas=[{
    "targets": 0,
    "searchable": false,
    "orderable": false,      
    "visible":true,
    "render": function ( datos, tipo, fila, meta ) {
    var configuracion =meta.settings;
    return configuracion._iDisplayStart + meta.row + 1;
    }
  }];  
};

moduloCierre.prototype.mostrarDepuracion=function(mensaje){
  var referenciaModulo=this;
  if (referenciaModulo.depuracionActivada=true){
    console.log(mensaje);
  }
};

moduloCierre.prototype.mostrarErrorServidor=function(xhr,estado,error){
  var referenciaModulo=this;
  if (xhr.status === constantes.ERROR_SIN_CONEXION) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_NO_CONECTADO);
  } else if (xhr.status == constantes.ERROR_RECURSO_NO_DISPONIBLE) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_RECURSO_NO_DISPONIBLE);
  } else if (xhr.status == constantes.ERROR_INTERNO_SERVIDOR) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_INTERNO_SERVIDOR);
  } else if (estado === constantes.ERROR_INTERPRETACION_DATOS) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_GENERICO_SERVIDOR);
  } else if (estado === constantes.ERROR_TIEMPO_AGOTADO) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_TIEMPO_AGOTADO);
  } else if (estado === constantes.ERROR_CONEXION_ABORTADA) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_GENERICO_SERVIDOR);
  } else {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,constantes.ERROR_GENERICO_SERVIDOR);
  }
};

moduloCierre.prototype.inicializar=function(){
  this.mostrarDepuracion("inicializar");
  this.inicializarControlesGenericos();
  this.inicializarGrilla();
  this.inicializarFormularioPrincipal();
  this.inicializarCampos();
};

moduloCierre.prototype.resetearFormulario= function(){
  var referenciaModulo= this;
  referenciaModulo.obj.frmPrincipal[0].reset();
  jQuery.each( this.obj, function( i, val ) {
    if (typeof referenciaModulo.obj[i].tipoControl != constantes.CONTROL_NO_DEFINIDO ){
      if (referenciaModulo.obj[i].tipoControl == constantes.TIPO_CONTROL_SELECT2){
        referenciaModulo.obj[i].select2("val", null);
      }
    }
  });
};

moduloCierre.prototype.inicializarControlesGenericos=function(){
  this.mostrarDepuracion("inicializarControlesGenericos");
  var referenciaModulo=this; 
  //CONTENEDORES
  this.obj.cntTabla=$("#cntTabla");
  this.obj.cntFormulario=$("#cntFormulario");
  //tabla principal
  this.obj.tablaPrincipal=$('#tablaPrincipal');
  //formularios
  this.obj.frmPrincipal = $("#frmPrincipal");
  this.obj.frmValidarAutorizacion = $("#frmValidarAutorizacion");
  this.obj.frmConfirmarModificarEstado=$("#frmConfirmarModificarEstado");	
  //botones
  this.obj.btnFiltrar=$("#btnFiltrar");
  this.obj.btnCerrar=$("#btnCerrar");
  this.obj.btnCambiarEstado=$("#btnCambiarEstado");
  this.obj.btnGuardarValidarAutorizacion=$("#btnGuardarValidarAutorizacion");
  this.obj.btnCancelarValidarAutorizacion=$("#btnCancelarValidarAutorizacion");
  this.obj.btnGuardarCambioEstado=$("#btnGuardarCambioEstado");
  this.obj.btnCancelarCambioEstado=$("#btnCancelarCambioEstado");
  this.obj.btnConfirmarModificarEstado=$("#btnConfirmarModificarEstado");
  //titulo de las pantallas
  this.obj.cmpTituloFormulario=$("#cmpTituloFormulario");	
  //Inicializar controles	
  this.obj.cmpEstado=$("#cmpEstado");
  
  
  
  
  
  this.obj.ocultaContenedorTabla=$("#ocultaContenedorTabla");
  this.obj.ocultaContenedorFormulario=$("#ocultaContenedorFormulario");
  this.obj.ocultaContenedorVista=$("#ocultaContenedorVista");
  this.obj.bandaInformacion=$("#bandaInformacion");
  //Botones	
  this.obj.btnListar=$("#btnListar");
  this.obj.btnAgregar=$("#btnAgregar");
  this.obj.btnModificar=$("#btnModificar");
  this.obj.btnModificarEstado=$("#btnModificarEstado");	
  this.obj.btnConfirmarModificarEstado=$("#btnConfirmarModificarEstado");
  this.obj.btnVer=$("#btnVer");
  this.obj.btnConfirmarEliminar=$("#btnConfirmarEliminar");	
  this.obj.btnGuardar=$("#btnGuardar");
  this.obj.btnArribo=$("#btnArribo");
  this.obj.btnCancelarGuardar=$("#btnCancelarGuardar");
  this.obj.btnCerrarVista=$("#btnCerrarVista");
  //estos valores para hacer los filtros de los listados	
  this.obj.filtroOperacion=$("#filtroOperacion");
  this.obj.filtroFechaPlanificada=$("#filtroFechaPlanificada");

  //eventos click de los botones
  this.obj.btnFiltrar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
	  referenciaModulo.modoEdicion=constantes.MODO_LISTAR;
	  referenciaModulo.listarRegistros();
  });

  this.obj.btnCerrar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
	  referenciaModulo.solicitarActualizarEstado();
  });
  
  this.obj.btnCambiarEstado.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
	  referenciaModulo.botonCambiarEstado();
  });
  
  this.obj.btnGuardarValidarAutorizacion.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
	  referenciaModulo.botonGuardarValidarAutorizacion();
  });

  this.obj.btnCancelarValidarAutorizacion.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
	  referenciaModulo.botonCancelarValidarAutorizacion();
  });

  this.obj.btnGuardarCambioEstado.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
	  referenciaModulo.botonGuardarCambioEstado();
  });
  
  this.obj.btnCancelarCambioEstado.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
	  referenciaModulo.botonCancelarCambioEstado();
  });
  
  this.obj.btnConfirmarModificarEstado.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
	  referenciaModulo.botonConfirmarModificarEstado();
  });
  
};

moduloBase.prototype.solicitarActualizarEstado= function(){
	try {
		this.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
		this.obj.frmConfirmarModificarEstado.modal("show");
	} catch(error){
		referenciaModulo.mostrarDepuracion(error.message);
	}
};

moduloCierre.prototype.botonConfirmarModificarEstado=function(){
  var eRegistro = {};
  var referenciaModulo=this;
  referenciaModulo.obj.frmConfirmarModificarEstado.modal("hide");
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
  try {
    eRegistro.id = parseInt(referenciaModulo.idRegistro);
    eRegistro.estado = parseInt(constantes.ESTADO_CERRADO);
  }  catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  }

  $.ajax({
    type: constantes.PETICION_TIPO_POST,
    url: referenciaModulo.URL_ACTUALIZAR_ESTADO, 
    contentType: referenciaModulo.TIPO_CONTENIDO, 
    data: JSON.stringify(eRegistro),	
    success: function(respuesta) {
      if (!respuesta.estado) {
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
      } else {		    				    			    		
        referenciaModulo.listarRegistros();
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
        referenciaModulo.obj.cntFormulario.hide();	
        referenciaModulo.obj.cntTabla.show();
      }
    },			    		    
    error: function() {
    	referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
    }
    });
};	

moduloCierre.prototype.botonCambiarEstado= function(){  
	var referenciaModulo=this;
	referenciaModulo.mostrarDepuracion("botonCambiarEstado");
	try {
    referenciaModulo.modoEdicion=constantes.MODO_NUEVO;
    referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_AGREGAR_REGISTRO);
    referenciaModulo.resetearFormulario();
    referenciaModulo.obj.cntTabla.hide();
    referenciaModulo.obj.cntFormulario.show();
    referenciaModulo.recuperarAutorizacionesXcodigoInterno();
    referenciaModulo.obj.frmValidarAutorizacion.modal("show");
    referenciaModulo.obj.ocultaContenedorFormulario.show();
    referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.INICIAR_OPERACION);
	} catch(error){
		referenciaModulo.mostrarDepuracion(error.message);
	};
};

moduloCierre.prototype.botonGuardarValidarAutorizacion= function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("botonGuardarValidarAutorizacion");
  referenciaModulo.guardarValidacionAutorizacion();
};

moduloCierre.prototype.botonCancelarValidarAutorizacion=function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("btnCancelarValidarAutorizacion");
  referenciaModulo.resetearFormulario();
  referenciaModulo.obj.cntFormulario.hide();
  referenciaModulo.obj.ocultaContenedorFormulario.hide();
  referenciaModulo.obj.cntTabla.show();
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.CANCELAR_OPERACION);
};

moduloCierre.prototype.botonGuardarCambioEstado=function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("btnGuardarCambioEstado");
  var eRegistro = {};
  var referenciaModulo=this;
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
  try {
	  
    eRegistro.id = parseInt(referenciaModulo.idRegistro);
    eRegistro.estado = parseInt(referenciaModulo.obj.cmpEstado.val());
  }  catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  }

  $.ajax({
    type: constantes.PETICION_TIPO_POST,
    url: referenciaModulo.URL_ACTUALIZAR_ESTADO, 
    contentType: referenciaModulo.TIPO_CONTENIDO, 
    data: JSON.stringify(eRegistro),	
    success: function(respuesta) {
      if (!respuesta.estado) {
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
      } else {		    				    			    		
        referenciaModulo.listarRegistros();
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
        referenciaModulo.obj.cntFormulario.hide();	
        referenciaModulo.obj.cntTabla.show();
        referenciaModulo.obj.btnCerrar.addClass(constantes.CSS_CLASE_DESHABILITADA);
		referenciaModulo.obj.btnCambiarEstado.addClass(constantes.CSS_CLASE_DESHABILITADA);
      }
    },			    		    
    error: function() {
    	referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
    }
    });
};

moduloCierre.prototype.botonCancelarCambioEstado=function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("botonCancelarCambioEstado");
  referenciaModulo.resetearFormulario();
  referenciaModulo.obj.cntFormulario.hide();
  referenciaModulo.obj.ocultaContenedorFormulario.hide();
  referenciaModulo.obj.cntTabla.show();
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.CANCELAR_OPERACION);
};
	
	
moduloCierre.prototype.llamadaAjaxGrilla=function(e,configuracion,json){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_AJAX);
  if (json.estado==true){
    json.recordsTotal=json.contenido.totalRegistros;
    json.recordsFiltered=json.contenido.totalEncontrados;
    json.data= json.contenido.carga;
    if (referenciaModulo.modoEdicion==constantes.MODO_LISTAR){
    referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
    }
  } else {
    json.recordsTotal=0;
    json.recordsFiltered=0;
    json.data= {};
    if (referenciaModulo.modoEdicion==constantes.MODO_LISTAR){
    referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
    } else {

    }
  }
  if (referenciaModulo.estaCargadaInterface==false){        
    referenciaModulo.estaCargadaInterface=true;
  }
  referenciaModulo.obj.ocultaContenedorTabla.hide();  
};

moduloCierre.prototype.inicializarGrilla=function(){
  //Nota no retornar el objeto solo manipular directamente
	//Establecer grilla y su configuracion
  var referenciaModulo=this;
  this.obj.tablaPrincipal.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
	   referenciaModulo.llamadaAjaxGrilla(e,configuracion,json);
  });
  
  this.obj.tablaPrincipal.on(constantes.DT_EVENTO_PREAJAX, function (e,configuracion,datos) {
    //Se ejecuta antes de cualquier llamada ajax
    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_PREAJAX);
    if (referenciaModulo.estaCargadaInterface==true){
    referenciaModulo.obj.ocultaContenedorTabla.show();
    }
  });

  this.obj.tablaPrincipal.on(constantes.DT_EVENTO_PAGINACION, function () {
  //Se ejecuta cuando se hace clic en boton de paginacion
    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_PAGINACION);
  });

  this.obj.tablaPrincipal.on(constantes.DT_EVENTO_ORDENACION, function () {
  //Se ejecuta cuando se hace clic en alguna cabecera de columna para ordenarla
    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_ORDENACION);
  });

  this.obj.datClienteApi= this.obj.tablaPrincipal.DataTable({
    "processing": true,
    "responsive": true,
    "dom": constantes.DT_ESTRUCTURA,
    "iDisplayLength": referenciaModulo.NUMERO_REGISTROS_PAGINA,
    "lengthMenu": referenciaModulo.TOPES_PAGINACION,
    "language": {
      "url": referenciaModulo.URL_LENGUAJE_GRILLA
    },
    "serverSide": true,
    "ajax": {
      "url": referenciaModulo.URL_LISTAR,
      "type":constantes.PETICION_TIPO_GET,
      "data": function (d) {
        var indiceOrdenamiento = d.order[0].column;
        d.registrosxPagina =  d.length; 
        d.inicioPagina = d.start; 
        d.campoOrdenamiento= d.columns[indiceOrdenamiento].data;
        d.sentidoOrdenamiento=d.order[0].dir;
        d.filtroOperacion= referenciaModulo.obj.filtroOperacion.val();
        d.filtroFechaPlanificada= referenciaModulo.obj.filtroFechaPlanificada.val();
        var rangoFecha = referenciaModulo.obj.filtroFechaPlanificada.val().split("-");
        var fechaInicio = utilitario.formatearFecha2Iso(rangoFecha[0]) ;
        var fechaFinal = utilitario.formatearFecha2Iso(rangoFecha[1]);
        d.filtroFechaInicio= fechaInicio;	
        d.filtroFechaFinal = fechaFinal;
      }
    },
    "columns": referenciaModulo.columnasGrilla,
    "columnDefs": referenciaModulo.definicionColumnas
	});	
	
	$('#tablaPrincipal tbody').on(referenciaModulo.NOMBRE_EVENTO_CLICK, 'tr', function () {
    if ( $(this).hasClass('selected') ) {
      $(this).removeClass('selected');
    } else {
      referenciaModulo.obj.datClienteApi.$('tr.selected').removeClass('selected');
      $(this).addClass('selected');
    }
		var indiceFila = referenciaModulo.obj.datClienteApi.row( this ).index();
		var idRegistro = referenciaModulo.obj.datClienteApi.cell(indiceFila,1).data();
		var estadoregistro = referenciaModulo.obj.datClienteApi.cell(indiceFila,9).data();
		if(estadoregistro  == constantes.ESTADO_CERRADO){
			referenciaModulo.obj.btnCerrar.addClass(constantes.CSS_CLASE_DESHABILITADA);
			referenciaModulo.obj.btnCambiarEstado.removeClass(constantes.CSS_CLASE_DESHABILITADA);
		} else {
			referenciaModulo.obj.btnCerrar.removeClass(constantes.CSS_CLASE_DESHABILITADA);
			referenciaModulo.obj.btnCambiarEstado.addClass(constantes.CSS_CLASE_DESHABILITADA);
		}

		referenciaModulo.idRegistro = idRegistro;		
		referenciaModulo.grillaDespuesSeleccionar(indiceFila);
	});
};

moduloCierre.prototype.inicializarFormularioPrincipal= function(){  
  //Establecer validaciones del formulario
  var referenciaModulo=this;
    this.obj.frmPrincipal.validate({
    rules: referenciaModulo.reglasValidacionFormulario,
    messages: referenciaModulo.mensajesValidacionFormulario,
    submitHandler: function(form) {
    // form.submit();
    }
  });
};

moduloCierre.prototype.listarRegistros = function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("listarRegistros");  
  this.obj.datClienteApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	 	
};

moduloCierre.prototype.actualizarBandaInformacion=function(tipo, mensaje){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("actualizarBandaInformacion");
	referenciaModulo.obj.bandaInformacion.removeClass(constantes.CLASE_MENSAJE_ERROR);
	referenciaModulo.obj.bandaInformacion.removeClass(constantes.CLASE_MENSAJE_EXITO);
	referenciaModulo.obj.bandaInformacion.removeClass(constantes.CLASE_MENSAJE_INFORMACION);
	if (tipo==constantes.TIPO_MENSAJE_INFO){
		referenciaModulo.obj.bandaInformacion.addClass(constantes.CLASE_MENSAJE_INFORMACION );
	} else if (tipo==constantes.TIPO_MENSAJE_EXITO){
		referenciaModulo.obj.bandaInformacion.addClass(constantes.CLASE_MENSAJE_EXITO );
	} else if (tipo==constantes.TIPO_MENSAJE_ERROR){
		referenciaModulo.obj.bandaInformacion.addClass(constantes.CLASE_MENSAJE_ERROR );
	}	
	referenciaModulo.obj.bandaInformacion.text(mensaje);
};

moduloCierre.prototype.iniciarListado= function(mensaje){
	var referenciaModulo = this;
	try {
		referenciaModulo.listarRegistros();
		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,mensaje);
		referenciaModulo.obj.cntFormulario.hide();	
		referenciaModulo.obj.cntTabla.show();
	} catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
	};
};

moduloCierre.prototype.iniciarContenedores = function(){
  var referenciaModulo = this;
  try {
    referenciaModulo.obj.cntFormulario.hide();	
    referenciaModulo.protegeFormulario(false);
    referenciaModulo.obj.cntTabla.show();
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  };
};

moduloCierre.prototype.solicitarActualizarEstado= function(){
	try {
		this.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
		this.obj.frmConfirmarModificarEstado.modal("show");
	} catch(error){
		referenciaModulo.mostrarDepuracion(error.message);
	}
};

moduloCierre.prototype.actualizarEstadoRegistro= function(){
  var eRegistro = {};
  var referenciaModulo=this;
	referenciaModulo.obj.frmConfirmarModificarEstado.modal("hide");
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
  if (referenciaModulo.estadoRegistro==constantes.ESTADO_ACTIVO){
    referenciaModulo.estadoRegistro=constantes.ESTADO_INACTIVO;
  } else {
    referenciaModulo.estadoRegistro=constantes.ESTADO_ACTIVO;
  }

  try {
    eRegistro.id = parseInt(referenciaModulo.idRegistro);
    eRegistro.estado = parseInt(referenciaModulo.estadoRegistro);
  }  catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  }

  $.ajax({
    type: constantes.PETICION_TIPO_POST,
    url: referenciaModulo.URL_ACTUALIZAR_ESTADO, 
    contentType: referenciaModulo.TIPO_CONTENIDO, 
    data: JSON.stringify(eRegistro),	
    success: function(respuesta) {
      if (!respuesta.estado) {
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
      } else {		    				    			    		
        referenciaModulo.listarRegistros();
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
        referenciaModulo.obj.cntFormulario.hide();	
        referenciaModulo.obj.cntTabla.show();
      }
    },			    		    
    error: function() {
      referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
    }
    });
	};
	
moduloCierre.prototype.guardarValidacionAutorizacion= function(){
//Implementar en cada caso
};
	
moduloCierre.prototype.recuperarAutorizacionesXcodigoInterno= function(){
//Implementar en cada caso
};
	
moduloCierre.prototype.inicializarCampos= function(){
//Implementar en cada caso
};

moduloCierre.prototype.llenarFormulario = function(registro){
//Implementar en cada caso	
};

moduloCierre.prototype.recuperarValores = function(registro){
var eRegistro = {};
//Implementar en cada caso
return eRegistro;
};

moduloCierre.prototype.grillaDespuesSeleccionar= function(indice){
//TODO implementar
};

moduloCierre.prototype.despuesListarRegistros=function(){
//Implementar en cada caso
};