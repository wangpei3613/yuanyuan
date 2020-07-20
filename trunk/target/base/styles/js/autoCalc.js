(function(){
	window.autoCalc = {
			//权益计算
		   autoAssets:function(newVal,oldVal){
		    	var initialAssets = $('#initialAssets').numberbox('getValue');
		    	var accumAssets = $('#accumAssets').numberbox('getValue');
		    	var familyAssets = $('#familyAssets').numberbox('getValue');
		    	var fatherAssets = $('#fatherAssets').numberbox('getValue');
		    	var currentRight = (Number(initialAssets)+Number(accumAssets)+Number(familyAssets)+Number(fatherAssets)).toFixed(2); ;
		    	$('#currentRight').numberbox('setValue',currentRight);
		    	var selfRatio = ((currentRight-Number(fatherAssets))/currentRight*100).toFixed(2);
		    	$('#selfRatio').numberbox('setValue',selfRatio);
		    },
		    autoProLossBZL:function(newVal,oldVal){
		    	var sy_qn_zyywsr = $('#sy_qn_zyywsr').numberbox('getValue');
		    	var sy_qn_tdsr = $('#sy_qn_tdsr').numberbox('getValue');
		    	var sy_qn_wgsr = $('#sy_qn_wgsr').numberbox('getValue');
		    	var sy_qn_qtsr = $('#sy_qn_qtsr').numberbox('getValue');
		    	var sy_qn_zyywcb = $('#sy_qn_zyywcb').numberbox('getValue');
		    	var sy_qn_zsr = (Number(sy_qn_zyywsr)+Number(sy_qn_tdsr)+Number(sy_qn_wgsr)+Number(sy_qn_qtsr)).toFixed(2);
		    	$('#sy_qn_zsr').numberbox('setValue',sy_qn_zsr);
		    	var sy_qn_zzc = (Number(sy_qn_zyywcb)).toFixed(2);
		    	$('#sy_qn_zzc').numberbox('setValue',sy_qn_zzc);
		    	var sy_qn_jlr = (Number(sy_qn_zsr)-Number(sy_qn_zzc)).toFixed(2);
		    	$('#sy_qn_jlr').numberbox('setValue',sy_qn_jlr);
		    },
		    autoProLossGXL:function(newVal,oldVal){
		    	/**var sy_sxq_gzsr = $('#sy_sxq_gzsr').numberbox('getValue');
		    	var sy_sxq_qtsr = $('#sy_sxq_qtsr').numberbox('getValue');
		    	var sy_sxq_sdf = $('#sy_sxq_sdf').numberbox('getValue');
		    	var sy_sxq_txf = $('#sy_sxq_txf').numberbox('getValue');
		    	var sy_sxq_jtf = $('#sy_sxq_jtf').numberbox('getValue');
		    	var sy_sxq_jtkz = $('#sy_sxq_jtkz').numberbox('getValue');
		    	var sy_sxq_lxzc = $('#sy_sxq_lxzc').numberbox('getValue');
		    	var sy_sxq_qtzc = $('#sy_sxq_qtzc').numberbox('getValue');
		    	var sy_sxq_zsr = (Number(sy_sxq_gzsr)+Number(sy_sxq_qtsr)).toFixed(2);
		    	$('#sy_sxq_zsr').numberbox('setValue',sy_sxq_zsr);
		    	var sy_sxq_zzc = (Number(sy_sxq_sdf)+Number(sy_sxq_txf)+Number(sy_sxq_jtf)+Number(sy_sxq_jtkz)+Number(sy_sxq_lxzc)+Number(sy_sxq_qtzc)).toFixed(2);
		    	$('#sy_sxq_zzc').numberbox('setValue',sy_sxq_zzc);
		    	var sy_sxq_jlr = (Number(sy_sxq_zsr)-Number(sy_sxq_zzc)).toFixed(2);
		    	$('#sy_sxq_jlr').numberbox('setValue',sy_sxq_jlr);
		    	$('#sy_sxq_ydpjjlr').numberbox('setValue',(sy_sxq_jlr/12).toFixed(2));**/
		    	
		    	
		    	var sy_qn_gzsr = $('#sy_qn_gzsr').numberbox('getValue');
		    	var sy_qn_qtsr = $('#sy_qn_qtsr').numberbox('getValue');
		    	var sy_qn_sdf = $('#sy_qn_sdf').numberbox('getValue');
		    	var sy_qn_txf = $('#sy_qn_txf').numberbox('getValue');
		    	var sy_qn_jtf = $('#sy_qn_jtf').numberbox('getValue');
		    	var sy_qn_jtkz = $('#sy_qn_jtkz').numberbox('getValue');
		    	var sy_qn_lxzc = $('#sy_qn_lxzc').numberbox('getValue');
		    	var sy_qn_qtzc = $('#sy_qn_qtzc').numberbox('getValue');
		    	var sy_qn_zsr = (Number(sy_qn_gzsr)+Number(sy_qn_qtsr)).toFixed(2);
		    	$('#sy_qn_zsr').numberbox('setValue',sy_qn_zsr);
		    	var sy_qn_zzc = (Number(sy_qn_sdf)+Number(sy_qn_txf)+Number(sy_qn_jtf)+Number(sy_qn_jtkz)+Number(sy_qn_lxzc)+Number(sy_qn_qtzc)).toFixed(2);
		    	$('#sy_qn_zzc').numberbox('setValue',sy_qn_zzc);
		    	var sy_qn_jlr = (Number(sy_qn_zsr)-Number(sy_qn_zzc)).toFixed(2);
		    	$('#sy_qn_jlr').numberbox('setValue',sy_qn_jlr);
		    	$('#sy_qn_ydpjjlr').numberbox('setValue',(sy_qn_jlr/12).toFixed(2));
		    	
		    	
		    	var sy_jn_gzsr = $('#sy_jn_gzsr').numberbox('getValue');
		    	var sy_jn_qtsr = $('#sy_jn_qtsr').numberbox('getValue');
		    	var sy_jn_sdf = $('#sy_jn_sdf').numberbox('getValue');
		    	var sy_jn_txf = $('#sy_jn_txf').numberbox('getValue');
		    	var sy_jn_jtf = $('#sy_jn_jtf').numberbox('getValue');
		    	var sy_jn_jtkz = $('#sy_jn_jtkz').numberbox('getValue');
		    	var sy_jn_lxzc = $('#sy_jn_lxzc').numberbox('getValue');
		    	var sy_jn_qtzc = $('#sy_jn_qtzc').numberbox('getValue');
		    	var sy_jn_zsr = (Number(sy_jn_gzsr)+Number(sy_jn_qtsr)).toFixed(2);
		    	$('#sy_jn_zsr').numberbox('setValue',sy_jn_zsr);
		    	var sy_jn_zzc = (Number(sy_jn_sdf)+Number(sy_jn_txf)+Number(sy_jn_jtf)+Number(sy_jn_jtkz)+Number(sy_jn_lxzc)+Number(sy_jn_qtzc)).toFixed(2);
		    	$('#sy_jn_zzc').numberbox('setValue',sy_jn_zzc);
		    	var sy_jn_jlr = (Number(sy_jn_zsr)-Number(sy_jn_zzc)).toFixed(2);
		    	$('#sy_jn_jlr').numberbox('setValue',sy_jn_jlr);
		    	$('#sy_jn_ydpjjlr').numberbox('setValue',(sy_jn_jlr/12).toFixed(2));
		    },
		    autoProLossJYL:function(newVal,oldVal){
		    	/**var sy_sxq_zyywsr = $('#sy_sxq_zyywsr').numberbox('getValue');
		    	var sy_sxq_gzsr = $('#sy_sxq_gzsr').numberbox('getValue');
		    	var sy_sxq_tdsr = $('#sy_sxq_tdsr').numberbox('getValue');
		    	var sy_sxq_wgsr = $('#sy_sxq_wgsr').numberbox('getValue');
		    	var sy_sxq_qtsr = $('#sy_sxq_qtsr').numberbox('getValue');
		    	var sy_sxq_zyywcb = $('#sy_sxq_zyywcb').numberbox('getValue');
		    	var sy_sxq_zfgz  = $('#sy_sxq_zfgz').numberbox('getValue');
		    	var sy_sxq_zfzj = $('#sy_sxq_zfzj').numberbox('getValue');
		    	var sy_sxq_sdf = $('#sy_sxq_sdf').numberbox('getValue');
		    	var sy_sxq_txf = $('#sy_sxq_txf').numberbox('getValue');
		    	var sy_sxq_jtf = $('#sy_sxq_jtf').numberbox('getValue');
		    	var sy_sxq_clss = $('#sy_sxq_clss').numberbox('getValue');
		    	var sy_sxq_ggjwhf = $('#sy_sxq_ggjwhf').numberbox('getValue');
		    	var sy_sxq_zdf = $('#sy_sxq_zdf').numberbox('getValue');
		    	var sy_sxq_jtkz = $('#sy_sxq_jtkz').numberbox('getValue');
		    	var sy_sxq_lxzc = $('#sy_sxq_lxzc').numberbox('getValue');
		    	var sy_sxq_qtzc = $('#sy_sxq_qtzc').numberbox('getValue');
		    	var sy_sxq_zsr = (Number(sy_sxq_zyywsr)+Number(sy_sxq_gzsr)+Number(sy_sxq_tdsr)+Number(sy_sxq_wgsr)+Number(sy_sxq_qtsr)).toFixed(2);
		    	$('#sy_sxq_zsr').numberbox('setValue',sy_sxq_zsr);
		    	var sy_sxq_zzc = (Number(sy_sxq_zyywcb)+Number(sy_sxq_zfgz)+Number(sy_sxq_zfzj)+Number(sy_sxq_sdf)+Number(sy_sxq_txf)+Number(sy_sxq_jtf)+Number(sy_sxq_clss)+Number(sy_sxq_ggjwhf)+Number(sy_sxq_zdf)+Number(sy_sxq_jtkz)+Number(sy_sxq_lxzc)+Number(sy_sxq_qtzc)).toFixed(2);
		    	$('#sy_sxq_zzc').numberbox('setValue',sy_sxq_zzc);
		    	var sy_sxq_zymlr = (Number(sy_sxq_zyywsr)-Number(sy_sxq_zyywcb)).toFixed(2);
		    	$('#sy_sxq_zymlr').numberbox('setValue',sy_sxq_zymlr);
		    	var sy_sxq_sds = $('#sy_sxq_sds').numberbox('getValue');
		    	var sy_sxq_jlr = (Number(sy_sxq_zsr)-Number(sy_sxq_zzc)-Number(sy_sxq_sds)).toFixed(2);
		    	$('#sy_sxq_jlr').numberbox('setValue',sy_sxq_jlr);
		    	$('#sy_sxq_ydpjjlr').numberbox('setValue',(sy_sxq_jlr/12).toFixed(2));**/
		    	
		    	
		    	var sy_qn_zyywsr = $('#sy_qn_zyywsr').numberbox('getValue');
		    	var sy_qn_gzsr = $('#sy_qn_gzsr').numberbox('getValue');
		    	var sy_qn_tdsr = $('#sy_qn_tdsr').numberbox('getValue');
		    	var sy_qn_wgsr = $('#sy_qn_wgsr').numberbox('getValue');
		    	var sy_qn_qtsr = $('#sy_qn_qtsr').numberbox('getValue');
		    	var sy_qn_zyywcb = $('#sy_qn_zyywcb').numberbox('getValue');
		    	var sy_qn_zfgz  = $('#sy_qn_zfgz').numberbox('getValue');
		    	var sy_qn_zfzj = $('#sy_qn_zfzj').numberbox('getValue');
		    	var sy_qn_sdf = $('#sy_qn_sdf').numberbox('getValue');
		    	var sy_qn_txf = $('#sy_qn_txf').numberbox('getValue');
		    	var sy_qn_jtf = $('#sy_qn_jtf').numberbox('getValue');
		    	var sy_qn_clss = $('#sy_qn_clss').numberbox('getValue');
		    	var sy_qn_ggjwhf = $('#sy_qn_ggjwhf').numberbox('getValue');
		    	var sy_qn_zdf = $('#sy_qn_zdf').numberbox('getValue');
		    	var sy_qn_jtkz = $('#sy_qn_jtkz').numberbox('getValue');
		    	var sy_qn_lxzc = $('#sy_qn_lxzc').numberbox('getValue');
		    	var sy_qn_qtzc = $('#sy_qn_qtzc').numberbox('getValue');
		    	var sy_qn_zsr = (Number(sy_qn_zyywsr)+Number(sy_qn_gzsr)+Number(sy_qn_tdsr)+Number(sy_qn_wgsr)+Number(sy_qn_qtsr)).toFixed(2);
		    	$('#sy_qn_zsr').numberbox('setValue',sy_qn_zsr);
		    	var sy_qn_zzc = (Number(sy_qn_zyywcb)+Number(sy_qn_zfgz)+Number(sy_qn_zfzj)+Number(sy_qn_sdf)+Number(sy_qn_txf)+Number(sy_qn_jtf)+Number(sy_qn_clss)+Number(sy_qn_ggjwhf)+Number(sy_qn_zdf)+Number(sy_qn_jtkz)+Number(sy_qn_lxzc)+Number(sy_qn_qtzc)).toFixed(2);
		    	$('#sy_qn_zzc').numberbox('setValue',sy_qn_zzc);
		    	var sy_qn_zymlr = (Number(sy_qn_zyywsr)-Number(sy_qn_zyywcb)).toFixed(2);
		    	$('#sy_qn_zymlr').numberbox('setValue',sy_qn_zymlr);
		    	var sy_qn_sds = $('#sy_qn_sds').numberbox('getValue');
		    	var sy_qn_jlr = (Number(sy_qn_zsr)-Number(sy_qn_zzc)-Number(sy_qn_sds)).toFixed(2);
		    	$('#sy_qn_jlr').numberbox('setValue',sy_qn_jlr);
		    	$('#sy_qn_ydpjjlr').numberbox('setValue',(sy_qn_jlr/12).toFixed(2));
		    	
		    	
		    	
		    	var sy_jn_zyywsr = $('#sy_jn_zyywsr').numberbox('getValue');
		    	var sy_jn_gzsr = $('#sy_jn_gzsr').numberbox('getValue');
		    	var sy_jn_tdsr = $('#sy_jn_tdsr').numberbox('getValue');
		    	var sy_jn_wgsr = $('#sy_jn_wgsr').numberbox('getValue');
		    	var sy_jn_qtsr = $('#sy_jn_qtsr').numberbox('getValue');
		    	var sy_jn_zyywcb = $('#sy_jn_zyywcb').numberbox('getValue');
		    	var sy_jn_zfgz  = $('#sy_jn_zfgz').numberbox('getValue');
		    	var sy_jn_zfzj = $('#sy_jn_zfzj').numberbox('getValue');
		    	var sy_jn_sdf = $('#sy_jn_sdf').numberbox('getValue');
		    	var sy_jn_txf = $('#sy_jn_txf').numberbox('getValue');
		    	var sy_jn_jtf = $('#sy_jn_jtf').numberbox('getValue');
		    	var sy_jn_clss = $('#sy_jn_clss').numberbox('getValue');
		    	var sy_jn_ggjwhf = $('#sy_jn_ggjwhf').numberbox('getValue');
		    	var sy_jn_zdf = $('#sy_jn_zdf').numberbox('getValue');
		    	var sy_jn_jtkz = $('#sy_jn_jtkz').numberbox('getValue');
		    	var sy_jn_lxzc = $('#sy_jn_lxzc').numberbox('getValue');
		    	var sy_jn_qtzc = $('#sy_jn_qtzc').numberbox('getValue');
		    	var sy_jn_zsr = (Number(sy_jn_zyywsr)+Number(sy_jn_gzsr)+Number(sy_jn_tdsr)+Number(sy_jn_wgsr)+Number(sy_jn_qtsr)).toFixed(2);
		    	$('#sy_jn_zsr').numberbox('setValue',sy_jn_zsr);
		    	var sy_jn_zzc = (Number(sy_jn_zyywcb)+Number(sy_jn_zfgz)+Number(sy_jn_zfzj)+Number(sy_jn_sdf)+Number(sy_jn_txf)+Number(sy_jn_jtf)+Number(sy_jn_clss)+Number(sy_jn_ggjwhf)+Number(sy_jn_zdf)+Number(sy_jn_jtkz)+Number(sy_jn_lxzc)+Number(sy_jn_qtzc)).toFixed(2);
		    	$('#sy_jn_zzc').numberbox('setValue',sy_jn_zzc);
		    	var sy_jn_zymlr = (Number(sy_jn_zyywsr)-Number(sy_jn_zyywcb)).toFixed(2);
		    	$('#sy_jn_zymlr').numberbox('setValue',sy_jn_zymlr);
		    	var sy_jn_sds = $('#sy_jn_sds').numberbox('getValue');
		    	var sy_jn_jlr = (Number(sy_jn_zsr)-Number(sy_jn_zzc)-Number(sy_jn_sds)).toFixed(2);
		    	$('#sy_jn_jlr').numberbox('setValue',sy_jn_jlr);
		    	$('#sy_jn_ydpjjlr').numberbox('setValue',(sy_jn_jlr/12).toFixed(2));
		    },
		    autoProLossComp:function(newVal,oldVal){
		    	//主营业务收入
		    	var sy_qn_zyywsr = $('#sy_qn_zyywsr').numberbox('getValue');
		    	//主营业务成本
		    	var sy_qn_zyywcb = $('#sy_qn_zyywcb').numberbox('getValue');
		    	//营业费用
		    	var sy_qn_yyfy = $('#sy_qn_yyfy').numberbox('getValue');
		    	//主营业务税金及附加
		    	var sy_qn_zyywsj = $('#sy_qn_zyywsj').numberbox('getValue');
		    	//主营业务利润=主营业务收入-主营业务成本-营业费用-主营业务税金及附加
		    	var sy_qn_zyywlr = (Number(sy_qn_zyywsr)-Number(sy_qn_zyywcb)-Number(sy_qn_yyfy)-Number(sy_qn_zyywsj)).toFixed(4);
		    	$('#sy_qn_zyywlr').numberbox('setValue',sy_qn_zyywlr);
		    	//其他业务利润
		    	var sy_qn_qtywlr = $('#sy_qn_qtywlr').numberbox('getValue');
		    	//管理费用
		    	var sy_qn_glfy  = $('#sy_qn_glfy').numberbox('getValue');
		    	//财务费用
		    	var sy_qn_cwfy = $('#sy_qn_cwfy').numberbox('getValue');
		    	//营业利润=主营业务利润+其他业务利润-管理费用-财务费用
		    	var sy_qn_yylr = (Number(sy_qn_zyywlr)+Number(sy_qn_qtywlr)-Number(sy_qn_glfy)-Number(sy_qn_cwfy)).toFixed(4);
		    	$('#sy_qn_yylr').numberbox('setValue',sy_qn_yylr);
		    	//投资收益
		    	var sy_qn_tzsy = $('#sy_qn_tzsy').numberbox('getValue');
		    	//补贴收入
		    	var sy_qn_btsr = $('#sy_qn_btsr').numberbox('getValue');
		    	//营业外收入
		    	var sy_qn_yywsr = $('#sy_qn_yywsr').numberbox('getValue');
		    	//营业外支出
		    	var sy_qn_yywzc = $('#sy_qn_yywzc').numberbox('getValue');
		    	//利润总额=营业利润+投资收益+补贴收入+营业外收入-营业外支出
		    	var sy_qn_lrze = (Number(sy_qn_yylr)+Number(sy_qn_tzsy)+Number(sy_qn_btsr)+Number(sy_qn_yywsr)-Number(sy_qn_yywzc)).toFixed(4);
		    	$('#sy_qn_lrze').numberbox('setValue',sy_qn_lrze);
		    	//所得税
		    	var sy_qn_sds = $('#sy_qn_sds').numberbox('getValue');
		    	//净利润=利润总额-所得税
		    	var sy_qn_jlr = (Number(sy_qn_lrze)-Number(sy_qn_sds)).toFixed(4);
		    	$('#sy_qn_jlr').numberbox('setValue',sy_qn_jlr);
		    	
		    	
		    	
		    	//主营业务收入
		    	var sy_jn_zyywsr = $('#sy_jn_zyywsr').numberbox('getValue');
		    	//主营业务成本
		    	var sy_jn_zyywcb = $('#sy_jn_zyywcb').numberbox('getValue');
		    	//营业费用
		    	var sy_jn_yyfy = $('#sy_jn_yyfy').numberbox('getValue');
		    	//主营业务税金及附加
		    	var sy_jn_zyywsj = $('#sy_jn_zyywsj').numberbox('getValue');
		    	//主营业务利润=主营业务收入-主营业务成本-营业费用-主营业务税金及附加
		    	var sy_jn_zyywlr = (Number(sy_jn_zyywsr)-Number(sy_jn_zyywcb)-Number(sy_jn_yyfy)-Number(sy_jn_zyywsj)).toFixed(4);
		    	$('#sy_jn_zyywlr').numberbox('setValue',sy_jn_zyywlr);
		    	//其他业务利润
		    	var sy_jn_qtywlr = $('#sy_jn_qtywlr').numberbox('getValue');
		    	//管理费用
		    	var sy_jn_glfy  = $('#sy_jn_glfy').numberbox('getValue');
		    	//财务费用
		    	var sy_jn_cwfy = $('#sy_jn_cwfy').numberbox('getValue');
		    	//营业利润=主营业务利润+其他业务利润-管理费用-财务费用
		    	var sy_jn_yylr = (Number(sy_jn_zyywlr)+Number(sy_jn_qtywlr)-Number(sy_jn_glfy)-Number(sy_jn_cwfy)).toFixed(4);
		    	$('#sy_jn_yylr').numberbox('setValue',sy_jn_yylr);
		    	//投资收益
		    	var sy_jn_tzsy = $('#sy_jn_tzsy').numberbox('getValue');
		    	//补贴收入
		    	var sy_jn_btsr = $('#sy_jn_btsr').numberbox('getValue');
		    	//营业外收入
		    	var sy_jn_yywsr = $('#sy_jn_yywsr').numberbox('getValue');
		    	//营业外支出
		    	var sy_jn_yywzc = $('#sy_jn_yywzc').numberbox('getValue');
		    	//利润总额=营业利润+投资收益+补贴收入+营业外收入-营业外支出
		    	var sy_jn_lrze = (Number(sy_jn_yylr)+Number(sy_jn_tzsy)+Number(sy_jn_btsr)+Number(sy_jn_yywsr)-Number(sy_jn_yywzc)).toFixed(4);
		    	$('#sy_jn_lrze').numberbox('setValue',sy_jn_lrze);
		    	//所得税
		    	var sy_jn_sds = $('#sy_jn_sds').numberbox('getValue');
		    	//净利润=利润总额-所得税
		    	var sy_jn_jlr = (Number(sy_jn_lrze)-Number(sy_jn_sds)).toFixed(4);
		    	$('#sy_jn_jlr').numberbox('setValue',sy_jn_jlr);
		    	
		    },
		    //财务简表---标准类
			autoCalcFinaBZL:function(){
				$('input:not([readonly])').on('change',function(){
					//银行存款
					var cw_ldzc_bq_yhck = autoCalc.getNumByNullString($('#cw_ldzc_bq_yhck').val());
					//应收款净额
					var cw_ldzc_bq_yszkje = autoCalc.getNumByNullString($('#cw_ldzc_bq_yszkje').val());
					//存货
					var cw_ldzc_bq_ch = autoCalc.getNumByNullString($('#cw_ldzc_bq_ch').val());
					//流动资产合计
					var cw_ldzc_bq_ldzchj = (cw_ldzc_bq_yhck + cw_ldzc_bq_yszkje + cw_ldzc_bq_ch ).toFixed(2);
					$('#cw_ldzc_bq_ldzchj').val(cw_ldzc_bq_ldzchj);
					//动产
					var cw_gdzc_bq_dc = autoCalc.getNumByNullString($('#cw_gdzc_bq_dc').val());
					//不动产
					var cw_gdzc_bq_bdc = autoCalc.getNumByNullString($('#cw_gdzc_bq_bdc').val());
					//固定资产合计
					var cw_gdzc_bq_gdzchj = ( cw_gdzc_bq_dc + cw_gdzc_bq_bdc).toFixed(2);
					$('#cw_gdzc_bq_gdzchj').val(cw_gdzc_bq_gdzchj);
					//资产合计
					var cw_zchj_bq = ( Number(cw_ldzc_bq_ldzchj) + Number(cw_gdzc_bq_gdzchj)).toFixed(2);
					$('#cw_zchj_bq').val(cw_zchj_bq);
					//银行借款
					var cw_ldfz_bq_yhjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_yhjk').val());
					//其中本行借款
					var cw_ldfz_bq_bhjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_bhjk').val());
					//其他借款
					var cw_ldfz_bq_qtjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_qtjk').val());
					//负债合计
					var cw_ldfz_bq_ldfzhj = ( cw_ldfz_bq_yhjk + cw_ldfz_bq_qtjk).toFixed(2);
					$('#cw_ldfz_bq_ldfzhj').val(cw_ldfz_bq_ldfzhj);
					//所有者权益合计
					var cw_syzqyhj_bq = ( Number(cw_zchj_bq) - Number(cw_ldfz_bq_ldfzhj)).toFixed(2);
					$('#cw_syzqyhj_bq').val(cw_syzqyhj_bq);
				});
			},
			
			//财务简表---工薪类
			autoCalcFinaGXL:function(finalCreditAmt, cw_ldzc_bq_ch){
				$('input:not([readonly])').on('change',function(newone,oldone){
					//现金-本期余额
					var cw_ldzc_bq_xj =  autoCalc.getNumByNullString($('#cw_ldzc_bq_xj').val());
					//银行存款-本期余额
					var cw_ldzc_bq_yhck = autoCalc.getNumByNullString($('#cw_ldzc_bq_yhck').val());
					//应收款净额-本期余额
					var cw_ldzc_bq_yszkje = autoCalc.getNumByNullString($('#cw_ldzc_bq_yszkje').val());
					//短期投资-本期余额
					var cw_ldzc_bq_dqtz = autoCalc.getNumByNullString($('#cw_ldzc_bq_dqtz').val());
					//流动资产合计
					var cw_ldzc_bq_ldzchj = Number((cw_ldzc_bq_xj+cw_ldzc_bq_yhck+cw_ldzc_bq_yszkje+cw_ldzc_bq_dqtz).toFixed(2));
					$('#cw_ldzc_bq_ldzchj').val(cw_ldzc_bq_ldzchj);
					//动产
					var cw_gdzc_bq_dc = autoCalc.getNumByNullString($('#cw_gdzc_bq_dc').val());
					//不动产
					var cw_gdzc_bq_bdc = autoCalc.getNumByNullString($('#cw_gdzc_bq_bdc').val());
					//固定资产合计
					var cw_gdzc_bq_gdzchj = (cw_gdzc_bq_dc+cw_gdzc_bq_bdc).toFixed(2);
					$('#cw_gdzc_bq_gdzchj').val(cw_gdzc_bq_gdzchj);
					//长期投资
					var cw_cqtz_bq = autoCalc.getNumByNullString($('#cw_cqtz_bq').val());
					//资产合计
					var cw_zchj_bq = Number((Number(cw_cqtz_bq)+cw_ldzc_bq_ldzchj+Number(cw_gdzc_bq_gdzchj)).toFixed(2));
					$('#cw_zchj_bq').val(cw_zchj_bq);
					//流动负债
					//银行借款
					var cw_ldfz_bq_yhjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_yhjk').val());
					//本行借款
					var cw_ldfz_bq_bhjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_bhjk').val());
					//社会集资
					var cw_ldfz_bq_shjz = autoCalc.getNumByNullString($('#cw_ldfz_bq_shjz').val());
					//其他应付款
					var cw_ldfz_bq_qtjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_qtjk').val());
					//流动负债合计
					var cw_ldfz_bq_ldfzhj = Number((cw_ldfz_bq_yhjk+cw_ldfz_bq_shjz+cw_ldfz_bq_qtjk).toFixed(2));
					$('#cw_ldfz_bq_ldfzhj').val(cw_ldfz_bq_ldfzhj);
					//其他负债
					var cw_qtfz_bq = autoCalc.getNumByNullString($('#cw_qtfz_bq').val());
					//长期负债
					var cw_cqfz_bq = autoCalc.getNumByNullString($('#cw_cqfz_bq').val());
					//负债合计
					var cw_fzhj_bq = Number((cw_ldfz_bq_ldfzhj+Number(cw_qtfz_bq)+Number(cw_cqfz_bq)).toFixed(2));
					$('#cw_fzhj_bq').val(cw_fzhj_bq);
					//所有者权益合计=资产合计-负债合计
					var cw_syzqyhj_bq = (cw_zchj_bq - cw_fzhj_bq).toFixed(2);
					$('#cw_syzqyhj_bq').val(cw_syzqyhj_bq);
					//贷后资产负债率=(负债合计+授信额度-本行借款)/(资产合计+授信额度-本行借款)
					var cw_dhzcfzl_bq = 0;
					if((cw_zchj_bq+Number(finalCreditAmt)-cw_ldfz_bq_bhjk) != 0 ){
						cw_dhzcfzl_bq = ((cw_fzhj_bq+Number(finalCreditAmt)-cw_ldfz_bq_bhjk)/(cw_zchj_bq+Number(finalCreditAmt)-cw_ldfz_bq_bhjk)*100).toFixed(2);
					}
					$('#cw_dhzcfzl_bq').val(cw_dhzcfzl_bq);
					//资产负债率=负债合计/资产合计
					var cw_cwblfx_bq_zcfzl = 0;
					if(cw_zchj_bq != 0){
						cw_cwblfx_bq_zcfzl = ((cw_fzhj_bq/cw_zchj_bq)*100).toFixed(2);
					}
					$('#cw_cwblfx_bq_zcfzl').val(cw_cwblfx_bq_zcfzl);
					//流动比率=流动资产合计/流动负债合计
					var cw_cwblfx_bq_ldbl = 0;
					if(cw_ldfz_bq_ldfzhj != 0){
						cw_cwblfx_bq_ldbl = ((cw_ldzc_bq_ldzchj/cw_ldfz_bq_ldfzhj)*100).toFixed(2);
					}
					$('#cw_cwblfx_bq_ldbl').val(cw_cwblfx_bq_ldbl);
					//速动比率=（流动资产合计-存货）/流动负债合计
					var cw_cwblfx_bq_sdbl = 0;
					if(Number(cw_ldfz_bq_ldfzhj) != 0){
						cw_cwblfx_bq_sdbl = ((cw_ldzc_bq_ldzchj-cw_ldzc_bq_ch)/cw_ldfz_bq_ldfzhj*100).toFixed(2);
					}
					$('#cw_cwblfx_bq_sdbl').val(cw_cwblfx_bq_sdbl);
					
				});
			},
			
			
			//财务简表---经营类
			autoCalcFinaJYL:function(finalCreditAmt,zyywcb,zyywsr,zsr,proloss){
				$('input:not([readonly])').on('change',function(newone,oldone){
					//现金-本期余额
					var cw_ldzc_bq_xj = autoCalc.getNumByNullString($('#cw_ldzc_bq_xj').val());
					//银行存款-本期余额
					var cw_ldzc_bq_yhck = autoCalc.getNumByNullString($('#cw_ldzc_bq_yhck').val());
					//应收款净额-本期余额
					var cw_ldzc_bq_yszkje = autoCalc.getNumByNullString($('#cw_ldzc_bq_yszkje').val());
					//预付账款-本期余额
					var cw_ldzc_bq_yfzk = autoCalc.getNumByNullString($('#cw_ldzc_bq_yfzk').val());
					//存货-本期余额
					var cw_ldzc_bq_ch = autoCalc.getNumByNullString($('#cw_ldzc_bq_ch').val());
					//短期投资-本期余额
					var cw_ldzc_bq_dqtz = autoCalc.getNumByNullString($('#cw_ldzc_bq_dqtz').val());
					//流动资产合计
					var cw_ldzc_bq_ldzchj = Number((cw_ldzc_bq_xj+cw_ldzc_bq_yhck+cw_ldzc_bq_yszkje+cw_ldzc_bq_yfzk+cw_ldzc_bq_ch+cw_ldzc_bq_dqtz).toFixed(2));
					$('#cw_ldzc_bq_ldzchj').val(cw_ldzc_bq_ldzchj);
					//动产
					var cw_gdzc_bq_dc = autoCalc.getNumByNullString($('#cw_gdzc_bq_dc').val());
					//不动产
					var cw_gdzc_bq_bdc = autoCalc.getNumByNullString($('#cw_gdzc_bq_bdc').val());
					//固定资产合计
					var cw_gdzc_bq_gdzchj = Number((cw_gdzc_bq_dc+cw_gdzc_bq_bdc).toFixed(2));
					$('#cw_gdzc_bq_gdzchj').val(cw_gdzc_bq_gdzchj);
					//长期投资
					var cw_cqtz_bq = autoCalc.getNumByNullString($('#cw_cqtz_bq').val());
					//资产合计
					var cw_zchj_bq = Number((cw_cqtz_bq+cw_ldzc_bq_ldzchj+cw_gdzc_bq_gdzchj).toFixed(2));
					$('#cw_zchj_bq').val(cw_zchj_bq);
					
					
					
					//现金-平均余额
					var cw_ldzc_pj_xj = autoCalc.getNumByNullString($('#cw_ldzc_pj_xj').val());
					//银行存款-平均余额
					var cw_ldzc_pj_yhck = autoCalc.getNumByNullString($('#cw_ldzc_pj_yhck').val());
					//应收款净额-平均余额=应收款净额本期余额*0.9
					var cw_ldzc_pj_yszkje = Number((cw_ldzc_bq_yszkje*0.9).toFixed(2));
					$('#cw_ldzc_pj_yszkje').val(cw_ldzc_pj_yszkje);
					//预付账款-平均余额
					var cw_ldzc_pj_yfzk = autoCalc.getNumByNullString($('#cw_ldzc_pj_yfzk').val());
					//存货-平均余额=存货*0.9
					var cw_ldzc_pj_ch =  Number((cw_ldzc_bq_ch*0.9).toFixed(2));
					$('#cw_ldzc_pj_ch').val(cw_ldzc_pj_ch);
					//短期投资-平均余额
					var cw_ldzc_pj_dqtz = autoCalc.getNumByNullString($('#cw_ldzc_pj_dqtz').val());
					//流动资产合计
					var cw_ldzc_pj_ldzchj = Number((cw_ldzc_pj_xj+cw_ldzc_pj_yhck+cw_ldzc_pj_yszkje+cw_ldzc_pj_yfzk+cw_ldzc_pj_ch+cw_ldzc_pj_dqtz).toFixed(2));
					$('#cw_ldzc_pj_ldzchj').val(cw_ldzc_pj_ldzchj);
					
					
					
					//流动负债
					//银行借款
					var cw_ldfz_bq_yhjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_yhjk').val());
					//本行借款
					var cw_ldfz_bq_bhjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_bhjk').val());
					//社会集资
					var cw_ldfz_bq_shjz = autoCalc.getNumByNullString($('#cw_ldfz_bq_shjz').val());
					//应付账款
					var cw_ldfz_bq_yfzk = autoCalc.getNumByNullString($('#cw_ldfz_bq_yfzk').val());
					//预收账款
					var cw_ldfz_bq_yszk = autoCalc.getNumByNullString($('#cw_ldfz_bq_yszk').val());
					//其他应付款
					var cw_ldfz_bq_qtyfk = autoCalc.getNumByNullString($('#cw_ldfz_bq_qtyfk').val());
					//流动负债合计
					var cw_ldfz_bq_ldfzhj = Number((cw_ldfz_bq_yhjk+cw_ldfz_bq_shjz+cw_ldfz_bq_yfzk+cw_ldfz_bq_yszk+cw_ldfz_bq_qtyfk).toFixed(2));
					$('#cw_ldfz_bq_ldfzhj').val(cw_ldfz_bq_ldfzhj);
					//其他负债
					var cw_qtfz_bq = autoCalc.getNumByNullString($('#cw_qtfz_bq').val());
					//长期负债
					var cw_cqfz_bq = autoCalc.getNumByNullString($('#cw_cqfz_bq').val());
					//负债合计
					var cw_fzhj_bq = Number((cw_ldfz_bq_ldfzhj+ cw_qtfz_bq + cw_cqfz_bq).toFixed(2));
					$('#cw_fzhj_bq').val(cw_fzhj_bq);
					//所有者权益合计=资产合计-负债合计
					var cw_syzqyhj_bq = Number((cw_zchj_bq - cw_fzhj_bq).toFixed(2));
					$('#cw_syzqyhj_bq').val(cw_syzqyhj_bq);
					//贷后资产负债率=(负债合计+授信额度-本行借款)/(资产合计+授信额度-本行借款)
					var cw_dhzcfzl_bq = 0;
					if((cw_zchj_bq+Number(finalCreditAmt)-cw_ldfz_bq_bhjk) > 0){
						cw_dhzcfzl_bq = ((cw_fzhj_bq+Number(finalCreditAmt)-cw_ldfz_bq_bhjk)/(cw_zchj_bq+Number(finalCreditAmt)-cw_ldfz_bq_bhjk)*100).toFixed(2);
					}
					$('#cw_dhzcfzl_bq').val(cw_dhzcfzl_bq);
					//资产负债率=负债合计/资产合计
					var cw_cwblfx_bq_zcfzl = 0;
					if(cw_zchj_bq > 0){
						cw_cwblfx_bq_zcfzl = (cw_fzhj_bq/cw_zchj_bq*100).toFixed(2);
					}
					$('#cw_cwblfx_bq_zcfzl').val(cw_cwblfx_bq_zcfzl);
					//流动比率=流动资产合计/流动负债合计
					var cw_cwblfx_bq_ldbl = 0;
					if(cw_ldfz_bq_ldfzhj > 0){
						cw_cwblfx_bq_ldbl = (cw_ldzc_bq_ldzchj/cw_ldfz_bq_ldfzhj*100).toFixed(2);
					}
					$('#cw_cwblfx_bq_ldbl').val(cw_cwblfx_bq_ldbl);
					//速动比率=（流动资产合计-存货）/流动负债合计
					var cw_cwblfx_bq_sdbl = 0;
					if(cw_ldfz_bq_ldfzhj > 0){
						cw_cwblfx_bq_sdbl = ((cw_ldzc_bq_ldzchj-cw_ldzc_bq_ch)/cw_ldfz_bq_ldfzhj*100).toFixed(2);
					}
					$('#cw_cwblfx_bq_sdbl').val(cw_cwblfx_bq_sdbl);
					
					
					
					//银行借款-平均
					var cw_ldfz_pj_yhjk = autoCalc.getNumByNullString($('#cw_ldfz_pj_yhjk').val());
					//本行借款-平均
					var cw_ldfz_pj_bhjk = autoCalc.getNumByNullString($('#cw_ldfz_pj_bhjk').val());
					//社会集资-平均
					var cw_ldfz_pj_shjz = autoCalc.getNumByNullString($('#cw_ldfz_pj_shjz').val());
					//应付账款-平均
					var cw_ldfz_pj_yfzk = autoCalc.getNumByNullString($('#cw_ldfz_pj_yfzk').val());
					//预收账款-平均
					var cw_ldfz_pj_yszk = autoCalc.getNumByNullString($('#cw_ldfz_pj_yszk').val());
					//其他应付款
					var cw_ldfz_pj_qtyfk = autoCalc.getNumByNullString($('#cw_ldfz_pj_qtyfk').val());
					//流动负债合计
					var cw_ldfz_pj_ldfzhj = Number((cw_ldfz_pj_yhjk+cw_ldfz_pj_shjz+cw_ldfz_pj_yfzk+cw_ldfz_pj_yszk+cw_ldfz_pj_qtyfk).toFixed(2));
					$('#cw_ldfz_pj_ldfzhj').val(cw_ldfz_pj_ldfzhj);
					//其他负债
					var cw_qtfz_pj = autoCalc.getNumByNullString($('#cw_qtfz_pj').val());
					//长期负债
					var cw_cqfz_pj = autoCalc.getNumByNullString($('#cw_cqfz_pj').val());
					//负债合计
					var cw_fzhj_pj = Number((cw_ldfz_pj_ldfzhj+cw_qtfz_pj+cw_cqfz_pj).toFixed(2));
					$('#cw_fzhj_pj').val(cw_fzhj_pj);
					
					
					//存货周转次数=IF(存货平均余额=0,0,去年主营业务成本/存货平均余额)
					var cw_chzzcs = 0;
					if(cw_ldzc_pj_ch > 0){
						cw_chzzcs = Math.floor(Number(zyywcb)/cw_ldzc_pj_ch);
					}
					$('#cw_chzzcs').val(cw_chzzcs);
					//应收周转次数=去年主营业务收入/应收款净额-平均余额
					var cw_yiszzcs = 0;
					if(cw_ldzc_pj_yszkje != 0){
						cw_yiszzcs = (Number(zyywsr)/cw_ldzc_pj_yszkje).toFixed(2);
					}
					$('#cw_yiszzcs').val(cw_yiszzcs);
					//预收周转次数=IF(预付账款平均余额=0,0,去年主营业务收入/预付账款平均余额)
					var cw_yuszzcs = 0;
					if(cw_ldzc_pj_yfzk > 0){
//						cw_yuszzcs = Math.floor(Number(zyywsr)/cw_ldzc_pj_yfzk);
						cw_yuszzcs = (Number(zyywsr)/cw_ldzc_pj_yfzk).toFixed(2);
					}
					$('#cw_yuszzcs').val(cw_yuszzcs);
					//预付周转次数=去年主营业务收入/预收帐款平均余额
					var cw_yufzzcs = 0;
					if(cw_ldfz_pj_yszk > 0){
//						cw_yufzzcs = Math.floor(Number(zyywsr)/cw_ldfz_pj_yszk);
						cw_yufzzcs = (Number(zyywsr)/cw_ldfz_pj_yszk).toFixed(2);
					}
					$('#cw_yufzzcs').val(cw_yufzzcs);
					//应付周转次数=IF(应付帐款平均余额=0,0,去年主营业务收入/应付帐款平均余额)
					var cw_yifzzcs = 0;
					if(cw_ldfz_pj_yfzk > 0){
//						cw_yifzzcs = Math.floor(Number(zyywsr)/cw_ldfz_pj_yfzk);
						cw_yifzzcs = (Number(zyywsr)/cw_ldfz_pj_yfzk).toFixed(2);
					}
					$('#cw_yifzzcs').val(cw_yifzzcs);
					//资金缺口
					//资金缺口
					var cw_zjqk = 0;
					if(autoCalc.getNumByNullString(zyywsr) != 0){
						var G82 = cw_chzzcs==0?0:(360/cw_chzzcs);
						var G83 = cw_yiszzcs==0?0:(360/cw_yiszzcs);
						var I83 = cw_yifzzcs==0?0:(360/cw_yifzzcs);
						var I82 = cw_yufzzcs==0?0:(360/cw_yufzzcs);
						var G84 = cw_yuszzcs==0?0:(360/cw_yuszzcs);
						cw_zjqk = (autoCalc.getNumByNullString(zyywsr)*(1-autoCalc.getNumByNullString(proloss.sy_qn_zymlr)/autoCalc.getNumByNullString(zyywsr))*(1+(autoCalc.getNumByNullString(proloss.sy_jn_zsr)-autoCalc.getNumByNullString(zyywsr))/autoCalc.getNumByNullString(zyywsr))/360*(G82+G83-I83+I82-G84)-(autoCalc.getNumByNullString(cw_ldzc_pj_yhck)+autoCalc.getNumByNullString(cw_ldzc_pj_xj))-autoCalc.getNumByNullString(cw_ldfz_pj_yhjk)-autoCalc.getNumByNullString(cw_ldfz_pj_shjz)).toFixed(2);
					}
					$('#cw_zjqk').val(cw_zjqk);
				});
			},
			
			//财务简表---个私简化类
			autoCalcFinaGSJHL:function(finalCreditAmt){
				$('input:not([readonly])').on('change',function(newone,oldone){
					//现金-本期余额
					var cw_ldzc_bq_xj = autoCalc.getNumByNullString($('#cw_ldzc_bq_xj').val());
					//银行存款-本期余额
					var cw_ldzc_bq_yhck = autoCalc.getNumByNullString($('#cw_ldzc_bq_yhck').val());
					//应收款净额-本期余额
					var cw_ldzc_bq_yszkje = autoCalc.getNumByNullString($('#cw_ldzc_bq_yszkje').val());
					//预付账款-本期余额
					var cw_ldzc_bq_yfzk = autoCalc.getNumByNullString($('#cw_ldzc_bq_yfzk').val());
					//存货-本期余额
					var cw_ldzc_bq_ch = autoCalc.getNumByNullString($('#cw_ldzc_bq_ch').val());
					//短期投资-本期余额
					var cw_ldzc_bq_dqtz = autoCalc.getNumByNullString($('#cw_ldzc_bq_dqtz').val());
					//流动资产合计
					var cw_ldzc_bq_ldzchj = Number((cw_ldzc_bq_xj+cw_ldzc_bq_yhck+cw_ldzc_bq_yszkje+cw_ldzc_bq_yfzk+cw_ldzc_bq_ch+cw_ldzc_bq_dqtz).toFixed(2));
					$('#cw_ldzc_bq_ldzchj').val(cw_ldzc_bq_ldzchj);
					//动产
					var cw_gdzc_bq_dc = autoCalc.getNumByNullString($('#cw_gdzc_bq_dc').val());
					//不动产
					var cw_gdzc_bq_bdc = autoCalc.getNumByNullString($('#cw_gdzc_bq_bdc').val());
					//固定资产合计
					var cw_gdzc_bq_gdzchj = Number((cw_gdzc_bq_dc+cw_gdzc_bq_bdc).toFixed(2));
					$('#cw_gdzc_bq_gdzchj').val(cw_gdzc_bq_gdzchj);
					//长期投资
					var cw_cqtz_bq = autoCalc.getNumByNullString($('#cw_cqtz_bq').val());
					//资产合计
					var cw_zchj_bq = Number((cw_cqtz_bq+cw_ldzc_bq_ldzchj+cw_gdzc_bq_gdzchj).toFixed(2));
					$('#cw_zchj_bq').val(cw_zchj_bq);
					
					
					//流动负债
					//银行借款
					var cw_ldfz_bq_yhjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_yhjk').val());
					//本行借款
					var cw_ldfz_bq_bhjk = autoCalc.getNumByNullString($('#cw_ldfz_bq_bhjk').val());
					//社会集资
					var cw_ldfz_bq_shjz = autoCalc.getNumByNullString($('#cw_ldfz_bq_shjz').val());
					//应付账款
					var cw_ldfz_bq_yfzk = autoCalc.getNumByNullString($('#cw_ldfz_bq_yfzk').val());
					//预收账款
					var cw_ldfz_bq_yszk = autoCalc.getNumByNullString($('#cw_ldfz_bq_yszk').val());
					//其他应付款
					var cw_ldfz_bq_qtyfk = autoCalc.getNumByNullString($('#cw_ldfz_bq_qtyfk').val());
					//流动负债合计
					var cw_ldfz_bq_ldfzhj = Number((cw_ldfz_bq_yhjk+cw_ldfz_bq_shjz+cw_ldfz_bq_yfzk+cw_ldfz_bq_yszk+cw_ldfz_bq_qtyfk).toFixed(2));
					$('#cw_ldfz_bq_ldfzhj').val(cw_ldfz_bq_ldfzhj);
					//其他负债
					var cw_qtfz_bq = autoCalc.getNumByNullString($('#cw_qtfz_bq').val());
					//长期负债
					var cw_cqfz_bq = autoCalc.getNumByNullString($('#cw_cqfz_bq').val());
					//负债合计
					var cw_fzhj_bq = Number((cw_ldfz_bq_ldfzhj+ cw_qtfz_bq + cw_cqfz_bq).toFixed(2));
					$('#cw_fzhj_bq').val(cw_fzhj_bq);
					//所有者权益合计=资产合计-负债合计
					var cw_syzqyhj_bq = Number((cw_zchj_bq - cw_fzhj_bq).toFixed(2));
					$('#cw_syzqyhj_bq').val(cw_syzqyhj_bq);
					//贷后资产负债率=(负债合计+授信额度)/(资产合计+授信额度)
					var cw_dhzcfzl_bq = 0;
					if((cw_zchj_bq+Number(finalCreditAmt)) > 0){
						cw_dhzcfzl_bq = ((cw_fzhj_bq+Number(finalCreditAmt))/(cw_zchj_bq+Number(finalCreditAmt))*100).toFixed(2);
					}
					$('#cw_dhzcfzl_bq').val(cw_dhzcfzl_bq);
					//资产负债率=负债合计/资产合计
					var cw_cwblfx_bq_zcfzl = 0;
					if(cw_zchj_bq > 0){
						cw_cwblfx_bq_zcfzl = (cw_fzhj_bq/cw_zchj_bq*100).toFixed(2);
					}
					$('#cw_cwblfx_bq_zcfzl').val(cw_cwblfx_bq_zcfzl);
					//流动比率=流动资产合计/流动负债合计
					var cw_cwblfx_bq_ldbl = 0;
					if(cw_ldfz_bq_ldfzhj > 0){
						cw_cwblfx_bq_ldbl = (cw_ldzc_bq_ldzchj/cw_ldfz_bq_ldfzhj*100).toFixed(2);
					}
					$('#cw_cwblfx_bq_ldbl').val(cw_cwblfx_bq_ldbl);
					//速动比率=（流动资产合计-存货）/流动负债合计
					var cw_cwblfx_bq_sdbl = 0;
					if(cw_ldfz_bq_ldfzhj > 0){
						cw_cwblfx_bq_sdbl = ((cw_ldzc_bq_ldzchj-cw_ldzc_bq_ch)/cw_ldfz_bq_ldfzhj*100).toFixed(2);
					}
					$('#cw_cwblfx_bq_sdbl').val(cw_cwblfx_bq_sdbl);
				});
			},
			//公司类财务分析
			autoCalcComFinace:function(){
				$('input:not([readonly])').on('change',function(){
					//固定资产
					var fixedassets = toN($('#fixedassets').val());
					//流动资产
					var flowassets = toN($('#flowassets').val());
					//总资产
					var totalassets = toN((fixedassets+flowassets).toFixed(2));
					$('#totalassets').val(totalassets);
					//短期借款
					var shortborrow = toN($('#shortborrow').val());
					//长期负债
					var longliabilities = toN($('#longliabilities').val());
					//总负债
					var totalliabilities = toN((shortborrow+longliabilities).toFixed(2));
					$('#totalliabilities').val(totalliabilities);
					//所有者权益
					var ownerequity = toN((totalassets-totalliabilities).toFixed(2));
					$('#ownerequity').val(ownerequity);
					//去年销售额
					var annualsales = toN($('#annualsales').val());
					//去年纳税销售额
					var annualtaxsales = toN($('#annualtaxsales').val());
					//去年主营业务成本
					var annualsalescost = toN($('#annualsalescost').val());
					//去年销售利润
					var annualsalesprofit = toN($('#annualsalesprofit').val());
					//去年销贷比=去年销售额/(授信额度合计-其他额度合计)
					var annualsalesloanratio = 0;
					var totalcreditamt = toN($('#totalcreditamt').val());
					var totalamount1 = toN($('#totalamount1').val());
					var amt1 = toN((totalcreditamt-totalamount1).toFixed(2));
					if(amt1 != 0){
						annualsalesloanratio = toN((annualsales/amt1).toFixed(2));
					}
					$('#annualsalesloanratio').val(annualsalesloanratio);
					//预计今年销售收入年增长率
					var nextyearsalegrowth = toN($('#nextyearsalegrowth').val());
					//资金回笼
					var returnfunds = toN($('#returnfunds').val());
					//应收账款前年
					var beforyearreceacco = toN($('#beforyearreceacco').val());
					//应收账款去年
					var lastyearreceacco = toN($('#lastyearreceacco').val());
					//应收账款周转天数=360/(去年销售额/((应收账款前年+应收账款去年)/2))
					var receaccoturnover = 0;
					if(annualsales != 0 && (beforyearreceacco+lastyearreceacco) != 0){
						receaccoturnover = toN((360/(annualsales/((beforyearreceacco+lastyearreceacco)/2))).toFixed(2));
					}
					$('#receaccoturnover').val(receaccoturnover);
					//存货前年
					var beforyearstock = toN($('#beforyearstock').val());
					//存货去年
					var lastyearstock = toN($('#lastyearstock').val());
					//存货周转天数=360/(去年主营业务成本/((存货前年+存货去年)/2))
					var stockturnover = 0;
					if(annualsalescost != 0 && (beforyearstock+lastyearstock) != 0){
						stockturnover = toN((360/(annualsalescost/((beforyearstock+lastyearstock)/2))).toFixed(2));
					}
					$('#stockturnover').val(stockturnover);
					//预付账款前年
					var beforyearprepadacco = toN($('#beforyearprepadacco').val());
					//预付账款去年
					var lastyearprepadacco = toN($('#lastyearprepadacco').val());
					//预付账款周转天数=360/(去年主营业务成本/((预付账款前年+预付账款去年)/2))
					var prepadaccoturnover = 0;
					if(annualsalescost != 0 && (beforyearprepadacco+lastyearprepadacco) != 0){
						prepadaccoturnover = toN((360/(annualsalescost/((beforyearprepadacco+lastyearprepadacco)/2))).toFixed(2));
					}
					$('#prepadaccoturnover').val(prepadaccoturnover);
					//应付账款前年
					var beforyearpadacco = toN($('#beforyearpadacco').val());
					//应付账款去年
					var lastyearpadacco = toN($('#lastyearpadacco').val());
					//应付账款周转天数=360/(去年主营业务成本/((应付账款前年+应付账款去年)/2))
					var padaccoturnover = 0;
					if(annualsalescost != 0 && (beforyearpadacco+lastyearpadacco) != 0){
						padaccoturnover = toN((360/(annualsalescost/((beforyearpadacco+lastyearpadacco)/2))).toFixed(2));
					}
					$('#padaccoturnover').val(padaccoturnover);
					//预收账款前年
					var beforyearprecco = toN($('#beforyearprecco').val());
					//预收账款去年
					var lastyearprecco = toN($('#lastyearprecco').val());
					//预收账款周转天数=360/(去年销售额/((预收账款前年+预收账款去年)/2)),如果（预收账款前年+预收账款去年）=0，预收账款周转天数=0
					var prereceaccoturnover = 0;
					if(annualsales != 0 && (beforyearprecco+lastyearprecco) != 0){
						prereceaccoturnover = toN((360/(annualsales/((beforyearprecco+lastyearprecco)/2))).toFixed(2));
					}
					$('#prereceaccoturnover').val(prereceaccoturnover);
					//营运周转次数=360/（存货周转天数+应收账款周转天数-应付账款周转天数+预付账款周转天数-预收账款账款周转天数）
					var capturnover = 0;
					var amt2 = toN((stockturnover + receaccoturnover - padaccoturnover + prepadaccoturnover - prereceaccoturnover).toFixed(2));
					if(amt2 != 0){
						capturnover = toN((360/amt2).toFixed(2));
					}
					$('#capturnover').val(capturnover);
					//去年销售利润率=去年销售利润/去年销售额
					var annualsalesrate = 0;
					if(annualsales != 0){
						annualsalesrate = toN((annualsalesprofit/annualsales).toFixed(2));
					}
					$('#annualsalesrate').val(annualsalesrate);
					//营运资金量=去年销售额*（1-去年销售利润率）*（1+下年度增长率/100）/营运周转次数
					var workcapital = 0;
					if(capturnover != 0){
						workcapital = annualsales*(1-annualsalesrate)*(1+nextyearsalegrowth/100)/capturnover;
					}
					$('#workcapital').val(workcapital);
					//票据敞口余额
					var openbill = toN($('#openbill').val(openbill));
					//流动资金贷款需求额度=营运资金量-（所有者权益-固定资产）-短期借款-票据敞口余额
					var loandemand = toN((workcapital - (ownerequity-fixedassets)-shortborrow-openbill).toFixed(2));
					$('#loandemand').val(loandemand);
				});
			},
			//公司类额度测算
			autoCalcComFinaceResult:function(comFiance){
				$('input:not([readonly])').on('change',function(){
					//营运周转次数=360/（存货周转天数+应收账款周转天数-应付账款周转天数+预付账款周转天数-预收账款账款周转天数）
					var stockturnover = toN(comFiance.stockturnover);//存货周转天数
					var receaccoturnover = toN(comFiance.receaccoturnover);//应收账款周转天数
					var padaccoturnover = toN(comFiance.padaccoturnover);//应付账款周转天数
					var prepadaccoturnover = toN(comFiance.prepadaccoturnover);//预付账款周转天数
					var prereceaccoturnover = toN(comFiance.prereceaccoturnover);//预收账款账款周转天数
					var capturnover = 0;
					var amt2 = toN((stockturnover + receaccoturnover - padaccoturnover + prepadaccoturnover - prereceaccoturnover).toFixed(2));
					if(amt2 != 0){
						capturnover = toN((360/amt2).toFixed(2));
					}
					$('#capturnover').val(capturnover);
					//去年销售利润率
					var annualsalesrate = toN(comFiance.annualsalesrate);
					//去年销售额
					var annualsales = toN(comFiance.annualsales);
					//下年度增长率
					var nextyearsalegrowth = toN(comFiance.nextyearsalegrowth);
					//营运资金量=去年销售额*（1-去年销售利润率）*（1+下年度增长率/100）/营运周转次数
					var workcapital = 0;
					if(capturnover != 0){
						workcapital =  toN((annualsales*(1-annualsalesrate)*(1+nextyearsalegrowth/100)/capturnover).toFixed(2));
					}
					$('#workcapital').val(workcapital);
					//票据敞口余额
					var openbill = toN($('#openbill').val());
					//所有者权益
					var ownerequity = toN(comFiance.ownerequity);
					//固定资产
					var fixedassets = toN(comFiance.fixedassets);
					//短期借款
					var shortborrow = toN(comFiance.shortborrow);
					//流动资金贷款需求额度=营运资金量-（所有者权益-固定资产）-短期借款-票据敞口余额
					var loandemand = toN((workcapital - (ownerequity-fixedassets)-shortborrow-openbill).toFixed(2));
					$('#loandemand').val(loandemand);
					
					//行业资产负债率上限
					var maxdebtratio = toN(comFiance.maxdebtratio);
					//行业资产负债率下限
					var mindebtratio = toN(comFiance.mindebtratio);
					//资产负债率=总负债/总资产
					var assetliabratio = 0;
					//总资产
					var totalassets = toN(comFiance.totalassets);
					//总负债
					var totalliabilities = toN(comFiance.totalliabilities);
					if(totalassets != 0){
						assetliabratio = toN((totalliabilities / totalassets).toFixed(2));
					}
					$('#assetliabratio').val(assetliabratio);
					//负债率系数=min(((负债率上限-资产负债率)/(负债率上限-负债率下限)),1);
					var assetliabratiocoe = 0;
					if((maxdebtratio - mindebtratio) != 0){
						assetliabratiocoe = Math.min(toN(((maxdebtratio - assetliabratio)/(maxdebtratio - mindebtratio)).toFixed(2)),1);
					}
					$('#assetliabratiocoe').val(assetliabratiocoe);
					//净利润
					var netprofit = toN($('#netprofit').val());
					//所得税
					var incometax = toN($('#incometax').val());
					//固定资产折旧
					var fixedassetdisc = toN($('#fixedassetdisc').val());
					//摊费用
					var stallcost = toN($('#stallcost').val());
					//利息现金
					var intercash = toN($('#intercash').val());
					//摊销前利润=(净利润+所得税+固定资产折旧+摊费用+利息现金)
					var preamortprofit = toN((netprofit+incometax+fixedassetdisc+stallcost+intercash).toFixed(2));
					$('#preamortprofit').val(preamortprofit);
					//现金盈余倍数
					var cashsurpmultip = toN(comFiance.cashsurpmultip);
					//新增负债初始值=摊销前利润*现金盈余倍数-总负债
					var newliabinitval = toN((preamortprofit * cashsurpmultip - totalliabilities).toFixed(2));
					$('#newliabinitval').val(newliabinitval);
					//评级系数
					var creditcode = toN(comFiance.creditcode);
					//有效净资产
					var effectassets = toN($('#effectassets').val());
					//最大新增负债试算1=MIN(MAX(有效净资产,0)*评级系数*新增负债初始值,新增负债初始值)
					var maxnewliabcalc1 = toN((Math.min((newliabinitval*creditcode*Math.max(effectassets,0)),newliabinitval)).toFixed(2));
					$('#maxnewliabcalc1').val(maxnewliabcalc1);
					//最大新增负债试算2
					var maxnewliabcalc2 = 0;
					$('#maxnewliabcalc2').val(maxnewliabcalc2);
					//最大新增负债试算3=MIN(MAX(有效净资产,0)*资产负债率系数,新增负债初始值)
					var maxnewliabcalc3 = toN((Math.min(assetliabratiocoe*Math.max(effectassets,0),newliabinitval).toFixed(2)));
					$('#maxnewliabcalc3').val(maxnewliabcalc3);
					//最大新增负债=IF(负债率系数>=0&新增负债初始值>=0,最大新增负债试算1,IF(负债率系数>=0&新增负债初始值<0,最大新增负债试算2,最大新增负债试算3))
					var maxnewliab = 0;
					if(assetliabratiocoe>=0 && newliabinitval>=0){
						maxnewliab = maxnewliabcalc1;
					}else if(assetliabratiocoe>=0 && newliabinitval<0){
						maxnewliab = maxnewliabcalc2;
					}else{
						maxnewliab = maxnewliabcalc3;
					}
					$('#maxnewliab').val(maxnewliab);
					//行业付息债务比
					var induinterateratio = toN(comFiance.induinterateratio);
					//行业系数
					var industrycoe = toN(comFiance.industrycoe);
					//区域系数
					var areacode = toN(comFiance.areacode);
					//担保方式系数
					var guaregcoe = toN(comFiance.guaregcoe);
					//用信额度合计
					var totalamount2 = toN(comFiance.totalamount2);
					//授信限额=MIN(MAX((最大新增负债+总负债)*行业付息债务比*行业评级系数*区域评级系数*担保方式系数,0),总资产-用信额度合计)
					var creditlimit = toN((Math.min(Math.max((maxnewliab+totalliabilities)*induinterateratio*industrycoe*areacode*guaregcoe,0),(totalassets-totalamount2))).toFixed(2));
					$('#creditlimit').val(creditlimit);
				});
			},
			//公司类财务分析
			autoFinanceComp:function(){
					//货币资金
					var hbzj_nc = toN($('#hbzj_nc').numberbox('getValue'));
					//短期投资
					var dqtz_nc = toN($('#dqtz_nc').numberbox('getValue'));
					//应收票据
					var yspj_nc = toN($('#yspj_nc').numberbox('getValue'));
					//应收账款
					var yszk_nc = toN($('#yszk_nc').numberbox('getValue'));
					//坏账准备
					var hzzb_nc = toN($('#hzzb_nc').numberbox('getValue'));
					//应收账款净额
					var yszkje_nc = toN($('#yszkje_nc').numberbox('getValue'));
					//其他应收款
					var qtyszk_nc = toN($('#qtyszk_nc').numberbox('getValue'));
					//预付账款
					var yufuzk_nc = toN($('#yufuzk_nc').numberbox('getValue'));
					//存货
					var ch_nc = toN($('#ch_nc').numberbox('getValue'));
					//待摊费用
					var dtfy_nc = toN($('#dtfy_nc').numberbox('getValue'));
					//待处理流动资产净损失
					var dclldzcjss_nc = toN($('#dclldzcjss_nc').numberbox('getValue'));
					//一年内到期的长期债券投资
					var dqcqzqtz_nc = toN($('#dqcqzqtz_nc').numberbox('getValue'));
					//其他流动资产
					var qtldzc_nc = toN($('#qtldzc_nc').numberbox('getValue'));
					//流动资产-合计=货币资金+短期投资+应收票据+应收账款-坏账准备+其他应收款+预付账款+存货+待摊费用+待处理流动资产净损失+其他流动资产
					var ldzchj_nc = toN((hbzj_nc+dqtz_nc+yspj_nc+yszk_nc-hzzb_nc+qtyszk_nc+yufuzk_nc+ch_nc+dtfy_nc+dclldzcjss_nc+dqcqzqtz_nc+qtldzc_nc));
					$('#ldzchj_nc').numberbox('setValue',ldzchj_nc);
					
					
					//货币资金
					var hbzj_qm = toN($('#hbzj_qm').numberbox('getValue'));
					//短期投资
					var dqtz_qm = toN($('#dqtz_qm').numberbox('getValue'));
					//应收票据
					var yspj_qm = toN($('#yspj_qm').numberbox('getValue'));
					//应收账款
					var yszk_qm = toN($('#yszk_qm').numberbox('getValue'));
					//坏账准备
					var hzzb_qm = toN($('#hzzb_qm').numberbox('getValue'));
					//应收账款净额
					var yszkje_qm = toN($('#yszkje_qm').numberbox('getValue'));
					//其他应收款
					var qtyszk_qm = toN($('#qtyszk_qm').numberbox('getValue'));
					//预付账款
					var yufuzk_qm = toN($('#yufuzk_qm').numberbox('getValue'));
					//存货
					var ch_qm = toN($('#ch_qm').numberbox('getValue'));
					//待摊费用
					var dtfy_qm = toN($('#dtfy_qm').numberbox('getValue'));
					//待处理流动资产净损失
					var dclldzcjss_qm = toN($('#dclldzcjss_qm').numberbox('getValue'));
					//一年内到期的长期债券投资
					var dqcqzqtz_qm = toN($('#dqcqzqtz_qm').numberbox('getValue'));
					//其他流动资产
					var qtldzc_qm = toN($('#qtldzc_qm').numberbox('getValue'));
					//流动资产-合计=货币资金+短期投资+应收票据+应收账款-坏账准备+其他应收款+预付账款+存货+待摊费用+待处理流动资产净损失+其他流动资产
					var ldzchj_qm = toN((hbzj_qm+dqtz_qm+yspj_qm+yszk_qm-hzzb_qm+qtyszk_qm+yufuzk_qm+ch_qm+dtfy_qm+dclldzcjss_qm+dqcqzqtz_qm+qtldzc_qm).toFixed(4));
					$('#ldzchj_qm').numberbox('setValue',ldzchj_qm);
					
					
					//短期借款
					var shortborrow_nc = toN($('#shortborrow_nc').numberbox('getValue'));
					//应付票据
					var yypj_nc = toN($('#yypj_nc').numberbox('getValue'));
					//应付账款
					var yfzk_nc = toN($('#yfzk_nc').numberbox('getValue'));
					//预收账款
					var yushouzk_nc = toN($('#yushouzk_nc').numberbox('getValue'));
					//应付工资
					var yygz_nc = toN($('#yygz_nc').numberbox('getValue'));
					//应付福利费
					var yyflf_nc = toN($('#yyflf_nc').numberbox('getValue'));
					//未交税金
					var wjsj_nc = toN($('#wjsj_nc').numberbox('getValue'));
					//未付利润
					var wflr_nc = toN($('#wflr_nc').numberbox('getValue'));
					//其他未交款
					var qtwjk_nc = toN($('#qtwjk_nc').numberbox('getValue'));
					//其他应付款
					var qtyyk_nc = toN($('#qtyyk_nc').numberbox('getValue'));
					//预提费用
					var ytfy_nc = toN($('#ytfy_nc').numberbox('getValue'));
					//一年内到期的长期负债
					var dqcqfz_nc = toN($('#dqcqfz_nc').numberbox('getValue'));
					//其他流动负债
					var qtldfz_nc = toN($('#qtldfz_nc').numberbox('getValue'));
					//流动负债--合计
					var ldfzhj_nc = toN((shortborrow_nc+yypj_nc+yfzk_nc+yushouzk_nc+yygz_nc+yyflf_nc+wjsj_nc+wflr_nc+qtwjk_nc+qtyyk_nc+ytfy_nc+dqcqfz_nc+qtldfz_nc).toFixed(4));
					$('#ldfzhj_nc').numberbox('setValue',ldfzhj_nc);
					
					
					
					//短期借款
					var shortborrow_qm = toN($('#shortborrow_qm').numberbox('getValue'));
					//应付票据
					var yypj_qm = toN($('#yypj_qm').numberbox('getValue'));
					//应付账款
					var yfzk_qm = toN($('#yfzk_qm').numberbox('getValue'));
					//预收账款
					var yushouzk_qm = toN($('#yushouzk_qm').numberbox('getValue'));
					//应付工资
					var yygz_qm = toN($('#yygz_qm').numberbox('getValue'));
					//应付福利费
					var yyflf_qm = toN($('#yyflf_qm').numberbox('getValue'));
					//未交税金
					var wjsj_qm = toN($('#wjsj_qm').numberbox('getValue'));
					//未付利润
					var wflr_qm = toN($('#wflr_qm').numberbox('getValue'));
					//其他未交款
					var qtwjk_qm = toN($('#qtwjk_qm').numberbox('getValue'));
					//其他应付款
					var qtyyk_qm = toN($('#qtyyk_qm').numberbox('getValue'));
					//预提费用
					var ytfy_qm = toN($('#ytfy_qm').numberbox('getValue'));
					//一年内到期的长期负债
					var dqcqfz_qm = toN($('#dqcqfz_qm').numberbox('getValue'));
					//其他流动负债
					var qtldfz_qm = toN($('#qtldfz_qm').numberbox('getValue'));
					//流动负债--合计
					var ldfzhj_qm = toN((shortborrow_qm+yypj_qm+yfzk_qm+yushouzk_qm+yygz_qm+yyflf_qm+wjsj_qm+wflr_qm+qtwjk_qm+qtyyk_qm+ytfy_qm+dqcqfz_qm+qtldfz_qm).toFixed(4));
					$('#ldfzhj_qm').numberbox('setValue',ldfzhj_qm);
					
					
					//固定资产原价
					var fixedassets_nc = toN($('#fixedassets_nc').numberbox('getValue'));
					//累计折旧
					var fixedassetdisc_nc = toN($('#fixedassetdisc_nc').numberbox('getValue'));
					//固定资产净值
					var gdzcjz_nc = toN((fixedassets_nc-fixedassetdisc_nc).toFixed(4));
					$('#gdzcjz_nc').numberbox('setValue',gdzcjz_nc);
					//固定资产清理
					var gdzcql_nc = toN($('#gdzcql_nc').numberbox('getValue'));
					//在建工程
					var zjgc_nc = toN($('#zjgc_nc').numberbox('getValue'));
					//待处理固定资产损失
					var dclgdzcss_nc = toN($('#dclgdzcss_nc').numberbox('getValue'));
					//固定资产--合计=固定资产净值+固定资产清理+在建工程+待处理固定资产损失
					var gdzchj_nc = toN((gdzcjz_nc+gdzcql_nc+zjgc_nc+dclgdzcss_nc).toFixed(4));
					$('#gdzchj_nc').numberbox('setValue',gdzchj_nc);
					
					//固定资产原价
					var fixedassets_qm = toN($('#fixedassets_qm').numberbox('getValue'));
					//累计折旧
					var fixedassetdisc_qm = toN($('#fixedassetdisc_qm').numberbox('getValue'));
					//固定资产净值
					var gdzcjz_qm = toN((fixedassets_qm-fixedassetdisc_qm).toFixed(4));
					$('#gdzcjz_qm').numberbox('setValue',gdzcjz_qm);
					//固定资产清理
					var gdzcql_qm = toN($('#gdzcql_qm').numberbox('getValue'));
					//在建工程
					var zjgc_qm = toN($('#zjgc_qm').numberbox('getValue'));
					//待处理固定资产损失
					var dclgdzcss_qm = toN($('#dclgdzcss_qm').numberbox('getValue'));
					//固定资产--合计=固定资产净值+固定资产清理+在建工程+待处理固定资产损失
					var gdzchj_qm = toN((gdzcjz_qm+gdzcql_qm+zjgc_qm+dclgdzcss_qm).toFixed(4));
					$('#gdzchj_qm').numberbox('setValue',gdzchj_qm);
					
					
					//长期借款
					var cqjk_nc = toN($('#cqjk_nc').numberbox('getValue'));
					//应付债券
					var yfzq_nc = toN($('#yfzq_nc').numberbox('getValue'));
					//长期应付款
					var cqyfk_nc = toN($('#cqyfk_nc').numberbox('getValue'));
					//其他长期负债
					var qtcqfz_nc = toN($('#qtcqfz_nc').numberbox('getValue'));
					//长期负债--合计
					var cqfzhj_nc = toN((cqjk_nc+yfzq_nc+cqyfk_nc+qtcqfz_nc).toFixed(4));
					$('#cqfzhj_nc').numberbox('setValue',cqfzhj_nc);
					
					
					//负债合计=流动负债合计+长期负债合计
					var fzhj_nc = toN((ldfzhj_nc+cqfzhj_nc).toFixed(4));
					$('#fzhj_nc').numberbox('setValue',fzhj_nc);
					
					//长期借款
					var cqjk_qm = toN($('#cqjk_qm').numberbox('getValue'));
					//应付债券
					var yfzq_qm = toN($('#yfzq_qm').numberbox('getValue'));
					//长期应付款
					var cqyfk_qm = toN($('#cqyfk_qm').numberbox('getValue'));
					//其他长期负债
					var qtcqfz_qm = toN($('#qtcqfz_qm').numberbox('getValue'));
					//长期负债--合计
					var cqfzhj_qm = toN((cqjk_qm+yfzq_qm+cqyfk_qm+qtcqfz_qm).toFixed(4));
					$('#cqfzhj_qm').numberbox('setValue',cqfzhj_qm);
					
					//负债合计=流动负债合计+长期负债合计
					var fzhj_qm = toN((ldfzhj_qm+cqfzhj_qm).toFixed(4));
					$('#fzhj_qm').numberbox('setValue',fzhj_qm);
					
					
					//无形资产
					var wxzc_nc = toN($('#wxzc_nc').numberbox('getValue'));
					//递延资产
					var dyzc_nc = toN($('#dyzc_nc').numberbox('getValue'));
					//无形资产--合计
					var wxzchj_nc = toN((wxzc_nc+dyzc_nc).toFixed(4));
					$('#wxzchj_nc').numberbox('setValue',wxzchj_nc);
					
					
					//无形资产
					var wxzc_qm = toN($('#wxzc_qm').numberbox('getValue'));
					//递延资产
					var dyzc_qm = toN($('#dyzc_qm').numberbox('getValue'));
					//无形资产--合计
					var wxzchj_qm = toN((wxzc_qm+dyzc_qm).toFixed(4));
					$('#wxzchj_qm').numberbox('setValue',wxzchj_qm);
					
					
					//实收资本
					var sszb_nc = toN($('#sszb_nc').numberbox('getValue'));
					//资本公积
					var zbgj_nc = toN($('#zbgj_nc').numberbox('getValue'));
					//盈余公积
					var yygj_nc = toN($('#yygj_nc').numberbox('getValue'));
					//未分配利润
					var wfplr_nc = toN($('#wfplr_nc').numberbox('getValue'));
					//所有者权益--合计
					var ownerequity_nc = toN((sszb_nc+zbgj_nc+yygj_nc+wfplr_nc).toFixed(4));
					$('#ownerequity_nc').numberbox('setValue',ownerequity_nc);
					
					//实收资本
					var sszb_qm = toN($('#sszb_qm').numberbox('getValue'));
					//资本公积
					var zbgj_qm = toN($('#zbgj_qm').numberbox('getValue'));
					//盈余公积
					var yygj_qm = toN($('#yygj_qm').numberbox('getValue'));
					//未分配利润
					var wfplr_qm = toN($('#wfplr_qm').numberbox('getValue'));
					//所有者权益--合计
					var ownerequity_qm = toN((sszb_qm+zbgj_qm+yygj_qm+wfplr_qm).toFixed(4));
					$('#ownerequity_qm').numberbox('setValue',ownerequity_qm);
					
					
					//资产合计=流动资产合计+长期投资+固定资产合计+无形资产合计+其他长期资产
					var cqtz_nc = toN($('#cqtz_nc').numberbox('getValue'));
					var qtcqzc_nc = toN($('#qtcqzc_nc').numberbox('getValue'));
					var totalassets_nc = toN((ldzchj_nc+cqtz_nc+gdzchj_nc+wxzchj_nc+qtcqzc_nc).toFixed(4));
					$('#totalassets_nc').numberbox('setValue',totalassets_nc);
					
					
					//资产合计=流动资产合计+长期投资+固定资产合计+无形资产合计+其他长期资产
					var cqtz_qm = toN($('#cqtz_qm').numberbox('getValue'));
					var qtcqzc_qm = toN($('#qtcqzc_qm').numberbox('getValue'));
					var totalassets_qm = toN((ldzchj_qm+cqtz_qm+gdzchj_qm+wxzchj_qm+qtcqzc_qm).toFixed(4));
					$('#totalassets_qm').numberbox('setValue',totalassets_qm);
					
					
					//负债及所有者权益合计=负债合计+所有者权益合计
					var fzysyzqyhj_nc = toN((fzhj_nc+ownerequity_nc).toFixed(4));
					$('#fzysyzqyhj_nc').numberbox('setValue',fzysyzqyhj_nc);
					
					//负债及所有者权益合计=负债合计+所有者权益合计
					var fzysyzqyhj_qm = toN((fzhj_qm+ownerequity_qm).toFixed(4));
					$('#fzysyzqyhj_qm').numberbox('setValue',fzysyzqyhj_qm);
					
			},
			//流动资金需求测算
			autoCalcFlowFound:function(){
					//去年销售额
					var annualsales = toN($('#annualsales').numberbox('getValue'));
					//去年主营业务成本
					var annualsalescost = toN($('#annualsalescost').numberbox('getValue'));
					//去年销售利润
					var annualsalesprofit = toN($('#annualsalesprofit').numberbox('getValue'));
					//预计今年销售收入年增长率
					var nextyearsalegrowth = toN($('#nextyearsalegrowth').numberbox('getValue'));
					//去年销售利润率=去年销售利润/去年销售额
					var annualsalesrate = 0;
					if(annualsales != 0){
						annualsalesrate = toN((annualsalesprofit/annualsales).toFixed(4));
					}
					$('#annualsalesrate').numberbox('setValue',annualsalesrate);
					//应收账款前年
					var beforyearreceacco = toN($('#beforyearreceacco').numberbox('getValue'));
					//应收账款去年
					var lastyearreceacco = toN($('#lastyearreceacco').numberbox('getValue'));
					//应收账款周转天数=360/(去年销售额/((应收账款前年+应收账款去年)/2))
					var receaccoturnover = 0;
					if(annualsales != 0 && (beforyearreceacco+lastyearreceacco) != 0){
						receaccoturnover = toN((360/(annualsales/((beforyearreceacco+lastyearreceacco)/2))).toFixed(4));
					}
					$('#receaccoturnover').numberbox('setValue',receaccoturnover);
					//存货前年
					var beforyearstock = toN($('#beforyearstock').numberbox('getValue'));
					//存货去年
					var lastyearstock = toN($('#lastyearstock').numberbox('getValue'));
					//存货周转天数=360/(去年主营业务成本/((存货前年+存货去年)/2))
					var stockturnover = 0;
					if(annualsalescost != 0 && (beforyearstock+lastyearstock) != 0){
						stockturnover = toN((360/(annualsalescost/((beforyearstock+lastyearstock)/2))).toFixed(4));
					}
					$('#stockturnover').numberbox('setValue',stockturnover);
					//预付账款前年
					var beforyearprepadacco = toN($('#beforyearprepadacco').numberbox('getValue'));
					//预付账款去年
					var lastyearprepadacco = toN($('#lastyearprepadacco').numberbox('getValue'));
					//预付账款周转天数=360/(去年主营业务成本/((预付账款前年+预付账款去年)/2))
					var prepadaccoturnover = 0;
					if(annualsalescost != 0 && (beforyearprepadacco+lastyearprepadacco) != 0){
						prepadaccoturnover = toN((360/(annualsalescost/((beforyearprepadacco+lastyearprepadacco)/2))).toFixed(4));
					}
					$('#prepadaccoturnover').numberbox('setValue',prepadaccoturnover);
					//应付账款前年
					var beforyearpadacco = toN($('#beforyearpadacco').numberbox('getValue'));
					//应付账款去年
					var lastyearpadacco = toN($('#lastyearpadacco').numberbox('getValue'));
					//应付账款周转天数=360/(去年主营业务成本/((应付账款前年+应付账款去年)/2))
					var padaccoturnover = 0;
					if(annualsalescost != 0 && (beforyearpadacco+lastyearpadacco) != 0){
						padaccoturnover = toN((360/(annualsalescost/((beforyearpadacco+lastyearpadacco)/2))).toFixed(4));
					}
					$('#padaccoturnover').numberbox('setValue',padaccoturnover);
					//预收账款前年
					var beforyearprecco = toN($('#beforyearprecco').numberbox('getValue'));
					//预收账款去年
					var lastyearprecco = toN($('#lastyearprecco').numberbox('getValue'));
					//预收账款周转天数=360/(去年销售额/((预收账款前年+预收账款去年)/2)),如果（预收账款前年+预收账款去年）=0，预收账款周转天数=0
					var prereceaccoturnover = 0;
					if(annualsales != 0 && (beforyearprecco+lastyearprecco) != 0){
						prereceaccoturnover = toN((360/(annualsales/((beforyearprecco+lastyearprecco)/2))).toFixed(4));
					}
					$('#prereceaccoturnover').numberbox('setValue',prereceaccoturnover);
					
					//所有者权益
					var ownerequity = toN($('#ownerequity').numberbox('getValue'));
					//固定资产
					var fixedassets  = toN($('#fixedassets').numberbox('getValue'));
					//短期借款
					var shortborrow = toN($('#shortborrow').numberbox('getValue'));
					//营运周转次数=360/（存货周转天数+应收账款周转天数-应付账款周转天数+预付账款周转天数-预收账款账款周转天数）
					var capturnover = 0;
					var amt2 = toN((stockturnover + receaccoturnover - padaccoturnover + prepadaccoturnover - prereceaccoturnover).toFixed(4));
					if(amt2 != 0){
						capturnover = toN((360/amt2).toFixed(4));
					}
					$('#capturnover').numberbox('setValue',capturnover);
					//营运资金量=去年销售额*（1-去年销售利润率）*（1+下年度增长率/100）/营运周转次数
					var workcapital = 0;
					if(capturnover != 0){
						workcapital = annualsales*(1-annualsalesrate)*(1+nextyearsalegrowth/100)/capturnover;
					}
					$('#workcapital').numberbox('setValue',workcapital);
					//票据敞口余额
					var openbill = toN($('#openbill').numberbox('getValue'));
					//流动资金贷款需求额度=营运资金量-（所有者权益-固定资产）-短期借款-票据敞口余额
					var loandemand = toN((workcapital - (ownerequity-fixedassets)-shortborrow-openbill).toFixed(4));
					$('#loandemand').numberbox('setValue',loandemand);
			},
			getNumByNullString:function(str){
				if(isNaN(str)){
					return Number(0); 
				}
				return Number(str);
		   }
	};
	function toN(str){
		if(isNaN(str)){
			return Number(0); 
		}
		return Number(str);
	}
})();