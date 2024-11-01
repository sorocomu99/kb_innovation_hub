class HeaderComponent extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
        <header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
            <a class="navbar-brand col-md-3 col-lg-2 me-0" href="#">KB Innovation HUB 관리자</a>
            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse"
                data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="right-link">
                <div class="item">
                    <a class="px-3" href="../" target="_blank"><span data-feather="home"></span> 홈페이지</a>
                </div>
                <div class="item">
                    <a class="px-3" href="../login/index.html"><span data-feather="log-out"></span> 로그아웃</a>
                </div>
            </div>
        </header>
        <nav class="sidebar" id="sidebarMenu">
            <div class="position-sticky pt-3">
                <div class="sidebar-nav accordion accordion-flush" id="accordionSideBarNavigation">
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button disabled class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panel1" aria-expanded="true" aria-controls="panel1">
                                <i data-feather="settings"></i>
                                기본설정 관리
                            </button>
                        </h2>
                        <div id="panel1" class="accordion-collapse collapse show">
                            <div class="accordion-body">
                                <a class="nav-link active" href="../setting/account_admin.html">관리자 계정 관리</a>
                                <a class="nav-link" href="../setting/popup.html">팝업 관리</a>
                                <a class="nav-link" href="../setting/menu.html">메뉴 관리</a>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#pannel2" aria-expanded="false" aria-controls="pannel2">
                                <span data-feather="grid"></span>
                                메인 관리
                            </button>
                        </h2>
                        <div id="pannel2" class="accordion-collapse collapse">
                            <div class="accordion-body">
                                <a class="nav-link" href="../page/main.html">메인 비주얼 관리</a>
                                <a class="nav-link" href="../page/result.html">주요 성과 관리</a>
                                <!-- <a class="nav-link" href="../main/starters.html">KB스타터스 관리</a> -->
                                <a class="nav-link" href="../history/index.html">연혁 관리</a>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#pannel3" aria-expanded="false" aria-controls="pannel3">
                                <svg class="feather" version="1.1" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" viewBox="0 0 16.5 18.9" stroke-linejoin="round" stroke-miterlimit="10" stroke-width="1.5" fill="none" stroke="currentColor">
                                    <path d="M11.8,8c0,0,0.1,0,0.1,0v0l-7.2,3c0,0-0.1,0-0.1,0v0L11.8,8z"/>
                                    <path d="M14,0.5c-1.2,0-2,0.8-2,2v2.1l-7.4,3V11c0,0,0.1,0,0.1,0l7.2-3v0v3.3v5.2c0,1.2,0.8,2,2,2c1.2,0,2-0.8,2-2V2.5 C16,1.3,15.2,0.5,14,0.5z"/>
                                    <path d="M11.8,8l-7.3,3v0V7.7V2.5c0-1.2-0.8-2-2-2c-1.2,0-2,0.8-2,2v13.9c0,1.2,0.8,2,2,2c1.2,0,2-0.8,2-2v-2.1 l7.4-3.1V7.9C11.9,8,11.9,8,11.8,8z"/>
                                </svg>
                                스타트업 육성 관리
                            </button>
                        </h2>
                        <div id="pannel3" class="accordion-collapse collapse">
                            <div class="accordion-body">
                                <a class="nav-link" href="../page/promote_graph.html">육성기업 그래프 관리</a>
                                <a class="nav-link" href="../page/hub_introduce.html">제휴 사례 관리</a>
                                <a class="nav-link" href="../page/investment_graph.html">투자 그래프 관리</a>
                                <a class="nav-link" href="../page/recruit.html">채용 지원 관리</a>
                                <a class="nav-link" href="../page/growth.html">협력기관 관리</a>
                                <a class="nav-link" href="../page/space.html">공간 관리</a>
                                <a class="nav-link" href="../page/interchange.html">현지교류 지원 관리</a>
                                <!-- <a class="nav-link" href="../page/hub_space.html">협업 공간 관리</a>
                                <a class="nav-link" href="../page/hub_global.html">글로벌 관리</a> -->
                            </div>
                        </div>
                    </div>
                    <!-- <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#pannel4" aria-expanded="true" aria-controls="pannel4">
                                <svg class="feather" version="1.1" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" viewBox="0 0 16.5 18.9" stroke-linejoin="round" stroke-miterlimit="10" stroke-width="1.5" fill="none" stroke="currentColor">
                                    <path d="M11.8,8c0,0,0.1,0,0.1,0v0l-7.2,3c0,0-0.1,0-0.1,0v0L11.8,8z"/>
                                    <path d="M14,0.5c-1.2,0-2,0.8-2,2v2.1l-7.4,3V11c0,0,0.1,0,0.1,0l7.2-3v0v3.3v5.2c0,1.2,0.8,2,2,2c1.2,0,2-0.8,2-2V2.5 C16,1.3,15.2,0.5,14,0.5z"/>
                                    <path d="M11.8,8l-7.3,3v0V7.7V2.5c0-1.2-0.8-2-2-2c-1.2,0-2,0.8-2,2v13.9c0,1.2,0.8,2,2,2c1.2,0,2-0.8,2-2v-2.1 l7.4-3.1V7.9C11.9,8,11.9,8,11.8,8z"/>
                                </svg>
                                HUB 소식 관리
                            </button>
                        </h2>
                        <div id="pannel4" class="accordion-collapse collapse">
                            <div class="accordion-body">
                                <a class="nav-link" href="../board/news.html">News 관리</a>
                                <a class="nav-link" href="../board/day.html">HUB Day 관리</a>
                            </div>
                        </div>
                    </div> -->
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#pannel5" aria-expanded="false" aria-controls="pannel5">
                                <span data-feather="hexagon"></span>
                                KB 스타터스 관리
                            </button>
                        </h2>
                        <div id="pannel5" class="accordion-collapse collapse">
                            <div class="accordion-body">
                                <a class="nav-link" href="../page/survey.html">지원서 설문 관리</a>
                                <a class="nav-link" href="../page/receipt.html">지원서 접수 관리</a>
                                <a class="nav-link" href="../page/receipt_trash.html">지원서 임시 보관함</a>
                                <a class="nav-link" href="../page/starters_list.html">포트폴리오 관리</a>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#pannel6" aria-expanded="false" aria-controls="pannel6">
                                <span data-feather="message-circle"></span>
                                커뮤니티 관리
                            </button>
                        </h2>
                        <div id="pannel6" class="accordion-collapse collapse">
                            <div class="accordion-body">
                                <a class="nav-link" href="../board/notice.html">공지사항 관리</a>
                                <a class="nav-link" href="../board/day.html">HUB센터 소식 관리</a>
                                <a class="nav-link" href="../board/faq.html">FAQ 관리</a>
                                <!-- <a class="nav-link" href="../board/qna.html">QnA 관리</a> -->
                                <!-- <a class="nav-link" href="../board/free.html">자유게시판 관리</a> -->
                                <!-- <a class="nav-link" href="../board/submission.html">제출게시판 관리</a> -->
                                <!-- <a class="nav-link" href="../board/submission_list.html">제출 내역 관리</a> -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
      `;
    }
}

customElements.define('app-header', HeaderComponent);