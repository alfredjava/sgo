$(document).ready(function(){
  var moduloActual = new moduloBase();
  
  moduloActual.urlBase='bitacora';
  moduloActual.URL_LISTAR = moduloActual.urlBase + '/listar';
  moduloActual.URL_RECUPERAR = moduloActual.urlBase + '/recuperar';
  
  moduloActual.ordenGrilla=[[ 2, 'asc' ]];
  moduloActual.columnasGrilla.push({ "data": 'id'});   //Target1
  moduloActual.columnasGrilla.push({ "data": 'tabla'}); //Target2
  moduloActual.columnasGrilla.push({ "data": 'usuario'});//Target3
  moduloActual.columnasGrilla.push({ "data": 'fechaRealizacion'});//Target4
  moduloActual.columnasGrilla.push({ "data": 'accion'});//Target5
    
  moduloActual.definicionColumnas.push({
    "targets": 1,
    "searchable": true,
    "orderable": true,
    "visible": false
  });
  moduloActual.definicionColumnas.push({
    "targets": 2,
    "searchable": true,
    "orderable": true,
    "visible": true
  });
  moduloActual.definicionColumnas.push({
    "targets": 3,
    "searchable": true,
    "orderable": true,
    "visible": true
  });
  moduloActual.definicionColumnas.push({
    "targets": 4,
    "searchable": true,
    "orderable": true,
    "visible": true
  });
  moduloActual.definicionColumnas.push({
    "targets": 5,
    "searchable": true,
    "orderable": true,
    "visible": true
  });
	  
  moduloActual.inicializarCampos= function(){
    //Campos de vista
    this.obj.vistaId=$("#vistaId");
    this.obj.vistaUsuario=$("#vistaUsuario");
    this.obj.vistaAccion=$("#vistaAccion");
    this.obj.vistaTabla=$("#vistaTabla");
    this.obj.vistaContenido=$("#vistaContenido");
    this.obj.vistaRealizadoEl=$("#vistaRealizadoEl");
    this.obj.vistaRealizadoPor=$("#vistaRealizadoPor");
    this.obj.vistaIdentificador=$("#vistaIdentificador");
    this.obj.vistaIpUsuario=$("#vistaIpUsuario");
  };
  
  moduloActual.llenarDetalles = function(registro){
	this.obj.vistaId.text(registro.id);
    this.obj.vistaUsuario.text(registro.usuario);
    this.obj.vistaAccion.text(registro.accion);
    this.obj.vistaTabla.text(registro.tabla);
    this.obj.vistaContenido.text(registro.contenido);
    this.obj.vistaRealizadoEl.text(registro.fechaRealizacion);
    this.obj.vistaRealizadoPor.text(registro.realizadoPor);
    this.obj.vistaIdentificador.text(registro.identificador);
    this.obj.vistaIpUsuario.text(registro.ipUsuario);
  };
  
  moduloActual.inicializar();
});
