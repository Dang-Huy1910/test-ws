<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "custom" uri = "WEB-INF/custom.tld"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Portfolio</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Lato', sans-serif;
            }
            .card {
                margin-bottom: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .card-header {
                background-color: #007bff;
                color: white;
                border-top-left-radius: 10px;
                border-top-right-radius: 10px;
            }
            .btn-custom {
                margin: 5px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container mt-5">
                <h3 class="text-center">List of Products</h3>
                <div class="text-end mb-3">
                    <button type="button" class="btn btn-primary" onclick="window.location.href = '/Workshop/addProduct';">
                        Add New Product
                    </button>
                </div>
                <!-- Search form -->
                <form method="get" action="/Workshop/product" class="d-flex justify-content-end mb-3">
                    <input type="text" name="search" placeholder="Search products..." value="${param.search}" class="form-control me-2" />
                    <button type="submit" class="btn btn-primary">Search</button>
                </form>

                <div class="row"> 
                    <c:if test="${not empty requestScope.productList}">
                        <c:forEach items="${requestScope.productList}" var="product">
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="card-title">Product: ${product.productName}</h5>
                                    </div>
                                    <div class="card-body">
                                        <!-- Display Product Image -->
                                        <c:if test="${product.productImage != null && !product.productImage.isEmpty()}">
                                            <img src="${pageContext.request.contextPath}${product.productImage}" alt="Image of ${product.productName}" class="img-fluid mb-3" style="max-height: 200px; object-fit: cover; width: 100%;" />
                                        </c:if>

                                        <p><strong>Product ID:</strong> ${product.productId}</p>
                                        <p><strong>Description:</strong> ${product.brief}</p>
                                        <p><strong>Posted Date:</strong> ${product.postedDate}</p>
                                        <p><strong>Category:</strong> ${product.type.categoryName}</p>
                                        <p><strong>Unit:</strong> ${product.unit}</p>
                                        <p><strong>Price:</strong> <custom:priceFormatter price="${product.price}"> </custom:priceFormatter></p>
                                        <p><strong>Discount:</strong> ${product.discount}%</p>
                                    </div>
                                    <div class="card-footer d-flex justify-content-end">
                                        <form action="updateProduct" method="get">
                                            <input type="hidden" name="productId" value="${product.productId}">
                                            <button type="submit" class="btn btn-warning btn-custom">Update</button>
                                        </form>
                                        <button type="button" class="btn btn-danger btn-custom" value="${product.productId}">Delete</button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty requestScope.productList}">
                        <div class="col-12 text-center">
                            <p class="alert alert-warning">No products found. Please try a different search.</p>
                        </div>
                    </c:if>
                </div>

                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="/Workshop/product?page=${i}&search=${param.search}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            document.querySelectorAll('.btn-danger').forEach(function (button) {
                button.addEventListener('click', function () {
                    var productId = this.getAttribute('value');

                    Swal.fire({
                        title: "Are you sure you want to delete this product?",
                        text: "This will permanently delete the product with ID: " + productId + "!",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonText: "Yes, delete it!",
                        cancelButtonText: "No, cancel!"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            deleteProduct(productId);
                        } else {
                            Swal.fire("Product deletion canceled.");
                        }
                    });
                });
            });

            function deleteProduct(productId) {
                fetch('/Workshop/delete', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'id=' + productId + '&action=product'
                })
                .then(response => {
                    if (response.ok) {
                        Swal.fire("Product deleted successfully!").then(() => {
                            location.reload();
                        });
                    } else {
                        Swal.fire("Failed to delete product. Please try again later.");
                    }
                })
                .catch(error => {
                    console.error('Error deleting product:', error);
                    Swal.fire("An error occurred while deleting the product. Please try again later.");
                });
            }
        </script>
    </body>
</html>
