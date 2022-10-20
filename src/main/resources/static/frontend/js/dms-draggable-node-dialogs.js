function dmsDragNodeDialogElement(elmnt) {
    if(elmnt == null)
        return;
  var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
  elmnt.style.top = (window.innerHeight / 2 - elmnt.offsetHeight / 2) + "px";
  elmnt.style.left = (window.innerWidth / 2 - elmnt.offsetWidth / 2) + "px";
  var dialogs = document.querySelectorAll('[id^=draggable-dialog-node-]'), i;
  var dialogsWidth = 10;
  for (i = 0; i < dialogs.length; i=i+2) {
      dialogsWidth += dialogs[i].offsetWidth;
  }
  for (i = 0; i < dialogs.length; i=i+2) {
      dialogs[i].style.left = (window.innerWidth / 2 - dialogsWidth /2 ) + "px";
      dialogsWidth -= dialogs[i].offsetWidth * 2 + 10;
  }

  if (document.getElementById(elmnt.id + "header")) {
    /* if present, the header is where you move the DIV from:*/
    document.getElementById(elmnt.id + "header").onmousedown = dragMouseDown;
  } else {
    /* otherwise, move the DIV from anywhere inside the DIV:*/
    elmnt.onmousedown = dragMouseDown;
  }

  function dragMouseDown(e) {
    e = e || window.event;
    e.preventDefault();
    // get the mouse cursor position at startup:
    pos3 = e.clientX;
    pos4 = e.clientY;
    document.onmouseup = closeDragElement;
    // call a function whenever the cursor moves:
    document.onmousemove = elementDrag;
  }

  function elementDrag(e) {
    e = e || window.event;
    e.preventDefault();
    // calculate the new cursor position:
    pos1 = pos3 - e.clientX;
    pos2 = pos4 - e.clientY;
    pos3 = e.clientX;
    pos4 = e.clientY;
    // set the element's new position:
    elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
    elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
  }

  function closeDragElement() {
    /* stop moving when mouse button is released:*/
    document.onmouseup = null;
    document.onmousemove = null;
  }
}