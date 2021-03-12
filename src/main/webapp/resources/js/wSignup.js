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
	return __webpack_require__(__webpack_require__.s = "../demo1/src/js/pages/custom/login/login-3.js");
	/** *** */
})
		/** ********************************************************************* */
		/** *** */
		({

			/***/
			"../demo1/src/js/pages/custom/login/login-3.js" :
			/*******************************************************************
			 * !*****************************************************!*\ !***
			 * ../demo1/src/js/pages/custom/login/login-3.js ***! \
			 ******************************************************************/
			/* ! no static exports found */
			/***/
			(function(module, exports, __webpack_require__) {
				
				
				
			//  회원가입전 이용약관 동의 여부 
				$("button[name=next]").click(function() {
				
														if ($('#c1').prop('checked') == false
																) {   

															Swal
																	.fire(
																			{
																				text : "필수 약관에 동의 하셔야 합니다.",
																				icon : "error",
																				buttonsStyling : false,
																				confirmButtonText : "다시 확인하기",
																				customClass : {
																					confirmButton : "btn font-weight-bold btn-light"
																				}
																			})
																	.then(function() {
																		KTUtil.scrollTop();
																	});
															return false; // Do not change
															// wizard step,
															// further
															// action will
															// be handled by
															// he validator
														} else {
															window.location.href = "/signup";       
														}

														
				})
				
				
				
				
     
		/*		
			 function fn_idChk(){
				id=	$('#kt_login_signup_form [name="id"]').val();
					$.ajax({
						url : "duplicate_check",
						type : "get",
						dataType : "text",    
						data : id,   
						success : function(data){
							if(data == "false"){
								alert("중복된 아이디입니다.");
							}else if(data ==""){   
								$("#idChk").attr("value", "Y");
								alert("사용가능한 아이디입니다.");
							}
						}
					})  
				}*/ 
				
				
				

			
				
				
				
				
				
				
			})

			/** *** */
			});


$(function() {
	
	
	
	
	// ***** 회원가입 ************

	
	// 아이디 중복일때 알람뜨게
	$('input[name=id]').blur(function(){
		var min = /[a-zA-Z0-9_]{3,}/;   
	   
		if(min.test($('#kt_login_signup_form [name="id"]').val())){    
			$.ajax({
				url:"duplicate_check",
				type:'get',
				dataType:"text",
				data : {
					id : function() {
						return $('#id').val();
					}
				},       
				success:function(data){
					var color;   
					var ans;    
					if(data == "false"){
						alert("중복된 아이디입니다.");
						 
                        return false; 
					}    
				}
			})	
		
		}
	});                
	
	
	
	$("#kt_login_signup_form_submit_button").click(function() {
		
			
		 
		if(!$('#kt_login_signup_form [name="name"]').val()){  
			alert("실명을 입력해주세요"); 
			return false;
			$('#kt_login_signup_form [name="name"]').focus();              
		}
		var emailPattern = /[a-z0-9A-Z]{2,}@[a-z0-9-]{2,}\.[a-z0-9]{2,}/;
		if(!$('#kt_login_signup_form [name="email"]').val() || !emailPattern.test($('#kt_login_signup_form [name="email"]').val() ) ){
			alert("올바른 이메일주소를 입력해주세요");
			return false;
			$('#kt_login_signup_form [name="email"]').focus();    
		}    
		
		 var isPhoneNum = /([09]{9})/;        
		
		if(!$('#kt_login_signup_form [name="phone"]').val() || !isPhoneNum.test($('#kt_login_signup_form [name="phone"]').val() )){      
			alert("핸드폰 번호를 입력해주세요(10자리이상)");    
			return false;
			$('#phone').focus();
		}       
		
		var min = /[a-zA-Z0-9_]{3,}/;   
		
		if(! $('#kt_login_signup_form [name="id"]').val() || !min.test($('#kt_login_signup_form [name="id"]').val() )){     
			alert("형식에 맞는 아이디를 입력해주세요(3글자이상)");  
			return false;  
			
			$('#id').val();    
		}
			


		
		 var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{6,40}/;
		
		if(! $('#kt_login_signup_form [name="pw"]').val() || !pwPattern.test( $('#kt_login_signup_form [name="pw"]').val()) ){
			alert("올바른 비밀번호를 입력해주세요(6자리 이상)");   
			
			$('#pw').val();
			return false; 
		}
	
		if(! $('#kt_login_signup_form [name="pw2"]').val()){
			alert("비밀번호 재확인을 입력해주세요");   
			$('#pw2').val();
			return false;
		}
		 
		if( $('#kt_login_signup_form [name="pw2"]').val() !== $('#kt_login_signup_form [name="pw"]').val() ){
			alert("비밀번호가 일치하지않습니다. ");   
			$('#pw2').val();         
			return false;
		}  
		
		
		
		if(! $('#kt_login_signup_form [name="bod"]').val()){
			alert("생년월일을 입력해주세요");   
			$("bod").focus();
			return false;  
		}    
		
		 
		if($('#kt_login_signup_form [name="id"]').val() || min.test($('#kt_login_signup_form [name="id"]').val() )){   
	   
			$.ajax({
				url:"duplicate_check",
				type:'get',
				dataType:"text",
				data : {
					id : function() {
						return $('#id').val();
					}
				},       
				success:function(data){   
					 
					if(data == "false"){
						alert("중복된 아이디입니다.");
						
                     return false; 

					}else{      
						 
						var userArray = $('#kt_login_signup_form').serialize();
						
					    
						
						console.log("#값이 오는지 확인 ---" + userArray);
						// select option 으로 가져올때 이 문법으로 보내려면
						// select name="" 네임 인지 확인하기
						// https://java119.tistory.com/27
						$.ajax({
							url : 'signUp',
							data : userArray,  
							method : 'POST',
							dataType : 'text',
							success : function(textData) {
								console.log(textData);
								if (textData.trim() == "true") {
										
									kt_login_signup_form.name.value = textData.name;
									kt_login_signup_form.phone.value = textData.phone;
									kt_login_signup_form.id.value = textData.id;
									kt_login_signup_form.pw.value = textData.pw;
									kt_login_signup_form.pw2.value = textData.pw2;
									kt_login_signup_form.email.value = textData.email;
									kt_login_signup_form.bod.value = textData.bod;
									kt_login_signup_form.gender.value = textData.gender;
									      
								alert("회원가입이 완료되었습니다.");     
									location.href = '/signin';    
				   
								}else {
									alert("죄송합니다. 입력하신 값이 올바르지 않습니다.  ");    
								}

							},
							
						});    
						
						
						
						
						
						
						
						
						
						
					} 
					  
					
					
				}     
			})	
			
		};
		
		

	});


	
	
  
	
	});

     
