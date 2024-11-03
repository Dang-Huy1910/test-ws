<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Category Portfolio</title>
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
                <h3 class="text-center">List of Categories</h3>
                <div class="text-end mb-3">
                    <button type="button" class="btn btn-primary" onclick="window.location.href = '/Workshop/addCategory';">
                        Add New Category
                    </button>
                </div>
                <form method="get" action="/Workshop/category" class="d-flex justify-content-end mb-3">
                    <input type="text" name="search" placeholder="Search categories" value="${search}" class="form-control me-2">
                    <button type="submit" class="btn btn-primary">Search</button>
                </form>
                <div class="row">
                    <c:if test="${not empty requestScope.list}">
                        <c:forEach items="${requestScope.list}" var="category">
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="card-title">Category: ${category.categoryName}</h5>
                                    </div>
                                    <div class="card-body">
                                        <p><strong>Type ID:</strong> ${category.typeId}</p>
                                        <p><strong>Memo:</strong> ${category.memo}</p>
                                    </div>
                                    <div class="card-footer text-end">
                                        <button type="button" class="btn btn-info btn-custom" 
                                                onclick="window.location.href = '/Workshop/updateCategory?id=${category.typeId}'">
                                            Update
                                        </button>
                                        <button type="button" class="btn btn-danger btn-custom" value="${category.typeId}">
                                            Delete
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty requestScope.list}">
                        <div class="col-12">
                            <div class="alert alert-warning text-center" role="alert">
                                No categories found matching your search.
                            </div>
                        </div>
                    </c:if>
                </div>
                <nav>
                    <ul class="pagination justify-content-center mt-4">
                        <c:if test="${page > 1}">
                            <li class="page-item"><a class="page-link" href="?page=${page - 1}&search=${search}">Previous</a></li>
                        </c:if>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == page ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}&search=${search}">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${page < totalPages}">
                            <li class="page-item"><a class="page-link" href="?page=${page + 1}&search=${search}">Next</a></li>
                        </c:if>
                    </ul>
                </nav>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
            <script>
                document.querySelectorAll('.btn-danger').forEach(function (button) {
                    button.addEventListener('click', function () {
                        var typeId = this.getAttribute('value');

                        Swal.fire({
                            title: "Are you sure you want to delete this category?",
                            text: "This will permanently delete the category with ID: " + typeId + "!",
                            icon: "warning",
                            showCancelButton: true,
                            confirmButtonText: "Yes, delete it!",
                            cancelButtonText: "No, cancel!"
                        }).then((result) => {
                            if (result.isConfirmed) {
                                deleteCategory(typeId);
                            } else {
                                Swal.fire("Category deletion canceled.");
                            }
                        });
                    });
                });

                function deleteCategory(typeId) {
                    fetch('/Workshop/delete', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: 'id=' + typeId + '&action=category'
                    })
                    .then(response => {
                        if (response.ok) {
                            Swal.fire("Category deleted successfully!").then(() => {
                                location.reload();
                            });
                        } else {
                            Swal.fire("Failed to delete category. Please try again later.");
                        }
                    })
                    .catch(error => {
                        console.error('Error deleting category:', error);
                        Swal.fire("An error occurred while deleting the category. Please try again later.");
                    });
                }
            </script>
    </body>
</html>
