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
    <title><fmt:message key="guest.reg.header" bundle="${link}"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/jquery/"></script>

</head>
<%--\\TODO already have an account?--%>
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
                                <a class="nav-link login-button"
                                   href="${pageContext.request.contextPath}/taxreturn/login"><fmt:message
                                        key="guest.header.log.in" bundle="${link}"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link login-button"
                                   href="${pageContext.request.contextPath}/taxreturn/registration"><fmt:message
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
                <div class="border border">
                    <h3 class="bg-gray p-4"><fmt:message key="guest.login.register" bundle="${link}"/></h3>
                    <form id="register-form" name="register" method="post"
                          action="${pageContext.request.contextPath}/taxreturn/registration">
                        <fieldset class="p-4">
                            <input type="text" name="fullName" value="${fullName}"
                                   placeholder="<fmt:message key="guest.reg.fullname" bundle="${link}"/>"
                                   class="border p-3 w-100 my-2"
                                   required>
                            <input type="text" name="username" value="${username}"
                                   placeholder="<fmt:message key="guest.reg.username" bundle="${link}"/>"
                                   class="border p-3 w-100 my-2"
                                   required>
                            <input type="text" name="idCode" value="${idCode}" maxlength="8"
                                   placeholder="<fmt:message key="guest.reg.idcode" bundle="${link}"/>"
                                   class="border p-3 w-100 my-2"
                                   required>
                            <input type="text" name="phone" value="${phone}"
                                   placeholder="<fmt:message key="guest.reg.phone" bundle="${link}"/>"
                                   class="border p-3 w-100 my-2"
                                   required>
                            <input type="text" name="email" value="${email}"
                                   placeholder="<fmt:message key="guest.reg.email" bundle="${link}"/>"
                                   class="border p-3 w-100 my-2"
                                   required>
                            <input type="password" name="password"
                                   placeholder="<fmt:message key="guest.reg.password" bundle="${link}"/>"
                                   class="border p-3 w-100 my-2"
                                   required>
                            <div class="loggedin-forgot d-inline-flex my-3">
                                <button id="register-form-submit" type="submit"
                                        class="d-block py-3 px-4 btn-primary text-white border-0 rounded font-weight-bold">
                                    <fmt:message key="guest.login.register" bundle="${link}"/>
                                </button>
                            </div>
                            <br>
                            <a class="mt-3 d-inline-block text-primary"
                               href="${pageContext.request.contextPath}/taxreturn/login"><fmt:message
                                    key="guest.reg.haveAcc" bundle="${link}"/></a>
                            <c:if test="${notUnique != null}">
                                <div class="alert alert-danger">
                                        ${notUnique}
                                </div>
                            </c:if>

                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="${pageContext.request.contextPath}/footer.jsp"/>


<script>
    window.setTimeout(function () {
        $(".alert").fadeTo(500, 0).slideUp(500, function () {
            $(this).remove();
        });
    }, 2500);
</script>

<script>
    (function () {
        var submitButton = document.getElementById('register-form-submit');

        var reg = {
            fullName: /^[A-ZА-Я][a-zA-Zа-яёїієґ]{3,}(?: [A-ZА-Я][a-zA-Zа-яёїієґ]*){0,2}$/,
            phone: /^\+?[0-9]{12}$/,
            idCode: /^[0-9]{8}$/,
            username: /^[a-zA-Z0-9]{3,12}$/,
            email: /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)$/
        };

        var errors = {
            fullName: false,
            username: false,
            idCode: false,
            phone: false,
            email: false,
            password: false
        };

        var canSubmit = true;

        var validate = function (e) {
            var isValid = reg[e.target.name] ? reg[e.target.name].test(e.target.value) : true;

            if (isValid) {
                e.target.classList.add('border-success');
                e.target.classList.remove('border-danger');
                errors[e.target.name] = false;
            } else {
                e.target.classList.add('border-danger');
                e.target.classList.remove('border-success');
                errors[e.target.name] = true;
            }

            canSubmit = true;
            for (var key in errors) {
                canSubmit = canSubmit && !errors[key];
            }

            submitButton.disabled = !canSubmit;
            !canSubmit ? submitButton.classList.add('btn-danger') : submitButton.classList.remove('btn-danger');

            console.log(canSubmit);
        };

        var fullName = document.querySelector('input[name="fullName"]');
        var username = document.querySelector('input[name="username"]');
        var idCode = document.querySelector('input[name="idCode"]');
        var phone = document.querySelector('input[name="phone"]');
        var email = document.querySelector('input[name="email"]');
        var password = document.querySelector('input[name="password"]');

        fullName.addEventListener('input', validate);
        username.addEventListener('input', validate);
        idCode.addEventListener('input', validate);
        phone.addEventListener('input', validate);
        email.addEventListener('input', validate);
        password.addEventListener('input', validate);
    })();

</script>
</body>
</html>


<script>
    console.log('VYKONAV KISIK inokenti for MYKYTA FROLOV')
</script>