 	

$(document).ready(function(){
  var moduloActual = new moduloBase();
  
  moduloActual.urlBase='estacion';
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  moduloActual.URL_ELIMINAR = moduloActual.urlBase + '/eliminar';
  moduloActual.URL_GUARDAR = moduloActual.urlBase + '/crear';
  moduloActual.URL_ACTUALIZAR = moduloActual.urlBase + '/actualizar';
  moduloActual.URL_ACTUALIZAR_ESTADO = moduloActual.urlBase + '/actualizarEstado';
  
  moduloActual.ordenGrilla=[[ 2, 'asc' ]];

  moduloActual.columnasGrilla.push({ "data": 'id'});
  moduloActual.columnasGrilla.push({ "data": 'nombre'});
  moduloActual.columnasGrilla.push({ "data": 'tipo'});
  moduloActual.columnasGrilla.push({ "data": 'operacion.nombre'});
  moduloActual.columnasGrilla.push({ "data": 'estado'});
  
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
    "orderable": true,
    "visible":true,
    "render":  utilitario.formatearTipoEstacion
  });
  moduloActual.definicionColumnas.push({
	"targets": 4,
	"searchable": true,
	"orderable": true,
	"visible":true
  });
  moduloActual.definicionColumnas.push({
	"targets": 5,
	"orderable": true,
	"visible":true,
	"render":  utilitario.formatearEstado
  });
  
  moduloActual.reglasValidacionFormulario={
	cmpNombre: {
		required: true,
		maxlength: 20
	},
    cmpIdOperacion: {
		required: true,
	},
	cmpTipo: {
		required: true,
	}
  };
  
  moduloActual.mensajesValidacionFormulario={
	cmpNombre: {
		required: "El campo es obligatorio",
		maxlength: "El campo debe contener 20 caracteres como m&aacute;ximo."
	},
	cmpIdOperacion: {
		required: "El campo es obligatorio",
	},
	cmpTipo: {
		required: "El campo es obligatorio",
	}
  };
  moduloActual.inicializarCampos= function(){
    //Campos de formulario
    this.obj.cmpNombre=$("#cmpNombre");
    this.obj.cmpTipo=$("#cmpTipo");
    this.obj.cmpEstado=$("#cmpEstado");
    this.obj.cmpIdOperacion=$("#cmpIdOperacion");    
    this.obj.cmpIdOperacion.tipoControl="select2";
    this.obj.cmpFiltroOperacion=$("#cmpFiltroOperacion");
    this.obj.cmpFiltroOperacion.select2();
    this.obj.cmpSelect2Operacion=$("#cmpIdOperacion").select2({
  	  ajax: {
  		    url: "./operacion/listar",
  		    dataType: 'json',
  		    delay: 250,
  		    data: function (parametros) {
  		      return {
  		    	valorBuscado: parametros.term,
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
		        return "<div class='select2-user-result'>" + registro.nombre + "</div>";
		    },
		    templateSelection: function (registro) {
		    	console.log("templateSelection");
		        return registro.nombre || registro.text;
		    },
  		minimumInputLength: 3
    });
  
    this.obj.cmpSincronizadoEl=$("#cmpSincronizadoEl");
    this.obj.cmpFechaReferencia=$("#cmpFechaReferencia");
    this.obj.cmpCodigoReferencia=$("#cmpCodigoReferencia");
    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaNombre=$("#vistaNombre");
    this.obj.vistaTipo=$("#vistaTipo");
    this.obj.vistaEstado=$("#vistaEstado");
    this.obj.vistaIdOperacion=$("#vistaIdOperacion");
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
  
  moduloActual.llamadaAjax=function(d){
		console.log("llamadaAjax2");
		var referenciaModulo =this;
	    var indiceOrdenamiento = d.order[0].column;
	    d.registrosxPagina =  d.length; 
	    d.inicioPagina = d.start; 
	    d.campoOrdenamiento= d.columns[indiceOrdenamiento].data;
	    d.sentidoOrdenamiento=d.order[0].dir;
	    d.valorBuscado=d.search.value;
	    d.txtFiltro = referenciaModulo.obj.txtFiltro.val();
	    d.filtroEstado= referenciaModulo.obj.cmpFiltroEstado.val();
	    d.filtroOperacion= referenciaModulo.obj.cmpFiltroOperacion.val();
	    console.log(d);
	};

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
    this.obj.cmpNombre.val(registro.nombre);
    this.obj.cmpTipo.val(registro.tipo);
    this.obj.cmpEstado.val(registro.estado);
    this.obj.cmpIdOperacion.val(registro.idOperacion);
    var elemento=constantes.PLANTILLA_OPCION_SELECTBOX;
    elemento = elemento.replace(constantes.ID_OPCION_CONTENEDOR,registro.operacion.id);
    elemento = elemento.replace(constantes.VALOR_OPCION_CONTENEDOR,registro.operacion.nombre);
    this.obj.cmpIdOperacion.empty().append(elemento).val(registro.operacion.id).trigger('change');
  };

  moduloActual.llenarDetalles = function(registro){
    this.idRegistro= registro.id;
    this.obj.vistaId.text(registro.id);
    this.obj.vistaNombre.text(registro.nombre);
    this.obj.vistaTipo.text(utilitario.formatearTipoEstacion(registro.tipo));
    this.obj.vistaEstado.text(utilitario.formatearEstado(registro.estado));
    this.obj.vistaIdOperacion.text(registro.operacion.nombre);
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
    eRegistro.tipo = parseInt(referenciaModulo.obj.cmpTipo.val());
    eRegistro.idOperacion = parseInt(referenciaModulo.obj.cmpIdOperacion.val());
    eRegistro.estado = parseInt(referenciaModulo.obj.cmpEstado.val());

    }  catch(error){
      console.log(error.message);
    }
    return eRegistro;
  };
  
  moduloActual.inicializar();
});
