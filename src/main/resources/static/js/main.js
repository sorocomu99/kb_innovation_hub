window.addEventListener('DOMContentLoaded', () => {
    sr.reveal('.section-title', { distance: 0, opacity: 1 });
    sr.reveal('.section-history .history .item', { distance: '150px' });
    sr.reveal('.section-program .context li', { distance: '85%', interval: 150 });
    sr.reveal('.section-status .col', { distance: '55%', interval: 150 });

    const counters = document.querySelectorAll("[data-count]");

    visual();
    introduce();
    brandHistory();

    counters.forEach((item) => {
        const value = item.dataset.count;
        const counter = new countUp.CountUp(item, value, {
            enableScrollSpy: true,
            scrollSpyDelay: 0,
            decimalPlaces: value % 1 !== 0 ? 1 : 0,
            decimal: '.',
            duration: 3
        });
        counter.start();
    });
});

function visual() {
    const text = new SplitType('[data-split]');
    const playTime = 3000;
    const playSpeed = 1000;
    text.chars;

    const visualSlide = new Swiper('.section-visual .swiper', {
        effect: 'fade',
        allowTouchMove: false,
        loop: true,
        speed: playSpeed,
        parallax: true,
        keyboard: {
            enabled: true,
            onlyInViewport: false,
        },
        fadeEffect: {
            crossFade: true
        },
        autoplay: {
            delay: playTime,
            disableOnInteraction: false
        },
        on: {
            init: function () {
                // gsap.to(this.el.querySelector('.background'), { duration: 1, ease: 'sine.out', opacity: 0.7 });
                animate(this);
            },
            slideChangeTransitionStart: function () {
                animate(this);
            }
        }
    });

    function animate(swiper){
        let activeSlide = swiper.el.querySelector('.swiper-slide-active');
        let activeBackground = activeSlide.querySelector('.background');
        // let activeVideo = activeSlide.querySelector('video');
        // let activeTitle = activeSlide.querySelector('.title');
        let activeText = activeSlide.querySelectorAll('.word');

        gsap.set(activeBackground, { scale: 1.1 });
        gsap.set(activeText, { opacity: 0, y: 35 });
        gsap.to(activeBackground, { duration: (playTime + playSpeed + 1000) / 1000, ease: 'sine.out', scale: 1 });
        gsap.to(activeText, { delay: 0.5, duration: 1.5, ease: 'power3.out', opacity: 1, y: 0, stagger: { each: 0.05 } });
    }
}

function introduce() {
    const tl1 = gsap.timeline();
    const breakpoint = () => (window.innerWidth > 1580) ? true : false;
    
    setTimeout(() => {
        tl1.fromTo('.section-introduce .background', 1.5, {
            top: '39vh',
            left: breakpoint() ? 'calc(50vw - 710px)' : '5%',
            width: '710px',
            height: '46.4vh',
            borderRadius: '24px'
        }, {
            top: '0vh',
            left: 'calc(0vw - 0px)',
            width: '100vw',
            height: '100vh',
            borderRadius: '0px'
        }, '<')
        tl1.to('.section-introduce .context', 0.1, { opacity: 0 }, '<')
        tl1.to('.section-introduce .section-title', 0.35, { opacity: 0, y: -75 }, '<')
        tl1.from('.section-introduce .title', 0.85, { opacity: 0, y: 75 }, '<0.35')
        tl1.from('.section-introduce .contents', 0.85, { opacity: 0, y: 75, stagger: 0.07 }, '<.5')

        ScrollTrigger.create({
            animation: tl1,
            trigger: '.section-introduce',
            start: 'top top',
            end: 'bottom bottom',
            scrub: 1,
        });
    }, 100)
}

function brandHistory() {
    const item = gsap.utils.toArray('.section-history .history .item');
    const tl = gsap.timeline();

    for(let i = 0; i < item.length; i++) {
        ScrollTrigger.create({
            trigger: item[i],
            start: 'top 57%',
            end: 'bottom bottom',
            onEnter: () => item[i].classList.add('on'),
            onLeaveBack: () => item[i].classList.remove('on'),
            // markers: true
        });
    }

    gsap.to('.section-history .history .progress .bar', {
        ease: 'none',
        height: '100%',
        scrollTrigger: {
            animation: tl,
            trigger: '.history',
            start: 'top 60%',
            end: 'bottom 60%',
            scrub: 0.5,
            // markers: true
        }
    });
}