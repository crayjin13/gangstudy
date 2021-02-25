function hasClass(target, className) {
	if( (' ' + target.className + ' ').replace(/[\n\t]/g, ' ').indexOf(' ' + className + ' ') > -1 ) return true;
	return false;
}
function removeClass(target, className){
    var elClass = ' ' + target.className + ' ';
    while(elClass.indexOf(' ' + className + ' ') !== -1){
         elClass = elClass.replace(' ' + className + ' ', '');
    }
    target.className = elClass;
}
function addClass(target, className){
    target.className += ' ' + className;   
}
  