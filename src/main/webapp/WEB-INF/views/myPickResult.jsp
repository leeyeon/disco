<!DOCTYPE html>
<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">

     <style>
      /* Your existing styles ... */

         /* 추가: 오버레이 스타일 */
         #loading-overlay {
             position: fixed;
             top: 0;
             left: 0;
             width: 100%;
             height: 100%;
             background-color: rgba(0, 0, 0, 0.7); /* Adjust the transparency as needed */
             z-index: 9998; /* Make sure the overlay appears below the loading message and bar */
             display: none; /* Hide the overlay by default */
         }

         /* 추가: 로딩 바 메시지 스타일 */
         #loading-message {
             position: fixed;
             top: 50%;
             left: 50%;
             transform: translate(-50%, -50%);
             z-index: 9999;
             display: none; /* Hide the loading message by default */
         }

         /* 추가: 로딩 바 스타일 */
         #loading-bar {
             position: fixed;
             top: 55%; /* Adjust the position as needed */
             left: 0;
             width: 100%;
             height: 4px;
             background-color: #ddd;
             z-index: 9999; /* Make sure the loading bar appears on top of the overlay */
             display: none; /* Hide the loading bar by default */
         }

         #loading-bar .progress-bar {
             height: 100%;
             background-color: #4e73df;
             animation: loading 2s linear infinite;
         }

         @keyframes loading {
             0% { width: 0; }
             50% { width: 50%; }
         }

         @keyframes blink {
             0% {
                 opacity: 0.2;
             }
             50% {
                 opacity: 1;
             }
             100% {
                 opacity: 0.2;
             }
         }

         .blink-message {
             animation: blink 2s linear infinite;
         }

         /* CSS 코드 */
         .carousel-control-prev,
         .carousel-control-next {
           width: 50px; /* 너비 조정 */
         }

         /* 아이콘 색상 설정 (예시로 빨간색으로 설정) */
         .carousel-control-prev-icon {
           background-image: url('previous-icon-url.png'); /* 이전 아이콘 이미지 URL 입력 */
         }

         .carousel-control-next-icon {
           background-image: url('next-icon-url.png'); /* 다음 아이콘 이미지 URL 입력 */
         }

     </style>
</head>

<body>

    <input type="hidden" id="recommendProductCnt" value="${fn:length(productDTOList)}" />

    <div class="container">
        <!-- Outer Row  -->
        <div class="row justify-content-center" id="disco-module">
            <div class="col-xl-10 col-lg-12 col-md-9">
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0" style="height: 600px;">
                        <!-- Nested Row within Card Body -->
                        <div class="p-5">
                            <div class="text-center">
                                <div class="col-auto" style="display: flex; justify-content: space-between; align-items: center;">
                                    <div class="col-auto" style="margin: 0 auto;">
                                        <h1 style="margin-left:50px;" class="h4 text-gray-600 mb-4">MY PICK RESULT</h1>
                                    </div>
                                    <div class="col-auto" onClick="reProduct();">
                                        <a href="#" class="btn btn-primary btn-circle">
                                            <i class="fas fa-search fa-fw"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="h1 text-gray-800 text-xs">
                                    ${productDTOList[0].userId} 님의 스타일을 큐레이팅 한 결과입니다
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-xl-12 col-md-12">
                                    <div id="productCarousel" class="carousel slide" data-ride="carousel">
                                        <div class="carousel-inner">
                                            <c:forEach var="product" items="${productDTOList}" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${status.index % 2 eq 0}">
                                                        <div class="carousel-item${status.index eq 0 ? ' active' : ''}">
                                                            <div class="row">
                                                    </c:when>
                                                </c:choose>
                                                <div class="col-xl-4 col-md-6 mb-2" id="product${product.productCd}">
                                                    <div class="card border-left-warning shadow h-100">
                                                        <div class="card-body">
                                                            <div class="row no-gutters">
                                                                <div class="col-4 mr-2" style="cursor: pointer;" onClick="showProduct('${product.productCd}','${product.productName}');">
                                                                    <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                                        #${product.brndNm} <br>
                                                                        #${product.price}원
                                                                    </div>
                                                                    <div class="h4 mb-1 font-weight-bold text-gray-800" style="font-size: 18px;"> ${product.productName} </div>
                                                                </div>
                                                                <div class="col-6 d-none d-md-block bg-login-image" style="height: 300px; background-size: contain;" id="productImage${product.productCd}" imageProduct="${product.productCd}"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <c:if test="${status.index % 2 eq 1 or status.last}">
                                                    </div>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                        <a class="carousel-control-prev" href="#productCarousel" role="button" data-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Previous</span>
                                        </a>
                                        <a class="carousel-control-next" href="#productCarousel" role="button" data-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Next</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="text-center mt-5">
                                <h7>Digital Special Coordination</h7>
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

    <!-- 추가: 로딩 바 메시지 -->
    <div id="loading-message" class="text-center" style="display: none;">
        <h5>gpt가 당신의 스타일을 추천중입니다</h5>
    </div>

    <!-- 추가: 로딩 바 및 어두운 배경을 감싸는 오버레이 -->
    <div id="loading-overlay" style="display: none;">
        <div id="loading-bar" class="progress">
            <div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div>
        </div>
    </div>


    <script>
        $(document).ready(function() {
            setImage();

            makeMessageBlink();
        });

        function setImage() {
            $('[imageProduct]').each(function() {
                var productCd = $(this).attr('imageProduct');
                var secondLastChar = productCd.substring(productCd.length - 2, productCd.length - 1);
                var thirdLastChar = productCd.substring(productCd.length - 3, productCd.length - 2);
                var fourthLastChar = productCd.substring(productCd.length - 4, productCd.length - 3);
                var fifthAndSixthChar = productCd.substring(4, 6);
                var eightAndNineChar = productCd.substring(2, 4);

                $(this).css({
                    "background":
                        'url("https://image.thehyundai.com/static/' + secondLastChar + '/' + thirdLastChar + '/' + fourthLastChar + '/' + fifthAndSixthChar + '/' + eightAndNineChar + '/' + productCd + '_0_600.jpg")',
                    "background-position": "center",
                    "background-size": "cover"
                });
            });
        }

        // 메세지를 반짝거리게 만드는 함수
        function makeMessageBlink() {
            var messageElement = $("#loading-message");
            messageElement.addClass("blink-message");
        }

        function showProduct(productCd, productName) {
            if (confirm("선택하신 [" + productName + "] 로/으로 바로 가볼까요?")) {
                var link = "https://www.thehyundai.com/front/pda/itemPtc.thd?slitmCd=" + productCd + "&sectId=&bfp=SearchList";
                window.open(link, "_blank");
            } else {
                alert("취소하였습니다");
            }
        }

        function reProduct() {
            if (confirm("추가로 추천을 받아보시겠어요?")) {
                // 텍스트 메시지를 보여주고 로딩 바를 표시
                $("#loading-overlay").fadeIn();
                $("#loading-message").fadeIn();
                $("#loading-bar").fadeIn();

                var result = {
                    "pickCd" : ${pickCd},
                    "recommendProductCnt" : $("#recommendProductCnt").val()
                };

                $.ajax({
                    type: "POST",
                    url: "/openai/create-recommend-product",
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify(result),
                    success: function (data) {
                        // 데이터를 성공적으로 받아왔을 때 처리 로직
                        if (data && data.length > 0) {
                            // 새로운 상품 정보를 기존 상품 목록에 추가
                            var newProductHtml = '';
                            for (var i = 0; i < data.length; i += 2) {
                                newProductHtml += `
                                    <div class="carousel-item">
                                        <div class="row">
                                `;
                                for (var j = i; j < i + 2 && j < data.length; j++) {
                                    var product = data[j];
                                    var title = '#'+product.brndNm+' <br> #'+product.price+'원';

                                    newProductHtml += '<div class="col-xl-4 col-md-6 mb-2" id="product'+product.productCd+'">'
                                        + '<div class="card border-left-warning shadow h-100">'
                                        + '       <div class="card-body">'
                                        + '           <div class="row no-gutters">'
                                        + '              <div class="col-4 mr-2" style="cursor: pointer;"'
                                        + "onClick=\"showProduct('" + product.productCd + "','" + product.productName + "');\""
                                        + '> <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">'+title;
                                    newProductHtml += `     </div>
                                                            <div class="h5 mb-1 font-weight-bold text-gray-800" style="font-size: 18px;">`+product.productName+`</div>
                                                        </div>
                                                        <div class="col-6 d-none d-md-block bg-login-image" style="height: 300px; background-size: contain;"`
                                                   + 'id="productImage'+product.productCd+'" imageProduct="'+product.productCd+'"></div>';
                                    newProductHtml += `
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    `;
                                }
                                newProductHtml += `
                                        </div>
                                    </div>
                                `;
                            }

                            $("#productCarousel .carousel-inner").append(newProductHtml);

                            setImage(); // 더현대이미지 세팅

                            // 기존 Carousel 갱신
                            $("#productCarousel").carousel("next");

                            var cnt = Number($("#recommendProductCnt").val()) + 2;

                            $("#recommendProductCnt").val(cnt);

                            // 로딩 바를 숨김
                            $("#loading-overlay").fadeOut();
                            $("#loading-message").fadeOut();
                            $("#loading-bar").fadeOut();

                        }
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

    </script>


</body>

</html>