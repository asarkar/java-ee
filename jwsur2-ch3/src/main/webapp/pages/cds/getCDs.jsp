<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean id="fetcher"
	type="com.github.mkalin.jwsur2.ch3.cds.FetchXML"
	class="com.github.mkalin.jwsur2.ch3.cds.FetchXML">
</jsp:useBean>

<html>
  <head>
    <title>Catalog</title>
  </head>
  <body>
    <div id="container" style="display: none">
      <jsp:getProperty name="fetcher" property="json" />
    </div>
    
    <script type="text/javascript"
	  src="http://code.jquery.com/jquery-latest.min.js">			
	</script>
	<script type="text/javascript">
	  var catalog = $("#container").text();
	  console.log(catalog);
	  var cds = $.parseJSON(catalog).cds;
	  $("#container").html("");
	  $("#container").append("<ul>");
	  $.each(cds, function(ind, val) {
	    $("#container").append(
          "<li>" + cds[ind].title + ": " + cds[ind].artist + "</li>"
        );
	  });
	  $("#container").append("</ul>");
	  
	  $("#container").show();
	</script>
  </body>
</html>
