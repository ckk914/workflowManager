<!-- header -->
<header>
    
    <div class="inner-header">
        <a href="#" class="menu-open">
            <span class="menu-txt"></span>
            <span class="lnr lnr-menu"></span>
        </a>
        <h1 class="logo">
            <a class="logo-img" href="/">
                <img src="/assets/img/workwave_logo.png" alt="로고이미지" class="logo">
            </a>
        </h1>
        <div class="profile-box">
            <c:choose>
                <c:when test="${login != null && login.profile != null}">
                    <img src="${login.profile}" alt="profile image">
                </c:when>
                <c:otherwise>
                    <img src="/assets/img/anonymous2.png" alt="profile image">
                </c:otherwise>
            </c:choose>
        </div>
        <!-- <h2 class="intro-text">Welcome ${login.nickName}</h2> -->
    </div>

    <nav class="gnb">
        <a href="#" class="close">
        </a>
        <ul class="nav-content">
            <li>
                
                <a href="/">
                    <i class="fas fa-home"></i>
                    Home
                </a>
            </li>

            <li>
                <a href="/myCalendar/viewMyEvent">
                    <i class="fas fa-clipboard-check"></i>
                    My Calendar
                </a>
            </li>
            <li>
                <a href="/traffic-map">
                    <i class="fas fa-bus-alt"></i>
                    Traffic Info
                </a>
            </li>
            <li>
                <a href="/lunchMateBoard/list">
                    <i class="fas fa-utensils"></i>
                    Lunch Mate
                </a>
            </li>
            <li>
                <a href="/board/list">
                    <i class="fas fa-comment-alt"></i>
                    Board
                </a>
            </li>
            <c:if test="${login == null}">
            <div class="login-section">   
                <li class="sign-up">
                    <a href="/join">
                        <i class="fas fa-user-plus"></i>
                        Sign Up
                    </a>
                </li>
                <li class="sign-in">   
                    <a href="/login">
                        <i class="fas fa-sign-in-alt"></i>
                        Sign In
                    </a>
                </li>
            </div> 
            </c:if>
            <c:if test="${login != null}">
                <div class="login-section">   
                <li class="sign-out">
                    <a href="/member/logout">
                        <i class="fas fa-sign-out-alt"></i>
                        Sign Out
                    </a>
                </li>
                </div>
            </c:if>
        </ul>
    </nav>
</header>
<!--//header-->
