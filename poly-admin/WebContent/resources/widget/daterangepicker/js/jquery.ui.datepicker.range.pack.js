var DATERANGEPICKER_DOMAIN = window.location.protocol + "//" + window.location.host + "/" + window.location.pathname.split("/")[1];
var DATERANGEPICKER_ROOT = DATERANGEPICKER_DOMAIN + "/resources/widget/daterangepicker";

document.write( '<link rel="stylesheet" href="' + DATERANGEPICKER_ROOT + '/css/redmond/jquery-ui-1.7.1.custom.css"  type="text/css" />');
document.write( '<link rel="stylesheet" href="' + DATERANGEPICKER_ROOT + '/css/jquery.ui.datepicker.range.css"  type="text/css" />');

document.write( '<script type="text/javascript" src="' + DATERANGEPICKER_ROOT + '/js/jquery-ui-1.7.1.custom.min.js"></script>');
document.write( '<script type="text/javascript" src="' + DATERANGEPICKER_ROOT + '/js/jquery.ui.datepicker.range.js"></script>');