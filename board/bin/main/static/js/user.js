/**
 * 
 *//**
 * 
 */

function chkLength(){
	var obj 			= event.srcElement;
	var obj_len 	= obj.value.length;
	var max_len 	= obj.maxLength;
	
	if (obj_len > max_len) {
		//obj_len = obj_len-1;
		alert("max_len = " + max_len);
		alert("0번째 문자 : " + obj.value.charAt(0));
		alert("마지막 문자 : " + obj.value.charAt(max_len-1));
		//var tmp = obj.value.replace(charAt(max_len-1),"");
	}// 키의 동작을 무효화.
}// chkLength

/////////////
function chkSex(){
	var man 		= document.getElementById("male");
	var woman	= document.getElementById("female");
	
	if(man.checked) {
		document.getElementById("maleLb").className = "on";
		document.getElementById("femaleLb").className = "";
	}
	else{
		document.getElementById("maleLb").className = "";
		document.getElementById("femaleLb").className = "on";
	}
	return false;
}//chkSex

/////////////////
function getOptVal(){
	var sel_mail		  = $('select[name="sel_mail"]');
	var sel_mail_val   = sel_mail.val();
	var mail2 			  = $('#mail2');

	if(sel_mail_val == "self"){
		mail2.val("");
		mail2.prop("readonly", true);
	}
	else if(sel_mail_val == "inst"){
		mail2.prop("readonly", false);
		mail2.val("");
		mail2.focus();
	}else{
		$('#mail2').val($('select[name="sel_mail"]').val());
	}
	return false;
}//getOptVal


function allowModify(){//modifyInfo.jsp
	// readonly 속성 해제하기 등, css 조작
	$("input[type=text], input[type=password]").attr("readonly", false);
	$("input[id=addr], input[id=zipcode]").attr("readonly", true);	// addr과 zipcode input은 readonly여야한다.
	$(".btn_wrapper").css("display", "block");//수정확정 버튼은 수정하기 버튼을 눌렀을때만 보여준다.
	$(".addr_area > span").css("display", "inline");
	$("#datepicker").attr("readonly", true);
	
	
	$("#searchAddr").on("click", function(){
		searchAddr();
	});
	
	// jQuery 달력. 달력 버튼 누르면 기능 동작부여.
	$("#datepicker").addClass("allow_datepicker");
	$("#datepicker").datepicker(setCalendarVar());
	$('img.ui-datepicker-trigger' ).prop('align' , 'absmiddle' ); // css 문제가 있는경우 일부 위치 조절 :)
	$('img.ui-datepicker-trigger' ).css('position' , 'absolute' ).css('top', '15px'); // css 문제가 있는경우 일부 위치 조절 :)
}


function searchAddr(){
	var width 	= 547;
	var height 	= 602;
    new daum.Postcode({
    	width:width,
    	height:height,
        oncomplete: function(data) {
        	 // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('addr').value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('detailAddr').focus();
        },//oncomplete
        theme : {
        	searchBgColor		: "", //배경 색상 지정. 지정 x시, 기본값 하얀색.
        	queryTextColor 	: "" // 폰트 색상 지정. 지정 x시, 기본값 검정색.
        }//theme
    }).open({
    	q: '판교역로 235',
    	left: (window.screen.width / 2) - (width / 2),
        top: (window.screen.height / 2) - (height / 2)
    });
}//searchAddr

function setCalendarVar(){
	var calAttr = {
	         dateFormat: 'yymmdd',
	         //startDate : 'c-c-c',
	         //// 오늘 날짜 부터 100년 이전의 오늘 날짜까지만 선택 가능하다. 오늘 이후의 날짜는 선택 불가.
	         // 오늘 날짜를 기준으로 minDate는 선택가능한 이전의 날짜 영역 지정가능하게끔함.
	         // "		"		" 		 maxDate는 선택가능한 이후의 날짜 영역 지정가능하게끔함.
	         // maxDate 가 '0'이면 오늘 이후의 날짜는 선택하지 못한다.
	         //minDate : "-110y",
	         maxDate : "0",
	         ////
	         changeMonth : true, //월 선택을 select-option으로 보여줄지 여부
	         changeYear : true, //년도 선택을 select-option으로 보여줄지 여부
	         showButtonPanel : true, // 달력 하단의 닫기,오늘날짜 버튼이 있는 버튼 패널 보여줄지 여부
	         currentText : '오늘날짜', // 버튼패널의 오늘날짜를 보여주는 버튼에 텍스트 지정
	         closeText : '닫기', // 버튼패널의 닫기 기능을 하는 버튼에 텍스트 지정
	         prevText: '이전 달',
	         nextText: '다음 달',
	         monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	         monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	         dayNames: [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
	         dayNamesShort: [ '일', '월', '화', '수', '목', '금', '토'],
	         dayNamesMin: [ '일', '월', '화', '수', '목', '금', '토'],
	         showMonthAfterYear: true,
	         yearRange: 'c-110:c', //'c-99:c+99', // 년도의 select 박스에서 년도 선택 시, 년도의 검색 범위를 지정.
	         yearSuffix: '년',
	         showOn: 'both', // 버튼과 텍스트 필드 또는 모두 캘린더에 표시할지 여부 선택. 필수
	         buttonImageOnly: true, //달력버튼이미지만 표시한다.('...'표시 생략)
	         //buttonImage: '<c:url value="/common/images/contents/calendar_icon_expansion.jpg"/>',  // 달력버튼이미지 설정.
	         buttonImage: 'http://localhost:8080/goodperson/common/images/contents/calendar_icon_expansion.jpg',  // 달력버튼이미지 설정.
	         buttonText: '달력선택'  //버튼안에
	};
	return calAttr;
}//setCalendarVar