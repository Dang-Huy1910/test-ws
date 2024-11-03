<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <h3 class="text-center">List Accounts in System</h3>
        <div class="text-end mb-3">
            <button type="button" class="btn btn-primary" onclick="window.location.href='/Workshop/AddAccount';">
                Add New Product                    
            </button>
        </div>
        
        <div class="row">
            <c:forEach items="${requestScope.list}" var="account">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="card-title">${account.firstName} ${account.lastName}</h5>
                        </div>
                        <div class="card-body">
                            <p><strong>Account:</strong> ${account.account}</p>
                            <p><strong>Birthday:</strong> ${account.birthday}</p>
                            <p><strong>Gender:</strong> ${account.gender ? "Male" : "Female"}</p>
                            <p><strong>Phone:</strong> ${account.phone}</p>
                            <p><strong>Role:</strong> ${account.roleInSystem == 1 ? "ADMIN" : "STAFF"}</p>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-light btn-custom" onclick="window.location.href='/Workshop/updateAccount?id=${account.account}'">UPDATE</button>
                            <c:if test="${account.isUse}">
                                <button type="button" class="btn btn-success btn-custom">ACTIVE</button>
                            </c:if>
                            <c:if test="${!account.isUse}">
                                <button type="button" class="btn btn-warning btn-custom">DEACTIVE</button>
                            </c:if>
                            <button type="button" class="btn btn-danger btn-custom">DELETE</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        document.querySelectorAll('.btn-danger').forEach(function (button) {
            button.addEventListener('click', function () {
                var userId = this.closest('.card').querySelector('.card-title').innerText;

                Swal.fire({
                    title: "Are you sure you want to DELETE?",
                    text: "This will remove the account for " + userId + "!",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonText: "Yes, delete it!",
                    cancelButtonText: "No, cancel!"
                }).then((result) => {
                    if (result.isConfirmed) {
                        deleteAccount(userId);
                    }
                });
            });
        });

        function deleteAccount(userId) {
            // Send POST request to Servlet
            fetch('/Workshop/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'id=' + userId + '&action=account'
            })
            .then(response => {
                if (response.ok) {
                    Swal.fire("Deleted!", "User deleted successfully!", "success").then(() => {
                        location.reload();
                    });
                } else {
                    Swal.fire("Failed!", "Please try again later.", "error");
                }
            })
            .catch(error => {
                console.error('Error deleting user:', error);
                Swal.fire("Error!", "An error occurred while deleting the user. Please try again later.", "error");
            });
        }
    </script>
</body>
</html>
