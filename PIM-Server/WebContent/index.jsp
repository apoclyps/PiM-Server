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
					<div id="table"></div>
				</div>
			</div>
		</div>

		<!-- Traffic Sources
      ================================================== -->
		<div class="col-sm-6">
			<div class="widget-box">
				<div class="widget-header widget-header-flat widget-header-small">
					<h5>
						<i class="icon-signal"></i> Traffic Sources
					</h5>

					<div class="widget-toolbar no-border">
						<button class="btn btn-minier btn-primary dropdown-toggle"
							data-toggle="dropdown">
							This Week <i class="icon-angle-down icon-on-right bigger-110"></i>
						</button>

						<ul
							class="dropdown-menu pull-right dropdown-125 dropdown-lighter dropdown-caret">
							<li class="active"><a href="#" class="blue"> <i
									class="icon-caret-right bigger-110">&nbsp;</i> This Week
							</a></li>

							<li><a href="#"> <i
									class="icon-caret-right bigger-110 invisible">&nbsp;</i> Last
									Week
							</a></li>

							<li><a href="#"> <i
									class="icon-caret-right bigger-110 invisible">&nbsp;</i> This
									Month
							</a></li>

							<li><a href="#"> <i
									class="icon-caret-right bigger-110 invisible">&nbsp;</i> Last
									Month
							</a></li>
						</ul>
					</div>
				</div>

				<div class="widget-body">
					<div class="widget-main">
						<div id="piechart-placeholder"></div>

						<div class="hr hr8 hr-double"></div>

						<div class="clearfix">
							<div class="grid3">
								<span class="grey"> <i
									class="icon-facebook-sign icon-2x blue"></i> &nbsp; likes
								</span>
								<h4 class="bigger pull-right">1,255</h4>
							</div>

							<div class="grid3">
								<span class="grey"> <i
									class="icon-twitter-sign icon-2x purple"></i> &nbsp; tweets
								</span>
								<h4 class="bigger pull-right">941</h4>
							</div>

							<div class="grid3">
								<span class="grey"> <i
									class="icon-pinterest-sign icon-2x red"></i> &nbsp; pins
								</span>
								<h4 class="bigger pull-right">1,050</h4>
							</div>
						</div>
					</div>
					<!-- /widget-main -->
				</div>
				<!-- /widget-body -->
			</div>
			<!-- /widget-box -->
		</div>
		<!-- /span -->

		<!-- Database Cache
      ================================================== -->
		<div class="col-sm-6">
			<div class="widget-box">
				<div class="widget-header widget-header-flat widget-header-small">
					<h5>
						<i class="icon-signal"></i> Database Cache
					</h5>

					<div class="widget-toolbar no-border">
						<button class="btn btn-minier btn-primary dropdown-toggle"
							data-toggle="dropdown">
							This Week <i class="icon-angle-down icon-on-right bigger-110"></i>
						</button>

						<ul
							class="dropdown-menu pull-right dropdown-125 dropdown-lighter dropdown-caret">
							<li class="active"><a href="#" class="blue"> <i
									class="icon-caret-right bigger-110">&nbsp;</i> This Week
							</a></li>

							<li><a href="#"> <i
									class="icon-caret-right bigger-110 invisible">&nbsp;</i> Last
									Week
							</a></li>

							<li><a href="#"> <i
									class="icon-caret-right bigger-110 invisible">&nbsp;</i> This
									Month
							</a></li>

							<li><a href="#"> <i
									class="icon-caret-right bigger-110 invisible">&nbsp;</i> Last
									Month
							</a></li>
						</ul>
					</div>
				</div>

				<div class="widget-body">
					<div class="widget-main">
						<div id="piechart-cache"></div>

						<div class="hr hr8 hr-double"></div>

						<div class="clearfix">
							<div class="grid3">
								<span class="grey"> <i
									class="icon-facebook-sign icon-2x blue"></i> &nbsp; likes
								</span>
								<h4 class="bigger pull-right">1,255</h4>
							</div>

							<div class="grid3">
								<span class="grey"> <i
									class="icon-twitter-sign icon-2x purple"></i> &nbsp; tweets
								</span>
								<h4 class="bigger pull-right">941</h4>
							</div>

							<div class="grid3">
								<span class="grey"> <i
									class="icon-pinterest-sign icon-2x red"></i> &nbsp; pins
								</span>
								<h4 class="bigger pull-right">1,050</h4>
							</div>
						</div>
					</div>
					<!-- /widget-main -->
				</div>
				<!-- /widget-body -->
			</div>
			<!-- /widget-box -->
		</div>
		<!-- /span -->

		<!-- /.container -->
	</div>

	<!--  Javascript files included in footer to make page load faster -->
	<jsp:include page="footer.jsp" />

</body>
</html>
