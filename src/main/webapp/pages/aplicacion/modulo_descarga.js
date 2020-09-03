function moduloDescarga(){
  this.obj = {};
  this.SEPARADOR_MILES = ",";
  this.NUMERO_REGISTROS_PAGINA = constantes.NUMERO_REGISTROS_PAGINA;
  this.TOPES_PAGINACION = constantes.TOPES_PAGINACION;
  this.URL_LENGUAJE_GRILLA="tema/datatable/language/es-ES.json";
  this.TIPO_CONTENIDO=constantes.TIPO_CONTENIDO_JSON;
  this.modoEdicion=constantes.MODO_LISTAR;
  this.NOMBRE_EVENTO_CLICK = constantes.NOMBRE_EVENTO_CLICK;
  this.depuracionActivada=true;
  this.estaCargadaInterface=false;
  this.URL_BASE='descarga';
  this.URL_LISTAR_DIA_OPERATIVO="../admin/dia_operativo/listarxdescarga";
  this.URL_LISTAR_CARGA = this.URL_BASE + '/listar-carga';
  this.URL_RECUPERAR_CARGA = this.URL_BASE + '/recuperar-carga';
  this.URL_GUARDAR_CARGA = this.URL_BASE + '/crear-carga';
  this.URL_ACTUALIZAR_CARGA = this.URL_BASE + '/actualizar-carga';
  this.URL_RECUPERAR_ESTACIONES="../admin/estacion/listar";
  this.URL_RECUPERAR_TANQUES="../admin/tanque/listar";
  //Inicializar propiedades
  this.mensajeEsMostrado=false;
  this.SECCION_PRINCIPAL=1;
  this.SECCION_DETALLE_CARGA=2;
  this.SECCION_DETALLE_DESCARGA=2;
  this.INDICE_COLUMNA_ID_DIA_OPERATIVO=1;
  this.INDICE_COLUMNA_FECHA_DIA_OPERATIVO=2;
  this.INDICE_COLUMNA_ESTADO_DIA_OPERATIVO=7;
  //
  this.GRILLA_DIA_OPERATIVO=1;
  this.GRILLA_CARGA_TANQUE=2;
  this.GRILLA_DESCARGA=3;  
  this.urlDataTableLocalization='../themes/default/plugins/datatables/lang/es.js';
  this.idRegistro = 0;
  this.idDiaOperativoSeleccionado=0;
  this.idOperacionSeleccionada=0;
  this.nombreOperacionSeleccionada="";
  this.idEstacionSeleccionada=0;
  this.fechaDiaOperativoSeleccionado="";
  this.columnasGrilla={};
  this.modoEdicionCargaTanque=null;
  this.definicionColumnas=[];
  this.reglasValidacionFormulario={};
  this.mensajesValidacionFormulario={};
  this.ordenGrillaDiaOperativo=[[ 2, 'asc' ]];
  this.columnasGrillaDiaOperativo=[{ "data": null} ];//Target 0
  this.definicionColumnasGrillaDiaOperativo = [{
    "targets": 0,
    "searchable": false,
    "orderable": false,
    "visible": true,
    "render": function ( datos, tipo, fila, meta ) {
      var configuracion = meta.settings;
      return configuracion._iDisplayStart + meta.row + 1;
    }
  }];
  
  this.ordenGrillaCargaTanque=[[ 1, 'asc' ]];
  this.columnasGrillaCargaTanque=[{ "data": null} ];//Target 0
  this.definicionColumnasGrillaCargaTanque = [{
    "targets": 0,
    "searchable": false,
    "orderable": false,
    "visible": true,
    "render": function ( datos, tipo, fila, meta ) {
      var configuracion = meta.settings;
      return configuracion._iDisplayStart + meta.row + 1;
    }
  }];  
}

moduloDescarga.prototype.mostrarDepuracion=function(mensaje){
  var referenciaModulo=this;
  if (referenciaModulo.depuracionActivada === true){
    console.log(mensaje);
  }
};

moduloDescarga.prototype.actualizarBandaInformacion=function(tipo, mensaje){
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

moduloDescarga.prototype.mostrarErrorServidor=function(xhr,estado,error){
  var referenciaModulo=this;
  if (xhr.status === constantes.ERROR_SIN_CONEXION) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,cadenas.ERROR_NO_CONECTADO);
  } else if (xhr.status === constantes.ERROR_RECURSO_NO_DISPONIBLE) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,cadenas.ERROR_RECURSO_NO_DISPONIBLE);
  } else if (xhr.status === constantes.ERROR_INTERNO_SERVIDOR) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,cadenas.ERROR_INTERNO_SERVIDOR);
  } else if (estado === constantes.ERROR_INTERPRETACION_DATOS) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,cadenas.ERROR_GENERICO_SERVIDOR);
  } else if (estado === constantes.ERROR_TIEMPO_AGOTADO) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,cadenas.ERROR_TIEMPO_AGOTADO);
  } else if (estado === constantes.ERROR_CONEXION_ABORTADA) {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,cadenas.ERROR_GENERICO_SERVIDOR);
  } else {
  referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,cadenas.ERROR_GENERICO_SERVIDOR);
  }
};

moduloDescarga.prototype.inicializarControles=function(){
  this.mostrarDepuracion("inicializarControles");
  var referenciaModulo=this; 
  //Controles
  //Seccion Dia Operativo
	this.obj.cntTablaDiaOperativo = $('#cntTablaDiaOperativo');
  this.obj.filtroOperacion = $("#filtroOperacion");
  this.obj.filtroFechaPlanificada = $("#filtroFechaPlanificada");  
  this.obj.tablaDiaOperativo=$("#tablaDiaOperativo");
  this.obj.ocultaContenedorTablaDiaOperativo=$("#ocultaContenedorTablaDiaOperativo");
  this.obj.btnDetalleDiaOperativo=$("#btnDetalleDiaOperativo");
  this.obj.btnFiltrarDiaOperativo=$("#btnFiltrarDiaOperativo");
  //Detalle dia Operativo
  this.obj.cntDetalleDiaOperativo=$("#cntDetalleDiaOperativo");
  this.obj.cmpCliente=$("#cmpCliente");
  this.obj.cmpOperacionDetalleDiaOperativo=$("#cmpOperacionDetalleDiaOperativo");
  this.obj.cmpFechaPlanificacionDetalleDiaOperativo=$("#cmpFechaPlanificacionDetalleDiaOperativo");
  this.obj.filtroEstacionDetalleDiaOperativo=$("#filtroEstacionDetalleDiaOperativo");
  this.obj.btnAgregarCarga=$("#btnAgregarCarga");
  this.obj.btnModificarCarga=$("#btnModificarCarga");
  this.obj.btnFiltrarCargaTanque=$("#btnFiltrarCargaTanque");
  this.obj.tablaCargaTanque=$("#tablaCargaTanque");
  this.obj.ocultaContenedorTablaCargaTanque=$("#ocultaContenedorTablaCargaTanque");
  this.obj.ocultaContenedorTablaDescarga=$("#ocultaContenedorTablaDescarga");
  //botones
  this.obj.btnAgregarDescarga=$("#btnAgregarDescarga");
  this.obj.btnModificarDescarga=$("#btnModificarDescarga");
  this.obj.btnVerDescarga=$("#btnVerDescarga");
  this.obj.btnAgregarEvento=$("#btnAgregarEvento");
  this.obj.tablaDescargaCisterna=$("#tablaDescargaCisterna");
  //Seccion Agregar Carga Tanque
  this.obj.cntFormularioCargaTanque=$("#cntFormularioCargaTanque");
  this.obj.frmAgregarCargaTanque=$("#frmAgregarCargaTanque");
  this.obj.cmpMedidaInicial=$("#cmpMedidaInicial");
  this.obj.cmpFechaHoraInicial=$("#cmpFechaHoraInicial");
  this.obj.cmpAlturaInicial=$("#cmpAlturaInicial");
  this.obj.cmpTemperaturaInicialCentro=$("#cmpTemperaturaInicialCentro");
  this.obj.cmpTemperaturaInicialProbeta=$("#cmpTemperaturaInicialProbeta");
  this.obj.cmpAPIObservadoInicial=$("#cmpAPIObservadoInicial");
  this.obj.cmpFactorInicial=$("#cmpFactorInicial");
  this.obj.cmpVolumenInicialObservado=$("#cmpVolumenInicialObservado");
  this.obj.cmpVolumenInicialCorregido=$("#cmpVolumenInicialCorregido");
 //
  this.obj.cmpMedidaFinal=$("#cmpMedidaFinal");
  this.obj.cmpFechaHoraFinal=$("#cmpFechaHoraFinal");
  this.obj.cmpAlturaFinal=$("#cmpAlturaFinal");
  this.obj.cmpTemperaturaFinalCentro=$("#cmpTemperaturaFinalCentro");
  this.obj.cmpTemperaturaFinalProbeta=$("#cmpTemperaturaFinalProbeta");
  this.obj.cmpAPIObservadoFinal=$("#cmpAPIObservadoFinal");
  this.obj.cmpFactorFinal=$("#cmpFactorFinal");
  this.obj.cmpVolumenFinalObservado=$("#cmpVolumenFinalObservado");
  this.obj.cmpVolumenFinalCorregido=$("#cmpVolumenFinalCorregido");
  //
  this.obj.btnGuardarCarga=$("#btnGuardarCarga");
  this.obj.frmAgregarCargaTanque=$("#frmAgregarCargaTanque");
  this.obj.btnCancelarGuardarCarga=$("#btnCancelarGuardarCarga");
  this.obj.ocultaContenedorFormularioCargaTanque=$("#ocultaContenedorFormularioCargaTanque");
  
  this.obj.cmpOperacionFormularioCargaTanque=$("#cmpOperacionFormularioCargaTanque");
  this.obj.cmpTanqueFormularioCargaTanque=$("#cmpTanqueFormularioCargaTanque");
  this.obj.cmpEstacionFormularioCargaTanque=$("#cmpEstacionFormularioCargaTanque");
//
  //Descarga
  
  this.obj.bandaInformacion=$("#bandaInformacion");  
  
  this.obj.btnFiltrarDiaOperativo.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
    referenciaModulo.listarDiasOperativos();
  });

  this.obj.btnDetalleDiaOperativo.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
    //Mostrar el detalle del dia operativo
    referenciaModulo.mostrarDetalleDiaOperativo();
  });
  
  this.obj.btnFiltrarCargaTanque.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
    referenciaModulo.listarCargasTanque();
  });
  
  this.obj.btnAgregarCarga.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
	 referenciaModulo.iniciarAgregarCargaTanque();
  });
  
  this.obj.btnGuardarCarga.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
	  referenciaModulo.guardarCargaTanque();
  });
  
  this.obj.btnCancelarGuardarCarga.on(referenciaModulo.NOMBRE_EVENTO_CLICK,function(){
	  referenciaModulo.cancelarGuardarCargaTanque();
  });
};

moduloDescarga.prototype.cancelarGuardarCargaTanque= function(){
  var referenciaModulo=this;
  try {
    referenciaModulo.obj.cntFormularioCargaTanque.hide();
    referenciaModulo.obj.cntTablaDiaOperativo.hide();
    referenciaModulo.obj.cntDetalleDiaOperativo.show();
  } catch(error){
    
  }
  
};

moduloDescarga.prototype.recuperarValoresCarga= function(){
  var referenciaModulo= this;
  try {
    var cargaTanque={};
    cargaTanque.idTanque= parseInt(referenciaModulo.obj.cmpTanqueFormularioCargaTanque.val());
    cargaTanque.idEstacion= parseInt(referenciaModulo.idEstacionSeleccionada);
    cargaTanque.idDiaOperativo= parseInt(referenciaModulo.idDiaOperativoSeleccionado);  
    cargaTanque.fechaHoraInicial = utilitario.formatearStringToDateHour(referenciaModulo.obj.cmpFechaHoraInicial.val());
    /*
    cargaTanque.alturaInicial= referenciaModulo.obj.cmpAlturaInicial.val();
    cargaTanque.temperaturaInicialCentro= referenciaModulo.obj.cmpTemperaturaInicialCentro.val();
    cargaTanque.temperaturaIniciaProbeta= referenciaModulo.obj.cmpTemperaturaInicialProbeta.val();
    cargaTanque.apiObservadoInicial= referenciaModulo.obj.cmpAPIObservadoInicial.val();
    cargaTanque.factorCorreccionInicial= referenciaModulo.obj.cmpFactorInicial.val();
    cargaTanque.volumenObservadoInicial= referenciaModulo.obj.cmpVolumenInicialObservado.val();
    cargaTanque.volumenCorregidoInicial= referenciaModulo.obj.cmpVolumenInicialCorregido.val();
    //
    cargaTanque.fechaHoraFinal = referenciaModulo.obj.cmpFechaHoraFinal.val();
    cargaTanque.alturaFinal= referenciaModulo.obj.cmpAlturaFinal.val();
    cargaTanque.temperaturaFinalCentro= referenciaModulo.obj.cmpTemperaturaFinalCentro.val();
    cargaTanque.temperaturaFinalProbeta= referenciaModulo.obj.cmpTemperaturaFinalProbeta.val();
    cargaTanque.apiObservadoFinal= referenciaModulo.obj.cmpAPIObservadoFinal.val();
    cargaTanque.factorCorreccionFinal= referenciaModulo.obj.cmpFactorFinal.val();
    cargaTanque.volumenObservadoFinal= referenciaModulo.obj.cmpVolumenFinalObservado.val();
    cargaTanque.volumenCorregidoFinal= referenciaModulo.obj.cmpVolumenFinalCorregido.val();
    //
   
    ;*/
    console.log(cargaTanque);
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  }
  return cargaTanque;
};

moduloDescarga.prototype.guardarCargaTanque= function(){
	var referenciaModulo = this;
  referenciaModulo.mostrarDepuracion("guardarRegistro");
	var referenciaModulo = this;
	if (referenciaModulo.obj.frmAgregarCargaTanque.valid()){
    referenciaModulo.obj.ocultaContenedorFormularioCargaTanque.show();
		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
		var eRegistro = referenciaModulo.recuperarValoresCarga();
		$.ajax({
      type: constantes.PETICION_TIPO_POST,
      url: referenciaModulo.URL_GUARDAR_CARGA, 
      contentType: referenciaModulo.TIPO_CONTENIDO, 
      data: JSON.stringify(eRegistro),	
      success: function(respuesta) {
        if (!respuesta.estado) {
          referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
          referenciaModulo.obj.ocultaContenedorFormularioCargaTanque.hide();
        } else {
          referenciaModulo.listarCargasTanque();
          referenciaModulo.obj.cntFormularioCargaTanque.hide();
          referenciaModulo.obj.cntDetalleDiaOperativo.show();
        }
        
      },			    		    
      error: function(xhr,estado,error) {
        referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
      }
		});
	} else {
    referenciaModulo.obj.ocultaContenedorFormularioCargaTanque.hide();
	}
};

moduloDescarga.prototype.inicializarGrillas = function(){
  var referenciaModulo=this;
  referenciaModulo.mostrarDepuracion("inicializarGrillas");
  referenciaModulo.inicializarGrillaDiaOperativo();
  referenciaModulo.inicializarGrillaCargaTanque();  
};

moduloDescarga.prototype.llamadaAjaxGrillaDiaOperativo = function(e,configuracion,json){
  var referenciaModulo = this;
  try {
    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_AJAX);
    referenciaModulo.desactivarBotones(referenciaModulo.GRILLA_DIA_OPERATIVO);
    if (json.estado === true){
      json.recordsTotal=json.contenido.totalRegistros;
      json.recordsFiltered=json.contenido.totalEncontrados;
      json.data= json.contenido.carga;
      referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
    } else {
      json.recordsTotal=0;
      json.recordsFiltered=0;
      json.data= {};
      referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,json.mensaje);
    }
    if (referenciaModulo.estaCargadaInterface === false){        
      referenciaModulo.estaCargadaInterface = true;
    }
    referenciaModulo.obj.ocultaContenedorTablaDiaOperativo.hide(); 
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  } 
};

moduloDescarga.prototype.llamadaAjaxDiaOperativo = function(cfgDiaOperativo){
  console.log("llamadaAjaxDiaOperativo INICIO");
  var referenciaModulo = this;
  var rangoFecha = referenciaModulo.obj.filtroFechaPlanificada.val().split("-");
  var fechaInicio = utilitario.formatearFecha2Iso(rangoFecha[0]) ;
  var fechaFinal = utilitario.formatearFecha2Iso(rangoFecha[1]);
  var indiceOrdenamiento = cfgDiaOperativo.order[0].column;
  cfgDiaOperativo.registrosxPagina =  cfgDiaOperativo.length; 
  cfgDiaOperativo.inicioPagina = cfgDiaOperativo.start; 
  cfgDiaOperativo.campoOrdenamiento = cfgDiaOperativo.columns[indiceOrdenamiento].data;
  cfgDiaOperativo.sentidoOrdenamiento = cfgDiaOperativo.order[0].dir;
  cfgDiaOperativo.filtroOperacion = referenciaModulo.obj.filtroOperacion.val();
  cfgDiaOperativo.filtroFechaInicio = fechaInicio;
  cfgDiaOperativo.filtroFechaFinal = fechaFinal;
  console.log("llamadaAjaxDiaOperativo FIN");
};

moduloDescarga.prototype.inicializarGrillaDiaOperativo=function(){
  var referenciaModulo = this;
  try {
    this.obj.tablaDiaOperativo.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
      referenciaModulo.llamadaAjaxGrillaDiaOperativo(e,configuracion,json);	   
    });
      
    this.obj.tablaDiaOperativo.on(constantes.DT_EVENTO_PREAJAX, function(e,configuracion,json) {
      referenciaModulo.obj.ocultaContenedorTablaDiaOperativo.show();
    });
    
    this.obj.tablaDiaOperativo.on(constantes.DT_EVENTO_PAGINACION, function () {
    });
    
    this.obj.tablaDiaOperativo.on(constantes.DT_EVENTO_ORDENACION, function () {
      //Se ejecuta cuando se hace clic en alguna cabecera de columna para ordenarla
      referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_ORDENACION);
    });

    this.obj.tablaDiaOperativoAPI = this.obj.tablaDiaOperativo.DataTable({
    deferLoading: 0,
    processing: true,
    responsive: true,
    dom: constantes.DT_ESTRUCTURA,
    iDisplayLength: referenciaModulo.NUMERO_REGISTROS_PAGINA,
    lengthMenu:referenciaModulo.TOPES_PAGINACION,
    language: {
      url: referenciaModulo.URL_LENGUAJE_GRILLA
    },
    serverSide: true,
    ajax: {
      url: referenciaModulo.URL_LISTAR_DIA_OPERATIVO,
      type:constantes.PETICION_TIPO_GET,
      data: function (cfgDiaOperativo) {
        console.log("inicializarGrillaDiaOperativo");
        referenciaModulo.llamadaAjaxDiaOperativo(cfgDiaOperativo);
      }
    },
    columns : referenciaModulo.columnasGrillaDiaOperativo,
    columnDefs : referenciaModulo.definicionColumnasGrillaDiaOperativo,
    order: referenciaModulo.ordenGrillaDiaOperativo
    });	
    
    $('#tablaDiaOperativo tbody').on(referenciaModulo.NOMBRE_EVENTO_CLICK,'tr',function () {
      if ($(this).hasClass('selected')) {
        $(this).removeClass('selected');
      } else {
        referenciaModulo.obj.tablaDiaOperativoAPI.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
      }
      var indiceFila = referenciaModulo.obj.tablaDiaOperativoAPI.row( this ).index();
      referenciaModulo.idDiaOperativoSeleccionado = referenciaModulo.obj.tablaDiaOperativoAPI.cell(indiceFila,referenciaModulo.INDICE_COLUMNA_ID_DIA_OPERATIVO).data();
      referenciaModulo.fechaDiaOperativoSeleccionado = referenciaModulo.obj.tablaDiaOperativoAPI.cell(indiceFila,referenciaModulo.INDICE_COLUMNA_FECHA_DIA_OPERATIVO).data();
      var estadoActual = referenciaModulo.obj.tablaDiaOperativoAPI.cell(indiceFila,referenciaModulo.INDICE_COLUMNA_ESTADO_DIA_OPERATIVO).data();
      
      if (parseInt(estadoActual)=== parseInt(constantes.ESTADO_ASIGNADO)) {
        referenciaModulo.activarBotones(referenciaModulo.GRILLA_DIA_OPERATIVO);
      } else {
        referenciaModulo.desactivarBotones(referenciaModulo.GRILLA_DIA_OPERATIVO);
      }
      //referenciaModulo.grillaDiaOperativoDespuesSeleccionar(indiceFila);      
    });
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  }
};

moduloDescarga.prototype.llamadaAjaxGrillaCargaTanque = function(e,configuracion,json){
  var referenciaModulo = this;
  try {
    referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_AJAX);
    referenciaModulo.desactivarBotones(referenciaModulo.GRILLA_CARGA_TANQUE);
    if (json.estado === true){
      json.recordsTotal=json.contenido.totalRegistros;
      json.recordsFiltered=json.contenido.totalEncontrados;
      json.data= json.contenido.carga;
      referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,json.mensaje);
    } else {
      json.recordsTotal=0;
      json.recordsFiltered=0;
      json.data= {};
      referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,json.mensaje);
    }
    referenciaModulo.obj.ocultaContenedorTablaCargaTanque.hide(); 
    referenciaModulo.obj.ocultaContenedorTablaDescarga.hide();
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  } 
};

moduloDescarga.prototype.llamadaAjaxCargaTanque = function(cfgCargaTanque){
  var referenciaModulo = this;
  var indiceOrdenamiento = cfgCargaTanque.order[0].column;
  console.log(indiceOrdenamiento);
  cfgCargaTanque.registrosxPagina =  cfgCargaTanque.length; 
  cfgCargaTanque.inicioPagina = cfgCargaTanque.start; 
  cfgCargaTanque.campoOrdenamiento= cfgCargaTanque.columns[indiceOrdenamiento].data;
  cfgCargaTanque.sentidoOrdenamiento=cfgCargaTanque.order[0].dir;
  cfgCargaTanque.valorBuscado=cfgCargaTanque.search.value;
  cfgCargaTanque.filtroEstacion = referenciaModulo.idEstacionSeleccionada;
  cfgCargaTanque.filtroFechaPlanificada = referenciaModulo.idDiaOperativoSeleccionado;
};

moduloDescarga.prototype.inicializarGrillaCargaTanque=function(){
  var referenciaModulo = this;
  referenciaModulo.mostrarDepuracion("inicializarGrillaCargaTanque");
  try {
    this.obj.tablaCargaTanque.on(constantes.DT_EVENTO_AJAX, function (e,configuracion,json) {
      referenciaModulo.llamadaAjaxGrillaCargaTanque(e,configuracion,json);	   
    });
      
    this.obj.tablaCargaTanque.on(constantes.DT_EVENTO_PREAJAX, function ( e, settings, data ) {
      referenciaModulo.obj.ocultaContenedorTablaCargaTanque.show();
      referenciaModulo.obj.ocultaContenedorTablaDescarga.show();
    });
    
    this.obj.tablaCargaTanque.on(constantes.DT_EVENTO_PAGINACION, function () {
      //Se ejecuta cuando se hace clic en boton de paginacion
      referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_PAGINACION);
    });
    
    this.obj.tablaCargaTanque.on(constantes.DT_EVENTO_ORDENACION, function () {
      //Se ejecuta cuando se hace clic en alguna cabecera de columna para ordenarla
      referenciaModulo.mostrarDepuracion(constantes.DT_EVENTO_ORDENACION);
    });
    
    this.obj.tablaCargaTanqueAPI = this.obj.tablaCargaTanque.DataTable({
    "processing": true,
    "deferLoading": 0,
    "responsive": true,
    "dom": constantes.DT_ESTRUCTURA,
    "iDisplayLength": referenciaModulo.NUMERO_REGISTROS_PAGINA,
    "lengthMenu":referenciaModulo.TOPES_PAGINACION,
    "language": {
      "url": referenciaModulo.URL_LENGUAJE_GRILLA
    },
    "serverSide": true,
    "ajax": {
      "url": referenciaModulo.URL_LISTAR_CARGA,
      "type":constantes.PETICION_TIPO_GET,
      "data": function (cfgCargaTanque) {
        referenciaModulo.llamadaAjaxCargaTanque(cfgCargaTanque);
      }
    },
    "columns" : referenciaModulo.columnasGrillaCargaTanque,
    "columnDefs" : referenciaModulo.definicionColumnasGrillaCargaTanque,
    "order": referenciaModulo.ordenGrillaCargaTanque
    });	
    
    referenciaModulo.mostrarDepuracion("tablaCargaTanqueAPI");
    
    $('#tablaCargaTanque tbody').on(referenciaModulo.NOMBRE_EVENTO_CLICK,'tr',function () {
      if ( $(this).hasClass('selected') ) {
        $(this).removeClass('selected');
      } else {
        referenciaModulo.obj.tablaDiaOperativoAPI.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
      }
      var indiceFila = referenciaModulo.obj.tablaDiaOperativoAPI.row( this ).index();
      referenciaModulo.idCargaTanque = referenciaModulo.obj.tablaDiaOperativoAPI.cell(indiceFila,1).data();
      referenciaModulo.activarBotones(referenciaModulo.GRILLA_CARGA_TANQUE);    
    });
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  }
};

moduloDescarga.prototype.activarBotones=function(grilla){
	var referenciaModulo=this;
  if (grilla===referenciaModulo.GRILLA_DIA_OPERATIVO) {    
	  referenciaModulo.obj.btnDetalleDiaOperativo.removeClass(constantes.CSS_CLASE_DESHABILITADA);
  }
  if (grilla==referenciaModulo.GRILLA_CARGA_TANQUE){
	  referenciaModulo.obj.btnModificarCarga.removeClass(constantes.CSS_CLASE_DESHABILITADA);
  }
};

moduloDescarga.prototype.desactivarBotones=function(grilla){
  var referenciaModulo=this;
  if (grilla == referenciaModulo.GRILLA_DIA_OPERATIVO) {
    this.obj.btnDetalleDiaOperativo.addClass(constantes.CSS_CLASE_DESHABILITADA);
  }
  if (grilla == referenciaModulo.GRILLA_CARGA_TANQUE) {
    this.obj.btnModificarCarga.addClass(constantes.CSS_CLASE_DESHABILITADA);
  }
};

moduloDescarga.prototype.mostrarDetalleDiaOperativo= function(){
  var referenciaModulo=this;
  var item =null;
  var numeroEstaciones=0;
  var listadoEstaciones=null;
  try {
	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.PROCESANDO_PETICION);
    referenciaModulo.idOperacionSeleccionada=referenciaModulo.obj.filtroOperacion.val();
    referenciaModulo.nombreOperacionSeleccionada = $("#filtroOperacion option:selected").text();
    referenciaModulo.obj.cntTablaDiaOperativo.hide();
    referenciaModulo.obj.cntDetalleDiaOperativo.show();
    referenciaModulo.obj.cmpOperacionDetalleDiaOperativo.val(referenciaModulo.nombreOperacionSeleccionada);
    referenciaModulo.obj.cmpFechaPlanificacionDetalleDiaOperativo.val(utilitario.formatearFecha(referenciaModulo.fechaDiaOperativoSeleccionado));
    referenciaModulo.obj.ocultaContenedorTablaCargaTanque.show();
    referenciaModulo.obj.ocultaContenedorTablaDescarga.show();
    $.ajax({
	    type: constantes.PETICION_TIPO_GET,
	    url: referenciaModulo.URL_RECUPERAR_ESTACIONES, 
	    contentType: referenciaModulo.TIPO_CONTENIDO, 
	    data: {filtroOperacion:referenciaModulo.idOperacionSeleccionada},	
	    success: function(respuesta) {
	    	if (!respuesta.estado) {          
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
	    	} else {          
          listadoEstaciones = respuesta.contenido.carga;
          numeroEstaciones = listadoEstaciones.length;
          referenciaModulo.obj.filtroEstacionDetalleDiaOperativo.children().remove();
          referenciaModulo.obj.filtroEstacionDetalleDiaOperativo.append($('<option>', { 
              value: "-1",
              text : cadenas.SELECCIONAR_ELEMENTO 
              }));
          for(var contador=0;contador<numeroEstaciones;contador++){
            item = listadoEstaciones[contador];
            referenciaModulo.obj.filtroEstacionDetalleDiaOperativo.append($('<option>', { 
            value: item.id,
            text : item.nombre 
            }));
          }
          referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,cadenas.LISTADO_ESTACIONES_EXITOSO);
          referenciaModulo.obj.ocultaContenedorTablaCargaTanque.hide();
          referenciaModulo.obj.ocultaContenedorTablaDescarga.hide();
    		}
	    },			    		    
	    error: function(xhr,estado,error) {
        referenciaModulo.mostrarErrorServidor(xhr,estado,error);        
	    }
    });
    //referenciaModulo.listarCargasTanque();    
  } catch(error){
    console.log(error.message);
  }  
};

moduloDescarga.prototype.listarDiasOperativos= function(){
  var referenciaModulo=this;
  try {
    referenciaModulo.mostrarDepuracion("listarRegistros");
    referenciaModulo.obj.tablaDiaOperativoAPI.ajax.reload();
  } catch(error){
    referenciaModulo.mostrarDepuracion(error.message);
  }
};

moduloDescarga.prototype.listarCargasTanque= function(){
  var referenciaModulo=this;
  try {
    referenciaModulo.idEstacionSeleccionada = referenciaModulo.obj.filtroEstacionDetalleDiaOperativo.val();
    referenciaModulo.mostrarDepuracion("listarRegistros");
    referenciaModulo.obj.tablaCargaTanqueAPI.ajax.reload();
  } catch(error){
    
  }  
};

moduloDescarga.prototype.iniciarAgregarCargaTanque=function(){
	var referenciaModulo=this;
	try {
    referenciaModulo.obj.ocultaContenedorFormularioCargaTanque.show();
    referenciaModulo.nombreEstacionSeleccionada = $("#filtroEstacionDetalleDiaOperativo option:selected").text();
    console.log( referenciaModulo.nombreEstacionSeleccionada);
    console.log(referenciaModulo.nombreOperacionSeleccionada);
    referenciaModulo.obj.cntTablaDiaOperativo.hide();
    referenciaModulo.obj.cntDetalleDiaOperativo.hide();
    referenciaModulo.obj.cntFormularioCargaTanque.show();
    referenciaModulo.modoEdicionCargaTanque=constantes.MODO_NUEVO;
    referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.INICIAR_OPERACION);
    referenciaModulo.obj.cmpOperacionFormularioCargaTanque.val(referenciaModulo.nombreOperacionSeleccionada);
    referenciaModulo.obj.cmpEstacionFormularioCargaTanque.val(referenciaModulo.nombreEstacionSeleccionada);
    $.ajax({
	    type: constantes.PETICION_TIPO_GET,
	    url: referenciaModulo.URL_RECUPERAR_TANQUES, 
	    contentType: referenciaModulo.TIPO_CONTENIDO, 
	    data: {filtroOperacion:referenciaModulo.idOperacionSeleccionada},	
	    success: function(respuesta) {
	    	if (!respuesta.estado) {          
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
	    	} else {          
          listadoTanques = respuesta.contenido.carga;
          console.log(listadoTanques);
          numeroTanques = listadoTanques.length;
          referenciaModulo.obj.cmpTanqueFormularioCargaTanque.children().remove();
          referenciaModulo.obj.cmpTanqueFormularioCargaTanque.append($('<option>', { 
              value: "-1",
              text : cadenas.SELECCIONAR_ELEMENTO 
              }));
          for(var contador=0;contador<numeroTanques;contador++){
            item = listadoTanques[contador];
            console.log(item);
            referenciaModulo.obj.cmpTanqueFormularioCargaTanque.append($('<option>', { 
            value: item.id,
            text : item.descripcion 
            }));
          }
          referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,cadenas.LISTADO_ESTACIONES_EXITOSO);
          referenciaModulo.obj.ocultaContenedorFormularioCargaTanque.hide();
    		}
	    },			    		    
	    error: function(xhr,estado,error) {
        referenciaModulo.mostrarErrorServidor(xhr,estado,error);        
	    }
    });
	} catch(error){
		console.log(error);
	}
};

	
moduloDescarga.prototype.inicializarCampos= function(){
//Implementar en cada caso
};

moduloDescarga.prototype.llenarFormulario = function(registro){
//Implementar en cada caso	
};

moduloDescarga.prototype.llenarDetalles = function(registro){
//Implementar en cada caso
};

moduloDescarga.prototype.recuperarValores = function(registro){
var eRegistro = {};
//Implementar en cada caso
return eRegistro;
};

moduloDescarga.prototype.grillaDespuesSeleccionar= function(indice){
//TODO implementar
};

moduloDescarga.prototype.despuesListarRegistros=function(){
//Implementar en cada caso
};

moduloDescarga.prototype.inicializar=function(){
  this.mostrarDepuracion("inicializar");
  this.inicializarControles();
  this.inicializarGrillas();
  //this.inicializarFormularioPrincipal();
  this.inicializarCampos();
  this.listarDiasOperativos();
  this.mostrarDepuracion("terminar");
};

moduloDescarga.prototype.resetearFormulario= function(){
  var referenciaModulo= this;
  referenciaModulo.obj.frmPrincipal[0].reset();
  jQuery.each( this.obj, function( i, val ) {
    if ( (typeof referenciaModulo.obj[i].tipoControl ) === "undefined" ){
      //TODO
    } else {
      if (referenciaModulo.obj[i].tipoControl === "select2"){
        referenciaModulo.obj[i].select2("val", null);
      }
    }
  });
};