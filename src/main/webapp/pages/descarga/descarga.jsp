<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="sgo.entidad.Cliente"%>
<%@ page import="sgo.entidad.Operacion"%>
<%@ page import="sgo.entidad.Producto"%>
<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1 id="tituloPantalla"> Registro de Descarga<small> - Listado</small></h1>
  </section>
  <!-- Contenido principal -->
  <section class="content">
  <!-- El contenido debe incluirse aqui-->
    <div id="cntBanda" class="row">
      <div class="col-md-12">
        <div id="bandaInformacion" class="callout callout-info">Bienvenido !</div>
      </div>
    </div>
    <div class="row" id="cntTablaDiaOperativo">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
            <form id="frmBuscar" class="form" role="form" novalidate="novalidate">
              <div class="row">
                <div class="col-md-6">
                  <div class="col-md-4" style="padding-left: 4px;padding-right: 4px">
                    <label id="lblFiltroOperacion" class="etiqueta-titulo-horizontal">Cliente / Operación: </label>
                  </div>
                  <div class="col-md-8" style="padding-left: 4px;padding-right: 4px" >
                    <select id="filtroOperacion" name="filtroOperacion" class="form-control" style="width:100%;">
                    <%
                    ArrayList<?> listaOperaciones = (ArrayList<?>) request.getAttribute("operaciones"); 
                    String fechaActual = (String) request.getAttribute("fechaActual");                  
                    int numeroOperaciones = listaOperaciones.size();
                    int indiceOperaciones=0;
                    Operacion eOperacion=null;
                    Cliente eCliente=null;
                    String seleccionado="selected='selected'";
                    for(indiceOperaciones=0; indiceOperaciones < numeroOperaciones; indiceOperaciones++){ 
                    eOperacion = (Operacion)listaOperaciones.get(indiceOperaciones);
                    eCliente = eOperacion.getCliente();
                    %>
                    <option <%=seleccionado%> data-fecha-actual='<%=fechaActual%>' data-volumen-promedio-cisterna='<%=eOperacion.getVolumenPromedioCisterna()%>' data-nombre-operacion='<%=eOperacion.getNombre().trim()%>' data-nombre-cliente='<%=eCliente.getNombreCorto().trim()%>' value='<%=eOperacion.getId()%>'><%=  eCliente.getNombreCorto().trim() + " / "+ eOperacion.getNombre().trim() %></option>
                    <%
                    seleccionado="";
                    } %>
                    </select>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="col-md-4" style="padding-left: 4px;padding-right: 4px">
                    <label id="lblFiltroFechaPlanificada" class="etiqueta-titulo-horizontal">F. Planificada: </label>
                  </div>
                  <div class="col-md-8" style="padding-left: 4px;padding-right: 4px">
                    <input id="filtroFechaPlanificada" type="text" style="width:100%" class="form-control" data-fecha-actual="<%=fechaActual%>" />
                  </div>
                </div>
                <div class="col-md-2">
                  <a id="btnFiltrarDiaOperativo" class="btn btn-default col-md-12"><i class="fa fa-refresh"></i>  Filtrar</a>
                </div>
              </div>
            </form>
          </div>        
          <div class="box-header">
            <div>
              <a id="btnDetalleDiaOperativo" class="btn btn-default espaciado"><i class="fa fa-search"></i>  Detalle</a>
            </div>
          </div>
          <div class="box-body">
            <table id="tablaDiaOperativo" class="table table-striped table-bordered responsive" style="width:100%;">
              <thead>
                <tr>
                <th>N#</th>
                <th>ID</th>
                <th>F. Planificada</th>
                <th>Tot. Asignados</th>
                <th>Tot. Descargados</th>
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
                <th>Tot. Asignados</th>
                <th>Tot. Descargados</th>
                <th>U. Actualizaci&oacute;n</th>
                <th>Usuario</th>
                <th>Estado</th>
                </tr>
              </tfoot>
            </table>
          </div>
          <div class="overlay" id="ocultaContenedorTablaDiaOperativo">
            <i class="fa fa-refresh fa-spin"></i>
          </div>
        </div>
      </div>
    </div>
    
  <div class="row" id="cntDetalleDiaOperativo" style="display:none">
    <div class="col-md-12">
      <div class="box box-default">
        <div class="box-header">
          <form id="frmFiltrarxEstacion" name="frmFiltrarxEstacion" class="form" role="form"  novalidate="novalidate">
            <div class="row">
              <div class="col-md-5">
                  <div class="col-md-5" style="padding-left: 4px;padding-right: 4px">
                    <label id="lblOperacionDetalleDiaOperativo" class="etiqueta-titulo-horizontal">Operación / Cliente : </label>
                  </div>
                  <div class="col-md-7" style="padding-left: 4px;padding-right: 4px">
                    <input id="cmpOperacionDetalleDiaOperativo" name="cmpOperacionDetalleDiaOperativo" 
                    type="text" class="form-control" value="Cliente / Operación " readonly />
                  </div>
               </div>
              <div class="col-md-3">
                  <div class="col-md-6" style="padding-left: 4px;padding-right: 4px">
                    <label id="lblFechaPlanificacionDetalleDiaOperativo" class="etiqueta-titulo-horizontal">F.Planificación: </label>
                  </div>
                  <div class="col-md-6" style="padding-left: 4px;padding-right: 4px">
                    <input id="cmpFechaPlanificacionDetalleDiaOperativo" name="cmpFechaPlanificacionDetalleDiaOperativo" 
                    type="text" class="form-control" value="12/12/2015" readonly />
                  </div>
               </div>
              <div class="col-md-3">
                  <div class="col-md-4" style="padding-left: 4px;padding-right: 4px">
                    <label id="lblFiltroEstacionDetalleDiaOperativo" class="etiqueta-titulo-horizontal">Estación: </label>
                  </div>
                  <div class="col-md-8" style="padding-left: 4px;padding-right: 4px">
                    <select id="filtroEstacionDetalleDiaOperativo" name="filtroEstacionDetalleDiaOperativo" class="form-control" >
                      <option value="1">No seleccionado</option>
                    </select>
                  </div>
               </div>
              <div class="col-md-1">
                  <a id="btnFiltrarCargaTanque" class="btn btn-default espaciado col-md-12"><i class="fa fa-refresh"></i>  Filtrar</a>
               </div>
            </div>
          </form>
        </div>
        <div class="box-header">
          <div>
          <a id="btnAgregarCarga" class="btn btn-default espaciado"><i class="fa fa-plus"></i>  Agregar</a>
          <a id="btnModificarCarga" class="btn btn-default disabled espaciado"><i class="fa fa-edit"></i>  Modificar</a>
          </div>
        </div>
        <div class="box-body">
          <table id="tablaCargaTanque" class="table table-striped table-bordered responsive" style="width:100%;">
            <thead>
              <tr>
              <th>N#</th>
              <th>ID</th>
              <th>Tanque</th>
              <th>F.Inicial</th>
              <th>A.Inicial (mm)</th>
              <th>V.Inicial</th>
              <th>F.Inicial</th>
              <th>A.Final (mm)</th>
              <th>V.Final</th>
              <th>V.Recibido</th>
              </tr>
            </thead>
            <tfoot>
              <tr>
              <th>N#</th>
              <th>ID</th>
              <th>Tanque</th>
              <th>F.Inicial</th>
              <th>A.Inicial (mm)</th>
              <th>V.Inicial</th>
              <th>F.Final</th>
              <th>A.Final (mm)</th>
              <th>V.Final</th>
              <th>V.Recibido</th>
              </tr>
            </tfoot>
          </table>
        </div>
        <div class="overlay" id="ocultaContenedorTablaCargaTanque">
            <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
      <div class="box box-default">
        <div class="box-header">
        </div>
        <div class="box-header">
          <div>
          <a id="btnAgregarDescarga" class="btn btn-default disabled espaciado"><i class="fa fa-plus"></i>  Agregar</a>
          <a id="btnModificarDescarga" class="btn btn-default disabled espaciado"><i class="fa fa-edit"></i>  Modificar</a>
          <a id="btnAgregarEvento" class="btn btn-default disabled espaciado"><i class="fa fa-plus"></i>  Evento</a>
          <a id="btnVerDescarga" class="btn btn-default disabled espaciado"><i class="fa fa-search"></i>  Ver</a>
          </div>
        </div>
        <div class="box-body">
          <table id="tablaDescargaCisterna" class="table table-striped table-bordered responsive" style="width:100%;">
            <thead>
              <tr>
              <th>N#</th>
              <th>ID</th>
              <th>Cisterna</th>
              <th>Tracto</th>
              <th>N# Guia</th>
              <th>Compartimento</th>
              <th>Despachado (60F)</th>
              <th>Recibido (60F)</th>
              <th>Variacion (60F)</th>
              </tr>
            </thead>
            <tfoot>
              <tr>
              <th>N#</th>
              <th>ID</th>
              <th>Cisterna</th>
              <th>Tracto</th>
              <th>N# Guia</th>
              <th>Compartimento</th>
              <th>Despachado (60F)</th>
              <th>Recibido (60F)</th>
              <th>Variacion (60F)</th>
              </tr>
            </tfoot>
            <tbody>
              <tr>
                <td>1</td>
                <td>1</td>
                <td>LP546</td>
                <td>CFV675</td>
                <td>01-34233</td>
                <td>1</td>
                <td>8000</td>
                <td>7900</td>
                <td>100</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="overlay" id="ocultaContenedorTablaDescarga">
            <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>      
    </div>
  </div>
  <!-- Aqui empieza el formulario -->
  <div class="row" id="cntFormularioCargaTanque" style="display:none">
    <div class="col-md-12">
      <div class="box box-default">
        <div class="box-header">
			<form id="frmFiltrarxTanque" name="frmFiltrarxTanque" class="form"  novalidate="novalidate">
            <div class="row">
              <div class="col-md-5">
                  <div class="col-md-5" style="padding-left: 4px;padding-right: 4px">
                    <label id="lblOperacionCargaTanque" class="etiqueta-titulo-horizontal"> Cliente / Operación  : </label>
                  </div>
                  <div class="col-md-7" style="padding-left: 4px;padding-right: 4px">
                    <input id="cmpOperacionFormularioCargaTanque" name="cmpOperacionFormularioCargaTanque" 
                    type="text" class="form-control" value="Cliente / Operación " readonly />
                  </div>
               </div>
              <div class="col-md-3">
                  <div class="col-md-6" style="padding-left: 4px;padding-right: 4px">
                    <label id="lblEstacionCargaTanque" class="etiqueta-titulo-horizontal">Estación: </label>
                  </div>
                  <div class="col-md-6" style="padding-left: 4px;padding-right: 4px">
                    <input id="cmpEstacionFormularioCargaTanque" name="cmpEstacionFormularioCargaTanque" 
                    type="text" class="form-control" value="" readonly />
                  </div>
               </div>
              <div class="col-md-4">
                  <div class="col-md-4" style="padding-left: 4px;padding-right: 4px">
                    <label id="4" class="etiqueta-titulo-horizontal">Tanque: </label>
                  </div>
                  <div class="col-md-8" style="padding-left: 4px;padding-right: 4px">
                    <select id="cmpTanqueFormularioCargaTanque" name="cmpTanqueFormularioCargaTanque" class="form-control" >
                      <option value="1">No seleccionado</option>
                    </select>
                  </div>
               </div>
            </div>
          </form>
        </div>
        <div class="box-body">
          <form id="frmAgregarCargaTanque" class="form form-horizontal" novalidate="novalidate">
          <table class="table table-condensed">
            <thead>
              <tr>
              <th class="text-center">Medida</th>
              <th class="text-center">Fecha/Hora</th>
              <th class="text-center">Altura</th>
              <th class="text-center">T.Centro</th>
              <th class="text-center">T. Probeta</th>
              <th class="text-center">API T. Obs.</th>
              <th class="text-center">Factor</th>
              <th class="text-center">Vol. T. Obs.</th>
              <th class="text-center">Vol. 60F</th>
              </tr>
            </thead>
            <tbody>
              <tr>
              <td><input id="cmpMedidaInicial" name="cmpMedidaInicial" type="text" class="form-control" value="Inicial" readonly="readonly" /></td>
              <td><input id="cmpFechaHoraInicial" name="cmpFechaHoraInicial" type="text" class="form-control" value="12/12/2015 20:00" /></td>
              <td><input id="cmpAlturaInicial" name="cmpAlturaInicial" type="text" class="form-control text-right" value="1200" /></td>
              <td><input id="cmpTemperaturaInicialCentro" name="cmpTemperaturaInicialCentro" type="text" class="form-control text-right" value="34.9" /></td>
              <td><input id="cmpTemperaturaInicialProbeta" name="cmpTemperaturaInicialProbeta" type="text" class="form-control text-right" value="38.7" /></td>
              <td><input id="cmpAPIObservadoInicial" name="cmpAPIObservadoInicial" type="text" class="form-control text-right" value="38.7" /></td>
              <td><input id="cmpFactorInicial" name="cmpFactorInicial"  type="text" class="form-control text-right" value="0.456456" /></td>
              <td><input id="cmpVolumenInicialObservado" name="cmpVolumenInicialObservado" type="text" class="form-control text-right" value="8500" /></td>
              <td><input id="cmpVolumenInicialCorregido" name="cmpVolumenInicialCorregido" type="text" class="form-control text-right" value="8600" /></td>
              </tr>
              <tr>
              <td><input id="cmpMedidaFinal" name="cmpMedidaFinal" type="text" class="form-control" value="Final" readonly="readonly" /></td>
              <td><input id="cmpFechaHoraFinal" name="cmpFechaHoraFinal" type="text" class="form-control" value="12/12/2015 20:00" /></td>
              <td><input id="cmpAlturaFinal" name="cmpAlturaFinal" type="text" class="form-control text-right" value="1200" /></td>
              <td><input id="cmpTemperaturaFinalCentro" name="cmpTemperaturaFinalCentro" type="text" class="form-control text-right" value="34.9" /></td>
              <td><input id="cmpTemperaturaFinalProbeta" name="cmpTemperaturaFinalProbeta" type="text" class="form-control text-right" value="38.7" /></td>
              <td><input id="cmpAPIObservadoFinal" name="cmpAPIObservadoFinal" type="text" class="form-control text-right" value="38.7" /></td>
              <td><input id="cmpFactorFinal" name="cmpFactorFinal" type="text" class="form-control text-right" value="0.456456" /></td>
              <td><input id="cmpVolumenFinalObservado" name="cmpVolumenFinalObservado" type="text" class="form-control text-right" value="8500" /></td>
              <td><input id="cmpVolumenFinalCorregido" name="cmpVolumenFinalCorregido" type="text" class="form-control text-right" value="8600" /></td>
              </tr>
            </tbody>
          </table>
          </form>
        </div>
        <div class="box-footer">
          <a id="btnGuardarCarga" class="btn btn-primary"><i class="fa fa-save"></i>  Guardar</a>
          <a id="btnCancelarGuardarCarga" class="btn btn-danger"><i class="fa fa-close"></i>  Cancelar</a>
        </div>
        <div class="overlay" id="ocultaContenedorFormularioCargaTanque" style="display:none">
          <i class="fa fa-refresh fa-spin"></i>
        </div>
      </div>
    </div>
  </div>
  </section>
</div>