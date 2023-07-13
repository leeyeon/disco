<!DOCTYPE html>
<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>MY PICK 상세 조회 화면</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
</head>
<script>

     function showProduct(productCd, productName) {
         if (confirm("선택하신 [" + productName + "] 로/으로 바로 가볼까요?")) {
             var link = "https://www.thehyundai.com/front/pda/itemPtc.thd?slitmCd=" + productCd + "&sectId=&bfp=SearchList";
             window.open(link, "_blank");
         } else {
             alert("취소하였습니다");
         }
     }

     function showDetailProduct(productCd) {
        $("#productImage").removeClass("bg-login-image");
        var secondLastChar = productCd.substring(productCd.length - 2, productCd.length - 1);
        var thirdLastChar = productCd.substring(productCd.length - 3, productCd.length - 2);
        var fourthLastChar = productCd.substring(productCd.length - 4, productCd.length - 3);
        var fifthAndSixthChar = productCd.substring(4, 6);
        var eightAndNineChar = productCd.substring(2, 4);

        $("#productImage").css({
          "background":
            'url("https://image.thehyundai.com/static/' + secondLastChar + '/'+ thirdLastChar +'/' +fourthLastChar+'/'+ fifthAndSixthChar +'/'+ eightAndNineChar +'/'+ productCd +'_0_600.jpg")',
          "background-position": "center",
          "background-size": "cover"
        });
     }

    function reProduct(productDTOList) {
      if (confirm("재추천 받아보시겠어요?")) {
        $.ajax({
          type: "GET",
          url: "/reCommand/list",
          success: function (data) {
              reGPT(data);
          },
          error: function (xhr, status, error) {
            // 요청 실패 시 처리
            console.log(xhr.responseText);
            alert("서버 요청에 실패했습니다");
          }
        });
      } else {
        alert("취소하였습니다");
      }
    }

    function reGPT(data) {
        $.ajax({
            type: "POST",
            url: "/reGPT/pick",
            data: {
                "productDTOJson": JSON.stringify(data)
            },
            success: function (data) {
                location.reload(); // 페이지 새로고침
            }
        });
    }

</script>
<body>
    <div class="container">
        <!-- Outer Row  -->
        <div class="row justify-content-center" id="disco-module">
            <div class="col-xl-10 col-lg-12 col-md-9">
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0" style="height: 600px;">
                        <!-- Nested Row within Card Body -->
                        <div class="row" style="height: 600px;">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image" id="productImage"></div>
                            <div class="col-lg-6">
                                <div class="p-5" style="height: 600px; overflow-y: scroll;">
                                   <div class="text-center">
                                       <div class="col-auto" style="display: flex; justify-content: space-between; align-items: center;">
                                           <div class="col-auto" style="margin: 0 auto;">
                                               <h1 class="h4 text-gray-600 mb-4">MY PICK RESULT</h1>
                                           </div>
                                           <div class="col-auto" onClick="reProduct('${productDTOList}');">
                                               <a href="#" class="btn btn-primary btn-circle">
                                                   <i class="fas fa-search fa-fw"></i>
                                               </a>
                                           </div>
                                       </div>
                                       <div class="h1 text-gray-800 text-xs">
                                           ${productDTOList[0].userId} 님의 "${productDTOList[0].pickNm}" 스타일을 큐레이팅 한 결과입니다
                                       </div>
                                   </div>
                                    <hr>
                                    <div class="row">
                                        <div class="btn col-xl-12 col-md-12">
                                            <div class="card shadow h-100">
                                                <div class="card-body">
                                                    <div class="col text-left">
                                                        <c:forEach var="product" items="${productDTOList}" varStatus="status">
                                                            <div class="row mb-2" id="product${product.productCd}">
                                                                <div class="col-xl-12 col-md-12">
                                                                    <c:if test="${status.index % 2 eq 0}">
                                                                        <div class="card border-left-warning shadow h-100">
                                                                    </c:if>
                                                                    <c:if test="${status.index % 2 eq 1}">
                                                                        <div class="card border-left-dark shadow h-100">
                                                                    </c:if>
                                                                        <div class="card-body">
                                                                            <div class="row no-gutters ">
                                                                                <div class="col mr-2" style="cursor: pointer;" onClick="showProduct('${product.productCd}','${product.productName}');">
                                                                                    <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                                                        #${product.division} #${product.brndNm} <br> #${product.price}원 #${product.pickNm}
                                                                                    </div>
                                                                                    <div class="h5 mb-1 font-weight-bold text-gray-800" style="font-size: 18px;">${product.productName}</div>
                                                                                </div>
                                                                                <br><br><br><br><br>
                                                                                <div class="col-auto mt-auto mb-2" onClick="showDetailProduct('${product.productCd}');" style="position: absolute; bottom: 0; left: 50;">
                                                                                    <div class="h6 text-gray-600 text-xs"><u>사진 보기</u></div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
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
