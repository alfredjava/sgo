$(document).ready(function(){
  $.fn.select2.defaults.set( "theme", "bootstrap" );
  var moduloActual = new moduloPlanificacion();  
  moduloActual.urlBase='planificacion';
  moduloActual.SEPARADOR_MILES = ",";
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
  moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
  moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';
  moduloActual.URL_RECUPERAR_ULTIMO_DIA='../admin/dia_operativo/recuperar-ultimo-dia';
  moduloActual.ordenGrilla=[[ 2, 'asc' ]];  
  moduloActual.columnasGrilla.push({ "data": 'id'}); 
  moduloActual.columnasGrilla.push({ "data": 'fechaOperativa'});
  moduloActual.columnasGrilla.push({ "data": 'totalCisternas'});
  moduloActual.columnasGrilla.push({ "data": 'detalleProductoSolicitado'});
  moduloActual.columnasGrilla.push({ "data": 'fechaActualizacion'});
  moduloActual.columnasGrilla.push({ "data": 'usuarioActualizacion'});
  moduloActual.columnasGrilla.push({ "data": 'estado'});
  //Columnas
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
  "visible" : true,
  "render" : utilitario.formatearFecha
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
  "searchable" : true,
  "orderable" : true,
  "visible" : true
  });
  moduloActual.definicionColumnas.push({
  "targets" : 7,
  "orderable" : true,
  "visible" : true,
  "render" : utilitario.formatearEstadoDiaOperativo
  });

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
    console.log(fechaActual);
    var rangoSemana = utilitario.retornarRangoSemana(fechaActual);
    console.log(rangoSemana);
    console.log(utilitario.formatearFecha2Cadena(rangoSemana.fechaInicial) + " - " +utilitario.formatearFecha2Cadena(rangoSemana.fechaFinal));
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
    //Campos formulario
    this.obj.cmpCliente = $("#cmpCliente");
    this.obj.cmpOperacion = $("#cmpOperacion");
    this.obj.cmpFechaPlanificada = $("#cmpFechaPlanificada");
    this.obj.cmpIdOperacion = $("#cmpIdOperacion"); 
    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaCliente=$("#vistaCliente");
    this.obj.vistaOperacion=$("#vistaOperacion");
    this.obj.vistaFechaPlanificacion=$("#vistaFechaPlanificacion");
    this.obj.vistaListaPlanificaciones=$("#vistaListaPlanificaciones");
    //Vista de auditoria
    this.obj.vistaActualizadoEl=$("#vistaActualizadoEl");
    this.obj.vistaActualizadoPor=$("#vistaActualizadoPor");
    this.obj.vistaIpActualizacion=$("#vistaIpActualizacion");    
    //botones Adicionales
    this.obj.btnAgregarProducto=$("#btnAgregarProducto");    
    this.obj.grupoPlanificacion = $('#GrupoPlanificacion').sheepIt({
      separator: '',
      allowRemoveLast: true,
      allowRemoveCurrent: true,
      allowRemoveAll: true,
      allowAdd: true,
      allowAddN: false,
      maxFormsCount: 6,
      minFormsCount: 0,
      iniFormsCount: 0,
      afterAdd: function(origen, formularioNuevo) {
        var cmpElementoProducto=$(formularioNuevo).find("select[elemento-grupo='producto']");
        var cmpVolumenPropuesto=$(formularioNuevo).find("input[elemento-grupo='volumenPropuesto']");
        var cmpVolumenSolicitado=$(formularioNuevo).find("input[elemento-grupo='volumenSolicitado']");
        var cmpNumeroCisternas=$(formularioNuevo).find("input[elemento-grupo='numeroCisternas']");
        cmpElementoProducto.select2();
        cmpVolumenPropuesto.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
        cmpVolumenSolicitado.inputmask('decimal', {digits: 2, groupSeparator:',',autoGroup:true,groupSize:3});
        cmpNumeroCisternas.inputmask('decimal', {digits: 0});
        cmpNumeroCisternas.on("input",function(){
          var totalVolumen = parseFloat(moduloActual.volumenPromedioCisterna) * parseInt(cmpNumeroCisternas.val()); 
          cmpVolumenSolicitado.val(totalVolumen);
        });      
      },
      afterRemoveCurrent: function(control) {
        if (control.hasForms()==false){
          control.addForm();          
        }
      }
    });    
    this.obj.btnAgregarProducto.on("click",function(){
      try {
        moduloActual.obj.grupoPlanificacion.addForm();
      } catch(error){
      console.log(error.message);
      }
    });    
  };  
  
  moduloActual.iniciarAgregar= function(){
    var referenciaModulo=this;
    var nombreOperacion="";
    var nombreCliente="";
    try {
      referenciaModulo.obj.grupoPlanificacion.removeAllForms();
      referenciaModulo.obj.grupoPlanificacion.addForm();  
      referenciaModulo.idOperacion = referenciaModulo.obj.filtroOperacion.val();
      referenciaModulo.volumenPromedioCisterna=$("#filtroOperacion option[value='"+referenciaModulo.idOperacion+"']").attr('data-volumen-promedio-cisterna');
      nombreOperacion=$("#filtroOperacion option[value='"+referenciaModulo.idOperacion+"']").attr('data-nombre-operacion');
      nombreCliente=$("#filtroOperacion option[value='"+referenciaModulo.idOperacion+"']").attr('data-nombre-cliente');
      referenciaModulo.modoEdicion=constantes.MODO_NUEVO;
      referenciaModulo.obj.cmpTituloFormulario.text(constantes.TITULO_AGREGAR_REGISTRO);
      referenciaModulo.resetearFormulario();
      //Asignar valores a las cajas
      referenciaModulo.obj.cmpCliente.val(nombreCliente);
      referenciaModulo.obj.cmpOperacion.val(nombreOperacion);
      referenciaModulo.obj.cntTabla.hide();
      referenciaModulo.obj.cntVistaRegistro.hide();
      referenciaModulo.obj.cntFormulario.show();
      referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_INFO,cadenas.INICIAR_OPERACION);
      $.ajax({
        type: constantes.PETICION_TIPO_GET,
        url: referenciaModulo.URL_RECUPERAR_ULTIMO_DIA, 
        contentType: referenciaModulo.TIPO_CONTENIDO, 
        data: {idOperacion:referenciaModulo.idOperacion},
        success: function(respuesta) {
          if (!respuesta.estado) {
            referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR,respuesta.mensaje);
          } else {
            referenciaModulo.fechaOperativa=respuesta.valor;
            referenciaModulo.obj.cmpFechaPlanificada.val(referenciaModulo.fechaOperativa);
          }
          referenciaModulo.obj.ocultaContenedorFormulario.hide();
        },
        error: function(xhr,estado,error) {
          referenciaModulo.mostrarErrorServidor(xhr,estado,error); 
        }
      });  
    } catch(error){
      referenciaModulo.mostrarDepuracion(error.message);
    }
  };

  moduloActual.llenarFormulario = function(registro){
    var referenciaModulo=this;
    this.idRegistro = registro.id;
    this.obj.cmpCliente.val(registro.operacion.cliente.razonSocial);
    this.obj.cmpOperacion.val(registro.operacion.nombre);	
    this.obj.cmpIdOperacion.val(registro.operacion.id);
    referenciaModulo.idOperacion = registro.operacion.id;	
    referenciaModulo.fechaOperativa = utilitario.formatearFecha(registro.fechaOperativa);
    referenciaModulo.obj.cmpFechaPlanificada.val(utilitario.formatearFecha(registro.fechaOperativa));	
    moduloActual.volumenPromedioCisterna = registro.operacion.volumenPromedioCisterna;
    var numeroPlanificaciones= registro.planificaciones.length;
    referenciaModulo.obj.grupoPlanificacion.removeAllForms();
    for(var contador=0; contador < numeroPlanificaciones;contador++){
      referenciaModulo.obj.grupoPlanificacion.addForm();
      var formulario= referenciaModulo.obj.grupoPlanificacion.getForm(contador);
      formulario.find("select[elemento-grupo='producto']").select2("val", registro.planificaciones[contador].idProducto);
      formulario.find("input[elemento-grupo='volumenPropuesto']").val(registro.planificaciones[contador].volumenPropuesto);
      formulario.find("input[elemento-grupo='volumenSolicitado']").val(registro.planificaciones[contador].volumenSolicitado);
      formulario.find("input[elemento-grupo='numeroCisternas']").val(registro.planificaciones[contador].cantidadCisternas);
    } 
  };
  
  moduloActual.llenarDetalles = function(registro){
    this.idRegistro = registro.id;
    this.obj.vistaId.text(registro.id);
    this.obj.vistaCliente.text(registro.operacion.cliente.razonSocial);
    this.obj.vistaOperacion.text(registro.operacion.nombre);
    this.obj.vistaFechaPlanificacion.text(utilitario.formatearFecha(registro.fechaOperativa));
    this.obj.vistaListaPlanificaciones=$("#vistaListaPlanificaciones");
    var indice= registro.planificaciones.length;
    var grilla = $('#listado_planificaciones');
    $('#listado_planificaciones').html("");
    g_tr = '<thead><tr><th class="text-center">Producto</th>' +
	  '<th class="text-center">Vol&uacute;men Propuesto</th>' + 
	  '<th class="text-center">Vol&uacute;men Solicitado</th>' + 
	  '<th class="text-center">N# Cisternas</th></tr></thead>'; 
    
 //   g_tr = '<thead><tr><th class="text-center">Producto</th><th class="text-center">Vol&uacute;men Propuesto</th><th>Vol&uacute;men Solicitado</th><th>N# Cisternas</th></tr></thead>'; 
    grilla.append(g_tr);
    for(var k = 0; k < indice; k++){ 	
    g_tr  = '<tr> '+
    '    <td width="40%">' +registro.planificaciones[k].producto.nombre   + '</td>' + // Descripción del producto
    '    <td class="text-right" width="20%">' +registro.planificaciones[k].volumenPropuesto  + '</td>' + // Volúmen Propuesto
    '    <td class="text-right" width="20%">' +registro.planificaciones[k].volumenSolicitado + '</td>' + // Volúmen Solicitado
    '    <td class="text-right" width="20%">' +registro.planificaciones[k].cantidadCisternas + '</td></tr>'; // Cantidad de Cisternas
    grilla.append(g_tr);
    }
    //Vista de auditoria
    this.obj.vistaActualizadoEl.text(registro.planificaciones[0].fechaActualizacion);
    this.obj.vistaActualizadoPor.text(registro.planificaciones[0].usuarioActualizacion);
    this.obj.vistaIpActualizacion.text(registro.planificaciones[0].ipActualizacion);
  };
  
  moduloActual.recuperarValores = function(registro){
	var referenciaModulo=this;
    var eRegistro = null;

    try {
    	eRegistro={};
       eRegistro.id = parseInt(referenciaModulo.idRegistro);
       eRegistro.fechaOperativa = utilitario.formatearStringToDate(referenciaModulo.fechaOperativa);
       eRegistro.idOperacion = parseInt(referenciaModulo.idOperacion);
       eRegistro.planificaciones=[];
       var numeroFormularios = referenciaModulo.obj.grupoPlanificacion.getForms().length;
       for(var contador = 0;contador < numeroFormularios;contador++){
        var planificacion={};
        var formulario = referenciaModulo.obj.grupoPlanificacion.getForm(contador);        
        var cmpProducto = formulario.find("select[elemento-grupo='producto']");
        var cmpVolumenPropuesto = formulario.find("input[elemento-grupo='volumenPropuesto']");
        var cmpVolumenSolicitado= formulario.find("input[elemento-grupo='volumenSolicitado']");
        var cmpNumeroCisternas= formulario.find("input[elemento-grupo='numeroCisternas']");
        planificacion.idProducto= parseInt(cmpProducto.val());
        planificacion.volumenPropuesto= parseFloat(cmpVolumenPropuesto.val().replace(moduloActual.SEPARADOR_MILES,""));
        planificacion.volumenSolicitado= parseFloat(cmpVolumenSolicitado.val().replace(moduloActual.SEPARADOR_MILES,""));
        planificacion.cantidadCisternas= parseInt(cmpNumeroCisternas.val());
        eRegistro.planificaciones.push(planificacion);
        console.log(eRegistro);
      }
    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
  moduloActual.validarProductoPlanificacion = function(retorno){
		referenciaModulo = this;
		retorno = true;
		try{
			var numeroFormularios = referenciaModulo.obj.grupoPlanificacion.getForms().length;
			for(var contador = 0; contador < numeroFormularios;contador++){
		        var formulario = referenciaModulo.obj.grupoPlanificacion.getForm(contador);        
		        var cmpProducto = formulario.find("select[elemento-grupo='producto']");
		        var cmpNumeroCisternas= formulario.find("input[elemento-grupo='numeroCisternas']");
		        if(parseInt(cmpNumeroCisternas.val()) > 0 ){
			        for(var comparador = 0; comparador < numeroFormularios; comparador++){
			        	if(comparador != contador){
				        	var comparacion = referenciaModulo.obj.grupoPlanificacion.getForm(comparador);
				        	var producto = comparacion.find("select[elemento-grupo='producto']");
				        	if(cmpProducto.val() != producto.val()){
				        		retorno = true;
				        	}
				        	else{
				        		referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR, "No puede planificar 2 productos iguales. ");
				        		referenciaModulo.obj.ocultaContenedorFormulario.hide();
				        		retorno = false;
				        		return retorno;
				        	}
			        	}
			        }
		        }
		        else{
		        	 referenciaModulo.actualizarBandaInformacion(constantes.TIPO_MENSAJE_ERROR, "El valor de N# Cisterna no puede ser 0. ");
		     		 referenciaModulo.obj.ocultaContenedorFormulario.hide();
		        	 retorno = false;
		        	 return retorno;
		        }
		    }
			return retorno;
		}
		catch(error){
		      console.log(error.message);
			
		}
	};
  
  moduloActual.inicializar();
});