<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <fmt:setBundle var="link" basename="message" scope="session"/>
    <!-- SITE TITTLE -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="guest.login.header" bundle="${link}"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.js"></script>


    <script></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body class="body-wrapper">


<section>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <nav class="navbar navbar-expand-lg navbar-light navigation">
                    <a class="navbar-brand fa fa-home fa-3x" href="${pageContext.request.contextPath}/taxreturn">
                    </a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarSupportedContent"
                            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarSupportedContent">

                        <ul class="navbar-nav ml-auto mt-10">
                            <li class="nav-item">
                                <a class="nav-link login-button" href="/taxreturn/login"><fmt:message
                                        key="guest.header.log.in" bundle="${link}"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link login-button" href="/taxreturn/registration"><fmt:message
                                        key="guest.header.registration" bundle="${link}"/></a>
                            </li>
                        </ul>
                        <ul class="d-flex">
                            <li><a href="${pageContext.request.contextPath}/?lang=en"><img
                                    src="${pageContext.request.contextPath}/images/usa-flag.png" alt=""
                                    style="width: 40px;height: 25px;margin-left: 10px"></a></li>
                            <li><a href="${pageContext.request.contextPath}/?lang=ua"><img
                                    src="${pageContext.request.contextPath}/images/ukraine-flag.png" alt=""
                                    style="width: 40px;height: 25px;margin-left: 10px"></a></li>
                        </ul>
                    </div>
                </nav>
            </div>
        </div>
    </div>
</section>

<section class="login py-5 border-top-1">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-8 align-item-center">
                <div class="border">
                    <h3 class="bg-gray p-4"><fmt:message key="guest.login.now" bundle="${link}"/></h3>
                    <form method="post" action="${pageContext.request.contextPath}/taxreturn/login">
                        <fieldset class="p-4">
                            <input type="text" placeholder="<fmt:message key="guest.login.username" bundle="${link}"/>"
                                   name="username" value="${username}" class="border p-3 w-100 my-2"
                                   required>
                            <input type="password"
                                   placeholder="<fmt:message key="guest.login.password" bundle="${link}"/>"
                                   name="password" class="border p-3 w-100 my-2"
                                   required>
                            <button type="submit"
                                    class="d-block py-3 px-5 bg-primary text-white border-0 rounded font-weight-bold mt-3">
                                <fmt:message key="guest.login.log" bundle="${link}"/>
                            </button>
                            <a class="mt-3 d-inline-block text-primary"
                               href="${pageContext.request.contextPath}/taxreturn/registration"><fmt:message
                                    key="guest.login.register" bundle="${link}"/></a>
                            <c:if test="${wrongLogin eq 'false'}">
                                <div class="alert alert-danger" script>
                                    <strong><fmt:message key="guest.login.error" bundle="${link}"/></strong><fmt:message
                                        key="guest.login.wrong" bundle="${link}"/>
                                </div>
                                <c:remove var="wrongLogin"/>
                            </c:if>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="${pageContext.request.contextPath}/footer.jsp"/>
</body>
</html>

<script>
    window.setTimeout(function () {
        $(".alert").fadeTo(500, 0).slideUp(500, function () {
            $(this).remove();
        });
    }, 2000);
</script>
