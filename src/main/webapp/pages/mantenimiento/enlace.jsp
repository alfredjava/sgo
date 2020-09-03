<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1>Enlace<small> - Listado</small> </h1>
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
			                <label for="txtFiltro" class="espaciado">Url Completa: </label>
			                <input id="txtFiltro" type="text" class="form-control espaciado text-uppercase"  placeholder="Buscar...">
			              </div>
			              <a id="btnFiltrar" class="btn btn-default"><i class="fa fa-refresh"></i>  Filtrar</a>
			            </form> 
					</div>
					<!-- BOTONES PARA EL MANTENIMIENTO -->
					<div class="box-header">
						<div>
							<a id="btnAgregar" class="btn btn-default espaciado"><i class="fa fa-plus"></i> Agregar</a> 
							<a id="btnModificar" class="btn btn-default disabled espaciado"><i class="fa fa-edit"></i> Modificar</a> 
							<a id="btnVer" class="btn btn-default disabled espaciado"><i class="fa fa-search"></i> Ver</a>
						</div>
					</div>
          <div class="box-body">
            <table id="tablaPrincipal" class="table table-striped table-bordered responsive" width="100%">
              <thead>
                <tr>
                <th>#</th>
                <th>Id</th>
                <th>Url Completa</th>
                <th>Url Relativa</th>
                <th>Orden</th>
                <th>Padre</th>
                <th>Tipo</th>
                <th>Permiso</th>
                
                </tr>
              </thead>
              <tfoot>
                <tr>
                <th>#</th>
                <th>Id</th>
                <th>Url Completa</th>
                <th>Url Relativa</th>
                <th>Orden</th>
                <th>Padre</th>
                <th>Tipo</th>
                <th>Permiso</th>
                </tr>
              </tfoot>
            </table>
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
                <label>Titulo</label>
                <input name="cmpTitulo" id="cmpTitulo" type="text" class="form-control text-uppercase" maxlength="80"  required placeholder="Ingresar titulo"/>
              </div>
              <div class="form-group">              
                <label>Url Completa</label>
                <input name="cmpUrlCompleta" id="cmpUrlCompleta" type="text" class="form-control"  maxlength="100" required placeholder="Ingresar url completa"/>
              </div>
              <div class="form-group">              
                <label>Url Relativa</label>
                <input name="cmpUrlRelativa" id="cmpUrlRelativa" type="text" class="form-control"  maxlength="100" required placeholder="Ingresar url relativa"/>
              </div>
              <div class="form-group">              
                <label>Orden</label>
                <input name="cmpOrden" id="cmpOrden" type="text" class="form-control" required placeholder="Ingresar orden"/>
              </div>
              <div class="form-group">              
                <label>Padre</label>
                <input name="cmpPadre" id="cmpPadre" type="text" class="form-control" required placeholder="Ingresar padre"/>
              </div>
              <div class="form-group">              
                <label>Tipo</label>
                <input name="cmpTipo" id="cmpTipo" type="text" class="form-control" required placeholder="Ingresar tipo"/>
              </div>
              <div class="form-group">
                <label>Permiso</label>
                <select tipo-control="select2" id="cmpPermiso" name="cmpPermiso" class="form-control" style="width: 100%">
                  <option value="" selected="selected">Seleccionar</option>
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
						<td>Titulo:</td>
						<td><span id='vistaTitulo'></span></td>
					</tr>
					<tr>
						<td>Url Completa:</td>
						<td><span id='vistaUrlCompleta'></span></td>
					</tr>
					<tr>
						<td>Url Relativa:</td>
						<td><span id='vistaUrlRelativa'></span></td>
					</tr>
					<tr>
						<td>Orden:</td>
						<td><span id='vistaOrden'></span></td>
					</tr>
					<tr>
						<td>Padre:</td>
						<td><span id='vistaPadre'></span></td>
					</tr>
					<tr>
						<td>Tipo:</td>
						<td><span id='vistaTipo'></span></td>
					</tr>
					<tr>
						<td>Permiso:</td>
						<td><span id='vistaPermiso'></span></td>
					</tr>

					<tr>
						<td>Creado el :</td>
						<td><span id="vistaCreadoEl"></span></td>
					</tr>
					<tr>
						<td>Creado Por :</td>
						<td><span id="vistaCreadoPor"></span></td>
					</tr>
					<tr>
						<td>Actualizado el :</td>
						<td><span id="vistaActualizadoEl"></span></td>
					</tr>
					<tr>
						<td>Actualizado Por :</td>
						<td><span id="vistaActualizadoPor"></span></td>
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