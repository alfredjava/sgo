<!-- Contenedor de pagina-->
<div class="content-wrapper">
	<!-- Cabecera de pagina -->
	<section class="content-header">
		<h1>Registrar C&oacute;digo de Autorizaci&oacute;n</h1>
	</section>
	<!-- Contenido principal -->
	<section class="content">
		<!-- El contenido debe incluirse aqui-->
		<div class="row">
			<div class="col-md-12">
				<div id="bandaInformacion" class="callout callout-info">Bienvenido!</div>
			</div>
		</div>

		<div class="row" id="cntAutorizacion" style="display:none;">
	      <div class="col-md-12">
	        <div class="box box-default">
	          <div class="box-header">
	            <h3 class="box-title">Registrar C&oacute;digo de Autorizaci&oacute;n</h3>
	          </div>
	          <div class="box-body with-border">
	            <form id="frmPrincipal" role="form">
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
							 <table id="tablaAutorizaciones" class="table table-bordered table-striped" width="50%"> </table>
						</div>
						<br />
						<div class="row col-md-12">
							<div class="col-md-2">	<label>Ingrese C&oacute;digo:</label>						</div>
							<div class="col-md-3">	 
								<input id="cmpCodigo" name="cmpCodigo" type="password" class="form-control"/> 		
							</div>
							<div class="col-md-2"> </div>	
							<div class="col-md-2">	<label>Reingrese C&oacute;digo:</label>			</div>
							<div class="col-md-3">	
								<input id="cmpConfirmaCodigo" name="cmpConfirmaCodigo" type="password" class="form-control"/> 		
							</div>
						</div>
						
						<div class="row col-md-12">
							<br />
							<div class="col-md-2">	<label>Vigencia Desde:</label>						</div>
							<div class="col-md-3">	 
								<input name="cmpVigenciaDesde" id="cmpVigenciaDesde" type="text" class="form-control"  required data-inputmask="'alias': 'dd/mm/yyyy'" placeholder="Vigencia Desde" />
							</div>
							<div class="col-md-2"> </div>	
							<div class="col-md-2">	<label>Vigencia Hasta:</label>			</div>
							<div class="col-md-3">	
								<input name="cmpVigenciaHasta" id="cmpVigenciaHasta" type="text" class="form-control"  required data-inputmask="'alias': 'dd/mm/yyyy'" placeholder="Vigencia Hasta" />
							</div>
							<br />
						</div>
					</div>	
				</form>
	          </div>
					<div class="box-footer">
	            <button id="btnGuardarAutorizacion" type="submit" class="btn btn-primary">Guardar</button>
	            <button id="btnLlamaPoppupAutorizacion"  class="btn btn-success">Autorizar</button>
	            <button id="btnCancelarAutorizacion"  class="btn btn-danger">Cancelar</button>
	          </div>
	          
	          <div id="frmValidarCodigo" class="modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">SGO-PETROPER&Uacute;-S.A.</h4>
						</div>
						<div class="modal-body">
							<p>El C�digo es incorrecto.</p>
						</div>
						<div class="modal-footer">
							<button id="btnCerrarValidarCodigo" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
			
			<div id="frmValidarAutorizacion" class="modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" aria-label="Close" data-dismiss="modal" type="button">
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
									<div class="col-md-2"> <label  class="control-label" for="cmpDescAutorizacion">Autorizaci�n:</label> </div> 
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
			
			
	          <div class="overlay" id="ocultaFormulario" style="display:none;">
	            <i class="fa fa-refresh fa-spin"></i>
	          </div>
	        </div>
	      </div>
	    </div>

	</section>
</div>