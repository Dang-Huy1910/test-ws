<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update Product</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container mt-5">
                <h3>UPDATE PRODUCT</h3>
            <c:if test="${requestScope.error != null}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.error}
                </div>
            </c:if>
                <form action="updateProduct" method="POST" enctype="multipart/form-data">
                    
                <div class="mb-3">
                    <label for="image" class="form-label">Product Image</label>
                    <!-- Display the current image -->
                    <div>
                        <img src="${pageContext.request.contextPath}${product.productImage}" alt="Current Product Image" class="img-thumbnail" style="max-width: 200px; max-height: 200px;">
                    </div>
                    <label for="image" class="form-label">Product Image</label>
                    <input type="file" class="form-control" id="image" name="image" accept="image/*">
                    <small class="form-text text-muted">Leave blank to keep the current image.</small>
                </div>
                <div class="mb-3">
                    <label for="productId" class="form-label">Product ID</label>
                    <input type="text" class="form-control" id="productId" name="productId" value="${product.productId}" readonly>
                </div>
                <div class="mb-3">
                    <label for="productName" class="form-label">Product Name</label>
                    <input type="text" class="form-control" id="productName" name="productName" value="${product.productName}" required>
                </div>
                <div class="mb-3">
                    <label for="brief" class="form-label">Brief Description</label>
                    <textarea class="form-control" id="brief" name="brief" rows="3" required>${product.brief}</textarea>
                </div>
                <div class="mb-3">
                    <label for="postedDate" class="form-label">Posted Date</label>
                    <input type="date" class="form-control" id="postedDate" name="postedDate" value="${product.postedDate}" required>
                </div>
                <div class="mb-3">
                    <label for="category" class="form-label">Category</label>
                    <select class="form-select" id="category" name="category" required>
                        <c:forEach items="${cate}" var="cate">
                            <option value="${cate.typeId}" <c:if test="${cate.typeId == product.type.typeId}">selected</c:if>>${cate.categoryName}</option>
                        </c:forEach>                    
                    </select>
                </div>
                <div class="mb-3">
                    <label for="unit" class="form-label">Unit</label>
                    <input type="text" class="form-control" id="unit" name="unit" value="${product.unit}" required>
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Price</label>
                    <input type="number" class="form-control" id="price" name="price" value="${product.price}" required>
                </div>
                <div class="mb-3">
                    <label for="discount" class="form-label">Discount</label>
                    <input type="number" class="form-control" id="discount" name="discount" min="0" max="100" value="${product.discount}" required>
                </div>
                <button type="submit" class="btn btn-primary">Update Product</button>
                <button type="button" class="btn btn-secondary" onclick="window.location.href = '/Workshop/product'">Cancel</button>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
