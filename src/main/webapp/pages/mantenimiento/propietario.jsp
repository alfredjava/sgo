<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1>Propietarios<small> - Listado</small> </h1>
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
			                <label for="txtFiltro" class="espaciado">Raz&oacute;n Social: </label>
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
							<a id="btnVer" class="btn btn-default disabled espaciado"><i class="fa fa-search"></i> Ver</a>
						</div>
					</div>
          <div class="box-body">
            <table id="tablaPrincipal" class="table table-striped table-bordered responsive" width="100%">
              <thead>
                <tr>
                <th>N#</th>
                <th>ID</th>
                 <th>Raz&oacute;n Social</th>
                 <th>Nombre Corto</th>
                 <th>RUC</th>
                 <th>Tipo</th>
                 <th>Estado</th>                
                </tr>
              </thead>
              <tfoot>
                <tr>
                <th>N#</th>
                <th>ID</th>
                 <th>Raz&oacute;n Social</th>
                 <th>Nombre Corto</th>
                 <th>RUC</th>
                 <th>Tipo</th>
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
                    <p>�Desea Modificar el estado del registro?</p>
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
                <label>Raz&oacute;n Social (Max. 150 caracteres)</label>
                <input name="cmpRazonSocial" id="cmpRazonSocial" type="text" class="form-control text-uppercase" maxlength="150" required placeholder="Ingresar Raz&oacute;n Social"/>
                </div>
               <div class="form-group">
                <label>Nombre Corto (Max. 20 caracteres)</label>
                <input name="cmpNombreCorto" id="cmpNombreCorto" type="text" class="form-control text-uppercase" maxlength="20" required placeholder="Ingresar Nombre Corto"/>
                </div>
               <div class="form-group">
                <label>RUC (11 caracteres)</label>
                <input name="cmpRuc" id="cmpRuc" type="text" class="form-control" maxlength="11"  required placeholder="Ingresar RUC"/>
                </div>
               <div class="form-group">
                <label>Tipo</label>
                <select id="cmpTipo" name="cmpTipo" class="form-control">
                  <option value="">Seleccione</option>
                  <option value="1">Propietario</option>
                  <option value="2">Contratista</option>
                </select>
                </div>            
              <div class="form-group">
                <label>Estado</label>
                <select id="cmpEstado" name="cmpEstado" class="form-control">
                  <option value="1">Activo</option>
                  <option value="0">Inactivo</option>
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
                <td>
                <span id='vistaId'></span>
                </td>
             </tr> 
             <tr>
				<td>Nombre Corto:</td>
		        <td>
					<span id="vistaNombreCorto"></span>
		        </td>
			</tr>
			<tr>
				<td>Raz&oacute;n Social:</td>
					<td>
						<span id="vistaRazonSocial"></span>
					</td>
			</tr>     
              <tr>
                <td>RUC:</td>
                <td>
                <span id='vistaRuc'></span>
                </td>
              </tr>              
              <tr>
                <td>Tipo:</td>
                <td>
                <span id='vistaTipo'></span>
                </td>
              </tr>              
              <tr>
                <td>Estado:</td>
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