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
        <link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">

    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <div class="container mt-5">
            <div class="container mt-4">
                <h2>ADD NEW ACCOUNT</h2>
                <c:if test="${requestScope.error != null}">
                    <div class="alert alert-danger" role="alert">
                        ${requestScope.error}
                    </div>
                </c:if>
                <form action="AddAccount" method="POST" id="create-account-form">
                    <div class="mb-3 row">
                        <label for="account" class="form-label col-md-3">Account</label>
                        <input type="text" class="form-control col-md-9" id="account" name="account" required>
                    </div>
                    <div class="mb-3 row">
                        <label for="account" class="form-labe col-md-3">Password</label>
                        <input type="password" class="form-control col-md-9" id="pass" name="pass" required>
                    </div>
                    <div class="mb-3 row">
                        <label for="firstName" class="form-label col-md-3">First Name</label>
                        <input type="text" class="form-control col-md-9" id="firstName" name="firstName" required>
                    </div>
                    <div class="mb-3 row">
                        <label for="lastName" class="form-label col-md-3">Last Name</label>
                        <input type="text" class="form-control col-md-9" id="lastName" name="lastName" required>
                    </div>
                    <div class="mb-3 row">
                        <label for="birthday" class="form-label col-md-3">Birthday</label>
                        <input type="date" class="form-control col-md-9" id="birthday" name="birthday" required>
                    </div>
                    <div class="mb-3 row">
                        <label for="gender" class="form-label col-md-3">Gender</label>
                        <select class="form-select col-md-9" id="gender" name="gender" required>
                            <option value="">Choose</option>
                            <option value="1">Male</option>
                            <option value="0">Female</option>
                        </select>
                    </div>
                    <div class="mb-3 row">
                        <label for="phone" class="form-label col-md-3">Phone</label>
                        <input type="tel" class="form-control col-md-9" id="phone" name="phone" required>
                    </div>
                    <div class="mb-3 row">
                        <label for="role" class="form-label col-md-3">Role in System</label>
                        <select class="form-select col-md-9" id="role" name="roleInSystem" required>
                            <option value="1">ADMIN</option>
                            <option value="2">STAFF</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.umd.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script>
            var deleteBtn = document.querySelectorAll('.btn-danger');
            deleteBtn.forEach(function (button) {
                button.addEventListener('click', function () {
                    // L?y giá tr? value c?a nút
                    var userId = this.getAttribute('value');

                    swal({
                        title: "Are you sure for DELETE?",
                        text: "This will unban the user with UserName: " + userId + "!",
                        icon: "warning",
                        buttons: true,
                        dangerMode: true,
                    })
                            .then((willBan) => {
                                if (willBan) {
                                    unbanUser(userId);
                                } else {
                                    swal("Don't unban account");
                                }
                            });
                });
            });
            function unbanUser(userId) {
                // Send POST request to Servlet
                fetch('/delete', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'userId=' + encodeURIComponent(userId) + '&action=account'
                })
                        .then(response => {
                            if (response.ok) {
                                swal("User delete successfully!").then(() => {
                                    // Reload the page
                                    location.reload();
                                });
                            } else {
                                swal("Failed to unban user. Please try again later.");
                            }
                        })
                        .catch(error => {
                            console.error('Error unbanning user:', error);
                            swal("An error occurred while unbanning the user. Please try again later.");
                        });
            }
        </script>
    </body>
</html>
