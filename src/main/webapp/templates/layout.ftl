[#ftl]
[#macro page]
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" media="screen, projection" href="/css/base.css"/>
  <link rel="stylesheet" media="screen, projection" href="/css/colorbox.css"/>
  <link rel="stylesheet" media="print" href="/css/print.css"/>
  <!--[if lt IE 7]>
  <link rel="stylesheet" type="text/css" href="/css/ie6.css"/>
  <![endif]-->
  <!--[if IE 7]>
  <style type="text/css">
    #footer, #nav {
      zoom: 1
    }
  </style>
  <![endif]-->
  <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
  <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
  <script src="/js/application.js"></script>
  <script src="/js/highlight.pack.js"></script>
  <script src="/js/jquery-1.4.2.min.js"></script>
  <script src="/js/jquery.colorbox-min.js"></script>
  <script type="text/javascript">
    // syntax highlighting
    hljs.initHighlightingOnLoad();
    // other stuff
    $(document).ready(function() {
      // smooth scrolling
      $('a[href*=#]').click(function() {
        var $target = $(this.hash);
        $target = $target.length && $target || $('[name=' + this.hash.slice(1) + ']');
        if ($target.length) {
          var $elem = this.hash.slice(1);
          var $loc = document.location.href.replace(/#.*$/, "") + "#" + $elem;
          var targetOffset = $target.offset().top;
          $('html,body').animate({scrollTop: targetOffset}, 750,
              function() {
                document.location.href = $loc;
              });
          return false;
        }
      });
      // show/hide TOC
      $('#toc-show').click(function() {
        $('#toc-show').fadeOut(200, function() {
          $('#toc').fadeIn(400);
        });
      });
      $('#toc-hide').click(function() {
        $('#toc').fadeOut(400, function() {
          $('#toc-show').fadeIn(200);
        });
      });
      $('#sidebar > ul').addClass("nav");
    });
  </script>
  [#if ciripage?? && .template_name?ends_with('/page.ftl')]
    <link rel="alternate"
          id="edit-link"
          type="application/x-wiki"
          title="Edit this page"
          href="${ciripage.uri}.html.e"/>
  [/#if]
  <title>
    [#if ciripage?? && ciripage.title != '']
    ${ciripage.title}
      [#else]
        Meta &mdash; elearning.rfei.ru documtntation
    [/#if]
  </title>
</head>
<body>
<div id="header">
  <div class="h-header">
  [#--<ul id="topnav">--]
  [#--<li id="help-link"><a href="/pages/help.shtml">Помощь</a></li>--]
  [#--<li id="enter-link"><a href="http://storage.rfei.ru/login">Войти</a></li>--]
  [#--</ul>--]
    <h1 id="logo"><a href="/">РФЭИ<span></span></a></h1>

    <h3>&mdash; институт, создавший сберегающее обучение</h3>
  </div>
</div>
  [#if toc?? && toc.toHtml != '']
  <a id="toc-show" class="toc" href="javascript:;" title="Show Table of Contents">table of contents &raquo;</a>

  <div id="toc" class="toc" style="display:none">
    <a id="toc-hide" class="right-float" href="javascript:;" title="Hide Table of Contents">&times;</a>
  ${toc.toHtml}
  </div>
  [/#if]
<div id="outer">
  <div id="scraps">
  </div>
  <div id="page">
    <div id="sidebar">
      [#if sitemap??]
        ${sitemap.toHtml}
      [/#if]
    </div>
    <div id="content">
      [#nested/]
    </div>
  </div>
  <div id="footer">
    <span class="years">2009-${currentYear}</span>
    <a class="home" href="http://${host}">${host}</a>
  </div>
</div>
[#--[@stats/]--]
</body>
</html>
[/#macro]


