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

<jsp:include page="header.jsp" />

<!-- <script language="javascript" src="js/amazonAjax.js"></script>-->

<!--  Traffic sources chart JS file -->
<script src="${pageContext.request.contextPath}/js/trafficSources.js"></script>
</head>

<body>
	<jsp:include page="nav-bar.jsp" />

	<div class="container">

		<!-- Title Status
      ================================================== -->
		<div class="row">
			<div class="col-lg-4">
				<div class="page-header">
					<h1>API Status :</h1>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="page-header">
					<h1>Database Status :</h1>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="page-header">
					<h1>Resource Status :</h1>
				</div>
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
					</ul>
				</div>
			</div>
		</div>



		
		<!-- /.container -->
	</div>

	<!--  Javascript files included in footer to make page load faster -->
	<jsp:include page="footer.jsp" />

</body>
</html>
