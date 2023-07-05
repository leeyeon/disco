<!DOCTYPE html>
<%@ page  isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

    <style>

    /*popup*/
    .popup_layer {position:fixed;top:0;left:0;z-index: 10000; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.4); }
    /*팝업 박스*/
    .popup_box{position: relative;top:50%;left:50%; overflow: auto; height: 600px; width:500px;transform:translate(-50%, -50%);z-index:1002;box-sizing:border-box;background:#fff;box-shadow: 2px 5px 10px 0px rgba(0,0,0,0.35);-webkit-box-shadow: 2px 5px 10px 0px rgba(0,0,0,0.35);-moz-box-shadow: 2px 5px 10px 0px rgba(0,0,0,0.35);}
    /*컨텐츠 영역*/
    .popup_box .popup_cont {padding:50px;line-height:1.4rem;font-size:14px; }
    .popup_box .popup_cont h2 {padding:15px 0;color:#333;margin:0;}
    .popup_box .popup_cont p{ border-top: 1px solid #666;padding-top: 30px;}
    /*버튼영역*/
    .popup_box .popup_btn {display:table;table-layout: fixed;width:100%;height:70px;background:#ECECEC;word-break: break-word;}
    .popup_box .popup_btn a {position: relative; display: table-cell; height:70px;  font-size:17px;text-align:center;vertical-align:middle;text-decoration:none; background:#ECECEC;}
    .popup_box .popup_btn a:before{content:'';display:block;position:absolute;top:26px;right:29px;width:1px;height:21px;background:#fff;-moz-transform: rotate(-45deg); -webkit-transform: rotate(-45deg); -ms-transform: rotate(-45deg); -o-transform: rotate(-45deg); transform: rotate(-45deg);}
    .popup_box .popup_btn a:after{content:'';display:block;position:absolute;top:26px;right:29px;width:1px;height:21px;background:#fff;-moz-transform: rotate(45deg); -webkit-transform: rotate(45deg); -ms-transform: rotate(45deg); -o-transform: rotate(45deg); transform: rotate(45deg);}
    .popup_box .popup_btn a.close_day {background:#5d5d5d;}
    .popup_box .popup_btn a.close_day:before, .popup_box .popup_btn a.close_day:after{display:none;}
    /*오버레이 뒷배경*/
    .popup_overlay{position:fixed;top:0px;right:0;left:0;bottom:0;z-index:1001;;background:rgba(0,0,0,0.5);}
    /*popup*/

    </style>

</head>

<body class="bg-gradient-primary-main">

    <script>
        $(document).ready(function() {
            $("#disco-module-onoff").on("click", function() {
                $("#disco-module").toggle();
            });

            $("#addCard").on("click", function() {
                $("#add-card-module").toggle();
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
                    <div class="card-body p-0" style="height: 600px;">
                        <!-- Nested Row within Card Body -->
                        <div class="row" style="height: 600px;">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5" style="height: 600px; overflow-y: scroll;">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">MY PICK</h1>
                                    </div>

                                    <hr>

                                    <!-- [S] 사용자별 PICK 목록 -->
                                    <c:forEach var="pick" items="${pickDTOList}">
                                        <div class="row mb-2">
                                            <div class="col-xl-12 col-md-12">
                                                <div class="card border-left-warning shadow h-100">
                                                    <div class="card-body">
                                                        <div class="row no-gutters align-items-center">
                                                            <div class="col mr-2">
                                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                                    ${pick.sexNm}, ${pick.totalAmt}, ${pick.style}</div>
                                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${pick.pickNm}</div>
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
                                    </c:forEach>

                                    <!-- [E] 사용자별 PICK 목록 -->

                                    <div class="row">
                                        <div class="btn col-xl-12 col-md-12">
                                            <div class="card shadow h-100">
                                                <div class="card-body" id="addCard">
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


    <div class="popup_layer" id="add-card-module" style="display: none;">


      <div class="popup_box">
          <div style="height: 10px; width: 375px; float: top;">
          </div>
          <!--팝업 컨텐츠 영역-->
          <div class="popup_cont">
              <h5> POPUP TILTE</h5>
              <p>
              !!!!!!!!!!!!!!!!!!!!<br>
                  ~~~~~~~~~~~~~~~~~
                  @@@@@@@@@@@@@@@@@@@
                  %%%%%%%%%%%%%%%%
                  ^^^^^^^^^^^^^^^^
                  &&&&&&&&&&&&&&
                  *************
                  ((((((((((((((((

              </p>

          </div>
          <!--팝업 버튼 영역-->
          <div class="popup_btn" style="float: bottom; margin-top: 250px;">
              저장하기 삭제하기 버튼 있을 곳
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