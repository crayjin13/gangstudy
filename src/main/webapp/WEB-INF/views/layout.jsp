

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--begin::Main-->

		<jsp:include page="/WEB-INF/views/partials/_header-mobile.jsp" flush="true"/> 
		<div class="d-flex flex-column flex-root"></div>

			<!--begin::Page-->
			<div class="d-flex flex-row flex-column-fluid page">

				<jsp:include page="/WEB-INF/views/partials/_aside.jsp" flush="true"/> 

				<!--begin::Wrapper-->
				<div class="d-flex flex-column flex-row-fluid wrapper" id="kt_wrapper">

					<jsp:include page="/WEB-INF/views/partials/_header.jsp" flush="true"/> 

					<!--begin::Content-->
					<div class="content d-flex flex-column flex-column-fluid" id="kt_content">

						<jsp:include page="/WEB-INF/views/partials/_subheader/subheader-v1.jsp" flush="true"/> 

						 <jsp:include page="/WEB-INF/views/partials/_content2.jsp" flush="true"/>   
					<%-- 	 <jsp:include page="/WEB-INF/views/pages/base.jsp" flush="true"/>   --%>
					
					</div>

					<!--end::Content-->

					<jsp:include page="/WEB-INF/views/partials/_footer.jsp" flush="true"/> 
				</div>

				<!--end::Wrapper-->
			</div>

			<!--end::Page-->
		</div>

		<!--end::Main-->