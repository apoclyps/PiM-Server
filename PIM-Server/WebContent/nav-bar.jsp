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
					    <li class="divider"></li>
						<li><a href="${pageContext.request.contextPath}/AmazonController">Amazon</a></li>
						<li><a href="${pageContext.request.contextPath}/ComicVineController">ComicVine</a></li>
						<li><a href="${pageContext.request.contextPath}/OMDBController">IMDB</a></li>
						<li><a href="${pageContext.request.contextPath}/ISBNDBController">ISBNDB</a></li>
						<li><a href="${pageContext.request.contextPath}/SteamController">Steam</a></li>
						<li><a href="${pageContext.request.contextPath}/SpotifyController">Spotify</a></li>
					</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">AJAX <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li class="dropdown-header">Dynamic Tables</li>
						<li class="divider"></li>
						<li><a href="${pageContext.request.contextPath}/AmazonController">Amazon</a></li>
						<li><a href="${pageContext.request.contextPath}/ComicVineController">ComicVine</a></li>
						<li><a href="${pageContext.request.contextPath}/OMDBController">IMDB</a></li>
						<li><a href="${pageContext.request.contextPath}/ISBNDBController">ISBNDB</a></li>
						<li><a href="${pageContext.request.contextPath}/SteamController">Steam</a></li>
						<li><a href="${pageContext.request.contextPath}/SpotifyController">Spotify</a></li>
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