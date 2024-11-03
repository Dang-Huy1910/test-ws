<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
%>
<div style="position: sticky; top: 0; z-index: 999;">
    <header class="border-bottom">
        <nav class="navbar navbar-expand-lg navbar-light" style="background: linear-gradient(to bottom, #343a40, #f8f9fa);">
            <!-- Container wrapper -->
            <div class="container">
                <!-- Navbar brand -->
                <div class="navbar-brand text-dark">
                    Welcome to 
                    <span style="color: red;">
                        <c:if test="${sessionScope.account.roleInSystem == 1}">ADMIN</c:if>
                        <c:if test="${sessionScope.account.roleInSystem == 2}">MANAGER</c:if>
                    </span>
                    [${sessionScope.account.lastName} ${sessionScope.account.firstName}]
                </div>

                <!-- Collapsible wrapper -->
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarButtonsExample" aria-controls="navbarButtonsExample" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse justify-content-center" id="navbarButtonsExample">
                    <!-- Left links -->
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link text-dark" href="<%=url%>/account">Account</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-dark" href="<%=url%>/product">Product</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-dark" href="<%=url%>/category">Category</a>
                        </li>
                    </ul>

                    <c:if test="${sessionScope.account != null}">
                        <div class="d-flex align-items-center">
                            <a class="btn btn-outline-danger" href="<%=url%>/logout">Logout</a>
                        </div>
                    </c:if>
                </div>
                <!-- Collapsible wrapper -->
            </div>
            <!-- Container wrapper -->
        </nav>
    </header>
</div>

<!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>-->
