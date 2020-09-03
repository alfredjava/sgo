var utilitario={	
	//str: Cadena con formato constantes.FORMATO_FECHA
	formatearStringToDate: function(str){
		var parametros = str.split('/');
		var fecha =  new Date(parametros[2],'' + (parseInt(parametros[1]) - 1),parametros[0],0,0,0,0);
	    return fecha;
	},
	//str: fecha en formato dd/mm/yyyy hh:mm:ss
	formatearStringToDateHour: function(str){
		var parametros = str.split(' ');
		var parametrosDia = parametros[0].split('/');
		var parametrosHora = parametros[1].split(':');
		var fecha =  new Date(parametrosDia[2],'' + (parseInt(parametrosDia[1]) - 1), parametrosDia[0], parametrosHora[0], parametrosHora[1], parametrosHora[2], 0);
	    return fecha;
	},
	formatearTimestampToString: function(fecha) {
		var date = new Date(fecha);
		var yy 	= date.getFullYear().toString();
	    var mm 	= (date.getMonth()+1).toString(); // getMonth() is zero-based
	    var dd  = date.getDate().toString();
		var h   = "0" + date.getHours();
		var m  	= "0" + date.getMinutes();
		var s 	= "0" + date.getSeconds();  
	    return  (dd[1]?dd:"0"+dd[0])+"/"+(mm[1]?mm:"0"+mm[0]) +"/"+yy +" " + h.substr(-2) + ":" + m.substr(-2) + ":" + s.substr(-2) ;
	},
  //formato dd/mm/yyyy
  formatearFecha2Iso:function(fecha){    
    var partes = fecha.trim().split("/");
    return partes[2] + "-" + partes[1] + "-" + partes[0];    
  },
	formatearFecha2Cadena: function(fecha) {
		   var yyyy = fecha.getFullYear().toString();
		   var mm = (fecha.getMonth()+1).toString(); // getMonth() is zero-based
		   var dd  = fecha.getDate().toString();
		   //return yyyy +(mm[1]?mm:"0"+mm[0]) + (dd[1]?dd:"0"+dd[0]); 
		   return  (dd[1]?dd:"0"+dd[0])+"/"+(mm[1]?mm:"0"+mm[0]) +"/"+yyyy ;
	},
	//str: fecha en formato yyyy-mm-dd
	//retorna fecha en formato dd/mm/yyyy
	formatearFecha: function(str){
		var parametros = str.split('-');
		var fecha =  new String(parametros[2]+ '/' + parametros[1] + '/' + parametros[0]);
	    return fecha;
	},
	formatearEstado: function(datos, tipo, fila, meta ){
		var valorFormateado="";
    console.log(datos);
		valorFormateado = constantes.ESTADO[datos];
	    return valorFormateado;
	},
	formatearTipoPropietario: function(datos, tipo, fila, meta ){
		var valorFormateado=constantes.TIPO_PROPIETARIO[datos];
	    return valorFormateado;
	},
	formatearTipoEstacion: function(datos, tipo, fila, meta ){
		var valorFormateado=constantes.TIPO_ESTACION[datos];
	    return valorFormateado;
	},
	formatearTipoContometro: function(datos, tipo, fila, meta ){
		var valorFormateado=constantes.TIPO_CONTOMETRO[datos];
	    return valorFormateado;
	},
	formatearEstadoDiaOperativo: function(datos, tipo, fila, meta ){
		var valorFormateado=constantes.ESTADOS_DIA_OPERATIVO[datos];
	    return valorFormateado;
	},
	formatearOrigenTransporte: function(datos, tipo, fila, meta ){
		var valorFormateado=constantes.ORIGEN_TRANSPORTE[datos];
	    return valorFormateado;
	},
	//Se ingresa el id del estado y retorna el texto del estado.
	formatearValorEstado: function(idEstado){
		var valorFormateado="";
		valorFormateado = constantes.ESTADO[idEstado];
	    return valorFormateado;
	},
	formatearTipoUsuario: function(datos, tipo, fila, meta ){
		var valorFormateado=constantes.TIPO_USUARIO[datos];
	    return valorFormateado;
	},
	formatearTipoEvento: function(datos, tipo, fila, meta ){
		var valorFormateado=constantes.TIPO_EVENTO[datos];
	    return valorFormateado;
	},
	retornarRangoSemana : function(fechaActual) {
    //fechaActual formato yyyy-mm-dd
    var fechaInicial = new Date(fechaActual);
    var fechaFinal = new Date(fechaActual);
    var numeroDiasRestar = 0,numeroDiasAgregar = 0;
    var diaSemana = fechaInicial.getDay();  
    var DIA_SEMANA={DOMINGO:0,LUNES:1,MARTES:2,MIERCOLES:3,JUEVES:4,VIERNES:5,SABADO:6}
    var rangoSemana={};
    if(diaSemana == DIA_SEMANA.DOMINGO){
      numeroDiasRestar = 6;
      diaSemanaASumar = 0;
    } else if (diaSemana == DIA_SEMANA.LUNES){
      numeroDiasRestar = 0;
      numeroDiasAgregar = 6;
    } else {
      numeroDiasRestar = diaSemana - 1;
      numeroDiasAgregar = 7 - diaSemana;
    }
    fechaInicial.setDate(fechaInicial.getDate() - parseInt(numeroDiasRestar));
    fechaFinal.setDate(fechaFinal.getDate() + parseInt(numeroDiasAgregar));	    
    rangoSemana = {"fechaInicial":fechaInicial,"fechaFinal":fechaFinal};
    return rangoSemana;
	} 
};

(function() {
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }
}());