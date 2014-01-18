<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<HTML>
  <HEAD>
    <TITLE>JAX-WS Instrumentation Demo</TITLE>
  </HEAD>
  <BODY>
	<c:if test="${not empty message}">
		<h1>${message}</h1>
	</c:if>
  </BODY>
</HTML>