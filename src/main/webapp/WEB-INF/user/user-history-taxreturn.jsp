<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <fmt:setBundle var="link" basename="message" scope="session"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><fmt:message key="user.history.name" bundle="${link}"/></title>

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


<c:if test="${empty historyList}">
    <section class="overly  section-sm" style="margin-top:20vh">
        <!-- Container Start -->
        <div class="container">
            <div class="row justify-content-md-center text-center">
                <div class="col-md-8">
                    <p style="font-size: 5em"><fmt:message key="user.history.no" bundle="${link}"/></p>
                </div>
            </div>
        </div>
        <!-- Container End -->
    </section>
</c:if>

<section class="overly  section-sm">
    <!-- Container Start -->
    <div class="container">
        <div class="row justify-content-md-center text-center">
            <div class="col-md-8">
                <div style="size: auto; color: #07ad76">
                </div>
            </div>
        </div>
    </div>
    <!-- Container End -->
</section>


<c:if test="${not empty historyList}">
    <section class="dashboard section">
        <div class="col-md-10 offset-md-1 col-lg-8 offset-lg-0 w-100">
            <!-- Recently Favorited -->
            <div class="widget dashboard-container my-adslist" style="background: #F5F5F5">
                <h3 class="widget-header"><fmt:message key="user.history.list" bundle="${link}"/></h3>

                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="inspector.tax.action" bundle="${link}"/></th>
                        <th scope="col"><fmt:message key="admin.change.message" bundle="${link}"/></th>
                        <th scope="col"><fmt:message key="admin.change.date" bundle="${link}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${historyList}" var="history">
                        <input type="hidden" name="currentPage" value="1">
                        <tr>
                            <td class="w-25"><span class="categories ol1">
                                <c:if test="${ history.getAction() eq 'APPROVED'}">
                                    <fmt:message key="common.approved" bundle="${link}"/>
                                </c:if>
                                <c:if test="${ history.getAction() eq 'EDIT'}">
                                    <fmt:message key="common.edit" bundle="${link}"/>
                                </c:if></span>
                            </td>
                            <td class="w-25"><span class="categories col1"><c:out
                                    value="${history.getMessage()}"/></span>
                            </td>
                            <td class="w-25"><span class="categories col1">
                                <c:set var="pattern"><fmt:message key="common.time" bundle="${link}"/></c:set>
                                <fmt:parseDate value="${ history.getDate() }" pattern="yyyy-MM-dd'T'HH:mm"
                                               var="parsedDateTime" type="both"/>
                                <fmt:formatDate pattern="${pattern}" value="${ parsedDateTime }"/>
                            </span>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link" style="width: 120px;"
                                   href="${pageContext.request.contextPath}/taxreturn/history/taxreturn?page=${currentPage-1}">
                                    <fmt:message key="common.previous" bundle="${link}"/>
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${pageCount}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <li class="page-item active">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/taxreturn/history/taxreturn?page=${i}">
                                            <c:out value="${i}"/><span class="sr-only">(current)</span>
                                        </a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/taxreturn/history/taxreturn?page=${i}">
                                            <c:out value="${i}"/>
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${currentPage lt pageCount}">
                            <li class="page-item">
                                <a class="page-link" style="width: 120px;"
                                   href="${pageContext.request.contextPath}/taxreturn/history/taxreturn?page=${currentPage+1}">
                                    <fmt:message key="common.next" bundle="${link}"/>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </section>
</c:if>

<jsp:include page="${pageContext.request.contextPath}/footer.jsp"/>
</body>
</html>




