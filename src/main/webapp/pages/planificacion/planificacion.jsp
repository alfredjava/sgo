<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="sgo.entidad.Cliente"%>
<%@ page import="sgo.entidad.Operacion"%>
<%@ page import="sgo.entidad.Producto"%>
<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1>Registro de Planificaci&oacute;n<small> - Listado</small></h1>
  </section>
  <!-- Contenido principal -->
  <section class="content">
  <!-- El contenido debe incluirse aqui-->
    <div class="row">
      <div class="col-md-12">
        <div id="bandaInformacion" class="callout callout-info">Bienvenido !</div>
      </div>
    </div>
    <div class="row" id="cntTabla">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
            <form id="frmBuscar" class="form" role="form"  novalidate="novalidate">
              <div class="row">
                <div class="col-md-6">
                  <div class="col-md-4" style="padding-left: 4px;padding-right: 4px">
                    <label class="etiqueta-titulo-horizontal">Operación / Cliente: </label>
                  </div>
                  <div class="col-md-8" style="padding-left: 4px;padding-right: 4px" >
                    <select id="filtroOperacion" name="filtroOperacion" class="form-control" style="width:100%;">
                    <%
                    ArrayList<Operacion> listaOperaciones = (ArrayList<Operacion>) request.getAttribute("operaciones"); 
                    String fechaActual = (String) request.getAttribute("fechaActual");                  
                    int numeroOperaciones = listaOperaciones.size();
                    int indiceOperaciones=0;
                    Operacion eOperacion=null;
                    Cliente eCliente=null;
                    String seleccionado="selected='selected'";
                    for(indiceOperaciones=0; indiceOperaciones < numeroOperaciones; indiceOperaciones++){ 
                    eOperacion = listaOperaciones.get(indiceOperaciones);
                    eCliente = eOperacion.getCliente();
                    %>
                    <option <%=seleccionado%> data-fecha-actual='<%=fechaActual%>' data-volumen-promedio-cisterna='<%=eOperacion.getVolumenPromedioCisterna()%>' data-nombre-operacion='<%=eOperacion.getNombre().trim()%>' data-nombre-cliente='<%=eCliente.getNombreCorto().trim()%>' value='<%=eOperacion.getId()%>'><%=eOperacion.getNombre().trim() + " / " + eCliente.getNombreCorto().trim() %></option>
                    <%
                    seleccionado="";
                    } %>
                    </select>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="col-md-4" style="padding-left: 4px;padding-right: 4px">
                    <label class="etiqueta-titulo-horizontal">F. Planificada: </label>
                  </div>
                  <div class="col-md-8" style="padding-left: 4px;padding-right: 4px">
                    <input id="filtroFechaPlanificada" type="text" style="width:100%" class="form-control" data-fecha-actual="<%=fechaActual%>" />
                  </div>
                </div>
                <div class="col-md-2">
                  <a id="btnFiltrar" class="btn btn-default col-md-12"><i class="fa fa-refresh"></i>  Filtrar</a>
                </div>
              </div>
            </form>
          </div>        
          <div class="box-header">
            <div>
              <a id="btnAgregar" class="btn btn-default espaciado"><i class="fa fa-plus"></i>  Agregar</a>
              <a id="btnModificar" class="btn btn-default disabled espaciado"><i class="fa fa-edit"></i>  Modificar</a>
              <a id="btnVer" class="btn btn-default disabled espaciado"><i class="fa fa-search"></i>  Ver</a>
            </div>
          </div>
          <div class="box-body">
            <table id="tablaPrincipal" class="table table-striped table-bordered responsive" width="100%">
              <thead>
                <tr>
                <th>N#</th>
                <th>ID</th>
                <th>F. Planificada</th>
                <th>Tot. Cisternas</th>
                <th>Producto</th>
                <th>U. Actualizaci&oacute;n</th>
                <th>Usuario</th>
                <th>Estado</th>
                </tr>
              </thead>
              <tfoot>
                <tr>
                <th>N#</th>
                <th>ID</th>
                <th>F. Planificada</th>
                <th>Tot. Cisternas</th>
                <th>Producto</th>
                <th>U. Actualizaci&oacute;n</th>
                <th>Usuario</th>
                <th>Estado</th>
                </tr>
              </tfoot>
            </table>
          </div>
          <div class="overlay" id="ocultaContenedorTabla">
            <i class="fa fa-refresh fa-spin"></i>
          </div>
        </div>
      </div>
    </div>
  <!-- Aqui empieza el formulario -->
  <div class="row" id="cntFormulario" style="display: none;">
    <div class="col-md-12">
      <div class="box box-default">
        <div class="box-header">
          <h3 id="cmpTituloFormulario" class="box-title">Agregar Planificación</h3>
        </div>
        <div class="box-body">
          <form id="frmPrincipal" class="form form-horizontal" role="form"  novalidate="novalidate">
            <div class="form-group">
              <div class="col-md-4">
                <div class="col-md-4">
                  <label class="etiqueta-titulo-horizontal">Cliente: </label>
                </div>
                <div class="col-md-8">
                  <input id="cmpCliente" type="text" class="form-control" value="" readonly />
                </div>
              </div>
              <div class="col-md-4">
                <div class="col-md-4">
                  <label class="etiqueta-titulo-horizontal">Operación: </label>
                </div>
                <div class="col-md-8">
                  <input id="cmpOperacion" type="text" class="form-control" value="" readonly />
                </div>
              </div>
              <div class="col-md-4">
                <div class="col-md-7">
                  <label class="etiqueta-titulo-horizontal">F. Planificada: </label>
                </div>
                <div class="col-md-5">
                  <strong><input id="cmpFechaPlanificada" type="text" class="form-control alert-danger text-center" value="" readonly /></strong>
                </div>
              </div>
            </div>
            <div class="form-group">
              <div class="">
                <div class="col-md-5">
                  <label>Producto: </label>
                </div>
                <div class="col-md-2">
                  <label>Vol. Propuesto: </label>
                </div>
                <div class="col-md-2">
                  <label>Vol. Solicitado: </label>
                </div>
                <div class="col-md-2">
                  <label>N# Cisternas: </label>
                </div>
                <div class="col-md-1">
                  <label> </label>
                </div>
              </div>
              <div id="GrupoPlanificacion">
                <div id="GrupoPlanificacion_template">
                   <div class="col-md-5" style="margin-bottom:2px;">
                    <select tipo-control="select2" elemento-grupo="producto" id="GrupoPlanificacion_#index#_Producto" style="width: 100%" name="planificacion[detalle][#index#][producto]" class="form-control">
                    <% ArrayList<Producto> listaProductos = (ArrayList<Producto>) request.getAttribute("productos"); 
                    Producto eProducto=null;
                    for(int i=0; i < listaProductos.size(); i++){ 
                    eProducto = listaProductos.get(i);
                    %>
                    <option value='<%=eProducto.getId()%>'><%=eProducto.getNombre() %></option>
                    <%} %>
                    </select>
                  </div>
                  <div class="col-md-2" style="margin-bottom:2px;">
                    <input elemento-grupo="volumenPropuesto" id="GrupoPlanificacion_#index#_VolumenPropuesto" name="planificacion[detalle][#index#][volumen_propuesto]" type="text" class="form-control text-right" disabled value="0" />
                  </div>
                  <div class="col-md-2" style="margin-bottom:2px;">
                    <input elemento-grupo="volumenSolicitado" id="GrupoPlanificacion_#index#_VolumenSolicitado" name="planificacion[detalle][#index#][volumen_solicitado]" type="text" class="form-control text-right" disabled value="0" />
                  </div>
                  <div class="col-md-2" style="margin-bottom:2px;">
                    <input elemento-grupo="numeroCisternas" id="GrupoPlanificacion_#index#_NumeroCisternas" name="planificacion[detalle][#index#][numero_cisternas]" type="text" class="form-control text-right" value="0" />
                  </div>
                  <div class="col-md-1" style="margin-bottom:2px;">
                    <a id="GrupoPlanificacion_remove_current" class="btn btn-default" style="display:inline-block"><i class="fa fa-remove"></i></a>
                  </div>
                </div>
                <div id="GrupoPlanificacion_noforms_template">No hay registros disponibles</div>
              </div>
            </div>            
          </form>
        </div>
        <div class="box-footer">
          <a id="btnGuardar" class="btn btn-primary"><i class="fa fa-save"></i>  Guardar</a>
          <a id="btnAgregarProducto" class="btn bg-purple"><i class="fa fa-plus-circle"></i>  Agregar Producto</a>
          <a id="btnCancelarGuardar" class="btn btn-danger"><i class="fa fa-close"></i>  Cancelar</a>
        </div>
        <div class="overlay" id="ocultaContenedorFormulario">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
    </div>
  </div>
  <div class="row" id="cntVistaRegistro" style="display: none;">
    <div class="col-md-12">
      <div class="box box-default">
        <div class="box-header with-border">
          <h3 class="box-title">Detalle del registro - Ver</h3>
        </div>
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <div class="row" style="padding:4px;">
                <div class="col-md-1"><label>Cliente:</label></div>
                <div class="col-md-3"><span id='vistaCliente'></span></div>
                <div class="col-md-1"><label>Operaci&oacute;n:</label></div>
                <div class="col-md-3"><span id='vistaOperacion'></span></div>
                <div class="col-md-2"><label class="control-label"> F. Planificaci&oacute;n </label> 	</div>
                <div class="col-md-2"><FONT COLOR=blue><B><span id='vistaFechaPlanificacion'></span></B></FONT></div>
              </div>
            </div>
          </div>
        </div>
    <div class="col-xs-12">
      <div class="box">
        <div class="box-header">
        <table id="listado_planificaciones" class="table table-bordered table-striped"></table>
        </div>
      </div>
    </div>
    <div class="col-xs-12">
      <div class="box">
        <div class="box-header">
        <div class="row" style="padding:2px;">
        <div class="col-md-2"><label>Actualizado el:</label></div>
        <div class="col-md-2"><span id='vistaActualizadoEl'></span>   </div>
        <div class="col-md-2"><label>Actualizado por:</label></div>
        <div class="col-md-2"><span id='vistaActualizadoPor'></span></div>
        <div class="col-md-2"><label class="control-label">IP (Actualizacion):</label></div>
        <div class="col-md-2"><span id='vistaIpActualizacion'></span></div>
        </div>
        </div>
      </div>
    </div>
    <div class="box-footer">
      <button id="btnCerrarVista" class="btn btn-danger">Cerrar</button>
    </div>
    <div class="overlay" id="ocultaContenedorVista">
      <i class="fa fa-refresh fa-spin"></i>
    </div>
    </div>
    </div>
  </div>
  </section>
</div>