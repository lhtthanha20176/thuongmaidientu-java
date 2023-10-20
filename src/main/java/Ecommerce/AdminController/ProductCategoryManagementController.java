package Ecommerce.AdminController;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Ecommerce.Entity.ProductCategory;
import Ecommerce.Service.ProductCategoryServiceImpl;
import Ecommerce.Validator.ProductCategoryValidator;

@Controller
public class ProductCategoryManagementController {
	@Autowired
	private ProductCategoryServiceImpl productCategoryServiceImpl;
	
	@RequestMapping(value = { "quan-tri/danh-sach-the-loai", "quan-tri/danh-sach-the-loai/{message}" }, method = RequestMethod.GET)
	public ModelAndView ProductCategoryList(HttpSession httpSession, @PathVariable(required = false) String message) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj == null)
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		String loginState = obj.toString();
		if(!loginState.matches("logged:true;username:([a-zA-Z0-9]{1,});role:Admin"))
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/product-category-list");
		modelAndView.addObject("productCategories", productCategoryServiceImpl.GetProductCategories());
		String adminUsername = loginState.replace("logged:true;username:", "").replace(";role:Admin", "");
		modelAndView.addObject("adminUsername", adminUsername);
		if(message != null) {
			if(message.equals("delete-success"))
				modelAndView.addObject("state", "Xóa thành công");
			else if(message.equals("delete-failed"))
				modelAndView.addObject("state", "Xóa thất bại");
			else 
				modelAndView.addObject("state", "Không xác định được nội dung thông báo");
		}
		return modelAndView;
	}

	@RequestMapping(value = "quan-tri/chi-tiet-the-loai/{id}", method = RequestMethod.GET)
	public ModelAndView ProductCategoryDetail(HttpSession httpSession, @PathVariable BigDecimal id) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj == null)
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		String loginState = obj.toString();
		if(!loginState.matches("logged:true;username:([a-zA-Z0-9]{1,});role:Admin"))
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		if (id.intValue() <= 0)
			return new ModelAndView("redirect:/quan-tri/danh-sach-the-loai");

		ProductCategory productCategory = productCategoryServiceImpl.GetProductCategory(id);
		if (productCategory == null)
			return new ModelAndView("redirect:/quan-tri/danh-sach-the-loai");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/product-category-detail");
		modelAndView.addObject("productCategory", productCategory);
		String adminUsername = loginState.replace("logged:true;username:", "").replace(";role:Admin", "");
		modelAndView.addObject("adminUsername", adminUsername);
		return modelAndView;
	}

	@RequestMapping(value = {"quan-tri/tao-moi-the-loai", "quan-tri/tao-moi-the-loai/{message}"}, method = RequestMethod.GET)
	public ModelAndView CreateProductCategory(HttpSession httpSession, @PathVariable(required = false) String message) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj == null)
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		String loginState = obj.toString();
		if(!loginState.matches("logged:true;username:([a-zA-Z0-9]{1,});role:Admin"))
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/create-product-category");
		modelAndView.addObject("productCategory", new ProductCategory());
		String adminUsername = loginState.replace("logged:true;username:", "").replace(";role:Admin", "");
		modelAndView.addObject("adminUsername", adminUsername);
		if(message != null) {
			if(message.equals("add-success"))
				modelAndView.addObject("state", "Thêm thành công");
			else if(message.equals("add-failed"))
				modelAndView.addObject("state", "Thêm thất bại");
			else 
				modelAndView.addObject("state", "Không xác định được nội dung thông báo");
		}
		return modelAndView;
	}

	@RequestMapping(value = "quan-tri/tao-moi-the-loai", method = RequestMethod.POST, produces = "application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView CreateProductCategory(HttpSession httpSession, @ModelAttribute("productCategory") ProductCategory productCategory, 
			BindingResult bindingResult, ProductCategoryValidator productCategoryValidator) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj == null)
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		String loginState = obj.toString();
		if(!loginState.matches("logged:true;username:([a-zA-Z0-9]{1,});role:Admin"))
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		productCategoryValidator.validate(productCategory, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("admin/create-product-category");
			modelAndView.addObject("productCategory", productCategory);
			String adminUsername = loginState.replace("logged:true;username:", "").replace(";role:Admin", "");
			modelAndView.addObject("adminUsername", adminUsername);
			return modelAndView;
		}

		if(productCategoryServiceImpl.CreateProductCategory(productCategory))
			return new ModelAndView("redirect:/quan-tri/tao-moi-the-loai/add-success");
		
		return new ModelAndView("redirect:/quan-tri/tao-moi-the-loai/add-failed");
	}

	@RequestMapping(value = {"quan-tri/chinh-sua-the-loai/{id}", "quan-tri/chinh-sua-the-loai/{id}/{message}"}, method = RequestMethod.GET)
	public ModelAndView UpdateCategory(HttpSession httpSession, @PathVariable BigDecimal id, @PathVariable(required = false) String message) {
		Object obj = httpSession.getAttribute("loginState");
		if(obj == null)
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		String loginState = obj.toString();
		if(!loginState.matches("logged:true;username:([a-zA-Z0-9]{1,});role:Admin"))
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		if (id.intValue() <= 0)
			return new ModelAndView("redirect:/quan-tri/danh-sach-the-loai");

		ProductCategory productCategory = productCategoryServiceImpl.GetProductCategory(id);
		if (productCategory == null)
			return new ModelAndView("redirect:/quan-tri/danh-sach-the-loai");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/update-product-category");
		modelAndView.addObject("productCategory", productCategory);
		String adminUsername = loginState.replace("logged:true;username:", "").replace(";role:Admin", "");
		modelAndView.addObject("adminUsername", adminUsername);
		if(message != null) {
			if(message.equals("edit-success"))
				modelAndView.addObject("state", "Chỉnh sửa thành công");
			else if(message.equals("edit-failed"))
				modelAndView.addObject("state", "Chỉnh sửa thất bại");
			else 
				modelAndView.addObject("state", "Không xác định được nội dung thông báo");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "quan-tri/chinh-sua-the-loai/{id}", method = RequestMethod.POST, produces = "application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView UpdateCategory(HttpSession httpSession, @ModelAttribute("category") ProductCategory productCategory, 
			BindingResult bindingResult, ProductCategoryValidator productCategoryValidator) {	
		Object obj = httpSession.getAttribute("loginState");
		if(obj == null)
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		String loginState = obj.toString();
		if(!loginState.matches("logged:true;username:([a-zA-Z0-9]{1,});role:Admin"))
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		productCategoryValidator.validate(productCategory, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("admin/update-product-category");
			modelAndView.addObject("productCategory", productCategory);
			String adminUsername = loginState.replace("logged:true;username:", "").replace(";role:Admin", "");
			modelAndView.addObject("adminUsername", adminUsername);
			return modelAndView;
		}

		if(productCategoryServiceImpl.UpdateProductCategory(productCategory))
			return new ModelAndView("redirect:/quan-tri/chinh-sua-the-loai/{id}/edit-success");
		
		return new ModelAndView("redirect:/quan-tri/chinh-sua-the-loai/{id}/edit-failed");
	}

	@RequestMapping(value = "quan-tri/xoa-the-loai", method = RequestMethod.POST)
	public ModelAndView DeleteCategory(HttpSession httpSession, @RequestParam(value = "id", required = true) BigDecimal id) {		
		Object obj = httpSession.getAttribute("loginState");
		if(obj == null)
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		String loginState = obj.toString();
		if(!loginState.matches("logged:true;username:([a-zA-Z0-9]{1,});role:Admin"))
			return new ModelAndView("redirect:/tai-khoan-quan-tri/dang-nhap");
		
		if(id.intValue() <= 0)
			return new ModelAndView("redirect:/quan-tri/danh-sach-the-loai");
		
		if(productCategoryServiceImpl.DeleteProductCategory(id))
			return new ModelAndView("redirect:/quan-tri/danh-sach-the-loai/delete-success");
		
		return new ModelAndView("redirect:/quan-tri/danh-sach-the-loai/delete-failed");
	}
}
