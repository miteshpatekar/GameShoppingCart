<!doctype html>

<!-- INDEX -->
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>GameZon</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
    <script type="text/javascript" src="javascript.js"></script>
    <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>

<body onload="init()">
<div id="container">
    <header>
    	<h1><a href="/">Game<span>Zoneeee</span></a></h1>    
    </header>
    <nav>
    	<ul>
        	<li class="start selected"><a href="index.html">Home</a></li>
            <li class=""><a href="/GameWebsite/Microsoft">Microsoft</a></li>
            <li class=""><a href="/GameWebsite/Sony">Sony</a></li>
            <li class="end"><a href="/GameWebsite/Nintendo">Nintendo</a></li>
            <li class="end"><a href="/GameWebsite/Accessories">Accessories</a></li>
            <li class="end"><a href="/GameWebsite/DataAnalytics.html">Data Analytics</a></li>
            <li class="end"><a href="signin.html">Sign In</a></li>
            <li class="end"><a href="signup.html">Sign Up</a></li>

            <form name="autofillform" action="autocomplete">
      Composer Name:</strong>
                            <input type="text"
                       size="40" 
                       id="complete-field"
                                   onkeyup="doCompletion()">
                       
                <table id="complete-table" class="popupBox" />
              
    </form>
        </ul>
    </nav>

	<img class="header-image" src="images/indexheader.jpg" width = "100%" height = "100%" alt="Index Page Image" />

    <div id="body">		

	<section id="content">

	    <article>
			<h2>Welcome to our Game World</h2>
			
            <p>Check out our new products to have fun with games</p>	
            
            <p>Get the best Products delivered at your door</p>		
		</article>
		
    </section>
        
    <aside class="sidebar">
	
            <ul>	
               <li>
                    <h4>Our Products</h4>
                    <ul>
                        <li><a href="/GameWebsite/Microsoft">Microsoft</a></li>
                        <li><a href="/GameWebsite/Sony">Sony</a></li>
                        <li><a href="/GameWebsite/Nintendo">Nintendo</a></li>
                        <li><a href="/GameWebsite/Accessories">Accessories</a></li>
                        <li><a href="/GameWebsite/DataAnalytics.html">Data Analytics</a></li>
                    </ul>
                </li>                                       
            </ul>
		
    </aside>
    
	<div class="clear"></div>
	</div>
    
	<footer>
	
        <div class="footer-bottom">
            <p>Let the Game began !</p>
        </div>
		
    </footer>
</div>

</body>

</html>