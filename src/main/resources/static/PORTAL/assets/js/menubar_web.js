(function () {
  window.documenta = {
    onrc: {
        tabMenubarInfo:undefined,
        topMenubarInfo: undefined,
        sideMenubarInfo: undefined,
        logout: undefined
    }
  };
  window.documenta.onrc.fetchTopMenubar = function (callback) {
    $.ajax({
      //url: "/dmsws/category/all_category_services",
      url: "/dmsws/meniu/all_menu_web_electronic",
      success: function (result) {
        console.log(result);
        menuInfo = JSON.stringify(result.menuList);
        window.documenta.onrc.topMenubarInfo = result.menuList;
        var sessionStorage = window.sessionStorage;
        !!menuInfo && sessionStorage.setItem("topMenuBar", menuInfo);
        result.menuList &&
          typeof callback === "function" &&
          callback(result.menuList);
      },
      error: function (err) {
        console.log(err);
      }
    });
  };


  function _populateTopMenubar(menubar, containerClassName) {

    if (!containerClassName) {
      containerClassName = "inline-menu-section";
    }
    if (!!menubar) {
      try {
        var storedMenuBarInfo = JSON.parse(menubar);
        window.documenta.onrc.topMenubarInfo = storedMenuBarInfo;
      } catch (err) {
        console.warn(err, menubar);
      }
    }
    if (!!window.documenta.onrc.topMenubarInfo) {
      var menuList = window.documenta.onrc.topMenubarInfo;
      //populate menu-bar here
      var topMenuContainerMain = $("." + containerClassName);
        topMenuContainerMain.empty();


      for (var i = 0; i < menuList.length; i++) {
          var topMenuContainer = $(
              "<div class='menu-box'></div>").appendTo(topMenuContainerMain);

        var menuItem = menuList[i];
          if(menuItem.nume.toLowerCase()=='persoane fizice'){
              $("<div class='menu-box-icon'>" +
                  "  <i class='fas fa-house-user active'></i> " +
                  " </div> " ).appendTo(topMenuContainer);
          }
          else if(menuItem.nume.toLowerCase()=='persoane juridice'){
              $("<div class='menu-box-icon'>" +
                  " <i class='fas fa-user-tie active'></i> " +
                  " </div> " ).appendTo(topMenuContainer);
          }
          else if(menuItem.nume.toLowerCase()=='liste publice'){
              $("<div class='menu-box-icon'>" +
                  "  <i class='fas fa-list active'></i>" +
                  " </div> " ).appendTo(topMenuContainer);
          }
          else{
              $("<div class='menu-box-icon'>" +
                  "  <i class='fas fa-align-justify active'></i>" +


                  " </div> " ).appendTo(topMenuContainer);
          }

          var menuListElement = $(
              "<div class='menu-box-content'></div>").appendTo(topMenuContainer);
        var menuItemElement = $(
            //lista de categorii
            "<h1 id='" + menuList[i].id + "'>"+menuItem.nume+"</h1>"
        ).appendTo(menuListElement);

        //lista nivel principal
        var subMenuItemList = menuItem.listaOptiuni;
        if (!!subMenuItemList && subMenuItemList.length > 0) {
          var subMenuListElement = $("<ul class='links-menu mt15 link-menu_dropdown'></ul>").appendTo(menuListElement);
          for (var j = 0; j < subMenuItemList.length; j++) {
             var subMenuItemListItem= subMenuItemList[j];

              var subMenuListElementLi=  $("<li id='" +
                  subMenuItemList[j].id +
                  "'></li>").appendTo(subMenuListElement);
              var linkHref="#";
              if(subMenuItemList[j].linkPortal!=null){
                  linkHref= subMenuItemList[j].linkPortal;
              }
              $("<a  target='_top' href='"+linkHref+"'>" +
                  subMenuItemList[j].nume +
                  "</a>").appendTo(subMenuListElementLi);
          if(!!subMenuItemListItem.listaOptiuni && subMenuItemListItem.listaOptiuni.length > 0){
              var secondSubMenuListElement = $("<ul class='links-menu links-menu-dropdown'></ul>").appendTo(subMenuListElementLi);
              for (var k = 0; k < subMenuItemListItem.listaOptiuni.length; k++) {
                var secondSubMenuItemListItem=subMenuItemListItem.listaOptiuni[k];
                  //var linkHref= secondSubMenuItemListItem.linkPortal!=null?secondSubMenuItemListItem.linkPortal+"&id="+secondSubMenuItemListItem.id:"consola.html?&id="+menuList[i].id;
                  var linkHref="#";
                  if(secondSubMenuItemListItem.linkPortal!=null){
                      linkHref= secondSubMenuItemListItem.linkPortal;
                  }
                  $(
                      "<li id='" +secondSubMenuItemListItem.id +
                      "' ><a target='_top' href='"+linkHref+"'>" +
                  secondSubMenuItemListItem.nume +
                      "</a></li>"
                  ).appendTo(secondSubMenuListElement);
              }
              }
          }
        }
      }
    } else {

      window.documenta.onrc.fetchTopMenubar(function () {
        _populateTopMenubar(undefined, containerClassName);
      });
    }
  }

  window.documenta.onrc.populateTopMenuBar = function (containerClassName) {

    try {
      if (!!sessionStorage) {
        var menubar = sessionStorage.getItem("topMenuBar");
        _populateTopMenubar(menubar, containerClassName);
      }
    } catch (err) {
      window.documenta.onrc.fetchTopMenubar(function () {
        _populateTopMenubar(undefined, containerClassName);
      });
    }
  };
  setIdParinte = function(id){
      window.sessionStorage.setItem("idParinte",id);
  }



  $(document).ready(function () {
      //sessionStorage.removeItem('topMenuBar');

      window.documenta.onrc.populateTopMenuBar();

      const sidebarElements = $('.sidebar-element');
      for (const child of sidebarElements) {
          const tooltipData = $(child).data('title');
          if (!tooltipData || tooltipData.length === 0) {
              continue;
          }
          tippy($(child).get(), {
              content: tooltipData ?? "",
              placement: 'right'
          });
      }

/*      $('.links-menu li').click(function(e) {
          e.stopPropagation();
          $(this).toggleClass('expanded');
      })*/

  });

})();

