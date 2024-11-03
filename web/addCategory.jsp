<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create New Product</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container mt-5">
                <h3>CREATE NEW CATEGORY</h3>
            <c:if test="${requestScope.error != null}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.error}
                </div>
            </c:if>
            <form action="addCategory" method="post">
                <div class="mb-3">
                    <label for="productId" class="form-label">Category Name</label>
                    <input type="text" class="form-control" id="productId" name="categoryName" required>
                </div>
                <div class="mb-3">
                    <label for="productName" class="form-label">Memo </label>
                    <input type="text" class="form-control" id="productName" name="memo">
                </div>
                <button type="submit" class="btn btn-primary">Create Category</button>
                <button type="button" class="btn btn-secondary" onclick="window.location.href = '/Workshop/category'">Cancel</button>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.umd.min.js"></script>
    </body>
</html>
