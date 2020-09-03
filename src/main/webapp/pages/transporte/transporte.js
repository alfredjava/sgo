$(document).ready(function(){
  var moduloActual = new moduloTransporte();

  moduloActual.urlBase='transporte';
  moduloActual.SEPARADOR_MILES = ",";
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
  moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
  moduloActual.URL_LISTAR_TRANSPORTES = moduloActual.urlBase + '/listarTransportes';
  moduloActual.URL_RECUPERAR_DETALLES_TRANSPORTE = moduloActual.urlBase + '/recuperarDetallesTransporte';
  moduloActual.URL_ACTUALIZAR_PESAJE = moduloActual.urlBase + '/actualizarPesaje';
  moduloActual.URL_GUARDAR_EVENTO = moduloActual.urlBase + '/guardarEventoTransporte';
  moduloActual.URL_LISTAR_ASIGNACION_TRANSPORTES = moduloActual.urlBase + '/listarAsignacionTransportes';
  
  moduloActual.ordenGrilla=[[ 2, 'asc' ]];
  moduloActual.columnasGrilla.push({ "data": 'id'}); //Target1
  moduloActual.columnasGrilla.push({ "data": 'fechaOperativa'});//Target2
  moduloActual.columnasGrilla.push({ "data": 'totalCisternas'});//Target3
  moduloActual.columnasGrilla.push({ "data": 'totalVolumenSolicitado'});//Target4
  moduloActual.columnasGrilla.push({ "data": 'fechaActualizacion'});//Target5
  moduloActual.columnasGrilla.push({ "data": 'usuarioActualizacion'});//Target6
  moduloActual.columnasGrilla.push({ "data": 'estado'});//Target7
  moduloActual.columnasGrilla.push({ "data": 'operacion.nombre'});//Target8
  moduloActual.columnasGrilla.push({ "data": 'operacion.cliente.razonSocial'});//Target9
  moduloActual.columnasGrilla.push({ "data": 'operacion.cliente.id'});//Target10
  //Columnas
  moduloActual.definicionColumnas.push({"targets" : 1, "searchable" : true, "orderable" : true, "visible" : false });
  moduloActual.definicionColumnas.push({"targets" : 2, "searchable" : true, "orderable" : true, "visible" : true, "render" : utilitario.formatearFecha });
  moduloActual.definicionColumnas.push({"targets" : 3, "searchable" : true, "orderable" : true, "visible" : true });
  moduloActual.definicionColumnas.push({"targets" : 4, "searchable" : true, "orderable" : true, "visible" : true });
  moduloActual.definicionColumnas.push({"targets" : 5, "searchable" : true, "orderable" : true, "visible" : true, "data-align":"center" });
  moduloActual.definicionColumnas.push({"targets" : 6, "searchable" : true, "orderable" : true, "visible" : true });
  moduloActual.definicionColumnas.push({"targets" : 7, "searchable" : true, "orderable" : true, "visible" : true, "render" : utilitario.formatearEstadoDiaOperativo});
  moduloActual.definicionColumnas.push({"targets" : 8, "searchable" : true, "orderable" : true, "visible" : false });
  moduloActual.definicionColumnas.push({"targets" : 9, "searchable" : true, "orderable" : true, "visible" : false });
  moduloActual.definicionColumnas.push({"targets" : 10, "searchable" : true, "orderable" : true, "visible" : false });
  
//esto para el dataTable secundario
  moduloActual.ordenGrillaSecundaria=[[ 2, 'asc' ]];
  //Agregar columnas a la grilla
  moduloActual.columnasGrillaSecundaria.push({ "data": 'id'});					//Target1
  moduloActual.columnasGrillaSecundaria.push({ "data": 'numeroGuiaRemision'});	//Target2
  moduloActual.columnasGrillaSecundaria.push({ "data": 'fechaEmisionGuia'});	//Target3
  moduloActual.columnasGrillaSecundaria.push({ "data": 'cisternaTracto'});		//Target4
  moduloActual.columnasGrillaSecundaria.push({ "data": 'codigoScop'});			//Target5
  moduloActual.columnasGrillaSecundaria.push({ "data": 'volumenTotalObservado'});//Target6
  moduloActual.columnasGrillaSecundaria.push({ "data": 'volumenTotalCorregido'});//Target7
  moduloActual.columnasGrillaSecundaria.push({ "data": 'origen'});				//Target8
  moduloActual.columnasGrillaSecundaria.push({ "data": 'estado'});				//Target9
  
  
  moduloActual.definicionColumnasSecundaria.push({ "targets": 1, "searchable": true, "orderable": true, "visible":false });
  moduloActual.definicionColumnasSecundaria.push({ "targets": 2, "searchable": true, "orderable": true, "visible":true });
  moduloActual.definicionColumnasSecundaria.push({ "targets": 3, "searchable": true, "orderable": true, "visible":true, "render" : utilitario.formatearFecha });
  moduloActual.definicionColumnasSecundaria.push({ "targets": 4, "searchable": true, "orderable": true, "visible":true, "class": "text-center" });
  moduloActual.definicionColumnasSecundaria.push({ "targets": 5, "searchable": true, "orderable": true, "visible":true });
  moduloActual.definicionColumnasSecundaria.push({ "targets": 6, "searchable": true, "orderable": true, "visible":true });
  moduloActual.definicionColumnasSecundaria.push({ "targets": 7, "searchable": true, "orderable": true, "visible":true });
  moduloActual.definicionColumnasSecundaria.push({ "targets": 8, "searchable": true, "orderable": true, "visible":true, "render" : utilitario.formatearOrigenTransporte });
  moduloActual.definicionColumnasSecundaria.push({ "targets": 9, "searchable": true, "orderable": true, "visible":true, "render" : utilitario.formatearEstado });
  
//esto para la tercera dataTable
  moduloActual.ordenTerceraGrilla=[[ 2, 'asc' ]];
  //Agregar columnas a la grilla
  moduloActual.columnasTerceraGrilla.push({ "data": 'descripcionProducto'});	//Target1
  moduloActual.columnasTerceraGrilla.push({ "data": 'volumenSolicitado'});		//Target2
  moduloActual.columnasTerceraGrilla.push({ "data": 'cisternasSolicitadas'});	//Target3
  moduloActual.columnasTerceraGrilla.push({ "data": 'volumenAsignado'});		//Target4
  moduloActual.columnasTerceraGrilla.push({ "data": 'cisternasAsignadas'});		//Target5
  
  moduloActual.definicionColumnasTerceraGrilla.push({ "targets": 1, "searchable": true, "orderable": true, "visible":true });
  moduloActual.definicionColumnasTerceraGrilla.push({ "targets": 2, "searchable": true, "orderable": true, "visible":true, "sClass": "a-center" });
  moduloActual.definicionColumnasTerceraGrilla.push({ "targets": 3, "searchable": true, "orderable": true, "visible":true, "sClass": "a-right" });
  moduloActual.definicionColumnasTerceraGrilla.push({ "targets": 4, "searchable": true, "orderable": true, "visible":true, "sClass": "a-right" });
  moduloActual.definicionColumnasTerceraGrilla.push({ "targets": 5, "searchable": true, "orderable": true, "visible":true, "sClass": "a-right" });

  moduloActual.reglasValidacionFormulario={
    cmpNumeroGuiaRemision:	{required: true, maxlength: 15 },
    cmpNumeroOrdenCompra: 	{required: true, maxlength: 15 },
    cmpCodigoScop: 			{required: true, maxlength: 15 },

    cmpPlantaDespacho: 		{required: true },
    cmpTransportista: 		{required: true },
    cmpCisternaTracto: 		{required: true },

    cmpConductor: 			{required: true },
    cmpFemisionOE: 			{required: true },
  };

  moduloActual.mensajesValidacionFormulario={
	cmpNumeroGuiaRemision:  {required: "El campo es obligatorio", maxlength: "El campo debe contener 15 caracteres como m&aacute;ximo." },
	cmpNumeroOrdenCompra: 	{required: "El campo es obligatorio", maxlength: "El campo debe contener 15 caracteres como m&aacute;ximo." },
    cmpCodigoScop: 			{required: "El campo es obligatorio", maxlength: "El campo debe contener 15 caracteres como m&aacute;ximo." },

    cmpPlantaDespacho: 		{required: "El campo es obligatorio" },
    cmpTransportista: 		{required: "El campo es obligatorio" },
    cmpCisternaTracto: 		{required: "El campo es obligatorio" },

    cmpConductor: 			{required: "El campo es obligatorio" },
    cmpFemisionOE: 			{required: "El campo es obligatorio" },
  };

  moduloActual.reglasValidacionFormularioEvento={
    cmpEventoTipoEvento:	{required: true },
    cmpEventoFechaHora: 	{required: true },
    cmpEventoDescripcion: 	{required: true, maxlength: 3000 },
  };

  moduloActual.mensajesValidacionFormularioEvento={
	cmpEventoTipoEvento:  {required: "El campo es obligatorio" },
	cmpEventoFechaHora:   {required: "El campo es obligatorio" },
    cmpEventoDescripcion: {required: "El campo es obligatorio", maxlength: "El campo debe contener 3000 caracteres como m&aacute;ximo." },
  };

  moduloActual.reglasValidacionFormularioPesaje={
	cmpPesajePesoBruto:	  {required: true },
	cmpPesajePesoTara: 	  {required: true },
  };

  moduloActual.mensajesValidacionFormularioPesaje={
	cmpPesajePesoBruto:	  {required: "El campo es obligatorio" },
	cmpPesajePesoTara:    {required: "El campo es obligatorio" },
  };

  moduloActual.inicializarCampos= function(){
	this.obj.filtroOperacion = $("#filtroOperacion");
    this.obj.filtroOperacion.on('change', function(e){
      moduloActual.idOperacion=$(this).val();
      moduloActual.volumenPromedioCisterna=$(this).find("option:selected").attr('data-volumen-promedio-cisterna');
      moduloActual.nombreOperacion=$(this).find("option:selected").attr('data-nombre-operacion');
      moduloActual.nombreCliente=$(this).find("option:selected").attr('data-nombre-cliente');
      e.preventDefault(); 
    });   

    this.obj.filtroFechaPlanificada = $("#filtroFechaPlanificada");
    //Recupera la fecha actual enviada por el servidor
    var fechaActual = this.obj.filtroFechaPlanificada.attr('data-fecha-actual');
    var rangoSemana = utilitario.retornarRangoSemana(fechaActual);
    this.obj.filtroFechaPlanificada.val(utilitario.formatearFecha2Cadena(rangoSemana.fechaInicial) + " - " +utilitario.formatearFecha2Cadena(rangoSemana.fechaFinal));
    //Controles de filtro
    this.obj.filtroOperacion.select2();
    this.obj.filtroFechaPlanificada.daterangepicker({
        singleDatePicker: false,        
        showDropdowns: false,
        locale: { 
          "format": 'DD/MM/YYYY',
          "applyLabel": "Aceptar",
          "cancelLabel": "Cancelar",
          "fromLabel": "Desde",
          "toLabel": "Hasta",
          "customRangeLabel": "Seleccionar",
          "daysOfWeek": [
          "Dom",
          "Lun",
          "Mar",
          "Mie",
          "Jue",
          "Vie",
          "Sab"
          ],
          "monthNames": [
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
          ]
        }
    });

    //Campos de detalle de transporte
	this.obj.detalleIdDOperativo=$("#detalleIdDOperativo");
    this.obj.detalleCliente=$("#detalleCliente");
    this.obj.detalleOperacion=$("#detalleOperacion");
    this.obj.detalleFechaPlanificacion=$("#detalleFechaPlanificacion");
    this.obj.detalleIdTransporte=$("#detalleIdTransporte");

    //Campos de Formulario Principal
    this.obj.cmpFormularioIdDOperativo=$("#cmpFormularioIdDOperativo");
    this.obj.cmpFormularioCliente=$("#cmpFormularioCliente");
    this.obj.cmpFormularioOperacion=$("#cmpFormularioOperacion");
    this.obj.cmpFormularioFechaPlanificacion=$("#cmpFormularioFechaPlanificacion");

    this.obj.cmpId=$("#cmpId");
    this.obj.formularioIdTransporte=$("#formularioIdTransporte");
    this.obj.cmpNumeroGuiaRemision=$("#cmpNumeroGuiaRemision");
    this.obj.cmpNumeroOrdenCompra=$("#cmpNumeroOrdenCompra");
    this.obj.cmpNumeroFactura=$("#cmpNumeroFactura");
    this.obj.cmpCodigoScop=$("#cmpCodigoScop");

    this.obj.cmpPlantaDespacho=$("#cmpPlantaDespacho");
    this.obj.cmpSelect2Planta=$("#cmpPlantaDespacho").select2({
  	  ajax: {
  		    url: "./planta/listar",
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
		        return "<div class='select2-user-result'>" + registro.descripcion + "</div>";
		    },
		    templateSelection: function (registro) {
		        return registro.descripcion || registro.text;
		    },
    });
    this.obj.cmpTransportista=$("#cmpTransportista");
    this.obj.cmpSelect2Transportista=$("#cmpTransportista").select2({
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
  		        return "<div class='select2-user-result'>" + (registro.razonSocial) + "</div>";
  		    },
  		    templateSelection: function (registro) {
  		    	moduloActual.idTransportista = registro.id;
  		    	try{
	  		      var elemento1 =constantes.PLANTILLA_OPCION_SELECTBOX;
	  		      elemento1 = elemento1.replace(constantes.ID_OPCION_CONTENEDOR, 0);
	  		      elemento1 = elemento1.replace(constantes.VALOR_OPCION_CONTENEDOR, "Seleccionar");
	  		      moduloActual.obj.cmpCisternaTracto.empty().append(elemento1).val(0).trigger('change');
  		    	} catch(error){
  		          console.log(error.message);
  		    	}
  		    	moduloActual.obj.contenedorDetalles.hide();
	  		  return registro.razonSocial || registro.text;
  		    },
      });

    this.obj.cmpIdTracto=$("#cmpIdTracto");
    this.obj.cmpPlacaCisterna=$("#cmpPlacaCisterna");
    this.obj.cmpPlacaTracto=$("#cmpPlacaTracto");
    this.obj.cmpCisternaTracto=$("#cmpCisternaTracto");
    this.obj.cmpSelect2CisternaTracto=$("#cmpCisternaTracto").select2({
	  ajax: {
  		    url: "./cisterna/recuperarPorTransportista",
  		    dataType: 'json',
  		    delay: 250,
  		    "data": function (parametros) {
  		      return { 
  		    	idTransportista:parseInt(moduloActual.idTransportista),
  		    	txt: parametros.term // search term
  		    	
  		      };
  		    },
	    
  		  processResults: function (respuesta, pagina) {
  		    	var resultados = respuesta.contenido.carga;
  		    	return { results: resultados};
  		    },
  		    cache: true
  		  },

	  		"language": "es",
	  		"escapeMarkup": function (markup) { 
	  			return markup; 
	  		},
	  		"templateResult": function (registro) {
	  			if (registro.loading) {
	  				return registro.text;
	  			}	
		        return "<div class='select2-user-result'>" + registro.placaCisternaTracto + "</div>";
		    },
		    "templateSelection": function (registro) {
		    	moduloActual.cmpIdTracto = registro.idTracto;
		    	moduloActual.cmpPlacaCisterna = registro.placa;
		    	moduloActual.cmpPlacaTracto = registro.placaTracto;
		    	moduloActual.agregarFilas(registro.cantidadCompartimentos);
		    	moduloActual.obj.contenedorDetalles.show();

		        return registro.placaCisternaTracto || registro.text;
		    },
	 });
    this.obj.cmpBreveteConductor=$("#cmpBreveteConductor");
    this.obj.cmpConductor=$("#cmpConductor");
    this.obj.cmpSelect2Conductor=$("#cmpConductor").select2({
    	  ajax: {
    		    url: "./conductor/listar",
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
  		        return "<div class='select2-user-result'>" + registro.nombreCompleto + "</div>";
  		    },
  		    templateSelection: function (registro) {
  		    	moduloActual.cmpBreveteConductor = registro.brevete;
  		        return  registro.nombreCompleto || registro.text;
  		    },
      });
    
    this.obj.cmpIdConductor=$("#cmpIdConductor");
    this.obj.cmpFemisionOE=$("#cmpFemisionOE");
    this.obj.cmpFemisionOE.inputmask(constantes.FORMATO_FECHA, 
    { 
        "placeholder": constantes.FORMATO_FECHA,
        onincomplete: function(){
            $(this).val('');
        }
    });
    this.obj.cmpPrecintos=$("#cmpPrecintos");
    
    this.obj.grupoTransporte = $('#GrupoTransporte').sheepIt({
        separator: '',
        allowRemoveLast: true,
        allowRemoveCurrent: true,
        allowRemoveAll: true,
        allowAdd: true,
        allowAddN: true,
        maxFormsCount: 6,
        minFormsCount: 0,
        iniFormsCount: 0,
        afterAdd: function(origen, formularioNuevo) {
          var cmpCompartimentos=$(formularioNuevo).find("input[elemento-grupo='compartimentos']");
          var cmpElementoProducto=$(formularioNuevo).find("select[elemento-grupo='producto']");
          var cmpVolumenTempObservada=$(formularioNuevo).find("input[elemento-grupo='volumenTempObservada']");
          var cmpTemperatura=$(formularioNuevo).find("input[elemento-grupo='temperatura']");
          var cmpAPI=$(formularioNuevo).find("input[elemento-grupo='API']");
          var cmpFactor=$(formularioNuevo).find("input[elemento-grupo='factor']");
          var cmpVolumen60F=$(formularioNuevo).find("input[elemento-grupo='volumen60F']");

          cmpCompartimentos.inputmask('decimal', {digits: 0});
          cmpElementoProducto.select2();
          cmpVolumenTempObservada.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
          cmpTemperatura.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
          cmpAPI.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
          cmpFactor.inputmask('decimal', {digits: 4, groupSeparator:',',autoGroup:true,groupSize:3});
          cmpVolumen60F.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
          
          cmpVolumenTempObservada.on("input",function(){
        	  moduloActual.sumaVolumenTempObservada();
          });  
          
          cmpVolumen60F.on("input",function(){
        	  moduloActual.sumaVolumen60F();
          });  
        },
        afterRemoveCurrent: function(control) {
          if (control.hasForms()==false){
            control.addForm();          
          }
        }
      }); 

    this.obj.cmpSumVolumenTempObservada=$("#cmpSumVolumenTempObservada");
    this.obj.cmpSumVolumenTempObservada.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
    this.obj.cmpSumVolumen60F=$("#cmpSumVolumen60F");
    this.obj.cmpSumVolumen60F.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});

    //Campos de vista
    this.obj.vistaIdDOperativo=$("#vistaIdDOperativo");
    this.obj.vistaCliente=$("#vistaCliente");
    this.obj.vistaOperacion=$("#vistaOperacion");
    this.obj.vistaFechaPlanificacion=$("#vistaFechaPlanificacion");
    this.obj.vistaIdTransporte=$("#vistaIdTransporte");

    //campos Formulario
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaNumeroGuiaRemision=$("#vistaNumeroGuiaRemision");
    this.obj.vistaNumeroOrdenCompra=$("#vistaNumeroOrdenCompra");
    this.obj.vistaNumeroFactura=$("#vistaNumeroFactura");
    this.obj.vistaCodigoScop=$("#vistaCodigoScop");
    this.obj.vistaFechaEmisionGuia=$("#vistaFechaEmisionGuia");
    this.obj.vistaPlantaDespacho=$("#vistaPlantaDespacho");
    this.obj.vistaPlantaRecepcion=$("#vistaPlantaRecepcion");
    this.obj.vistaIdCliente=$("#vistaIdCliente");
    this.obj.vistaIdConductor=$("#vistaIdConductor");
    this.obj.vistaBreveteConductor=$("#vistaBreveteConductor");
    this.obj.vistaIdCisterna=$("#vistaIdCisterna");
    this.obj.vistaPlacaCisterna=$("#vistaPlacaCisterna");
    this.obj.vistaTarjetaCubicacionCompartimento=$("#vistaTarjetaCubicacionCompartimento");
    this.obj.vistaIdTracto=$("#vistaIdTracto");
    this.obj.vistaPlacaTracto=$("#vistaPlacaTracto");
    this.obj.vistaIdTransportista=$("#vistaIdTransportista");
    this.obj.vistaVolumenTotalObservado=$("#vistaVolumenTotalObservado");
    this.obj.vistaVolumenTotalObservado.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
    this.obj.vistaVolumenTotalCorregido=$("#vistaVolumenTotalCorregido");
    this.obj.vistaVolumenTotalCorregido.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
    this.obj.vistaPrecintos=$("#vistaPrecintos");
    this.obj.vistaEstado=$("#vistaEstado");
    this.obj.vistaSincronizadoEl=$("#vistaSincronizadoEl");
    this.obj.vistaCisternaTracto=$("#vistaCisternaTracto");
    this.obj.vistaCreadoEl=$("#vistaCreadoEl");
    this.obj.vistaCreadoPor=$("#vistaCreadoPor");
    this.obj.vistaActualizadoPor=$("#vistaActualizadoPor");
    this.obj.vistaActualizadoEl=$("#vistaActualizadoEl");
    this.obj.vistaIpCreacion=$("#vistaIpCreacion");
    this.obj.vistaIpActualizacion=$("#vistaIpActualizacion");

    //Campos de formulario Evento
    this.obj.cmpEventoIdTransporteEvento=$("#cmpEventoIdTransporteEvento");
    this.obj.cmpEventoIdDOperativo=$("#cmpEventoIdDOperativo");
    this.obj.cmpEventoCliente=$("#cmpEventoCliente");
    this.obj.cmpEventoOperacion=$("#cmpEventoOperacion");
    this.obj.cmpEventoFechaPlanificacion=$("#cmpEventoFechaPlanificacion");

    this.obj.cmpEventoNumeroGuiaRemision=$("#cmpEventoNumeroGuiaRemision");
    this.obj.cmpEventoNumeroOrdenCompra=$("#cmpEventoNumeroOrdenCompra");
    this.obj.cmpEventoCisternaTracto=$("#cmpEventoCisternaTracto");

    this.obj.cmpEventoTipoEvento=$("#cmpEventoTipoEvento");
    this.obj.cmpEventoFechaHora=$("#cmpEventoFechaHora");
    this.obj.cmpEventoFechaHora.inputmask(constantes.FORMATO_FECHA, 
    { 
        "placeholder": constantes.FORMATO_FECHA,
        onincomplete: function(){
            $(this).val('');
        }
    });
    this.obj.cmpEventoDescripcion=$("#cmpEventoDescripcion");

    //Campos de formulario Pesaje
    this.obj.cmpPesajeIdTransporte=$("#cmpPesajeIdTransporte");
    this.obj.cmpPesajeCliente=$("#cmpPesajeCliente");
    this.obj.cmpPesajeOperacion=$("#cmpPesajeOperacion");
    this.obj.cmpPesajeFechaPlanificacion=$("#cmpPesajeFechaPlanificacion");

    this.obj.cmpPesajeNumeroGuiaRemision=$("#cmpPesajeNumeroGuiaRemision");
    this.obj.cmpPesajeNumeroOrdenCompra=$("#cmpPesajeNumeroOrdenCompra");
    this.obj.cmpPesajeCisternaTracto=$("#cmpPesajeCisternaTracto");

    this.obj.cmpPesajePesoBruto=$("#cmpPesajePesoBruto");
    this.obj.cmpPesajePesoTara=$("#cmpPesajePesoTara");
    this.obj.cmpPesajePesoNeto=$("#cmpPesajePesoNeto");

    this.obj.cmpPesajePesoBruto.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
    this.obj.cmpPesajePesoTara.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
	this.obj.cmpPesajePesoNeto.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});

    this.obj.cmpPesajePesoBruto.on("keypress",function(){
    	var pesoBruto = moduloActual.eliminaSeparadorComa(document.forms["frmPesaje"].cmpPesajePesoBruto.value);
    	var pesoTara = moduloActual.eliminaSeparadorComa(document.forms["frmPesaje"].cmpPesajePesoTara.value);
    	var pesoNeto = parseFloat(pesoBruto) - parseFloat(pesoTara);
        document.forms["frmPesaje"].cmpPesajePesoNeto.value = pesoNeto;
    }); 

    this.obj.cmpPesajePesoTara.on("keypress",function(){
    	var pesoBruto = moduloActual.eliminaSeparadorComa(document.forms["frmPesaje"].cmpPesajePesoBruto.value);
    	var pesoTara = moduloActual.eliminaSeparadorComa(document.forms["frmPesaje"].cmpPesajePesoTara.value);
    	var pesoNeto = parseFloat(pesoBruto) - parseFloat(pesoTara);
        document.forms["frmPesaje"].cmpPesajePesoNeto.value = pesoNeto;
    });
  };

  moduloActual.agregarFilas = function(cantidad){
    var referenciaModulo=this;
  	try{
  		this.obj.grupoTransporte.removeAllForms();
  	  	this.obj.grupoTransporte.addNForms(cantidad);
	  	  for(var contador=0; contador < cantidad; contador++){
			  var formulario= moduloActual.obj.grupoTransporte.getForm(contador);
			  formulario.find("input[elemento-grupo='compartimentos']").val(contador); // contador
		  }
  	    this.obj.cmpSumVolumenTempObservada.val(0);
  	    this.obj.cmpSumVolumen60F.val(0);
  	} catch(error){
      console.log(error.message);
    };
  };

  moduloActual.eliminaSeparadorComa = function(numeroFloat){
	var parametros = numeroFloat.split(',');
  	var retorno =  new String(parametros[0]);
  	if(parametros[1] != null){
  		retorno =  new String(parametros[0] + parametros[1]);
  	}
  	if(parametros[2] != null){
  		retorno =  new String(parametros[0] + parametros[1] + parametros[2]);
  	}
  	if(parametros[3] != null){
  		retorno =  new String(parametros[0] + parametros[1] + parametros[2] + parametros[3]);
  	}
  	if(parametros[4] != null){
  		retorno =  new String(parametros[0] + parametros[1] + parametros[2] + parametros[3] + parametros[4]);
  	}
  	return retorno;
  };

  //TODO
  moduloActual.iniciarAgregar= function(registro){
	  var referenciaModulo=this;
	  referenciaModulo.resetearFormularioPrincipal();
	  moduloActual.idTransporte = -1;

  	  this.obj.cmpFormularioCliente.text(referenciaModulo.obj.clienteSeleccionado);
  	  this.obj.cmpFormularioOperacion.text(referenciaModulo.obj.operacionSeleccionado);
  	  this.obj.cmpFormularioFechaPlanificacion.text(utilitario.formatearFecha(referenciaModulo.obj.fechaPlanificacionSeleccionado));

  };

  moduloActual.llenarFormulario = function(registro){
	  var referenciaModulo=this;
	  this.idRegistro = registro.idDoperativo;

	  //identificadores para las busquedas
	  this.obj.idDiaOperativo = registro.idDoperativo;
	  this.obj.idTransporte = registro.idTransporte;

	  var transporte = registro.transportes[0];
	  var diaOperativo = registro.diaOperativo[0];

	  this.obj.cmpFormularioIdDOperativo.text(registro.idDoperativo);
	  this.obj.cmpFormularioCliente.text(diaOperativo.operacion.cliente.razonSocial);
	  this.obj.cmpFormularioOperacion.text(diaOperativo.operacion.nombre);
	  this.obj.cmpFormularioFechaPlanificacion.text(utilitario.formatearFecha(diaOperativo.fechaOperativa));

	  this.obj.formularioIdTransporte.val(registro.idTransporte);
	  this.obj.cmpNumeroGuiaRemision.val(transporte.numeroGuiaRemision);
	  this.obj.cmpNumeroOrdenCompra.val(transporte.numeroOrdenCompra);
	  this.obj.cmpNumeroFactura.val(transporte.numeroFactura);
	  this.obj.cmpCodigoScop.val(transporte.codigoScop);

	  this.obj.cmpCisternaTracto.val(transporte.cisternaTracto);
	  this.obj.cmpFemisionOE.val(utilitario.formatearFecha(transporte.fechaEmisionGuia));
	  this.obj.cmpPrecintos.val(transporte.precintosSeguridad);

	  var elemento1=constantes.PLANTILLA_OPCION_SELECTBOX;
	  elemento1 = elemento1.replace(constantes.ID_OPCION_CONTENEDOR,transporte.idTransportista);
	  elemento1 = elemento1.replace(constantes.VALOR_OPCION_CONTENEDOR, transporte.transportista.razonSocial);
	  this.obj.cmpTransportista.empty().append(elemento1).val(transporte.idTransportista).trigger('change');

	  var elemento2=constantes.PLANTILLA_OPCION_SELECTBOX;
	  elemento2 = elemento2.replace(constantes.ID_OPCION_CONTENEDOR,transporte.idPlantaDespacho);
	  elemento2 = elemento2.replace(constantes.VALOR_OPCION_CONTENEDOR, transporte.plantaDespacho.descripcion);
	  this.obj.cmpPlantaDespacho.empty().append(elemento2).val(transporte.idPlantaDespacho).trigger('change');
	  
	  var elemento3=constantes.PLANTILLA_OPCION_SELECTBOX;
	  elemento3 = elemento3.replace(constantes.ID_OPCION_CONTENEDOR, transporte.idConductor);
	  elemento3 = elemento3.replace(constantes.VALOR_OPCION_CONTENEDOR, transporte.conductor.nombreCompleto);
	  this.obj.cmpConductor.empty().append(elemento3).val(transporte.idConductor).trigger('change');
	
	  this.obj.idTransportista = transporte.idTransportista;

	  var elemento3 =constantes.PLANTILLA_OPCION_SELECTBOX;
	  elemento3 = elemento3.replace(constantes.ID_OPCION_CONTENEDOR, transporte.idCisterna);
	  elemento3 = elemento3.replace(constantes.VALOR_OPCION_CONTENEDOR, transporte.cisternaTracto);
	  this.obj.cmpCisternaTracto.empty().append(elemento3).val(transporte.idCisterna).trigger('change');

	  moduloActual.cmpIdTracto = transporte.idTracto;
  	  moduloActual.cmpPlacaCisterna = transporte.placaCisterna;
  	  moduloActual.cmpPlacaTracto = transporte.placaTracto;
  	  moduloActual.cmpBreveteConductor = transporte.breveteConductor;
  	  moduloActual.idCliente = diaOperativo.operacion.cliente.id;

  	  var numeroDetalles= transporte.detalles.length;
	  var sumaVolumenTempObs = 0;
	  var sumaVol60F = 0;

	  this.obj.grupoTransporte.removeAllForms();
	  for(var contador=0; contador < numeroDetalles; contador++){
		  moduloActual.obj.grupoTransporte.addForm();
		  var formulario= moduloActual.obj.grupoTransporte.getForm(contador);
		  formulario.find("input[elemento-grupo='compartimentos']").val(contador); // contador
		  formulario.find("select[elemento-grupo='producto']").select2("val", transporte.detalles[contador].idProducto); // id_producto
		  formulario.find("input[elemento-grupo='volumenTempObservada']").val(transporte.detalles[contador].volumenTemperaturaObservada); // volumen_temperatura_observada
		  formulario.find("input[elemento-grupo='temperatura']").val(transporte.detalles[contador].temperaturaObservada); // temperatura_observada
		  formulario.find("input[elemento-grupo='API']").val(transporte.detalles[contador].apiTemperaturaBase); // api_temperatura_base
		  formulario.find("input[elemento-grupo='factor']").val(transporte.detalles[contador].factorCorrecion); // factor_correcion
		  formulario.find("input[elemento-grupo='volumen60F']").val(transporte.detalles[contador].volumenTemperaturaBase); // volumen_temperatura_base

		  sumaVolumenTempObs = sumaVolumenTempObs + parseFloat(transporte.detalles[contador].volumenTemperaturaObservada);      
		  sumaVol60F = sumaVol60F + parseFloat(transporte.detalles[contador].volumenTemperaturaBase);
	  }
	  this.obj.cmpSumVolumenTempObservada.val(sumaVolumenTempObs);
	  this.obj.cmpSumVolumen60F.val(sumaVol60F);
  };

  moduloActual.recuperarValores = function(registro){
    var eRegistro = {};
    var eTransporte={};
    var referenciaModulo=this;
    try {
	    //datos para la asignacion
	    eRegistro.id = parseInt(referenciaModulo.idRegistro);
	    eRegistro.idDoperativo = parseInt(referenciaModulo.idDiaOperativo);
	    eRegistro.idTransporte = parseInt(referenciaModulo.idTransporte);

	    eRegistro.id = referenciaModulo.obj.cmpId.val();
	    //datos para transporte
	    eRegistro.transportes=[];
	    eTransporte.id = parseInt(referenciaModulo.idTransporte);
	    eTransporte.numeroGuiaRemision = referenciaModulo.obj.cmpNumeroGuiaRemision.val();
	    eTransporte.numeroOrdenCompra = referenciaModulo.obj.cmpNumeroOrdenCompra.val();
	    eTransporte.numeroFactura = referenciaModulo.obj.cmpNumeroFactura.val();
	    eTransporte.codigoScop = referenciaModulo.obj.cmpCodigoScop.val();
	    
	    eTransporte.idPlantaDespacho = parseInt(referenciaModulo.obj.cmpPlantaDespacho.val());
	    eTransporte.idTransportista = parseInt(referenciaModulo.obj.cmpTransportista.val());
	    eTransporte.idCisterna = parseInt(referenciaModulo.obj.cmpCisternaTracto.val());
	    eTransporte.idTracto = parseInt(moduloActual.cmpIdTracto);
	    eTransporte.placaCisterna = moduloActual.cmpPlacaCisterna;
	    eTransporte.placaTracto = moduloActual.cmpPlacaTracto;
	    
	    eTransporte.idConductor = parseInt(referenciaModulo.obj.cmpConductor.val());
	    eTransporte.breveteConductor = moduloActual.cmpBreveteConductor;
	    eTransporte.fechaEmisionGuia = utilitario.formatearStringToDate(referenciaModulo.obj.cmpFemisionOE.val());
	    eTransporte.precintosSeguridad = referenciaModulo.obj.cmpPrecintos.val();
	    
	    eTransporte.volumenTotalObservado = parseFloat(referenciaModulo.obj.cmpSumVolumenTempObservada.val().replace(moduloActual.SEPARADOR_MILES,""));
	    eTransporte.volumenTotalCorregido = parseFloat(referenciaModulo.obj.cmpSumVolumen60F.val().replace(moduloActual.SEPARADOR_MILES,""));
	    
	    console.log("moduloActual.idCliente " + moduloActual.idCliente);
	    eTransporte.idCliente = parseInt(moduloActual.idCliente);

	    eTransporte.detalles=[];
	    //datos para detalleTransporte
	      var numeroFormularios = referenciaModulo.obj.grupoTransporte.getForms().length;
	      for(var contador = 0; contador < numeroFormularios; contador++){
	        var detalles={};
	        var formulario = referenciaModulo.obj.grupoTransporte.getForm(contador);
	        var cmpCompartimentos			= formulario.find("input[elemento-grupo='compartimentos']");
	        var cmpElementoProducto			= formulario.find("select[elemento-grupo='producto']");
	        var cmpVolumenTempObservada		= formulario.find("input[elemento-grupo='volumenTempObservada']");
	        var cmpTemperatura				= formulario.find("input[elemento-grupo='temperatura']");
	        var cmpAPI						= formulario.find("input[elemento-grupo='API']");
	        var cmpFactor					= formulario.find("input[elemento-grupo='factor']");
	        var cmpVolumen60F				= formulario.find("input[elemento-grupo='volumen60F']");
	        detalles.idTransporte			= parseInt(referenciaModulo.idTransporte);
	        detalles.numeroCompartimento	= parseInt(cmpCompartimentos.val());
	        detalles.idProducto				= parseInt(cmpElementoProducto.val());
	        detalles.volumenTemperaturaObservada = parseFloat(cmpVolumenTempObservada.val().replace(moduloActual.SEPARADOR_MILES,""));
	        detalles.temperaturaObservada 	= parseFloat(cmpTemperatura.val().replace(moduloActual.SEPARADOR_MILES,""));
	        detalles.apiTemperaturaBase 	= parseFloat(cmpAPI.val().replace(moduloActual.SEPARADOR_MILES,""));
	        detalles.factorCorrecion 		= parseFloat(cmpFactor.val().replace(moduloActual.SEPARADOR_MILES,""));
	        detalles.volumenTemperaturaBase = parseFloat(cmpVolumen60F.val().replace(moduloActual.SEPARADOR_MILES,""));
	        eTransporte.detalles.push(detalles);
	      }
	      eRegistro.transportes.push(eTransporte);
    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
  moduloActual.llenarDetalleTransporte = function(){
	var referenciaModulo = this;
	moduloActual.obj.detalleCliente.text(referenciaModulo.obj.clienteSeleccionado);
	moduloActual.obj.detalleOperacion.text(referenciaModulo.obj.operacionSeleccionado);
	moduloActual.obj.detalleFechaPlanificacion.text(utilitario.formatearFecha(referenciaModulo.obj.fechaPlanificacionSeleccionado));
  };

	 /* moduloActual.guardarFormulario = function(){
		var referenciaModulo = this;
		//Ocultar alertas de mensaje
		if (referenciaModulo.obj.frmEvento.valid()){
			referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petición...");
			//referenciaModulo.protegeFormulario(true);
			if(this.obj.cmpEventoFechaHora.val() == ""){
				referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"La fecha y hora debe estar informada.");
		    	//referenciaModulo.protegeFormulario(false);
	    	} else if (this.obj.cmpEventoDescripcion.val() == ""){
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"La observaci&oacute;n debe ser informada.");
	    		//referenciaModulo.protegeFormulario(false);
			} else {
				var eRegistro = referenciaModulo.recuperarValoresEvento();
				$.ajax({
				    type: "POST",
				    url: referenciaModulo.URL_GUARDAR_EVENTO, 
				    contentType: "application/json", 
				    data: JSON.stringify(eRegistro),	
				    success: function(respuesta) {
				    	if (!respuesta.estado) {
				    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
				    		//referenciaModulo.protegeFormulario(false);
				    	} 	else {		    	
				    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
				    		//referenciaModulo.protegeFormulario(false);
				    		moduloActual.modoEdicion=constantes.MODO_DETALLE_TRANSPORTE;
					    	moduloActual.obj.cntDetalleTransporte.show();
					    	moduloActual.obj.cntEventoTransporte.hide();
			    		}
				    },			    		    
				    error: function() {
				    	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la peticion");
				    	//referenciaModulo.protegeFormulario(false);
				    }
				});
			}
		} else {
			console.log("No valido");
		}
	};*/
	  
//========================== cntVistaRegistro ============================================================ 
  moduloActual.llenarDetalles = function(registro){
	  var referenciaModulo = this;
		 //identificadores para las busquedas
		this.obj.idDiaOperativo = registro.idDoperativo;
		this.obj.idTransporte = registro.idTransporte;
		
	  var transporte = registro.transportes[0];
	  var diaOperativo = registro.diaOperativo[0];
	  
		this.obj.vistaCliente.text(diaOperativo.operacion.cliente.razonSocial);
		this.obj.vistaOperacion.text(diaOperativo.operacion.nombre);
		this.obj.vistaFechaPlanificacion.text(utilitario.formatearFecha(diaOperativo.fechaOperativa));
		this.obj.vistaNumeroGuiaRemision.text(transporte.numeroGuiaRemision);
		this.obj.vistaNumeroOrdenCompra.text(transporte.numeroOrdenCompra);
		this.obj.vistaNumeroFactura.text(transporte.numeroFactura);
		this.obj.vistaCodigoScop.text(transporte.codigoScop);
		this.obj.vistaFechaEmisionGuia.text(utilitario.formatearFecha(transporte.fechaEmisionGuia));
		this.obj.vistaPlantaDespacho.text(transporte.plantaDespacho.descripcion);
		this.obj.vistaPlantaRecepcion.text(transporte.plantaRecepcion.Descripcion);
		this.obj.vistaIdCliente.text(transporte.idCliente);
		this.obj.vistaIdConductor.text(transporte.conductor.apellidos + ", " + transporte.conductor.nombres);
		this.obj.vistaBreveteConductor.text(transporte.breveteConductor);
		this.obj.vistaIdCisterna.text(transporte.idCisterna);
		this.obj.vistaPlacaCisterna.text(transporte.placaCisterna);
		this.obj.vistaTarjetaCubicacionCompartimento.text(transporte.tarjetaCubicacionCompartimento);
		this.obj.vistaIdTracto.text(transporte.idTracto);
		this.obj.vistaPlacaTracto.text(transporte.placaTracto);
		this.obj.vistaIdTransportista.text(transporte.transportista.razonSocial);
		this.obj.vistaVolumenTotalObservado.text(transporte.volumenTotalObservado);
		this.obj.vistaVolumenTotalCorregido.text(transporte.volumenTotalCorregido);
		this.obj.vistaEstado.text(transporte.estado);
		this.obj.vistaSincronizadoEl.text(transporte.sincronizadoEl);
		this.obj.vistaCisternaTracto.text(transporte.cisternaTracto);
		this.obj.vistaPrecintos.text(transporte.precintosSeguridad);
	    
	    //detalle de transportes
	    var indice= transporte.detalles.length;
	    var fila = $('#listado_vista_detalles');
	    $('#listado_vista_detalles').html("");
	    g_tr = '<thead><tr><th class="text-center">Compartimentos</th>' +
	    				  '<th class="text-center">Producto</th>' + 
	    				  '<th class="text-center">Vol. T. Obs. (gal)</th>' + 
	    				  '<th class="text-center">Temperatura (F)</th>' + 
	    				  '<th class="text-center">API 60 F</th>' + 
	    				  '<th class="text-center">Factor</th>' + 
	    				  '<th class="text-center">Vol 60F (gal)</th></tr></thead>'; 
	    fila.append(g_tr);
	    for(var k = 0; k < indice; k++){ 	
	    g_tr  = '<tr><td class="text-right">' + k    + '</td>' + // compartimiento
	    '    <td class="text-right">' +transporte.detalles[k].idProducto   + '</td>' + // fecha_emision
	    '    <td class="text-right">' +transporte.detalles[k].volumenTemperaturaObservada  + '</td>' + // volumen_temperatura_observada
	    '    <td class="text-right">' +transporte.detalles[k].temperaturaObservada + '</td>' + // temperatura_observada
	    '    <td class="text-right">' +transporte.detalles[k].apiTemperaturaBase + '</td>' + // api_temperatura_base
	    '    <td class="text-right">' +transporte.detalles[k].factorCorrecion + '</td>' + // factor_correcion
	    '    <td class="text-right">' +transporte.detalles[k].volumenTemperaturaBase + '</td></tr>'; // volumen_temperatura_base
	    fila.append(g_tr);
	    }
	    g_tr = '<tr><th></td>' +
	    	   	   '<th class="text-right">Vol. T. Obs. Total (gal):</th>' +
	    	   	   '<th class="text-right">' + transporte.volumenTotalObservado + '</th>' +
	    	   	   '<th></th>' +
	    	   	   '<th></th>' +
	    	   	   '<th class="text-right">Vol. 60 F Total (gal):</th>' +
	    	   	   '<th class="text-right">' + transporte.volumenTotalCorregido + '</th></tr>'; 
	    fila.append(g_tr);

	    //detalle de eventos
	    var indiceEvento= transporte.eventos.length;
	    var filaEvento = $('#listado_vista_eventos');
	    $('#listado_vista_eventos').html("");

	    for(var r = 0; r < indiceEvento; r++){ 	
	    	g_tr = '<tr><td><label>Tipo:<label></td><td>' +utilitario.formatearTipoEvento(transporte.eventos[r].tipoEvento)   + ' </td>'+
	    			   '<td width="20"></td><td><label>Fecha y hora:<label></td><td>' +utilitario.formatearTimestampToString(transporte.eventos[r].fechaHoraTimestamp) + '</td><tr>';
	    	filaEvento.append(g_tr);
		    g_tr = '<tr><td><label>Observaciones:<label></td><tr>';
		    filaEvento.append(g_tr);
		    g_tr = '<tr><td colspan="5">' + transporte.eventos[r].descripcion   + '</td><tr>';
		    filaEvento.append(g_tr);
		    
		    if(indiceEvento != r+1){
		    	 g_tr = '<tr><td colspan="5"><hr style="color: #0056b2;" /></td><tr>';
				 filaEvento.append(g_tr);
		    }
	    }
  };		

//========================== cntEventoTransporte ============================================================  
  moduloActual.llenarEventoTransporte = function(registro){
	  var referenciaModulo = this;
		 //identificadores para las busquedas
	  this.obj.idDiaOperativo = registro.idDoperativo;
	  this.obj.idTransporte = registro.idTransporte;
		
	  var transporte = registro.transportes[0];
	  var diaOperativo = registro.diaOperativo[0];

	  this.obj.cmpEventoIdTransporteEvento.val(registro.idTransporte);
		  
	  this.obj.cmpEventoCliente.text(diaOperativo.operacion.cliente.razonSocial);
	  this.obj.cmpEventoOperacion.text(diaOperativo.operacion.nombre);
	  this.obj.cmpEventoFechaPlanificacion.text(utilitario.formatearFecha(diaOperativo.fechaOperativa));
  
	  this.obj.cmpEventoNumeroGuiaRemision.text(transporte.numeroGuiaRemision);
	  this.obj.cmpEventoNumeroOrdenCompra.text(transporte.numeroOrdenCompra);
	  this.obj.cmpEventoCisternaTracto.text(transporte.cisternaTracto);
  };

  moduloActual.guardarEvento = function(){
		var referenciaModulo = this;
		//Ocultar alertas de mensaje
		if (referenciaModulo.obj.frmEvento.valid()){
			referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petición...");
			//referenciaModulo.protegeFormulario(true);
			if(this.obj.cmpEventoFechaHora.val() == ""){
				referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"La fecha y hora debe estar informada.");
		    	//referenciaModulo.protegeFormulario(false);
	    	} else if (this.obj.cmpEventoDescripcion.val() == ""){
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"La observaci&oacute;n debe ser informada.");
	    		//referenciaModulo.protegeFormulario(false);
			} else {
				var eRegistro = referenciaModulo.recuperarValoresEvento();
				$.ajax({
				    type: "POST",
				    url: referenciaModulo.URL_GUARDAR_EVENTO, 
				    contentType: "application/json", 
				    data: JSON.stringify(eRegistro),	
				    success: function(respuesta) {
				    	if (!respuesta.estado) {
				    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
				    		//referenciaModulo.protegeFormulario(false);
				    	} 	else {		    	
				    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
				    		//referenciaModulo.protegeFormulario(false);
				    		moduloActual.modoEdicion=constantes.MODO_DETALLE_TRANSPORTE;
					    	moduloActual.obj.cntDetalleTransporte.show();
					    	moduloActual.obj.cntEventoTransporte.hide();
			    		}
				    },			    		    
				    error: function() {
				    	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la peticion");
				    	//referenciaModulo.protegeFormulario(false);
				    }
				});
			}
		} else {
			console.log("No valido");
		}
	};
	
	moduloActual.recuperarValoresEvento = function(registro){
	    var eRegistro = {};
	    var referenciaModulo=this;
	    try {
		    eRegistro.tipoRegistro = 1; //1 porque el tipo de registro es transporte
		    eRegistro.tipoEvento = parseInt(this.obj.cmpEventoTipoEvento.val());
		    eRegistro.fechaHora  = utilitario.formatearStringToDateHour(this.obj.cmpEventoFechaHora.val());
		    eRegistro.descripcion = this.obj.cmpEventoDescripcion.val().toUpperCase();
		    eRegistro.idRegistro = parseInt(this.obj.cmpEventoIdTransporteEvento.val());
		    
	    } catch(error){
	      console.log(error.message);
	    }
	    return eRegistro;
	  };

//========================== FIN cntEventoTransporte ========================================================  	  

//========================== cntPesajeTransporte ============================================================  
  moduloActual.llenarPesajeTransporte = function(registro){
	  var referenciaModulo = this;

	  //identificadores para las busquedas
	  this.obj.idDiaOperativo = registro.idDoperativo;
	  this.obj.idTransporte = registro.idTransporte;

	  var transporte = registro.transportes[0];
	  var diaOperativo = registro.diaOperativo[0];

	  this.obj.cmpPesajeIdTransporte.val(registro.idTransporte);
	  this.obj.cmpPesajeCliente.text(diaOperativo.operacion.cliente.razonSocial);
	  this.obj.cmpPesajeOperacion.text(diaOperativo.operacion.nombre);
	  this.obj.cmpPesajeFechaPlanificacion.text(utilitario.formatearFecha(diaOperativo.fechaOperativa));

	  this.obj.cmpPesajeNumeroGuiaRemision.text(transporte.numeroGuiaRemision);
	  this.obj.cmpPesajeNumeroOrdenCompra.text(transporte.numeroOrdenCompra);
	  this.obj.cmpPesajeCisternaTracto.text(transporte.cisternaTracto);

	  this.obj.cmpPesajePesoBruto.val(transporte.pesoBruto);
	  this.obj.cmpPesajePesoTara.val(transporte.pesoTara);
	  this.obj.cmpPesajePesoNeto.val(transporte.pesoNeto);
  };

  moduloActual.guardarPesaje= function(){
		var referenciaModulo = this;

		//Ocultar alertas de mensaje
		if (referenciaModulo.obj.frmPesaje.valid()){
			referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,"Procesando petición...");
			//referenciaModulo.protegeFormulario(true);
			if(this.obj.cmpPesajePesoBruto.val() == 0){
				referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"El Peso Bruto no puede ser 0.");
		    	//referenciaModulo.protegeFormulario(false);
	    	} else if (this.obj.cmpPesajePesoTara.val() == 0){
	    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"El Peso Tara no puede ser 0.");
	    		//referenciaModulo.protegeFormulario(false);
			}
			else{
				var eRegistro = referenciaModulo.recuperarValoresPesaje();
				$.ajax({
				    type: "POST",
				    url: referenciaModulo.URL_ACTUALIZAR_PESAJE, 
				    contentType: "application/json", 
				    data: JSON.stringify(eRegistro),	
				    success: function(respuesta) {
				    	if (!respuesta.estado) {
				    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
				    		//referenciaModulo.protegeFormulario(false);
				    	} 	else {		    	
				    		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_EXITO,respuesta.mensaje);
				    		//referenciaModulo.protegeFormulario(false);
				    		moduloActual.modoEdicion=constantes.MODO_DETALLE_TRANSPORTE;
					    	moduloActual.obj.cntDetalleTransporte.show();
					    	moduloActual.obj.cntPesajeTransporte.hide();
			    		}
				    },			    		    
				    error: function() {
				    	referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,"Hubo un error en la peticion");
				    	//referenciaModulo.protegeFormulario(false);
				    }
				});
			}
		} else {
			console.log("No valido");
		}	
	};
	
	moduloActual.recuperarValoresPesaje = function(registro){
	    var eRegistro = {};
	    var referenciaModulo=this;
	    try {
    		eRegistro.id = parseInt(this.obj.cmpPesajeIdTransporte.val());
	    	eRegistro.pesoBruto = parseFloat(this.obj.cmpPesajePesoBruto.val().replace(moduloActual.SEPARADOR_MILES,""));
	    	eRegistro.pesoTara = parseFloat(this.obj.cmpPesajePesoTara.val().replace(moduloActual.SEPARADOR_MILES,""));
	    	eRegistro.pesoNeto = parseFloat(this.obj.cmpPesajePesoNeto.val().replace(moduloActual.SEPARADOR_MILES,""));
	    }  catch(error){
	      console.log(error.message);
	    }
	    return eRegistro;
	  };

//========================== FIN cntPesajeTransporte ========================================================  	  

  moduloActual.sumaVolumenTempObservada = function(){
    var referenciaModulo=this;
    var suma = 0;
    try {
      var numeroFormularios = referenciaModulo.obj.grupoTransporte.getForms().length;
      for(var contador = 0;contador < numeroFormularios; contador++){
        var formulario = referenciaModulo.obj.grupoTransporte.getForm(contador);   
        var cmpVolumenTempObservada = formulario.find("input[elemento-grupo='volumenTempObservada']");
        suma = suma + parseFloat(moduloActual.eliminaSeparadorComa(cmpVolumenTempObservada.val()));
      }
      this.obj.cmpSumVolumenTempObservada.val(suma);
    }  catch(error){
      console.log(error.message);
    }
  };
  
  moduloActual.sumaVolumen60F = function(){
	    var referenciaModulo=this;
	    var suma = 0;
	    try {
	      var numeroFormularios = referenciaModulo.obj.grupoTransporte.getForms().length;
	      for(var contador = 0;contador < numeroFormularios; contador++){
	        var formulario = referenciaModulo.obj.grupoTransporte.getForm(contador);   
	        var cmpVolumen60F = formulario.find("input[elemento-grupo='volumen60F']");
	        suma = suma + parseFloat(moduloActual.eliminaSeparadorComa(cmpVolumen60F.val()));
	      }
	      this.obj.cmpSumVolumen60F.val(suma);
	    }  catch(error){
	      console.log(error.message);
	    }
	  };
  
//========================== FIN cntPesajeTransporte ========================================================  	 

moduloActual.validarDetallesTransporte = function(retorno){
	referenciaModulo = this;
	retorno = false;
	try{
		var numeroFormularios = referenciaModulo.obj.grupoTransporte.getForms().length;
		for(var contador = 0; contador < numeroFormularios; contador++){
	        var formulario = referenciaModulo.obj.grupoTransporte.getForm(contador);
	        var cmpVolumenTempObservada		= formulario.find("input[elemento-grupo='volumenTempObservada']");
	        var cmpTemperatura				= formulario.find("input[elemento-grupo='temperatura']");
	        var cmpAPI						= formulario.find("input[elemento-grupo='API']");
	        var cmpFactor					= formulario.find("input[elemento-grupo='factor']");
	        var cmpVolumen60F				= formulario.find("input[elemento-grupo='volumen60F']");
	        
	        if((cmpVolumenTempObservada!=null && parseInt(cmpVolumenTempObservada.val()) > 0 )  &&
	        	(cmpTemperatura!=null 	&& parseInt(cmpTemperatura.val()) > 0 ) &&
	        	(cmpAPI!=null		 	&& parseInt(cmpAPI.val()) > 0 ) &&
	        	(cmpFactor!=null 		&& parseInt(cmpFactor.val()) > 0 ) &&
	        	(cmpVolumen60F!=null 	&& parseInt(cmpVolumen60F.val()) > 0 )){
	        	console.log("este dato es válido");
	        	return true;
	        }
	    }
		console.log("se ha recorrido todo y se ha encontrado incongruencias.........");
		return retorno;
		}
		catch(error){
		      console.log(error.message);
			
		}
	};
	
  moduloActual.inicializar();
});

