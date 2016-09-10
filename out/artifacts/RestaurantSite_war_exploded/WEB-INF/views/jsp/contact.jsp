<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Fresh Point cafe</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="Restaurant of health fresh food. Affordable to everyone.">
  <meta name="author" content="Liliya Yalovchenko">
  <link rel="shortcut icon" href="/resources/apple.ico">

  <title>Cover Template for Bootstrap</title>

  <!-- Bootstrap core CSS -->
  <link href="/resources/css/bootstrap.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="/resources/css/carousel.css" rel="stylesheet">
</head>
<body>
<!--Navigation bar-->
<div class="navbar-wrapper" style="position: relative;">
  <div class="container">
    <div class="navbar navbar-inverse navbar-static-top" style="background-color: #689f38; border: none;" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <img src="/resources/images/apple(2).png" style="padding: 10px 0;"><a class="navbar-brand" style="color: #ffffff;" href="#">Fresh Point</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="/" style="background-color: #e33539; color: #ffffff">Home</a></li>
            <li><a href="/employees" style="color: #ffffff;">Our team</a></li>
            <li><a href="/menus" style="color: #ffffff">Menu</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" style="color: #ffffff" data-toggle="dropdown">More <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="/schema">Restaurant schema</a></li>
                <li><a href="/contact">It is easy to find us</a></li>

              </ul>
            </li>
          </ul>
          <form class="navbar-form navbar-right" role="form" action="/search" method="post">
            <div class="form-group">
              <input type="text" name="pattern" placeholder="Type dish name" class="form-control">
            </div>

            <button type="submit" class="btn btn-success">Search</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div> <!--Nav bar finished-->

<div class="container marketing" style="background-color: #ffffff">
  <div class="row">
    <div class="col-lg-8" style="float: none; margin-left: auto; margin-right: auto;">
      <h1 class="text-center" style="margin-top: 0; font-family: 'Minion Pro'; font-size: 80px; color: #e33539 ;">Fresh Point Cafe</h1>

    </div>
  </div>

  <hr class="featurette-divider" style="margin: 15px 0;">
  <div class="row" style="margin-top: 40px;">

    <div class="col-lg-4" style="margin: 0 auto; float: none;">
      <h2 class="text-center" style="color: #689f38;font-size: 30px;">We located here</h2>
      <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2540.8936866816116!2d30
      .51666646014519!3d50.44308088050026!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4
      0d4cef8af8001b5%3A0xa3fa9852d741b914!2sTarasa+Shevchenko+Blvd%2C+4%2C+Kyiv!5e0!3m2!1sen!2s
      ua!4v1470662494590" width="400" height="300" frameborder="0"  style="border:0; float: none; margin: 0 auto;" allowfullscreen></iframe>
      <address class="text-center">
        <strong>Fresh Point Cafe</strong><br>
        Taras Shevchenko Blvd. 4<br>
        Kiev, Ukraine<br>
        <abbr title="Phone">P:</abbr> (044) 456-7890
      </address>

      <address class="text-center">
        <strong>Anna Deepak</strong><br>
        <a href="mailto:#">anna.deepak@example.com</a>
      </address>

    </div><!-- /.col-lg-4 -->

  </div><!-- /.row -->
</div><!-- /.container -->

<!-- FOOTER -->
<div id="footer">
  <div class="container" style="padding: 40px 25px; background-color: #689f38;">
    <p class="text-center" style="color: #ffffff;">

      2016 Fresh Point Cafe, <strong>Taras Shevchenko Blvd. 4 </strong>
      <img src="/resources/images/apple(2).png" width="24">
    </p>
  </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src=/resources/js/docs.min.js"></script>
</body>
</html>
