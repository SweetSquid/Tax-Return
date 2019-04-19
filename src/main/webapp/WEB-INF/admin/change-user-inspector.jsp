<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <style>
        .dashboard {
            padding: 4% 15% 4% 15%;
        }
    </style>
    <fmt:setBundle var="link" basename="message" scope="session"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><fmt:message key="admin.header.changerequest" bundle="${link}"/></title>


    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
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
                    <div class="collapse navbar-collapse w-75" id="navbarSupportedContent">

                        <ul class="navbar-nav ml-auto main-nav ">
                            <li class="nav-item ">
                                <a class="nav-link"
                                   href="${pageContext.request.contextPath}/taxreturn/logged_users"><fmt:message
                                        key="admin.header.logged" bundle="${link}"/></a>
                            </li>
                            <li class="nav-item ">
                                <a class="nav-link"
                                   href="${pageContext.request.contextPath}/taxreturn/change-user-inspector"><fmt:message
                                        key="admin.header.changerequest" bundle="${link}"/></a>
                            </li>
                        </ul>

                        <ul class="navbar-nav ml-auto mt-10">
                            <li class="nav-link " style="font-size: 30px">Admin</li>
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


<c:if test="${ not empty changeInspectorList}">
    <section class="dashboard ">
        <div class="col-md-12 offset-md-1 col-lg-12 offset-lg-0 w-100">
            <!-- Recently Favorited -->
            <div class="widget dashboard-container my-adslist" style="background: #F5F5F5">
                <h3 class="widget-header"><fmt:message key="admin.change.returns" bundle="${link}"/></h3>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="admin.change.userid" bundle="${link}"/></th>
                        <th scope="col"><fmt:message key="admin.change.previous" bundle="${link}"/></th>
                        <th scope="col"><fmt:message key="admin.change.new" bundle="${link}"/></th>
                        <th scope="col"><fmt:message key="admin.change.message" bundle="${link}"/></th>
                        <th scope="col"><fmt:message key="admin.change.date" bundle="${link}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                        <%--//TODO сделать сортировку по кнопкам--%>

                    <c:forEach items="${changeInspectorList}" var="changeInspector">
                        <form id="change-inspector-form" name="change-inspector" method="post"
                              action="${pageContext.request.contextPath}/taxreturn/change-user-inspector?id=${changeInspector.getId()}">
                            <tr>
                                <td class="fa-w-20"><span class="categories ol1"><c:out
                                        value="${changeInspector.getUserId()}"/></span></td>
                                <td class="fa-w-20"><span class="categories col1"><c:out
                                        value="${changeInspector.getPreviousInspectorId()}"/></span>
                                </td>
                                <td class="fa-w-20"><span class="categories col1">
                                <label>
                                    <select name="newInspectorId" id="newInspectorId">
                                         <c:forEach items="${inspectorList}" var="inspector">
                                             <c:if test="${inspector ne changeInspector.getPreviousInspectorId()}">
                                                 <option><c:out value="${inspector}"/></option>
                                                 <%--//TODO попытаться кидать в request, а не сессию--%>
                                             </c:if>
                                         </c:forEach>
                                     </select>
                                </label>
                            </span>
                                </td>
                                <td class="fa-w-20"><span class="categories col1"><c:out
                                        value="${changeInspector.getMessage()}"/></span>
                                </td>
                                <td class="fa-w-20"><span class="categories col1">
                                    <c:set var="pattern"><fmt:message key="common.time" bundle="${link}"/></c:set>
                                    <fmt:parseDate value="${ changeInspector.getDate() }" pattern="yyyy-MM-dd'T'HH:mm"
                                                   var="parsedDateTime" type="both"/>
                                    <fmt:formatDate pattern="${pattern}" value="${ parsedDateTime }"/>
                                </span>
                                </td>
                                <c:set value="${changeInspector.getUserId()}" var="userId" scope="request"/>
                                <td>
                                    <li class="list-inline-item list-inline justify-content-center">

                                        <button type="submit" class="btn btn-">
                                            <a name="a" data-toggle="tooltip" data-placement="top"
                                               title="<fmt:message key="common.approve" bundle="${link}"/>"
                                               class="view">
                                                <i class="fa fa-check"></i>
                                            </a>
                                        </button>

                                    </li>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </section>
</c:if>


<c:if test="${empty changeInspectorList}">
    <section class="overly  section-sm" style="margin-top:20vh">
        <!-- Container Start -->
        <div class="container">
            <div class="row justify-content-md-center text-center">
                <div class="col-md-8">
                    <p style="font-size: 5em"><fmt:message key="admin.change.request" bundle="${link}"/></p>
                </div>
            </div>
        </div>
        <!-- Container End -->
    </section>
</c:if>

<jsp:include page="${pageContext.request.contextPath}/footer.jsp"/>
</body>
</html>




