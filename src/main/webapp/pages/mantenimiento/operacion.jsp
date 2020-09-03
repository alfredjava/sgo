<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="sgo.entidad.Cliente"%>
<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1>Operaciones<small> - Listado</small> </h1>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div id="bandaInformacion" class="callout callout-success">
					Cargando...
				</div>
			</div>
		</div>
		<div class="row" id="cntTabla">
			<div class="col-xs-12">
				<div class="box">
					<div class="box-header">
						<form class="form-inline" role="form">
			              <div class="form-group">
			                <label for="txtFiltro" class="espaciado">Nombre: </label>
			                <input id="txtFiltro" type="text" class="form-control espaciado text-uppercase"  placeholder="Buscar...">
			              </div>
			              <div class="form-group">
			                <label for="cmpFiltroEstado" class="espaciado">Estado: </label>
			                <select id="cmpFiltroEstado" name="cmpFiltroEstado" class="form-control espaciado">
			                  <option value="0">Todos</option>
			                  <option value="1">Activo</option>
			                  <option value="-1">Inactivo</option>
			                </select>
			              </div>
			              <a id="btnFiltrar" class="btn btn-default"><i class="fa fa-refresh"></i>  Filtrar</a>
			            </form> 
					</div>
					<!-- BOTONES PARA EL MANTENIMIENTO -->
					<div class="box-header">
						<div>
							<a id="btnAgregar" class="btn btn-default espaciado"><i class="fa fa-plus"></i> Agregar</a> 
							<a id="btnModificar" class="btn btn-default disabled espaciado"><i class="fa fa-edit"></i> Modificar</a> 
							<a id="btnModificarEstado" class="btn btn-default disabled espaciado"><i class="fa fa-cloud-upload"></i> Activar</a> 
							<a id="btnVer" class="btn btn-default disabled espaciado"><i class="fa fa-search"></i> Ver</a>
						</div>
					</div>
          <div class="box-body">
            <table id="tablaPrincipal" class="table table-striped table-bordered responsive" width="100%">
              <thead>
                <tr>
 				<th>N#</th>
                <th>ID</th>
                 <th>Nombre</th>
                 <th>Cliente</th>
                 <th>Ref. planta recepci&oacute;n</th>
                 <th>Ref. destino mercader&iacute;a</th>
                 <th>Estado</th>                 
                </tr>
              </thead>
              <tfoot>
                <tr>
 				<th>N#</th>
                <th>ID</th>
                 <th>Nombre</th>
                 <th>Cliente</th>
                 <th>Ref. planta recepci&oacute;n</th>
                 <th>Ref. destino mercader&iacute;a</th>
                 <th>Estado</th>                 
                </tr>
              </tfoot>
            </table>
           <div id="frmConfirmarModificarEstado" class="modal">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">SGO-PetroPeru</h4>
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
        </div>
      </div>
    </div>
    <div class="row" id="cntFormulario" style="display:none;">
      <div class="col-md-12">
        <div class="box box-default">
          <div class="box-header">
            <h3 id="cmpTituloFormulario" class="box-title">uii</h3>
          </div>
          <div class="box-body">
            <form id="frmPrincipal" role="form">
               <div class="form-group">
                <label>Nombre (Max. 150 caracteres)</label>
                <input name="cmpNombre" id="cmpNombre" type="text" class="form-control text-uppercase" maxlength="150" required placeholder="Ingresar Nombre"/>
                </div>
              <div class="form-group">
                <label>Cliente</label>
                <select tipo-control="select2" id="cmpIdCliente" name="cmpIdCliente" class="form-control" style="width: 100%">
                  <option value="" selected="selected">Seleccionar</option>
                    <%
                    ArrayList<?> listadoClientes = (ArrayList<?>) request.getAttribute("listadoClientes");               
                    int numeroClientes = listadoClientes.size();
                    Cliente eCliente=null;
                    for(int contador=0; contador < numeroClientes; contador++){ 
                     eCliente =(Cliente) listadoClientes.get(contador);
                    %>
                    <option value='<%=eCliente.getId()%>'><%=eCliente.getRazonSocial().trim()  %></option>
                    <%}%>
                </select>
              </div>
              <div class="form-group">
                <label>Referencia Planta Recepci&oacute;n</label>
                 <input name="cmpIdRefPlantaRecepcion" id="cmpIdRefPlantaRecepcion" type="text" class="form-control" maxlength="20" required placeholder="Ingresar Referencia Planta Recepci&oacute;n"/>
              </div>
              <div class="form-group">
                <label>Referencia Destinatario Mercader&iacute;a</label>
                <input name="cmpIdRefDestMercaderia" id="cmpIdRefDestMercaderia" type="text" class="form-control" maxlength="20" required placeholder="Ingresar Referencia Destinatario Mercader&iacute;a"/>
              </div>
              <div class="form-group">
                <label>Vol&uacute;men Promedio Cisterna</label>
                <input name="cmpVolumenPromedioCisterna" id="cmpVolumenPromedioCisterna" type="text" class="form-control" required placeholder="Ingresar Vol&uacute;men Promedio Cisterna"/>
              </div>
            </form>
          </div>
          <div class="box-footer">
            <button id="btnGuardar" type="submit" class="btn btn-primary">Guardar</button>
            <button id="btnCancelarGuardar"  class="btn btn-danger">Cancelar</button>
          </div>
          <div class="overlay" id="ocultaFormulario" style="display:none;">
            <i class="fa fa-refresh fa-spin"></i>
          </div>
        </div>
      </div>
    </div>
    <div class="row" id="cntVistaRegistro" style="display:none;">
      <div class="col-md-12">
        <div class="box box-default">
        	<div class="box-header with-border">
        		<h3 class="box-title">Bordered Table</h3>
        	</div>
          <div class="box-body">
            <table class="table table-bordered table-striped">
            <tbody>
              <tr>
                <td> ID:</td>
                <td>
                <span id='vistaId'></span>
                </td>
              </tr>              
              <tr>
                <td> Nombre:</td>
                <td>
                <span id='vistaNombre'></span>
                </td>
              </tr>              
              <tr>
                <td> Cliente:</td>
                <td>
                <span id='vistaIdCliente'></span>
                </td>
              </tr>
              <tr>
                <td> Referencia Planta Recepcion:</td>
                <td>
                <span id='vistaIdRefPlantaRecepcion'></span>
                </td>
              </tr>
              <tr>
                <td> Referencia Destinatario Destino:</td>
                <td>
                <span id='vistaIdRefDestMercaderia'></span>
                </td>
              </tr>
              <tr>
                <td> Volumen Promedio Cisterna:</td>
                <td>
                <span id='vistaVolumenPromedioCisterna'></span>
                </td>
              </tr>    
              <tr>
                <td> Sincronizado el:</td>
                <td>
                <span id='vistaSincronizadoEl'></span>
                </td>
              </tr> 
              <tr>
                <td> Estado:</td>
                <td>
                <span id='vistaEstado'></span>
                </td>
              </tr>              
              <tr>
                <td>Creado el:</td>
                <td>
					<span id="vistaCreadoEl"></span>
                </td>
              </tr>
			   <tr>
              	<td>Creado por:</td>
                <td>
					<span id="vistaCreadoPor"></span>
                </td>
			</tr>
			<tr>
              	<td>IP Creaci&oacute;n:</td>
                <td>
					<span id="vistaIpCreacion"></span>
                </td>
			</tr> 
             <tr>
                <td>Actualizado el:</td>
                <td>
					<span id="vistaActualizadoEl"></span>
                </td>
              </tr>
				<tr>
                <td>Actualizado por:</td>
                <td>
					<span id="vistaActualizadoPor"></span>
                </td>
              </tr>
 				<tr>
              	<td>IP Actualizaci&oacute;n:</td>
                <td>
					<span id="vistaIpActualizacion"></span>
                </td>
               </tr>           
            </tbody>
            </table>
          </div>
			<div class="box-footer">
            <button id="btnCerrarVista"  class="btn btn-danger">Cerrar</button>
          </div>
        </div>
      </div>
    </div>
  </section>
</div>