<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <div class="container mt-5">
        <h3>Update Category</h3>
        <form action="updateCategory" method="post">
            <input type="hidden" name="typeId" value="${category.typeId}"/>
            <div class="mb-3">
                <label for="categoryName" class="form-label">Category Name</label>
                <input type="text" class="form-control" id="categoryName" name="categoryName" value="${category.categoryName}" required>
            </div>
            <div class="mb-3">
                <label for="memo" class="form-label">Memo</label>
                <textarea class="form-control" id="memo" name="memo">${category.memo}</textarea>
            </div>
            <button type="submit" class="btn btn-primary">Update Category</button>
            <button type="button" onclick="window.location.href = '/Workshop/category'" class="btn btn-dark">Cancel</button>
        </form>
                <c:if test="${requestScope.error2 != null &&not empty requestScope.error2}">
                    <div class="alert alert-light mt-3" style="color: red">${error2}</div>
                </c:if>
    </div>
</body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.umd.min.js"></script>
</html>
