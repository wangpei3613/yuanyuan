;$(function() {
    /**
	 * 扩展easyui的validator插件rules，支持更多类型验证
	 */
    $.extend($.fn.validatebox.defaults.rules, {
        minLength: {
            // 判断最小长度
            validator: function(value, param) {
                return value.length >= param[0];
            },
            message: '最少输入 {0} 个字符'
        },
        maxLength: {
            // 判断最大长度
            validator: function(value, param) {
                var len;
                if (value == "")
                    len = 0;
                else
                    len = value.replace(/[^\x00-\xff]/g, '***').length;
                if (len <= parseInt(param[0]))
                    return true;
                return false;
            },
            message: "内容长度过长<br/>"
        },
        length: {
            // 长度
            validator: function(value, param) {
                var len = $.trim(value).length;
                return len >= param[0] && len <= param[1];
            },
            message: "输入内容长度必须介于{0}和{1}之间"
        },
        TimeCheck: {
            validator: function(value, param) {
                var s = $("input[name=" + param[0] + "]").val();
                // 因为日期是统一格式的所以可以直接比较字符串 否则需要Date.parse(_date)转换
                return value >= s;
            },
            message: '非法数据'
        },
        checkTime: {
            validator: function(value, param) {
                // 时间大于当前时间
                // 且小于传入时间
                var cd = new Date();
                var s = Date.parse(value);
                if (s > cd) {
                    var d = $("input[name=" + param[0] + "]").val();
                    if (d) {
                        d = Date.parse(d);
                        return s < d;
                    }
                    return true;
                }
                return false;
            },
            message: '非法数据'
        },
        //字符串小于
        strLess:{
        	validator: function(value, param) {
        		if(value && param && param[0]){
        			return value<=param[0];
        		}
        		return true;
            },
            message: '需小于{0}'
        },
        phone: {
            // 验证电话号码
            validator: function(value) {
                return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
            },
            message: '格式不正确,请使用下面格式:020-88888888'
        },
        mobile: {
            // 验证手机号码
            validator: function(value) {
                return /^(13|14|15|17|18|19)\d{9}$/i.test(value);
            },
            message: '手机号码格式不正确'
        },
        naturalNumber: {
            // 验自然数
            validator: function(value) {
                return /^[+]?[0-9]+\d*$/i.test(value);
            },
            message: '请输入自然数'
        },
        phoneAndMobile: {
            // 电话号码或手机号码
            validator: function(value) {
                if (/^(13|14|15|17|18|19)\d{9}$/i.test(value) || /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/i.test(value))
                    return true;
                return false;
            },
            message: '电话号码或手机号码格式不正确'
        },
        idcard: {
            // 验证身份证
            validator: function(value) {
                return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value) || /^\d{18}(\d{2}[A-Za-z0-9])?$/i.test(value);
            },
            message: '身份证号码格式不正确'
        },
        checkNumTwo: {
            // 验证整数或小数或负数
            validator: function(value) {

                return /^(\-|\+)?(0|[1-9]\d*)(\.\d{1,2})?$/i.test(value);
            },
            message: '请输入数字，最多两位小数'
        },
        checkNumFour: {
            // 验证整数或小数或负数
            validator: function(value) {

                return /^(\-|\+)?(0|[1-9]\d*)(\.\d{1,4})?$/i.test(value);
            },
            message: '请输入数字，最多四位小数'
        },
        checkNum: {
            // 验证整数或小数或负数
            validator: function(value) {

                return /^(\-|\+)?\d+(\.\d+)?$/i.test(value);
            },
            message: '请输入数字，并确保格式正确'
        },
        intOrFloat: {
            // 验证整数或小数
            validator: function(value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message: '请输入数字，并确保格式正确'
        },
        currency: {
            // 验证货币
            validator: function(value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message: '货币格式不正确'
        },
        qq: {
            // 验证QQ,从10000开始
            validator: function(value) {
                return /^[1-9]\d{4,9}$/i.test(value);
            },
            message: 'QQ号码格式不正确'
        },
        integer: {
            // 验证整数
            validator: function(value) {
                return /^-?\d+$/i.test(value);
            },
            message: '请输入整数'
        },
        positiveInteger: {
            // 验证正整数
            validator: function(value) {
                return /^[1-9]\d*$/i.test(value);
            },
            message: '请输入正整数'
        },
        chinese: {
            // 验证中文
            validator: function(value) {
                return /^[\u0391-\uFFE5]+$/i.test(value);
            },
            message: '请输入中文'
        },
        chineseAndLength: {
            // 验证中文及长度
            validator: function(value, param) {
                var len = $.trim(value).length;
                if (len >= param[0] && len <= param[1]) {
                    return /^[\u0391-\uFFE5]+$/i.test(value);
                }
            },
            message: '请输入中文'
        },
        english: {
            // 验证英语
            validator: function(value) {
                return /^[A-Za-z]+$/i.test(value);
            },
            message: '请输入英文'
        },
        englishAndLength: {
            // 验证英语及长度
            validator: function(value, param) {
                var len = $.trim(value).length;
                if (len >= param[0] && len <= param[1]) {
                    return /^[A-Za-z]+$/i.test(value);
                }
            },
            message: '请输入英文'
        },
        englishUpperCase: {
            // 验证英语大写
            validator: function(value) {
                return /^[A-Z]+$/.test(value);
            },
            message: '请输入大写英文'
        },
        unnormal: {
            // 验证是否包含空格和非法字符
            validator: function(value) {
                return /.+/i.test(value);
            },
            message: '输入值不能为空和包含其他非法字符'
        },
        username: {
            // 验证用户名
            validator: function(value) {
                return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
            },
            message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
        },
        faxno: {
            // 验证传真
            validator: function(value) {
                return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
            },
            message: '传真号码不正确'
        },
        zip: {
            // 验证邮政编码
            validator: function(value) {
                return /^[1-9]\d{5}$/i.test(value);
            },
            message: '邮政编码格式不正确'
        },
        ip: {
            // 验证IP地址
            validator: function(value) {
                return /d+.d+.d+.d+/i.test(value);
            },
            message: 'IP地址格式不正确'
        },
        name: {
            // 验证姓名，可以是中文或英文
            validator: function(value) {
                return /^[\u0391-\uFFE5]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
            },
            message: '请输入姓名'
        },
        engOrChinese: {
            // 可以是中文或英文
            validator: function(value) {
                return /^[\u0391-\uFFE5]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
            },
            message: '请输入中文'
        },
        engOrChineseAndLength: {
            // 可以是中文或英文
            validator: function(value) {
                var len = $.trim(value).length;
                if (len >= param[0] && len <= param[1]) {
                    return /^[\u0391-\uFFE5]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
                }
            },
            message: '请输入中文或英文'
        },
        carNo: {
            validator: function(value) {
                return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
            },
            message: '车牌号码无效（例：粤B12350）'
        },
        carenergin: {
            validator: function(value) {
                return /^[a-zA-Z0-9]{16}$/.test(value);
            },
            message: '发动机型号无效(例：FG6H012345654584)'
        },
        same: {
            validator: function(value, param) {
                if ($(param[0]).val() != "" && value != "") {
                    return $(param[0]).val() == value;
                } else {
                    return true;
                }
            },
            message: '两次输入的密码不一致!'
        },
        checkWSDL: {
            validator: function(value, param) {
                var reg = "^(http://|([0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}[.]{1}[0-9]{1,3}:[0-9]{1,4}))[/a-zA-Z0-9._%&:=(),?+]*[?]{1}wsdl$";
                return reg.test(value);
            },
            message: '请输入合法的WSDL地址'
        },
        checkIp: {
            // 验证IP地址
            validator: function(value) {
                var reg = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/;
                return reg.test(value);
            },
            message: 'IP地址格式不正确'
        }
    });
});
