$(function() {

	$("#navbar").find("a").eq(1).addClass("choose");

	categoryService.listCategory("#category_ul", 0, categoryData);

});