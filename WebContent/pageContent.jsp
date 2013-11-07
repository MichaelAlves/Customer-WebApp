<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@page import="customer.webapp.*" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
    
<shared:header></shared:header>
<div class="container">
	<div class="row">
	<h3>Customers</h3>
	<kendo:grid name="customers" pageable="true" filterable="true">
		<kendo:grid-sortable allowUnsort="false" mode="single"/>
		<kendo:grid-editable mode="popup"></kendo:grid-editable>
		<kendo:grid-pageable pageSizes="true" refresh="true"></kendo:grid-pageable>
		<kendo:dataSource pageSize="10">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="customer.webapp/CustomerServlet"></kendo:dataSource-transport-read>
				<kendo:dataSource-transport-update url ="customer.webapp/CustomerServlet?update" type="POST"></kendo:dataSource-transport-update>
				<kendo:dataSource-transport-destroy url="customer.webapp/CustomerServlet?delete" type="POST"></kendo:dataSource-transport-destroy>
				<kendo:dataSource-transport-create url="customer.webapp/CustomerServlet?create" type="POST"></kendo:dataSource-transport-create>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema total="Total" data="Data">
				<kendo:dataSource-schema-model id="id"></kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
		<kendo:grid-columns> 
		<kendo:grid-column title="First name" field="firstName" width ="150"></kendo:grid-column>
			<kendo:grid-column title="Last name" field="lastName" width="150"></kendo:grid-column>
			<kendo:grid-column title="Address" field="address"></kendo:grid-column>
			<kendo:grid-column title="City" field="city">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function cityFilter(element) {
								element.kendoDropDownList({
									optionLabel: "--Select Value --",
									dataSource: {
										transport: {
											read: "customer.webapp/Cities"
										}
									}
								});
					  		}
					  	</script>
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
	    	</kendo:grid-column>
			<kendo:grid-column title="State" field="state">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function stateFilter(element) {
								element.kendoDropDownList({
									optionLabel: "--Select Value --",
									dataSource: {
										transport: {
											read: "customer.webapp/States"
										}
									}
								});
					  		}
					  	</script>
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
	    	</kendo:grid-column>
			<kendo:grid-column title="Phone" field="phoneNumber"></kendo:grid-column>
			<kendo:grid-column>
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"></kendo:grid-column-commandItem>
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
	</kendo:grid>
	</div>
</div>

<shared:footer></shared:footer>