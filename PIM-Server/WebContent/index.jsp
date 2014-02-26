<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.ico">

<!-- Bootstrap core CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css"
	type="text/css" />


<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<!-- JQuery CDN -->
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<!-- JQueryUI CDN -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
<!-- JQueryUI CSS -->

<style type="text/css">
body {
	padding-top: 50px;
}

.starter-template {
	padding: 40px 15px;
	text-align: left;
}
</style>
<!-- <script language="javascript" src="js/amazonAjax.js"></script>-->
</head>

<body>

	<jsp:include page="/nav-bar.jsp" />

	<div class="container">

	 <!-- Title Status
      ================================================== -->
		<div class="row">
			<div class="col-lg-4">
				<h2>API Status :</h2>
			</div>
			
			<div class="col-lg-4">
				<h2>Database Status :</h2>
			</div>
			
			<div class="col-lg-4">
				<h2>Resource Status :</h2>
			</div>
			
		</div>
		
	  <!-- Status
      ================================================== -->
		<div class="row">
	  <!-- API Status
      ================================================== -->
			<div class="col-lg-4">
				<div class="bs-example">
					<ul class="list-group">
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/AmazonController">Amazon</a></li>
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/ComicVineController">ComicVine</a></li>
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/ISBNDBController">ISBNDB</a></li>	
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/OMDBController">IMDB</a></li>
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/SteamController">Steam</a></li>
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/SpotifyController">Spotify</a></li>
					</ul>
				</div>
			</div>
			  <!-- API Status
      ================================================== -->
      <div class="col-lg-4">
				<div class="bs-example">
					<ul class="list-group">
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/MySQLController">MySQL</a></li>
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/CassandraController">Cassandra</a></li>
					</ul>
				</div>
			</div>
      	  <!-- API Status
      ================================================== -->
      <div class="col-lg-4">
				<div class="bs-example">
					<ul class="list-group">
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/HadoopController">Hadoop</a></li>
						<li class="list-group-item"><span class="badge">Active</span>
							<a href="${pageContext.request.contextPath}/StormController">Storm</a></li>
					</ul>
				</div>
			</div>
		</div>
		
	  <!-- Tables
      ================================================== -->
		<div class="bs-docs-section">

			<div class="row">
				<div class="col-lg-12">
					<div class="page-header">
						<h1 id="tables">Results</h1>
					</div>
					<div id="table">
					</div>
				</div>
			</div>
		</div>
		
		
		
		<!-- /.container -->
	    </div>

		<!-- Bootstrap core JavaScript
    ================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
