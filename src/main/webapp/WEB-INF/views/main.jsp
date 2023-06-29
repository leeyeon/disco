<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>



<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Login</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>

</head>

<body class="bg-gradient-primary-main">

    <script>
        $(document).ready(function() {
            $("#disco-module-onoff").on("click", function() {
                $("#disco-module").toggle();
            });
        });
    </script>

    <div class="container">

        <div class="row" style="margin-top: 38px; margin-left: 270px;">
            <a href="#" class="btn btn-info btn-circle" id="disco-module-onoff">
                <i class="fas fa-info-circle"></i>
            </a>
        </div>

        <!-- Outer Row  -->
        <div class="row justify-content-center" id="disco-module" style="display:none;">

            <div class="col-xl-10 col-lg-12 col-md-9">
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">MY PICK</h1>
                                    </div>

                                    <hr>

                                    <div class="row mb-2">
                                        <div class="col-xl-12 col-md-12">
                                            <div class="card border-left-warning shadow h-100">
                                                <div class="card-body">
                                                    <div class="row no-gutters align-items-center">
                                                        <div class="col mr-2">
                                                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                                여성, ~30,000원, 30대, 캐쥬얼/심플</div>
                                                            <div class="h5 mb-0 font-weight-bold text-gray-800">30만원대 캐쥬얼룩</div>
                                                        </div>
                                                        <div class="col-auto">
                                                            <a href="#" class="btn btn-danger btn-circle">
                                                                <i class="fas fa-trash"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="btn col-xl-12 col-md-12">
                                            <div class="card shadow h-100">
                                                <div class="card-body">
                                                    <div class="col text-center">
                                                        <i class="fa fa-plus"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

</body>

</html>