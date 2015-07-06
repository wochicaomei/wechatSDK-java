$(function() {

	$("#navbar").find("a").eq(0).addClass("choose");

	categoryService.listCategory("#category_ul", 0, categoryData);

});