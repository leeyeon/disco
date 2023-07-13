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

    <title>DISCO 구현 화면</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>

    <script>
    var MsgBox = {
        /* Alert */
        Alert: function(msg, okhandler) {
            new Promise((resolve, reject) => {
                $("#msg_popup #btn_confirm").hide();
                $("#msg_popup #btn_alert").show();

                $("#msg_popup #alert_ok").unbind();
                $("#msg_popup .modal-body").html(msg);
                $('#msg_popup').modal('show');

                $("#msg_popup #alert_ok").click(function() {
                    $('#msg_popup').modal('hide');
                });

                $("#msg_popup").on("hidden.bs.modal", function(e) {
                    e.stopPropagation();
                    if(okhandler != null) resolve();
                    else reject();
                });
            }).then(okhandler).catch(function() {});
        },

        /* Confirm */
        Confirm: function(msg, yeshandler, nohandler) {
            new Promise((resolve, reject) => {
                var flag = false;
                $("#msg_popup #btn_alert").hide();
                $("#msg_popup #btn_confirm").show();

                $("#msg_popup #confirm_yes").unbind();
                $("#msg_popup #confirm_no").unbind();
                $("#msg_popup .modal-body").html(msg);
                $('#msg_popup').modal('show');

                $('#msg_popup').on('keypress', function (e) {
                    var keycode = (e.keyCode ? e.keyCode : e.which);
                    if(keycode == '13') {
                        flag = true;
                        $('#msg_popup').modal('hide');
                    }
                });

                $("#msg_popup #confirm_yes").click(function() {
                    flag = true;
                });
                $("#msg_popup #confirm_no").click(function() {
                    flag = false;
                });

                $("#msg_popup").on("hidden.bs.modal", function(e) {
                    e.stopPropagation();
                    if(yeshandler != null && flag == true) resolve(1);
                    else if(nohandler != null && flag == false) resolve(2);
                    else reject();
                });

            }).then(function(value) {
                if(value == 1)      yeshandler();
                else if(value == 2) nohandler();
            }).catch(function() {});
        },
    }
    </script>

</head>
<body class="bg-gradient-primary-main">
    <script>
    var check = new Array();
    var styleList = new Array();
    check = [0,0,0,0,0,0,0,0,0,0,0];
    styleList = ['빈티지','미니멀','비즈니스','캐주얼','레트로','페미닌','러블리','심플','모던','스트릿','클래식'];

        $(document).ready(function() {
            $("#disco-module-onoff").on("click", function() {
                $("#disco-module").toggle();
            });

            $("#addCard").on("click", function() {
                $("#add-card-module").toggle();
            });

            $("#popupClose").on("click", function() {
                $("#add-card-module").hide();
            });

            $("#form_submit").on('click', function(){
                let form_data = $("#pickDetail").serialize();
                var pickStyle = "";
                for(var i = 1; i<12; i++){
                    if(check[i-1] == '1'){
                        pickStyle += styleList[i-1]+',';
                    }
                }
                form_data += '&style='+pickStyle;
                console.log("~~~~~~ : "+form_data);
                s_ajax(form_data);

            });

            $('.btn-2').click(function(){
                 var styleId = $(this).attr("id");
                for(var i=1; i<12; i++){
                 if(check[i-1] == '0' && styleId == i){
                    check[i-1] = "1";
                    $(this).addClass("btn-2-hover");
                 } else if(check[i-1] == '1' && styleId == i){
                    check[i-1] = "0";
                    $(this).removeClass("btn-2-hover");
                 }
                }
            });

        });

        function delPick(pickCd, pickNm) {
            MsgBox.Confirm("선택하신 ["+ pickNm + "] 를/을 삭제하시겠습니까?"
                , function() {
                    $.ajax({
                        type : "POST",
                        url : "/delete/pick",
                        data : {"pickCd" : pickCd},
                        success : function(res){
                            alert(res);
                            $("#pick"+pickCd).remove();
                        },
                        error : function(XMLHttpRequest, textStatus, errorThrown){
                            alert("통신 실패.");
                        }
                    });
                });
        }

        function showPick(pick, pickNm) {
                //json으로 DTO 넘겨주기
                var jsonData = pick.substring(pick.indexOf("(") + 1, pick.lastIndexOf(")")); // 괄호 내의 문자열 추출
                var keyValuePairs = jsonData.split(", "); // 키-값 쌍으로 분리

                var pickDTOJson = {};
                for (var i = 0; i < keyValuePairs.length; i++) {
                  var pair = keyValuePairs[i].split("=");
                  var key = pair[0].trim();
                  var value = pair[1].trim();
                  pickDTOJson[key] = value;
                }

            if (confirm("선택하신 [" + pickNm + "] 를/을 상품 추천을 받아보시겠어요?")) {
                $.ajax({
                    type: "POST",
                    url: "/GPT/pick",
                    data: {
                        "pickDTOJson": JSON.stringify(pickDTOJson)
                    },
                    success: function (data) {
                    openResizableWindow('/myPickResult', 1000, 700);
                    }
                });
            } else {
                alert("취소하였습니다");
            }
        }

        function openResizableWindow(url, width, height) {
            var features = "width=" + width + ",height=" + height + ", resizable=no";
            window.open(url, "_blank", features);
        }

         function getTotalsSum() {
             var sum2 = parseInt(0);
             var topWearAmt = $("#topWear").val();
             var bottomAmt = $("#bottom").val();
             var shoseAmt = $("#shose").val();

             sum2 = Number(topWearAmt) + Number(bottomAmt) + Number(shoseAmt);
             console.log(sum2);
             $("#totalSum").val(sum2);
         }
         function s_ajax(form_data) {
              $.ajax({
                 url: "insertPick",
                 type: "POST",
                 data: form_data,
                 success: function(data){
                    alert("저장되었습니다.");
                 },
                 error: function (request, status, error){
                    alert("저장에 실패하였습니다.");
                 }
             });
         }

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
                                        <div class="h6 text-gray-600 text-xs">
                                            'PICK' 하시면 고객님 취향저격 상품을 추천해드려요♥
                                        </div>
                                    </div>

                                    <hr>

                                    <!-- [S] 사용자별 PICK 목록 -->
                                    <c:forEach var="pick" items="${pickDTOList}" varStatus="status">
                                        <div class="row mb-2" id="pick${pick.pickCd}">
                                            <div class="col-xl-12 col-md-12">
                                                <c:if test="${status.index % 2 eq 0}">
                                                    <div class="card border-left-warning shadow h-100">
                                                </c:if>
                                                <c:if test="${status.index % 2 eq 1}">
                                                    <div class="card border-left-dark shadow h-100">
                                                </c:if>
                                                    <div class="card-body">
                                                        <div class="row no-gutters align-items-center">
                                                            <div class="col mr-2" style="cursor: pointer;" onClick="showPick('${pick}','${pick.pickNm}');">
                                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                                    ${pick.sexNm}, ${pick.style}, 예산 ${pick.totalAmt}원
                                                                </div>
                                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${pick.pickNm}</div>
                                                            </div>
                                                            <div class="col-auto" onClick="delPick('${pick.pickCd}','${pick.pickNm}');">
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


          <div class="popup_box" style="height: 800px;">
              <div style="height: 20px; width: 375px; float: top;">
              </div>
                <div>
                  <button id="popupClose" class="close close3" type="button" style="float:right;"></button>
                </div>
              <!--팝업 컨텐츠 영역-->
              <form id="pickDetail">
              <div class="popup_cont">
                  <div>
                  <h2 align="center" style="margin-left:30px; padding:20px;">    My PICK !</h2>
                      <div>
                        <label style="display:inline-block; width:100px; text-align:center;" for="pickNm">Pick 명</label>  <input class="popup-input" style="width:250px;" type="text" name="pickNm" id="pickNm" /><br/>
                      </div>
                      <div>
                      <label style="display:inline-block; width:100px; text-align:center;" for="favBrnd">선호브랜드</label>  <input class="popup-input" style="width:250px;" type="text" name="favBrand" id="favBrnd" /><br/>
                      </div>
                  </div>
                  <div>
                      <h5 align="center" style="padding:10px;">항목별 예산설정</h5>
                      <div>
                      <label style="display:inline-block; width:100px; text-align:center;" for="lblTop">상의</label>  <input class="popup-input" style="width:250px;" type="number" name="topWear" id="topWear" onkeyup="getTotalsSum();" /><br/>
                      </div>
                      <div>
                      <label style="display:inline-block; width:100px; text-align:center;" for="lblBottom">하의</label>  <input class="popup-input" style="width:250px;" type="number" name="bottom" id="bottom"  onkeyup="getTotalsSum();" /><br/>
                      </div>
                      <div>
                      <label style="display:inline-block; width:100px; text-align:center;" for="lblShose">신발</label>  <input class="popup-input" style="width:250px;" type="number" name="shose" id="shose"  onkeyup="getTotalsSum();" /><br/>
                      </div>
                  <h5 align="center" style="padding:10px;"> 총 <input type="text" name="totalSum" id="totalSum" style="text-align:center; border:none;" disabled/> 원 </h5>
                  <label style="display:inline-block; width:100px; text-align:center;" for="age">성별</label>
                    <input style="width:20px;" type="radio" name="sex" id="men" value="1" text="남성" checked /> 남성
                    <input style="width:20px;" type="radio" name="sex" id="women" value="0" text="여성" /> 여성<br/>
                  </div>
                  <div>
                  <h5 align="center" style="padding:10px;">선호 스타일 (*복수선택가능)</h5>
                  <ul class="nav nav-pills">
                    <li class="nav-item">
                        <input type="button" name="favStyle1" id="1"  class="custom-btn btn-2" value="빈티지" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                    <li class="nav-item">
                        <input type="button" name="favStyle2" id="2" class="custom-btn btn-2" value="미니멀" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                    <li class="nav-item">
                        <input type="button" name="favStyle3" id="3" class="custom-btn btn-2"value="비즈니스" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                  </ul>
                  <ul class="nav nav-pills">
                    <li class="nav-item">
                        <input type="button" name="favStyle4" id="4" class="custom-btn btn-2" value="캐주얼" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                    <li class="nav-item">
                        <input type="button" name="favStyle5" id="5" class="custom-btn btn-2" value="레트로" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                    <li class="nav-item">
                        <input type="button" name="favStyle6" id="6" class="custom-btn btn-2"value="페미닌" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                  </ul>
                  <ul class="nav nav-pills">
                    <li class="nav-item">
                        <input type="button" name="favStyle7" id="7" class="custom-btn btn-2" value="러블리" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                    <li class="nav-item">
                        <input type="button" name="favStyle8" id="8" class="custom-btn btn-2" value="심플" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                    <li class="nav-item">
                        <input type="button" name="favStyle9" id="9" class="custom-btn btn-2"value="모던" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                  </ul>
                  <ul class="nav nav-pills">
                    <li class="nav-item">
                        <input type="button" name="favStyle10" id="10" class="custom-btn btn-2" value="스트릿" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                    <li class="nav-item">
                        <input type="button" name="favStyle11" id="11" class="custom-btn btn-2" value="클래식" style="margin:5px;">
                            <span></span>
                        </input>
                    </li>
                  </ul>
               </div>
              </div>
              <!--팝업 버튼 영역-->
              <div class="popup_bottom" style="float: bottom; margin-top: 1%;">
                   <input type="submit" id="form_submit" value="저장하기" class="w-btn w-btn-outline w-btn-indigo" style="width:100%;">
                  </a>
              </div>
              </form>
          </div>

    </div>

    <!-- Alert, Confirm Modal -->
    <div class="modal" id="msg_popup" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <!-- MSG Space-->
                </div>
                <div class="modal-footer" id="btn_confirm">
                    <button type="button" id="confirm_yes" class="btn btn-Light col mr-2" data-dismiss="modal">
                        네, 필요없어요.
                    </button>
                    <button type="button" id="confirm_no" class="btn btn-secondary col mr-2" data-dismiss="modal">
                        아니요! 더 추천받을래요.
                    </button>
                </div>
                <div class="modal-footer" id="btn_alert">
                    <button type="button" id="alert_ok"class="btn btn-primary" data-dismiss="modal" >OK</button>
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