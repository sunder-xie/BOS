<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理分区</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/ocupload/jquery.ocupload-1.1.2.js"
	type="text/javascript"></script>
	
<script type="text/javascript">
	function doAdd(){
		$("#id").val('');
		$("#addresskey").val('');
		$("#startnum").val('');
		$("#endnum").val('');
		$("#single").val('');
		$("#position").val('');
		
		$('#addSubareaWindow').window("open");
	}
	
	function doEdit(){
		alert("修改...");
	}
	
	function doDelete(){
		var arr = $("#grid").datagrid('getSelections');
		if(arr.length==0){
			$.messager.alert('警告','请选择需要删除项','warning');	
		}else{
		$("#formgrid").submit();
	}} 
	function doSearch(){
		
		$('#searchWindow').window("open");
	}
	
	function doExport(){
		window.location.href="${pageContext.request.contextPath }/subarea_exportXLS.action";
	}
	
	function doImport(){
		/* alert("导入"); */
		$('#button-import').upload({
			name : 'upload' , // <input type="file" name="upload" 
			action : '${pageContext.request.contextPath}/subarea_importXls.action', // 表单提交路径
			enctype: 'multipart/form-data',
			onComplete : function(response){
				var info = eval('('+response+')');
				$.messager.alert('信息',info.msg,'info');
				$('#grid').datagrid('reload');
			}
		});
		
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	},{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo',
		handler : doImport
	},{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'showid',
		title : '分拣编号',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.id;
		}
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.region.province;
		}
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.region.city;
		}
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.region.district;
		}
	}, {
		field : 'addresskey',
		title : '关键字',
		width : 120,
		align : 'center'
	}, {
		field : 'startnum',
		title : '起始号',
		width : 100,
		align : 'center'
	}, {
		field : 'endnum',
		title : '终止号',
		width : 100,
		align : 'center'
	} , {
		field : 'single',
		title : '单双号',
		width : 100,
		align : 'center',
		formatter : function(data,row ,index){
			if(row.single=="1"){
				return "单号";
			}else if(row.single=="2"){
				return "双号";
			}else{
				return "单双号";
			}
		}
	} , {
		field : 'position',
		title : '位置',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [5,8,10],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath }/subarea_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加、修改分区
		$('#addSubareaWindow').window({
	        title: '添加修改分区',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询分区
		$('#searchWindow').window({
	        title: '查询分区',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		$.fn.serializeJson=function(){  
            var serializeObj={};  
            var array=this.serializeArray();  
            var str=this.serialize();  
            $(array).each(function(){  
                if(serializeObj[this.name]){  
                    if($.isArray(serializeObj[this.name])){  
                        serializeObj[this.name].push(this.value);  
                    }else{  
                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
                    }  
                }else{  
                    serializeObj[this.name]=this.value;   
                }  
            });  
            return serializeObj;  
        };

		
		$("#btn").click(function(){
			var params = $('#searchForm').serializeJson();
			$('#grid').datagrid('load',params); 
			$('#searchWindow').window("close");
		});
		
	});


	function doDblClickRow(rowIndex, rowData){
		
		$("#id").val(rowData.id);
		$("#addresskey").val(rowData.addresskey);
		$("#startnum").val(rowData.startnum);
		$("#endnum").val(rowData.endnum);
		$("#single").val(rowData.single);
		$("#position").val(rowData.position);
		
		/* 数据回显问题 */
	 	$('#regionId').combobox('setValue', rowData.region.id);  
		
		$('#addSubareaWindow').window("open");
	}
	
	function _validatebox(){
		if($("#_form").form('validate')){
			//校验通过
			$("#_form").submit();
		}else{
			$.messager.alert('警告','表单数据异常','warning');
		}
	}
</script>	
</head>

<body class="easyui-layout" style="visibility:hidden;">
<form id="formgrid" action="${pageContext.request.contextPath }/subarea_delete.action" method="post">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	</form>
	<!-- 添加 修改分区 -->
	<div class="easyui-window" title="分区添加修改" id="addSubareaWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="javascript:_validatebox();" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="_form" action="${pageContext.request.contextPath }/subarea_saveOrUpdate.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<tr>
						<td>分拣编码</td>
						<td><input type="text" id="id" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>选择区域</td>
						<td>
							<input class="easyui-combobox" name="region.id"  id="regionId"
    							data-options="valueField:'id',textField:'info',url:'${pageContext.request.contextPath }/region_queryRegion.action?'" />  
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" id="addresskey" name="addresskey" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>起始号</td>
						<td><input type="text" id="startnum" name="startnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>终止号</td>
						<td><input type="text" id="endnum" name="endnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单双号</td>
						<td>
							<select class="easyui-combobox" id="single" name="single" style="width:150px;">  
							    <option value="0">单双号</option>  
							    <option value="1">单号</option>  
							    <option value="2">双号</option>  
							</select> 
						</td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td><input type="text" id="position" name="position" class="easyui-validatebox" required="true" style="width:250px;"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询分区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm" action="${pageContext.request.contextPath }/subarea_search.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text"  name="region.province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text"  name="region.city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td><input type="text"  name="region.district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>定区编码</td>
						<td><input type="text" name="decidedzone.id" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>