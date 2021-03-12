/** *** */
(function(modules) { // webpackBootstrap
	/** *** */
	// The module cache
	/** *** */
	var installedModules = {};
	/** *** */
	/** *** */
	// The require function
	/** *** */
	function __webpack_require__(moduleId) {
		/** *** */
		/** *** */
		// Check if module is in cache
		/** *** */
		if (installedModules[moduleId]) {
			/** *** */
			return installedModules[moduleId].exports;
			/** *** */
		}
		/** *** */
		// Create a new module (and put it into the cache)
		/** *** */
		var module = installedModules[moduleId] = {
			/** *** */
			i : moduleId,
			/** *** */
			l : false,
			/** *** */
			exports : {}
		/** *** */
		};
		/** *** */
		/** *** */
		// Execute the module function
		/** *** */
		modules[moduleId].call(module.exports, module, module.exports,
				__webpack_require__);
		/** *** */
		/** *** */
		// Flag the module as loaded
		/** *** */
		module.l = true;
		/** *** */
		/** *** */
		// Return the exports of the module
		/** *** */
		return module.exports;
		/** *** */
	}
	/** *** */
	/** *** */
	/** *** */
	// expose the modules object (__webpack_modules__)
	/** *** */
	__webpack_require__.m = modules;
	/** *** */
	/** *** */
	// expose the module cache
	/** *** */
	__webpack_require__.c = installedModules;
	/** *** */
	/** *** */
	// define getter function for harmony exports
	/** *** */
	__webpack_require__.d = function(exports, name, getter) {
		/** *** */
		if (!__webpack_require__.o(exports, name)) {
			/** *** */
			Object.defineProperty(exports, name, {
				enumerable : true,
				get : getter
			});
			/** *** */
		}
		/** *** */
	};
	/** *** */
	/** *** */
	// define __esModule on exports
	/** *** */
	__webpack_require__.r = function(exports) {
		/** *** */
		if (typeof Symbol !== 'undefined' && Symbol.toStringTag) {
			/** *** */
			Object.defineProperty(exports, Symbol.toStringTag, {
				value : 'Module'
			});
			/** *** */
		}
		/** *** */
		Object.defineProperty(exports, '__esModule', {
			value : true
		});
		/** *** */
	};
	/** *** */
	/** *** */
	// create a fake namespace object
	/** *** */
	// mode & 1: value is a module id, require it
	/** *** */
	// mode & 2: merge all properties of value into the ns
	/** *** */
	// mode & 4: return value when already ns object
	/** *** */
	// mode & 8|1: behave like require
	/** *** */
	__webpack_require__.t = function(value, mode) {
		/** *** */
		if (mode & 1)
			value = __webpack_require__(value);
		/** *** */
		if (mode & 8)
			return value;
		/** *** */
		if ((mode & 4) && typeof value === 'object' && value
				&& value.__esModule)
			return value;
		/** *** */
		var ns = Object.create(null);
		/** *** */
		__webpack_require__.r(ns);
		/** *** */
		Object.defineProperty(ns, 'default', {
			enumerable : true,
			value : value
		});
		/** *** */
		if (mode & 2 && typeof value != 'string')
			for ( var key in value)
				__webpack_require__.d(ns, key, function(key) {
					return value[key];
				}.bind(null, key));
		/** *** */
		return ns;
		/** *** */
	};
	/** *** */
	/** *** */
	// getDefaultExport function for compatibility with non-harmony modules
	/** *** */
	__webpack_require__.n = function(module) {
		/** *** */
		var getter = module && module.__esModule ?
		/** *** */
		function getDefault() {
			return module['default'];
		} :
		/** *** */
		function getModuleExports() {
			return module;
		};
		/** *** */
		__webpack_require__.d(getter, 'a', getter);
		/** *** */
		return getter;
		/** *** */
	};
	/** *** */
	/** *** */
	// Object.prototype.hasOwnProperty.call
	/** *** */
	__webpack_require__.o = function(object, property) {
		return Object.prototype.hasOwnProperty.call(object, property);
	};
	/** *** */
	/** *** */
	// __webpack_public_path__
	/** *** */
	__webpack_require__.p = "";
	/** *** */
	/** *** */
	/** *** */
	// Load entry module and return exports
	/** *** */
	return __webpack_require__(__webpack_require__.s = "../demo1/src/js/pages/crud/datatables/data-sources/javascript.js");
	/** *** */
})
		/** ********************************************************************* */
		/** *** */
		({

			/***/
			"../demo1/src/js/pages/crud/datatables/data-sources/javascript.js" :
			/*******************************************************************
			 * !************************************************************************!*\
			 * !***
			 * ../demo1/src/js/pages/crud/datatables/data-sources/javascript.js
			 * ***! \
			 ******************************************************************/
			/* ! no static exports found */
			/***/
			(function(module, exports, __webpack_require__) {

				"use strict";

				var KTDatatablesDataSourceHtml = function() {

					var dataJSONArray = JSON.parse(books)

					var initTable1 = function() {
						var table = $('#kt_datatable');
						
						
						// begin first table
						table.DataTable({
									language: {
									    "lengthMenu":	'<select name="kt_datatable_length" aria-controls="kt_datatable" class="custom-select custom-select-sm form-control form-control-sm">'+
															'<option value="10">10개씩</option>'+
															'<option value="25">25개씩</option>'+
															'<option value="50">50개씩</option>'+
															'<option value="100">100개씩</option>'+
														'</select>',
   										"search":		"_INPUT_",
										"paginate": {
											"previous":	"<",
									    	"next":		">"
									    }
									},
									lengthMenu: {
								        "className": 'form-control form-control-sm'
								    },
									info: false,
									responsive : true,
									dom: "<'row' <'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f> > <'row'rt> p",
									data : dataJSONArray,
									columnDefs : [
										
										{
										targets : -1,
										title : 'Actions',
										orderable : false,
										render : function(data, type, full, meta) {
												if (data.state == 'wait') {
													return '\
														<div class="row">\
															<a href="javascript:cancel('+ data.book_no + ');" class="btn btn-sm btn-clean btn-icon" title="Delete">\
																<button class=" btn-xs listbtn-xs-red">삭제 </button>\
															</a>\
														</div>\
														<div class="row">\
															<a href="javascript:modify('+ data.book_no + ');" class="btn btn-sm btn-clean btn-icon" title="Edit details">\
																<button class=" btn-xs listbtn-xs-blue">수정 </button></i>\
															</a>\
														</div>\
													';
												} 
												else if (data.state == 'uncharge') {
													return '\
														<div class="row">\
															<a href="javascript:cancel('+ data.book_no + ');" class="btn btn-sm btn-clean btn-icon" title="Delete">\
																<button class=" btn-xs listbtn-xs-red">삭제 </button>\
															</a>\
														</div>\
														<div class="row">\
															<a href="javascript:payment('+ data.book_no + ');" class="btn btn-sm btn-clean btn-icon" title="Edit details">\
																<button class=" btn-xs listbtn-xs-blue">결제 </button></i>\
															</a>\
														</div>\
													';
												} else {
													return '<div class="d-flex align-items-center"> - </div>';
												}
											}
										},
										{ 
											orderable: false, targets: [0,1,2,3,4,5,6]
										}
									],
									order: []
								});
					};

					return {

						// main function to initiate the module
						init : function() {
							initTable1();
						},

					};

				}();

				jQuery(document).ready(function() {
					KTDatatablesDataSourceHtml.init();
					$('.custom-select').removeClass('custom-select custom-select-sm')
				});

				/***/
			})

		/** *** */
		});
// # sourceMappingURL=javascript.js.map

function modify(book_no) {
	book_no: book_no
	var modifycheck = confirm('예약을 수정하시겠습니까?');
	if (modifycheck) {    
		$.get("/booking/danalCheck/", {
			book_no : book_no
		},function(jqXHR) {
			// always          
		},'text' /* xml, text, script, html */)
		.done(function(res) {        
			if(res=="true"){
				alert("죄송합니다. " +
					"현재 다날 결제건은 수정이 불가합니다." +
					" 취소 후 재 예약 부탁드립니다.");
				
			}else {     
			window.location.href = '/booking/modify' + '?book_no=' + book_no;
			}
		})     
		
		

	}
}

function cancel(book_no) {
	if(confirm('예약을 취소하시겠습니까?')) {
		$.get("/payment/cancel", {
			book_no : book_no
		},function(jqXHR) {
			// always
		},'text' /* xml, text, script, html */)
		.done(function(message) {   
			alert(message);
			location.href = '/booking/check';
		})
		.fail(function(jqXHR) {
		})
		.always(function(jqXHR) {
		});
	}
}