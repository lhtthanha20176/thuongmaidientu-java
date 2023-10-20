<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Page Heading -->
<div class="d-sm-flex align-items-center justify-content-between mb-4">
	<h1 class="h3 mb-0 text-gray-800">Trang quản trị/Chi tiết trạng thái sản phẩm</h1>
	<a href="<c:url value="/quan-tri/tao-moi-trang-thai-san-pham"></c:url>"
		class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
		<i class="fas fa-download fa-sm text-white-50"></i> Tạo mới
	</a>
</div>

<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">Chi tiết trạng thái sản phẩm "${ productState.name }"</h6>
	</div>
	<div class="card-body">
		<div class="table-responsive">
			<div id="dataTable" class="dataTables_wrapper dt-bootstrap4">
				<table class="table table-bordered table table-hover">
					<tbody>
						<tr>
							<th style="width: 25%;">ID trạng thái</th>
							<td>${ productState.ID }</td>
						</tr>
						<tr>
							<th style="width: 25%;">Tên trạng thái</th>
							<td>${ productState.name }</td>
						</tr>
						<tr>
							<th style="width: 25%;">Mô tả trạng thái</th>
							<td>${ productState.description }</td>
						</tr>
						<tr>
							<th style="width: 25%;">Ngày tạo</th>
							<td>${ productState.createAt }</td>
						</tr>
						<tr>
							<th style="width: 25%;">Ngày cập nhật</th>
							<td>${ productState.updateAt }</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="card-footer">
		<a class="btn btn-primary"
			href="<c:url value="/quan-tri/danh-sach-trang-thai-san-pham"></c:url>">Quay về danh
			sách</a> | <a
			class="btn btn-success"
			href="<c:url value="/quan-tri/chinh-sua-trang-thai-san-pham/${ productState.ID }"></c:url>">Chỉnh sửa</a>
	</div>
</div>