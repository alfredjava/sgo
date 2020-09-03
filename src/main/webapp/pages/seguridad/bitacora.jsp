<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<!-- Contenedor de pagina-->
<div class="content-wrapper">
  <!-- Cabecera de pagina -->
  <section class="content-header">
    <h1>Bitacora<small> - Listado</small>
    </h1>
    <ol class="breadcrumb">
    <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
    <li class="active">Here</li>
    </ol>
  </section>
  <!-- Contenido principal -->
  <section class="content">          
    <!-- El contenido debe incluirse aqui-->
    <div class="row">
      <div class="col-xs-12">
      <div id="bandaInformacion" class="callout callout-info">
      Bienvenido !
      </div> 
      </div>
    </div>
    <div class="row" id="cntTabla">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">            
            <div class="row" style="padding:4px;">
              <div class="col-md-1">
                <label class="control-label"> Entre el </label>
              </div>
              <div class="col-md-2">
                <input type="text" name="cmpFechaInicial" id="cmpFechaInicial" class="form-control"/>
              </div>
              <div class="col-md-1">
                <label class="control-label"> y el </label>
              </div>
              <div class="col-md-2">
                <input type="text" name="cmpFechaFinal" id="cmpFechaFinal" class="form-control"  />
              </div>
              <div class="col-md-1">
                <label class="control-label"> de la tabla </label>
              </div>
              <div class="col-md-2">
                <select id="cmpFiltroTabla" name="cmpFiltroTabla" class="form-control">
	                <option value="todos">				Todos</option>                     
					<option value="sgo.propietario">	sgo.propietario</option>           
					<option value="sgo.planta">			sgo.planta</option>                
					<option value="sgo.tanque">			sgo.tanque</option>                
					<option value="sgo.producto">		sgo.producto</option>              
					<option value="sgo.contometro">		sgo.contometro</option>            
					<option value="sgo.recorrido">		sgo.recorrido</option>             
					<option value="sgo.planificacion">	sgo.planificacion</option>         
					<option value="sgo.evento">			sgo.evento</option>                
					<option value="sgo.transporte">		sgo.transporte</option>            
					<option value="sgo.enlace">			sgo.enlace</option>                
					<option value="sgo.mensaje">		sgo.mensaje</option>               
					<option value="sgo.parametro">		sgo.parametro</option>             
					<option value="sgo.reporte">		sgo.reporte</option>               
					<option value="sgo.traduccion">		sgo.traduccion</option>            
					<option value="sgo.cliente">		sgo.cliente</option>               
					<option value="sgo.operacion">		sgo.operacion</option>             
					<option value="sgo.vehiculo">		sgo.vehiculo</option>              
					<option value="sgo.tracto">			sgo.tracto</option>                
					<option value="sgo.conductor">		sgo.conductor</option>             
					<option value="sgo.transportista">	sgo.transportista</option>         
					<option value="sgo.estacion">		sgo.estacion</option>              
					<option value="sgo.cisterna">		sgo.cisterna</option>              
                </select>
              </div>
              <div class="col-md-1">
                <label class="control-label">del usuario </label>
              </div>
              <div class="col-md-2">
                <select id="cmpFiltroUsuario" name="cmpFiltroUsuario" class="form-control">
                <option value="todos">Todos</option>
                <option value="admin">admin</option>
                <option value="pedro">pedro</option>
                </select>
              </div>
            </div>
			<!-- BOTONES PARA EL MANTENIMIENTO -->
			<div>
				 <a id="btnFiltrar" class="btn btn-default"><i class="fa fa-refresh"></i>  Filtrar</a>
				 <a id="btnVer" class="btn btn-default disabled espaciado"><i class="fa fa-search"></i> Ver</a>
            </div>
          </div>
          <div class="box-body">
            <table id="tablaPrincipal" class="table table-striped table-bordered responsive" width="100%">
              <thead>
                <tr>
				<th>#</th>
				<th>ID</th>
				<th>Tabla</th>
				<th>Usuario</th>
				<th>Fecha</th>
				<th>Acci&oacute;n</th>         
                </tr>
              </thead>
              <tfoot>
                <tr>
				<th>#</th>
				<th>ID</th>
                <th>Tabla</th>
				<th>Usuario</th>
				<th>Fecha</th>
				<th>Acci&oacute;n</th>   
                </tr>
              </tfoot>
            </table>
          </div>
        </div>
      </div>
    </div>
    <div class="row" id="cntVistaRegistro" style="display:none;">
      <div class="col-md-12">
        <div class="box box-default">
        	<div class="box-header with-border">
        		<h3 class="box-title">Detalle de registro</h3>
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
                <td> Usuario:</td>
                <td>
                <span id='vistaUsuario'></span>
                </td>
              </tr>
   				<tr>
                <td> Acci&oacute;n:</td>
                <td>
                <span id='vistaAccion'></span>
                </td>
              </tr> 
              <tr>
                <td> Tabla:</td>
                <td>
                <span id='vistaTabla'></span>
                </td>
              </tr> 
              <tr>
                <td> Contenido:</td>
                <td>
                <span id='vistaContenido'></span>
                </td>
              </tr>         
              <tr>
                <td> Realizado el:</td>
                <td>
                <span id='vistaRealizadoEl'></span>
                </td>
              </tr>              
              <tr>
                <td> Realizado por:</td>
                <td>
                <span id='vistaRealizadoPor'></span>
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