$(document).ready(function () {
	removeCssPrimeface();
	//$("LINK[href*='/Web/javax.faces.resource/theme.css.xhtml?ln=primefaces-aristo']").remove();
	$('.tab-icon a').click(function(){
		$('.tab-icon-active').attr('class','tab-icon');
	   	$(this).parent().attr('class','tab-icon-active');
	});
	
    currenttabid = GetURLParam("tabid"); if (currenttabid == undefined) currenttabid = "tab-1";
    currenticonid = GetURLParam("iconid"); if (currenticonid == undefined) currenticonid = "";
    ChangeTab(currenttabid, currenticonid);   
});

function ChangeTab(tabId, iconid) {
	var tab0 = document.getElementById("tab-0");
	if (tab0 != null) 
		tab0.className = "main-tab-content none";
    var tab1 = document.getElementById("tab-1");
    if (tab1 != null)
    	tab1.className = "main-tab-content none";
    var tab2 = document.getElementById("tab-2");
    if (tab2 != null)
    	tab2.className = "main-tab-content none";
    var tab3 = document.getElementById("tab-3");
    if (tab3 != null)
    	tab3.className = "main-tab-content none";
    var tab4 = document.getElementById("tab-4");
    if (tab4 != null)
    	tab4.className = "main-tab-content none";
    var tab5 = document.getElementById("tab-5");
    if (tab5 != null)
    	tab5.className = "main-tab-content none";
    var tab6 = document.getElementById("tab-6");
    if (tab6 != null)
    	tab6.className = "main-tab-content none";
    var tab7 = document.getElementById("tab-7");
    if (tab7 != null)
    	tab7.className = "main-tab-content none";
    var tab8 = document.getElementById("tab-8");
    if (tab8 != null)
    	tab8.className = "main-tab-content none";
    var tab9 = document.getElementById("tab-9");
    if (tab9 != null)
    	tab9.className = "main-tab-content none";
    var tab10 = document.getElementById("tab-10");
    if (tab10 != null)
    	tab10.className = "main-tab-content none";
    var tab11 = document.getElementById("tab-11");
    if (tab11 != null)
    	tab11.className = "main-tab-content none";
    var tab12 = document.getElementById("tab-12");
    if (tab12 != null)
    	tab12.className = "main-tab-content none";
    var tab13 = document.getElementById("tab-13");
    if (tab13 != null)
    	tab13.className = "main-tab-content none";
    var tab14 = document.getElementById("tab-14");
    if (tab14 != null)
    	tab14.className = "main-tab-content none";    
    var tab15 = document.getElementById("tab-15");
    if (tab15 != null)
    	tab15.className = "main-tab-content none";
    var tab16 = document.getElementById("tab-16");
    if (tab16 != null)
    	tab16.className = "main-tab-content none";
    
    var oldTabTitle = $(".tab-item-active");
    if (oldTabTitle != null) 
    	$(oldTabTitle).removeClass('tab-item-active fl').addClass('tab-item-normal fl');
		
    var currentTab = document.getElementById(tabId);
    if (currentTab != null) 
    	currentTab.className = "main-tab-content";
    var currentTabTitle = document.getElementById(tabId + "-title");
    if (currentTabTitle != null) 
    	currentTabTitle.className = "tab-item-active fl";
    var currentTabIcon = document.getElementById("tab-icon-" + iconid);
    
    if (currentTabIcon != null) currentTabIcon.className = "tab-icon-active";
    currentTabID = tabId;
}

function removeCssPrimeface() {
	var styleSheets = document.styleSheets;
	var patt = /theme.css/g;
	for (var i = 0; i < styleSheets.length; i++) {
	    if (patt.test(styleSheets[i].href)) {
	        styleSheets[i].disabled = true;
	        break;
	    }
	}
}