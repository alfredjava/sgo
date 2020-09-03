<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <section class="content-header">
    <h1>Clientes<small> - Listado</small>
    </h1>
  </section>
  <section class="content" id="cntInterface">
    <div class="row">
      <div class="col-xs-12">
      	<div id="bandaInformacion" class="callout callout-success">
      `	Cargando...
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
                  <option value="0">Todos</option>
                  <option value="1">Activo</option>
                  <option value="-1">Inactivo</option>
                </select>
              </div>
              <a id="btnFiltrar" class="btn btn-default"><i class="fa fa-refresh"></i>  Filtrar</a>
            </form>              
          </div>
          <div class="box-header">
            <div>
              <a id="btnAgregar" class="btn btn-default espaciado"><i class="fa fa-plus"></i>  Agregar</a>
              <a id="btnModificar" class="btn btn-default disabled espaciado"><i class="fa fa-edit"></i>  Modificar</a>
              <a id="btnModificarEstado" class="btn btn-default disabled espaciado"><i class="fa fa-cloud-upload"></i>  Activar</a>
              <a id="btnVer" class="btn btn-default disabled espaciado"><i class="fa fa-search"></i>  Ver</a>
            </div>
          </div>
          <div class="box-body">
            <table id="tablaPrincipal" class="table table-bordered table-striped" cellspacing="0" width="100%">
              <thead>
                <tr>
                <th>N#</th>
                <th>ID</th>
                <th>Nombre Corto</th>
                <th>Raz&oacute;n Social</th>
                <th>RUC</th>
                <th>Estado</th>
                </tr>
              </thead>
              <tfoot>
                <tr>
                <th>N#</th>
                <th>ID</th>
                <th>Nombre Corto</th>
                <th>Raz&oacute;n Social</th>
                <th>RUC</th>
                <th>Estado</th>
                </tr>
              </tfoot>
            </table>
            <div id="frmConfirmarModificarEstado" class="modal" data-keyboard="false" data-backdrop="static">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">SGO-PetroPeru</h4>
                  </div>
                  <div class="modal-body">
                    <p>¿Desea cambiar el estado del registro?</p>
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
                <input name="cmpNombreCorto" id="cmpNombreCorto" type="text" class="form-control text-uppercase" maxlength="20" required placeholder="Ingresar Nombre Corto" />
              </div>
              <div class="form-group">
                <label>RUC (11 caracteres)</label>
                <input name="cmpRuc" id="cmpRuc" type="text" class="form-control" required placeholder="Ingresar RUC" />
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
            <a id="btnGuardar" class="btn btn-primary"><i class="fa fa-save"></i>  Guardar</a>
            <a id="btnCancelarGuardar" class="btn btn-danger"><i class="fa fa-close"></i>  Cancelar</a>
          </div>
          <div class="overlay" id="ocultaContenedorFormulario">
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
                    <span id="vistaRuc"></span>
                  </td>
                </tr>
                <tr>
                  <td>Estado:</td>
                  <td>
                    <span id="vistaEstado"></span>
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
                    <span id="vistaIPCreacion"></span>
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
                    <span id="vistaIPActualizacion"></span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="box-footer">
            <button id="btnCerrarVista"  class="btn btn-danger">Cerrar</button>
          </div>
          <div class="overlay" id="ocultaContenedorVista">
            <i class="fa fa-refresh fa-spin"></i>
          </div>
        </div>
      </div>
    </div>
  </section>
</div>