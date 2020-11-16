

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String pages = request.getParameter("pages");
	if (pages == null)
		pages = "index.jsp";
%>

<!--begin::Main-->

<%@ include file="/WEB-INF/views/partials/_header-mobile.jsp"%>
<div class="d-flex flex-column flex-root"></div>

<!--begin::Page-->
<div class="d-flex flex-row flex-column-fluid page">

	<%@ include file="/WEB-INF/views/partials/_aside.jsp"%>

	<!--begin::Wrapper-->
	<div class="d-flex flex-column flex-row-fluid wrapper" id="kt_wrapper">

		<%@ include file="/WEB-INF/views/partials/_header.jsp"%>

		<!--begin::Content-->
		<div class="content d-flex flex-column flex-column-fluid"
			id="kt_content">

			<%@ include
				file="/WEB-INF/views/partials/_subheader/subheader-v1.jsp"%>
			<%-- <jsp:include page="<%=route%>" flush="false" /> --%>

			<jsp:include page="<%=pages%>"  />
			<%-- <%@ include file="/WEB-INF/views/partials/main_content.jsp"%> --%>
		</div>

		<!--end::Content-->

		<%@ include file="/WEB-INF/views/partials/_footer.jsp"%>
	</div>

	<!--end::Wrapper-->
</div>

<!--end::Page-->
</div>

<!--end::Main-->