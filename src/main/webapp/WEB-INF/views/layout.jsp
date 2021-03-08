<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!--begin::Main-->
<%@ include file="/WEB-INF/views/partials/_header-mobile.jsp"%>

<div class="d-flex flex-column flex-root">

	<!--begin::Page-->
	<div class="d-flex flex-row flex-column-fluid page">

		<!--begin::Wrapper-->
		<div class="d-flex flex-column flex-row-fluid wrapper" id="kt_wrapper">
		
			<%@ include file="/WEB-INF/views/partials/_header.jsp"%>

			<!--begin::Content-->
			<div class="content d-flex flex-column flex-column-fluid" id="kt_content">
				<%@ include file="/WEB-INF/views/partials/main_content.jsp"%>
			</div>
			<!--end::Content-->

			<!--[html-partial:include:{"file":"partials/_footer/extended.html"}]/-->
			<%@ include file="/WEB-INF/views/partials/_footer/extended.jsp"%>
		</div>
		<!--end::Wrapper-->
	</div>
	<!--end::Page-->
</div>
<!--end::Main-->