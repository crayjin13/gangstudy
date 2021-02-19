$(function() {

// *********** 로그인 처리 *****************
	$("button[name=loginbtn]").click(function() {
		var mlafArray = $('#kt_login_singin_form').serialize();
		console.log("---- 로그인 값이 들어오는가  ---" + mlafArray);
		$.ajax({
			url : 'sign_in_action',
			method : 'POST',
			data : mlafArray,
			dataType : 'text',
			success : function(textData) {
				if (textData.trim() == "true") {

					location.href = '/';

				} else if (textData.trim() == "false1") {
					alert('아이디를 다시 확인해주세요');
					id_check();

				} else if (textData.trim() == "false2") {
					alert('비밀번호를 다시 확인해주세요');
					password_check();
				} else if (textData.trim() == "false") {
					alert('정보를 다시 확인해 주세요');
  
				}
			}
		});
	});

});


/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "../demo1/src/js/pages/custom/login/login-4.js");
/******/ })
/************************************************************************/
/******/ ({

/***/ "../demo1/src/js/pages/custom/login/login-4.js":
/*!*****************************************************!*\
  !*** ../demo1/src/js/pages/custom/login/login-4.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// Class Definition
var KTLogin = function() {
	var _buttonSpinnerClasses = 'spinner spinner-right spinner-white pr-15';

	var _handleFormSignin = function() {
		var form = KTUtil.getById('kt_login_singin_form');
		var formSubmitUrl = KTUtil.attr(form, 'action');
		var formSubmitButton = KTUtil.getById('kt_login_singin_form_submit_button');

		if (!form) {
			return;
		}

		FormValidation
		    .formValidation(
		        form,
		        {
		            fields: {
						id: {
							validators: {
								notEmpty: {
									message: '아이디를 입력해주세요.'
								}
							}
						},
						pw: {
							validators: {
								notEmpty: {
									message: '비밀번호를 입력해주세요.'
								}
							}
						}
		            },
		            plugins: {
						trigger: new FormValidation.plugins.Trigger(),
						submitButton: new FormValidation.plugins.SubmitButton(),
	            		//defaultSubmit: new FormValidation.plugins.DefaultSubmit(), // Uncomment this line to enable normal button submit after form validation
						bootstrap: new FormValidation.plugins.Bootstrap({
						//	eleInvalidClass: '', // Repace with uncomment to hide bootstrap validation icons
						//	eleValidClass: '',   // Repace with uncomment to hide bootstrap validation icons
						})
		            }
		        }
		    )
		    .on('core.form.valid', function() {
				// Show loading state on button
				KTUtil.btnWait(formSubmitButton, _buttonSpinnerClasses, "Please wait");

				// Simulate Ajax request
				setTimeout(function() {
					KTUtil.btnRelease(formSubmitButton);
				}, 2000);

				// Form Validation & Ajax Submission: https://formvalidation.io/guide/examples/using-ajax-to-submit-the-form
				/**
		        FormValidation.utils.fetch(formSubmitUrl, {
		            method: 'POST',
					dataType: 'json',
		            params: {
		                name: form.querySelector('[name="username"]').value,
		                email: form.querySelector('[name="password"]').value,
		            },
		        }).then(function(response) { // Return valid JSON
					// Release button
					KTUtil.btnRelease(formSubmitButton);

					if (response && typeof response === 'object' && response.status && response.status == 'success') {
						Swal.fire({
			                text: "All is cool! Now you submit this form",
			                icon: "success",
			                buttonsStyling: false,
							confirmButtonText: "Ok, got it!",
							customClass: {
								confirmButton: "btn font-weight-bold btn-light-primary"
							}
			            }).then(function() {
							KTUtil.scrollTop();
						});
					} else {
						Swal.fire({
			                text: "Sorry, something went wrong, please try again.",
			                icon: "error",
			                buttonsStyling: false,
							confirmButtonText: "Ok, got it!",
							customClass: {
								confirmButton: "btn font-weight-bold btn-light-primary"
							}
			            }).then(function() {
							KTUtil.scrollTop();
						});
					}
		        });
				**/
		    })
			.on('core.form.invalid', function() {
				Swal.fire({
					text: "정보가 맞는지 확인 후 다시 시도해주세요.",
					icon: "error",
					buttonsStyling: false,
					confirmButtonText: "네",
					customClass: {
						confirmButton: "btn font-weight-bold btn-light-primary"
					}
				}).then(function() {
					KTUtil.scrollTop();
				});
		    });
    }

      
    // Public Functions
    return {
        init: function() {
            _handleFormSignin();
			_handleFormForgot();
			_handleFormSignup();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function() {
    KTLogin.init();
});


/***/ })

/******/ });
//# sourceMappingURL=login-4.js.map