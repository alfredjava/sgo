<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="sgo.entidad.Cliente"%>
<%@ page import="sgo.entidad.Operacion"%>
<%@ page import="sgo.entidad.Producto"%>
<%@ page import="sgo.entidad.Transportista"%>

<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1>Registro de Transporte<small> - Listado</small></h1>
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
            <a id="btnDetalle" class="btn btn-default disabled espaciado"><i class="fa fa-search"></i>  Detalle</a>
          </div>
        </div>
          <div class="box-body">
            <table id="tablaPrincipal" class="table table-striped table-bordered responsive" width="100%">
              <thead>
                <tr>
 					<th>#</th>
 					<th>ID</th>
                 	<th>F. Planificaci&oacute;n</th>
                 	<th>Tot. Cis. Planificadas</th>
                 	<th>Tot Cis. Asignadas</th>
                 	<th>U.  Actualizaci&oacute;n</th>
                 	<th>Usuario</th>
					<th>Estado</th>
					<th>operacion</th>
					<th>cliente</th>
					<th>idCliente</th>
                </tr>
              </thead>
              <tfoot>
                <tr>
 					<th>#</th>
 					<th>IDPlanificacion</th>
                 	<th>F. Planificaci&oacute;n</th>
                 	<th>Tot. Cis. Planificadas</th>
                 	<th>Tot Cis. Asignadas</th>
                 	<th>U.  Actualizaci&oacute;n</th>
                 	<th>Usuario</th>
					<th>Estado</th>
					<th>operacion</th>
					<th>cliente</th>
					<th>idCliente</th>
                </tr>
              </tfoot>
            </table>
          </div>
        </div>
      </div>
    </div>

	<div class="row" id="cntDetalleTransporte" style="display: none;">
		<div class="col-md-12">
			<div class="box box-default">
				<div class="box-header with-border">
					<h3  id="cmpTituloFormulario" class="box-title"></h3>
				</div>
				<div class="box-header with-border">
					<div class="col-xs-12">
						<div class="row" style="padding: 4px;">
							<div class="col-md-1">	<label>Cliente:</label>						</div>
							<div class="col-md-3">	<span id='detalleCliente'>	</span> 		</div>
							<div class="col-md-1">	<label>Operaci&oacute;n:</label>			</div>
							<div class="col-md-3">	<span id='detalleOperacion'></span> 		</div>
							<div class="col-md-2">	<label> F. Planificaci&oacute;n:</label>	</div>
							<div class="col-md-2"><FONT COLOR=blue><B>	<span id='detalleFechaPlanificacion'></span></B></FONT></div>
						</div>
						<div  class="col-md-12">
							<table id="terceraTabla" class="table table-striped table-bordered responsive" width="100%">
			       				<thead>
			          					<tr>
										<th>#</th>
										<th>Producto</th>
										<th>Vol. Solicitado </th>
								    	<th>Cist. Solicitados</th>
								    	<th>Vol. Asignados</th>
								    	<th>Cist. Asignados</th>
			          					</tr>
			        				</thead>
       						</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div class="box box-default">
	   			<div class="box-header">
          			<div>
           				<a id="btnAgregarTransporte" class="btn btn-default espaciado"><i class="fa fa-plus"></i>  Agregar</a>
           				<a id="btnModificarTransporte" class="btn btn-default disabled espaciado"><i class="fa fa-edit"></i>  Modificar</a>
			            <a id="btnImportar" class="btn btn-default disabled espaciado"><i class="fa fa-download"></i>  Importar</a>
			            <a id="btnVer" class="btn btn-default disabled espaciado"><i class="fa fa-search"></i>  Ver</a>
			            <a id="btnEvento" class="btn btn-default disabled espaciado"><i class="fa fa-eye"></i> Evento</a>
						<a id="btnPesaje" class="btn btn-default disabled espaciado"><i class="fa fa-truck"></i> Pesaje</a>
          			</div>
        		</div>
          		<div class="box-body">
	   				<!-- <table id="listado_transportes" class="table table-bordered table-striped"></table> -->
	   				
	   				<table id="tablaSecundaria" class="table table-striped table-bordered responsive display" width="100%">
        				<thead>
           					<tr>
								<th>#</th>
								<th>ID</th>
								<th>G/R</th>
						    	<th>F. Emisi&oacute;n</th>
						    	<th>Cisterna / Tracto</th>
						    	<th>SCOP</th>
						    	<th>Vol. [T. Obs.]</th>
						    	<th>Vol. [60F]</th>
						    	<th>Origen</th>
								<th>Estado</th>
           					</tr>
         				</thead>
       				</table>
	   			</div>
	   		</div>
		</div>
		<div class="box-footer" align="right">
			<button id="btnCerrarDetalleTransporte" class="btn btn-danger">Cerrar</button>
		</div>
	</div>

	
	<div class="row" id="cntFormulario" style="display: none;">
		<div class="col-md-12">
			<div class="box box-default">
				<div class="box-header with-border">
					<h3 id="cmpTituloFormulario" class="box-title"></h3>
				</div>
				<div class="box-header">
					<form id="frmPrincipal" class="form form-horizontal" role="form" novalidate="novalidate">

						<div class="col-xs-12 form-group">
							<div class="row">
								<div class="col-md-1"> 	<label>Cliente:</label> 								</div>
								<div class="col-md-3">	<span id='cmpFormularioCliente'></span>
														<span class="hide" id='cmpFormularioIdDOperativo' />	</div>
								<div class="col-md-1">	<label>Operaci&oacute;n:</label>						</div>
								<div class="col-md-3">	<span id='cmpFormularioOperacion'></span>				</div>
								<div class="col-md-2">	<label> F. Planificaci&oacute;n:</label>				</div>
								<div class="col-md-2">	<span id='cmpFormularioFechaPlanificacion'></span>		</div>
							</div>
						</div>

						<div class="col-xs-12 form-group">
							<div class="row">
								<div class="col-md-1">
									<label> G/R:</label>
								</div>
								<div class="col-md-2">
									<input class="hide" name="formularioIdTransporte" id='formularioIdTransporte' />
									<input name="cmpNumeroGuiaRemision" id="cmpNumeroGuiaRemision" maxlength="15" type="text" class="form-control" required placeholder="" />
								</div>
								<div class="col-md-1">
									<label> O/E:</label>
								</div>
								<div class="col-md-2">
									<input name="cmpNumeroOrdenCompra" id="cmpNumeroOrdenCompra" maxlength="15" type="text" class="form-control" required placeholder="" />
								</div>
								<div class="col-md-1">
									<label> Factura:</label>
								</div>
								<div class="col-md-2">
									<input name="cmpNumeroFactura" id="cmpNumeroFactura" maxlength="15" type="text" class="form-control" placeholder="" />
								</div>
								<div class="col-md-1">
									<label> SCOP:</label>
								</div>
								<div class="col-md-2">
									<input name="cmpCodigoScop" id="cmpCodigoScop" maxlength="11" type="text" class="form-control" required placeholder="" />
								</div>
							</div>
						</div>

						<div class="col-xs-12 form-group">
							<div class="row">
								<div class="col-md-1">
									<label> Planta:</label>
								</div>
								<div class="col-md-3">
									<select tipo-control="select2" id="cmpPlantaDespacho" name="cmpPlantaDespacho" class="form-control text-uppercase" style="width: 100%">
										<option value="" selected="selected">Seleccionar</option>
									</select>
								</div>
								<div class="col-md-1">
									<label> Transportista: </label>
								</div>
								<div class="col-md-3">
									 <select tipo-control="select2" id="cmpTransportista" name="cmpTransportista" class="form-control text-uppercase"
										style="width: 100%">
										<option value="" selected="selected">Seleccionar</option>
									</select> 
								</div>
								<div class="col-md-2">
									<label> Cisterna/Tracto:</label>
								</div>
								<div class="col-md-2">
									<select tipo-control="select2" id="cmpCisternaTracto" name="cmpCisternaTracto" class="form-control"
										style="width: 100%">
										<option value="" selected="selected">Seleccionar</option>
									</select>
								</div>
							</div>
						</div>

						<div class="col-xs-12 form-group">
							<div class="row">
								<div class="col-md-1">
									<label> Conductor:</label>
								</div>
								<div class="col-md-7">
									<select tipo-control="select2" id="cmpConductor" name="cmpConductor" class="form-control text-uppercase" style="width: 100%">
										<option value="" selected="selected">Seleccionar</option>
									</select>
								</div>
								<div class="col-md-2">
									<label> F. Emisi&oacute;n O/E:</label>
								</div>
								<div class="col-md-2">
									<input name="cmpFemisionOE" id="cmpFemisionOE" type="text" class="form-control"  required data-inputmask="'alias': 'dd/mm/yyyy'" placeholder="" />
								</div>
							</div>
						</div>

						<div class="col-xs-12 form-group">
							<div class="row">
								<div class="col-md-1">
									<label> Precintos:</label>
								</div>
								<div class="col-md-11">
									<input name="cmpPrecintos" id="cmpPrecintos" maxlength="180" type="text" class="form-control" placeholder="" />
								</div>
							</div>
						</div>
						<div class="col-xs-12 form-group" id="contenedorDetalles">
							<div class="col-xs-12 form-group">
								<div class="row">
								  <div class="col-md-1" align="center"> <label>Compartimentos </label>			</div>
								  <div class="col-md-4" align="center">	<label> Producto </label>				</div>
								  <div class="col-md-1" align="center">	<label>Vol. T. Obs. (gal) </label>		</div>
								  <div class="col-md-1" align="center">	<label>Temperatura (F)</label>			</div>
								  <div class="col-md-1" align="center">	<label>API 60 F </label>				</div>
								  <div class="col-md-2" align="center">	<label>Factor </label>					</div>
								  <div class="col-md-2" align="center">	<label>Vol. 60 F (gal) </label>			</div>
								</div>
							</div>
							<div class="col-xs-12 form-group" id="GrupoTransporte">
							<div class="row">
								<div id="GrupoTransporte_template">
									<div class="col-md-1" style="margin-bottom: 2px;">
										<input elemento-grupo="compartimentos" id="GrupoTransporte_#index#_Compartimentos" name="transporte[detalle][#index#][compartimentos]" type="text" class="form-control text-right" disabled value="0" />
									</div>
									<div class="col-md-4" style="margin-bottom: 2px;">
										<select tipo-control="select2" elemento-grupo="producto" id="GrupoTransporte_#index#_Producto" style="width: 100%" name="transporte[detalle][#index#][producto]"
											class="form-control">
											<%ArrayList<Producto> listaProductos = (ArrayList<Producto>) request.getAttribute("productos");
												Producto eProducto = null;
												for (int i = 0; i < listaProductos.size(); i++) {
													eProducto = listaProductos.get(i);
											%>
											<option value='<%=eProducto.getId()%>'><%=eProducto.getNombre()%></option>
											<%	} %>
										</select>
									</div>
									<div class="col-md-1" style="margin-bottom: 2px;">
										<input elemento-grupo="volumenTempObservada" id="GrupoTransporte_#index#_VolumenTempObservada" name="transporte[detalle][#index#][volumenTempObservada]" required type="text" class="form-control text-right" value="0" />
									</div>
									<div class="col-md-1" style="margin-bottom: 2px;">
										<input elemento-grupo="temperatura" id="GrupoTransporte_#index#_Temperatura" name="transporte[detalle][#index#][temperatura]" required type="text" class="form-control text-right" value="0" />
									</div>
									<div class="col-md-1" style="margin-bottom: 2px;">
										<input elemento-grupo="API" id="GrupoTransporte_#index#_API" name="transporte[detalle][#index#][API]" required type="text" type="text" class="form-control text-right" value="0" />
									</div>
									<div class="col-md-2" style="margin-bottom: 2px;">
										<input elemento-grupo="factor" id="GrupoTransporte_#index#_Factor" name="transporte[detalle][#index#][factor]" required type="text" class="form-control text-right" value="0" />
									</div>
									<div class="col-md-2" style="margin-bottom: 2px;">
										<input elemento-grupo="volumen60F" id="GrupoTransporte_#index#_Volumen60F" name="transporte[detalle][#index#][volumen60F]" required type="text" class="form-control text-right" value="0" />
									</div>
								</div>
								<div id="GrupoTransporte_noforms_template">No hay registros disponibles</div>
								</div>
							</div>
							<div class="col-xs-12 form-group">
								<div class="row">
								  <div class="col-md-5" style="margin-bottom: 2px;"></div>
								  <div class="col-md-1" style="margin-bottom: 2px;">	
								  	<input  name="cmpSumVolumenTempObservada" id="cmpSumVolumenTempObservada" type="text" class="form-control text-right" disabled />		
								  </div>
								  <div class="col-md-4" style="margin-bottom: 2px;"></div>
								  <div class="col-md-2" style="margin-bottom: 2px;">	
								  	<input name="cmpSumVolumen60F" id="cmpSumVolumen60F" type="text" class="form-control text-right" disabled />			
								  </div>
								</div>
							</div>
						</div>
					</form>
				</div>

				<div class="box-footer col-xs-12">
					<div class="col-md-1">
						<button id="btnGuardar" type="submit" class="btn btn-primary">Guardar</button>
					</div>
					<div class="col-md-10"></div>
					<div class="col-md-1">
						<button id="btnCancelarGuardarFormulario" class="btn btn-danger">Cancelar</button>
					</div>
				</div>
				<div class="overlay" id="ocultaContenedorFormulario" style="display: none;">
					<i class="fa fa-refresh fa-spin"></i>
				</div>
			</div>
		</div>
	</div>


	<div class="row" id="cntVistaRegistro" style="display: none;">
		<div class="col-md-12">
			<div class="box box-default">
				<div class="box-header with-border">
					<h3 id="cmpTituloFormulario" class="box-title"></h3>
				</div>
				<div class="box-body">

					<div class="col-xs-12 form-group">
						<div class="row" style="padding: 4px;">
							<div class="col-md-1">	<label>Cliente:</label>						</div>
							<div class="col-md-3">	<span class="text-uppercase" id='vistaCliente'></span> 		</div>
							<div class="col-md-1">	<label>Operaci&oacute;n:</label>			</div>
							<div class="col-md-3">	<span id='vistaOperacion'></span> 			</div>
							<div class="col-md-2">	<label> F. Planificaci&oacute;n:</label>		</div>
							<div class="col-md-2">	<span id='vistaFechaPlanificacion'></span>	</div>
						</div>
					</div>
	
					<div class="col-xs-12 form-group">
						<div class="row">
							<div class="col-md-2">	<label> Guia Remisión:</label>		</div>
							<div class="col-md-1">	<span id='vistaNumeroGuiaRemision'></span></div>
							<div class="col-md-2">	<label> Orden Entrega:</label>  	</div>
							<div class="col-md-1">	<span id='vistaNumeroOrdenCompra'></span></div>
							<div class="col-md-1">	<label> Factura:</label>			</div>
							<div class="col-md-2">	<span id='vistaNumeroFactura'></span></div>
							<div class="col-md-1">	<label> SCOP:</label>				</div>
							<div class="col-md-2">	<span id='vistaCodigoScop'></span></div>
						</div>
					</div>
	
					<div class="col-xs-12 form-group">
						<div class="row">
							<div class="col-md-1">	<label> Planta:</label>					</div>
							<div class="col-md-2">	<span class="text-uppercase" id='vistaPlantaDespacho'></span>	</div>
							<div class="col-md-2">	<label> Transportista:</label>			</div>
							<div class="col-md-2">	<span class="text-uppercase" id='vistaIdTransportista'></span>	</div>
							<div class="col-md-2">	<label> Cisterna/Tracto:</label>		</div>
							<div class="col-md-3">	<span id='vistaCisternaTracto'></span>	</div>
						</div>
					</div>
	
					<div class="col-xs-12 form-group">
						<div class="row">
							<div class="col-md-1">	<label> Conductor:</label>				</div>
							<div class="col-md-6">	<span class="text-uppercase" id='vistaIdConductor'></span>		</div>
							<div class="col-md-2">	<label> F. Emisi&oacute;n O/E:</label>	</div>
							<div class="col-md-3">	<span id='vistaFechaEmisionGuia'></span></div>
						</div>
					</div>
	
					<div class="col-xs-12 form-group">
						<div class="row">
							<div class="col-md-1">	<label> Precintos:</label>				</div>
							<div class="col-md-11">	<span id='vistaPrecintos'></span></div>
						</div>
					</div>
					
					<table id="listado_vista_detalles" class="table table-bordered table-striped"></table>
				</div>
				
				<div class="box-header with-border">
					<div class="col-xs-12">
						<div class="row">
							<div class="col-md-12">	<label> Eventos:</label>				</div>
						</div>
						<div class="box with-border">
							<table width="80%" id="listado_vista_eventos" cellpadding="5" cellspacing="5" ></table>
						</div>
					</div>
				</div>
				<div class="box-footer" align="right">
					<button id="btnCerrarVista" class="btn btn-danger">Cerrar</button>
				</div>
			</div>
		</div>
	</div>

	<div class="row" id="cntEventoTransporte" style="display: none;">
		<div class="col-md-12">
			<div class="box box-default">
				<div class="box-header with-border">
					<h3 id="cmpTituloFormulario" class="box-title"></h3>
				</div>
				<div class="box-default">
					
					<form id="frmEvento" role="form">
						<div class="col-xs-12 form-group">
							<br />
							<div class="row">
								<div class="col-md-2">	<label>Cliente:</label>						</div>
								<div class="col-md-2">	<span id='cmpEventoCliente'></span>			</div>
								<div class="col-md-2">	<label>Operaci&oacute;n:</label>			</div>
								<div class="col-md-2">	<span id='cmpEventoOperacion'></span> 		</div>
								<div class="col-md-2">	<label> F. Planificaci&oacute;n:</label>	</div>
								<div class="col-md-2">	<span id='cmpEventoFechaPlanificacion'></span>	</div>
							</div>
							<br />
							<div class="row">
								<div class="col-md-2">	<label> Guia Remisi&oacute;n:</label>		</div>
								<div class="col-md-2">	<span id='cmpEventoNumeroGuiaRemision'></span></div>
								<div class="col-md-2">	<label> Orden Entrega:</label>  	</div>
								<div class="col-md-2">	<span id='cmpEventoNumeroOrdenCompra'></span></div>
								<div class="col-md-2">	<label> Cisterna/Tracto:</label>			</div>
								<div class="col-md-2">	<span id='cmpEventoCisternaTracto'></span></div>
							</div>
							<br />
							<div class="row">
								<div class="col-md-2">	<label> Tipo:</label>					</div>
								<div class="col-md-2">
									 <input class="hide" name="cmpEventoIdTransporteEvento" id='cmpEventoIdTransporteEvento' />
									<select id="cmpEventoTipoEvento" name="cmpEventoTipoEvento" class="form-control">
										<option value="1">Incidencia</option>
										<option value="2">Accidente</option>
										<option value="3">Falla Operacional</option>
									</select>
								</div>
								<div class="col-md-4"></div>
								<div class="col-md-2">	<label> Fecha y hora:</label>			</div>
								<div class="col-md-2">
									<input name="cmpEventoFechaHora" id="cmpEventoFechaHora" type="text" class="form-control" required data-bind="value: dateTime" placeholder="__/__/____ __:__:__" data-inputmask="'mask': 'd/m/y h:s:s'" />	 
								</div>
							</div>
							<br />
							<div class="row">
								<div class="control-label col-md-12">	<label> Observaciones:</label>				</div>
							</div>
							<div class="row">
								<div class="col-md-12">	
									<textarea class="form-control text-uppercase" required id="cmpEventoDescripcion" name="cmpEventoDescripcion"  placeholder="Ingrese observaci&oacute;n..." rows="3" ></textarea>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="box-footer">
		            <button id="btnGuardarEvento" type="submit" class="btn btn-primary">Guardar</button>
		            <button id="btnCancelarGuardarEvento" class="btn btn-danger">Cancelar</button>
		            <br />
		    	</div>
		    	<div class="overlay" id="ocultaFormulario" style="display:none;">
		            <i class="fa fa-refresh fa-spin"></i>
		        </div>
			</div>
		</div>
	</div>
	
	
	<div class="row" id="cntPesajeTransporte" style="display: none;">
		<div class="col-md-12">
			<div class="box box-default">
				<div class="box-header with-border">
					<h3 id="cmpTituloFormulario" class="box-title"></h3>
				</div>
				<div class="box-default">
					<form id="frmPesaje" role="form">
						<div class="col-xs-12 form-group">
							 <br />
							<div class="row">
								<div class="col-md-1">	<label>Cliente:</label>						</div>
								<div class="col-md-3">	<span id='cmpPesajeCliente'></span>			</div>
								<div class="col-md-2">	<label>Operaci&oacute;n:</label>			</div>
								<div class="col-md-2">	<span id='cmpPesajeOperacion'></span> 		</div>
								<div class="col-md-2">	<label> F. Planificaci&oacute;n:</label>	</div>
								<div class="col-md-2">	<span id='cmpPesajeFechaPlanificacion'></span>	</div>
							</div>
						</div>
		
						<div class="col-xs-12 form-group">
							<div class="row">
								<div class="col-md-1">	<label> Guia Remisi&oacute;n:</label>		</div>
								<div class="col-md-3">	<span id='cmpPesajeNumeroGuiaRemision'></span></div>
								<div class="col-md-2">	<label> Orden Entrega:</label>  	</div>
								<div class="col-md-2">	<span id='cmpPesajeNumeroOrdenCompra'></span></div>
								<div class="col-md-2">	<label> Cisterna/Tracto:</label>			</div>
								<div class="col-md-2">	<span id='cmpPesajeCisternaTracto'></span></div>
							</div>
						</div>
					
						<div class="col-xs-12 form-group">
							<div class="row">
								<div class="col-md-1">	<label> Peso Bruto:</label>					</div>
								<div class="col-md-2">
									<input class="hide" name="cmpPesajeIdTransporte" id='cmpPesajeIdTransporte' />
									<input name="cmpPesajePesoBruto" id="cmpPesajePesoBruto" type="text" class="form-control text-right" required placeholder="" />
								</div>
								<div class="col-md-1"></div>
								<div class="col-md-2">	<label> Peso Tara:</label>			</div>
								<div class="col-md-2">	
									<input name="cmpPesajePesoTara" id="cmpPesajePesoTara"" type="text" class="form-control text-right" required placeholder="" />
								</div>
								<div class="col-md-1"></div>
								<div class="col-md-1">	<label> Peso Neto:</label>			</div>
								<div class="col-md-2">	
									<input name="cmpPesajePesoNeto" id="cmpPesajePesoNeto"" type="text" class="form-control text-right" disabled placeholder="" />
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="box-footer">
		            <button id="btnGuardarPesaje" type="submit" class="btn btn-primary">Guardar</button>
		            <button id="btnCancelarGuardarPesaje" class="btn btn-danger">Cancelar</button>
		    	</div>
		    	<div class="overlay" id="ocultaFormulario" style="display:none;">
		            <i class="fa fa-refresh fa-spin"></i>
		          </div>
			</div>
		</div>
	</div>
	
	
	</section>
</div>