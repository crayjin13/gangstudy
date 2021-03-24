(function() {
	var ps;
	
    defaults = {
		sInputMsg: "시작시간을 선택해주세요.",
		eInputMsg: "종료시간을 선택해주세요.",
		dInputMsg: "이용날짜"
	}
	
	addEvent = function(el, e, callback)
    {
        el.addEventListener(e, callback)
    },

	sequence = function(callforth, callback) {
		if(callback!=null) { callback() }
	}
	
	requestTimes = function(target, data, url, callback) {
		$.ajax({
			url : url,
			type : "GET",
			data : data,
			success : function(response) {
				sequence(addOptions(target, response), callback)
			},
			error : function(){
				alert("requestTimes error")
			}
		})
	}
	
	addOption = function(target, text, value) {
		var option = document.createElement("option")
		option.text = text
		option.value = value
		option.name = value
		target.options.add(option)
	}
	addOptions = function(target, values) {
		for(var i = 0; i < values.length; i++) {
			addOption(target, values[i], values[i])
		}
	}
	
	removeOptions = function (selectElement) {
		var i, L = selectElement.options.length - 1
		for(i = L; i >= 0; i--) {
			selectElement.remove(i)
		}
	}
	sObj = function() {
		return {
			"date" : ps.dInput.value,
			"book_no" : ps.book_no
		}
	}
	eObj = function() {
		return {
			"date" : ps.dInput.value,
			"startTime" : ps.sInput.value,
			"book_no" : ps.book_no
		}
	}
	
	svgPaints = function(input) {
		var icon = input.nextElementSibling
		var object = icon.children[0].contentDocument
		var svg = object.getElementsByTagName("svg")[0]
		var paints = svg.getElementsByClassName("paint")
		return paints
	}
	
	getTime = function(target) {
		var date = new Date(),
			time = target.value.split(":")
		
		date.setHours(time[0])
		date.setMinutes(time[1])
		
		return date
	}
		
	BookForm = function(params) {
		ps = this.config(params)
		addEvent(ps.dInput, "change", this.onChangeDate)
		addEvent(ps.sInput, "change", this.onChangeStart)
		
		addEvent(ps.dInput, "focus", this.onFocued)
		addEvent(ps.sInput, "focus", this.onFocued)
		addEvent(ps.eInput, "focus", this.onFocued)
		addEvent(ps.pInput, "focus", this.onFocued)
		
		addEvent(ps.dInput, "blur", this.onBlur)
		addEvent(ps.sInput, "blur", this.onBlur)
		addEvent(ps.eInput, "blur", this.onBlur)
		addEvent(ps.pInput, "blur", this.onBlur)
	}
	
	BookForm.prototype = {
		
		config: function(params) {
			
			params.dValue = params.dInput.getAttribute("value")
			params.sValue = params.sInput.getAttribute("value")
			params.eValue = params.eInput.getAttribute("value")
			
			return params;
		},
		
		onChangeDate: function() {
			removeOptions(ps.sInput)
			removeOptions(ps.eInput)
			addOption(ps.sInput, defaults.sInputMsg, "")
			requestTimes(ps.sInput, sObj(), ps.sURL)
		},
		
		onChangeStart: function() {
			removeOptions(ps.eInput)
			if(ps.sInput.value == "") {
				return;
			}
			addOption(ps.eInput, defaults.eInputMsg, "")
			requestTimes(ps.eInput, eObj(), ps.eURL)
		},
		
		onPagesShow: function() {
			if(ps.dValue != "") {
				requestTimes(ps.sInput, sObj(), ps.sURL, function() {
					ps.sInput.value = ps.sValue
					if(ps.sValue != "") {
						requestTimes(ps.eInput, eObj(), ps.eURL, function() {
							ps.eInput.value = ps.eValue
						})
					}
				})
				
				ps.pInput.value = ps.pInput.getAttribute("people")
			} else {
				ps.dInput.value = defaults.dInputMsg
			}
		},
		
		onFocued: function() {
			var paints = svgPaints(this)
			
			for(var i = 0; i < paints.length; i++) {
				var paint = paints[i]
				if(paint.classList.contains("normal")) {
					paint.classList.remove("normal")
				}
				if(paint.classList.contains("active")) {
					paint.classList.remove("active")
				}
				paint.classList.add("clicked")
				this.classList.add("active")
			}
		},
	
		onBlur: function() {
			var paints = svgPaints(this)
			
			for(var i = 0; i < paints.length; i++) {
				var paint = paints[i]
				if(paint.classList.contains("normal")) {
					paint.classList.remove("normal")
				}
				if(paint.classList.contains("clicked")) {
					paint.classList.remove("clicked")
				}
				paint.classList.add("active")
			}
		},
		
		validation: function() {
			if(ps.dInput.value == "") {
				return false
			} else if(ps.sInput.value == "") {
				return false
			} else if(ps.eInput.value == "") {
				return false
			} else if(ps.pInput.value == "") {
				return false
			} else {
				return true
			}
		},
		
		getInterval: function() {
			var sTime = getTime(ps.sInput),
				eTime = getTime(ps.eInput)
				
			var interval = eTime - sTime
			return interval / 1000 / 60 / 60
		},
		
		getSInput: function() {
			return ps.sInput
		}
		
		
	}
})()

function getParam(sname) {
	var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
        temp = params[i].split("=");
        if ([temp[0]] == sname) { sval = temp[1]; }
    }
    return sval;
}

 /* pikaday */
function getFormatDate(date){
	var year = date.getFullYear()
	var month = (1 + date.getMonth())
	month = month >= 10 ? month : "0" + month
	var day = date.getDate()
	day = day >= 10 ? day : "0" + day
	return year + "-" + month + "-" + day
}
var minDate = new Date()
var maxDate = new Date()
maxDate.setDate(minDate.getDate() + 7)

var pika = new Pikaday({
    field: document.getElementById("dateInput"),
    format: 'YYYY-MM-DD',
    toString : function(date) {
        return getFormatDate(date)
	}
})
pika.setMinDate(minDate)
pika.setMaxDate(maxDate)




