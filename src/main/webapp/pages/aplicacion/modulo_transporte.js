function moduloTransporte (){
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
  
  this.reglasValidacionFormularioEvento={};
  this.mensajesValidacionFormularioEvento={};
  
  this.reglasValidacionFormularioPesaje={};
  this.mensajesValidacionFormularioPesaje={};
  
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
  
  this.NUMERO_REGISTROS_PAGINA_TRANSPORTE = constantes.NUMERO_REGISTROS_PAGINA_TRANSPORTE;
  this.TOPES_PAGINACION_TRANSPORTE = constantes.TOPES_PAGINACION_TRANSPORTE;
  this.columnasGrillaSecundaria={};
  this.definicionColumnasSecundaria=[];
  this.ordenGrillaSecundaria=[[ 1, 'asc' ]];
  this.columnasGrillaSecundaria=[{ "data": null} ];//Target 0
  // this.cmpFitlroEstado.val("2");
  this.definicionColumnasSecundaria=[{
    "targets": 0,
    "searchable": false,
    "orderable": false,      
    "visible":true,
    "render": function ( datos, tipo, fila, meta ) {
        var configuracion =meta.settings;
        return configuracion._iDisplayStart + meta.row + 1;}
  }]; 
  
  this.columnasTerceraGrilla={};
  this.definicionColumnasTerceraGrilla=[];
  this.ordenTerceraGrilla=[[ 1, 'asc' ]];
  this.columnasTerceraGrilla=[{ "data": null} ];//Target 0
  // this.cmpFitlroEstado.val("2");
  this.definicionColumnasTerceraGrilla=[{
    "targets": 0,
    "searchable": false,
    "orderable": false,      
    "visible":true,
    "render": function ( datos, tipo, fila, meta ) {
        var configuracion =meta.settings;
        return configuracion._iDisplayStart + meta.row + 1;}
  }]; 
};

moduloTransporte.prototype.mostrarDepuracion=function(mensaje){
  var referenciaModulo=this;
  if (referenciaModulo.depuracionActivada=true){
    console.log(mensaje);
  }
};

moduloTransporte.prototype.inicializar=function(){
  this.mostrarDepuracion("inicializar");
  this.inicializarControlesGenericos();
  this.inicializarGrilla();
  this.inicializarGrillaSecundaria();
  this.inicializarTerceraGrilla();
  this.inicializarFormularioPrincipal();
  this.inicializarFormularioEvento();
  this.inicializarFormularioPesaje();
  this.inicializarCampos();
};

moduloTransporte.prototype.resetearFormularioPrincipal= function(){
  var referenciaModulo= this;
  referenciaModulo.obj.frmPrincipal[0].reset();
  jQuery.each( this.obj, function( i, val ) {
    if (typeof referenciaModulo.obj[i].tipoControl != "undefined" ){
      if (referenciaModulo.obj[i].tipoControl =="select2"){
        referenciaModulo.obj[i].select2("val", null);
      }
    }
  });
};

moduloTransporte.prototype.inicializarControlesGenericos=function(){
  this.mostrarDepuracion("inicializarControlesGenericos");
  var referenciaModulo=this;
  //Contenedores
  this.obj.cntTabla=$("#cntTabla");							//1
  this.obj.cntDetalleTransporte=$("#cntDetalleTransporte"); //2
  this.obj.cntFormulario=$("#cntFormulario");				//3
  this.obj.cntVistaRegistro=$("#cntVistaRegistro");			//4
  this.obj.cntEventoTransporte=$("#cntEventoTransporte");	//5
  this.obj.cntPesajeTransporte=$("#cntPesajeTransporte");	//6
  //contenedor de detalle de transporte (sheepit)
  this.obj.contenedorDetalles=$("#contenedorDetalles");
  //formularios
  this.obj.frmPrincipal = $("#frmPrincipal");
  this.obj.frmPesaje = $("#frmPesaje");
  this.obj.frmEvento = $("#frmEvento");
  //dataTables
  this.obj.tablaPrincipal=$('#tablaPrincipal');
  this.obj.tablaSecundaria=$('#tablaSecundaria');
  this.obj.terceraTabla=$('#terceraTabla');
  //botones
  this.obj.btnFiltrar=$("#btnFiltrar");
  this.obj.btnDetalle=$("#btnDetalle");
  this.obj.btnAgregarTransporte=$("#btnAgregarTransporte");
  this.obj.btnModificarTransporte=$("#btnModificarTransporte");
  this.obj.btnImportar=$("#btnImportar");
  this.obj.btnVer=$("#btnVer");
  this.obj.btnEvento=$("#btnEvento");
  this.obj.btnPesaje=$("#btnPesaje");
  this.obj.btnCerrarDetalleTransporte=$("#btnCerrarDetalleTransporte");
  this.obj.btnGuardar=$("#btnGuardar");
  this.obj.btnCancelarGuardarFormulario=$("#btnCancelarGuardarFormulario");
  this.obj.btnCerrarVista=$("#btnCerrarVista");
  //botones de formulario Evento
  this.obj.btnGuardarEvento=$("#btnGuardarEvento");
  this.obj.btnCancelarGuardarEvento=$("#btnCancelarGuardarEvento");
  //botones de formulario Pesaje
  this.obj.btnGuardarPesaje=$("#btnGuardarPesaje");
  this.obj.btnCancelarGuardarPesaje=$("#btnCancelarGuardarPesaje");
  //titulo de las pantallas
  this.obj.cmpTituloFormulario=$("#cmpTituloFormulario");	
  //Protectores de pantallas
  this.obj.ocultaContenedorTabla=$("#ocultaContenedorTabla");
  this.obj.ocultaContenedorTabla=$("#ocultaContenedorDetalleTransporte");
  this.obj.ocultaContenedorFormulario=$("#ocultaContenedorFormulario");
  this.obj.ocultaContenedorVista=$("#ocultaContenedorVista");
  this.obj.ocultaContenedorTabla=$("#ocultaContenedorFormularioEvento");
  this.obj.ocultaContenedorTabla=$("#ocultaContenedorFormularioPesaje");
  //banda para mensajes de error o exito
  this.obj.bandaInformacion=$("#bandaInformacion");
  //Identificadores principales para la recuperacion del registro
  this.obj.idDiaOperativo=$("#idDiaOperativo");
  this.obj.idTransporte=$("#idTransporte");
  this.obj.clienteSeleccionado=$("#clienteSeleccionado");
  this.obj.operacionSeleccionado=$("#operacionSeleccionado");
  this.obj.fechaPlanificacionSeleccionado=$("#fechaPlanificacionSeleccionado");
  this.obj.cmpFechaInicial=$("#cmpFechaInicial");
	this.obj.cmpFechaFinal=$("#cmpFechaFinal");
  this.obj.idCliente=$("#idCliente");
  this.obj.idTransportista=$("#idTransportista");
	
  
  
  //Inicializar controles	
	this.obj.frmConfirmarModificarEstado=$("#frmConfirmarModificarEstado");	
	this.obj.frmConfirmarEliminar=$("#frmConfirmarEliminar");
	this.obj.btnListar=$("#btnListar");
	this.obj.btnAgregar=$("#btnAgregar");
	this.obj.btnModificar=$("#btnModificar");
	this.obj.btnModificarEstado=$("#btnModificarEstado");	
	this.obj.btnConfirmarModificarEstado=$("#btnConfirmarModificarEstado");
	this.obj.btnEliminar=$("#btnEliminar");
	
	this.obj.btnConfirmarEliminar=$("#btnConfirmarEliminar");	
	
	this.obj.btnArribo=$("#btnArribo");
	this.obj.btnCancelarGuardar=$("#btnCancelarGuardar");
	
	
	//estos valores para hacer los filtros de los listados	
	this.obj.txtFiltro=$("#txtFiltro");
	this.obj.cmpFiltroEstado=$("#cmpFiltroEstado");
	
	this.obj.filtroOperacion=$("#filtroOperacion");
	this.obj.filtroFechaPlanificada=$("#filtroFechaPlanificada");
	this.obj.cmpFiltroUsuario=$("#cmpFiltroUsuario");
	this.obj.cmpFiltroTabla=$("#cmpFiltroTabla");
	this.obj.cmpFiltroTipoFecha=$("#cmpFiltroTipoFecha");	
	
	//eventos click
	this.obj.btnFiltrar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonFiltro();
	});

	this.obj.btnDetalle.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonDetalle();
	});
	
	this.obj.btnAgregarTransporte.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonAgregarTransporte();
	});
	
	this.obj.btnModificarTransporte.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonModificarTransporte();
	});
	
	this.obj.btnVer.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonVer();
	});
	
	this.obj.btnEvento.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonEvento();
	});
	
	this.obj.btnPesaje.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonPesaje();		
	});	
	
	this.obj.btnCerrarDetalleTransporte.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonCerrarDetalleTransporte();		
	});	
	
	this.obj.btnGuardar.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonGuardar();
	});
	
	this.obj.btnCancelarGuardarFormulario.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonCancelarGuardarFormulario();		
	});	
	
	this.obj.btnCerrarVista.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonCerrarVista();
	});
	
	this.obj.btnGuardarEvento.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonGuardarEvento();
	});
	
	this.obj.btnCancelarGuardarEvento.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonCancelarGuardarEvento();
	});
	
	this.obj.btnGuardarPesaje.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonGuardarPesaje();
	});
	
	this.obj.btnCancelarGuardarPesaje.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
		referenciaModulo.botonCancelarGuardarPesaje();
	});
	
};

moduloTransporte.prototype.botonFiltro = function(){
	var referenciaModulo = this;
    try {							    
		referenciaModulo.listarRegistros();
    } catch(error){
  	  console.log(error.message);
    }
};

moduloTransporte.prototype.botonDetalle = function(){
	var referenciaModulo = this;
    try {
    	referenciaModulo.modoEdicion = constantes.MODO_DETALLE_TRANSPORTE;
    	referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_DETALLE_TRANSPORTE);
    	referenciaModulo.obj.cntTabla.hide();
    	referenciaModulo.obj.cntDetalleTransporte.show();
    	referenciaModulo.obj.cntFormulario.hide();
    	referenciaModulo.obj.cntVistaRegistro.hide();
    	referenciaModulo.obj.cntEventoTransporte.hide();
    	referenciaModulo.obj.cntPesajeTransporte.hide();
    	referenciaModulo.llenarDetalleTransporte();//esto para llenar la cabecera
    	this.obj.datSecundariaApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);
    	this.obj.datTerceraTablaApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	
  	  	referenciaModulo.desactivarBotones();
    } catch(error){
  	  console.log(error.message);
    }
};

moduloTransporte.prototype.botonAgregarTransporte = function(){
	var referenciaModulo=this;
	referenciaModulo.inicializarFormularioPrincipal();
	referenciaModulo.resetearFormularioPrincipal();
    try {
	    referenciaModulo.modoEdicion=constantes.MODO_FORMULARIO_TRANSPORTE_NUEVO;
	    referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_AGREGAR_REGISTRO);
	    referenciaModulo.obj.contenedorDetalles.hide();
	    referenciaModulo.obj.cntTabla.hide();
	    referenciaModulo.obj.cntDetalleTransporte.hide();
	    referenciaModulo.obj.cntFormulario.show();
	    referenciaModulo.obj.cntVistaRegistro.hide();
	    referenciaModulo.obj.cntEventoTransporte.hide();
	    referenciaModulo.obj.cntPesajeTransporte.hide();
	    referenciaModulo.recuperarRegistro();
	} catch(error){
	  console.log(error.message);
	}
};

moduloTransporte.prototype.botonModificarTransporte = function(){
	var referenciaModulo=this;
	referenciaModulo.inicializarFormularioPrincipal();
	referenciaModulo.resetearFormularioPrincipal();
    try {
	    referenciaModulo.modoEdicion=constantes.MODO_FORMULARIO_TRANSPORTE_MODIFICAR;
	    referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_MODIFICA_REGISTRO);
	    referenciaModulo.obj.contenedorDetalles.show();
	    referenciaModulo.obj.cntTabla.hide();
	    referenciaModulo.obj.cntDetalleTransporte.hide();
	    referenciaModulo.obj.cntFormulario.show();
	    referenciaModulo.obj.cntVistaRegistro.hide();
	    referenciaModulo.obj.cntEventoTransporte.hide();
	    referenciaModulo.obj.cntPesajeTransporte.hide();
	    referenciaModulo.recuperarRegistro();
    } catch(error){
    	console.log(error.message);
    }
};

moduloTransporte.prototype.botonVer = function(){
	var referenciaModulo=this;
    try {
	    referenciaModulo.modoEdicion=constantes.MODO_VER_TRANSPORTE;
	    referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_DETALLE_REGISTRO);
	    referenciaModulo.obj.cntTabla.hide();
	    referenciaModulo.obj.cntDetalleTransporte.hide();
	    referenciaModulo.obj.cntFormulario.hide();
	    referenciaModulo.obj.cntVistaRegistro.show();
	    referenciaModulo.obj.cntEventoTransporte.hide();
	    referenciaModulo.obj.cntPesajeTransporte.hide();
	    referenciaModulo.recuperarRegistro();
    } catch(error){
	    console.log(error.message);
    }	
};

moduloTransporte.prototype.botonEvento = function(){
	var referenciaModulo=this;
    try {
    	referenciaModulo.modoEdicion=constantes.MODO_EVENTO_TRANSPORTE;
    	referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_FORMULARIO_EVENTO);
    	referenciaModulo.obj.cntTabla.hide();
    	referenciaModulo.obj.cntDetalleTransporte.hide();
    	referenciaModulo.obj.cntFormulario.hide();
    	referenciaModulo.obj.cntVistaRegistro.hide();
    	referenciaModulo.obj.cntEventoTransporte.show();
    	referenciaModulo.obj.cntPesajeTransporte.hide();
    	referenciaModulo.obj.frmEvento[0].reset();
    	referenciaModulo.recuperarRegistro();
	} catch(error){
	    console.log(error.message);
	}
};

moduloTransporte.prototype.botonPesaje = function(){
	var referenciaModulo=this;
	try {
		referenciaModulo.modoEdicion=constantes.MODO_PESAJE_TRANSPORTE;
		referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_AGREGAR_PESAJE);
		referenciaModulo.obj.cntTabla.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntDetalleTransporte.hide();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntEventoTransporte.hide();
		referenciaModulo.obj.cntPesajeTransporte.show();
		referenciaModulo.obj.frmPesaje[0].reset();
		referenciaModulo.recuperarRegistro();
    } catch(error){
	    console.log(error.message);
    }
};

moduloTransporte.prototype.botonCerrarDetalleTransporte = function(){
	var referenciaModulo=this;
	try {
		referenciaModulo.modoEdicion=constantes.MODO_LISTAR_TRANSPORTE;
		referenciaModulo.obj.cntTabla.show();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntDetalleTransporte.hide();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntEventoTransporte.hide();
		referenciaModulo.obj.cntPesajeTransporte.hide();
		referenciaModulo.obj.btnDetalle.removeClass("disabled");
    } catch(error){
	    console.log(error.message);
    }
};

moduloTransporte.prototype.botonGuardar = function(){
	var referenciaModulo=this;
	console.log("entra botonGuardar  ");
	if (referenciaModulo.modoEdicion == constantes.MODO_FORMULARIO_TRANSPORTE_NUEVO){
		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Detalle de Transporte validado.");
		referenciaModulo.guardarRegistro();
	} else if  (referenciaModulo.modoEdicion == constantes.MODO_FORMULARIO_TRANSPORTE_MODIFICAR){
		referenciaModulo.actualizarRegistro();
	}
};

moduloTransporte.prototype.botonCancelarGuardarFormulario = function(){
	var referenciaModulo=this;
	referenciaModulo.resetearFormularioPrincipal();
	try {
		referenciaModulo.modoEdicion = constantes.MODO_DETALLE_TRANSPORTE;
		referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_DETALLE_TRANSPORTE);
		referenciaModulo.obj.cntTabla.hide();
		referenciaModulo.obj.cntDetalleTransporte.show();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntEventoTransporte.hide();
		referenciaModulo.obj.cntPesajeTransporte.hide();
	} catch (error) {
		console.log(error.message);
	}
};

moduloTransporte.prototype.botonCerrarVista= function(){  
	var referenciaModulo = this;
	try {
		referenciaModulo.modoEdicion = constantes.MODO_DETALLE_TRANSPORTE;
		referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_DETALLE_TRANSPORTE);
		referenciaModulo.obj.cntTabla.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntDetalleTransporte.show();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntEventoTransporte.hide();
	} catch(error){
		console.log(error.message);
	};
};

moduloTransporte.prototype.botonGuardarEvento= function(){
	var referenciaModulo = this;
	try {
		referenciaModulo.obj.cntTabla.hide();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntPesajeTransporte.hide();
		referenciaModulo.guardarEvento();
	} catch (error) {
		console.log(error.message);
	}
};

moduloTransporte.prototype.botonCancelarGuardarEvento= function(){
	var referenciaModulo = this;
	try {
		referenciaModulo.modoEdicion = constantes.MODO_DETALLE_TRANSPORTE;
		referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_DETALLE_TRANSPORTE);
		referenciaModulo.obj.cntTabla.hide();
		referenciaModulo.obj.cntDetalleTransporte.show();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntEventoTransporte.hide();
		referenciaModulo.obj.cntPesajeTransporte.hide();
	} catch (error) {
		console.log(error.message);
	}
};

moduloTransporte.prototype.botonGuardarPesaje= function(){
	var referenciaModulo = this;
	try {
		referenciaModulo.obj.cntTabla.hide();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntEventoTransporte.hide();
		referenciaModulo.guardarPesaje();
	} catch (error) {
		console.log(error.message);
	}
};

moduloTransporte.prototype.botonCancelarGuardarPesaje= function(){
	var referenciaModulo = this;
	try {
		referenciaModulo.modoEdicion = constantes.MODO_DETALLE_TRANSPORTE;
		referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_DETALLE_TRANSPORTE);
		referenciaModulo.obj.cntTabla.hide();
		referenciaModulo.obj.cntDetalleTransporte.show();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntEventoTransporte.hide();
		referenciaModulo.obj.cntPesajeTransporte.hide();
	} catch (error) {
		console.log(error.message);
	}
};

moduloTransporte.prototype.inicializarGrilla=function(){
  //Nota no retornar el objeto solo manipular directamente
	//Establecer grilla y su configuracion
  var referenciaModulo=this;
	this.obj.tablaPrincipal.on('xhr.dt', function (e,settings,json) {
    referenciaModulo.mostrarDepuracion("xhr.dt");
    referenciaModulo.desactivarBotones();
		if (json.estado==true){
			json.recordsTotal=json.contenido.totalRegistros;
			json.recordsFiltered=json.contenido.totalEncontrados;
			json.data= json.contenido.carga;
			if (referenciaModulo.modoEdicion==constantes.MODO_LISTAR){
				referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
			}
			//
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
	});
  
  this.obj.tablaPrincipal.on('preXhr.dt', function ( e, settings, data ) {
    //Se ejecuta antes de cualquier llamada ajax
    referenciaModulo.mostrarDepuracion("preXhr");
    if (referenciaModulo.estaCargadaInterface==true){
      referenciaModulo.obj.ocultaContenedorTabla.show();
    }
  });
  
  this.obj.tablaPrincipal.on('page.dt', function () {
    //Se ejecuta cuando se hace clic en boton de paginacion
    referenciaModulo.mostrarDepuracion("page.dt");
  });
  
  this.obj.tablaPrincipal.on('order.dt', function () {
    //Se ejecuta cuando se hace clic en alguna cabecera de columna para ordenarla
    referenciaModulo.mostrarDepuracion("order.dt");
  });

	this.obj.datClienteApi= this.obj.tablaPrincipal.DataTable({
		"processing": true,
		"responsive": true,
		"dom": '<"row" <"col-sm-12" t> ><"row" <"col-sm-3"l> <"col-sm-4"i> <"col-sm-5"p>>',
		"iDisplayLength": referenciaModulo.NUMERO_REGISTROS_PAGINA,
		"lengthMenu": referenciaModulo.TOPES_PAGINACION,
		"language": {
      "url": referenciaModulo.URL_LENGUAJE_GRILLA
    },
    "serverSide": true,
		"ajax": {
		      "url": referenciaModulo.URL_LISTAR,
		      "type":"GET",
		      "data": function (d) {
		    	  console.log("entra en el ajax");
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
		//"order": referenciaModulo.ordenGrilla
	});	
	
	$('#tablaPrincipal tbody').on( 'click', 'tr', function () {
    if ( $(this).hasClass('selected') ) {
      $(this).removeClass('selected');
    } else {
      referenciaModulo.obj.datClienteApi.$('tr.selected').removeClass('selected');
      $(this).addClass('selected');
    }
		var indiceFila = referenciaModulo.obj.datClienteApi.row( this ).index();
		var idRegistro = referenciaModulo.obj.datClienteApi.cell(indiceFila,1).data();
		referenciaModulo.idRegistro = idRegistro;		

		referenciaModulo.idDiaOperativo = referenciaModulo.obj.datClienteApi.cell(indiceFila,1).data();
		referenciaModulo.idTransporte = -1;

		referenciaModulo.idCliente = referenciaModulo.obj.datClienteApi.cell(indiceFila,10).data();
		referenciaModulo.obj.clienteSeleccionado = referenciaModulo.obj.datClienteApi.cell(indiceFila,9).data();
		referenciaModulo.obj.operacionSeleccionado = referenciaModulo.obj.datClienteApi.cell(indiceFila,8).data();
		referenciaModulo.obj.fechaPlanificacionSeleccionado = referenciaModulo.obj.datClienteApi.cell(indiceFila,2).data();
		referenciaModulo.obj.btnDetalle.removeClass("disabled");
	});
};

//ESTO PARA LA GRILLA SECUNDARIA
//TODO
moduloTransporte.prototype.llamadaAjaxGrillaSecundaria=function(e,configuracion,json){
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
	 // referenciaModulo.obj.ocultaContenedorTabla.hide();  
	};

moduloTransporte.prototype.inicializarGrillaSecundaria=function(){
//Nota no retornar el objeto solo manipular directamente
	//Establecer grilla y su configuracion
var referenciaModulo=this;
this.obj.tablaSecundaria.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
	   referenciaModulo.llamadaAjaxGrillaSecundaria(e,configuracion,json);
});
};

moduloTransporte.prototype.inicializarGrillaSecundaria = function(){
	  //Nota no retornar el objeto solo manipular directamente
	  //Establecer grilla y su configuracion
	  var referenciaModulo=this;
	  //referenciaModulo.obj.ocultaContenedorAutorizacion.show();
	  
	  this.obj.tablaSecundaria.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
		   referenciaModulo.llamadaAjaxGrillaSecundaria(e,configuracion,json);
	  });
	  
	  this.obj.tablaSecundaria.on(constantes.DT_EVENTO_PREAJAX, function (e,configuracion,datos) {
	    //Se ejecuta antes de cualquier llamada ajax
	    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_PREAJAX);
	    //referenciaModulo.obj.ocultaContenedorAutorizacion.show();
	    if (referenciaModulo.estaCargadaInterface==true){
	    	//referenciaModulo.obj.ocultaContenedorAutorizacion.hide();
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
		  this.obj.datSecundariaApi = this.obj.tablaSecundaria.DataTable({
		    "processing": true,
		    "responsive": true,
		    "dom": constantes.DT_ESTRUCTURA,
			"iDisplayLength": referenciaModulo.NUMERO_REGISTROS_PAGINA_TRANSPORTE,
		    "lengthMenu": referenciaModulo.TOPES_PAGINACION_TRANSPORTE,
		    "language": { "url": referenciaModulo.URL_LENGUAJE_GRILLA, },
		    "serverSide": true,
		    "ajax": {
			    "url": "./transporte/listarTransportes",
			    "type":constantes.PETICION_TIPO_GET,
	  		    "data": function (parametros) {
	  		    	  console.log(referenciaModulo.idRegistro);
	    		      return {
	    		    	  ID:	referenciaModulo.idRegistro, 
	    		    };
	  		    },
			 },
		    "columns": referenciaModulo.columnasGrillaSecundaria,
		    "columnDefs": referenciaModulo.definicionColumnasSecundaria,
		});	

	  	$('#tablaSecundaria tbody').on( 'click', 'tr', function () {
	  		 //var referenciaModulo = this;
		     if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		     } else {
		    	 referenciaModulo.obj.datSecundariaApi.$('tr.selected').removeClass('selected');
		     	$(this).addClass('selected');
		     }
			var indiceFilaTransporte = referenciaModulo.obj.datSecundariaApi.row( this ).index();
			referenciaModulo.idTransporte = referenciaModulo.obj.datSecundariaApi.cell(indiceFilaTransporte, 1).data();
			var origenRegistro = referenciaModulo.obj.datSecundariaApi.cell(indiceFilaTransporte, 8).data();
			referenciaModulo.activarBotones();
			if (origenRegistro == "M") {
				referenciaModulo.obj.btnModificarTransporte.removeClass("disabled");
			} else if (origenRegistro == "A") {
				referenciaModulo.obj.btnModificarTransporte.addClass("disabled");
			}
		});
	} catch(error){
	    console.log(error.message);
	}
};

//ESTO PARA LA TERCERA GRILLA
//TODO
moduloTransporte.prototype.llamadaAjaxTerceraGrilla=function(e,configuracion,json){
	  var referenciaModulo=this;
	  referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_AJAX);
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
	};

moduloTransporte.prototype.inicializarTerceraGrilla=function(){
//Nota no retornar el objeto solo manipular directamente
	//Establecer grilla y su configuracion
var referenciaModulo=this;
this.obj.terceraTabla.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
	   referenciaModulo.llamadaAjaxTerceraGrilla(e,configuracion,json);
});
};

moduloTransporte.prototype.inicializarTerceraGrilla = function(){
	  //Nota no retornar el objeto solo manipular directamente
	  //Establecer grilla y su configuracion
	  var referenciaModulo=this;
	  //referenciaModulo.obj.ocultaContenedorAutorizacion.show();
	  
	  this.obj.terceraTabla.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
		   referenciaModulo.llamadaAjaxTerceraGrilla(e,configuracion,json);
	  });
	  
	  this.obj.terceraTabla.on(constantes.DT_EVENTO_PREAJAX, function (e,configuracion,datos) {
	    //Se ejecuta antes de cualquier llamada ajax
	    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_PREAJAX);
	    //referenciaModulo.obj.ocultaContenedorAutorizacion.show();
	    if (referenciaModulo.estaCargadaInterface==true){
	    	//referenciaModulo.obj.ocultaContenedorAutorizacion.hide();
	    }
	  });

	  this.obj.terceraTabla.on(constantes.DT_EVENTO_PAGINACION, function () {
	  //Se ejecuta cuando se hace clic en boton de paginacion
	    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_PAGINACION);
	  });

	  this.obj.terceraTabla.on(constantes.DT_EVENTO_ORDENACION, function () {
	  //Se ejecuta cuando se hace clic en alguna cabecera de columna para ordenarla
	    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_ORDENACION);
	  });
	  try{
		  this.obj.datTerceraTablaApi = this.obj.terceraTabla.DataTable({
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
		    "url": "./transporte/listarAsignacionTransportes",
		    "type":constantes.PETICION_TIPO_GET,
  		    "data": function (parametros) {
  		    	  console.log(referenciaModulo.idRegistro);
    		      return {
    		    	  ID:	referenciaModulo.idRegistro, 
    		      };
  		    	},
		    },
		    "columns": referenciaModulo.columnasTerceraGrilla,
		    "columnDefs": referenciaModulo.definicionColumnasTerceraGrilla,
		});	

		/*  //TODO
	  	$('#tablaSecundaria tbody').on( 'click', 'tr', function () {
	  		 //var referenciaModulo = this;
		     if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		     } else {
		    	 referenciaModulo.obj.datSecundariaApi.$('tr.selected').removeClass('selected');
		     	$(this).addClass('selected');
		     }
			var indiceFilaTransporte = referenciaModulo.obj.datSecundariaApi.row( this ).index();
			referenciaModulo.idTransporte = referenciaModulo.obj.datSecundariaApi.cell(indiceFilaTransporte, 1).data();
			var origenRegistro = referenciaModulo.obj.datSecundariaApi.cell(indiceFilaTransporte, 8).data();
			referenciaModulo.activarBotones();
			if (origenRegistro == "M") {
				referenciaModulo.obj.btnModificarTransporte.removeClass("disabled");
			} else if (origenRegistro == "A") {
				referenciaModulo.obj.btnModificarTransporte.addClass("disabled");
			}
		});*/
	} catch(error){
	    console.log(error.message);
	}
};

moduloTransporte.prototype.inicializarFormularioPrincipal= function(){  
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

moduloTransporte.prototype.inicializarFormularioEvento= function(){  
	//Establecer validaciones del formulario
  var referenciaModulo=this;
  this.obj.frmEvento.validate({
    rules: referenciaModulo.reglasValidacionFormularioEvento,
    messages: referenciaModulo.mensajesValidacionFormularioEvento,
    submitHandler: function(form) {
    // form.submit();
    }
  });
};

moduloTransporte.prototype.inicializarFormularioPesaje= function(){  
	//Establecer validaciones del formulario
  var referenciaModulo=this;
  this.obj.frmPesaje.validate({
    rules: referenciaModulo.reglasValidacionFormularioPesaje,
    messages: referenciaModulo.mensajesValidacionFormularioPesaje,
    submitHandler: function(form) {
    // form.submit();
    }
  });
};


moduloTransporte.prototype.activarBotones=function(){
	this.obj.btnDetalle.addClass("disabled");
	this.obj.btnAgregarTransporte.removeClass("disabled");
	this.obj.btnImportar.addClass("disabled");
	this.obj.btnVer.removeClass("disabled");
	this.obj.btnEvento.removeClass("disabled");
	this.obj.btnPesaje.removeClass("disabled");
};

moduloTransporte.prototype.desactivarBotones=function(){
    //habilitamos agregarTransporte
	this.obj.btnAgregarTransporte.removeClass("disabled");
 	//estos botones deshabilitados
	this.obj.btnDetalle.addClass("disabled");
	this.obj.btnModificarTransporte.addClass("disabled");
	this.obj.btnImportar.addClass("disabled");
	this.obj.btnVer.addClass("disabled");
	this.obj.btnEvento.addClass("disabled");
	this.obj.btnPesaje.addClass("disabled");
};

moduloTransporte.prototype.listarRegistros = function(){
	var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("listarRegistros");
  console.log("entra en antes del reload de la tabla principal");
	this.obj.datClienteApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	
	console.log("despues en antes del reload de la tabla principal");
};

moduloTransporte.prototype.listarTransportes = function(){
	var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("listarRegistros de tabla secundaria");
  
	this.obj.datSecundariaApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	 
	this.obj.datTerceraTablaApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	
};

moduloTransporte.prototype.actualizarBandaInformacion=function(tipo, mensaje){
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

moduloTransporte.prototype.recuperarRegistro= function(){
	console.log("entra en recuperarRegistro");
	var referenciaModulo = this;
	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petición...");
	//referenciaModulo.protegeFormulario(true);

	 //identificadores para las busquedas
	var doperativo = referenciaModulo.idDiaOperativo;
	var transporte = referenciaModulo.idTransporte;
	
	$.ajax({
	    type: "GET",
	    url: referenciaModulo.URL_RECUPERAR, 
	    contentType: "application/json", 
	    data: {ID:doperativo, IDTransporte: transporte},	
	    success: function(respuesta) {
	    	if (!respuesta.estado) {
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
	    	} else {		 
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
	    		if (referenciaModulo.modoEdicion == constantes.MODO_FORMULARIO_TRANSPORTE_NUEVO){
	    			referenciaModulo.iniciarAgregar(respuesta.contenido.carga[0]); 
	    		} else if (referenciaModulo.modoEdicion == constantes.MODO_FORMULARIO_TRANSPORTE_MODIFICAR){
	    			referenciaModulo.llenarFormulario(respuesta.contenido.carga[0]);
	    		} else if (referenciaModulo.modoEdicion == constantes.MODO_VER_TRANSPORTE){
	    			referenciaModulo.llenarDetalles(respuesta.contenido.carga[0]);
	    		} else if (referenciaModulo.modoEdicion == constantes.MODO_EVENTO_TRANSPORTE){
	    			referenciaModulo.llenarEventoTransporte(respuesta.contenido.carga[0]);
	    		} else if (referenciaModulo.modoEdicion == constantes.MODO_PESAJE_TRANSPORTE){
	    			referenciaModulo.llenarPesajeTransporte(respuesta.contenido.carga[0]);
	    		}
    		}
	    },			    		    
	    error: function() {
	    	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la peticion");
	    }
	});
};

moduloTransporte.prototype.verRegistro= function(){
	var referenciaModulo = this;
	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petición...");
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
	    	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la peticion");
	    	referenciaModulo.obj.ocultaContenedorVista.show();
	    }
	});
};

moduloTransporte.prototype.guardarRegistro= function(){
	var referenciaModulo = this;
	referenciaModulo.mostrarDepuracion("guardarRegistro");
		if (referenciaModulo.obj.frmPrincipal.valid()){
		if (this.validarDetallesTransporte()){

			referenciaModulo.obj.ocultaContenedorFormulario.show();
			referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petición...");
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
			        	referenciaModulo.recuperarRegistro();
			        	referenciaModulo.iniciarListado();
			        }
			        referenciaModulo.obj.ocultaContenedorFormulario.hide();
				},			    		    
				error: function() {
		        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la peticion");
		     }
		   });
		} else{
			referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Al menos un Detalle de Transporte debe estar informado.");
			referenciaModulo.obj.ocultaContenedorFormulario.hide();
		}
	} else {
		referenciaModulo.obj.ocultaContenedorFormulario.hide();
	}
};

moduloTransporte.prototype.iniciarListado= function(mensaje){
	var referenciaModulo = this;
	console.log("entra en iniciarListado");
	try{
		referenciaModulo.modoEdicion = constantes.MODO_DETALLE_TRANSPORTE;
		referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_DETALLE_TRANSPORTE);
		referenciaModulo.obj.cntTabla.hide();
		referenciaModulo.obj.cntDetalleTransporte.show();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntEventoTransporte.hide();
		referenciaModulo.obj.cntPesajeTransporte.hide();
		console.log("this.obj.datSecundariaApi.ajax.reload");
		this.obj.datSecundariaApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);
		this.obj.datTerceraTablaApi.ajax.reload(referenciaModulo.despuesListarRegistros,true);	
		referenciaModulo.desactivarBotones();
		//referenciaModulo.recuperarRegistro();
	} catch(error){
	    referenciaModulo.mostrarDepuracion(error.message);
	};
};

moduloTransporte.prototype.actualizarRegistro= function(){
	//Ocultar alertas de mensaje
	var referenciaModulo = this;
	if (referenciaModulo.obj.frmPrincipal.valid()){
		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petición...");
    referenciaModulo.obj.ocultaContenedorFormulario.show();
    var eRegistro = referenciaModulo.recuperarValores();
    console.log(eRegistro);
		$.ajax({
		    type: constantes.PETICION_TIPO_POST,
		    url: referenciaModulo.URL_ACTUALIZAR, 
		    contentType: referenciaModulo.TIPO_CONTENIDO, 
		    data: JSON.stringify(eRegistro),	
		    success: function(respuesta) {
		    	if (!respuesta.estado) {
		    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
		    	} 	else {		    			
		    		referenciaModulo.recuperarRegistro();
		    		referenciaModulo.iniciarListado(respuesta.mensaje);
	    		}
          referenciaModulo.obj.ocultaContenedorFormulario.hide();
		    },			    		    
		    error: function() {
		    	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la peticion");
          referenciaModulo.obj.ocultaContenedorFormulario.hide();
		    }
		});
	} else {
	
	}	
};

moduloTransporte.prototype.iniciarContenedores = function(){
	console.log("iniciarContenedores ");
	var referenciaModulo = this;
	try {
		referenciaModulo.modoEdicion = constantes.MODO_DETALLE_TRANSPORTE;
		referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_DETALLE_TRANSPORTE);
		referenciaModulo.obj.cntTabla.hide();
		referenciaModulo.obj.cntDetalleTransporte.show();
		referenciaModulo.obj.cntFormulario.hide();
		referenciaModulo.obj.cntVistaRegistro.hide();
		referenciaModulo.obj.cntEventoTransporte.hide();
		referenciaModulo.obj.cntPesajeTransporte.hide();
		referenciaModulo.recuperarRegistro();
	} catch (error) {
		referenciaModulo.mostrarDepuracion(error.message);
	}
};

moduloTransporte.prototype.solicitarActualizarEstado= function(){
	try {
		this.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petición...");
		this.obj.frmConfirmarModificarEstado.modal("show");
	} catch(error){
		referenciaModulo.mostrarDepuracion(error.message);
	}
};

moduloTransporte.prototype.actualizarEstadoRegistro= function(){
  var eRegistro = {};
  var referenciaModulo=this;
	referenciaModulo.obj.frmConfirmarModificarEstado.modal("hide");
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petición...");
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
        referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la petición");
        //referenciaModulo.protegeFormulario(false);
    }
    });
	};

	
	
	
moduloTransporte.prototype.inicializarCampos= function(){
//Implementar en cada caso
};

moduloTransporte.prototype.llenarFormulario = function(registro){
//Implementar en cada caso	
};

moduloTransporte.prototype.llenarDetalles = function(registro){
//Implementar en cada caso
};

moduloTransporte.prototype.llenarDetalleTransporte = function(){
//Implementar en cada caso
};

moduloTransporte.prototype.recuperarValores = function(registro){
var eRegistro = {};
//Implementar en cada caso
return eRegistro;
};

moduloTransporte.prototype.grillaDespuesSeleccionar= function(indice){
//TODO implementar
};

moduloTransporte.prototype.despuesListarRegistros=function(){
//Implementar en cada caso
};