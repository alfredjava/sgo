<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="sgo.entidad.Cliente"%>
<%@ page import="sgo.entidad.Operacion"%>
<%@ page import="sgo.entidad.Producto"%>
<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1>Cierre de Día - Consulta por Fecha de Planificación</h1>
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
              <a id="btnCerrar" class="btn btn-default disabled espaciado"><i class="fa fa-calendar-minus-o"></i>  Cerrar</a>
              <a id="btnCambiarEstado" class="btn btn-default disabled espaciado"><i class="fa fa-eye"></i>  Cambiar Estado</a>
            </div>
          </div>
          <div class="box-body">
            <table id="tablaPrincipal" class="table table-striped table-bordered responsive" width="100%">
              <thead>
                <tr>
                <th>N#</th>
                <th>ID</th>
                <th>F. Planificada</th>
                <th>Tot. Asignados</th>
                <th>Tot. Descargados</th>
                <th>U. Actualizaci&oacute;n</th>
                <th>Usuario</th>
                <th>Operacion</th>
                <th>Cliente</th>
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
                <th>Operacion</th>
                <th>Cliente</th>
                <th>Estado</th>
                </tr>
              </tfoot>
            </table>
            <div id="frmConfirmarModificarEstado" class="modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">PETROPERÚ S.A.</h4>
						</div>
						<div class="modal-body">
							<p>¿Desea Modificar el estado del registro?</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cerrar</button>
							<button id="btnConfirmarModificarEstado" type="button" class="btn btn-primary">Confirmar</button>
						</div>
					</div>
				</div>
			</div>
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
          <h3 id="cmpTituloFormulario" class="box-title">Cierre de D&iacute;a - Cambiar Estado</h3>
        </div>
        <div class="box-body">
        	<form id="frmPrincipal" class="form form-horizontal" role="form"  novalidate="novalidate">
          		<div class="col-xs-12 form-group">
					<div class="row">
						<div class="col-md-2">	<label>Cliente:</label>						</div>
						<div class="col-md-3">	<span id='clienteSeleccionado'>	</span> 	</div>
						<div class="col-md-2"></div>
						<div class="col-md-2">	<label>Operaci&oacute;n:</label>			</div>
						<div class="col-md-3">	<span id='operacionSeleccionada'></span> 	</div>
					</div>
				</div>
				
				<div class="col-xs-12 form-group">
					<div class="row">
						<div class="col-md-2">	<label>F. Planificaci&oacute;n:</label>						</div>
						<div class="col-md-3">	<span id='fechaPlanificadaSeleccionada'>	</span> 	</div>
						<div class="col-md-2"></div>
						<div class="col-md-2">	<label>Estado Actual:</label>			</div>
						<div class="col-md-3">	<span id='estadoSeleccionado'></span> 	</div>
					</div>
				</div>
	
				<div class="col-xs-12 form-group">
					<div class="col-md-2"><label>Nuevo Estado:</label>			</div> 
					<div class="col-md-3">
						<select id="cmpEstado" name="cmpEstado" Class="form-control">
							<option value="1">Planificado</option>
							<option value="2">Asignado</option>
							<option value="3">Descargando</option>
						</select>
					</div>
					<div class="col-md-7"></div>
				</div>
          </form>
        </div>
        <div class="box-footer">
        	<button id="btnGuardarCambioEstado" type="submit" class="btn btn-primary">Guardar</button>
            <button id="btnCancelarCambioEstado"  class="btn btn-danger">Cancelar</button>
        </div>
        
        <div id="frmValidarAutorizacion" class="modal" data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button  type="button" class="close" aria-label="Close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Ingreso de Autorizaci&oacute;n</h4>
						</div>
						
						<div class="modal-body">
						<div class="row" id="cntInformacion" style="display:none;">
							<div class="row">
								<div class="col-md-12">
									<div id="bandaInformacionVentana" class="callout callout-info"></div>
								</div>
							</div>
						</div>
							<div class="col-xs-12 form-group">
								<div class="row">
									<div class="col-md-2"> <label  class="control-label" for="cmpDescAutorizacion">Autorización:</label> </div> 
									<div class="col-md-10">
										<input name="cmpDescAutorizacion" id="cmpDescAutorizacion" type="text" class="form-control text-uppercase" maxlength="15" disabled placeholder="" />
									</div>
								</div>
							</div>
							
							<div class="col-xs-12 form-group">
								<div class="row">
									<div class="col-md-2"><label  class="control-label" for="cmpNombreAprobador">Aprobador:</label> </div>
									<div class="col-md-10">
										<select tipo-control="select2" id="cmpAprobador" name="cmpAprobador" class="form-control" style="width: 100%">
											<option value="" selected="selected">Seleccionar</option>
										</select>
									</div>
								</div>
							</div>
						
							<div class="col-xs-12 form-group">
								<div class="row">
									<div class="col-md-2"> 	<label>C&oacute;digo de Autorizacion:</label> 								</div>
									<div class="col-md-4">	
										<input id="cmpCodigoValidacion" name="cmpCodigoValidacion" type="password" class="form-control"/> 			
									</div>
									<div class="col-md-1">	</div>
									<div class="col-md-2">	<label>Vigencia hasta:</label> 						</div>
									<div class="col-md-3">
										<input id="cmpVigenciaHastaValidacion" name="cmpVigenciaHastaValidacion" type="text" class="form-control" disabled required data-inputmask="'alias': 'dd/mm/yyyy'" placeholder="" /> 
									</div>
								</div>
							</div>
							
							<div class="col-xs-12 form-group">
								<div class="row">
									<div class="col-md-12"> 	<label>Justificaci&oacute;n:</label> </div>
								</div>
							</div>
							<div class="col-xs-12 form-group">
								<div class="row">
									<textarea class="form-control text-uppercase" required id="cmpJustificacion" name="cmpJustificacion"  placeholder="Ingrese justificaci&oacute;n..." rows="3" ></textarea>
								</div>
						   	</div>
						</div>
						<div class="modal-footer">
				            <button id="btnGuardarValidarAutorizacion" class="btn btn-default pull-left" data-dismiss="modal" type="button">Guardar</button>
				            <button id="btnCancelarValidarAutorizacion"  class="btn btn-default  pull-rigth" type="button">Cancelar</button>
				        </div>
					</div>
				</div>
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