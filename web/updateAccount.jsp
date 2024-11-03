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
                    <h2>UPDADTE ACCOUNT</h2>
                <c:if test="${requestScope.error != null}">
                    <div class="alert alert-danger" role="alert">
                        ${requestScope.error}
                    </div>
                </c:if>
                <form action="updateAccount" method="POST" id="create-account-form">
                    <div class="mb-3">
                        <label for="account" class="form-label">Account</label>
                        <input type="text" class="form-control" id="account" name="account" readonly="true" value="${account.account}">
                    </div>
                    <div class="mb-3">
                        <label for="account" class="form-label">Password</label>
                        <input type="password" class="form-control" id="pass" name="pass" required value="${account.pass}">
                    </div>
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" required value="${account.firstName}">
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" required value="${account.lastName}">
                    </div>
                    <div class="mb-3">
                        <label for="birthday" class="form-label">Birthday</label>
                        <input type="date" class="form-control" id="birthday" name="birthday" required value="${account.birthday}">
                    </div>
                    <div class="mb-3">
                        <label for="gender" class="form-label">Gender</label>
                        <select class="form-select" id="gender" name="gender" required>
                            <option value="">Choose</option>
                            <option <c:if test="${account.gender==true}"> selected </c:if> value="1">Male</option>
                            <option value="0" <c:if test="${account.gender!=true}"> selected </c:if> >Female</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone</label>
                        <input type="tel" class="form-control" id="phone" name="phone" required value="${account.phone}">
                    </div>
                    <div class="mb-3">
                        <label for="role" class="form-label">Role in System</label>
                        <select class="form-select" id="role" name="roleInSystem" required >
                            <option value="1" <c:if test="${account.roleInSystem==1}"> selected </c:if>>ADMIN</option>
                            <option value="2"<c:if test="${account.roleInSystem!=1}"> selected </c:if>> STAFF</option>
                        </select>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="isUse" id="isUse" <c:if test="${account.isUse}">checked</c:if> >
                        <label class="form-check-label" for="isUse">
                            Active
                        </label>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <button type="button" class="btn btn-primary"  onclick="history.back();">Cancel</button>
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
