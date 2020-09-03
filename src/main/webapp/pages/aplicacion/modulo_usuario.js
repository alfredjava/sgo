function moduloUsuario (){
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
  
  this.columnasGrillaSecundaria={};
  this.definicionColumnasSecundaria=[];
  this.ordenGrillaSecundaria=[[ 1, 'asc' ]];
  this.columnasGrillaSecundaria=[{ "data": 'tieneAutorizacion'} ];//Target 0
  // this.cmpFitlroEstado.val("2");
  this.definicionColumnasSecundaria=[{
    "targets": 0,
    "searchable": false,
    "orderable": false,      
    "visible":true,
    "render": function (data, type, row, meta){
    			if ( type === 'display' ) {
    				return '<input type="checkbox" class="tabla-tieneAutorizacion">';
    			}
    			return data;}
  }];  
};

moduloUsuario.prototype.mostrarDepuracion = function(mensaje){
  var referenciaModulo=this;
  if (referenciaModulo.depuracionActivada=true){
    console.log(mensaje);
  }
};

moduloUsuario.prototype.mostrarErrorServidor=function(xhr,estado,error){
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

moduloUsuario.prototype.inicializar=function(){
  this.mostrarDepuracion("inicializar");
  this.inicializarControlesGenericos();
  this.inicializarGrilla();
  this.inicializarGrillaSecundaria();
  this.inicializarFormularioPrincipal();
  this.inicializarCampos();
};

moduloUsuario.prototype.resetearFormulario= function(){
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

moduloUsuario.prototype.inicializarControlesGenericos=function(){
  this.mostrarDepuracion("inicializarControlesGenericos");
  var referenciaModulo=this; 
  this.obj.tablaPrincipal=$('#tablaPrincipal');
  this.obj.tablaSecundaria=$('#tablaSecundaria');
  this.obj.frmPrincipal = $("#frmPrincipal");
  this.obj.cntTabla=$("#cntTabla");
  this.obj.cntFormulario=$("#cntFormulario");
  this.obj.cmpTituloFormulario=$("#cmpTituloFormulario");	
  this.obj.cntVistaRegistro=$("#cntVistaRegistro");
  this.obj.cntAutorizacion=$("#cntAutorizacion");
	this.obj.btnAutorizar=$("#btnAutorizar");
  //Inicializar controles	
  this.obj.frmConfirmarModificarEstado=$("#frmConfirmarModificarEstado");	
  this.obj.frmConfirmarEliminar=$("#frmConfirmarEliminar");
  this.obj.ocultaContenedorTabla=$("#ocultaContenedorTabla");
  this.obj.ocultaContenedorFormulario=$("#ocultaContenedorFormulario");
  this.obj.ocultaContenedorAutorizacion=$("#ocultaContenedorAutorizacion");
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
  this.obj.btnFiltrar=$("#btnFiltrar");
  //estos valores para hacer los filtros de los listados	
  this.obj.txtFiltro=$("#txtFiltro");
  this.obj.cmpFiltroEstado=$("#cmpFiltroEstado");
  this.obj.cmpFechaInicial=$("#cmpFechaInicial");
  this.obj.cmpFechaFinal=$("#cmpFechaFinal");
  this.obj.filtroOperacion=$("#filtroOperacion");
  this.obj.filtroFechaPlanificada=$("#filtroFechaPlanificada");
  this.obj.cmpFiltroUsuario=$("#cmpFiltroUsuario");
  this.obj.cmpFiltroTabla=$("#cmpFiltroTabla");
  this.obj.cmpFiltroTipoFecha=$("#cmpFiltroTipoFecha");	
  //
  this.obj.btnFiltrar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){ 
  referenciaModulo.modoEdicion=constantes.MODO_LISTAR;
  referenciaModulo.listarRegistros();
  });

  this.obj.btnListar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.modoEdicion=constantes.MODO_LISTAR;
  referenciaModulo.listarRegistros();
  });

  this.obj.btnAgregar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarAgregar();
  });

  this.obj.btnModificar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarModificar();
  });

  this.obj.btnModificarEstado.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.solicitarActualizarEstado();
  });

  this.obj.btnConfirmarModificarEstado.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.actualizarEstadoRegistro();
  });

  this.obj.btnVer.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarVer();		
  });

  this.obj.btnCancelarGuardar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarCancelar();
  });

  this.obj.btnCerrarVista.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarCerrarVista();
  });

  this.obj.btnGuardar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarGuardar();
  });
  
  this.obj.btnAutorizar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
  referenciaModulo.iniciarAutorizar();
  });
};

moduloUsuario.prototype.iniciarAutorizar = function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("btnAutorizar.click");
  try {
	  referenciaModulo.modoEdicion=constantes.MODO_AUTORIZAR;
	  referenciaModulo.obj.cmpTituloFormulario.text("Asignar Autorizaci&oacute;n a Usuario");
	  referenciaModulo.resetearFormulario();
	  referenciaModulo.obj.cntTabla.hide();
	  referenciaModulo.obj.cntVistaRegistro.hide();
	  referenciaModulo.obj.cntFormulario.hide();
	  referenciaModulo.obj.cntAutorizacion.show();
	  //referenciaModulo.obj.ocultaContenedorAutorizacion.hide();
	  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.INICIAR_OPERACION);
	  referenciaModulo.recuperarAutorizaciones();
	  this.obj.datSecundariaApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	
	  referenciaModulo.obj.ocultaContenedorAutorizacion.show();
	  //referenciaModulo.c();
  } catch(error){
		referenciaModulo.mostrarDepuracion(error.message);
  };  
};

moduloUsuario.prototype.iniciarGuardar = function(){
  var referenciaModulo=this;
  try {
    if (referenciaModulo.modoEdicion == constantes.MODO_NUEVO){
      referenciaModulo.guardarRegistro();
    } else if  (referenciaModulo.modoEdicion == constantes.MODO_ACTUALIZAR){
      referenciaModulo.actualizarRegistro();
    }
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  };
};

moduloUsuario.prototype.iniciarAgregar= function(){  
	var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("iniciarAgregar");
	try {
    referenciaModulo.modoEdicion=constantes.MODO_NUEVO;
    referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_AGREGAR_REGISTRO);
    referenciaModulo.resetearFormulario();
    referenciaModulo.obj.cntTabla.hide();
    referenciaModulo.obj.cntVistaRegistro.hide();
    referenciaModulo.obj.cntFormulario.show();
    referenciaModulo.obj.ocultaContenedorFormulario.hide();
    referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.INICIAR_OPERACION);
	} catch(error){
		referenciaModulo.mostrarDepuracion(error.message);
	};
};

moduloUsuario.prototype.iniciarModificar= function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("iniciarModificar");
  referenciaModulo.modoEdicion=constantes.MODO_ACTUALIZAR;
  referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_MODIFICA_REGISTRO);
  referenciaModulo.obj.cntTabla.hide();
  referenciaModulo.obj.cntVistaRegistro.hide();
  referenciaModulo.obj.ocultaContenedorFormulario.show();
  referenciaModulo.obj.cntFormulario.show();  
  referenciaModulo.recuperarRegistro();
};

moduloUsuario.prototype.iniciarVer= function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("iniciarVer");
  referenciaModulo.modoEdicion=constantes.MODO_VER;
  referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_DETALLE_REGISTRO);
  referenciaModulo.obj.cntTabla.hide();
  referenciaModulo.obj.cntFormulario.hide();
  referenciaModulo.obj.ocultaContenedorVista.show();
  referenciaModulo.obj.cntVistaRegistro.show();
  referenciaModulo.recuperarRegistro();
};

moduloUsuario.prototype.iniciarCancelar=function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("iniciarCancelar");
  referenciaModulo.resetearFormulario();
  referenciaModulo.obj.cntFormulario.hide();
  referenciaModulo.obj.ocultaContenedorFormulario.show();
  referenciaModulo.obj.cntVistaRegistro.hide();
  referenciaModulo.obj.cntTabla.show();
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.CANCELAR_OPERACION);
};

moduloUsuario.prototype.iniciarCerrarVista=function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("iniciarCerrarVista");
  referenciaModulo.obj.cntFormulario.hide();
  referenciaModulo.obj.cntVistaRegistro.hide();
  referenciaModulo.obj.ocultaContenedorVista.show();
  referenciaModulo.obj.cntTabla.show();
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.CERRAR_VISTA);
};

moduloUsuario.prototype.llamadaAjaxGrilla=function(e,configuracion,json){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_AJAX);
  referenciaModulo.desactivarBotones();
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

moduloUsuario.prototype.inicializarGrilla=function(){
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

  this.obj.datClienteApi = this.obj.tablaPrincipal.DataTable({
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
        d.valorBuscado=d.search.value;
        //d.valorBuscado=referenciaModulo.obj.textoFiltro.val();
        d.txtFiltro = referenciaModulo.obj.txtFiltro.val();
        d.filtroEstado= referenciaModulo.obj.cmpFiltroEstado.val();
        d.filtroOperacion= referenciaModulo.obj.filtroOperacion.val();
        d.filtroFechaPlanificada= referenciaModulo.obj.filtroFechaPlanificada.val();
        d.filtroFechaInicio= referenciaModulo.obj.cmpFechaInicial.val();	
        d.filtroFechaFinal = referenciaModulo.obj.cmpFechaFinal.val();	
        d.filtroUsuario= referenciaModulo.obj.cmpFiltroUsuario.val();
        d.filtroTabla= referenciaModulo.obj.cmpFiltroTabla.val();
      }
    },
    "columns": referenciaModulo.columnasGrilla,
    "columnDefs": referenciaModulo.definicionColumnas
    //"order": referenciaModulo.ordenGrilla
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
		referenciaModulo.idRegistro=idRegistro;
		referenciaModulo.grillaDespuesSeleccionar(indiceFila);
		referenciaModulo.activarBotones();
	});
};

//ESTO PARA LA GRILLA SECUNDARIA

//TODO
moduloUsuario.prototype.llamadaAjaxGrillaSecundaria=function(e,configuracion,json){
	  var referenciaModulo=this;
	  referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_AJAX);
	  referenciaModulo.desactivarBotones();
	  if (json.estado==true){
	    json.recordsTotal=json.contenido.totalRegistros;
	    json.recordsFiltered=json.contenido.totalEncontrados;
	    json.data= json.contenido.carga;
	    if (referenciaModulo.modoEdicion==constantes.MODO_AUTORIZAR){
	    	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
	    }
	  } else {
	    json.recordsTotal=0;
	    json.recordsFiltered=0;
	    json.data= {};
	    if (referenciaModulo.modoEdicion==constantes.MODO_AUTORIZAR){
	    	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
	    } else {

	    }
	  }
	  if (referenciaModulo.estaCargadaInterface==false){        
	    referenciaModulo.estaCargadaInterface=true;
	  }
	  referenciaModulo.obj.ocultaContenedorTabla.hide();  
	};

moduloUsuario.prototype.inicializarGrillaSecundaria=function(){
  //Nota no retornar el objeto solo manipular directamente
	//Establecer grilla y su configuracion
  var referenciaModulo=this;
  this.obj.tablaSecundaria.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
	   referenciaModulo.llamadaAjaxGrillaSecundaria(e,configuracion,json);
  });
};
  
moduloUsuario.prototype.inicializarGrillaSecundaria = function(){
	  //Nota no retornar el objeto solo manipular directamente
	  //Establecer grilla y su configuracion
	  var referenciaModulo=this;
	  var idSeleccionado;
	  referenciaModulo.obj.ocultaContenedorAutorizacion.show();
	  
	  this.obj.tablaSecundaria.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
		   referenciaModulo.llamadaAjaxGrillaSecundaria(e,configuracion,json);
	  });
	  
	  this.obj.tablaSecundaria.on(constantes.DT_EVENTO_PREAJAX, function (e,configuracion,datos) {
	    //Se ejecuta antes de cualquier llamada ajax
	    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_PREAJAX);
	    referenciaModulo.obj.ocultaContenedorAutorizacion.show();
	    if (referenciaModulo.estaCargadaInterface==true){
	    	referenciaModulo.obj.ocultaContenedorAutorizacion.show();
	    }
	  });

	  this.obj.tablaSecundaria.on(constantes.DT_EVENTO_PAGINACION, function () {
	  //Se ejecuta cuando se hace clic en boton de paginacion
	    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_PAGINACION);
	  });

	  this.obj.tablaSecundaria.on(constantes.DT_EVENTO_ORDENACION, function () {
	  //Se ejecuta cuando se hace clic en alguna cabecera de columna para ordenarla
	    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_ORDENACION);
	  });
	  try{
		  this.obj.datSecundariaApi= this.obj.tablaSecundaria.DataTable({
		    "processing": true,
		    "responsive": true,
		    "dom": constantes.DT_ESTRUCTURA,
		    "iDisplayLength": referenciaModulo.NUMERO_REGISTROS_PAGINA,
		    "lengthMenu": referenciaModulo.TOPES_PAGINACION,
		    "language": {
		      "url": referenciaModulo.URL_LENGUAJE_GRILLA,
		    },
		    "serverSide": true,
		    "ajax": {
		    	"url": "./usuario/recuperarAutorizaciones",
		    	"type":constantes.PETICION_TIPO_GET,
    		    "data": function (parametros) {
	    		      return {
	    		    	  ID: referenciaModulo.idRegistro,
	    		      };
    		    },
		    },
		    "columns": referenciaModulo.columnasGrillaSecundaria,
		    "columnDefs": referenciaModulo.definicionColumnasSecundaria,
		    
		    "rowCallback": function (row, data ) {
		            $('input.tabla-tieneAutorizacion', row).prop( 'checked', data.tieneAutorizacion == 1 );
		        }
			});	
			
		  	$('#tablaSecundaria').on('change', 'input.tabla-tieneAutorizacion', function () {
			   var autorizacion = $(this).prop( 'checked' ) ? 1 : 0;
			   referenciaModulo.asignarAutorizacion(idSeleccionado, autorizacion);
		    } );
		   
		  	$('#tablaSecundaria tbody').on( 'click', 'tr', function () {
			     if ( $(this).hasClass('selected') ) {
			            $(this).removeClass('selected');
			     } else {
			    	 referenciaModulo.obj.datSecundariaApi.$('tr.selected').removeClass('selected');
			     	$(this).addClass('selected');
			     }
				var indiceFila = referenciaModulo.obj.datSecundariaApi.row( this ).index();
				idSeleccionado = referenciaModulo.obj.datSecundariaApi.cell(indiceFila,1).data();
			});
		} catch(error){
		    console.log(error.message);
		}
	};

moduloUsuario.prototype.inicializarFormularioPrincipal= function(){  
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

moduloUsuario.prototype.activarBotones=function(){
  this.obj.btnModificar.removeClass(constantes.CSS_CLASE_DESHABILITADA);
  this.obj.btnModificarEstado.removeClass(constantes.CSS_CLASE_DESHABILITADA);
  this.obj.btnVer.removeClass(constantes.CSS_CLASE_DESHABILITADA);
  this.obj.btnAutorizar.removeClass(constantes.CSS_CLASE_DESHABILITADA);
};

moduloUsuario.prototype.desactivarBotones=function(){
  this.obj.btnModificarEstado.html('<i class="fa fa-cloud-upload"></i>'+constantes.TITULO_ACTIVAR_REGISTRO);
  this.obj.btnModificar.addClass(constantes.CSS_CLASE_DESHABILITADA);
  this.obj.btnModificarEstado.addClass(constantes.CSS_CLASE_DESHABILITADA);
  this.obj.btnVer.addClass(constantes.CSS_CLASE_DESHABILITADA);
  this.obj.btnAutorizar.addClass(constantes.CSS_CLASE_DESHABILITADA);
};

moduloUsuario.prototype.listarRegistros = function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("listarRegistros");  
  this.obj.datClienteApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	 	
};

moduloUsuario.prototype.actualizarBandaInformacion=function(tipo, mensaje){
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

moduloUsuario.prototype.recuperarRegistro= function(){
	var referenciaModulo = this;
	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
	$.ajax({
	    type: constantes.PETICION_TIPO_GET,
	    url: referenciaModulo.URL_RECUPERAR, 
	    contentType: referenciaModulo.TIPO_CONTENIDO, 
	    data: {ID:referenciaModulo.idRegistro},	
	    success: function(respuesta) {
	    	if (!respuesta.estado) {
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
	    	} else {		 
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
	    		if (referenciaModulo.modoEdicion == constantes.MODO_ACTUALIZAR){
	    			referenciaModulo.llenarFormulario(respuesta.contenido.carga[0]);
            referenciaModulo.obj.ocultaContenedorFormulario.hide();
	    		} else if (referenciaModulo.modoEdicion == constantes.MODO_VER){
	    			referenciaModulo.llenarDetalles(respuesta.contenido.carga[0]);
            referenciaModulo.obj.ocultaContenedorVista.hide();
	    		}          
    		}
	    },			    		    
	    error: function(xhr,estado,error) {
        referenciaModulo.mostrarErrorServidor(xhr,estado,error);        
        if (referenciaModulo.modoEdicion == constantes.MODO_ACTUALIZAR){
          referenciaModulo.obj.ocultaContenedorFormulario.hide();
        } else if (referenciaModulo.modoEdicion == constantes.MODO_VER){
          referenciaModulo.obj.ocultaContenedorVista.hide();
        } 
	    }
	});
};

moduloUsuario.prototype.verRegistro= function(){
	var referenciaModulo = this;
	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
	$.ajax({
	    type: constantes.PETICION_TIPO_GET,
	    url: referenciaModulo.URL_RECUPERAR, 
	    contentType: referenciaModulo.TIPO_CONTENIDO, 
	    data: {ID:referenciaModulo.idRegistro},	
	    success: function(respuesta) {
	    	if (!respuesta.estado) {
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
	    	} 	else {		 
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
	    		referenciaModulo.llenarDetalles(respuesta.contenido.carga[0]);
	    		referenciaModulo.obj.ocultaContenedorVista.show();
    		}
	    },			    		    
	    error: function() {
	    	referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
	    	referenciaModulo.obj.ocultaContenedorVista.show();
	    }
	});
};

moduloUsuario.prototype.guardarRegistro= function(){
  referenciaModulo.mostrarDepuracion("guardarRegistro");
	var referenciaModulo = this;
	if (referenciaModulo.obj.frmPrincipal.valid()){
    referenciaModulo.obj.ocultaContenedorFormulario.show();
		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
		var eRegistro = referenciaModulo.recuperarValores();
		$.ajax({
      type: constantes.PETICION_TIPO_POST,
      url: referenciaModulo.URL_GUARDAR, 
      contentType: referenciaModulo.TIPO_CONTENIDO, 
      data: JSON.stringify(eRegistro),	
      success: function(respuesta) {
        if (!respuesta.estado) {
          referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
        } else {
          referenciaModulo.iniciarListado(respuesta.mensaje);
        }
        referenciaModulo.obj.ocultaContenedorFormulario.hide();
      },			    		    
      error: function() {
        referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
      }
		});
	} else {
    referenciaModulo.obj.ocultaContenedorFormulario.hide();
	}
};

moduloUsuario.prototype.iniciarListado= function(mensaje){
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

moduloUsuario.prototype.actualizarRegistro= function(){
  //Ocultar alertas de mensaje
  var referenciaModulo = this;
  if (referenciaModulo.obj.frmPrincipal.valid()){
    referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
    referenciaModulo.obj.ocultaContenedorFormulario.show();
    var eRegistro = referenciaModulo.recuperarValores();
    $.ajax({
      type: constantes.PETICION_TIPO_POST,
      url: referenciaModulo.URL_ACTUALIZAR, 
      contentType: referenciaModulo.TIPO_CONTENIDO, 
      data: JSON.stringify(eRegistro),	
      success: function(respuesta) {
        if (!respuesta.estado) {
          referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
        } 	else {		    				    			    		
          referenciaModulo.iniciarListado(respuesta.mensaje);
        }
        referenciaModulo.obj.ocultaContenedorFormulario.hide();
      },			    		    
      error: function() {
        referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
        referenciaModulo.obj.ocultaContenedorFormulario.hide();
      }
    });
  } else {

  }	
};

moduloUsuario.prototype.iniciarContenedores = function(){
  var referenciaModulo = this;
  try {
    referenciaModulo.obj.cntFormulario.hide();	
    referenciaModulo.protegeFormulario(false);
    referenciaModulo.obj.cntTabla.show();
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  };
};

moduloUsuario.prototype.solicitarActualizarEstado= function(){
	try {
		this.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
		this.obj.frmConfirmarModificarEstado.modal("show");
	} catch(error){
		referenciaModulo.mostrarDepuracion(error.message);
	}
};

moduloUsuario.prototype.actualizarEstadoRegistro= function(){
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

moduloUsuario.prototype.asignarAutorizacion= function(idSeleccionado, autorizacion){
//Implementar en cada caso
};	

moduloUsuario.prototype.recuperarAutorizaciones= function(){
//Implementar en cada caso
};
	
moduloUsuario.prototype.inicializarCampos= function(){
//Implementar en cada caso
};

moduloUsuario.prototype.llenarFormulario = function(registro){
//Implementar en cada caso	
};

moduloUsuario.prototype.llenarDetalles = function(registro){
//Implementar en cada caso
};

moduloUsuario.prototype.recuperarValores = function(registro){
var eRegistro = {};
//Implementar en cada caso
return eRegistro;
};

moduloUsuario.prototype.grillaDespuesSeleccionar= function(indice){
//TODO implementar
};

moduloUsuario.prototype.despuesListarRegistros=function(){
//Implementar en cada caso
};