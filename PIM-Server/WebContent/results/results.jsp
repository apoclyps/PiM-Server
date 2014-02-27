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
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/assets/ico/favicon.ico">

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

<c:choose>
	<c:when test="${param.resource=='comicvine'}">
		<script type="text/javascript">
		<%@include file="/js/ComicVineAjax.js" %>
		</script>
	</c:when>
	<c:when test="${param.resource=='amazon'}">
		
			<%@include file="/js/AmazonAjax.js" %>
		</script>
	</c:when>
	<c:when test="${param.resource=='spotify'}">
		<script type="text/javascript">
		<%@include file="/js/SpotifyAjax.js" %>
		</script>
	</c:when>
	<c:when test="${param.resource=='steam'}">
		<script type="text/javascript">
		<%@include file="/js/SteamAjax.js" %>
		</script>
	</c:when>
	<c:when test="${param.resource=='isbndb'}">
		<script type="text/javascript">
		<%@include file="/js/IsbndbAjax.js" %>
		</script>
	</c:when>
	<c:when test="${param.resource=='imdb'}">
		<script type="text/javascript">
		<%@include file="/js/ImdbAjax.js" %>
		</script>
	</c:when>
</c:choose>

</head>

<body>

	<!-- Navbar
      ================================================== -->
	<div class="navbar navbar-default navbar-fixed-top">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-responsive-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">PIM</a>
		</div>
		<div class="navbar-collapse collapse navbar-responsive-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#">Stats</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">APIs <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li class="dropdown-header">Raw JSON</li>
						<li><a
							href="${pageContext.request.contextPath}/AmazonController">Amazon</a></li>
						<li><a
							href="${pageContext.request.contextPath}/ComicVineController">ComicVine</a></li>
						<li><a
							href="${pageContext.request.contextPath}/OMDBController">IMDB</a></li>
						<li><a
							href="${pageContext.request.contextPath}/ISBNDBController">ISBNDB</a></li>
						<li><a
							href="${pageContext.request.contextPath}/SteamController">Steam</a></li>
						<li><a
							href="${pageContext.request.contextPath}/SpotifyController">Spotify</a></li>
						<li class="divider"></li>
						<li class="dropdown-header">AJAX</li>
						<li><a
							href="${pageContext.request.contextPath}/AmazonController">Amazon</a></li>
						<li><a
							href="${pageContext.request.contextPath}/ComicVineController">ComicVine</a></li>
						<li><a
							href="${pageContext.request.contextPath}/OMDBController">IMDB</a></li>
						<li><a
							href="${pageContext.request.contextPath}/ISBNDBController">ISBNDB</a></li>
						<li><a
							href="${pageContext.request.contextPath}/SteamController">Steam</a></li>
						<li><a
							href="${pageContext.request.contextPath}/SpotifyController">Spotify</a></li>

					</ul></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="#">Link</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Dropdown <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li class="divider"></li>
						<li><a href="#">Separated link</a></li>
					</ul></li>
			</ul>
			<form class="navbar-form navbar-right">
				<input type="text" class="form-control col-lg-8"
					placeholder="Search">
			</form>
		</div>
		<!-- /.nav-collapse -->
	</div>
	<!-- /.navbar -->

	<div class="container">

		<!-- Tables
      ================================================== -->
		<div class="bs-docs-section">

			<div class="row">
				<div class="col-lg-12">
					<div class="page-header">
						<h1 id="tables">${param.resource} Results</h1>
					</div>
					<div id="table"></div>
				</div>
			</div>
		</div>

		<!-- Forms
      ================================================== -->

	</div>
	<!-- /.container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
