<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle var="link" basename="message" scope="session"/>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><fmt:message key="inspector.tax.headername" bundle="${link}"/></title>


    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/js/bootstrap.js">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
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
                        <ul class="navbar-nav ml-auto main-nav ">
                            <li class="nav-item ">
                                <a class="nav-link" href="${pageContext.request.contextPath}/taxreturn/tax-return-list"><fmt:message
                                        key="inspector.header.taxreturn.list" bundle="${link}"/></a>
                            </li>
                            <li class="nav-item dropdown dropdown-slide">

                        </ul>
                        <ul class="navbar-nav ml-auto mt-10">
                            <li class="nav-item">
                                <label class="nav-link " style="font-size: 30px">${fullname}</label>
                            </li>
                            <li class="nav-item logoutButton">
                                <a class="nav-link login-button "
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

<c:if test="${not empty taxReturnList}">
    <section class="dashboard section">
        <div class="col-md-10 offset-md-1 col-lg-8 offset-lg-0">
            <!-- Recently Favorited -->
            <div class="widget dashboard-container my-adslist" style="background: #F5F5F5">
                <h3 class="widget-header"><fmt:message key="inspector.tax.return" bundle="${link}"/></h3>

                <table class="table ">
                    <thead>
                    <tr>
                        <th><fmt:message key="inspector.tax.user" bundle="${link}"/></th>
                        <th class="text-center"><fmt:message key="inspector.tax.category" bundle="${link}"/></th>
                        <th class="text-center"><fmt:message key="inspector.tax.action" bundle="${link}"/></th>
                    </tr>
                    </thead>
                    <c:forEach items="${taxReturnList}" var="taxReturn">
                        <tbody>
                        <tr>
                            <td class="product-details">
                            <span class="categories ol1"><strong><fmt:message key="inspector.tax.userId"
                                                                              bundle="${link}"/>:</strong> <c:out
                                    value="${taxReturn.getUserId()}"/></span>
                                <span class="categories ol1"><strong><fmt:message key="inspector.tax.wage"
                                                                                  bundle="${link}"/>:</strong> <c:out
                                        value="${taxReturn.getWage()}"/></span>
                                <span class="categories ol1"><strong><fmt:message key="inspector.tax.military"
                                                                                  bundle="${link}"/>:</strong> <c:out
                                        value="${taxReturn.getMilitaryCollection()}"/></span>
                                <span class="categories ol1"><strong><fmt:message key="inspector.tax.income"
                                                                                  bundle="${link}"/>:</strong> <c:out
                                        value="${taxReturn.getIncomeTax()}"/></span>
                                <span class="categories ol1"><strong><fmt:message key="inspector.tax.posted"
                                                                                  bundle="${link}"/>: </strong><time>
                                <c:set var="pattern"><fmt:message key="common.time" bundle="${link}"/></c:set>
                                <fmt:parseDate value="${ taxReturn.getDate() }" pattern="yyyy-MM-dd'T'HH:mm"
                                               var="parsedDateTime" type="both"/>
                                <fmt:formatDate pattern="${pattern}" value="${ parsedDateTime }"/>
                                </time> </span>
                            </td>
                            <td class="product-category"><span class="categories ol1 align-content-center"><c:out
                                    value="${taxReturn.getCategory()}"/></span></td>
                            <td class="action" data-title="Action">
                                <div class="">
                                    <ul class="list-inline justify-content-center">
                                        <li class="list-inline-item">
                                            <a name="a" data-toggle="tooltip" data-placement="top"
                                               title="<fmt:message key="common.approve" bundle="${link}"/>"
                                               class="view"
                                               href="${pageContext.request.contextPath}/taxreturn/approve?id=${taxReturn.getId()}">
                                                <i class="fa fa-check"></i>
                                            </a>
                                        </li>
                                        <li class="list-inline-item">
                                            <a class="edit" data-toggle="tooltip" data-placement="top"
                                               title="<fmt:message key="common.edit" bundle="${link}"/>"
                                               href="${pageContext.request.contextPath}/taxreturn/edit?id=${taxReturn.getId()}">
                                                <i class="fa fa-pencil"></i>
                                            </a>
                                        </li>

                                    </ul>
                                </div>
                            </td>
                        </tr>

                        </tbody>
                    </c:forEach>
                </table>
                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link" style="width: 120px;"
                                   href="${pageContext.request.contextPath}/taxreturn/tax-return-list?page=${currentPage-1}">
                                    <fmt:message key="common.previous" bundle="${link}"/>
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${pageCount}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <li class="page-item active">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/taxreturn/tax-return-list?page=${i}">
                                            <c:out value="${i}"/><span class="sr-only">(current)</span>
                                        </a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/taxreturn/tax-return-list?page=${i}">
                                            <c:out value="${i}"/>
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${currentPage lt pageCount}">
                            <li class="page-item">
                                <a class="page-link" style="width: 120px;"
                                   href="${pageContext.request.contextPath}/taxreturn/tax-return-list?page=${currentPage+1}">
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
<c:if test="${empty taxReturnList}">
    <section class="overly  section-sm" style="margin-top:20vh">
        <!-- Container Start -->
        <div class="container">
            <div class="row justify-content-md-center text-center">
                <div class="col-md-8">
                    <p style="font-size: 4em"><fmt:message key="inspector.tax.noreturn" bundle="${link}"/></p>
                </div>
            </div>
        </div>
        <!-- Container End -->
    </section>
</c:if>


<jsp:include page="${pageContext.request.contextPath}/footer.jsp"/>
</body>
</html>




