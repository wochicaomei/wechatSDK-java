$(function() {

	$("#navbar").find("a").eq(1).addClass("choose");

	categoryService.listCategory("#category_ul1", 0, categoryData);
	categoryService.listCategory("#category_ul2", 0, categoryData);
	categoryService.listCategory("#category_ul3", 0, categoryData);

});