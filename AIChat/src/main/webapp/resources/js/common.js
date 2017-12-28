
/**
 * SchShare Common
 */
var SchShareObj = {
	conf: {
		sitename : 'SchShare',
		contextPath : '',
		version : '1.0',
		webImgPath: {
			//개발
			address: 'http://localhost',
			fileDrive: 'D:',

			getChangeWebPath: function(path) {
				if (!path) return '';
				
				var ret = path.replace(this.fileDrive, this.address);
				
				return ret;
			}
		}
	},
	
	// 데이터 처리
	data: {
		ajax : function (url, _config) {
			try {
                if (!_config || !url) throw 'SchShareObj.data.ajax configuration error!\ncheck your script code!';

                if (!_config.method) _config.method = 'post';
                if (_config.async == undefined) _config.async = true;
                if (_config.contentType == undefined) _config.contentType = 'application/x-www-form-urlencoded';
                if (!_config.data) _config.data = 'json';

                jQuery.ajax({
                    url: url,
                    data: _config.pars,
                    type: _config.method,
                    async: _config.async,
                    contentType: _config.contentType,
                    dataType: _config.data,
                    success: function(res) {
                    	_config.onsucc(res);
                    },
                    error: function(res) {
                    	if (_config.onerr != undefined) {
                    		_config.onerr(res.responseText);	
                    	} else {
                    		alert('onError : ' + res.responseText);
                    	}
                    },
                    timeout: function (res) {
                        alert('onTimeout : ' + res.responseText);
                    }
                });
            } catch (e) { alert(e); }
		},
		
		// bind
		bind: {
			combo: function(_config) {
				$('#' + _config.renderId).empty();
				
				var po = [];
				for (var i = 0; i < _config.object.length; i++) {
					po.push("<option value=\"" + _config.object[i]['value'] + "\"");
					if (_config.selected === _config.object[i]['value']){
						po.push(" selected ");
					}
					po.push(">" + _config.object[i]['text'] + "</option>");
				}
		
				$('#' + _config.renderId).append(po.join(''));
			}
		},
		
		// 바이트 계산
		getBytes: function(msg) {
        	var b, i, c;
        	for (b = i = 0; c = msg.charCodeAt(i++); b += c >> 11 ? 3 : c >> 7 ? 2 : 1);
        	return b;
        },
        
        getSizeList: function() {
        	var str = '';
            for (var i = 1; i <= 100; i++) {
            	if (i !== 100) {
            		str = str.concat(i).concat(':').concat(i).concat(';');	
            	} else {
            		str = str.concat(i).concat(':').concat(i).concat('');
            	}
            }
            return str.replace(/ /g, '');
        }
	},
	
	// 검증
	vaild: {
		setNumeric: function() {
			$('.numeric').css('imeMode','disabled').keypress(function(event) {
				if(event.which && (event.which < 48 || event.which > 57) ) {
					event.preventDefault();
					event.stopPropagation();
				}
			}).keyup(function(){
				if($(this).val() != null && $(this).val() != '' ) {
					$(this).val($(this).val().replace(/[^0-9]/g, '') );
				}
			});
		},
		
		number: function(_val) {
			var regExp =  /^[0-9]+$/;
			
			if (!_val.match(regExp)) {
				return false;
			}
			
			return true;
		}
	},
	
	// 팝업
	popup: {
		open: function(_config) {
			if (!_config.url) throw new ('SchShareObj.popup.open url not defined !');
			if (!_config.title) _config.title = SchShareObj.conf.sitename + '_popup';
			if (!_config.method) _config.method = 'get';
			if (!_config.resizeable) _config.resizeable = 'no';
			if (!_config.scrollbars) _config.scrollbars = 'yes';
			if (!_config.toolbars) _config.toolbars = 'no';
			if (!_config.pars) _config.pars = '';

			var options = {top: 0, left: 0, width: 800, height: screen.availHeight - 60, title: '', resizeable: 'no', scrollbars: 'yes', toolbars: 'no', status: 'no', menu: 'no', mode: 'center'};

			if (_config.width != undefined && ("" + _config.width).indexOf('%') < 1) {
				options.left = (screen.availWidth - _config.width) / 2;
			}

			if (_config.height != undefined && ("" + _config.height).indexOf('%') < 1) {
				options.top = (screen.height - _config.height) / 2;
			}

            var opt = 'top=' + options.top + ',left=' + options.left + ',resizable=' + _config.resizeable + ',scrollbars=' + _config.scrollbars;
            	opt += ',toolbars=' + _config.toolbars + ',status=' + options.status + ',menu=' + options.menu;

            if (_config.width ) {
            	opt += ',width=' + _config.width;
            }
            
            if (_config.height ) {
            	opt += ',height=' + _config.height;
            }
            var pars = _config.pars.length != 0 ? '?' + _config.pars : '';
            var url = _config.url + pars;
                        
            var win = window.open(url, _config.title, opt);
            
            if (parseInt(navigator.appVersion) >= 4) {
                win.focus();
            }
		}
	},
	
	// 날짜 
	date: {
		oDate: new Date(),
		
		// 달력 초기화
		setDatePicker: function() {
			$.datepicker.setDefaults({
    		    prevText: '이전달',
                nextText: '다음달',
                monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
                showMonthAfterYear: true,
                dateFormat: 'yy-mm-dd',
                showAnim: '',
                showOn: 'both',
                changeYear : true,
                changeMonth : true,
                showOptions: {direction: 'horizontal'},
                duration: '',
                buttonImage: '<c:url value="/resources/img/icon/icon_date.png"/>',
                buttonImageOnly: true
            });
		},
		
		// 현재날짜 반환
		getCurrentDate: function(delimeter) {
			var oDate = SchShareObj.date.oDate;
			
			var year = oDate.getFullYear();
			var month = oDate.getMonth() + 1;
			var date = oDate.getDate();
			
			var today = year + '-' +  addZero(month) + '-' + addZero(date);
			
			return today;
		}
	}
};

function log(str) {
    if (typeof console === 'undefined') {
        return false;
    };
    
    console.log(str);
}

/**
 *  날짜, 시간앞에 0 붙이기
 */
function addZero(n) {
	if(n == null) return "00"; 
	
	return n < 10 ? "0" + Number(n) : n;
}

/**
 * 숫자에 천단위 구분자 넣기
 **/
function addComma(n) {
	
	if(n == null || n == 0) return "0";
	
	if(isNaN(n)) return n;	//숫자 형식이 아닌 경우 그대로 리턴
	
	n = n + "";	//replace를 위해 문자열로 변환
	
	return n.replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,');
}
