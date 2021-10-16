<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form class="form-search col-md-12" name="choice"
	style="margin-top: -100px;" method="get" action="${root}/house/search.do">
	<div class="row  align-items-end">

		<div class="col-md-2" style="margin-left: 50px;">
			<label for="sido">도/광역시</label>
			<div class="select-wrap">
				<span class="icon icon-arrow_drop_down"></span> <select name="sido"
					id="sido" class="form-control d-block rounded-0">
					<option value="">선택</option>
					<option value="서울특별시">서울특별시</option>
				</select>
			</div>
		</div>

		<div class="col-md-2">
			<label for="gugun">시/구/군</label>
			<div class="select-wrap">
				<span class="icon icon-arrow_drop_down"></span> <select name="gugun"
					id="gugun" class="form-control d-block rounded-0">
					<option value="">선택</option>
					<option value="종로구">종로구</option>
				</select>
			</div>
		</div>
		<div class="col-md-2">
			<label for="dong">동</label>
			<div class="select-wrap">
				<span class="icon icon-arrow_drop_down"></span> <select name="dong"
					id="dong" class="form-control d-block rounded-0">
					<option value="">선택</option>
					<option value="사직동">사직동</option>
					<option value="혜화동">혜화동</option>
					<option value="무악동">무악동</option>
					<option value="신영동">신영동</option>
					<option value="홍지동">홍지동</option>
					<option value="부암동">부암동</option>
					<option value="평창동">평창동</option>
					<option value="구기동">구기동</option>
					<option value="행촌동">행촌동</option>
					<option value="교북동">교북동</option>
					<option value="홍파동">홍파동</option>
					<option value="송월동">송월동</option>
					<option value="평동">평동</option>
				</select>
			</div>
		</div>
		<div class="col-md-3">
			<label for="apt">아파트</label>
			<div class="select-wrap">
				<input type="text" id="apt" name="apt" style="text-align: center;"
					class="form-control d-block rounded-0">
			</div>
		</div>
		<div class="col-md-2">
			<input type="button" onclick="check()"
				class="btn btn-success text-white btn-block rounded-0" value="검색">
		</div>
	</div>
</form>