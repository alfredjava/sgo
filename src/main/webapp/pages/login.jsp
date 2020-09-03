<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/processlogin" var="loginUrl"/>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Sistema de Gestión de Operaciones - Acceso al Sistema</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <!-- Bootstrap 3.3.2 -->
    <link href="tema/adminlte/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Font Awesome Icons -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="tema/adminlte/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- iCheck -->
    <link href="tema/adminlte/plugins/iCheck/square/blue.css" rel="stylesheet" type="text/css" />
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>
  <body class="login-page">
    <div class="login-box">
      <div class="login-logo">
        <!-- <a href="../../index2.html"><b>Sistema de Gestión de Operaciones SGO</b></a> -->
         <a><b>Sistema de Gestión de Operaciones SGO</b></a>
      </div><!-- /.login-logo -->
      <div class="login-box-body">
        <p class="login-box-msg"></p>        
        <form action="${loginUrl}" method="post">
        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          <div class="form-group has-feedback">
            <input name="username" type="text" class="form-control" placeholder="Usuario"/>
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input name="password" type="password" class="form-control" placeholder="Contraseña"/>
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="row">
            <div class="col-xs-8">                     
            </div>
            <div class="col-xs-4">
              <button type="submit" class="btn btn-primary btn-block btn-flat">Ingresar</button>
            </div><!-- /.col -->
          </div>
        </form>
        
      </div><!-- /.login-box-body -->
    </div><!-- /.login-box -->
    <!-- jQuery 2.1.3 -->
    <script src="tema/adminlte/plugins/jQuery/jQuery-2.1.3.min.js"></script>
    <!-- Bootstrap 3.3.2 JS -->
    <script src="tema/adminlte/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- iCheck -->
    <script src="tema/adminlte/plugins/iCheck/icheck.min.js" type="text/javascript"></script>
    <script>
      $(function () {
        $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        });
      });
    </script>
  </body>
</html>