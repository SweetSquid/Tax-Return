<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <fmt:setBundle var="link" basename="message" scope="session"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="user.edit.name" bundle="${link}"/></title>
    <%--<title><fmt:message key="" bundle="${link}"/></title>--%>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <style>
        @media only screen and (min-width: 768px) {
            .dropdown:hover .dropdown-menu {
                display: block;
                margin-top: 0;
            }
        }
    </style>
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
                        <ul class="navbar-nav ml-auto main-nav ">
                            <li class="nav-item ">
                                <a class="nav-link"
                                   href="${pageContext.request.contextPath}/taxreturn/new-tax-return"><fmt:message
                                        key="user.header.new" bundle="${link}"/></a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link"
                                   href="${pageContext.request.contextPath}/taxreturn/action-report-list"><fmt:message
                                        key="user.header.actionList" bundle="${link}"/></a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <fmt:message key="user.header.history" bundle="${link}"/>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <a class="dropdown-item"
                                       href="${pageContext.request.contextPath}/taxreturn/history/taxreturn"><fmt:message
                                            key="user.header.history.tax" bundle="${link}"/></a>
                                    <a class="dropdown-item"
                                       href="${pageContext.request.contextPath}/taxreturn/history/change"><fmt:message
                                            key="user.header.history.change" bundle="${link}"/></a>
                                </div>
                            </li>
                        </ul>
                        <ul class="navbar-nav ml-auto mt-10">
                            <li class="nav-item">
                                <label class="nav-link " style="font-size: 30px">${fullname}</label>
                            </li>
                            <li class="nav-item logoutButton">
                                <a class="nav-link login-button"
                                   href="${pageContext.request.contextPath}/taxreturn/logout"><fmt:message
                                        key="common.header.log.out" bundle="${link}"/></a>
                            </li>
                            <li style="padding-top: 4.5%;"><a href="${pageContext.request.contextPath}/?lang=en"><img
                                    src="${pageContext.request.contextPath}/images/usa-flag.png" alt=""
                                    style="width: 40px;height: 25px;margin-left: 10px"></a></li>
                            <li style="padding-top: 4.5%;"><a href="${pageContext.request.contextPath}/?lang=ua"><img
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
                    <h3 class="bg-gray p-4">Edit tax return</h3>
                    <form method="post" action="${pageContext.request.contextPath}/taxreturn/action-report-list/edit">
                        <fieldset class="p-4">
                            <textarea name="message" class="border p-3 w-100 my-3 " placeholder="Enter something..."
                                      maxlength="299" style="resize: none" readonly>${message}</textarea>
                            <label class="w-100"> <fmt:message key="user.edit.name" bundle="${link}"/>
                                <select name="taxCategory" class="w-100 form-control mt-lg-2 mt-md-2 border"
                                        style=" color: #666666; font-size: 14px; font-weight: 400; font-family: sans-serif">
                                    <c:forEach items="${taxList}" var="tax">
                                        <option><c:out value="${tax.getInstance()}"/></option>
                                    </c:forEach>
                                </select>
                            </label>
                            <input type="text" name="wage" value="${taxReturn.getWage()}" pattern="^[0-9]+([.][0-9]+)?$"
                                   placeholder="<fmt:message key="user.new.wage" bundle="${link}"/>"
                                   class="border p-3 w-100 my-2" required> <br>
                            <input type="text" name="militaryCollection" value="${taxReturn.getMilitaryCollection()}"
                                   pattern="^[0-9]+([.][0-9]+)?$"
                                   placeholder="<fmt:message key="user.new.military" bundle="${link}"/>"
                                   class="border p-3 w-100 my-2" required> <br>
                            <input type="text" name="incomeTax" value="${taxReturn.getIncomeTax()}"
                                   pattern="^[0-9]+([.][0-9]+)?$"
                                   placeholder="<fmt:message key="user.new.income" bundle="${link}"/>"
                                   class="border p-3 w-100 my-2" required> <br>
                            <button type="submit"
                                    class="d-block py-3 px-4 bg-primary text-white border-0 rounded font-weight-bold">
                                <fmt:message key="user.new.send" bundle="${link}"/>
                            </button>
                            <c:if test="${not empty inspectorId}">
                                <a class="mt-3 d-inline-block text-primary"
                                   href="${pageContext.request.contextPath}/taxreturn/change-inspector"><fmt:message
                                        key="user.new.changeInspector" bundle="${link}"/></a>
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






