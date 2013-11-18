<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@page import="customer.webapp.*" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>

    
<shared:header></shared:header>
										<script>
												function exportPDF(e){
													document.location.href='customer.webapp/PDF_File';
												}
										
		        								function exportExcel(e){
													document.location.href='customer.webapp/ExcelFile'; //servlet url
												}      						
		        			
												
                                				function createChart(e){
                                					
                                					var tr = $(e.target).closest("tr"); // get the current table row (tr)
                                			          
                                			        var data = this.dataItem(tr);	// get the data bound to the current table row
                                			        var servname = data.firstName + " " + data.lastName;
                                			      
                                					var energyData = [{
                                						"month":"January",
                                						"kWh" : Math.floor(Math.random() * (700-300)) + 300
                                					},
                                					{
                                						"month": "February",
                                						"kWh" : Math.floor(Math.random() * (700-300)) + 300
                                					},
                                					{
                                						"month": "March",
                                						"kWh": Math.floor(Math.random() * (700-300)) + 300
                                					},
                                					{
                                						"month": "April", 
                                						"kWh": Math.floor(Math.random() * (700-300)) + 300
                                					},
                                					{
                                						"month": "May",
                                						"kWh": Math.floor(Math.random() * (700-300)) + 300
                                					}, 
                                					{
                                						"month": "June", 
                                						"kWh": Math.floor(Math.random() * (700-300)) + 300
                                					},
                                					{
                                						"month": "July",
                                						"kWh": Math.floor(Math.random() * (700-300)) + 300
                                					},
                                					{
                                						"month": "August",
                                						"kWh": Math.floor(Math.random() * (700-300)) + 300
                                					},
                                					{
                                						"month": "September",
                                						"kWh": Math.floor(Math.random() * (700-300)) + 300
                                					},
                                					{
                                						"month":"October", 
                                						"kWh": Math.floor(Math.random() * (700-300)) + 300
                                					},
                                					{
                                						"month":"November",
                                						"kWh": Math.floor(Math.random() * (700-300)) + 300
                                					}];
                                					
                                					
                                    					
                                					$("#chart").kendoChart({
                                				        theme: $(document).data("kendoSkin") || "default",

                                				        dataSource: {
                                				            data: energyData
                                				        },
                                				        title: {
                                				            text: servname + "'s Energy Consumption January-November(kWh)"
                                				        },
                                				        legend: {
                                				            position: "bottom"
                                				        },
                                				        seriesDefaults: {
                                				        	type: "column",
                                				        },
                                				        series: [{
                                				        	type: "column",
                                				            field: "kWh",
                                				            name: "Energy Consumed"
                                				        }],
                                				        valueAxis: {
                                				        	max: 700,
                                				        	majorUnit: 100,
                                				            labels: {
                                				                format: "{0}kWh"
                                				            },
                                				        },
                                				        
                                				        categoryAxis: {
                                				            field: "month"
                                				        }
                                				    });
                                				}
                                				$(document).ready(function (){;
                                				$("#chartContainer").show();
                                				});
                                				
                                				
                                			</script>
<div class="container">
        <h3 align="center">Customers</h3>
        <kendo:grid name="customers" pageable="true" filterable="true">
        		<kendo:grid-toolbarTemplate>
        			<a class='k-button k-button-icontext' onclick='exportExcel()' href='#'>Export To Excel</a>
        			<a class='k-button k-button-icontext' onclick='exportPDF()' href='#'>Export To PDF</a>
        		</kendo:grid-toolbarTemplate>
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
                        <kendo:grid-column title="Edit">
                                <kendo:grid-column-command>
                                        <kendo:grid-column-commandItem name="edit"></kendo:grid-column-commandItem>
                                </kendo:grid-column-command>
                        </kendo:grid-column>
                         <kendo:grid-column title="Energy Use">
                             <kendo:grid-column-command>
                                	<kendo:grid-column-commandItem name="View Stats" click="createChart">
                                			
                                	</kendo:grid-column-commandItem>
                                	</kendo:grid-column-command>
                                </kendo:grid-column>
                </kendo:grid-columns>
        </kendo:grid>
        
</div>
<div id='chartContainer' style='width:1000px; margin-left:auto; margin-right:auto; display:none'>
        <div id="chart"></div>
 </div>
 <style scoped>
  .container {
      width: 1300px;
      margin-left: auto;
      margin-right: auto;
   }
   h3 {
   	 margin-left: auto;
   	 margin-right: auto;
     margin: 1em 0 0.5em 0;
     color: #343434;
     font-weight: normal;
     font-family: 'Ultra', sans-serif;   
     font-size: 36px;
     line-height: 42px;
     text-transform: uppercase;
     text-shadow: 0 2px white, 0 3px #777;
   }
 </style>
<shared:footer></shared:footer>