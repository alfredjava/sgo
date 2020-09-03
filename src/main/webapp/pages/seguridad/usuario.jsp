<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="sgo.entidad.Cliente"%>
<%@ page import="sgo.entidad.Operacion"%>
<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1>Usuarios<small> - Listado</small> </h1>
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
			                  <option value="2">Todos</option>
			                  <option value="1">Activo</option>
			                  <option value="0">Inactivo</option>
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
							<a id="btnAutorizar" class="btn btn-default disabled espaciado"><i class="fa fa-key"></i> Autorizar</a> 
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
						<th>Identidad</th>
						<th>Rol</th>
						<th>Operacion</th>
						<th>Estado</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th>N#</th>
						<th>ID</th>
						<th>Nombre</th>
						<th>Identidad</th>
						<th>Rol</th>
						<th>Operacion</th>
						<th>Estado</th>
					</tr>
				</tfoot>
            </table>
            <!-- Esto para la confirmación de cambio de estado del registro -->
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
            	<div class="overlay" id="ocultaContenedorTabla">
	            	<i class="fa fa-refresh fa-spin"></i>
	          	</div>
            </div>
    	</div>
	</div>
    <!-- Aqui empieza el formulario -->        
    <div class="row" id="cntFormulario" style="display:none;">
      <div class="col-md-12">
        <div class="box box-default">
          <div class="box-header">
            <h3 id="cmpTituloFormulario" class="box-title">Agregar Nuevo Registro</h3>
          </div>
          <div class="box-body">
            <form id="frmPrincipal" role="form">
					<div class="form-group">
						<label>Nombre</label> 
            <input name="cmpNombre" id="cmpNombre" type="text" class="form-control text-uppercase" maxlength="16" required placeholder="" />
					</div>
					<div class="form-group">
						<label>Clave</label> 
            <input name="cmpClave" id="cmpClave" type="password" class="form-control" required placeholder="" />
					</div>
					<div class="form-group">
						<label>Identidad</label> 
            <input name="cmpIdentidad" id="cmpIdentidad" type="text" class="form-control text-uppercase" maxlength="120" required placeholder="" />
					</div>
					<div class="form-group">
						<label>Zona Horaria</label> 
            <select name="cmpZonaHoraria" id="cmpZonaHoraria" class="form-control" >
              <option value="">Elija...</option>
              <option value="-12">UTC -12</option>
              <option value="-11">UTC -11</option>
              <option value="-10">UTC -10</option>
              <option value="-09">UTC -9</option>
              <option value="-08">UTC -8</option>
              <option value="-07">UTC -7</option>
              <option value="-06">UTC -6</option>
              <option value="-05">UTC -5</option>
              <option value="-04">UTC -4</option>
              <option value="-03">UTC -3</option>
              <option value="-02">UTC -2</option>
              <option value="-01">UTC -1</option>
              <option value="+00">UTC +0</option>
              <option value="+01">UTC +1</option>
              <option value="+02">UTC +2</option>
              <option value="+03">UTC +3</option>
              <option value="+04">UTC +4</option>
              <option value="+05">UTC +5</option>
              <option value="+06">UTC +6</option>
              <option value="+07">UTC +7</option>
              <option value="+08">UTC +8</option>
              <option value="+09">UTC +9</option>
              <option value="+10">UTC +10</option>
              <option value="+11">UTC +11</option>
              <option value="+12">UTC +12</option>
              <option value="+13">UTC +13</option>
              <option value="+14">UTC +14</option>
            </select>
					</div>
					<div class="form-group">
						<label>Email</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-envelope"></i>
							</span>
							<input  name="cmpEmail" id="cmpEmail" class="form-control"  type="email" maxlength="120" placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label>Rol</label> 
            <select tipo-control="select2" id="cmpIdRol" name="cmpIdRol" class="form-control" style="width: 100%">
							<option value="" selected="selected">Seleccionar</option>
						</select>
					</div>
          <div class="form-group">
						<label>Cliente</label>
            <select tipo-control="select2" id="cmpIdCliente" name="cmpIdCliente" class="form-control" style="width: 100%">
							<option value="" selected="selected">Todos</option>
					<%
                    HashMap<String,String> listaClientes ;
					ArrayList<?> listadoClientes;
					listadoClientes = (ArrayList<?>) request.getAttribute("listadoClientes"); 
                    Cliente eCliente=null;
                    for(int contador=0; contador < listadoClientes.size(); contador++){ 
                     eCliente =(Cliente) listadoClientes.get(contador);
                    %>
                    <option value='<%=eCliente.getId()%>'><%=eCliente.getNombreCorto().trim() %></option>
                    <%} %>
						</select>
					</div>
          <div class="form-group">
						<label>Operacion</label>
            <select tipo-control="select2" id="cmpIdOperacion" name="cmpIdOperacion" class="form-control" style="width: 100%">
							<option value="" selected="selected">Todas</option>
						</select>
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
  
  
  <div class="row" id="cntAutorizacion" style="display:none;">
      <div class="col-md-12">
        <div class="box box-default">
          <div class="box-header">
            <h3 class="box-title">Asignar Autorizaci&oacute;n a Usuario</h3>
          </div>
          <div class="box-body">
            <form id="frmAutorizacion" role="form">
            	<div class="col-xs-12">
					<div class="row">
					<br />
						<div class="col-md-1">	<label>Usuario:</label>						</div>
						<div class="col-md-3">	<span id='usuario'>	</span> 		</div>
						<div class="col-md-1">	<label>Rol:</label>			</div>
						<div class="col-md-3">	<span id='RolUsuario'></span> 		</div>
						<div class="col-md-2">	<label> Operaci&oacute;n:</label>	</div>
						<div class="col-md-2">	<span id='OperacionUsuario'></span></div>
					</div>
					<div class="col-md-12">
						 <table id="tablaSecundaria" class="table table-bordered table-striped" cellspacing="0" width="100%">
						   <thead>
						      <tr>
						         <th></th>
						         <th>ID</th>
						         <th>Autorizaci&oacute;n</th>
						   	  </tr>
						   </thead>
						</table>
					</div>
				</div>
			</form>
          </div>
          <div class="box-footer">
            <button id="btnGuardarAutorizacion" type="submit" class="btn btn-primary">Guardar</button>
            <button id="btnCancelarAutorizacion"  class="btn btn-danger">Cancelar</button>
          </div>

        </div>
      </div>
    </div>

  
  
    <div class="row" id="cntVistaRegistro" style="display:none;">
      <div class="col-md-12">
        <div class="box box-default">
        	<div class="box-header with-border">
        		<h3 class="box-title">Detalle del registro</h3>
        	</div>
          <div class="box-body">
            <table class="table table-bordered table-striped">
            <tbody>
						<tr>
							<td>Id:</td>
							<td><span id='vistaId'></span></td>
						</tr>
						<tr>
							<td>Nombre:</td>
							<td><span id='vistaNombre'></span></td>
						</tr>
						<tr>
							<td>Clave:</td>
							<td><span id='vistaClave'></span></td>
						</tr>
						<tr>
							<td>Identidad:</td>
							<td><span id='vistaIdentidad'></span></td>
						</tr>
						<tr>
							<td>Zona Horaria:</td>
							<td><span id='vistaZonaHoraria'></span></td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><span id='vistaEmail'></span></td>
						</tr>
						<tr>
							<td>Rol:</td>
							<td><span id='vistaIdRol'></span></td>
						</tr>
						<tr>
							<td>Tipo:</td>
							<td><span id='vistaTipo'></span></td>
						</tr>
						<tr>
							<td>Operacion:</td>
							<td><span id='vistaIdOperacion'></span></td>
						</tr>
						<tr>
							<td>Estado:</td>
							<td><span id='vistaEstado'></span></td>
						</tr>
						<tr>
							<td>Creado el:</td>
							<td><span id='vistaCreadoEl'></span></td>
						</tr>
						<tr>
							<td>Creado por:</td>
							<td><span id='vistaCreadoPor'></span></td>
						</tr>
						<tr>
							<td>Actualizado por:</td>
							<td><span id='vistaActualizadoPor'></span></td>
						</tr>
						<tr>
							<td>Actualizado El:</td>
							<td><span id='vistaActualizadoEl'></span></td>
						</tr>
						<tr>
							<td>IP (Creación):</td>
							<td><span id='vistaIpCreacion'></span></td>
						</tr>
						<tr>
							<td>IP (Actualizacion):</td>
							<td><span id='vistaIpActualizacion'></span></td>
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