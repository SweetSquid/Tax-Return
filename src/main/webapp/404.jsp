<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <fmt:setBundle var="link" basename="message" scope="session"/>
    <!-- SITE TITTLE -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="404.header" bundle="${link}"/></title>


    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


</head>

<body class="body-wrapper">


<section class="section" style="padding: 15%; background: #F5F5F5; min-height:100vh; position:relative">
    <div class="language mx-2" style="
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 30px;
  right:30px">
        <div style="background-image: url('${pageContext.request.contextPath}/images/ukraine-flag.png');
                background-size: cover; height: 20px; width: 26px;">
            <a href="${pageContext.request.contextPath}/?lang=ua" style="display: block; height: 100%"></a>
        </div>
        <div style="background-image: url('${pageContext.request.contextPath}/images/usa-flag.png');
                background-size: cover; height: 20px; width: 26px; margin-top: 2.5px">
            <a href="${pageContext.request.contextPath}/?lang=en" style="display: block; height: 100%"></a>
        </div>
    </div>
    <div class="container">
        <div class="row justify-content-md-center text-center">
            <div class="col-md-6 text-center mx-auto">
                <div class="404-img">
                    <img src="${pageContext.request.contextPath}/images/404.jpg" alt="ERROR">
                </div>
                <div class="404-content">
                    <h1 class="display-1 pt-1 pb-2"><fmt:message key="404.error.oops" bundle="${link}"/></h1>
                    <p class="px-3 pb-2 text-dark"><fmt:message key="404.error.text" bundle="${link}"/></p>
                    <a href="${pageContext.request.contextPath}/taxreturn" class="btn btn-info"><fmt:message
                            key="404.error.home" bundle="${link}"/></a>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="${pageContext.request.contextPath}/footer.jsp"/>

</body>
</html>



