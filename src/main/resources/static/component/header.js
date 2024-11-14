let htmlCode = "";
$.ajax({
    url: `/menu/`, // 요청 URL
    type: `GET`, // HTTP 메서드
    contentType: `application/x-www-form-urlencoded`, // 헤더 설정
    data: {}, // 보내는 데이터
    success: function (response) {
        htmlCode =
        `<header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
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
                <div class="sidebar-nav accordion accordion-flush" id="accordionSideBarNavigation">`;
                    for( let i = 0; i < response.length; i++) {
                        var idx;
                        htmlCode += `<div class="accordion-item">`;
                        if(response[i].menu_depth == 1) {
                            idx = i;
                            htmlCode += `    <h2 class="accordion-header">`;
                            htmlCode += `        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#pannel`+ idx + `" aria-expanded="false" aria-controls="pannel`+ idx + `">`;
                            htmlCode += `            <i data-feather="settings"></i>`;
                            htmlCode +=              response[i].menu_nm;
                            htmlCode += `        </button>`;
                            htmlCode += `    </h2>`;
                        }
                        if(response[i].menu_depth == 2) {
                            htmlCode += `    <div id="pannel` + idx + `" class="accordion-collapse collapse">`;
                            htmlCode += `        <div class="accordion-body">`;
                            htmlCode += `            <a class="nav-link" href="` + response[i].menu_link +`">`;
                            htmlCode +=                 response[i].menu_nm;
                            htmlCode += `            </a>`;
                            htmlCode += `        </div>`;
                            htmlCode += `    </div>`;
                        }
                        htmlCode += `</div>`;
                    };
                    htmlCode +=
                `</div>
            </div>
        </nav>`;
    },
    error: function (xhr, status, error) {
        console.error(`Error:`, error);
    }
});

class HeaderComponent extends HTMLElement {
    connectedCallback() {
        this.innerHTML = htmlCode;
    }
}

customElements.define(`app-header`, HeaderComponent);